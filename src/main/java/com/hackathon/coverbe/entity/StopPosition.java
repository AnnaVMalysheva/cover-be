package com.hackathon.coverbe.entity;

import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StopPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_stop_position_ids_gen")
    @SequenceGenerator(name = "app_stop_position_ids_gen", sequenceName = "app_stop_position_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Point coordinates;

    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL)
    private Set<RouteStop> routes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public Set<RouteStop> getRoutes() {
        return routes;
    }

    public StopPosition(String name, Point coordinates, Set<RouteStop> routes) {
        this.name = name;
        this.coordinates = coordinates;
        this.routes = routes;
    }
}