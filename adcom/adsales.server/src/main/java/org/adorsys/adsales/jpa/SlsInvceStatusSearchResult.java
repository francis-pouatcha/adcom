package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsInvceStatusSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsInvceStatus> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsInvceStatusSearchInput searchInput;

   public SlsInvceStatusSearchResult()
   {
      super();
   }

   public SlsInvceStatusSearchResult(Long count, List<SlsInvceStatus> resultList,
         SlsInvceStatusSearchInput searchInput)
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

   public List<SlsInvceStatus> getResultList()
   {
      return resultList;
   }

   public SlsInvceStatusSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsInvceStatus> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsInvceStatusSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
