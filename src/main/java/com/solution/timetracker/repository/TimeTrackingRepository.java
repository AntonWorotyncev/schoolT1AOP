package com.solution.timetracker.repository;

import com.solution.timetracker.entities.TimeTrackingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackingRepository extends JpaRepository<TimeTrackingRecord, Long>{
}
