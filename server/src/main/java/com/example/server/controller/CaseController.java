package com.example.server.controller;

import com.example.server.dto.CaseDto;
import com.example.server.dto.ReportDto;
import com.example.server.dto.converter.CaseDtoConverter;
import com.example.server.model.Case;
import com.example.server.service.CaseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/v1/cases")
@RestController
public class CaseController {
    private final CaseService caseService;
    private final CaseDtoConverter caseDtoConverter;

    public CaseController(CaseService caseService, CaseDtoConverter caseDtoConverter){
        this.caseService = caseService;
        this.caseDtoConverter = caseDtoConverter;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCases(
            @RequestParam(required = false) String reportLocation,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate) {
        Map<String, String> responseBody = new HashMap<>();
        try{

            if((startDate != null && endDate == null) || (startDate == null && endDate != null)){
                responseBody.put("message", "Both of the startDate and endDates must be given");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
            List<Case> allCases = caseService.getCases(reportLocation,startDate,endDate);

            return new ResponseEntity<List<CaseDto>>(
                    caseDtoConverter.convertToDto(allCases), HttpStatus.OK
            );
        } catch (Exception error){
            responseBody.put("message", "Error fetching cases: " + error.getMessage());
            // Return the error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(
            @RequestParam(required = false) String reportLocation,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate)
    {
        Map<String, String> responseBody = new HashMap<>();
        try{
            // Step 1 -- Check the Date Parameters
            if((startDate != null && endDate == null) || (startDate == null && endDate != null)){
                responseBody.put("message", "Both of the startDate and endDates must be given");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
            List<Map> stats = caseService.getStatistics(reportLocation,startDate,endDate);
            return new ResponseEntity<>(
                    stats, HttpStatus.OK
            );
        }catch (Exception error){
            responseBody.put("message", "Error fetching cases: " + error.getMessage());
            // Return the error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
