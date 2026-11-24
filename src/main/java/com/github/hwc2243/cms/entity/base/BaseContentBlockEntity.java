package com.github.hwc2243.cms.entity.base;


import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.model.base.BaseContentBlock;
import com.github.hwc2243.cms.model.ContentBlock;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class BaseContentBlockEntity extends AbstractBaseEntity
    implements BaseContentBlock<ContentPageEntity>, Serializable
{
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id = null;

  @Column
  protected Integer sortOrder = null;
  
  @Column
  @Enumerated(EnumType.STRING)
  protected ContentBlockBlockType blockType = null;
  
  @Column
  protected String heading = null;
  
  @Column
  protected String body = null;
  
  @Column
  protected Long primaryDocumentId = null;
  
  @Column
  protected String secondaryDocumentIds = null;
  
  @Column
  protected String metadataJson = null;
  
  @Column
  protected Boolean visible = null;
  
  @Column
  @Enumerated(EnumType.STRING)
  protected ContentBlockContentState contentState = null;
  

  @ManyToOne
  @JoinColumn(name= "pageId", nullable=true)
  protected ContentPageEntity page;

  
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

  public ContentPageEntity getPage ()
  {
    return this.page;
  }
  
  public void setPage (ContentPageEntity page)
  {
    this.page = page;
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
			
		BaseContentBlockEntity other = (BaseContentBlockEntity) obj;
		return Objects.equals(getId(), other.getId());
	}

}
