package com.healthcare.healthcare_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.CampSchedule;
import com.healthcare.healthcare_system.repository.CampScheduleRepository;

@Service
public class CampScheduleService {

    @Autowired
    private CampScheduleRepository campScheduleRepository;

    public List<CampSchedule> getAllCamps() {
        return campScheduleRepository.findAll();
    }

    public CampSchedule getCampById(Long id) {
        return campScheduleRepository.findById(id).orElse(null);
    }

    public CampSchedule saveCamp(CampSchedule camp) {
        return campScheduleRepository.save(camp);
    }

    public void deleteCamp(Long id) {
        campScheduleRepository.deleteById(id);
    }
}

