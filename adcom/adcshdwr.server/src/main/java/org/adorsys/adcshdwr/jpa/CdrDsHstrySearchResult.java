package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrDsHstrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrDsHstry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrDsHstrySearchInput searchInput;

   public CdrDsHstrySearchResult()
   {
      super();
   }

   public CdrDsHstrySearchResult(Long count, List<CdrDsHstry> resultList,
         CdrDsHstrySearchInput searchInput)
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

   public List<CdrDsHstry> getResultList()
   {
      return resultList;
   }

   public CdrDsHstrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrDsHstry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrDsHstrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
