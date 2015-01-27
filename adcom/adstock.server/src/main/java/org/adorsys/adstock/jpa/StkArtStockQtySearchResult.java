package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkArtStockQtySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkArtStockQty> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkArtStockQtySearchInput searchInput;

   public StkArtStockQtySearchResult()
   {
      super();
   }

   public StkArtStockQtySearchResult(Long count, List<StkArtStockQty> resultList,
         StkArtStockQtySearchInput searchInput)
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

   public List<StkArtStockQty> getResultList()
   {
      return resultList;
   }

   public StkArtStockQtySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkArtStockQty> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkArtStockQtySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
