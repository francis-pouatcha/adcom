package org.adorsys.adstock.recptcls;

import java.util.List;

import javax.ejb.Asynchronous;
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
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2OuEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;

/**
 * Check for the incomming of delivery closed event and 
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
	private StkArticleLotEJB articleLotEJB;
	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;
	@Inject
	private StkArticleLot2OuEJB articleLot2OuEJB;
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;

	@Asynchronous
	public void process(PrcmtDlvryItemEvtData evtRef, PrcmtDeliveryEvt deliveryEvt) {
		PrcmtDlvryItemEvtData itemEvtData = itemEvtDataEJB.findById(evtRef.getDlvryItemNbr());
		if(itemEvtData==null) return;

		PrcmtDeliveryEvtData deliveryEvtData = evtDataEJB.findById(evtRef.getDlvryNbr());
		if(deliveryEvtData==null) return;
		
		String artPic = itemEvtData.getArtPic();
		String lotPic = itemEvtData.getLotPic();
		StkArticleLot articleLot = articleLotEJB.findByIdentif(StkAbstractArticleLot.toId(lotPic));
		if(articleLot!=null)return;
		
		StkArticleLot stkArticleLot = new StkArticleLot();
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
		stkArticleLot.setVatPurchPct(itemEvtData.getVatPct());
		stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
		stkArticleLot.setDlvryItemNbr(itemEvtData.getDlvryItemNbr());
		stkArticleLot.setDlvryNbr(itemEvtData.getDlvryNbr());
		articleLotEJB.create(stkArticleLot);
		
		// Lot Stock Qty
		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setArtPic(artPic);
		lotStockQty.setLotPic(lotPic);
		lotStockQty.setQtyDt(deliveryEvt.getHstryDt());
		lotStockQty.setSeqNbr(0);
		lotStockQty.setStockQty(itemEvtData.getQtyDlvrd());
		lotStockQtyEJB.create(lotStockQty);
		

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
		for (PrcmtDlvryItem2StrgSctnEvtData strgSctnEvtData : strgSctnEvtDataList) {
			StkArticleLot2StrgSctn strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(stkArticleLot.getArtPic());
			strgSctn.setLotPic(stkArticleLot.getLotPic());
			strgSctn.setStrgSection(strgSctnEvtData.getStrgSection());
			strgSctn.setQty(BigDecimalUtils.sum(strgSctnEvtData.getStkQtyPreDlvry(), strgSctnEvtData.getQtyStrd()));
			articleLot2StrgSctnEJB.create(strgSctn);
		}
		
		itemEvtDataEJB.deleteById(itemEvtData.getId());
	}

}
