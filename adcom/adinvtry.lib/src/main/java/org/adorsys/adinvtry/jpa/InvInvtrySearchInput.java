package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class InvInvtrySearchInput
{

	/**
	 * The entity holding search inputs.
	 */
	private InvInvtry entity;

	/**
	 * The start cursor
	 */
	private int start = -1;

	/**
	 * The max number of records to return.
	 */
	private int max = -1;

	/**
	 * The field names to be included in the search.
	 */
	private List<String> fieldNames = new ArrayList<String>();

	private Date invtryDtFrom;

	private Date invtryDtTo;
	
	private Date acsngDtFrom;
	
	private Date acsngDtTo;
	
	private BigDecimal gapPurchAmtHTFrom;
	
	private BigDecimal gapPurchAmtHTTo;

	private List<String> invrtyNbrs = new ArrayList<String>();

	public InvInvtry getEntity()
	{
		return entity;
	}

	public void setEntity(InvInvtry entity)
	{
		this.entity = entity;
	}

	public List<String> getFieldNames()
	{
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames)
	{
		this.fieldNames = fieldNames;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
	}

	public Date getInvtryDtFrom() {
		return invtryDtFrom;
	}

	public void setInvtryDtFrom(Date invtryDtFrom) {
		this.invtryDtFrom = invtryDtFrom;
	}

	public Date getInvtryDtTo() {
		return invtryDtTo;
	}

	public void setInvtryDtTo(Date invtryDtTo) {
		this.invtryDtTo = invtryDtTo;
	}

	public Date getAcsngDtFrom() {
		return acsngDtFrom;
	}

	public void setAcsngDtFrom(Date acsngDtFrom) {
		this.acsngDtFrom = acsngDtFrom;
	}

	public Date getAcsngDtTo() {
		return acsngDtTo;
	}

	public void setAcsngDtTo(Date acsngDtTo) {
		this.acsngDtTo = acsngDtTo;
	}

	public BigDecimal getGapPurchAmtHTFrom() {
		return gapPurchAmtHTFrom;
	}

	public void setGapPurchAmtHTFrom(BigDecimal gapPurchAmtHTFrom) {
		this.gapPurchAmtHTFrom = gapPurchAmtHTFrom;
	}

	public BigDecimal getGapPurchAmtHTTo() {
		return gapPurchAmtHTTo;
	}

	public void setGapPurchAmtHTTo(BigDecimal gapPurchAmtHTTo) {
		this.gapPurchAmtHTTo = gapPurchAmtHTTo;
	}
	public boolean noSpecialParams(){
		return invtryDtFrom==null && invtryDtTo==null && acsngDtFrom==null && acsngDtTo==null && gapPurchAmtHTFrom==null && gapPurchAmtHTTo==null;
	}

	public List<String> getInvrtyNbrs() {
		return invrtyNbrs;
	}

	public void setInvrtyNbrs(List<String> invrtyNbrs) {
		this.invrtyNbrs = invrtyNbrs;
	}
}
