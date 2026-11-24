package com.github.hwc2243.cms.service.base;


import com.github.hwc2243.cms.dto.base.BaseContentPageDTO;
import com.github.hwc2243.cms.model.ContentPageStatus;
import com.github.hwc2243.cms.service.ServiceException;
import java.time.LocalDateTime;
import java.util.List;

public interface BaseContentPageService<D extends BaseContentPageDTO, ID> extends EntityService<D, ID> {

	public D fetchBySlug (String slug);

	public List<D> findByStatus (ContentPageStatus status);
}
