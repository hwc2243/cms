package com.github.hwc2243.cms.model.base;

import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import com.github.hwc2243.cms.model.ContentPage;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface BaseContentBlock<CONTENTPAGE>
 extends Serializable
{ 
  public Long getId ();
  public void setId (Long id);


  public Integer getSortOrder ();
  public void setSortOrder (Integer sortOrder);
  

  public ContentBlockBlockType getBlockType ();
  public void setBlockType (ContentBlockBlockType blockType);
  

  public String getHeading ();
  public void setHeading (String heading);
  

  public String getBody ();
  public void setBody (String body);
  

  public Long getPrimaryDocumentId ();
  public void setPrimaryDocumentId (Long primaryDocumentId);
  

  public String getSecondaryDocumentIds ();
  public void setSecondaryDocumentIds (String secondaryDocumentIds);
  

  public String getMetadataJson ();
  public void setMetadataJson (String metadataJson);
  

  public Boolean getVisible ();
  public void setVisible (Boolean visible);
  

  public ContentBlockContentState getContentState ();
  public void setContentState (ContentBlockContentState contentState);
  

  public CONTENTPAGE getPage ();
  public void setPage (CONTENTPAGE page);
  

}
