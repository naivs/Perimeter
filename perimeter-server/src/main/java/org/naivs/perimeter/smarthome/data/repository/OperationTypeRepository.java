package org.naivs.perimeter.smarthome.data.repository;

import org.naivs.perimeter.smarthome.data.entity.OperationTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends CrudRepository<OperationTypeEntity, Long> {
}
