package com.hackathon.coverbe.repository;


import com.hackathon.coverbe.entity.Route;
import com.hackathon.coverbe.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {

    List<RouteStop> findByRouteOrderByPosition(Route route);
}