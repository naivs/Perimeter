package org.naivs.perimeter.smarthome.data.repository;

import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findPhotoEntityByNameAndPath(String name, String path);

    List<Photo> findByPath(String path);
}
