package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsHistoryTypeSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsHistoryType> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsHistoryTypeSearchInput searchInput;

   public SlsHistoryTypeSearchResult()
   {
      super();
   }

   public SlsHistoryTypeSearchResult(Long count, List<SlsHistoryType> resultList,
         SlsHistoryTypeSearchInput searchInput)
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

   public List<SlsHistoryType> getResultList()
   {
      return resultList;
   }

   public SlsHistoryTypeSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsHistoryType> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsHistoryTypeSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
