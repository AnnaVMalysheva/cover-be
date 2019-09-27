package com.hackathon.coverbe.mapper;

import com.hackathon.coverbe.dto.StopPositionDto;
import com.hackathon.coverbe.entity.StopPosition;

import java.util.List;
import java.util.stream.Collectors;

public class StopPositionMapper {

    public static StopPositionDto asDto(StopPosition stop) {
        return StopPositionDto.builder()
                .latlng(new Double[] {stop.getCoordinates().getX(), stop.getCoordinates().getY()})
                .name(stop.getName())
                .build();
    }

    public static List<StopPositionDto> asDtos(List<StopPosition> stops) {
        return stops.stream().map(StopPositionMapper::asDto).collect(Collectors.toList());
    }
}
