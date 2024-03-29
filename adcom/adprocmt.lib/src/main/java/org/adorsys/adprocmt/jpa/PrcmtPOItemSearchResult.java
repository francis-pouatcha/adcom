package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrcmtPOItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PrcmtPOItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PrcmtPOItemSearchInput searchInput;

   public PrcmtPOItemSearchResult()
   {
      super();
   }

   public PrcmtPOItemSearchResult(Long count, List<PrcmtPOItem> resultList,
         PrcmtPOItemSearchInput searchInput)
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

   public List<PrcmtPOItem> getResultList()
   {
      return resultList;
   }

   public PrcmtPOItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PrcmtPOItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PrcmtPOItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
