package org.adorsys.adprocmt.jpa;

import org.adorsys.javaext.description.Description;

@Description("ProcmtDlvryStatus_description")
public enum ProcmtDlvryStatus
{
   @Description("ProcmtDlvryStatus_SUSPENDED_description")
   SUSPENDED, @Description("ProcmtDlvryStatus_ONGOING_description")
   ONGOING, @Description("ProcmtDlvryStatus_RESTORED_description")
   RESTORED, @Description("ProcmtDlvryStatus_CLOSED_description")
   CLOSED
}