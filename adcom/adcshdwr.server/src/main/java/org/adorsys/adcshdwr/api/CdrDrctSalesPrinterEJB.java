package org.adorsys.adcshdwr.api;

//import java.awt.Desktop;
//import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.DateUtil;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.print.api.PrintMode;
import org.adorsys.adcshdwr.receiptprint.CdrDrctSalesPrinterData;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrintTemplatePDF;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrinterData;
import org.adorsys.adcshdwr.rest.CdrDsArtItemEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
import org.apache.commons.io.IOUtils;


@Stateless
public class CdrDrctSalesPrinterEJB {
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	CdrDsPymntItemEJB cdrDsPymntItemEJB;
	
	@Inject
	private CdrDsArtItemEJB cdrDsArtItemEJB;
	
	
	/**
	 * Printing pdf
	 * @param sales
	 */
	public void printReceiptPdf(CdrDrctSales sales){
		CdrDrctSalesPrinterData drctSalesPrinterData = createCdrDrctSalesPrinterData(sales);
		ReceiptPrinterData receiptPrinterData = createReceiptPrinterData(sales);
		ReceiptPrintTemplatePDF worker = new ReceiptPrintTemplatePDF(receiptPrinterData);
		worker.printPdfReceipt(drctSalesPrinterData);
		
		// Output pdf
		byte[] data =  (byte[]) worker.getPage();
		String pymntDocNbr = worker.getReceiptPrinterData().getPayment().getPymntDocNbr();
		String fileName = pymntDocNbr+".pdf";
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			IOUtils.write(data, fileOutputStream);
			PrintMode printMode = worker.getReceiptPrintMode();
			switch (printMode) {
			case open:
//				 Desktop.getDesktop().open(new File(fileName));
				break;
            case print:
//            	 Desktop.getDesktop().print(new File(fileName));
            	 break;
			default:
				break;
			}
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		finally{
			// What to do
		}
		
	}
	
	
	public CdrDrctSalesPrinterData createCdrDrctSalesPrinterData(CdrDrctSales sales){
		Login connectedUser = securityUtil.getConnectedUser();
		OrgUnit company = securityUtil.getCurrentOrgUnit();
		if(company!=null){
			company.setContact(createSampleOrgContact());
		}else {
			OrgUnit orgUnit = createSampleOrgUnit();
			OrgContact contact = createSampleOrgContact(); 
			orgUnit.setContact(contact);
			company = orgUnit;
		}
		List<CdrDsArtItem> cdrDsArtItems = cdrDsArtItemEJB.findByDsNbr(sales.getDsNbr());
		CdrDsArtItemSearchResult cdrDsArtItemSearchResult = new CdrDsArtItemSearchResult();
		cdrDsArtItemSearchResult.setResultList(cdrDsArtItems);
		CdrDrctSalesPrinterData cdrDrctSalesPrinterData = new CdrDrctSalesPrinterData(sales, connectedUser, company, cdrDsArtItemSearchResult);
	
		return cdrDrctSalesPrinterData;
	}
	
	public ReceiptPrinterData createReceiptPrinterData(CdrDrctSales sales){
		Login cashier = securityUtil.getConnectedUser();
		OrgUnit company = securityUtil.getCurrentOrgUnit();
		if(company!=null){
			company.setContact(createSampleOrgContact());
		}else {
			OrgUnit orgUnit = createSampleOrgUnit();
			OrgContact contact = createSampleOrgContact(); 
			orgUnit.setContact(contact);
			company = orgUnit;
		}
		CdrDsPymntItem cdrDsPymntItem = cdrDsPymntItemEJB.findByDsNbr(sales.getDsNbr()).iterator().next();
		String customer="";
		if(sales.getRcptNbr()=="0000"){
			customer = "CLIENT DIVERS";
		}
		String paymentDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT);
		ReceiptPrinterData receiptPrinterData = new ReceiptPrinterData(customer, paymentDate , cdrDsPymntItem, cashier, company);
		
		return receiptPrinterData;
	}
	
	public OrgUnit createSampleOrgUnit(){
		OrgUnit orgUnit = new OrgUnit();
		orgUnit.setFullName("PHARMACIE ALLIANCE");
		orgUnit.setShortName("PHALLLIANCE");
		orgUnit.setIdentif("RC0000013450");
		orgUnit.setIdentif("RC/00001");
		
		return orgUnit;
	}
	
	public OrgContact createSampleOrgContact(){
		OrgContact orgContact = new OrgContact();
		orgContact.setCountry("CAMEROUN");
		orgContact.setEmail("phalliance@yahoo.fr");
		orgContact.setCity("Douala");
		orgContact.setPhone("+237 22 31 72 56");
		orgContact.setRegion("Littoral");
		orgContact.setStreet("Akwa");
		orgContact.setFax("+237 22 31 72 24");
		orgContact.setContactPeople("DR TCHIENGOU PLUCHERIE");
		orgContact.setOuIdentif("RC/00001");
		orgContact.setContactIndex("MERCI DE VOTRE VISITE, BONNE GUERISON.");
		
		return orgContact;
	}

}
