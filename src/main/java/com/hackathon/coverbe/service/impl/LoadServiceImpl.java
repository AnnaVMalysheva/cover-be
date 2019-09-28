package com.hackathon.coverbe.service.impl;

import com.hackathon.coverbe.dto.MobileTrackDto;
import com.hackathon.coverbe.dto.RecordDto;
import com.hackathon.coverbe.dto.StopPositionDto;
import com.hackathon.coverbe.entity.*;
import com.hackathon.coverbe.mapper.MobileTrackMapper;
import com.hackathon.coverbe.mapper.StopPositionMapper;
import com.hackathon.coverbe.repository.*;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.hackathon.coverbe.mapper.StopPositionMapper.asDto;
import static com.hackathon.coverbe.mapper.StopPositionMapper.asDtos;


@Service
@Slf4j
public class LoadServiceImpl implements LoadService {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    private final RecordRepository recordRepository;
    private final StopPositionRepository stopPositionRepository;
    private final RouteRepository routeRepository;
    private final RouteStopRepository routeStopRepository;
    private final MobileTrackRepository mobileTrackRepository;

    public LoadServiceImpl(RecordRepository recordRepository, StopPositionRepository stopPositionRepository,
                           RouteRepository routeRepository, RouteStopRepository routeStopRepository, MobileTrackRepository mobileTrackRepository) {
        this.recordRepository = recordRepository;
        this.stopPositionRepository = stopPositionRepository;
        this.routeRepository = routeRepository;
        this.routeStopRepository = routeStopRepository;
        this.mobileTrackRepository = mobileTrackRepository;
    }

    @Override
    @Async
    public void uploadCoordinates(InputStream inputStream) throws IOException {
        CSVParser csvParser = CSVFormat.TDF.withFirstRecordAsHeader().parse(new InputStreamReader(inputStream));
        List<Record> records = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            String[] result = record.get(0).split("\\s+");

            Point point = geometryFactory.createPoint(new Coordinate(Double.valueOf(result[3]), Double.valueOf(result[4])));
            records.add(Record.builder().coordinates(point).phoneId(Long.valueOf(result[0])).date(result[1]).build());

        }
        recordRepository.saveAll(records);
    }

    @Override
    @Async
    public void uploadRouteCoordinates(InputStream inputStream) throws IOException {
        CSVParser csvParser = CSVFormat.TDF.withFirstRecordAsHeader().parse(new InputStreamReader(inputStream));
        List<Record> records = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            String[] result = record.get(0).split("\\s+");

            Point point = geometryFactory.createPoint(new Coordinate(Double.valueOf(result[1]), Double.valueOf(result[2])));
            records.add(Record.builder().coordinates(point).name(result[0]).build());

        }
        recordRepository.saveAll(records);
    }

    @Override
    public Set<RecordDto> getCoordinates() {
//        List<Point> records = recordRepository.findReports();
//        return records.stream().map(record -> RecordDto.builder().lan(record.getX()).log(record.getY()).build()).collect(Collectors.toList());
        //List<Record> records = recordRepository.findReports();
        List<Record> records = recordRepository.findAll();
        return records.stream().map(record ->
                RecordDto.builder()
                        .latlng(new Double[]{record.getCoordinates().getX(), record.getCoordinates().getY()})
                        .name(record.getName())
                        .build()).collect(Collectors.toSet());

    }

    @Override
    public void uploadCoordinatesFromResources() throws IOException {
        CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(new ClassPathResource("files/busstops.csv").getInputStream()));
        List<StopPosition> records = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            Point point = geometryFactory.createPoint(new Coordinate(Double.parseDouble(record.get(1)), Double.parseDouble(record.get(2))));
            records.add(StopPosition.builder().coordinates(point).name(record.get(0)).build());
        }
        stopPositionRepository.saveAll(records);
    }

    @Override
    public StopPositionDto getCoordinatesByRouteId(String routeId) {
        StopPosition stop = stopPositionRepository.findByNameIs(routeId);
        return asDto(stop);
    }

    @Override
    public List<StopPositionDto> getCoordinatesByRouteName(String routeId) {
        Route byNameIs = routeRepository.findByNameIs(routeId);
        List<StopPosition> stops = routeStopRepository.findByRouteOrderByPosition(byNameIs)
                .stream().map(RouteStop::getStop).collect(Collectors.toList());
        return asDtos(stops);
    }

    @Override
    public void uploadRoutesFromResources() throws IOException {
        CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(new ClassPathResource("files/busRelation.csv").getInputStream()));
        List<Route> records = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            Route route = new Route();
            route.setName(record.get(0));
            int length = record.size() - 1;

            Iterator<String> iterator = record.iterator();
            iterator.next();
            Integer index = 0;
            while (iterator.hasNext()) {
                String stop = iterator.next();
                StopPosition stopPosition = stopPositionRepository.findByNameIs(stop);
                if (stopPosition != null) {
                    route.addStop(new RouteStop(stopPosition, index));
                    index++;
                }
            }
            records.add(route);
            routeRepository.save(route);
        }
        //routeRepository.saveAll(records);
    }

    @Override
    public Map<String, List<StopPositionDto>> getAllRoutes() {
        /*List<Route> allRoutes = routeRepository.findRoutes();
        return allRoutes.stream().collect(
                Collectors.toMap(Route::getName, route -> StopPositionMapper.asDtos(route.getStops())));*/
        Map<String, List<StopPositionDto>> result = new HashMap<>();
        Route byNameIs = routeRepository.findByNameIs("2924444");
        List<StopPosition> stops = routeStopRepository.findByRouteOrderByPosition(byNameIs)
                .stream().map(RouteStop::getStop).collect(Collectors.toList());
        result.put(byNameIs.getName(), StopPositionMapper.asDtos(stops));
        return result;
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
    public List<MobileTrackDto> getMobileTracks() {
       return MobileTrackMapper.asDtos(mobileTrackRepository.findAll());
    }
}