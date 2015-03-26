package org.adorsys.adaptmt.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class AptAptmtRepport implements Serializable
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
   private String title;

   @Column
   private String description;

   @Column
   @NotNull
   private String aptmtIdentify;

   @Temporal(TemporalType.TIMESTAMP)
   private Date created;

   @Column
   @NotNull
   private String createUserId;

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
         return id.equals(((AptAptmtRepport) that).id);
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

   public String getTitle()
   {
      return this.title;
   }

   public void setTitle(final String title)
   {
      this.title = title;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public String getAptmtIdentify()
   {
      return this.aptmtIdentify;
   }

   public void setAptmtIdentify(final String aptmtIdentify)
   {
      this.aptmtIdentify = aptmtIdentify;
   }

   public Date getCreated()
   {
      return this.created;
   }

   public void setCreated(final Date created)
   {
      this.created = created;
   }

   public String getCreateUserId()
   {
      return this.createUserId;
   }

   public void setCreateUserId(final String createUserId)
   {
      this.createUserId = createUserId;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (title != null && !title.trim().isEmpty())
         result += "title: " + title;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (aptmtIdentify != null && !aptmtIdentify.trim().isEmpty())
         result += ", aptmtIdentify: " + aptmtIdentify;
      if (createUserId != null && !createUserId.trim().isEmpty())
         result += ", createUserId: " + createUserId;
      return result;
   }
}