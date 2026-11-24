package com.github.hwc2243.cms.service.base;


import com.github.hwc2243.cms.dto.base.BaseContentBlockDTO;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import com.github.hwc2243.cms.service.ServiceException;
import java.util.List;

public interface BaseContentBlockService<D extends BaseContentBlockDTO, ID> extends EntityService<D, ID> {

	public List<D> findByPageIdAndContentStateAndSortOrder (Long pageId, ContentBlockContentState contentState, Integer sortOrder);
}
