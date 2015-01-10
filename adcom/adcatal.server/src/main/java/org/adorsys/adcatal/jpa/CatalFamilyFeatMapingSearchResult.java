package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalFamilyFeatMapingSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalFamilyFeatMaping> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalFamilyFeatMapingSearchInput searchInput;

   public CatalFamilyFeatMapingSearchResult()
   {
      super();
   }

   public CatalFamilyFeatMapingSearchResult(Long count, List<CatalFamilyFeatMaping> resultList,
         CatalFamilyFeatMapingSearchInput searchInput)
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

   public List<CatalFamilyFeatMaping> getResultList()
   {
      return resultList;
   }

   public CatalFamilyFeatMapingSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalFamilyFeatMaping> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalFamilyFeatMapingSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
