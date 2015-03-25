package org.adorsys.adaptmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractEntity;

@Entity
public class AptAptmtRepportLogin extends AbstractEntity
{

   @Column
   private String loginIdentify;

   @Column
   private String aptmtRepportIdentify;


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

   public String getLoginIdentify()
   {
      return this.loginIdentify;
   }

   public void setLoginIdentify(final String loginIdentify)
   {
      this.loginIdentify = loginIdentify;
   }

   public String getAptmtRepportIdentify()
   {
      return this.aptmtRepportIdentify;
   }

   public void setAptmtRepportIdentify(final String aptmtRepportIdentify)
   {
      this.aptmtRepportIdentify = aptmtRepportIdentify;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (loginIdentify != null && !loginIdentify.trim().isEmpty())
         result += "loginIdentify: " + loginIdentify;
      if (aptmtRepportIdentify != null && !aptmtRepportIdentify.trim().isEmpty())
         result += ", aptmtRepportIdentify: " + aptmtRepportIdentify;
      return result;
   }
}