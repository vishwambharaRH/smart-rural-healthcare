package com.healthcare.healthcare_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.healthcare_system.model.CampSchedule;

@Repository
public interface CampScheduleRepository extends JpaRepository<CampSchedule, Long> {
}

