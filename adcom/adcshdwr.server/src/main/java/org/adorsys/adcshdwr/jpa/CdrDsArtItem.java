package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrDsArtItem_description")
public class CdrDsArtItem extends AbstractIdentifData {

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
}