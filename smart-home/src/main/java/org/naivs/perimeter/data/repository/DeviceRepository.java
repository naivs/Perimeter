package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {

    DeviceEntity getById(Long id);

    DeviceEntity findByTypeIdAndDescription(Long typeId, String description);
}
