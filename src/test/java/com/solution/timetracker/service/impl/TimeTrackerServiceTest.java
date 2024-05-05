package com.solution.timetracker.service.impl;

import com.solution.timetracker.dto.ExecutionTimeStatsDto;
import com.solution.timetracker.entities.TimeTrackingRecord;
import com.solution.timetracker.repository.TimeTrackingRepository;
import com.solution.timetracker.service.TimeTrackerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TimeTrackerServiceTest {

   @Mock
   private TimeTrackingRepository timeTrackingRepository;

        @InjectMocks
        private TimeTrackerServiceImpl timeTrackerService;

        @Test
        public void testSaveExecutionTime() {
            String methodName = "someMethod";
            Object[] methodArgs = { "arg1", 123, true };
            long executionTime = 1000;

            timeTrackerService.saveExecutionTime(methodName, methodArgs, executionTime);

            // Убедимся, что репозиторий был вызван для сохранения записи
            // Используем задержку, чтобы убедиться, что асинхронный код завершил свою работу.
            CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
                verify(timeTrackingRepository, timeout(1000)).save(any(TimeTrackingRecord.class));
            })).join();
        }

        @Test
        public void testGetExecutionTimeStats() {

            TimeTrackingRecord record1 = TimeTrackingRecord.builder().methodName("methodA").executionTime(500).build();
            TimeTrackingRecord record2 = TimeTrackingRecord.builder().methodName("methodA").executionTime(1000).build();
            TimeTrackingRecord record3 = TimeTrackingRecord.builder().methodName("methodB").executionTime(2000).build();

            Mockito.when(timeTrackingRepository.findAll()).thenReturn(Arrays.asList(record1, record2, record3));

            ExecutionTimeStatsDto statsDto = timeTrackerService.getExecutionTimeStats();

            Map <String, Long> expectedTotalExecutionTimeByMethod = new HashMap<>();
            expectedTotalExecutionTimeByMethod.put("methodA", 1500L);
            expectedTotalExecutionTimeByMethod.put("methodB", 2000L);

            Map<String, Double> expectedAverageExecutionTimeByMethod = new HashMap<>();
            expectedAverageExecutionTimeByMethod.put("methodA", 750.0);
            expectedAverageExecutionTimeByMethod.put("methodB", 2000.0);

            Assertions.assertEquals(expectedTotalExecutionTimeByMethod, statsDto.getTotalExecutionTimeByMethod());
            Assertions.assertEquals(expectedAverageExecutionTimeByMethod, statsDto.getAverageExecutionTimeByMethod());
        }
    }
