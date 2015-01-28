package org.adorsys.adbase.enums;

import org.adorsys.javaext.description.Description;

@Description("BaseHistoryTypeEnum_description")
public enum BaseHistoryTypeEnum
{
   @Description("BaseHistoryTypeEnum_INFO_description")
   INFO, 
   @Description("BaseHistoryTypeEnum_INITIATED_description")
   INITIATED, 
   @Description("BaseHistoryTypeEnum_APPROVAL_REQUESTED_description")
   APPROVAL_REQUESTED, 
   @Description("BaseHistoryTypeEnum_APPROVED_description")
   APPROVED, 
   @Description("BaseHistoryTypeEnum_FAILED_APPROVAL_description")
   FAILED_APPROVAL, @Description("BaseHistoryTypeEnum_DENIED_APPROVAL_description")
   DENIED_APPROVAL, @Description("BaseHistoryTypeEnum_FROZEN_APPROVING_description")
   FROZEN_APPROVING, @Description("BaseHistoryTypeEnum_FROZEN_ABANDONED_description")
   FROZEN_ABANDONED, @Description("BaseHistoryTypeEnum_FROZEN_TERMINATED_description")
   FROZEN_TERMINATED, @Description("BaseHistoryTypeEnum_MODIFIED_description")
   MODIFIED, @Description("BaseHistoryTypeEnum_CANCELED_description")
   CANCELED, @Description("BaseHistoryTypeEnum_FAILED_CANCELATION_description")
   FAILED_CANCELATION, @Description("BaseHistoryTypeEnum_COMMITTED_description")
   COMMITTED, @Description("BaseHistoryTypeEnum_FAILED_COMMITMENT_description")
   FAILED_COMMITMENT, @Description("BaseHistoryTypeEnum_CLOSED_description")
   CLOSED, @Description("BaseHistoryTypeEnum_FAILED_CLOSING_description")
   FAILED_CLOSING, @Description("BaseHistoryTypeEnum_POSTED_description")
   POSTED, @Description("BaseHistoryTypeEnum_REVERSED_description")
   REVERSED, @Description("BaseHistoryTypeEnum_FAILED_REVERSE_description")
   FAILED_REVERSE, @Description("BaseHistoryTypeEnum_RECALL_description")
   RECALL, @Description("BaseHistoryTypeEnum_FAILED_RECALL_description")
   FAILED_RECALL
}