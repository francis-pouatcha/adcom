package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrgContactSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<OrgContact> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private OrgContactSearchInput searchInput;

   public OrgContactSearchResult()
   {
      super();
   }

   public OrgContactSearchResult(Long count, List<OrgContact> resultList,
         OrgContactSearchInput searchInput)
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

   public List<OrgContact> getResultList()
   {
      return resultList;
   }

   public OrgContactSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<OrgContact> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(OrgContactSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
