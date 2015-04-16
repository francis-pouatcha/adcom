package org.adorsys.adsales.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.jpa.CurrencyEnum;
import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.rest.SlsInvceHistoryEJB;
import org.adorsys.adsales.rest.SlsInvceItemEJB;
import org.adorsys.adsales.rest.SlsInvcePtnrEJB;
import org.adorsys.adsales.rest.SlsInvoiceEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SlsInvoiceManager {

	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private SlsInvoiceEJB slsInvoiceEJB;
	@Inject
	private SlsInvceHistoryEJB slsInvceHistoryEJB;
	@Inject
	private SlsInvceItemEJB slsInvceItemEJB;
	@Inject
	private SlsInvcePtnrEJB slsInvcePtnrEJB;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public SlsInvoiceHolder processInvoice(SlsInvoiceHolder invoiceHolder) {
		
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		SlsInvoice slsInvoice = invoiceHolder.getSlsInvoice();		
		slsInvoice = createinvoiceObject(slsInvoice, currentLoginName, now);
		
		invoiceHolder.setSlsInvoice(slsInvoice);
		boolean modified = false;
			
		modified |= synchslsInvcePtnr(invoiceHolder);
		
		boolean itemModified = deleteHolders(invoiceHolder);
		
		List<SlsInvceItemHolder> slsInvceItemsholder = invoiceHolder.getSlsInvceItemsholder();
		if(slsInvceItemsholder==null) slsInvceItemsholder=new ArrayList<SlsInvceItemHolder>();
		
		for (SlsInvceItemHolder itemHolder : slsInvceItemsholder) {
			SlsInvceItem invceItem = itemHolder.getSlsInvceItem();
			
			if(StringUtils.isBlank(invceItem.getLotPic())) continue;
			
			if(StringUtils.isBlank(invceItem.getInvNbr()))
				invceItem.setInvNbr(slsInvoice.getInvceNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(invceItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(invceItem.getId())){
				// todo check mdified
				   SlsInvceItem persInvce = slsInvceItemEJB.findById(invceItem.getId());
				if(persInvce==null) throw new IllegalStateException("Missing slsInvoice item with id: " + invceItem.getId());
				if(!invceItem.contentEquals(persInvce)){
					invceItem.copyTo(persInvce);
					invceItem.evlte();
					invceItem = slsInvceItemEJB.update(persInvce);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(invceItem.getInvNbr())) {
					SlsInvceItem persInvce = slsInvceItemEJB.findById(invceItem.getInvNbr());
					if(persInvce!=null){
						if(!invceItem.contentEquals(persInvce)){
							invceItem.copyTo(persInvce);
							invceItem.evlte();
							invceItem = slsInvceItemEJB.update(persInvce);
							itemModified = true;
						}
					} else {
						invceItem.evlte();
						invceItem.setInvNbr(slsInvoice.getInvceNbr());
						invceItem = slsInvceItemEJB.create(invceItem);
						itemModified = true;
					}
				} else {
					invceItem.evlte();
					invceItem = slsInvceItemEJB.create(invceItem);
					itemModified = true;
				}
			}
			itemHolder.setSlsInvceItem(invceItem);
		}
		if(itemModified){
			recomputeslsInvoice(slsInvoice);
			slsInvoice.setInvceStatus(BaseProcessStatusEnum.ONGOING.name());
			slsInvoice = slsInvoiceEJB.update(slsInvoice);
			invoiceHolder.setSlsInvoice(slsInvoice);		
		}
		if(modified || itemModified){
			createModifiedslsInvoiceHistory(slsInvoice);
		}
		return invoiceHolder;
	}

	private void createModifiedslsInvoiceHistory(SlsInvoice slsInvoice) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsInvceHistory invoiceHstry = new SlsInvceHistory();
		invoiceHstry.setComment(BaseHistoryTypeEnum.MODIFIED.name());
		invoiceHstry.setAddtnlInfo(SlsInvoiceInfo.prinInfo(slsInvoice));
		invoiceHstry.setEntIdentif(slsInvoice.getId());
		invoiceHstry.setEntStatus(slsInvoice.getInvceStatus());
		invoiceHstry.setHstryDt(new Date());
		invoiceHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());	
		invoiceHstry.setOrignLogin(callerPrincipal.getName());
		invoiceHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invoiceHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		invoiceHstry.makeHistoryId(true);
		slsInvceHistoryEJB.create(invoiceHstry);
	}

	private void recomputeslsInvoice(final SlsInvoice slsInvoice) {
		String InvNbr = slsInvoice.getInvceNbr();
		Long count = slsInvceItemEJB.countByInvceNbr(InvNbr);
		int start = 0;
		int max = 100;
		slsInvoice.clearAmts();
		while(start<=count){
			List<SlsInvceItem> list = slsInvceItemEJB.findInvceNbr(InvNbr, start, max);
			start +=max;
			for (SlsInvceItem slsInvceItem : list) {
				slsInvoice.addGrossSPPreTax(slsInvceItem.getGrossSPPreTax());
				slsInvoice.addRebate(slsInvceItem.getRebate());
				slsInvoice.addNetSPPreTax(slsInvceItem.getNetSPPreTax());
				slsInvoice.addVatAmount(slsInvceItem.getVatAmount());
				slsInvoice.addNetSPTaxIncl(slsInvceItem.getNetSPTaxIncl());
			}
		}
		slsInvoice.evlte();
	}

	private boolean deleteHolders(SlsInvoiceHolder invoiceHolder) {
		List<SlsInvceItemHolder> invceItems = invoiceHolder.getSlsInvceItemsholder();
		List<SlsInvceItemHolder> soiToRemove = new ArrayList<SlsInvceItemHolder>();
		boolean modified = false;
		for (SlsInvceItemHolder itemHolder : invceItems) {
			if(itemHolder.isDeleted()){
				SlsInvceItem invceItem = itemHolder.getSlsInvceItem();
				String id = StringUtils.isNotBlank(invceItem.getId())?invceItem.getId():invceItem.getInvNbr();
				if(StringUtils.isNotBlank(id)){
					slsInvceItemEJB.deleteById(id);
					modified = true;					
				}
				soiToRemove.add(itemHolder);
			}
		}
		invceItems.removeAll(soiToRemove);
		return modified;
	}

	private boolean synchslsInvcePtnr(SlsInvoiceHolder invoiceHolder) {
		SlsInvoice Invoice = invoiceHolder.getSlsInvoice();
		String InvNbr = Invoice.getInvceNbr();
		boolean modified = false;
		
		List<SlsInvcePtnrHolder> invcePtnrs = invoiceHolder.getSlsInvcePtnrsHolder();
		List<SlsInvcePtnrHolder> invcePtnrsToRemove = new ArrayList<SlsInvcePtnrHolder>();
		if(!invcePtnrs.isEmpty()){
			for (SlsInvcePtnrHolder invcePtnrHolder : invcePtnrs) {
				SlsInvcePtnr invcePtnrPersi = slsInvcePtnrEJB.findPtnr(InvNbr,invcePtnrHolder.getSlsInvcePtnr().getPtnrNbr(),invcePtnrHolder.getSlsInvcePtnr().getRoleInInvce());
				if(invcePtnrHolder.isDeleted()){
					if(invcePtnrPersi!=null){
						slsInvcePtnrEJB.deleteById(invcePtnrPersi.getId());
						invcePtnrsToRemove.add(invcePtnrHolder);
						modified = true;
					}
					continue;
				}
				if(invcePtnrPersi==null){
					invcePtnrPersi = slsInvcePtnrEJB.addPtnr(Invoice, invcePtnrHolder.getSlsInvcePtnr().getPtnrNbr(), invcePtnrHolder.getSlsInvcePtnr().getRoleInInvce());
					invcePtnrHolder.setSlsInvcePtnr(invcePtnrPersi);
					modified = true;
				} else {
					SlsInvcePtnr invcePtnr = invcePtnrHolder.getSlsInvcePtnr();
					if(!invcePtnr.contentEquals(invcePtnrPersi)){
						invcePtnr.copyTo(invcePtnrPersi);
						invcePtnrPersi.setId(invcePtnr.getId());
						invcePtnrPersi = slsInvcePtnrEJB.update(invcePtnrPersi);
						invcePtnrHolder.setSlsInvcePtnr(invcePtnrPersi);
						modified = true;
					}
				}
			}
		}
		invcePtnrs.removeAll(invcePtnrsToRemove);
		return modified;
	}

	private SlsInvoice createinvoiceObject(SlsInvoice slsInvoice,
			String currentLoginName, Date now) {
	
		if(StringUtils.isBlank(slsInvoice.getId())){
			slsInvoice.setCreatingUsr(currentLoginName);
			if(slsInvoice.getInvceDt()==null) slsInvoice.setInvceDt(now);
			slsInvoice.setInvceStatus(BaseProcessStatusEnum.ONGOING.name());
			if(slsInvoice.getInvceCur()==null)slsInvoice.setInvceCur(CurrencyEnum.XAF.name());
			slsInvoice = slsInvoiceEJB.create(slsInvoice);
			createInitialinvoiceHistory(slsInvoice);
		}else{
			slsInvoice = slsInvoiceEJB.findById(slsInvoice.getId());
		}
		return slsInvoice;
	}

	private void createInitialinvoiceHistory(SlsInvoice slsInvoice) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsInvceHistory invoiceHstry = new SlsInvceHistory();
		invoiceHstry.setComment(BaseHistoryTypeEnum.INITIATED.name());
		invoiceHstry.setAddtnlInfo(SlsInvoiceInfo.prinInfo(slsInvoice));
		invoiceHstry.setEntIdentif(slsInvoice.getId());
		invoiceHstry.setEntStatus(slsInvoice.getInvceStatus());
		invoiceHstry.setHstryDt(new Date());
		invoiceHstry.setHstryType(BaseHistoryTypeEnum.INITIATED.name());
		
		invoiceHstry.setOrignLogin(callerPrincipal.getName());
		invoiceHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invoiceHstry.setProcStep(BaseProcStepEnum.INITIATING.name());
		invoiceHstry.makeHistoryId(true);
		slsInvceHistoryEJB.create(invoiceHstry);
	}

	public SlsInvoiceHolder findInvoice(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
