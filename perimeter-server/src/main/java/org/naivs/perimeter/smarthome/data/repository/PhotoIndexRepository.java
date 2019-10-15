package org.naivs.perimeter.smarthome.data.repository;

import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PhotoIndexRepository extends JpaRepository<PhotoIndex, Long> {

    Set<PhotoIndex> findByNameIn(List<String> names);
}
