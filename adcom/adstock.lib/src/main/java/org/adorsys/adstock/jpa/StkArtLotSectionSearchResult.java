package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkArtLotSectionSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkArtLotSection> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkArtLotSectionSearchInput searchInput;

   public StkArtLotSectionSearchResult()
   {
      super();
   }

   public StkArtLotSectionSearchResult(Long count, List<StkArtLotSection> resultList,
         StkArtLotSectionSearchInput searchInput)
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

   public List<StkArtLotSection> getResultList()
   {
      return resultList;
   }

   public StkArtLotSectionSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkArtLotSection> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkArtLotSectionSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
