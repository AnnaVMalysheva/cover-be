package com.hackathon.coverbe.entity;

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
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_relation_ids_gen")
    @SequenceGenerator(name = "app_relation_ids_gen", sequenceName = "app_relation_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private Set<RouteStop> stops = new HashSet<>();

    public void addStop(RouteStop routeStop) {
        routeStop.setRoute(this);
        stops.add(routeStop);
    }
}