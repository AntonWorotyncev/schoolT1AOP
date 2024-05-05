package com.solution.timetracker.controllers;

import com.solution.timetracker.dto.ExecutionTimeStatsDto;
import com.solution.timetracker.service.TimeTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/timeTracker")
@RequiredArgsConstructor
@Tag(name = "Time Tracker", description = "Cистема учета времени выполнения методов")
public class TimeTrackerController {

    private final TimeTrackerService timeTrackerService;

    @Operation(summary = "Получить статистику времени выполнения",
            description = "Возвращает статистику времени выполнения методов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
    })

    @GetMapping("/stats")
    public ExecutionTimeStatsDto getExecutionTimeStats(){
        return timeTrackerService.getExecutionTimeStats();
    }
}
