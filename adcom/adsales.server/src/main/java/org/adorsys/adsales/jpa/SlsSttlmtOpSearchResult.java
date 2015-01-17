package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsSttlmtOpSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsSttlmtOp> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsSttlmtOpSearchInput searchInput;

   public SlsSttlmtOpSearchResult()
   {
      super();
   }

   public SlsSttlmtOpSearchResult(Long count, List<SlsSttlmtOp> resultList,
         SlsSttlmtOpSearchInput searchInput)
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

   public List<SlsSttlmtOp> getResultList()
   {
      return resultList;
   }

   public SlsSttlmtOpSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsSttlmtOp> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsSttlmtOpSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
