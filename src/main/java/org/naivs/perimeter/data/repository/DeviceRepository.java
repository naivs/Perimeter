package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {

//    List<DeviceEntity> findAll();
}
