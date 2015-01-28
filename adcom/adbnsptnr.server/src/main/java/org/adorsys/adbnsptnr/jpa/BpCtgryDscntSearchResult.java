package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpCtgryDscntSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpCtgryDscnt> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpCtgryDscntSearchInput searchInput;

   public BpCtgryDscntSearchResult()
   {
      super();
   }

   public BpCtgryDscntSearchResult(Long count, List<BpCtgryDscnt> resultList,
         BpCtgryDscntSearchInput searchInput)
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

   public List<BpCtgryDscnt> getResultList()
   {
      return resultList;
   }

   public BpCtgryDscntSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpCtgryDscnt> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpCtgryDscntSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
