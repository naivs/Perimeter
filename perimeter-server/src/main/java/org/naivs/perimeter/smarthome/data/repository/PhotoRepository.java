package org.naivs.perimeter.smarthome.data.repository;

import org.naivs.perimeter.smarthome.data.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

    Optional<PhotoEntity> findPhotoEntityByNameAndPath(String name, String path);

    Optional<PhotoEntity> findByPath(String path);
}
