package com.solution.timetracker.service;

import com.solution.timetracker.dto.ExecutionTimeStatsDto;
import org.aspectj.lang.Signature;

public interface TimeTrackerService {

    void saveExecutionTime(String methodName, Object[] methodArgs, long executionTime);


    ExecutionTimeStatsDto getExecutionTimeStats();
}
