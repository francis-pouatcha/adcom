package org.adorsys.adsales.pharma;

import org.adorsys.javaext.description.Description;

@Description("HistoryTypeEnum_description")
public enum SlsHistoryTypeEnum
{
   @Description("HistoryTypeEnum_INFO_description")
   INFO, @Description("HistoryTypeEnum_APPROVAL_REQUESTED_description")
   APPROVAL_REQUESTED, @Description("HistoryTypeEnum_APPROVED_description")
   APPROVED, @Description("HistoryTypeEnum_FAILED_APPROVAL_description")
   FAILED_APPROVAL, @Description("HistoryTypeEnum_DENIED_APPROVAL_description")
   DENIED_APPROVAL, @Description("HistoryTypeEnum_FROZEN_APPROVING_description")
   FROZEN_APPROVING, @Description("HistoryTypeEnum_FROZEN_ABANDONED_description")
   FROZEN_ABANDONED, @Description("HistoryTypeEnum_FROZEN_TERMINATED_description")
   FROZEN_TERMINATED, @Description("HistoryTypeEnum_MODIFIED_description")
   MODIFIED, @Description("HistoryTypeEnum_CANCELED_description")
   CANCELED, @Description("HistoryTypeEnum_FAILED_CANCELATION_description")
   FAILED_CANCELATION, @Description("HistoryTypeEnum_COMMITTED_description")
   COMMITTED, @Description("HistoryTypeEnum_FAILED_COMMITMENT_description")
   FAILED_COMMITMENT, @Description("HistoryTypeEnum_CLOSED_description")
   CLOSED, @Description("HistoryTypeEnum_FAILED_CLOSING_description")
   FAILED_CLOSING, @Description("HistoryTypeEnum_POSTED_description")
   POSTED, @Description("HistoryTypeEnum_REVERSED_description")
   REVERSED, @Description("HistoryTypeEnum_FAILED_REVERSE_description")
   FAILED_REVERSE, @Description("HistoryTypeEnum_RECALL_description")
   RECALL, @Description("HistoryTypeEnum_FAILED_RECALL_description")
   FAILED_RECALL
}