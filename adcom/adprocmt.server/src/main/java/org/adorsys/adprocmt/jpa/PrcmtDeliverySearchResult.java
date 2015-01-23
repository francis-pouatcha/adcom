package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrcmtDeliverySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PrcmtDeliveryEvtData> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PrcmtDeliverySearchInput searchInput;

   public PrcmtDeliverySearchResult()
   {
      super();
   }

   public PrcmtDeliverySearchResult(Long count, List<PrcmtDeliveryEvtData> resultList,
         PrcmtDeliverySearchInput searchInput)
   {
      super();
      this.count = count;
      this.resultList = resultList;
      this.searchInput = searchInput;
   }

   public Long getCount()
   {
      return count;
   }

   public List<PrcmtDeliveryEvtData> getResultList()
   {
      return resultList;
   }

   public PrcmtDeliverySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PrcmtDeliveryEvtData> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PrcmtDeliverySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
