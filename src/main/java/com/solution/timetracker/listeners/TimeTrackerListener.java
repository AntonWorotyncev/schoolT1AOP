package com.solution.timetracker.listeners;

import com.solution.timetracker.event.ExecutionTimeEvent;
import com.solution.timetracker.service.TimeTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeTrackerListener implements ApplicationListener<ExecutionTimeEvent> {

    private final TimeTrackerService timeTrackerService;

    @Override
    public void onApplicationEvent(ExecutionTimeEvent event) {
        timeTrackerService.saveExecutionTime(event.getMethodName(),event.getMethodArgs(), event.getExecutionTime());
    }
}
