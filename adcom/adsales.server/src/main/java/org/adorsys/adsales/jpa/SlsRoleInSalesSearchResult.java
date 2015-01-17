package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsRoleInSalesSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsRoleInSales> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsRoleInSalesSearchInput searchInput;

   public SlsRoleInSalesSearchResult()
   {
      super();
   }

   public SlsRoleInSalesSearchResult(Long count, List<SlsRoleInSales> resultList,
         SlsRoleInSalesSearchInput searchInput)
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

   public List<SlsRoleInSales> getResultList()
   {
      return resultList;
   }

   public SlsRoleInSalesSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsRoleInSales> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsRoleInSalesSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
