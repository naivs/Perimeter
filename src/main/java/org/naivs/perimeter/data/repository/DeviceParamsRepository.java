package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.DeviceParamsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceParamsRepository extends CrudRepository<DeviceParamsEntity, Long> {

    List<DeviceParamsEntity> getAllByDeviceId(Long deviceId);
}
