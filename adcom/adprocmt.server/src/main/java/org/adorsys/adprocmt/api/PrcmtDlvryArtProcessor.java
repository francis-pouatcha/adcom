package org.adorsys.adprocmt.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryArtPrcssngEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEJB;
import org.adorsys.adprocmt.rest.PrcmtPOItemEJB;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class PrcmtDlvryArtProcessor {

	@Inject
	private PrcmtDlvryArtPrcssngEJB artPrcssngEJB;
	@Inject
	private PrcmtDlvryItemEJB dlvryItemEJB;
	@Inject
	private PrcmtPOItemEJB poItemEJB;
	@Inject
	private PrcmtDeliveryEJB deliveryEJB;

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void process(String artPrcssngId){
		String myId = UUID.randomUUID().toString();
		PrcmtDlvryArtPrcssng artPrcssng = artPrcssngEJB.findById(artPrcssngId);
		if(artPrcssng==null) return;
		
		Date now = new Date();
		
		if(artPrcssng.getPrcssngAgent()!=null){
			Date endTme = artPrcssng.getPrcssngEndTme();
			if(endTme!=null && endTme.after(now)) return;
		}
		artPrcssng.setPrcssngAgent(myId);
		artPrcssng.setPrcssngEndTme(DateUtils.addMinutes(now, 3));
		try {
			artPrcssng = artPrcssngEJB.lock(artPrcssng);
		} catch(OptimisticLockException ex){
			// noop, somebody else had the lock.
		}
		
		// Now process the article.
		List<PrcmtDlvryItem> dlvryItems = dlvryItemEJB.findByDlvryNbrAndArtPic(artPrcssng.getDlvryNbr(), artPrcssng.getArtPic());
		// We start by sorting per expiration date
		Map<String, List<PrcmtDlvryItem>> byExpirDate = sortByExpirDate(dlvryItems);

		// We sort them per purchase price per unit pre tax
		Map<String, Map<String, List<PrcmtDlvryItem>>> byExpirAndPppu = sortByPppu(byExpirDate);
		// Now each list will have the same lot identification code.
		flattenLotPics(byExpirAndPppu);
				
		// Save items.
		for (PrcmtDlvryItem dlvryItem : dlvryItems) {
			dlvryItemEJB.update(dlvryItem);
		}
		
		artPrcssngEJB.unlock(artPrcssng);
		
		artPrcssngEJB.deleteById(artPrcssng.getId());
	}

	private void flattenLotPics(
			Map<String, Map<String, List<PrcmtDlvryItem>>> byExpirAndPppu) {
		Collection<Map<String, List<PrcmtDlvryItem>>> byPppus = byExpirAndPppu.values();
		for (Map<String, List<PrcmtDlvryItem>> byPppu : byPppus) {
			Collection<List<PrcmtDlvryItem>> values = byPppu.values();
			for (List<PrcmtDlvryItem> list : values) {
				String lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
				for (PrcmtDlvryItem di : list) {
					di.setLotPic(lotPic);
				}
			}
		}
		
	}

	private Map<String, Map<String, List<PrcmtDlvryItem>>> sortByPppu(
			Map<String, List<PrcmtDlvryItem>> byExpirDate) {
		Map<String, Map<String, List<PrcmtDlvryItem>>> result = new HashMap<String, Map<String,List<PrcmtDlvryItem>>>();
		Set<String> keySet = byExpirDate.keySet();
		for (String expirDayStr : keySet) {
			Map<String, List<PrcmtDlvryItem>> map = new HashMap<String, List<PrcmtDlvryItem>>();
			result.put(expirDayStr, map);
			List<PrcmtDlvryItem> list = byExpirDate.get(expirDayStr);
			for (PrcmtDlvryItem prcmtDlvryItem : list) {
				String pppuStr = null;
				BigDecimal pppuPreTax = prcmtDlvryItem.getPppuPreTax();
				if(pppuPreTax==null){
					// Fist try to find the pppu
					pppuStr = BigDecimal.ZERO.toString();
				} else {
					pppuStr = pppuPreTax.toString();
				}
				putToMap(pppuStr, prcmtDlvryItem, map);
			}
		}
		return result ;
	}


	public static final String DEFAULT_DATE = "DEFAULT_DATE";
	private Map<String, List<PrcmtDlvryItem>> sortByExpirDate(
			List<PrcmtDlvryItem> dlvryItems) {
		Map<String, List<PrcmtDlvryItem>> result = new HashMap<String, List<PrcmtDlvryItem>>();
		for (PrcmtDlvryItem prcmtDlvryItem : dlvryItems) {
			if(prcmtDlvryItem.getExpirDt()==null){
				putToMap(DEFAULT_DATE,prcmtDlvryItem, result);
			} else {
				String dayStr = CalendarUtil.getDayStr(prcmtDlvryItem.getExpirDt(), null);
				putToMap(dayStr,prcmtDlvryItem, result);
			}
		}
		return result;
	}
	private void putToMap(String key, PrcmtDlvryItem prcmtDlvryItem,
			final Map<String, List<PrcmtDlvryItem>> result) {
		List<PrcmtDlvryItem> list = result.get(key);
		if(list==null) {
			list = new ArrayList<PrcmtDlvryItem>();
			result.put(key, list);
		}
		list.add(prcmtDlvryItem);
	}
}
