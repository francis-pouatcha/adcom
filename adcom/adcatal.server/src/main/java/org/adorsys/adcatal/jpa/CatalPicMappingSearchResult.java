package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalPicMappingSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalPicMapping> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalPicMappingSearchInput searchInput;

   public CatalPicMappingSearchResult()
   {
      super();
   }

   public CatalPicMappingSearchResult(Long count, List<CatalPicMapping> resultList,
         CatalPicMappingSearchInput searchInput)
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

   public List<CatalPicMapping> getResultList()
   {
      return resultList;
   }

   public CatalPicMappingSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalPicMapping> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalPicMappingSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
