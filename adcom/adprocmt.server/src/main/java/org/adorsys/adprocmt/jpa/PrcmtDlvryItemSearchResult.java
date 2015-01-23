package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrcmtDlvryItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PrcmtDlvryItemEvtData> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PrcmtDlvryItemSearchInput searchInput;

   public PrcmtDlvryItemSearchResult()
   {
      super();
   }

   public PrcmtDlvryItemSearchResult(Long count, List<PrcmtDlvryItemEvtData> resultList,
         PrcmtDlvryItemSearchInput searchInput)
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

   public List<PrcmtDlvryItemEvtData> getResultList()
   {
      return resultList;
   }

   public PrcmtDlvryItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PrcmtDlvryItemEvtData> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PrcmtDlvryItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
