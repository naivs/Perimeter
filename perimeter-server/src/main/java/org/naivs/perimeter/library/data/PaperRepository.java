package org.naivs.perimeter.library.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends CrudRepository<PaperEntity, Long> {

    /**
     * Get all paper entities
     * @return List of paper entities
     */
    @Query(value = "SELECT p FROM PaperEntity p")
    List<PaperEntity> findAll();
}
