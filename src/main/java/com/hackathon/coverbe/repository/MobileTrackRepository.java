package com.hackathon.coverbe.repository;

import com.hackathon.coverbe.entity.MobileTrack;
import com.hackathon.coverbe.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileTrackRepository extends JpaRepository<MobileTrack, Long> {

}
