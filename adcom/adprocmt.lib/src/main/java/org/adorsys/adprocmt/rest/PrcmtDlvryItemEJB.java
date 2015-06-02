package org.adorsys.adprocmt.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2OuRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2POItemRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2StrgSctnRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemRepository;

/**
 * Stores a delivery item. 
 * 
 * Whenever a delivery item is stored, a copy (event data) is also stored for event processing.
 * This copy is updated and deleted synchronously with the delivery item.
 * 
 * The event data object can also be deleted under certain condition independently of the delivery item object.
 * 
 * @author francis
 *
 */
@Stateless
public class PrcmtDlvryItemEJB {

	@Inject
	private PrcmtDlvryItemRepository repository;

	@Inject
	private PrcmtDlvryArtPrcssngEJB artPrcssngEJB;
	
	@Inject
	private PrcmtDlvryItem2OuRepository ouRepository;
	
	@Inject
	private PrcmtDlvryItem2POItemRepository poItemRepository;
	
	@Inject
	private PrcmtDlvryItem2StrgSctnRepository strgSctnRepository;
	
	@Inject
	private SecurityUtil securityUtil;

	public PrcmtDlvryItem create(PrcmtDlvryItem entity) {
		
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		entity.setCreatingUsr(currentLoginName);
		entity.setCreationDt(now);
		
		entity = repository.save(attach(entity));
		
		// CHeck and create the article processing synchronization object
		PrcmtDlvryArtPrcssng artPrcssng = artPrcssngEJB.findById(PrcmtDlvryArtPrcssng.toId(entity.getDlvryNbr(), entity.getArtPic()));
		if(artPrcssng==null) artPrcssng = new PrcmtDlvryArtPrcssng();
		artPrcssng.setDlvryNbr(entity.getDlvryNbr());
		artPrcssng.setArtPic(entity.getArtPic());
		artPrcssngEJB.create(artPrcssng);
		return entity;
	}
	
	public PrcmtDlvryItem2Ou addDlvryItem2Ou(PrcmtDlvryItem dlvryItem, String rcvngOrgUnit, BigDecimal qtyDlvrd, BigDecimal freeQty){
		PrcmtDlvryItem2Ou item2Ou = new PrcmtDlvryItem2Ou();
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		item2Ou.setDlvryItemNbr(dlvryItemNbr );
		item2Ou.setRcvngOrgUnit(rcvngOrgUnit);
		item2Ou.setQtyDlvrd(qtyDlvrd);
		item2Ou.setFreeQty(freeQty);
		item2Ou = ouRepository.save(item2Ou);

		return item2Ou;
	}
	
	public PrcmtDlvryItem2POItem addDlvryItem2POItem(PrcmtDlvryItem dlvryItem, String poItemNbr, BigDecimal qtyOrdered, BigDecimal qtyDlvrd, BigDecimal freeQty){
		PrcmtDlvryItem2POItem item2PoItem = new PrcmtDlvryItem2POItem();
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		item2PoItem.setDlvryItemNbr(dlvryItemNbr );
		item2PoItem.setPoItemNbr(poItemNbr);
		item2PoItem.setQtyOrdered(qtyOrdered);
		item2PoItem.setQtyDlvrd(qtyDlvrd);
		item2PoItem.setFreeQty(freeQty);
		item2PoItem = poItemRepository.save(item2PoItem);
		
		return item2PoItem;
	}
	public PrcmtDlvryItem2StrgSctn addDlvryItem2StrgSctn(PrcmtDlvryItem dlvryItem, String strgSection, BigDecimal stkQtyPreDlvry, BigDecimal qtyStrd){
		PrcmtDlvryItem2StrgSctn item2StrgSctn = new PrcmtDlvryItem2StrgSctn();
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		item2StrgSctn.setDlvryItemNbr(dlvryItemNbr);
		item2StrgSctn.setStrgSection(strgSection);
		item2StrgSctn.setQtyStrd(qtyStrd);
		item2StrgSctn.setStkQtyPreDlvry(stkQtyPreDlvry);
		item2StrgSctn = strgSctnRepository.save(item2StrgSctn);
		
		return item2StrgSctn;
	}

	public PrcmtDlvryItem deleteById(String id) {
		PrcmtDlvryItem entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
			List<PrcmtDlvryItem2Ou> ous = listDlvryItem2Ou(entity.getDlvryItemNbr());
			for (PrcmtDlvryItem2Ou ou : ous) {
				ouRepository.remove(ou);
			}
			List<PrcmtDlvryItem2POItem> pos = listDlvryItem2POItem(entity.getDlvryItemNbr());
			for (PrcmtDlvryItem2POItem po : pos) {
				poItemRepository.remove(po);
			}
			List<PrcmtDlvryItem2StrgSctn> strgs = listDlvryItem2StrgSctn(entity.getDlvryItemNbr());
			for (PrcmtDlvryItem2StrgSctn strg : strgs) {
				strgSctnRepository.remove(strg);
			}
		}
		return entity;
	}
	
	public PrcmtDlvryItem2POItem deletePoItem(String dlvryItemNbr, String poItemNbr){
		PrcmtDlvryItem2POItem po = poItemRepository.findBy(PrcmtDlvryItem2POItem.toId(dlvryItemNbr, poItemNbr));
		if(po!=null){
			poItemRepository.remove(po);
		}
		return po;
	}
	
	public PrcmtDlvryItem2Ou deleteOu(String dlvryItemNbr, String rcvngOrgUnit){
		PrcmtDlvryItem2Ou ou = ouRepository.findBy(PrcmtDlvryItem2Ou.toId(dlvryItemNbr, rcvngOrgUnit));
		if(ou!=null){
			ouRepository.remove(ou);
		}
		return ou;
	}
	
	public PrcmtDlvryItem2StrgSctn deleteStrgSctn(String dlvryItemNbr, String strgSection){
		PrcmtDlvryItem2StrgSctn strgSctn = strgSctnRepository.findBy(PrcmtDlvryItem2StrgSctn.toId(dlvryItemNbr, strgSection));
		if(strgSctn!=null){
			strgSctnRepository.remove(strgSctn);
		}
		return strgSctn;
	}

	public PrcmtDlvryItem update(PrcmtDlvryItem entity) {
		entity = repository.save(attach(entity));

		return entity;
	}

	
	public PrcmtDlvryItem2Ou updateDlvryItem2Ou(PrcmtDlvryItem dlvryItem, PrcmtDlvryItem2Ou item2Ou){
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		PrcmtDlvryItem2Ou pers = ouRepository.findBy(PrcmtDlvryItem2Ou.toId(dlvryItemNbr, item2Ou.getRcvngOrgUnit()));
		if(pers==null) throw new IllegalStateException("Missing PrcmtDlvryItem2Ou with id : " + PrcmtDlvryItem2Ou.toId(dlvryItemNbr, item2Ou.getRcvngOrgUnit()));
		if(item2Ou.contentEquals(pers)) return item2Ou;

		item2Ou.copyTo(pers);
		item2Ou = ouRepository.save(pers);

		return item2Ou;
	}
	
	public PrcmtDlvryItem2POItem updateDlvryItem2POItem(PrcmtDlvryItem dlvryItem, PrcmtDlvryItem2POItem item2PoItem){
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		PrcmtDlvryItem2POItem pers = poItemRepository.findBy(PrcmtDlvryItem2POItem.toId(dlvryItemNbr, item2PoItem.getPoItemNbr()));
		if(pers==null) throw new IllegalStateException("Missing PrcmtDlvryItem2POItem with id : " + PrcmtDlvryItem2POItem.toId(dlvryItemNbr, item2PoItem.getPoItemNbr()));
		if(item2PoItem.contentEquals(pers)) return item2PoItem;
		
		item2PoItem.copyTo(pers);
		item2PoItem = poItemRepository.save(pers);

		return item2PoItem;
	}
	public PrcmtDlvryItem2StrgSctn updateDlvryItem2StrgSctn(PrcmtDlvryItem dlvryItem, PrcmtDlvryItem2StrgSctn item2StrgSctn){
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		PrcmtDlvryItem2StrgSctn pers = strgSctnRepository.findBy(PrcmtDlvryItem2StrgSctn.toId(dlvryItemNbr, item2StrgSctn.getStrgSection()));
		if(pers==null) throw new IllegalStateException("Missing PrcmtDlvryItem2POItem with id : " + PrcmtDlvryItem2StrgSctn.toId(dlvryItemNbr, item2StrgSctn.getStrgSection()));		
		if(item2StrgSctn.contentEquals(pers)) return item2StrgSctn;
		
		item2StrgSctn.copyTo(pers);
		item2StrgSctn = strgSctnRepository.save(pers);

		return item2StrgSctn;
	}
	
	public PrcmtDlvryItem findById(String id) {
		return repository.findBy(id);
	}
	public PrcmtDlvryItem2Ou findDlvryItem2Ou(String dlvryItemNbr, String orgUnit){
		return ouRepository.findBy(PrcmtDlvryItem2Ou.toId(dlvryItemNbr, orgUnit));
	}
	public PrcmtDlvryItem2POItem findDlvryItem2POItem(String dlvryItemNbr, String poItemNbr){
		return poItemRepository.findBy(PrcmtDlvryItem2POItem.toId(dlvryItemNbr, poItemNbr));
	}
	public PrcmtDlvryItem2StrgSctn findDlvryItem2StrgSctn(String dlvryItemNbr, String strgSctn){
		return strgSctnRepository.findBy(PrcmtDlvryItem2StrgSctn.toId(dlvryItemNbr, strgSctn));
	}

	public List<PrcmtDlvryItem> listAll(int start, int max) {
		return repository.findAll(start, max);
	}
	public List<PrcmtDlvryItem2Ou> listDlvryItem2Ou(String dlvryItemNbr){
		return ouRepository.findByDlvryItemNbr(dlvryItemNbr);
	}
	public List<PrcmtDlvryItem2POItem> listDlvryItem2POItem(String dlvryItemNbr){
		return poItemRepository.findByDlvryItemNbr(dlvryItemNbr);
	}
	public List<PrcmtDlvryItem2StrgSctn> listDlvryItem2StrgSctn(String dlvryItemNbr){
		return strgSctnRepository.findByDlvryItemNbr(dlvryItemNbr);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDlvryItem> findBy(PrcmtDlvryItem entity, int start,
			int max, SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDlvryItem entity,
			SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDlvryItem> findByLike(PrcmtDlvryItem entity, int start,
			int max, SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDlvryItem entity,
			SingularAttribute<PrcmtDlvryItem, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDlvryItem attach(PrcmtDlvryItem entity) {
		if (entity == null)
			return null;

		return entity;
	}
	public List<PrcmtDlvryItem> findByDlvryNbrAndArtPic(String dlvryNbr, String artPic){
		return repository.findByDlvryNbrAndArtPic(dlvryNbr, artPic);
	}
	
	public List<PrcmtDlvryItem> findByDlvryNbr(String dlvryNbr, int start, int max){
		return repository.findByDlvryNbr(dlvryNbr).firstResult(start).maxResults(max).getResultList();
	}
	public List<PrcmtDlvryItem> findByDlvryNbr(String dlvryNbr){
		return repository.findByDlvryNbr(dlvryNbr).getResultList();
	}

	@SuppressWarnings("unchecked")
	public Long countByDlvryNbr(String dlvryNbr){
		PrcmtDlvryItem dlvryItem = new PrcmtDlvryItem();
		dlvryItem.setDlvryNbr(dlvryNbr);
		//return repository.count(dlvryItem, new SingularAttribute[]{PrcmtDlvryItem_.dlvryNbr});
		return repository.findByDlvryNbr(dlvryNbr).count();
	}

	public PrcmtDlvryItem findByIdentif(String identif) {
		List<PrcmtDlvryItem> resultList = repository.findByIdentif(identif).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
}
