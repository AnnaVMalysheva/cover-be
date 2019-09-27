package com.hackathon.coverbe.controller;

import com.hackathon.coverbe.dto.RecordDto;
import com.hackathon.coverbe.dto.StopPositionDto;
import com.hackathon.coverbe.service.LoadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MapController {

    private final LoadService loadService;

    @PostMapping("/coordinates")
    public void uploadCoordinates(@RequestParam("file") MultipartFile file) throws IOException {
        loadService.uploadRouteCoordinates(file.getInputStream());
    }

    @GetMapping("/coordinates")
    public Set<RecordDto> getCoordinates() throws IOException {
        return loadService.getCoordinates();
    }

    @PostMapping("/coordinatesFromResources")
    public void uploadCoordinatesFromResources() throws IOException {
        loadService.uploadCoordinatesFromResources();
    }

    @PostMapping("/routeFromResources")
    public void uploadRelationsFromResources() throws IOException {
        loadService.uploadRoutesFromResources();
    }

    @GetMapping("/coordinatesByRouteId")
    public StopPositionDto getCoordinatesByRouteId(@RequestParam("routeId") String routeId) {
        return loadService.getCoordinatesByRouteId(routeId);
    }

    @GetMapping("/coordinatesByRoute")
    public List<StopPositionDto> getCoordinatesByRoute(@RequestParam("routeId") String routeId) {
        return loadService.getCoordinatesByRouteName(routeId);
    }

    @GetMapping("/allRoutes")
    public Map<String, List<StopPositionDto>> getAllRoutes() {
        return loadService.getAllRoutes();
    }
}