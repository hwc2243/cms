package com.github.hwc2243.cms.service.base;

import com.github.hwc2243.cms.dto.base.BaseContentBlockDTO;
import com.github.hwc2243.cms.dto.ContentBlockDTO;
import com.github.hwc2243.cms.entity.base.BaseContentBlockEntity;
import com.github.hwc2243.cms.entity.ContentBlockEntity;
import com.github.hwc2243.cms.model.ContentBlockBlockType;
import com.github.hwc2243.cms.model.ContentBlockContentState;
import com.github.hwc2243.cms.persistence.base.BaseContentBlockPersistence;
import com.github.hwc2243.cms.persistence.ContentBlockPersistence;
import com.github.hwc2243.cms.service.event.CrudOperation;
import com.github.hwc2243.cms.service.event.EntityCrudEvent;
import com.github.hwc2243.cms.service.event.ServiceEvent;
import com.github.hwc2243.cms.service.event.ServiceEventBus;
import com.github.hwc2243.cms.service.ServiceException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseContentBlockServiceImpl<D extends ContentBlockDTO, E extends ContentBlockEntity, ID>
  implements BaseContentBlockService<D, ID> {

  @Autowired
  private BaseContentBlockPersistence<E, ID> baseContentBlockPersistence;

  @Autowired
  protected ContentBlockPersistence contentBlockPersistence;

  @Autowired(required = false)
  protected ServiceEventBus serviceEventBus;


    @Override
  @Transactional
  public D create (D dto) throws ServiceException
  {
    E entity = toEntity(dto);
    E saved = baseContentBlockPersistence.save(entity);
    D savedDto = toDto(saved);
    publishCrudEvent(CrudOperation.CREATED, savedDto);
    return savedDto;
  }
  
  @Override
  @Transactional
  public void delete (ID id) throws ServiceException
  {
    D deletedDto = serviceEventBus == null ? null : get(id);
    baseContentBlockPersistence.deleteById(id);
    if (deletedDto != null) {
      publishCrudEvent(CrudOperation.DELETED, deletedDto);
    }
  }
  
  @Override
  public List<D> findAll () throws ServiceException
  {
    List<E> entities = baseContentBlockPersistence.findAll();
    return toDtos(entities);
  }

  @Override
  public List<D> findByPageIdAndContentStateAndSortOrder (Long pageId, ContentBlockContentState contentState, Integer sortOrder)
  {
	return toDtos(baseContentBlockPersistence.findByPageIdAndContentStateAndSortOrder(pageId, contentState, sortOrder));
  }


  @Override
  public D get (ID id) throws ServiceException
  {
    Optional<E> optional = baseContentBlockPersistence.findById(id);

    return optional.isEmpty() ? null : toDto(optional.get());
  }

  
  @Override
  @Transactional
  public D update (D dto) throws ServiceException
  {
    E entity = toEntity(dto);
    E saved = baseContentBlockPersistence.save(entity);
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
      publishEvent(new EntityCrudEvent<D>(operation, "contentBlock", dto.getId(), dto));
    }
  }

  protected abstract E toEntity (D dto);
  protected abstract List<E> toEntities (List<D> dtos);

  protected abstract D toDto (E entity);
  protected abstract List<D> toDtos (List<E> entities);
}
