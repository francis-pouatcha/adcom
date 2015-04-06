package org.adorsys.adsales.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adsales.jpa.SlsSOHstry;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.rest.SlsSOHstryEJB;
import org.adorsys.adsales.rest.SlsSOItemEJB;
import org.adorsys.adsales.rest.SlsSOPtnrEJB;
import org.adorsys.adsales.rest.SlsSalesOrderEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SaleOrderManager {

	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private SlsSalesOrderEJB slsSalesOrderEJB;
	@Inject
	private SlsSOHstryEJB slsSOHstryEJB;
	@Inject
	private SlsSOItemEJB slsSOItemEJB;
	@Inject
	private SlsSOPtnrEJB slsSOPtnrEJB;
	
	
	public SlsSalesOrderHolder doSale(SlsSalesOrderHolder saleOrderHolder) {
		
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		SlsSalesOrder slsSalesOrder = saleOrderHolder.getSlsSalesOrder();		
		slsSalesOrder = createSaleOrderObject(slsSalesOrder, currentLoginName, now);
		
		saleOrderHolder.setSlsSalesOrder(slsSalesOrder);
		boolean modified = false;
			
		modified |= synchSlsSOPtnr(saleOrderHolder);
		
		boolean itemModified = deleteHolders(saleOrderHolder);
		
		List<SlsSOItemHolder> slsSOItemsholder = saleOrderHolder.getSlsSOItemsholder();
		if(slsSOItemsholder==null) slsSOItemsholder=new ArrayList<SlsSOItemHolder>();
		
		for (SlsSOItemHolder itemHolder : slsSOItemsholder) {
			SlsSOItem soItem = itemHolder.getSlsSOItem();

			if(StringUtils.isBlank(soItem.getSoNbr()))
				soItem.setSoNbr(slsSalesOrder.getSoNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(soItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(soItem.getId())){
				// todo check mdified
				  SlsSOItem persSo = slsSOItemEJB.findById(soItem.getId());
				if(persSo==null) throw new IllegalStateException("Missing slsSalesOrder item with id: " + soItem.getId());
				if(!soItem.contentEquals(persSo)){
					soItem.copyTo(persSo);
					soItem.evlte();
					soItem = slsSOItemEJB.update(persSo);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(soItem.getSoNbr())) {
					SlsSOItem persSo = slsSOItemEJB.findById(soItem.getSoNbr());
					if(persSo!=null){
						if(!soItem.contentEquals(persSo)){
							soItem.copyTo(persSo);
							soItem.evlte();
							soItem = slsSOItemEJB.update(persSo);
							itemModified = true;
						}
					} else {
						soItem.evlte();
						soItem.setSoNbr(slsSalesOrder.getSoNbr());
						soItem = slsSOItemEJB.create(soItem);
						itemModified = true;
					}
				} else {
					soItem.evlte();
					soItem = slsSOItemEJB.create(soItem);
					itemModified = true;
				}
			}
			itemHolder.setSlsSOItem(soItem);
		}
		if(itemModified){
			recomputeslsSalesOrder(slsSalesOrder);
			slsSalesOrder.setSoStatus(BaseProcessStatusEnum.ONGOING);
			slsSalesOrder = slsSalesOrderEJB.update(slsSalesOrder);
			saleOrderHolder.setSlsSalesOrder(slsSalesOrder);		
		}
		if(modified || itemModified){
			createModifiedslsSalesOrderHistory(slsSalesOrder);
		}
		return saleOrderHolder;
	}

	private void createModifiedslsSalesOrderHistory(SlsSalesOrder slsSalesOrder) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsSOHstry saleOrderHstry = new SlsSOHstry();
		saleOrderHstry.setComment(BaseHistoryTypeEnum.MODIFIED.name());
		saleOrderHstry.setAddtnlInfo(SaleOrderInfo.prinInfo(slsSalesOrder));
		saleOrderHstry.setEntIdentif(slsSalesOrder.getId());
		saleOrderHstry.setEntStatus(slsSalesOrder.getSoStatus().name());
		saleOrderHstry.setHstryDt(new Date());
		saleOrderHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());	
		saleOrderHstry.setOrignLogin(callerPrincipal.getName());
		saleOrderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		saleOrderHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		saleOrderHstry.makeHistoryId(true);
		slsSOHstryEJB.create(saleOrderHstry);
	}

	private void recomputeslsSalesOrder(final SlsSalesOrder slsSalesOrder) {
		String soNbr = slsSalesOrder.getSoNbr();
		Long count = slsSOItemEJB.countBySoNbr(soNbr);
		int start = 0;
		int max = 100;
		slsSalesOrder.clearAmts();
		while(start<=count){
			List<SlsSOItem> list = slsSOItemEJB.findSoNbr(soNbr, start, max);
			start +=max;
			for (SlsSOItem slsSOItem : list) {
				slsSalesOrder.addGrossSPPreTax(slsSOItem.getGrossSPPreTax());
				slsSalesOrder.addRebate(slsSOItem.getRebate());
				slsSalesOrder.addNetSPPreTax(slsSOItem.getNetSPPreTax());
				slsSalesOrder.addVatAmount(slsSOItem.getVatAmount());
				slsSalesOrder.addNetSPTaxIncl(slsSOItem.getNetSPTaxIncl());
			}
		}
		slsSalesOrder.evlte();
	}

	private boolean deleteHolders(SlsSalesOrderHolder saleOrderHolder) {
		List<SlsSOItemHolder> soItems = saleOrderHolder.getSlsSOItemsholder();
		List<SlsSOItemHolder> soiToRemove = new ArrayList<SlsSOItemHolder>();
		boolean modified = false;
		for (SlsSOItemHolder itemHolder : soItems) {
			if(itemHolder.isDeleted()){
				SlsSOItem soItem = itemHolder.getSlsSOItem();
				String id = StringUtils.isNotBlank(soItem.getId())?soItem.getId():soItem.getSoNbr();
				if(StringUtils.isNotBlank(id)){
					slsSOItemEJB.deleteById(id);
					modified = true;					
				}
				soiToRemove.add(itemHolder);
			}
		}
		soItems.removeAll(soiToRemove);
		return modified;
	}

	private boolean synchSlsSOPtnr(SlsSalesOrderHolder saleOrderHolder) {
		SlsSalesOrder salesOrder = saleOrderHolder.getSlsSalesOrder();
		String soNbr = salesOrder.getSoNbr();
		boolean modified = false;
		
		List<SlsSOPtnrHolder> soPtnrs = saleOrderHolder.getSlsSOPtnrsHolder();
		List<SlsSOPtnrHolder> soPtnrsToRemove = new ArrayList<SlsSOPtnrHolder>();
		if(!soPtnrs.isEmpty()){
			for (SlsSOPtnrHolder soPtnrHolder : soPtnrs) {
				SlsSOPtnr soPtnrPersi = slsSOPtnrEJB.findPtnr(soNbr,soPtnrHolder.getSlsSoPtnr().getPtnrNbr(),soPtnrHolder.getSlsSoPtnr().getRoleInSO());
				if(soPtnrHolder.isDeleted()){
					if(soPtnrPersi!=null){
						slsSOPtnrEJB.deleteById(soPtnrPersi.getId());
						soPtnrsToRemove.add(soPtnrHolder);
						modified = true;
					}
					continue;
				}
				if(soPtnrPersi==null){
					soPtnrPersi = slsSOPtnrEJB.addPtnr(salesOrder, soPtnrHolder.getSlsSoPtnr().getPtnrNbr(), soPtnrHolder.getSlsSoPtnr().getRoleInSO());
					soPtnrHolder.setSlsSoPtnr(soPtnrPersi);
					modified = true;
				} else {
					SlsSOPtnr soPtnr = soPtnrHolder.getSlsSoPtnr();
					if(!soPtnr.contentEquals(soPtnrPersi)){
						soPtnr.copyTo(soPtnrPersi);
						soPtnrPersi.setId(soPtnr.getId());
						soPtnrPersi = slsSOPtnrEJB.update(soPtnrPersi);
						soPtnrHolder.setSlsSoPtnr(soPtnrPersi);
						modified = true;
					}
				}
			}
		}
		soPtnrs.removeAll(soPtnrsToRemove);
		return modified;
	}

	private SlsSalesOrder createSaleOrderObject(SlsSalesOrder slsSalesOrder,
			String currentLoginName, Date now) {
	
		if(StringUtils.isBlank(slsSalesOrder.getId())){
			slsSalesOrder.setAcsngUser(currentLoginName);
			slsSalesOrder.setAcsngDt(now);
			if(slsSalesOrder.getSoDt()==null) slsSalesOrder.setSoDt(now);
			slsSalesOrder.setSoStatus(BaseProcessStatusEnum.ONGOING);
			slsSalesOrder = slsSalesOrderEJB.create(slsSalesOrder);
			createInitialSaleOrderHistory(slsSalesOrder);
		}else{
			slsSalesOrder = slsSalesOrderEJB.findById(slsSalesOrder.getId());
		}
		return slsSalesOrder;
	}

	private void createInitialSaleOrderHistory(SlsSalesOrder slsSalesOrder) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsSOHstry saleOrderHstry = new SlsSOHstry();
		saleOrderHstry.setComment(BaseHistoryTypeEnum.INITIATED.name());
		saleOrderHstry.setAddtnlInfo(SaleOrderInfo.prinInfo(slsSalesOrder));
		saleOrderHstry.setEntIdentif(slsSalesOrder.getId());
		saleOrderHstry.setEntStatus(slsSalesOrder.getSoStatus().name());
		saleOrderHstry.setHstryDt(new Date());
		saleOrderHstry.setHstryType(BaseHistoryTypeEnum.INITIATED.name());
		
		saleOrderHstry.setOrignLogin(callerPrincipal.getName());
		saleOrderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		saleOrderHstry.setProcStep(BaseProcStepEnum.INITIATING.name());
		saleOrderHstry.makeHistoryId(true);
		slsSOHstryEJB.create(saleOrderHstry);
	}

	public SlsSalesOrderHolder findSaleOrder(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
