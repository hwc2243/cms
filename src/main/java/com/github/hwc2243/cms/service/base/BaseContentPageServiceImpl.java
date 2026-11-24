package com.github.hwc2243.cms.service.base;

import com.github.hwc2243.cms.dto.base.BaseContentPageDTO;
import com.github.hwc2243.cms.dto.ContentPageDTO;
import com.github.hwc2243.cms.entity.base.BaseContentPageEntity;
import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.model.ContentPageStatus;
import com.github.hwc2243.cms.persistence.base.BaseContentPagePersistence;
import com.github.hwc2243.cms.persistence.ContentPagePersistence;
import com.github.hwc2243.cms.service.event.CrudOperation;
import com.github.hwc2243.cms.service.event.EntityCrudEvent;
import com.github.hwc2243.cms.service.event.ServiceEvent;
import com.github.hwc2243.cms.service.event.ServiceEventBus;
import com.github.hwc2243.cms.service.ServiceException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseContentPageServiceImpl<D extends ContentPageDTO, E extends ContentPageEntity, ID>
  implements BaseContentPageService<D, ID> {

  @Autowired
  private BaseContentPagePersistence<E, ID> baseContentPagePersistence;

  @Autowired
  protected ContentPagePersistence contentPagePersistence;

  @Autowired(required = false)
  protected ServiceEventBus serviceEventBus;


    @Override
  @Transactional
  public D create (D dto) throws ServiceException
  {
    E entity = toEntity(dto);
    E saved = baseContentPagePersistence.save(entity);
    D savedDto = toDto(saved);
    publishCrudEvent(CrudOperation.CREATED, savedDto);
    return savedDto;
  }
  
  @Override
  @Transactional
  public void delete (ID id) throws ServiceException
  {
    D deletedDto = serviceEventBus == null ? null : get(id);
    baseContentPagePersistence.deleteById(id);
    if (deletedDto != null) {
      publishCrudEvent(CrudOperation.DELETED, deletedDto);
    }
  }
  
  @Override
  public List<D> findAll () throws ServiceException
  {
    List<E> entities = baseContentPagePersistence.findAll();
    return toDtos(entities);
  }

  @Override
  public D fetchBySlug (String slug)
  {
	return toDto(baseContentPagePersistence.findFirstBySlug(slug));
  }

  @Override
  public List<D> findByStatus (ContentPageStatus status)
  {
	return toDtos(baseContentPagePersistence.findByStatus(status));
  }


  @Override
  public D get (ID id) throws ServiceException
  {
    Optional<E> optional = baseContentPagePersistence.findById(id);

    return optional.isEmpty() ? null : toDto(optional.get());
  }

  
  @Override
  @Transactional
  public D update (D dto) throws ServiceException
  {
    E entity = toEntity(dto);
    E saved = baseContentPagePersistence.save(entity);
    D savedDto = toDto(saved);
    publishCrudEvent(CrudOperation.UPDATED, savedDto);
    return savedDto;
  }

  protected void publishEvent (ServiceEvent event)
  {
    if (serviceEventBus != null) {
      serviceEventBus.publish(event);
    }
  }

  protected void publishCrudEvent (CrudOperation operation, D dto)
  {
    if (dto != null) {
      publishEvent(new EntityCrudEvent<D>(operation, "contentPage", dto.getId(), dto));
    }
  }

  protected abstract E toEntity (D dto);
  protected abstract List<E> toEntities (List<D> dtos);

  protected abstract D toDto (E entity);
  protected abstract List<D> toDtos (List<E> entities);
}
