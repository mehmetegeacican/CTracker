package com.example.server.service;

import com.example.server.model.Case;
import com.example.server.repository.CaseRepository;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CaseService {
    private final CaseRepository caseRepository;


    public CaseService(CaseRepository caseRepository){
        this.caseRepository = caseRepository;
    }

    /**
     * Gets the Cases based on the Criteria
     * @param locationName
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Case> getCases(String locationName, Date startDate, Date endDate){
        if(this.isLocationParameterValid(locationName) && this.areDateParametersValid(startDate,endDate)){
            return caseRepository.findByReportLocationAndReportDateBetween(locationName,startDate,endDate);
        } else if (this.isLocationParameterValid(locationName)) {
            return caseRepository.findByReportLocation(locationName);
        } else if (this.areDateParametersValid(startDate,endDate)) {
            return caseRepository.findByReportDateBetween(startDate,endDate);
        }
        else {
            return caseRepository.findAll();
        }
    }

    public boolean isLocationParameterValid(String locationName){
        return locationName != null && !locationName.isEmpty();
    }

    public boolean areDateParametersValid(Date startDate,Date endDate){
        return (startDate != null && endDate != null);
    }
}
