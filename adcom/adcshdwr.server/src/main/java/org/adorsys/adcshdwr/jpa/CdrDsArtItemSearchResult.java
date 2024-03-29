package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrDsArtItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrDsArtItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrDsArtItemSearchInput searchInput;

   public CdrDsArtItemSearchResult()
   {
      super();
   }

   public CdrDsArtItemSearchResult(Long count, List<CdrDsArtItem> resultList,
         CdrDsArtItemSearchInput searchInput)
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

   public List<CdrDsArtItem> getResultList()
   {
      return resultList;
   }

   public CdrDsArtItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrDsArtItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrDsArtItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
