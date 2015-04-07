package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("CdrDsArtItem_description")
public class CdrAbstractDsArtItem extends AbstractIdentifData {

	private static final long serialVersionUID = -1884185030982589312L;

	@Column
	@Description("CdrDsArtItem_dsNbr_description")
	@NotNull
	private String dsNbr;

	@Column
	@Description("CdrDsArtItem_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("CdrDsArtItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("CdrDsArtItem_soldQty_description")
	@NotNull
	private BigDecimal soldQty;

	@Column
	@Description("CdrDsArtItem_returnedQty_description")
	private BigDecimal returnedQty;

	@Column
	@Description("CdrDsArtItem_sppuPreTax_description")
	private BigDecimal sppuPreTax;

	@Column
	@Description("CdrDsArtItem_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("CdrDsArtItem_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("CdrDsArtItem_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("CdrDsArtItem_restockgFees_description")
	private BigDecimal restockgFees;

	@Column
	@Description("CdrDsArtItem_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("CdrDsArtItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("CdrDsArtItem_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("CdrDsArtItem_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("CdrDsArtItem_objctOrgUnit_description")
	@NotNull
	private String objctOrgUnit;

	public String getDsNbr() {
		return this.dsNbr;
	}

	public void setDsNbr(final String dsNbr) {
		this.dsNbr = dsNbr;
	}

	public String getLotPic() {
		return this.lotPic;
	}

	public void setLotPic(final String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public BigDecimal getSoldQty() {
		return this.soldQty;
	}

	public void setSoldQty(final BigDecimal soldQty) {
		this.soldQty = soldQty;
	}

	public BigDecimal getReturnedQty() {
		return this.returnedQty;
	}

	public void setReturnedQty(final BigDecimal returnedQty) {
		this.returnedQty = returnedQty;
	}

	public BigDecimal getSppuPreTax() {
		return this.sppuPreTax;
	}

	public void setSppuPreTax(final BigDecimal sppuPreTax) {
		this.sppuPreTax = sppuPreTax;
	}

	public String getSppuCur() {
		return this.sppuCur;
	}

	public void setSppuCur(final String sppuCur) {
		this.sppuCur = sppuCur;
	}

	public BigDecimal getGrossSPPreTax() {
		return this.grossSPPreTax;
	}

	public void setGrossSPPreTax(final BigDecimal grossSPPreTax) {
		this.grossSPPreTax = grossSPPreTax;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(final BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getRestockgFees() {
		return this.restockgFees;
	}

	public void setRestockgFees(final BigDecimal restockgFees) {
		this.restockgFees = restockgFees;
	}

	public BigDecimal getNetSPPreTax() {
		return this.netSPPreTax;
	}

	public void setNetSPPreTax(final BigDecimal netSPPreTax) {
		this.netSPPreTax = netSPPreTax;
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

	public BigDecimal getNetSPTaxIncl() {
		return this.netSPTaxIncl;
	}

	public void setNetSPTaxIncl(final BigDecimal netSPTaxIncl) {
		this.netSPTaxIncl = netSPTaxIncl;
	}

	public String getObjctOrgUnit() {
		return this.objctOrgUnit;
	}

	public void setObjctOrgUnit(final String objctOrgUnit) {
		this.objctOrgUnit = objctOrgUnit;
	}

	@Override
	protected String makeIdentif() {
		return dsNbr + "_" + lotPic + "_" + artPic;
	}
	/**
	 * Use this method to check if this article item is in a good state.
	 * i.e : soldQty > 0, rebate >=0, etc.
	 * checkState.
	 * this method should thrown a runtime exception if something is wrong with this article item.
	 * @return
	 */
	public boolean consolidate() {
		//return true actually, but
		if(rebate == null || rebate.compareTo(BigDecimal.ZERO) == -1) rebate = BigDecimal.ZERO;
		if(grossSPPreTax == null || grossSPPreTax.compareTo(BigDecimal.ZERO) == -1) grossSPPreTax = BigDecimal.ZERO;
		if(netSPPreTax == null || netSPPreTax.compareTo(BigDecimal.ZERO) == -1) netSPPreTax = BigDecimal.ZERO;
		if(netSPTaxIncl == null || netSPTaxIncl.compareTo(BigDecimal.ZERO) == -1) netSPTaxIncl = BigDecimal.ZERO;
		if(soldQty == null || soldQty.compareTo(BigDecimal.ZERO) == -1) soldQty = BigDecimal.ZERO;
		if(sppuPreTax == null || sppuPreTax.compareTo(BigDecimal.ZERO) == -1) sppuPreTax = BigDecimal.ZERO;
		return true;
	}
	

	public void copyTo(CdrAbstractDsArtItem target){
		target.dsNbr=dsNbr;
		target.lotPic=lotPic;
		target.artPic=artPic;
		target.soldQty=soldQty;
		target.returnedQty=returnedQty;
		target.sppuPreTax=sppuPreTax;
		target.sppuCur=sppuCur;
		target.grossSPPreTax=grossSPPreTax;
		target.rebate=rebate;
		target.restockgFees=restockgFees;
		target.netSPPreTax=netSPPreTax;
		target.vatPct=vatPct;
		target.vatAmount=vatAmount;
		target.netSPTaxIncl=netSPTaxIncl;
		target.objctOrgUnit=objctOrgUnit;
	}
	
	public boolean contentEquals(CdrAbstractDsArtItem target){
		if(!BigDecimalUtils.numericEquals(target.grossSPPreTax,grossSPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.netSPPreTax,netSPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.netSPTaxIncl,netSPTaxIncl)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebate,rebate)) return false;
		if(!StringUtils.equals(target.sppuCur,sppuCur)) return false;
		if(!BigDecimalUtils.numericEquals(target.restockgFees,restockgFees)) return false;
		if(!BigDecimalUtils.numericEquals(target.returnedQty,returnedQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.soldQty,soldQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.sppuPreTax,sppuPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatAmount,vatAmount)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatPct,vatPct)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;
		if(!StringUtils.equals(target.dsNbr,dsNbr)) return false;
		if(!StringUtils.equals(target.lotPic,lotPic)) return false;
		if(!StringUtils.equals(target.objctOrgUnit,objctOrgUnit)) return false;
		return true;
	}
	
	public void evlte() {
		consolidate();
		grossSPPreTax = sppuPreTax.multiply(soldQty);
		netSPPreTax = FinancialOps.substract(grossSPPreTax, rebate, sppuCur);
		if(this.netSPPreTax.compareTo(BigDecimal.ZERO)<=0){
			this.vatPct = BigDecimal.ZERO;
			this.vatAmount = BigDecimal.ZERO;
		} else {
			this.vatAmount = FinancialOps.amtFromPrct(this.netSPPreTax, this.vatPct, this.sppuCur);
		}
		netSPTaxIncl =  FinancialOps.add(netSPPreTax, vatAmount, sppuCur);
	}
}