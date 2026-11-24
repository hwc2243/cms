package com.github.hwc2243.cms.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.model.base.BaseContentPage;
import com.github.hwc2243.cms.model.ContentPageStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class BaseContentPageDTO
implements  Serializable
{
  protected Long id = null;

  protected String slug = null;
  
  protected String title = null;
  
  protected String summary = null;
  
  protected ContentPageStatus status = null;
  
  protected Integer version = null;
  
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  protected LocalDateTime publishedAt = null;
  
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  protected LocalDateTime createdAt = null;
  
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  protected LocalDateTime updatedAt = null;
  
  protected String createdBy = null;
  
  protected String updatedBy = null;
  

  protected BaseContentPageDTO () {
  }
  
  // Private constructor to force the use of the Builder
  protected BaseContentPageDTO (Builder builder)
  {
    this.id = builder.id;
    this.slug = builder.slug;
    this.title = builder.title;
    this.summary = builder.summary;
    this.status = builder.status;
    this.version = builder.version;
    this.publishedAt = builder.publishedAt;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.createdBy = builder.createdBy;
    this.updatedBy = builder.updatedBy;
  }

  public Long getId ()
  {
    return this.id;
  }
  
  public void setId (Long id)
  {
    this.id = id;
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
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
			
		BaseContentPageDTO other = (BaseContentPageDTO) obj;
		return id == other.id;
	}

  public abstract static class Builder {

  private Long id = null;

  private String slug = null;
  
  private String title = null;
  
  private String summary = null;
  
  private ContentPageStatus status = null;
  
  private Integer version = null;
  
  private LocalDateTime publishedAt = null;
  
  private LocalDateTime createdAt = null;
  
  private LocalDateTime updatedAt = null;
  
  private String createdBy = null;
  
  private String updatedBy = null;
  


    public Builder id(Long id) {
      this.id = id;
      return this;
    }
    
    public Builder slug(String slug) {
      this.slug = slug;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder summary(String summary) {
      this.summary = summary;
      return this;
    }

    public Builder status(ContentPageStatus status) {
      this.status = status;
      return this;
    }

    public Builder version(Integer version) {
      this.version = version;
      return this;
    }

    public Builder publishedAt(LocalDateTime publishedAt) {
      this.publishedAt = publishedAt;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder createdBy(String createdBy) {
      this.createdBy = createdBy;
      return this;
    }

    public Builder updatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
      return this;
    }

    /**
     * The build method creates and returns the immutable Entity object.
     */
    public abstract ContentPageDTO build();
  }
}
