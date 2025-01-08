package com.example.server.controller;

import com.example.server.dto.CaseDto;
import com.example.server.dto.ReportDto;
import com.example.server.dto.converter.CaseDtoConverter;
import com.example.server.model.Case;
import com.example.server.service.CaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate) {
        Map<String, String> responseBody = new HashMap<>();
        try{
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
}
