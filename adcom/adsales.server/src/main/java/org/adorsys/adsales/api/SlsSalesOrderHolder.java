package org.adorsys.adsales.api;

import java.util.List;

import org.adorsys.adsales.jpa.SlsSalesOrder;

public class SlsSalesOrderHolder {

	private SlsSalesOrder slsSalesOrder;
	
	private List<SlsSOItemHolder> slsSOItemsholder;
	
	private List<SlsSOPtnrHolder> slsSOPtnrsHolder;

	public SlsSalesOrder getSlsSalesOrder() {
		return slsSalesOrder;
	}

	public void setSlsSalesOrder(SlsSalesOrder slsSalesOrder) {
		this.slsSalesOrder = slsSalesOrder;
	}

	public List<SlsSOItemHolder> getSlsSOItemsholder() {
		return slsSOItemsholder;
	}

	public void setSlsSOItemsholder(List<SlsSOItemHolder> slsSOItemsholder) {
		this.slsSOItemsholder = slsSOItemsholder;
	}

	public List<SlsSOPtnrHolder> getSlsSOPtnrsHolder() {
		return slsSOPtnrsHolder;
	}

	public void setSlsSOPtnrsHolder(List<SlsSOPtnrHolder> slsSOPtnrsHolder) {
		this.slsSOPtnrsHolder = slsSOPtnrsHolder;
	}
}
