package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.IndicationLogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicationLogRepository extends CrudRepository<IndicationLogEntity, Long> {
}
