package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsInvoiceTypeSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsInvoiceType> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsInvoiceTypeSearchInput searchInput;

   public SlsInvoiceTypeSearchResult()
   {
      super();
   }

   public SlsInvoiceTypeSearchResult(Long count, List<SlsInvoiceType> resultList,
         SlsInvoiceTypeSearchInput searchInput)
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

   public List<SlsInvoiceType> getResultList()
   {
      return resultList;
   }

   public SlsInvoiceTypeSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsInvoiceType> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsInvoiceTypeSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
