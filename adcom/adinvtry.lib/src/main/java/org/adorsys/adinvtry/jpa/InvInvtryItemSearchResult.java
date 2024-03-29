package org.adorsys.adinvtry.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvInvtryItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<InvInvtryItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private InvInvtryItemSearchInput searchInput;

   public InvInvtryItemSearchResult()
   {
      super();
   }

   public InvInvtryItemSearchResult(Long count, List<InvInvtryItem> resultList,
         InvInvtryItemSearchInput searchInput)
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

   public List<InvInvtryItem> getResultList()
   {
      return resultList;
   }

   public InvInvtryItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<InvInvtryItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(InvInvtryItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
