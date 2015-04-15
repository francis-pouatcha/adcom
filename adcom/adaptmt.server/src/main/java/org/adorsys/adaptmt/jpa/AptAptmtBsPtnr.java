package org.adorsys.adaptmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractEntity;
import org.adorsys.adcore.jpa.AbstractTimedData;

@Entity
public class AptAptmtBsPtnr extends AbstractEntity
{
	private static final long serialVersionUID = 5118120729653009565L;

   @Column
   @NotNull
   private String aptmtIdentify;

   @Column
   @NotNull
   private String bsPtnrIdentify;


   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      return super.equals(that);
   }


   public String getAptmtIdentify()
   {
      return this.aptmtIdentify;
   }

   public void setAptmtIdentify(final String aptmtIdentify)
   {
      this.aptmtIdentify = aptmtIdentify;
   }

   public String getBsPtnrIdentify()
   {
      return this.bsPtnrIdentify;
   }

   public void setBsPtnrIdentify(final String bsPtnrIdentify)
   {
      this.bsPtnrIdentify = bsPtnrIdentify;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (aptmtIdentify != null && !aptmtIdentify.trim().isEmpty())
         result += "aptmtIdentify: " + aptmtIdentify;
      if (bsPtnrIdentify != null && !bsPtnrIdentify.trim().isEmpty())
         result += ", bsPtnrIdentify: " + bsPtnrIdentify;
      return result;
   }


@Override
protected String makeIdentif() {
	return aptmtIdentify + "_" + bsPtnrIdentify;
}
}