package com.solution.timetracker;

import com.solution.timetracker.dto.Country;
import com.solution.timetracker.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class OpenSchoolT1Application {

    private final CountryService countryService;

    public static void main(String[] args) {
        SpringApplication.run(OpenSchoolT1Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {

        countryService.addCountry(1, "Россия");
        countryService.fetchDataAsync();
        countryService.addCountry(2, "Германия");

        countryService.addAndReturnCountries(List.of(new Country(2, "Украина"),
                new Country(3, "Латвия"), new Country(4, "Литва"), new Country(5, "Эстония"),
                new Country(6, "Беларусь"), new Country(7, "Польша"),
                new Country(9, "Франция"), new Country(10, "Италия")));

        countryService.removeCountryByKey(2);

        countryService.containsCountryByKey(2);
        countryService.getCountryByKey(1);

       countryService.addAndReturnCountries(Collections.emptyList());
    }
}
