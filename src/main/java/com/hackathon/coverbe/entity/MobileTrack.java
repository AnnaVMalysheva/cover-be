package com.hackathon.coverbe.entity;

import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mobile_track")
public class MobileTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mobile_track_ids_gen")
    @SequenceGenerator(name = "mobile_track_ids_gen", sequenceName = "mobile_track_id_seq", allocationSize = 1)
    private Long id;

    private Point coordinates;

    @Column(name = "track_date")
    Long trackDate;
    String operator;
    String level;
    @Column(name = "type_conn")
    String typeConnection;
    @Column(name = "mobile_id")
    String mobileId;
}
