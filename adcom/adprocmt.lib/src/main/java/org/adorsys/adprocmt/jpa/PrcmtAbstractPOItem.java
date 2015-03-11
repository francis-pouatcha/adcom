package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.adcore.jpa.AmtOrPct;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("PrcmtPOItem_description")
public abstract class PrcmtAbstractPOItem extends AbstractMvmtData {

	private static final long serialVersionUID = 2165286732868397835L;

	@Column
	@Description("PrcmtPOItem_poNbr_description")
	@NotNull
	private String poNbr;

	@Column
	@Description("PrcmtPOItem_artPic_description")
	@NotNull
	private String artPic;
	
	/*
	 * this field is use to carry article name
	 */
	@Column
	@Description("PrcmtDlvryItem_artName_description")
	private String artName;


	@Column
	@Description("PrcmtPOItem_supplier_description")
	private String supplier;

	@Column
	@Description("PrcmtPOItem_qtyOrdered_description")
	@NotNull
	private BigDecimal qtyOrdered;

	@Column
	@Description("PrcmtPOItem_freeQty_description")
	private BigDecimal freeQty;

	/*
	 * This is the receiving organization unit. The same aticle lot can be splitted to many 
	 * receiving organization units.
	 */
	@Column
	@Description("PrcmtPOItem_rcvngOrgUnit_description")
	//@NotNull
	private String rcvngOrgUnit;

	/*
	 * The storage section in a warehouse in which the received articles where 
	 * stored.
	 */
	@Column
	@Description("PrcmtPOItem_strgSection_description")
	//@NotNull
	private String strgSection;
	
	@Column
	@Description("PrcmtPOItem_stkQtyPreOrder_description")
	//@NotNull
	private BigDecimal stkQtyPreOrder;

	@Column
	@Description("PrcmtPOItem_pppuPreTax_description")
	private BigDecimal pppuPreTax;

	@Column
	@Description("PrcmtPOItem_pppuCur_description")
	private String pppuCur;

	@Column
	@Description("PrcmtPOItem_grossPPPreTax_description")
	private BigDecimal grossPPPreTax;

	@Column
	@Description("PrcmtPOItem_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("PrcmtPOItem_netPPPreTax_description")
	private BigDecimal netPPPreTax;

	@Column
	@Description("PrcmtPOItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("PrcmtPOItem_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("PrcmtPOItem_netPPTaxIncl_description")
	private BigDecimal netPPTaxIncl;

	@Column
	@Description("PrcmtPOItem_creatingUsr_description")
	@NotNull
	private String creatingUsr;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(toId(poNbr, artPic, rcvngOrgUnit, strgSection));
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtPOItem_createdDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date createdDt;

	public String getPoNbr() {
		return this.poNbr;
	}

	public void setPoNbr(final String poNbr) {
		this.poNbr = poNbr;
	}

	public String getArtPic() {
		return this.artPic;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(final String supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getQtyOrdered() {
		return this.qtyOrdered;
	}

	public void setQtyOrdered(final BigDecimal qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public BigDecimal getStkQtyPreOrder() {
		return this.stkQtyPreOrder;
	}

	public void setStkQtyPreOrder(final BigDecimal stkQtyPreOrder) {
		this.stkQtyPreOrder = stkQtyPreOrder;
	}

	public BigDecimal getPppuPreTax() {
		return this.pppuPreTax;
	}

	public void setPppuPreTax(final BigDecimal pppuPreTax) {
		this.pppuPreTax = pppuPreTax;
	}

	public String getPppuCur() {
		return this.pppuCur;
	}

	public void setPppuCur(final String pppuCur) {
		this.pppuCur = pppuCur;
	}

	public BigDecimal getGrossPPPreTax() {
		return this.grossPPPreTax;
	}

	public void setGrossPPPreTax(final BigDecimal grossPPPreTax) {
		this.grossPPPreTax = grossPPPreTax;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(final BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getNetPPPreTax() {
		return this.netPPPreTax;
	}

	public void setNetPPPreTax(final BigDecimal netPPPreTax) {
		this.netPPPreTax = netPPPreTax;
	}

	public BigDecimal getVatPct() {
		return this.vatPct;
	}

	public void setVatPct(final BigDecimal vatPct) {
		this.vatPct = vatPct;
	}

	public BigDecimal getVatAmount() {
		return this.vatAmount;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetPPTaxIncl() {
		return this.netPPTaxIncl;
	}

	public void setNetPPTaxIncl(final BigDecimal netPPTaxIncl) {
		this.netPPTaxIncl = netPPTaxIncl;
	}

	public String getCreatingUsr() {
		return this.creatingUsr;
	}

	public void setCreatingUsr(final String creatingUsr) {
		this.creatingUsr = creatingUsr;
	}

	public Date getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(final Date createdDt) {
		this.createdDt = createdDt;
	}
	
	public BigDecimal getFreeQty() {
		return freeQty;
	}

	public void setFreeQty(BigDecimal freeQty) {
		this.freeQty = freeQty;
	}

	public String getRcvngOrgUnit() {
		return rcvngOrgUnit;
	}

	public void setRcvngOrgUnit(String rcvngOrgUnit) {
		this.rcvngOrgUnit = rcvngOrgUnit;
	}

	public String getStrgSection() {
		return strgSection;
	}

	public void setStrgSection(String strgSection) {
		this.strgSection = strgSection;
	}

	public static String toId(String poNbr, String artPic, String rcvngOrgUnit, String strgSection){
		return poNbr + "_" + artPic + "_" + rcvngOrgUnit + "_" + strgSection;
	}
	
	public void copyTo(PrcmtAbstractPOItem target){
		target.poNbr=poNbr;	
		target.artPic=artPic;
		target.artName=artName;
		target.supplier=supplier;	
		target.qtyOrdered=qtyOrdered;
		target.freeQty=freeQty;
		target.stkQtyPreOrder = stkQtyPreOrder;
		target.pppuPreTax=pppuPreTax;
		target.pppuCur=pppuCur;
		target.grossPPPreTax=grossPPPreTax;
		target.netPPPreTax=netPPPreTax;
		target.netPPTaxIncl = netPPTaxIncl;
		target.vatPct=vatPct;
		target.vatAmount = vatAmount;
		target.rebate = rebate;
		target.creatingUsr=creatingUsr;
		target.createdDt=createdDt;	
		target.strgSection = strgSection;
		target.rcvngOrgUnit = rcvngOrgUnit;
	}
	
	public boolean contentEquals(PrcmtAbstractPOItem target){
		if(!BigDecimalUtils.numericEquals(target.qtyOrdered,qtyOrdered)) return false;
		if(!BigDecimalUtils.numericEquals(target.freeQty,freeQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.stkQtyPreOrder,stkQtyPreOrder)) return false;
		if(!BigDecimalUtils.numericEquals(target.pppuPreTax,pppuPreTax)) return false;
		if(!StringUtils.equals(target.pppuCur,pppuCur)) return false;
		if(!BigDecimalUtils.numericEquals(target.grossPPPreTax,grossPPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebate,rebate)) return false;
		if(!BigDecimalUtils.numericEquals(target.netPPPreTax,netPPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatPct,vatPct)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatAmount,vatAmount)) return false;
		if(!BigDecimalUtils.numericEquals(target.netPPTaxIncl,netPPTaxIncl)) return false;
		if(!StringUtils.equals(target.creatingUsr,creatingUsr)) return false;
		if(!CalendarUtil.isSameInstant(target.createdDt,createdDt)) return false;
		if(!StringUtils.equals(target.poNbr,poNbr)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;
		if(!StringUtils.equals(target.artName,artName)) return false;
		if(!StringUtils.equals(target.supplier,supplier)) return false;
		if(!StringUtils.equals(target.rcvngOrgUnit,rcvngOrgUnit)) return false;
		if(!StringUtils.equals(target.strgSection,strgSection)) return false;
		return true;
	}
	
	public void evlte() {
		if(this.qtyOrdered==null) this.qtyOrdered=BigDecimal.ZERO;
		if(this.freeQty==null) this.freeQty=BigDecimal.ZERO;
		BigDecimal qtyOderedNet = this.qtyOrdered.subtract(this.freeQty);
		if(this.pppuPreTax==null) this.pppuPreTax=BigDecimal.ZERO;
		this.grossPPPreTax = qtyOderedNet.multiply(this.pppuPreTax);
		if(this.rebate==null) this.rebate=BigDecimal.ZERO;
		this.netPPPreTax = FinancialOps.substract(this.grossPPPreTax, this.rebate, this.pppuCur);

		if(this.vatPct==null)this.vatPct=BigDecimal.ZERO;
		if(this.vatAmount==null)this.vatAmount=BigDecimal.ZERO;
		if(this.netPPPreTax.compareTo(BigDecimal.ZERO)<=0){
			this.vatPct = BigDecimal.ZERO;
			this.vatAmount = BigDecimal.ZERO;
		} else {
			this.vatAmount = FinancialOps.amtFromPrct(this.netPPPreTax, this.vatPct, this.pppuCur);
		}
		this.netPPTaxIncl = FinancialOps.add(this.netPPPreTax, this.vatAmount, this.pppuCur);
	}
	
}