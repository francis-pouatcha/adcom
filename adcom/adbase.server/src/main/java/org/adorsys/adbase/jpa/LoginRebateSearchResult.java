package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginRebateSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<LoginRebate> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private LoginRebateSearchInput searchInput;

   public LoginRebateSearchResult()
   {
      super();
   }

   public LoginRebateSearchResult(Long count, List<LoginRebate> resultList,
		   LoginRebateSearchInput searchInput)
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

   public List<LoginRebate> getResultList()
   {
      return resultList;
   }

   public LoginRebateSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<LoginRebate> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(LoginRebateSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
