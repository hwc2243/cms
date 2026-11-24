package com.github.hwc2243.cms.dto;

import com.github.hwc2243.cms.dto.base.BaseContentBlockDTO;

public class ContentBlockDTO extends BaseContentBlockDTO
{
  private static final long serialVersionUID = 1L;

  public ContentBlockDTO () {
  }
  
  public ContentBlockDTO (Builder builder) {
    super(builder);
  }
  
  public static class Builder extends BaseContentBlockDTO.Builder {
    public ContentBlockDTO build() {
      return new ContentBlockDTO(this);
    }
  }
}
