package org.adorsys.adsales.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsSOItem_description")
public class SlsSOItem extends AbstractIdentifData {

	private static final long serialVersionUID = -4440710514317606918L;

	@Column
	@Description("SlsSOItem_soNbr_description")
	@NotNull
	private String soNbr;

	@Column
	@Description("SlsSOItem_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("SlsSOItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("SlsSOItem_orderedQty_description")
	@NotNull
	private BigDecimal orderedQty;

	@Column
	@Description("SlsSOItem_returnedQty_description")
	private BigDecimal returnedQty;

	@Column
	@Description("SlsSOItem_deliveredQty_description")
	private BigDecimal deliveredQty;

	@Column
	@Description("SlsSOItem_sppuPreTax_description")
	private BigDecimal sppuPreTax;

	@Column
	@Description("SlsSOItem_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("SlsSOItem_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsSOItem_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("SlsSOItem_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsSOItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("SlsSOItem_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsSOItem_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsSOItem_objctOrgUnit_description")
	@NotNull
	private String objctOrgUnit;

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
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

	public BigDecimal getOrderedQty() {
		return this.orderedQty;
	}

	public void setOrderedQty(final BigDecimal orderedQty) {
		this.orderedQty = orderedQty;
	}

	public BigDecimal getReturnedQty() {
		return this.returnedQty;
	}

	public void setReturnedQty(final BigDecimal returnedQty) {
		this.returnedQty = returnedQty;
	}

	public BigDecimal getDeliveredQty() {
		return this.deliveredQty;
	}

	public void setDeliveredQty(final BigDecimal deliveredQty) {
		this.deliveredQty = deliveredQty;
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
		return soNbr + "_" + lotPic + "_" + artPic;
	}
}