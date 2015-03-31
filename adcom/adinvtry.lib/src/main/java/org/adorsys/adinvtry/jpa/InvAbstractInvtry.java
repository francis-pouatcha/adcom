package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@MappedSuperclass
@Description("InvInvtry_description")
public abstract class InvAbstractInvtry extends AbstractIdentifData {

	private static final long serialVersionUID = -7418836442555482998L;

	@Column(unique = true)
	@Description("InvInvtry_invtryNbr_description")
	@NotNull
	private String invtryNbr;

	/*
	 * The only user that will be allowed to create inventory items.
	 */
	@Column
	@Description("InvInvtry_acsngUser_description")
	private String acsngUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date acsngDt;

	@Column
	@Description("InvInvtry_gapSaleAmtHT_description")
	private BigDecimal gapSaleAmtHT;

	@Column
	@Description("InvInvtry_gapPurchAmtHT_description")
	private BigDecimal gapPurchAmtHT;

	@Column
	@Description("InvInvtry_invtryStatus_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private InvInvtryStatus invtryStatus;
	
	@Column
	@Description("InvInvtry_invInvtryType_description")
	@Enumerated(EnumType.STRING)
	private InvInvtryType invInvtryType;

	@Column
	@Description("InvInvtry_descptn_description")
	@Size(max = 256, message = "InvInvtry_descptn_Size_validation")
	private String descptn;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_invtryDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date invtryDt;
	
	@Column
	@Description("InvInvtry_rangeStart_description")
	private String rangeStart;
	
	@Column
	@Description("InvInvtry_rangeEnd_description")
	private String rangeEnd;

	@Column
	@Description("InvInvtry_section_description")
	private String section;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_preparedDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date preparedDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_closedDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date closedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_mergedDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date mergedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_mergedDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date postedDate;
	
	/*
	 * Define the group to which this inventory belongs to. IT is necessary to
	 * help select inventory belonging to the same group and compare them and even merge them.
	 */
	@Description("InvInvtry_invtryGroup_description")
	private String invtryGroup;
	
	/*
	 * The identifier of the container inventory. It is the inventory
	 * into which this inventory is merged.
	 */
	@Description("InvInvtry_containerId_description")
	private String containerId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_consistentDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date conflictDt;


	public Date getPreparedDt() {
		return preparedDt;
	}

	public void setPreparedDt(Date preparedDt) {
		this.preparedDt = preparedDt;
	}

	public String getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(String rangeStart) {
		this.rangeStart = rangeStart;
	}

	public String getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public String getInvtryNbr() {
		return this.invtryNbr;
	}

	public void setInvtryNbr(final String invtryNbr) {
		this.invtryNbr = invtryNbr;
	}

	public String getAcsngUser() {
		return this.acsngUser;
	}

	public void setAcsngUser(final String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public Date getAcsngDt() {
		return this.acsngDt;
	}

	public void setAcsngDt(final Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	public BigDecimal getGapSaleAmtHT() {
		return this.gapSaleAmtHT;
	}

	public void setGapSaleAmtHT(final BigDecimal gapSaleAmtHT) {
		this.gapSaleAmtHT = gapSaleAmtHT;
	}

	public BigDecimal getGapPurchAmtHT() {
		return this.gapPurchAmtHT;
	}

	public void setGapPurchAmtHT(final BigDecimal gapPurchAmtHT) {
		this.gapPurchAmtHT = gapPurchAmtHT;
	}

	public String getDescptn() {
		return this.descptn;
	}

	public void setDescptn(final String descptn) {
		this.descptn = descptn;
	}

	public Date getInvtryDt() {
		return this.invtryDt;
	}

	public void setInvtryDt(final Date invtryDt) {
		this.invtryDt = invtryDt;
	}

	public InvInvtryStatus getInvtryStatus() {
		return invtryStatus;
	}

	public void setInvtryStatus(InvInvtryStatus invtryStatus) {
		this.invtryStatus = invtryStatus;
	}

	public InvInvtryType getInvInvtryType() {
		return invInvtryType;
	}

	public void setInvInvtryType(InvInvtryType invInvtryType) {
		this.invInvtryType = invInvtryType;
	}

	@Override
	protected String makeIdentif() {
		return invtryNbr;
	}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getInvtryGroup() {
		return invtryGroup;
	}

	public void setInvtryGroup(String invtryGroup) {
		this.invtryGroup = invtryGroup;
	}

	public Date getConflictDt() {
		return conflictDt;
	}

	public void setConflictDt(Date conflictDt) {
		this.conflictDt = conflictDt;
	}

	public void copyTo(InvAbstractInvtry target) {
		target.invtryNbr = invtryNbr;
		target.acsngUser = acsngUser;
		target.acsngDt = acsngDt;
		target.conflictDt = conflictDt;
		target.gapSaleAmtHT = gapSaleAmtHT;
		target.gapPurchAmtHT = gapPurchAmtHT;
		target.invtryStatus = invtryStatus;
		target.invInvtryType = invInvtryType;
		target.descptn = descptn;
		target.invtryDt = invtryDt;
		target.section = section;
		target.invtryGroup = invtryGroup;
	}

	public void clearAmts() {
		this.gapSaleAmtHT=BigDecimal.ZERO;
		this.gapPurchAmtHT = BigDecimal.ZERO;
	}

	public void addGapSaleAmtHT(BigDecimal gapSaleAmtHT) {
		if(this.gapSaleAmtHT==null)this.gapSaleAmtHT=BigDecimal.ZERO;
		this.gapSaleAmtHT = this.gapSaleAmtHT.add(gapSaleAmtHT);
	}

	public void addGapPurchAmtHT(BigDecimal gapPurchAmtHT) {
		if(this.gapPurchAmtHT==null)this.gapPurchAmtHT=BigDecimal.ZERO;
		this.gapPurchAmtHT = this.gapPurchAmtHT.add(gapPurchAmtHT);
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Date getMergedDate() {
		return mergedDate;
	}

	public void setMergedDate(Date mergedDate) {
		this.mergedDate = mergedDate;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	
}
