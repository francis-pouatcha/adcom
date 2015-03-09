package org.adorsys.adstock.recptcls;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.adorsys.adinvtry.rest.InvInvtryEvtDataEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEvtDataEJB;
import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkInvtryItemHstry;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkInvtryItemHstryEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;

/**
 * Check for the incoming of inventory closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkInvtryItemEvtProcessor {
	@Inject
	private InvInvtryEvtDataEJB evtDataEJB;
	@Inject
	private InvInvtryItemEvtDataEJB itemEvtDataEJB;
	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;
	@Inject
	private StkInvtryItemHstryEJB invtryItemHstryEJB;
	@Inject
	private StkArticleLotEJB articleLotEJB;

	public void process(String itemEvtDataId, InvInvtryEvt invtryEvt) {
		InvInvtryItemEvtData itemEvtData = itemEvtDataEJB.findById(itemEvtDataId);
		if(itemEvtData==null) return;

		InvInvtryEvtData invtryEvtData = evtDataEJB.findById(itemEvtData.getInvtryNbr());
		if(invtryEvtData==null) return;

		StkInvtryItemHstry hstry = invtryItemHstryEJB.findById(itemEvtData.getIdentif());
		if(hstry!=null) return;// processed.
		
		String artPic = itemEvtData.getArtPic();
		String lotPic = itemEvtData.getLotPic();
		String section = itemEvtData.getSection();
		
		StkArticleLot stkArticleLot = articleLotEJB.findByIdentif(StkAbstractArticleLot.toId(lotPic));
		if(stkArticleLot==null){
			stkArticleLot = new StkArticleLot();
			stkArticleLot.setArtPic(artPic);
			stkArticleLot.setExpirDt(itemEvtData.getExpirDt());
			stkArticleLot.setLotPic(lotPic);
			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
			stkArticleLot.setPppuHT(itemEvtData.getPppuPT());
			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
			stkArticleLot.setSppuHT(itemEvtData.getSppuPT());
			stkArticleLot.setStkgDt(invtryEvt.getHstryDt());
			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
			stkArticleLot.setSupplier(itemEvtData.getSupplier());
			stkArticleLot.setVatPurchPct(itemEvtData.getVatPurchPct());
			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.create(stkArticleLot);
		} else {
			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
			stkArticleLot.setPppuHT(itemEvtData.getPppuPT());
			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
			stkArticleLot.setSppuHT(itemEvtData.getSppuPT());
			stkArticleLot.setStkgDt(invtryEvt.getHstryDt());
			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
			stkArticleLot.setSupplier(itemEvtData.getSupplier());
			stkArticleLot.setVatPurchPct(itemEvtData.getVatPurchPct());
			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.update(stkArticleLot);
		}		
		
		StkArticleLot2StrgSctn strgSctn = articleLot2StrgSctnEJB.findByStrgSectionAndLotPicAndArtPic(section, lotPic, artPic);
		if(strgSctn==null){
			strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(artPic);
			strgSctn.setLotPic(lotPic);
			strgSctn.setStrgSection(section);
			strgSctn = articleLot2StrgSctnEJB.create(strgSctn);
		}

		StkLotStockQty latestQty = lotStockQtyEJB.findLatestQty(artPic, lotPic, section);
		// Lot Stock Qty
		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setArtPic(artPic);
		lotStockQty.setLotPic(lotPic);
		lotStockQty.setSection(section);
		lotStockQty.setQtyDt(invtryEvt.getHstryDt());
		if(latestQty!=null){
			lotStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			lotStockQty.setSeqNbr(0);
		}
		lotStockQty.setStockQty(BigDecimalUtils.negate(itemEvtData.getGap()));
		lotStockQty = lotStockQtyEJB.create(lotStockQty);
		
		hstry = new StkInvtryItemHstry();
		invtryEvt.copyTo(hstry);
		hstry.setId(itemEvtData.getIdentif());
		hstry.setEntIdentif(itemEvtData.getIdentif());
		hstry.setAddtnlInfo("Inventory Gap : " + lotStockQty.getStockQty());
		hstry.setComment(lotStockQty.artPicAndLotPicAndSection());
		invtryItemHstryEJB.create(hstry);
	}

}
