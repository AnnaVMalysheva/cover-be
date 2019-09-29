package com.hackathon.coverbe.service;


import com.hackathon.coverbe.dto.MobileTrackDto;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LoadService {
    void saveMobileTrack(MobileTrackDto mobileTrackDto);

    List<MobileTrackDto> getMobileTracks(String operator);

    void hardcodeMobileTracks();
}
