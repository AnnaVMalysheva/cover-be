package com.hackathon.coverbe.repository;

import com.hackathon.coverbe.entity.MobileTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileTrackRepository extends JpaRepository<MobileTrack, Long> {
    List<MobileTrack> findByOperatorLike(String operator);
}
