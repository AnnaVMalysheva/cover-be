package com.hackathon.coverbe.repository;


import com.hackathon.coverbe.entity.StopPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopPositionRepository extends JpaRepository<StopPosition, Long> {
    StopPosition findByNameIs(String name);
    List<StopPosition> findByNameIn(List<String> names);
}
