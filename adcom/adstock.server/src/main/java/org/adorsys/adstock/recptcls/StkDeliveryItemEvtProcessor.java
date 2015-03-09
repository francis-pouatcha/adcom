package org.adorsys.adstock.recptcls;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2OuEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctnEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtDataEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEvtDataEJB;
import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkDlvryItemHstry;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2OuEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkDlvryItemHstryEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;

/**
 * Check for the incoming of delivery closed event and 
 * process corresponding delivery items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDeliveryItemEvtProcessor {
	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;
	@Inject
	private PrcmtDlvryItemEvtDataEJB itemEvtDataEJB;
	@Inject
	private StkDlvryItemHstryEJB dlvryItemHstryEJB;
	@Inject
	private StkArticleLotEJB articleLotEJB;
	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;
	@Inject
	private StkArticleLot2OuEJB articleLot2OuEJB;
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;

	public void process(String itemEvtDataId, PrcmtDeliveryEvt deliveryEvt) {
		PrcmtDlvryItemEvtData itemEvtData = itemEvtDataEJB.findById(itemEvtDataId);
		if(itemEvtData==null) return;
		
		PrcmtDeliveryEvtData deliveryEvtData = evtDataEJB.findById(itemEvtData.getDlvryNbr());
		if(deliveryEvtData==null) return;

		StkDlvryItemHstry hstry = dlvryItemHstryEJB.findById(itemEvtData.getDlvryItemNbr());
		if(hstry!=null) return;
		
		String artPic = itemEvtData.getArtPic();
		String lotPic = itemEvtData.getLotPic();
		StkArticleLot stkArticleLot = articleLotEJB.findByIdentif(StkAbstractArticleLot.toId(lotPic));
		if(stkArticleLot==null){
			stkArticleLot = new StkArticleLot();
			stkArticleLot.setArtPic(artPic);
			stkArticleLot.setExpirDt(itemEvtData.getExpirDt());
			stkArticleLot.setLotPic(lotPic);
			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
			stkArticleLot.setPppuHT(itemEvtData.getPppuPreTax());
			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
			stkArticleLot.setSppuHT(itemEvtData.getSppuPreTax());
			stkArticleLot.setStkgDt(deliveryEvt.getHstryDt());
			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
			stkArticleLot.setSupplier(itemEvtData.getSupplier());
			stkArticleLot.setVatPurchPct(itemEvtData.getVatPct());
			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.create(stkArticleLot);
		} else {
			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
			stkArticleLot.setPppuHT(itemEvtData.getPppuPreTax());
			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
			stkArticleLot.setSppuHT(itemEvtData.getSppuPreTax());
			stkArticleLot.setStkgDt(deliveryEvt.getHstryDt());
			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
			stkArticleLot.setSupplier(itemEvtData.getSupplier());
			stkArticleLot.setVatPurchPct(itemEvtData.getVatPct());
			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.update(stkArticleLot);
		}
				
		List<PrcmtDlvryItem2OuEvtData> ouEvtDataList = itemEvtDataEJB.listDlvryItem2OuEvtData(itemEvtData.getDlvryItemNbr());
		for (PrcmtDlvryItem2OuEvtData evtData : ouEvtDataList) {
			StkArticleLot2Ou ou = new StkArticleLot2Ou();
			ou.setArtPic(stkArticleLot.getArtPic());
			ou.setLotPic(stkArticleLot.getLotPic());
			ou.setOrgUnit(evtData.getRcvngOrgUnit());
			ou.setQty(BigDecimalUtils.sum(evtData.getFreeQty(), evtData.getQtyDlvrd()));
			articleLot2OuEJB.create(ou);
		}
		
		List<PrcmtDlvryItem2StrgSctnEvtData> strgSctnEvtDataList = itemEvtDataEJB.listDlvryItem2StrgSctnEvtData(itemEvtData.getDlvryItemNbr());
		BigDecimal stored = BigDecimal.ZERO;
		for (PrcmtDlvryItem2StrgSctnEvtData strgSctnEvtData : strgSctnEvtDataList) {
			StkArticleLot2StrgSctn strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(stkArticleLot.getArtPic());
			strgSctn.setLotPic(stkArticleLot.getLotPic());
			strgSctn.setStrgSection(strgSctnEvtData.getStrgSection());
			articleLot2StrgSctnEJB.create(strgSctn);

			// Lot Stock Qty
			StkLotStockQty lotStockQty = new StkLotStockQty();
			lotStockQty.setArtPic(itemEvtData.getArtPic());
			lotStockQty.setLotPic(itemEvtData.getLotPic());
			lotStockQty.setSection(strgSctnEvtData.getStrgSection());
			lotStockQty.setQtyDt(deliveryEvt.getHstryDt());
			lotStockQty.setSeqNbr(0);
			lotStockQty.setStockQty(strgSctnEvtData.getQtyStrd());
			stored = BigDecimalUtils.sum(stored, strgSctnEvtData.getQtyStrd());
			lotStockQtyEJB.create(lotStockQty);
		}
		
		hstry = new StkDlvryItemHstry();
		deliveryEvt.copyTo(hstry);
		hstry.setId(itemEvtData.getDlvryItemNbr());
		hstry.setEntIdentif(itemEvtData.getDlvryItemNbr());
		hstry.setAddtnlInfo("stored : " + stored);
		hstry.setComment(stkArticleLot.getLotPic());
		dlvryItemHstryEJB.create(hstry);
	}

}
