package org.adorsys.adinvtry.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvInvtrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<InvInvtry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private InvInvtrySearchInput searchInput;

   public InvInvtrySearchResult()
   {
      super();
   }

   public InvInvtrySearchResult(Long count, List<InvInvtry> resultList,
         InvInvtrySearchInput searchInput)
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

   public List<InvInvtry> getResultList()
   {
      return resultList;
   }

   public InvInvtrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<InvInvtry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(InvInvtrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
