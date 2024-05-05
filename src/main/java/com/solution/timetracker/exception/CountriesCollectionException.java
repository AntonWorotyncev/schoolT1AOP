package com.solution.timetracker.exception;

import com.solution.timetracker.annotations.Trows;

@Trows
public class CountriesCollectionException extends RuntimeException {
    public CountriesCollectionException(String message) {
        super(message);
    }
}
