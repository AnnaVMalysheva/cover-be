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
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_records_ids_gen")
    @SequenceGenerator(name = "app_records_ids_gen", sequenceName = "app_records_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Point coordinates;

    private String date;

    private Long phoneId;

}
