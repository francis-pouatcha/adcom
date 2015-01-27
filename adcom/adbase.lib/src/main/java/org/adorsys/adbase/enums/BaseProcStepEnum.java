package org.adorsys.adbase.enums;

import org.adorsys.javaext.description.Description;

@Description("BaseProcStepEnum_description")
public enum BaseProcStepEnum
{
   @Description("BaseProcStepEnum_INITIATING_description")
   INITIATING, @Description("BaseProcStepEnum_CLOSING_description")
   CLOSING, @Description("BaseProcStepEnum_ANNULATING_description")
   ANNULATING, @Description("BaseProcStepEnum_APPROVING_description")
   APPROVING, @Description("BaseProcStepEnum_RECALLING_description")
   RECALLING, @Description("BaseProcStepEnum_MODIFYING_description")
   MODIFYING, @Description("BaseProcStepEnum_COMMITTING_description")
   COMMITTING, @Description("BaseProcStepEnum_POSTING_description")
   POSTING, @Description("BaseProcStepEnum_REVERSING_description")
   REVERSING
}