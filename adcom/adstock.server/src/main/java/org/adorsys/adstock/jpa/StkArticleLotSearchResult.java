package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkArticleLotSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkArticleLot> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkArticleLotSearchInput searchInput;

   public StkArticleLotSearchResult()
   {
      super();
   }

   public StkArticleLotSearchResult(Long count, List<StkArticleLot> resultList,
         StkArticleLotSearchInput searchInput)
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

   public List<StkArticleLot> getResultList()
   {
      return resultList;
   }

   public StkArticleLotSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkArticleLot> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkArticleLotSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
