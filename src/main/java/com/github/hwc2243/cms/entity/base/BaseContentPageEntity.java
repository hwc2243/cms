package com.github.hwc2243.cms.entity.base;


import com.github.hwc2243.cms.model.base.BaseContentPage;
import com.github.hwc2243.cms.model.ContentPage;
import com.github.hwc2243.cms.model.ContentPageStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class BaseContentPageEntity extends AbstractBaseEntity
    implements BaseContentPage, Serializable
{
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id = null;

  @Column
  protected String slug = null;
  
  @Column
  protected String title = null;
  
  @Column
  protected String summary = null;
  
  @Column
  @Enumerated(EnumType.STRING)
  protected ContentPageStatus status = null;
  
  @Column
  protected Integer version = null;
  
  @Column
  protected LocalDateTime publishedAt = null;
  
  @Column
  protected LocalDateTime createdAt = null;
  
  @Column
  protected LocalDateTime updatedAt = null;
  
  @Column
  protected String createdBy = null;
  
  @Column
  protected String updatedBy = null;
  

  
  public Long getId ()
  {
    return this.id;
  }
  
  public void setId (Long id)
  {
    this.id = id;
  }

  public Object getKey ()
  {
    return this.id;
  }
  

  public String getSlug ()
  {
    return this.slug;
  }
  
  public void setSlug (String slug)
  {
    this.slug = slug;
  }
  

  public String getTitle ()
  {
    return this.title;
  }
  
  public void setTitle (String title)
  {
    this.title = title;
  }
  

  public String getSummary ()
  {
    return this.summary;
  }
  
  public void setSummary (String summary)
  {
    this.summary = summary;
  }
  

  public ContentPageStatus getStatus ()
  {
    return this.status;
  }
  
  public void setStatus (ContentPageStatus status)
  {
    this.status = status;
  }

  public Integer getVersion ()
  {
    return this.version;
  }
  
  public void setVersion (Integer version)
  {
    this.version = version;
  }
  

  public LocalDateTime getPublishedAt ()
  {
    return this.publishedAt;
  }
  
  public void setPublishedAt (LocalDateTime publishedAt)
  {
    this.publishedAt = publishedAt;
  }
  

  public LocalDateTime getCreatedAt ()
  {
    return this.createdAt;
  }
  
  public void setCreatedAt (LocalDateTime createdAt)
  {
    this.createdAt = createdAt;
  }
  

  public LocalDateTime getUpdatedAt ()
  {
    return this.updatedAt;
  }
  
  public void setUpdatedAt (LocalDateTime updatedAt)
  {
    this.updatedAt = updatedAt;
  }
  

  public String getCreatedBy ()
  {
    return this.createdBy;
  }
  
  public void setCreatedBy (String createdBy)
  {
    this.createdBy = createdBy;
  }
  

  public String getUpdatedBy ()
  {
    return this.updatedBy;
  }
  
  public void setUpdatedBy (String updatedBy)
  {
    this.updatedBy = updatedBy;
  }
  


    @Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
			
		BaseContentPageEntity other = (BaseContentPageEntity) obj;
		return Objects.equals(getId(), other.getId());
	}

}
