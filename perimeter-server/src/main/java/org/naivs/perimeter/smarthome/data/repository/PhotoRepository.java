package org.naivs.perimeter.smarthome.data.repository;

import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findPhotoByFilenameAndPath(String name, String path);

    Optional<Photo> findPhotoById(Long id);

    List<Photo> findAllByPath(String path);

    @Query(value = "SELECT * FROM public.photo P " +
            "WHERE P.id in ( " +
            "    SELECT INDX.photo_id " +
            "    FROM public.photo_indexes INDX JOIN public.photo_index IND " +
            "ON INDX.photo_index_id = IND.id " +
            "    WHERE IND.name in :indexes)", nativeQuery = true)
    List<Photo> findByIndexes(@Param(value = "indexes") String[] indexes);
}
