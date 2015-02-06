package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalArt2ProductFamilySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalArt2ProductFamily> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalArt2ProductFamilySearchInput searchInput;

   public CatalArt2ProductFamilySearchResult()
   {
      super();
   }

   public CatalArt2ProductFamilySearchResult(Long count, List<CatalArt2ProductFamily> resultList,
         CatalArt2ProductFamilySearchInput searchInput)
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

   public List<CatalArt2ProductFamily> getResultList()
   {
      return resultList;
   }

   public CatalArt2ProductFamilySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalArt2ProductFamily> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalArt2ProductFamilySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
