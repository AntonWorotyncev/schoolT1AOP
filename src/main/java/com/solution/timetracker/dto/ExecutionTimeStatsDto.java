package com.solution.timetracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Статистика времени выполнения методов")
public class ExecutionTimeStatsDto {
    @Schema(description = "Общее время выполнения метода в наносекундах",
            example = "addCountry: 9555.5", required = true)
    private Map<String, Long> totalExecutionTimeByMethod;
    @Schema(description = "Среднее время выполнения метода в наносекундах",
            example = "addCountry: 9555.5", required = true)
    private Map<String, Double> averageExecutionTimeByMethod;
}

