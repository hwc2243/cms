package com.github.hwc2243.cms.persistence.base;




import com.github.hwc2243.cms.entity.ContentBlockEntity;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseContentBlockPersistence<E extends ContentBlockEntity, ID> extends JpaRepository<E, ID>
{


    public List<E> findByPageIdAndContentStateAndSortOrder(Long pageId, ContentBlockContentState contentState, Integer sortOrder);


}
