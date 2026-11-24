package com.github.hwc2243.cms.dto;

import com.github.hwc2243.cms.dto.base.BaseContentPageDTO;

public class ContentPageDTO extends BaseContentPageDTO
{
  private static final long serialVersionUID = 1L;

  public ContentPageDTO () {
  }
  
  public ContentPageDTO (Builder builder) {
    super(builder);
  }
  
  public static class Builder extends BaseContentPageDTO.Builder {
    public ContentPageDTO build() {
      return new ContentPageDTO(this);
    }
  }
}
