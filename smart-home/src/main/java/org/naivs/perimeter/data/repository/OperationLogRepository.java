package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.OperationLogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogRepository extends CrudRepository<OperationLogEntity, Long> {
}
