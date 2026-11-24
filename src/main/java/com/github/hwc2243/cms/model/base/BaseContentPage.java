package com.github.hwc2243.cms.model.base;

import com.github.hwc2243.cms.model.ContentPageStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface BaseContentPage
 extends Serializable
{ 
  public Long getId ();
  public void setId (Long id);


  public String getSlug ();
  public void setSlug (String slug);
  

  public String getTitle ();
  public void setTitle (String title);
  

  public String getSummary ();
  public void setSummary (String summary);
  

  public ContentPageStatus getStatus ();
  public void setStatus (ContentPageStatus status);
  

  public Integer getVersion ();
  public void setVersion (Integer version);
  

  public LocalDateTime getPublishedAt ();
  public void setPublishedAt (LocalDateTime publishedAt);
  

  public LocalDateTime getCreatedAt ();
  public void setCreatedAt (LocalDateTime createdAt);
  

  public LocalDateTime getUpdatedAt ();
  public void setUpdatedAt (LocalDateTime updatedAt);
  

  public String getCreatedBy ();
  public void setCreatedBy (String createdBy);
  

  public String getUpdatedBy ();
  public void setUpdatedBy (String updatedBy);
  

}
