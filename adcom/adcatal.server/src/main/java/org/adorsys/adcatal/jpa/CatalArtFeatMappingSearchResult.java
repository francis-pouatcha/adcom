package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalArtFeatMappingSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalArtFeatMapping> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalArtFeatMappingSearchInput searchInput;

   public CatalArtFeatMappingSearchResult()
   {
      super();
   }

   public CatalArtFeatMappingSearchResult(Long count, List<CatalArtFeatMapping> resultList,
         CatalArtFeatMappingSearchInput searchInput)
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

   public List<CatalArtFeatMapping> getResultList()
   {
      return resultList;
   }

   public CatalArtFeatMappingSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalArtFeatMapping> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalArtFeatMappingSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
