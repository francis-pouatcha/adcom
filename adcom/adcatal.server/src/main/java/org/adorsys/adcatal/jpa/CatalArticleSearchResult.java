package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatalArticleSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CatalArticle> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CatalArticleSearchInput searchInput;

   public CatalArticleSearchResult()
   {
      super();
   }

   public CatalArticleSearchResult(Long count, List<CatalArticle> resultList,
         CatalArticleSearchInput searchInput)
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

   public List<CatalArticle> getResultList()
   {
      return resultList;
   }

   public CatalArticleSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CatalArticle> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CatalArticleSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
