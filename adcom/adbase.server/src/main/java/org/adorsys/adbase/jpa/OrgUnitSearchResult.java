package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrgUnitSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<OrgUnit> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private OrgUnitSearchInput searchInput;

   public OrgUnitSearchResult()
   {
      super();
   }

   public OrgUnitSearchResult(Long count, List<OrgUnit> resultList,
         OrgUnitSearchInput searchInput)
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

   public List<OrgUnit> getResultList()
   {
      return resultList;
   }

   public OrgUnitSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<OrgUnit> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(OrgUnitSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
