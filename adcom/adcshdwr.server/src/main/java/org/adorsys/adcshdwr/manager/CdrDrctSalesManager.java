/**
 * 
 */
package org.adorsys.adcshdwr.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEJB;
import org.adorsys.adcshdwr.rest.CdrDsArtItemEJB;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class CdrDrctSalesManager {
	
	@Inject
	private CdrDrctSalesEJB cdrDrctSalesEJB;
	
	@Inject
	private CdrDsArtItemEJB cdrDsArtItemEJB;
	
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	@Inject
	private SecurityUtil securityUtil;
	public CdrDsArtHolder save(CdrDsArtHolder cdrDsArtHolder) {
		if(cdrDsArtHolder == null) throw new IllegalArgumentException("Invalid ds articles holder");
		List<CdrDsArtItemHolder> items = cdrDsArtHolder.getItems();

		CdrCshDrawer activeCshDrawer = cdrCshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new IllegalStateException("Invalid cash drawer. A new one must be opened");
		
		CdrDrctSales cdrDrctSales = new CdrDrctSales();
		cdrDrctSales.setDsNbr(SequenceGenerator.getSequence(SequenceGenerator.CASHDRAWER_SEQUENCE_PREFIXE));
		cdrDrctSales.setCashier(securityUtil.getCurrentLoginName());
		cdrDrctSales.setIdentif(UUID.randomUUID().toString());
		cdrDrctSales.setCdrNbr(activeCshDrawer.getCdrNbr());
		cdrDrctSales.setRcptNbr(RandomStringUtils.randomAlphabetic(7));
		
		BigDecimal amountTTC = BigDecimal.ZERO;
		BigDecimal amountHT = BigDecimal.ZERO;
		for (CdrDsArtItemHolder artItemHolder : items) {
			CdrDsArtItem artItem = artItemHolder.getItem();
			
			BigDecimal sppuPreTax = artItem.getSppuPreTax();
			BigDecimal soldQty = artItem.getSoldQty();
			if(sppuPreTax == null) throw new IllegalStateException("Invlaid sppuPreTax value");
			if(soldQty == null) throw new IllegalStateException("Invlaid soldQty value");
			BigDecimal netSPPreTax = sppuPreTax.multiply(soldQty);
			BigDecimal vatAmount = artItem.getVatAmount();
			if(vatAmount == null ) vatAmount = BigDecimal.ZERO;
			BigDecimal basePercentOfRatePct = BigDecimalUtils.basePercentOfRatePct(vatAmount, netSPPreTax);
			BigDecimal netSPTaxIncl = BigDecimalUtils.sum(netSPPreTax,basePercentOfRatePct);
			
			amountHT = BigDecimalUtils.sum(amountHT,netSPPreTax);
			amountTTC = BigDecimalUtils.sum(amountTTC,netSPTaxIncl);
			
			CdrDsArtItem cdrDsArtItem = new CdrDsArtItem();
			cdrDsArtItem.setArtPic(artItem.getArtPic());
			cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
			cdrDsArtItem.setGrossSPPreTax(artItem.getGrossSPPreTax());
			cdrDsArtItem.setLotPic(artItem.getLotPic());
			cdrDsArtItem.setNetSPPreTax(netSPPreTax);//set the netSPPreTax
			cdrDsArtItem.setNetSPTaxIncl(netSPTaxIncl);//set the netSPTaxIncl
			cdrDsArtItem.setObjctOrgUnit(securityUtil.getCurrentOrgUnit().getIdentif());
			cdrDsArtItem.setRebate(artItem.getRebate());
			cdrDsArtItem.setRestockgFees(artItem.getRestockgFees());
			cdrDsArtItem.setReturnedQty(artItem.getReturnedQty());//TODO compute the returned qty ?
			cdrDsArtItem.setSoldQty(artItem.getSoldQty());
			cdrDsArtItem.setSppuCur(artItem.getSppuCur());
			cdrDsArtItem.setSppuPreTax(artItem.getSppuPreTax());
			cdrDsArtItem.setVatAmount(artItem.getVatAmount());
			cdrDsArtItem.setVatPct(artItem.getVatPct());
			
			cdrDsArtItemEJB.create(cdrDsArtItem);
			
		}
		
		cdrDrctSalesEJB.create(cdrDrctSales);
		//TODO update cdrcshdrawer amount
		return cdrDsArtHolder;
	}
}