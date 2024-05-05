package com.solution.timetracker.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ExecutionTimeEvent extends ApplicationEvent {
    private final String methodName;
    private final long executionTime;
    private final Object[] methodArgs;

    public ExecutionTimeEvent(Object source, String methodName, Object[] methodArgs, long executionTime) {
        super(source);
        this.methodName = methodName;
        this.executionTime = executionTime;
        this.methodArgs = methodArgs;
    }
}