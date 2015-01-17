package org.adorsys.adsales.pharma;

import org.adorsys.javaext.description.Description;

@Description("ProcStepEnum_description")
public enum SlsProcStepEnum
{
   @Description("ProcStepEnum_INITIATING_description")
   INITIATING, @Description("ProcStepEnum_CLOSING_description")
   CLOSING, @Description("ProcStepEnum_ANNULATING_description")
   ANNULATING, @Description("ProcStepEnum_APPROVING_description")
   APPROVING, @Description("ProcStepEnum_RECALLING_description")
   RECALLING, @Description("ProcStepEnum_MODIFYING_description")
   MODIFYING, @Description("ProcStepEnum_COMMITTING_description")
   COMMITTING, @Description("ProcStepEnum_POSTING_description")
   POSTING, @Description("ProcStepEnum_REVERSING_description")
   REVERSING
}