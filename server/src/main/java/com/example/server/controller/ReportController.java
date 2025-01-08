package com.example.server.controller;

import com.example.server.dto.ReportDto;
import com.example.server.dto.converter.ReportDtoConverter;
import com.example.server.model.Report;
import com.example.server.request.ReportRequest;
import com.example.server.request.converter.ReportRequestConverter;
import com.example.server.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReportDtoConverter reportDtoConverter;

    private final ReportRequestConverter reportRequestConverter;

    public ReportController(ReportService reportService,ReportDtoConverter reportDtoConverter, ReportRequestConverter reportRequestConverter){
        this.reportService = reportService;
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
            // Step 2 -- Create the Report
            Report createdReport = reportService.createReport(reportEntity);
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
            responseBody.put("message", "Report deleted successfully");
            return ResponseEntity.status(200).body(responseBody);

        } catch (Exception error){
            responseBody.put("message", "Error deleting report " + error);
            return ResponseEntity.status(500).body(responseBody);
        }
    }
}
