package com.example.server.controller;

import com.example.server.dto.ReportDto;
import com.example.server.dto.converter.ReportDtoConverter;
import com.example.server.helpers.ReportHelper;
import com.example.server.model.Report;
import com.example.server.request.ReportRequest;
import com.example.server.request.converter.ReportRequestConverter;
import com.example.server.service.CaseService;
import com.example.server.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final CaseService caseService;
    private final ReportDtoConverter reportDtoConverter;

    private final ReportRequestConverter reportRequestConverter;

    public ReportController(ReportService reportService, CaseService caseService, ReportDtoConverter reportDtoConverter, ReportRequestConverter reportRequestConverter){
        this.reportService = reportService;
        this.caseService = caseService;
        this.reportDtoConverter = reportDtoConverter;
        this.reportRequestConverter = reportRequestConverter;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReports(){
        try{
            List<Report> reports = reportService.getAllReports();
            return new ResponseEntity<List<ReportDto>>(
                    reportDtoConverter.convertToDto(reports), HttpStatus.OK
            );

        } catch (Exception error){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error fetching reports: " + error.getMessage());

            // Return the error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


    @GetMapping("/{reportId}")
    public ResponseEntity<?> getSpecificReport(@PathVariable String reportId){
        try{
            Optional<Report> reportOptional = reportService.getReportById(reportId);
            // Check if the report is present
            if (reportOptional.isPresent()) {
                // Convert Report to ReportDto
                ReportDto reportDto = reportDtoConverter.convertToDto(reportOptional.get());
                return ResponseEntity.ok(reportDto);  // Return ReportDto
            } else {
                // If the report is not found, return error message
                Map<String, String> response = new HashMap<>();
                response.put("error", "Report not found with ID: " + reportId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception error){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error fetching reports: " + error.getMessage());

            // Return the error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping
    public ResponseEntity<Map<String,Object>> postNewReport(@RequestBody ReportRequest reportRequest){
        Map<String, Object> responseBody = new HashMap<>();
        try{
            Report reportEntity = reportRequestConverter.convertToEntity(reportRequest);
            // Step 1 -- Report Checks
            if(reportEntity.getReport() == null || reportEntity.getReport().isEmpty()){
                responseBody.put("message", "Report section can not be empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
            // Step 2 -- Validate Report contains the right keywords
            String reportText = reportEntity.getReport();
            if(ReportHelper.isValidReport(reportText) == false){
                responseBody.put("message", "Report content is invalid. Must include City, Date, Death, Discharged and New Case information.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
            // Step 3 -- Extract the Datas and Add them to Cases
            String dateStr = ReportHelper.extractDate(reportText);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC-3"));
            Date date = sdf.parse(dateStr);
            String city = ReportHelper.extractCity(reportText);
            // String Report with Retracted Date
            String retractedReport = ReportHelper.removeDateFromReport(reportText);
            Map<String,Integer> numberMap = ReportHelper.extractNumbers(retractedReport);
            // Step 4 -- Create the Report
            Report createdReport = reportService.createReport(reportEntity);
            // Step 5 -- Add the Case
            caseService.addCase(city,createdReport.getId(),date,numberMap.get("newCases"),numberMap.get("deathCases"), numberMap.get("dischargedCases"));

            responseBody.put("message", "Report Inserted Successfully");
            responseBody.put("report", createdReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception error){
            responseBody.put("message", "Error deleting report " + error);
            return ResponseEntity.status(500).body(responseBody);
        }
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Map<String, String>> deleteReport(@PathVariable String reportId) {
        Map<String, String> responseBody = new HashMap<>();
        try{
            if(reportService.getReportById(reportId).isEmpty()){
                responseBody.put("message", "The following id could not be found");
                return ResponseEntity.status(400).body(responseBody);
            }
            reportService.deleteReport(reportId);
            // Delete the Case of the Report
            caseService.deleteCase(reportId);
            responseBody.put("message", "Report deleted successfully");
            return ResponseEntity.status(200).body(responseBody);

        } catch (Exception error){
            responseBody.put("message", "Error deleting report " + error);
            return ResponseEntity.status(500).body(responseBody);
        }
    }
}
