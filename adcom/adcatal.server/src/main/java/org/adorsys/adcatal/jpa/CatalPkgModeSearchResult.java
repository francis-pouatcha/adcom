package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalPkgModeSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalPkgMode> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalPkgModeSearchInput searchInput;

   public CatalPkgModeSearchResult()
   {
      super();
   }

   public CatalPkgModeSearchResult(Long count, List<CatalPkgMode> resultList,
         CatalPkgModeSearchInput searchInput)
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

   public List<CatalPkgMode> getResultList()
   {
      return resultList;
   }

   public CatalPkgModeSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalPkgMode> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalPkgModeSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
