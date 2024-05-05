package com.solution.timetracker.service;

import com.solution.timetracker.dto.Country;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CountryService {

    CompletableFuture<String> fetchDataAsync();

    void addCountry(Integer key, String value);

    List<Country> addAndReturnCountries(List <Country> newCountries);

    void removeCountryByKey(Integer key);

    String getCountryByKey(Integer key);

    boolean containsCountryByKey(Integer key);
}


