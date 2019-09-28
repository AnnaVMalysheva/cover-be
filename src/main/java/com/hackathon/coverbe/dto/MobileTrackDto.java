package com.hackathon.coverbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileTrackDto {
    Double lat;
    Double lng;
    Long trackDate;
    String operator;
    String level;
    String typeConnection;
    String mobileId;
}
