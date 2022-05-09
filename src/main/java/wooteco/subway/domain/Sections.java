package wooteco.subway.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sections {

    private final List<Section> values;

    public Sections(List<Section> sections) {
        values = sort(new ArrayList<>(sections));
    }

    private List<Section> sort(List<Section> sections) {
        List<Section> values = new ArrayList<>();
        Station firstStation = findFirstStation(sections);
        while (values.size() != sections.size()) {
            for (Section section : sections) {
                if (section.getUpStation().equals(firstStation)) {
                    values.add(section);
                    firstStation = section.getDownStation();
                }
            }
        }

        return values;
    }

    private Station findFirstStation(List<Section> sections) {
        List<Station> upStations = createUpStations(sections);
        List<Station> downStations = createDownStations(sections);

        return upStations.stream()
            .filter(upStation -> !downStations.contains(upStation))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    private List<Station> createDownStations(List<Section> sections) {
        return sections.stream()
            .map(Section::getDownStation)
            .collect(Collectors.toList());
    }

    private List<Station> createUpStations(List<Section> sections) {
        return sections.stream()
            .map(Section::getUpStation)
            .collect(Collectors.toList());
    }

    public List<Section> getValues() {
        return List.copyOf(values);
    }
}
