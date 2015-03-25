package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtLoginSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AptAptmtLogin> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AptAptmtLoginSearchInput searchInput;

   public AptAptmtLoginSearchResult()
   {
      super();
   }

   public AptAptmtLoginSearchResult(Long count, List<AptAptmtLogin> resultList,
		   AptAptmtLoginSearchInput searchInput)
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

   public List<AptAptmtLogin> getResultList()
   {
      return resultList;
   }

   public AptAptmtLoginSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AptAptmtLogin> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AptAptmtLoginSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
