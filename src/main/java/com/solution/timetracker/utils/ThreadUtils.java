package com.solution.timetracker.utils;

public class ThreadUtils {
    private ThreadUtils(){}

    public static void awaitTime(long time){
        try{
            Thread.sleep(time);
        } catch (InterruptedException ex){
            throw new RuntimeException(ex);
        }
    }
}
