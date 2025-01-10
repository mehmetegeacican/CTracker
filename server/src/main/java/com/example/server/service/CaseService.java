package com.example.server.service;

import com.example.server.dto.CaseDto;
import com.example.server.model.Case;
import com.example.server.repository.CaseRepository;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
            query.addCriteria(Criteria.where("reportLocation").is(locationName.toLowerCase()));
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
        return (startDate != null && endDate != null && startDate.before(endDate));
    }

    public void addCase(String locationName,String reportId,Date date,int newCase,int deathCase,int dischargedCase){
        Case newCaseEntity = new Case();
        newCaseEntity.setReportLocation(locationName);
        newCaseEntity.setReportId(reportId);
        newCaseEntity.setReportDate(date);
        newCaseEntity.setNewCaseNumber(newCase);
        newCaseEntity.setDeathCaseNumber(deathCase);
        newCaseEntity.setDischargedCaseNumber(dischargedCase);

        mongoTemplate.save(newCaseEntity);
    }


    public void deleteCase(String reportId){
        Query query = new Query(Criteria.where("reportId").is(reportId));
        DeleteResult result = mongoTemplate.remove(query, Case.class);
        if (result.getDeletedCount() == 0) {
            System.out.println("Report not found");
        }
    }


    /*
    public List<Map> getStatistics (String reportLocation, Date startDate, Date endDate){
        Criteria matchCriteria = new Criteria();
        if (isLocationParameterValid(reportLocation)) {
            matchCriteria.and("reportLocation").is(reportLocation.toLowerCase());
        }
        if (areDateParametersValid(startDate, endDate)) {
            matchCriteria.and("reportDate").gte(startDate).lte(endDate);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(matchCriteria),
                Aggregation.group()
                        .sum("newCaseNumber").as("totalNewCases")
                        .sum("dischargedCaseNumber").as("totalDischargedCases")
                        .sum("deathCaseNumber").as("totalDeathCases"),
                Aggregation.project("totalNewCases", "totalDischargedCases", "totalDeathCases")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "cases", Map.class);

        if (results.getMappedResults().isEmpty()) {
            Map<String, Object> defaultResult = new HashMap<>();
            defaultResult.put("totalNewCases", 0);
            defaultResult.put("totalDischargedCases", 0);
            defaultResult.put("totalDeathCases", 0);
            return Collections.singletonList(defaultResult);
        }

        return results.getMappedResults();

    }
     */
}
