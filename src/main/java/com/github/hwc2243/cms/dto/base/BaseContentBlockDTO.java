package com.github.hwc2243.cms.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.model.base.BaseContentBlock;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class BaseContentBlockDTO
implements  Serializable
{
  protected Long id = null;

  protected Integer sortOrder = null;
  
  protected ContentBlockBlockType blockType = null;
  
  protected String heading = null;
  
  protected String body = null;
  
  protected Long primaryDocumentId = null;
  
  protected String secondaryDocumentIds = null;
  
  protected String metadataJson = null;
  
  protected Boolean visible = null;
  
  protected ContentBlockContentState contentState = null;
  
  protected Long pageId;


  protected BaseContentBlockDTO () {
  }
  
  // Private constructor to force the use of the Builder
  protected BaseContentBlockDTO (Builder builder)
  {
    this.id = builder.id;
    this.sortOrder = builder.sortOrder;
    this.blockType = builder.blockType;
    this.heading = builder.heading;
    this.body = builder.body;
    this.primaryDocumentId = builder.primaryDocumentId;
    this.secondaryDocumentIds = builder.secondaryDocumentIds;
    this.metadataJson = builder.metadataJson;
    this.visible = builder.visible;
    this.contentState = builder.contentState;
    this.pageId = builder.pageId;
  }

  public Long getId ()
  {
    return this.id;
  }
  
  public void setId (Long id)
  {
    this.id = id;
  }


  public Integer getSortOrder ()
  {
    return this.sortOrder;
  }
  
  public void setSortOrder (Integer sortOrder)
  {
    this.sortOrder = sortOrder;
  }
  

  public ContentBlockBlockType getBlockType ()
  {
    return this.blockType;
  }
  
  public void setBlockType (ContentBlockBlockType blockType)
  {
    this.blockType = blockType;
  }

  public String getHeading ()
  {
    return this.heading;
  }
  
  public void setHeading (String heading)
  {
    this.heading = heading;
  }
  

  public String getBody ()
  {
    return this.body;
  }
  
  public void setBody (String body)
  {
    this.body = body;
  }
  

  public Long getPrimaryDocumentId ()
  {
    return this.primaryDocumentId;
  }
  
  public void setPrimaryDocumentId (Long primaryDocumentId)
  {
    this.primaryDocumentId = primaryDocumentId;
  }
  

  public String getSecondaryDocumentIds ()
  {
    return this.secondaryDocumentIds;
  }
  
  public void setSecondaryDocumentIds (String secondaryDocumentIds)
  {
    this.secondaryDocumentIds = secondaryDocumentIds;
  }
  

  public String getMetadataJson ()
  {
    return this.metadataJson;
  }
  
  public void setMetadataJson (String metadataJson)
  {
    this.metadataJson = metadataJson;
  }
  

  public Boolean getVisible ()
  {
    return this.visible;
  }
  
  public void setVisible (Boolean visible)
  {
    this.visible = visible;
  }
  

  public ContentBlockContentState getContentState ()
  {
    return this.contentState;
  }
  
  public void setContentState (ContentBlockContentState contentState)
  {
    this.contentState = contentState;
  }

  public Long getPageId ()
  {
    return this.pageId;
  }

  public void setPageId (Long pageId)
  {
    this.pageId = pageId;
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
			
		BaseContentBlockDTO other = (BaseContentBlockDTO) obj;
		return id == other.id;
	}

  public abstract static class Builder {

  private Long id = null;

  private Integer sortOrder = null;
  
  private ContentBlockBlockType blockType = null;
  
  private String heading = null;
  
  private String body = null;
  
  private Long primaryDocumentId = null;
  
  private String secondaryDocumentIds = null;
  
  private String metadataJson = null;
  
  private Boolean visible = null;
  
  private ContentBlockContentState contentState = null;
  

    private Long pageId = null;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }
    
    public Builder sortOrder(Integer sortOrder) {
      this.sortOrder = sortOrder;
      return this;
    }

    public Builder blockType(ContentBlockBlockType blockType) {
      this.blockType = blockType;
      return this;
    }

    public Builder heading(String heading) {
      this.heading = heading;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Builder primaryDocumentId(Long primaryDocumentId) {
      this.primaryDocumentId = primaryDocumentId;
      return this;
    }

    public Builder secondaryDocumentIds(String secondaryDocumentIds) {
      this.secondaryDocumentIds = secondaryDocumentIds;
      return this;
    }

    public Builder metadataJson(String metadataJson) {
      this.metadataJson = metadataJson;
      return this;
    }

    public Builder visible(Boolean visible) {
      this.visible = visible;
      return this;
    }

    public Builder contentState(ContentBlockContentState contentState) {
      this.contentState = contentState;
      return this;
    }

    public Builder pageId (Long pageId) {
      this.pageId = pageId;
      return this;
    }
    /**
     * The build method creates and returns the immutable Entity object.
     */
    public abstract ContentBlockDTO build();
  }
}
