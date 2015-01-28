package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalArtEquivalenceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalArtEquivalence> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalArtEquivalenceSearchInput searchInput;

   public CatalArtEquivalenceSearchResult()
   {
      super();
   }

   public CatalArtEquivalenceSearchResult(Long count, List<CatalArtEquivalence> resultList,
         CatalArtEquivalenceSearchInput searchInput)
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

   public List<CatalArtEquivalence> getResultList()
   {
      return resultList;
   }

   public CatalArtEquivalenceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalArtEquivalence> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalArtEquivalenceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
