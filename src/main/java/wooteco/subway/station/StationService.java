package wooteco.subway.station;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import wooteco.subway.exception.station.StationDuplicateException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public StationResponse create(String name) {
        StationName stationName = new StationName(name);
        try {
            return new StationResponse(stationDao.insert(stationName));
        } catch (DataIntegrityViolationException e) {
            throw new StationDuplicateException();
        }
    }

    public List<StationResponse> showStations() {
        List<Station> stations = stationDao.findAll();
        return stations.stream()
                .map(StationResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        stationDao.deleteById(id);
    }
}
