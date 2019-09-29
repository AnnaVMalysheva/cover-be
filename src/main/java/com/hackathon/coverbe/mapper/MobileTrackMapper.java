package com.hackathon.coverbe.mapper;

import com.hackathon.coverbe.dto.MobileTrackDto;
import com.hackathon.coverbe.entity.MobileTrack;

import java.util.List;
import java.util.stream.Collectors;

public class MobileTrackMapper {

    public static MobileTrackDto asDto(MobileTrack mobileTrack) {
        return MobileTrackDto.builder()
                .lat(mobileTrack.getCoordinates().getX())
                .lng(mobileTrack.getCoordinates().getY())
                .level(mobileTrack.getLevel())
                .mobileId(mobileTrack.getMobileId())
                .operator(mobileTrack.getOperator())
                .trackDate(mobileTrack.getTrackDate())
                .typeConnection(mobileTrack.getTypeConnection())
                .build();
    }

    public static List<MobileTrackDto> asDtos(List<MobileTrack> mobileTracks) {
        return mobileTracks.stream().map(MobileTrackMapper::asDto).collect(Collectors.toList());
    }
}
