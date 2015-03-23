package org.adorsys.adinvtry.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvInvtryItemListSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<InvInvtryItemList> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private InvInvtryItemListSearchInput searchInput;

   public InvInvtryItemListSearchResult()
   {
      super();
   }

   public InvInvtryItemListSearchResult(Long count, List<InvInvtryItemList> resultList,
         InvInvtryItemListSearchInput searchInput)
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

   public List<InvInvtryItemList> getResultList()
   {
      return resultList;
   }

   public InvInvtryItemListSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<InvInvtryItemList> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(InvInvtryItemListSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
