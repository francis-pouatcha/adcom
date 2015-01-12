package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkInvtryItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkInvtryItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkInvtryItemSearchInput searchInput;

   public StkInvtryItemSearchResult()
   {
      super();
   }

   public StkInvtryItemSearchResult(Long count, List<StkInvtryItem> resultList,
         StkInvtryItemSearchInput searchInput)
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

   public List<StkInvtryItem> getResultList()
   {
      return resultList;
   }

   public StkInvtryItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkInvtryItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkInvtryItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
