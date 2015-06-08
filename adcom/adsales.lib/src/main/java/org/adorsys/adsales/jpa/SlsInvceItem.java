package org.adorsys.adsales.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

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
	@Description("SlsInvceItem_artName_description")
	@NotNull
	private String artName;

	@Column
	@Description("SlsInvceItem_section_description")
	@NotNull
	private String section;
	
	@Column
	@Description("SlsInvceItem_qty_description")
	private BigDecimal qty;
	
	@Column
	@Description("SlsInvceItem_stkQty_description")
	private BigDecimal stkQty;

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
	@Description("SlsInvceItem_rebatePct_description")
	private BigDecimal rebatePct;


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
	//@NotNull
	private String objctOrgUnit;
	
	
	public boolean contentEquals(SlsInvceItem target) {
		if(!BigDecimalUtils.numericEquals(target.qty,qty)) return false;
		if(!BigDecimalUtils.numericEquals(target.grossSPPreTax,grossSPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.netSPPreTax,netSPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.netSPTaxIncl,netSPTaxIncl)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebate,rebate)) return false;
		if(!BigDecimalUtils.numericEquals(target.sppuPreTax,sppuPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatAmount,vatAmount)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatPct,vatPct)) return false;	
		if(!StringUtils.equals(target.invNbr,invNbr)) return false;
		if(!StringUtils.equals(target.lotPic,lotPic)) return false;
		if(!StringUtils.equals(target.section,section)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;	
		if(!StringUtils.equals(target.sppuCur,sppuCur)) return false;
		if(!StringUtils.equals(target.objctOrgUnit,objctOrgUnit)) return false;			
		return true;
	}

	public void copyTo(SlsInvceItem target) {	
		target.qty = qty;
		target.grossSPPreTax = grossSPPreTax;
		target.netSPPreTax = netSPPreTax;
		target.netSPTaxIncl = netSPTaxIncl;
		target.rebate = rebate;
		target.sppuPreTax = sppuPreTax;
		target.vatAmount = vatAmount;
		target.vatPct = vatPct;
		target.invNbr = invNbr;
		target.lotPic = lotPic;
		target.section = section;
		target.artPic = artPic;
		target.sppuCur = sppuCur;
		target.objctOrgUnit = objctOrgUnit;
		target.identif = identif;	
	}

	public void evlte() {
		if(this.qty==null) this.qty = BigDecimal.ZERO;
		if(this.sppuPreTax==null) this.sppuPreTax=BigDecimal.ZERO;
		this.grossSPPreTax = this.qty.multiply(this.sppuPreTax);
		
		if(this.rebatePct==null)this.rebatePct=BigDecimal.ZERO;
		if(this.rebate==null)this.rebate=BigDecimal.ZERO;
		
		if(this.grossSPPreTax.compareTo(BigDecimal.ZERO)<=0){
			this.rebate = BigDecimal.ZERO;
			this.rebatePct = BigDecimal.ZERO;
		} else {
			if(this.rebatePct.compareTo(BigDecimal.ZERO) == 1){
				this.rebate = FinancialOps.amtFromPrct(this.grossSPPreTax, this.rebatePct, this.sppuCur);
			} else {
				this.rebatePct = FinancialOps.prctFromAmt(this.grossSPPreTax, this.rebate, this.sppuCur);
			}
		}
		this.netSPPreTax = FinancialOps.substract(this.grossSPPreTax, this.rebate, this.sppuCur);
		
		if(this.vatPct==null)this.vatPct=BigDecimal.ZERO;
		if(this.vatAmount==null)this.vatAmount=BigDecimal.ZERO;
		if(this.netSPPreTax.compareTo(BigDecimal.ZERO)<=0){
			this.vatPct = BigDecimal.ZERO;
			this.vatAmount = BigDecimal.ZERO;
		} else {
			this.vatAmount = FinancialOps.amtFromPrct(this.netSPPreTax, this.vatPct, this.sppuCur);
		}
		this.netSPTaxIncl = FinancialOps.add(this.netSPPreTax, this.vatAmount, this.sppuCur);
		
	}

	public String getInvNbr() {
		return this.invNbr;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
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

	public BigDecimal getStkQty() {
		return stkQty;
	}

	public void setStkQty(BigDecimal stkQty) {
		this.stkQty = stkQty;
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

	public BigDecimal getRebatePct() {
		return rebatePct;
	}

	public void setRebatePct(BigDecimal rebatePct) {
		this.rebatePct = rebatePct;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	protected String makeIdentif() {
		return invNbr + "_" + lotPic+ "_" + artPic;
	}
}