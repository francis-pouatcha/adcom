package org.adorsys.adsales.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsInvceItem_description")
public class SlsInvceItem extends AbstractIdentifData {

	private static final long serialVersionUID = -8466036435151817125L;

	@Column
	@Description("SlsInvceItem_invNbr_description")
	@NotNull
	private String invNbr;

	@Column
	@Description("SlsInvceItem_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("SlsInvceItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("SlsInvceItem_qty_description")
	private BigDecimal qty;

	@Column
	@Description("SlsInvceItem_sppuPreTax_description")
	private BigDecimal sppuPreTax;

	@Column
	@Description("SlsInvceItem_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("SlsInvceItem_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsInvceItem_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("SlsInvceItem_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsInvceItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("SlsInvceItem_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsInvceItem_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsInvceItem_objctOrgUnit_description")
	@NotNull
	private String objctOrgUnit;

	public String getInvNbr() {
		return this.invNbr;
	}

	public void setInvNbr(final String invNbr) {
		this.invNbr = invNbr;
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

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(final BigDecimal qty) {
		this.qty = qty;
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
		return invNbr + "_" + lotPic+ "_" + artPic;
	}
}