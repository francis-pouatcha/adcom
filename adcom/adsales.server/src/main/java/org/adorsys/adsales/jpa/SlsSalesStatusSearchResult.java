package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsSalesStatusSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsSalesStatus> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsSalesStatusSearchInput searchInput;

   public SlsSalesStatusSearchResult()
   {
      super();
   }

   public SlsSalesStatusSearchResult(Long count, List<SlsSalesStatus> resultList,
         SlsSalesStatusSearchInput searchInput)
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

   public List<SlsSalesStatus> getResultList()
   {
      return resultList;
   }

   public SlsSalesStatusSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsSalesStatus> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsSalesStatusSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
