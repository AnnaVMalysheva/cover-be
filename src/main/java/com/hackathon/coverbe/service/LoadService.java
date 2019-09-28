package com.hackathon.coverbe.service;


import com.hackathon.coverbe.dto.MobileTrackDto;
import com.hackathon.coverbe.dto.RecordDto;
import com.hackathon.coverbe.dto.StopPositionDto;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LoadService {
    void uploadCoordinates(InputStream inputStream) throws IOException;

    @Async
    void uploadRouteCoordinates(InputStream inputStream) throws IOException;

    Set<RecordDto> getCoordinates();

    void uploadCoordinatesFromResources() throws IOException;

    StopPositionDto getCoordinatesByRouteId(String routeId);

    List<StopPositionDto> getCoordinatesByRouteName(String routeId);

    void uploadRoutesFromResources() throws IOException;

    Map<String, List<StopPositionDto>> getAllRoutes();

    void saveMobileTrack(MobileTrackDto mobileTrackDto);

    List<MobileTrackDto> getMobileTracks(String operator);

    void hardcodeMobileTracks();
}
