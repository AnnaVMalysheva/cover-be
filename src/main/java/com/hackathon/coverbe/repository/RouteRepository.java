
package com.hackathon.coverbe.repository;

import com.hackathon.coverbe.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findByNameIs(String name);

    @Query(value = "SELECT * FROM public.route as r WHERE r.id in ( select rs.route_id  from route_stops as rs JOIN stop_position as s ON (rs.stop_id = s.id) group by route_id having count(*) >100 )", nativeQuery = true)
    List<Route> findRoutes();
}