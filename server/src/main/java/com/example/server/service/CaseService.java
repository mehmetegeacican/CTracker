package com.example.server.service;

import com.example.server.dto.CaseDto;
import com.example.server.model.Case;
import com.example.server.repository.CaseRepository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CaseService {
    private final CaseRepository caseRepository;
    private final MongoTemplate mongoTemplate;


    public CaseService(CaseRepository caseRepository,MongoTemplate mongoTemplate){
        this.caseRepository = caseRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Gets the Cases based on the Criteria
     * @param locationName
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Case> getCases(String locationName, Date startDate, Date endDate){
        Query query = new Query();
        // Step 1 -- Add the Queries if they are present
        if (isLocationParameterValid(locationName)) {
            query.addCriteria(Criteria.where("reportLocation").is(locationName));
        }
        if (areDateParametersValid(startDate, endDate)) {
            query.addCriteria(Criteria.where("reportDate").gte(startDate).lte(endDate));
        }
        // Step 2 -- Return the cases based on filters
        return mongoTemplate.find(query, Case.class);
    }

    public boolean isLocationParameterValid(String locationName){
        return locationName != null && !locationName.isEmpty();
    }

    public boolean areDateParametersValid(Date startDate,Date endDate){
        return (startDate != null && endDate != null);
    }

    public void addCase(String locationName,Date date,int newCase,int deathCase,int dischargedCase){
        Case newCaseEntity = new Case();
        newCaseEntity.setReportLocation(locationName);
        newCaseEntity.setReportDate(date);
        newCaseEntity.setNewCaseNumber(newCase);
        newCaseEntity.setDeathCaseNumber(deathCase);
        newCaseEntity.setDischargedCaseNumber(dischargedCase);

        mongoTemplate.save(newCaseEntity);
    }
}
