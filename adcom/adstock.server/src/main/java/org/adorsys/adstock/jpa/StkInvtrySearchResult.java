package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkInvtrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkInvtry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkInvtrySearchInput searchInput;

   public StkInvtrySearchResult()
   {
      super();
   }

   public StkInvtrySearchResult(Long count, List<StkInvtry> resultList,
         StkInvtrySearchInput searchInput)
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

   public List<StkInvtry> getResultList()
   {
      return resultList;
   }

   public StkInvtrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkInvtry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkInvtrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
