package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalArtDetailConfigSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalArtDetailConfig> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalArtDetailConfigSearchInput searchInput;

   public CatalArtDetailConfigSearchResult()
   {
      super();
   }

   public CatalArtDetailConfigSearchResult(Long count, List<CatalArtDetailConfig> resultList,
         CatalArtDetailConfigSearchInput searchInput)
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

   public List<CatalArtDetailConfig> getResultList()
   {
      return resultList;
   }

   public CatalArtDetailConfigSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalArtDetailConfig> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalArtDetailConfigSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
