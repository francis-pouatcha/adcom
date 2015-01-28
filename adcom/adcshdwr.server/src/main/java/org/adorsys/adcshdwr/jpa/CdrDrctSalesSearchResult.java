package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrDrctSalesSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrDrctSales> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrDrctSalesSearchInput searchInput;

   public CdrDrctSalesSearchResult()
   {
      super();
   }

   public CdrDrctSalesSearchResult(Long count, List<CdrDrctSales> resultList,
         CdrDrctSalesSearchInput searchInput)
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

   public List<CdrDrctSales> getResultList()
   {
      return resultList;
   }

   public CdrDrctSalesSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrDrctSales> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrDrctSalesSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
