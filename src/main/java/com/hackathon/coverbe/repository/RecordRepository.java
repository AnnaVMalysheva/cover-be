package com.hackathon.coverbe.repository;

import com.hackathon.coverbe.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "select ranked_scores.id, ranked_scores.coordinates, ranked_scores.date, ranked_scores.phone_id from\n" +
            "    (SELECT *,rank() OVER (PARTITION BY phone_id ORDER BY id DESC)\n" +
            "     FROM record) ranked_scores\n" +
            "where ranked_scores.rank <=5", nativeQuery = true)
    List<Record> findReports();
}
