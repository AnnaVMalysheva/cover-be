package com.hackathon.coverbe.controller;

import com.hackathon.coverbe.dto.MobileTrackDto;
import com.hackathon.coverbe.service.LoadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MapController {

    private final LoadService loadService;

    @PostMapping("/mobiletrack")
    public void addMobileTrack(@RequestBody MobileTrackDto mobileTrackDto) {
        loadService.saveMobileTrack(mobileTrackDto);
    }

    @GetMapping("/mobiletrack")
    public List<MobileTrackDto> getMobileTracks(@RequestParam("operator") String operator) {
        return loadService.getMobileTracks(operator);
    }

    @GetMapping("/test")
    public void test() {
        loadService.hardcodeMobileTracks();
    }
}