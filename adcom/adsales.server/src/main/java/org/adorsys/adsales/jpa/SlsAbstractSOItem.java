package org.adorsys.adsales.jpa;

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
@Description("SlsSOItem_description")
public abstract class SlsAbstractSOItem extends AbstractIdentifData {

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
	@Description("SlsSOItem_artName_description")
	@NotNull
	private String artName;

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
	@Description("SlsSOItem_rebatePct_description")
	private BigDecimal rebatePct;

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

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public BigDecimal getOrderedQty() {
		return this.orderedQty;
	}

	public BigDecimal getRebatePct() {
		return rebatePct;
	}

	public void setRebatePct(BigDecimal rebatePct) {
		this.rebatePct = rebatePct;
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

	public boolean contentEquals(SlsAbstractSOItem target) {
		if(!BigDecimalUtils.numericEquals(target.deliveredQty,deliveredQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.grossSPPreTax,grossSPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.netSPPreTax,netSPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.netSPTaxIncl,netSPTaxIncl)) return false;
		if(!BigDecimalUtils.numericEquals(target.orderedQty,orderedQty)) return false;	
		if(!BigDecimalUtils.numericEquals(target.rebate,rebate)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebatePct,rebatePct)) return false;
		if(!BigDecimalUtils.numericEquals(target.returnedQty,returnedQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.sppuPreTax,sppuPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatAmount,vatAmount)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatPct,vatPct)) return false;	
		if(!StringUtils.equals(target.soNbr,soNbr)) return false;
		if(!StringUtils.equals(target.lotPic,lotPic)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;	
		if(!StringUtils.equals(target.sppuCur,sppuCur)) return false;
		if(!StringUtils.equals(target.objctOrgUnit,objctOrgUnit)) return false;			
		return true;
	}

	public void copyTo(SlsAbstractSOItem target) {	
		target.deliveredQty = deliveredQty;
		target.grossSPPreTax = grossSPPreTax;
		target.netSPPreTax = netSPPreTax;
		target.netSPTaxIncl = netSPTaxIncl;
		target.orderedQty = orderedQty;
		target.rebate = rebate;
		target.rebatePct = rebatePct;
		target.returnedQty = returnedQty;
		target.sppuPreTax = sppuPreTax;
		target.vatAmount = vatAmount;
		target.vatPct = vatPct;
		target.soNbr = soNbr;
		target.lotPic = lotPic;
		target.artPic = artPic;
		target.sppuCur = sppuCur;
		target.objctOrgUnit = objctOrgUnit;
		target.identif = identif;	
	}

	public void evlte() {
		
		if(this.orderedQty==null) this.orderedQty=BigDecimal.ZERO;
		if(this.returnedQty==null) this.returnedQty=BigDecimal.ZERO;
		this.deliveredQty=this.orderedQty.subtract(this.returnedQty);
		
		if(this.sppuPreTax==null) this.sppuPreTax=BigDecimal.ZERO;
		this.grossSPPreTax = deliveredQty.multiply(this.sppuPreTax);
		
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
}