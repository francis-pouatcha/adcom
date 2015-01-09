package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalProductFamilySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalProductFamily> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalProductFamilySearchInput searchInput;

   public CatalProductFamilySearchResult()
   {
      super();
   }

   public CatalProductFamilySearchResult(Long count, List<CatalProductFamily> resultList,
         CatalProductFamilySearchInput searchInput)
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

   public List<CatalProductFamily> getResultList()
   {
      return resultList;
   }

   public CatalProductFamilySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalProductFamily> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalProductFamilySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
