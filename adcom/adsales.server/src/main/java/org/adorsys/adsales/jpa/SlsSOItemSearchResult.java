package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsSOItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsSOItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsSOItemSearchInput searchInput;

   public SlsSOItemSearchResult()
   {
      super();
   }

   public SlsSOItemSearchResult(Long count, List<SlsSOItem> resultList,
         SlsSOItemSearchInput searchInput)
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

   public List<SlsSOItem> getResultList()
   {
      return resultList;
   }

   public SlsSOItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsSOItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsSOItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
