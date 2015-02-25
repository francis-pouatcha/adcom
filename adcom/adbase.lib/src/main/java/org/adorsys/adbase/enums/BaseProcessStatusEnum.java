package org.adorsys.adbase.enums;

import org.adorsys.javaext.description.Description;

@Description("BaseProcessStatusEnum_description")
public enum BaseProcessStatusEnum
{
   @Description("BaseProcessStatusEnum_SUSPENDED_description")
   SUSPENDED, 
   @Description("BaseProcessStatusEnum_ONGOING_description")
   ONGOING, 
   @Description("BaseProcessStatusEnum_RESUMED_description")
   RESUMED, 
   @Description("BaseProcessStatusEnum_CLOSING_description")
   CLOSING,
   @Description("BaseProcessStatusEnum_CLOSED_description")
   CLOSED
}