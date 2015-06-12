package org.adorsys.adbase.jpa;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;
/**
 * 
 * @author guymoyo
 *
 *the purpose of this class is to save, the global config 
 *of the application
 */
@Entity 
@Table(name="BaseConfig")
@Description("BaseConfig_description")
public class BaseConfig extends AbstractTimedData {

	private BigDecimal nbrOfItemsPrcmtOrder = new BigDecimal(100);
	
	private PrintMode PrintMode;

	public PrintMode getPrintMode() {
		return PrintMode;
	}

	public void setPrintMode(PrintMode printMode) {
		PrintMode = printMode;
	}

	public BigDecimal getNbrOfItemsPrcmtOrder() {
		return nbrOfItemsPrcmtOrder;
	}

	public void setNbrOfItemsPrcmtOrder(BigDecimal nbrOfItemsPrcmtOrder) {
		this.nbrOfItemsPrcmtOrder = nbrOfItemsPrcmtOrder;
	}

	@Override
	protected String makeIdentif() {
		// TODO Auto-generated method stub
		return null;
	}
}