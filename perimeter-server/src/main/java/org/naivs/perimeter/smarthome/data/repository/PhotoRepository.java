package org.naivs.perimeter.smarthome.data.repository;

import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findPhotoByFilenameAndPath(String name, String path);

    List<Photo> findAllByPath(String path);

    @Query(value = "SELECT * " +
            "FROM public.photo p " +
            "WHERE p.id in (" +
            "SELECT pies.photo_id " +
            "FROM public.photo_indexes pies, public.photo_index pi " +
            "WHERE pies.indexes_id=pi.id AND pi.name=:index)", nativeQuery = true)
    List<Photo> findByIndex(@Param(value = "index") String index);
}
