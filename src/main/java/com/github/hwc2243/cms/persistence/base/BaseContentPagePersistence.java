package com.github.hwc2243.cms.persistence.base;




import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.model.ContentPageStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseContentPagePersistence<E extends ContentPageEntity, ID> extends JpaRepository<E, ID>
{


    public E findFirstBySlug(String slug);


    public List<E> findByStatus(ContentPageStatus status);


}
