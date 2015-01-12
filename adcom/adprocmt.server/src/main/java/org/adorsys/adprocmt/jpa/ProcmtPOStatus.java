package org.adorsys.adprocmt.jpa;

import org.adorsys.javaext.description.Description;

@Description("ProcmtPOStatus_description")
public enum ProcmtPOStatus
{
   @Description("ProcmtPOStatus_SUSPENDED_description")
   SUSPENDED, @Description("ProcmtPOStatus_ONGOING_description")
   ONGOING, @Description("ProcmtPOStatus_RESTORED_description")
   RESTORED, @Description("ProcmtPOStatus_CLOSED_description")
   CLOSED
}