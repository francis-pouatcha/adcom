package org.adorsys.adsales.jpa;

import org.adorsys.javaext.description.Description;

@Description("SlsSOStatus_description")
public enum SlsSOStatus {
	@Description("SlsSOStatus_SUSPENDED_description")
	   SUSPENDED, @Description("SlsSOStatus_ONGOING_description")
	   ONGOING, @Description("SlsSOStatus_RESUMED_description")
	   RESUMED, @Description("SlsSOStatus_CLOSED_description")
	   CLOSED
}
