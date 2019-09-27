package com.hackathon.coverbe.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RouteStop.RouteStopId.class)
public class RouteStop implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Route route;

    @Id
    @ManyToOne
    @JoinColumn
    private StopPosition stop;

    private Integer position;

    public Route getRoute() {
        return route;
    }

    public StopPosition getStop() {
        return stop;
    }

    public Integer getPosition() {
        return position;
    }

    public RouteStop(StopPosition stop, Integer position) {
        this.stop = stop;
        this.position = position;
    }

    public static class RouteStopId implements Serializable {

        private Route route;
        private StopPosition stop;

        public RouteStopId(Route route, StopPosition stop) {
            this.route = route;
            this.stop = stop;
        }

        public RouteStopId() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RouteStop)) return false;
            RouteStop that = (RouteStop) o;
            return Objects.equals(stop.getName(), that.stop.getName()) &&
                    Objects.equals(route.getName(), that.route.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(stop.getName(), route.getName());
        }
    }
}
