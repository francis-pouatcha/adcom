package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrcmtProcOrderSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PrcmtProcOrder> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PrcmtProcOrderSearchInput searchInput;

   public PrcmtProcOrderSearchResult()
   {
      super();
   }

   public PrcmtProcOrderSearchResult(Long count, List<PrcmtProcOrder> resultList,
         PrcmtProcOrderSearchInput searchInput)
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

   public List<PrcmtProcOrder> getResultList()
   {
      return resultList;
   }

   public PrcmtProcOrderSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PrcmtProcOrder> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PrcmtProcOrderSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
