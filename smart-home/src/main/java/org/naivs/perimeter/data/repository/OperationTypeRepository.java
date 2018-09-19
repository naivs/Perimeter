package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.OperationTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends CrudRepository<OperationTypeEntity, Long> {
}
