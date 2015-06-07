package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsInvceItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsInvceItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsInvceItemSearchInput searchInput;

   public SlsInvceItemSearchResult()
   {
      super();
   }

   public SlsInvceItemSearchResult(Long count, List<SlsInvceItem> resultList,
         SlsInvceItemSearchInput searchInput)
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

   public List<SlsInvceItem> getResultList()
   {
      return resultList;
   }

   public SlsInvceItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsInvceItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsInvceItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
