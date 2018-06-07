package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.DeviceTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository extends CrudRepository<DeviceTypeEntity, Long> {
}
