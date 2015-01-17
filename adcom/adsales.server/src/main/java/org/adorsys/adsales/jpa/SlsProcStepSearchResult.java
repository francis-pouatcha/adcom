package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsProcStepSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsProcStep> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsProcStepSearchInput searchInput;

   public SlsProcStepSearchResult()
   {
      super();
   }

   public SlsProcStepSearchResult(Long count, List<SlsProcStep> resultList,
         SlsProcStepSearchInput searchInput)
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

   public List<SlsProcStep> getResultList()
   {
      return resultList;
   }

   public SlsProcStepSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsProcStep> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsProcStepSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
