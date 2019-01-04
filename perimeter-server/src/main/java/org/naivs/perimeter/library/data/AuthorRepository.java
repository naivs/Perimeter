package org.naivs.perimeter.library.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    /**
     * Get all authors
     * @return List of AuuthorEntities
     */
    List<AuthorEntity> findAll();

    /**
     * Get author entity by ID
     * @param id author identifier
     * @return Author entity
     */
    AuthorEntity getById(Long id);

    /**
     * Get author wntity by name. Name consists of FIO, and must be unique
     * @param name author's FIO
     * @return Author entity
     */
    AuthorEntity getByName(String name);
}