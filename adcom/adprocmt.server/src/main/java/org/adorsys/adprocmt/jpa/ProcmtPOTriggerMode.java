package org.adorsys.adprocmt.jpa;

import org.adorsys.javaext.description.Description;

@Description("ProcmtPOTriggerMode_description")
public enum ProcmtPOTriggerMode
{
   @Description("ProcmtPOTriggerMode_MANUAL_description")
   MANUAL, @Description("ProcmtPOTriggerMode_STOCK_SHORTAGE_description")
   STOCK_SHORTAGE, @Description("ProcmtPOTriggerMode_MOST_SOLD_description")
   MOST_SOLD
}