package com.solution.timetracker.service.impl;

import com.solution.timetracker.annotations.TrackAsyncTime;
import com.solution.timetracker.annotations.TrackTime;
import com.solution.timetracker.dto.Country;
import com.solution.timetracker.exception.CountriesCollectionException;
import com.solution.timetracker.service.CountryService;
import com.solution.timetracker.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    private static String SUCCESSFUL_TASK_EXECUTION = "Success";

    private final Map<Integer, String> countries = new HashMap<>();

    @TrackAsyncTime
    @Override
    public CompletableFuture<String> fetchDataAsync() {
        return CompletableFuture.supplyAsync(() -> {
                ThreadUtils.awaitTime(5000);
                return SUCCESSFUL_TASK_EXECUTION;
        });
    }

    @TrackTime
    @Override
    public void addCountry(Integer key, String value) {
        countries.put(key, value);
    }

    @Override
    public List<Country> addAndReturnCountries(List <Country> newCountries){
        if (newCountries.isEmpty()){
            throw new CountriesCollectionException("Переданная коллекция не содержит элементов");
        }
        countries.putAll(newCountries.stream().collect(Collectors.toMap(Country::id,Country::name)));
        return newCountries;
    }

    @TrackTime
    @Override
    public void removeCountryByKey(Integer key) {
        countries.remove(key);
    }
    @TrackTime
    @Override
    public String getCountryByKey(Integer key) {
        return countries.get(key);
    }

    @TrackTime
    @Override
    public boolean containsCountryByKey(Integer key) {
        return countries.containsKey(key);
    }
}

