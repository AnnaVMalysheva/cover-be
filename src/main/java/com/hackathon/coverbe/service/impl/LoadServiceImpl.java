package com.hackathon.coverbe.service.impl;

import com.hackathon.coverbe.dto.MobileTrackDto;
import com.hackathon.coverbe.entity.MobileTrack;
import com.hackathon.coverbe.mapper.MobileTrackMapper;
import com.hackathon.coverbe.repository.MobileTrackRepository;
import com.hackathon.coverbe.service.LoadService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class LoadServiceImpl implements LoadService {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private final MobileTrackRepository mobileTrackRepository;

    public LoadServiceImpl(MobileTrackRepository mobileTrackRepository) {
        this.mobileTrackRepository = mobileTrackRepository;
    }

    @Override
    @Async
    public void saveMobileTrack(MobileTrackDto mobileTrackDto) {
        Point point = geometryFactory.createPoint(new Coordinate(mobileTrackDto.getLat(), mobileTrackDto.getLng()));
        mobileTrackRepository.save(MobileTrack.builder().coordinates(point)
                .trackDate(mobileTrackDto.getTrackDate()).level(mobileTrackDto.getLevel())
                .operator(mobileTrackDto.getOperator()).typeConnection(mobileTrackDto.getTypeConnection())
                .mobileId(mobileTrackDto.getMobileId()).build());
    }

    @Override
    public List<MobileTrackDto> getMobileTracks(String operator) {
       return MobileTrackMapper.asDtos(mobileTrackRepository.findByOperatorLike(operator));
    }

    @Override
    public void hardcodeMobileTracks() {
        List<MobileTrack> mobileTracks = new ArrayList<>();
        Double rangeMinX = 56.397818;
        Double rangeMaxX = 56.228216;
        Double rangeMinY = 43.709052;
        Double rangeMaxY = 44.292155;
        Double lat;
        Double lng;
        Integer level;
        Point point;
        Integer operatorId;
        String[] operators = {"Мегафон", "Теле2", "МТС", "Билайн"};
        Random r = new Random();
        for(int i = 0; i < 10000; i++) {
            lat = rangeMinX + (rangeMaxX - rangeMinX) * r.nextDouble();
            lng = rangeMinY + (rangeMaxY - rangeMinY) * r.nextDouble();
            operatorId = r.nextInt(4);
            level= r.nextInt(14);
            point = geometryFactory.createPoint(new Coordinate(lat, lng));
            mobileTracks.add(MobileTrack.builder().coordinates(point).trackDate(Instant.now().toEpochMilli()).
                    level(level.toString()).typeConnection("conn" + i).mobileId("mobile" + i).operator(operators[operatorId]).build());
        }
        mobileTrackRepository.saveAll(mobileTracks);
    }

}