package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalArtManufSuppSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalArtManufSupp> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalArtManufSuppSearchInput searchInput;

   public CatalArtManufSuppSearchResult()
   {
      super();
   }

   public CatalArtManufSuppSearchResult(Long count, List<CatalArtManufSupp> resultList,
         CatalArtManufSuppSearchInput searchInput)
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

   public List<CatalArtManufSupp> getResultList()
   {
      return resultList;
   }

   public CatalArtManufSuppSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalArtManufSupp> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalArtManufSuppSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
