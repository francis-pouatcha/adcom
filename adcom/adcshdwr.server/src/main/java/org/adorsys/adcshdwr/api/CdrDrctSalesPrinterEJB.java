package org.adorsys.adcshdwr.api;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.print.PrintService;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.DateUtil;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
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
import org.adorsys.adcshdwr.voucherprint.CdrCstmrVchrPrinterData;
import org.adorsys.adcshdwr.voucherprint.VoucherPrintTemplatePdf;
import org.adorsys.adcshdwr.voucherprint.VoucherprinterData;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;


@Stateless
public class CdrDrctSalesPrinterEJB {
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	CdrDsPymntItemEJB cdrDsPymntItemEJB;
	
	@Inject
	private CdrDsArtItemEJB cdrDsArtItemEJB;
	
	
	/**
	 * Printing Receipt pdf
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
		PDDocument document;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			IOUtils.write(data, fileOutputStream);
			File file = new File(fileName);
			PrintMode printMode = worker.getReceiptPrintMode();
			document = PDDocument.load(file);
			switch (printMode) {
			/* case open:
				try {
					document.print();
				} catch (PrinterException e) {
					throw new IllegalArgumentException();
				}
				document.close();
				break;
            /* case print:
            	try {
					document.silentPrint();
				} catch (PrinterException e) {
					throw new IllegalArgumentException();
				}
            	document.close();
            	break; */
			default:
				break;
			}
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		finally{
			//
		}
		
	}
	
//	
//	public void getPrinterName(){
//		try {
//			PrintService[] printServices = PrinterJob.lookupPrintServices();
//			if(printServices.length!=0){
//				for (int i = 0; i < printServices.length; i++) {
//					@SuppressWarnings("unused")
//					String name = printServices[i].getName();
//				}
//			}
//		} catch (Exception e) {
//		  throw new IllegalArgumentException(); 
//		}
//	}
	
	/**
	 * Print Voucher pdf
	 */
	public VoucherPrintTemplatePdf printVoucherPdf(CdrCstmrVchr  cdrCstmrVchr){
		
		CdrCstmrVchrPrinterData cstmrVchrPrinterData = createCstmrVchrPrinterData(cdrCstmrVchr);
		VoucherprinterData vchrPrinterData = createVchrPrinterData(cdrCstmrVchr);
		VoucherPrintTemplatePdf worker = new VoucherPrintTemplatePdf(vchrPrinterData);
		worker.printPdfVoucher(cstmrVchrPrinterData);
	    return worker;
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
	
	
	public CdrCstmrVchrPrinterData createCstmrVchrPrinterData(CdrCstmrVchr  cdrCstmrVchr){
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
		CdrCstmrVchrPrinterData cdrCstmrVchrPrinterData = new CdrCstmrVchrPrinterData(cdrCstmrVchr, connectedUser, company);
		return cdrCstmrVchrPrinterData;
	}
	
	
	public VoucherprinterData createVchrPrinterData(CdrCstmrVchr  cdrCstmrVchr){
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
	    String customer = StringUtils.isBlank(cdrCstmrVchr.getCstmrName())?"CLIENTS DIVERS":cdrCstmrVchr.getCstmrName();
		VoucherprinterData voucherprinterData = new VoucherprinterData(customer, DateUtil.format(cdrCstmrVchr.getPrntDt(), DateUtil.DATE_TIME_FORMAT), connectedUser, company);
		return voucherprinterData;
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
