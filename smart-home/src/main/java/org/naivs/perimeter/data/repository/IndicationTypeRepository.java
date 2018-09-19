package org.naivs.perimeter.data.repository;

import org.naivs.perimeter.data.entity.IndicationTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicationTypeRepository extends CrudRepository<IndicationTypeEntity, Long> {
}
