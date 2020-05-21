package wooteco.subway.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.admin.dto.controller.request.LineStationControllerRequest;
import wooteco.subway.admin.dto.controller.response.LineStationControllerResponse;
import wooteco.subway.admin.dto.service.response.LineStationServiceResponse;
import wooteco.subway.admin.dto.service.response.StationServiceResponse;
import wooteco.subway.admin.dto.view.request.LineStationCreateViewRequest;
import wooteco.subway.admin.service.LineService;
import wooteco.subway.admin.service.StationService;

import javax.validation.Valid;

@RestController
@RequestMapping("lines")
public class LineStationController {
	private final LineService lineService;
	private final StationService stationService;

	public LineStationController(LineService lineService, StationService stationService) {
		this.lineService = lineService;
		this.stationService = stationService;
	}

	@PostMapping("{lineId}/stations")
	public ResponseEntity<LineStationControllerResponse> createLineStation(
			@PathVariable("lineId") Long lineId,
			@RequestBody @Valid LineStationCreateViewRequest view) {
		lineService.addLineStation(lineId, LineStationControllerRequest.of(view));
		StationServiceResponse stationResponse = stationService.findBy(view.getStationId());
		LineStationServiceResponse response = new LineStationServiceResponse(lineId, stationResponse);

		return ResponseEntity
				.ok()
				.body(LineStationControllerResponse.of(response));
	}

	@DeleteMapping("{lineId}/stations/{stationId}")
	public ResponseEntity<Void> deleteLineStation(@PathVariable("lineId") Long lineId, @PathVariable("stationId") Long stationId) {
		lineService.removeLineStation(lineId, stationId);

		return ResponseEntity
				.noContent()
				.build();
	}
}
