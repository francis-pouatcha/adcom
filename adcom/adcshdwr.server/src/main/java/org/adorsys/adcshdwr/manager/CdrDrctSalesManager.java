/**
 * 
 */
package org.adorsys.adcshdwr.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
		
		AmtHolder salesHolder = new AmtHolder();
		for (CdrDsArtItemHolder artItemHolder : items) {
			CdrDsArtItem artItem = artItemHolder.getItem();
			artItem.consolidate();
			BigDecimal totalRebate = BigDecimal.ZERO;
			BigDecimal rebate = artItem.getRebate();
			BigDecimal grossSPPreTax = artItem.getGrossSPPreTax();
			BigDecimal netSPPreTax = artItem.getNetSPPreTax();
			BigDecimal netSPTaxIncl = artItem.getNetSPTaxIncl();
			BigDecimal soldQty = artItem.getSoldQty();
			BigDecimal sppuPreTax = artItem.getSppuPreTax();
			BigDecimal vatAmount = artItem.getVatAmount();
			
//			String sppuCur = artItem.getSppuCur();
			
			totalRebate = rebate.multiply(soldQty);
			grossSPPreTax = sppuPreTax.multiply(soldQty);
			netSPPreTax = grossSPPreTax.subtract(totalRebate);
			vatAmount = netSPPreTax.multiply(artItem.getVatPct().divide(BigDecimalUtils.HUNDRED));
			netSPTaxIncl = netSPPreTax.add(vatAmount);
			
			artItem.setDsNbr(cdrDrctSales.getDsNbr());
			artItem.setNetSPPreTax(netSPPreTax);
			artItem.setNetSPTaxIncl(netSPTaxIncl);
			artItem.setRebate(totalRebate);
			artItem.setVatAmount(vatAmount);
			artItem.setObjctOrgUnit(securityUtil.getCurrentOrgUnit().getIdentif());
			cdrDsArtItemEJB.create(artItem);
			//sum amt for the drctsales
			salesHolder.grossSPPreTax = salesHolder.grossSPPreTax.add(grossSPPreTax);
			salesHolder.netSPPreTax = salesHolder.netSPPreTax.add(netSPPreTax);
			salesHolder.netSPTaxIncl = salesHolder.netSPTaxIncl.add(netSPTaxIncl);
			salesHolder.vatAmount = salesHolder.vatAmount.add(vatAmount);
			salesHolder.rebate = salesHolder.rebate.add(totalRebate);
		}
		salesHolder.netSalesAmt = salesHolder.netSPTaxIncl;
		cdrDrctSales.setNetSalesAmt(salesHolder.netSalesAmt);
        cdrDrctSales.setNetAmtToPay(salesHolder.netSalesAmt);
        cdrDrctSales.setNetSPPreTax(salesHolder.netSPPreTax);
        cdrDrctSales.setNetSPTaxIncl(salesHolder.netSPTaxIncl);
        cdrDrctSales.setVatAmount(salesHolder.vatAmount);
		cdrDrctSalesEJB.create(cdrDrctSales);
		return cdrDsArtHolder;
	}
	
	@SuppressWarnings(value = { "unused" })
	private CdrDsArtItem createInstance(CdrDrctSales cdrDrctSales,
			CdrDsArtItem artItem, BigDecimal netSPPreTax,
			BigDecimal netSPTaxIncl) {
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
		return cdrDsArtItem;
	}
}