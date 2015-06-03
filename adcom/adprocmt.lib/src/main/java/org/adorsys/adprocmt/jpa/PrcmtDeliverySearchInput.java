package org.adorsys.adprocmt.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class PrcmtDeliverySearchInput
{

   /**
    * The entity holding search inputs.
    */
   private PrcmtDelivery entity;

   /**
    * The start cursor
    */
   private int start = -1;

   /**
    * The max number of records to return.
    */
   private int max = -1;
   
   private Date dateMin;
   
   private Date dateMax;

   /**
    * The field names to be included in the search.
    */
   private List<String> fieldNames = new ArrayList<String>();

   public PrcmtDelivery getEntity()
   {
      return entity;
   }

   public void setEntity(PrcmtDelivery entity)
   {
      this.entity = entity;
   }

   public List<String> getFieldNames()
   {
      return fieldNames;
   }

   public Date getDateMin() {
	return dateMin;
}

public void setDateMin(Date dateMin) {
	this.dateMin = dateMin;
}

public Date getDateMax() {
	return dateMax;
}

public void setDateMax(Date dateMax) {
	this.dateMax = dateMax;
}

public void setFieldNames(List<String> fieldNames)
   {
      this.fieldNames = fieldNames;
   }

   public int getStart()
   {
      return start;
   }

   public void setStart(int start)
   {
      this.start = start;
   }

   public int getMax()
   {
      return max;
   }

   public void setMax(int max)
   {
      this.max = max;
   }
}
