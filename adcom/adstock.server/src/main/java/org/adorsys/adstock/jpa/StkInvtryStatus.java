package org.adorsys.adstock.jpa;

import org.adorsys.javaext.description.Description;

@Description("InvntrStatus_description")
public enum StkInvtryStatus
{
   @Description("InvntrStatus_SUSPENDED_description")
   SUSPENDED, @Description("InvntrStatus_ONGOING_description")
   ONGOING, @Description("InvntrStatus_RESTORED_description")
   RESTORED, @Description("InvntrStatus_CLOSED_description")
   CLOSED
}