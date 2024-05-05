package com.solution.timetracker.controller;

import com.solution.timetracker.controllers.TimeTrackerController;
import com.solution.timetracker.dto.ExecutionTimeStatsDto;
import com.solution.timetracker.service.CountryService;
import com.solution.timetracker.service.TimeTrackerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeTrackerController.class)
public class TimeTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private TimeTrackerService timeTrackerService;

    @Test
    public void getExecutionTimeStatsTest() throws Exception {
        ExecutionTimeStatsDto sampleStatsDto = new ExecutionTimeStatsDto();
        sampleStatsDto.setAverageExecutionTimeByMethod(Map.of("methodA", 10.0));
        sampleStatsDto.setTotalExecutionTimeByMethod(Map.of("methodA",20L));
        when(timeTrackerService.getExecutionTimeStats()).thenReturn(sampleStatsDto);

        mockMvc.perform(get("/api/timeTracker/stats"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.averageExecutionTimeByMethod['methodA']").value(10.0))
               .andExpect(jsonPath("$.totalExecutionTimeByMethod['methodA']").value(20));
    }
}
