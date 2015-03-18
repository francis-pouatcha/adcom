package org.adorsys.adaptmt.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.validation.constraints.NotNull;

@Entity
public class AptmtAptLogin implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @NotNull
   private String aptmtIdentify;

   @Column
   @NotNull
   private String loginIdentify;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

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
      if (id != null)
      {
         return id.equals(((AptmtAptLogin) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getAptmtIdentify()
   {
      return this.aptmtIdentify;
   }

   public void setAptmtIdentify(final String aptmtIdentify)
   {
      this.aptmtIdentify = aptmtIdentify;
   }

   public String getLoginIdentify()
   {
      return this.loginIdentify;
   }

   public void setLoginIdentify(final String loginIdentify)
   {
      this.loginIdentify = loginIdentify;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (aptmtIdentify != null && !aptmtIdentify.trim().isEmpty())
         result += "aptmtIdentify: " + aptmtIdentify;
      if (loginIdentify != null && !loginIdentify.trim().isEmpty())
         result += ", loginIdentify: " + loginIdentify;
      return result;
   }
}