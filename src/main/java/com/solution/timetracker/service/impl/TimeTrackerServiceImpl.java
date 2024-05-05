package com.solution.timetracker.service.impl;

import com.solution.timetracker.dto.ExecutionTimeStatsDto;
import com.solution.timetracker.entities.TimeTrackingRecord;
import com.solution.timetracker.repository.TimeTrackingRepository;
import com.solution.timetracker.service.TimeTrackerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeTrackerServiceImpl implements TimeTrackerService {

    private final TimeTrackingRepository timeTrackingRepository;
    @Override
    public void saveExecutionTime(String methodName, Object[] methodArgs, long executionTime) {
        TimeTrackingRecord record = TimeTrackingRecord
                .builder().methodName(methodName).executionTime(executionTime).build();
        CompletableFuture.runAsync(() -> timeTrackingRepository.save(record))
                        .thenRun(() -> {
            log.info("Метод {} с параметрами {} был успешно сохранен в базу данных", methodName, methodArgs);
        });
    }

    @Override
    public ExecutionTimeStatsDto getExecutionTimeStats() {

        List<TimeTrackingRecord> records = timeTrackingRepository.findAll();

        Map<String, Long> totalExecutionTimeByMethod = new HashMap<>();
        Map<String, Integer> countByMethod = new HashMap<>();

        records.forEach(record -> {
            String methodName = record.getMethodName();
            long executionTime = record.getExecutionTime();

            totalExecutionTimeByMethod.put(
                    methodName,
                    totalExecutionTimeByMethod.getOrDefault(methodName, 0L) + executionTime
            );

            countByMethod.put(
                    methodName,
                    countByMethod.getOrDefault(methodName, 0) + 1
            );
        });

        Map<String, Double> averageExecutionTimeByMethod = totalExecutionTimeByMethod.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / countByMethod.get(entry.getKey())
                ));

        return new ExecutionTimeStatsDto(totalExecutionTimeByMethod, averageExecutionTimeByMethod);
    }
}

