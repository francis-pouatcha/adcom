package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalManufSupplSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalManufSuppl> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalManufSupplSearchInput searchInput;

   public CatalManufSupplSearchResult()
   {
      super();
   }

   public CatalManufSupplSearchResult(Long count, List<CatalManufSuppl> resultList,
         CatalManufSupplSearchInput searchInput)
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

   public List<CatalManufSuppl> getResultList()
   {
      return resultList;
   }

   public CatalManufSupplSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalManufSuppl> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalManufSupplSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
