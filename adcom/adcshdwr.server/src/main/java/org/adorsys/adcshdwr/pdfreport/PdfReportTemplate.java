package org.adorsys.adcshdwr.pdfreport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.DateUtil;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * @author guymoyo
 * @param <T>
 *
 */
@Stateless
public class PdfReportTemplate<T> {
	
	@Inject
	SecurityUtil securityUtil;
	@Inject
	PdfUtil<T> pdfUtil;
	
	private DateUtil dateUtil;

	/** The document. */
	private Document document;
	
	/** The report table. */
	private PdfPTable reportTable;
	
	/** The file. */
	private File file ;
	
	private String username;
	
	/** The user date. */
	private Date userDate ;
	
	private  ByteArrayOutputStream baos ;
	
	private List<String> fieldsName;
	
	private String klassName;
	

	/** The bold font. */
	static Font boldFont = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
	
	/** The font. */
	static Font font = FontFactory.getFont("Times-Roman", 10);
	
	public PdfReportTemplate() {
	}

	/**
	 * Instantiates a new locality report pdf.
	 * 
	 * @param username
	 *            the username
	 * @param userDate
	 *            the user date
	 * @throws DocumentException
	 *             the document exception
	 */
	public void setup (Class<T> entityKlass) throws DocumentException {
		this.username = securityUtil.getCurrentLoginName();
		this.userDate = new Date() ;
		
		pdfUtil.setEntity(entityKlass);
		fieldsName = pdfUtil.getFieldsName();
		klassName = pdfUtil.getKlassName();

		document = new Document(PageSize.A4.rotate(),5,5,5,5);
		try {
			baos = new ByteArrayOutputStream();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		try {
			PdfWriter.getInstance(document, baos);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
		resetDocument();
	}

	/**
	 * Adds the items.
	 * @param <T>
	 * 
	 * @param items
	 *            the items
	 */
	public void addItems(List<T> items) {
		List<String> values;
		for(T entity:items){
			values = pdfUtil.fieldValues(entity);
			newTableRow(values);
		}
	}
	
	/**
	 * main method to call, when you wan to build a pdfReport
	 * @param items
	 * @param entityKlass
	 * @throws DocumentException
	 */
	public ByteArrayOutputStream build(List<T> items, Class<T> entityKlass) throws DocumentException{
		setup(entityKlass);
		addItems(items);
		closeDocument();
		return baos;
	}

	/**
	 * Convert a date to a string, for display. This method handle the cas when the date is null, and return a "-" sign.
	 * 
	 * @param validFrom
	 * @param pattern
	 * @return
	 */
	
	/**
	 * Add new row to the table
	 * @param agentId
	 * @param countryISO3
	 * @param language
	 * @param city
	 * @param localityStr
	 * @param timeZone
	 * @param validFrom
	 * @param validTo
	 */
	private void newTableRow(List<String> fieldsValue){
		PdfPCell pdfPCell;
		for(String value:fieldsValue){	
			 pdfPCell = new PdfPCell();
			pdfPCell.setFixedHeight(20f);
			pdfPCell.addElement(new Phrase(value,font));
			reportTable.addCell(pdfPCell);
		}
	}

	/**
	 * Fill table haeder.
	 * 
	 * @throws DocumentException
	 *             the document exception
	 */
	private void fillTableHaeder() throws DocumentException {
		int size = fieldsName.size();
	
		reportTable = new PdfPTable(size);
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);
		
		PdfPCell pdfPCell;
		for(String fieldName:fieldsName){		
			pdfPCell = new PdfPCell();
			pdfPCell.setFixedHeight(23f);
			pdfPCell.addElement(new Phrase(fieldName,boldFont));
			reportTable.addCell(pdfPCell);
		}
	}

	/**
	 * Prints the report header.
	 * 
	 * @throws DocumentException
	 *             the document exception
	 */
	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new Phrase("LISTE "+klassName,boldFont));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new Phrase("Imprime Le  : "+dateUtil.format(userDate,dateUtil.DATE_FORMAT_SHORT))+" Par : "+username);
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
	}



	/**
	 * The Class StandardText.
	 */
	static class StandardText extends Phrase{
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -5796192414147292471L;
		
		/**
		 * Instantiates a new standard text.
		 */
		StandardText() {
			super();
			setFont(font);
		}
		
		/**
		 * Instantiates a new standard text.
		 * 
		 * @param text
		 *            the text
		 */
		StandardText(String text) {
			super(text);
			setFont(font);
		}
	}

	/**
	 * The Class BoldText.
	 */
	static class BoldText extends Phrase {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -6569891897489003768L;
		
		/**
		 * Instantiates a new bold text.
		 */
		BoldText() {
			super();
			setFont(boldFont);
		}
		
		/**
		 * Instantiates a new bold text.
		 * 
		 * @param text
		 *            the text
		 */
		BoldText(String text) {
			super(text);
			setFont(boldFont);
		}
	}

	/**
	 * The Class RightParagraph.
	 */
	static class RightParagraph extends Paragraph {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 986392503142787342L;

		/**
		 * Instantiates a new right paragraph.
		 * 
		 * @param phrase
		 *            the phrase
		 */
		public RightParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_RIGHT);
		}

		/**
		 * Instantiates a new right paragraph.
		 * 
		 * @param string
		 *            the string
		 */
		public RightParagraph(String string) {
			this(new Phrase(string));
		}
	}

	/**
	 * Close document.
	 */
	public void closeDocument() {
		try {
			document.add(reportTable);
			document.close();
			baos.close();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	
	public ByteArrayOutputStream getBaos() {
		return baos;
	}

	public void setBaos(ByteArrayOutputStream baos) {
		this.baos = baos;
	}

	/**
	 * Reset document.
	 * 
	 * @throws DocumentException
	 *             the document exception
	 */
	public void resetDocument() throws DocumentException{
		document.open();
		if(reportTable!=null)
			reportTable.getRows().clear();
		printReportHeader();
		fillTableHaeder();
	}

	/**
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 * 
	 * @param file
	 *            the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
}
