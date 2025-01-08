package com.example.server.controller;

import com.example.server.dto.ReportDto;
import com.example.server.dto.converter.ReportDtoConverter;
import com.example.server.model.Report;
import com.example.server.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReportDtoConverter reportDtoConverter;

    public ReportController(ReportService reportService,ReportDtoConverter reportDtoConverter){
        this.reportService = reportService;
        this.reportDtoConverter = reportDtoConverter;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReportDto>> getAllReports(){
        List<Report> reports = reportService.getAllReports();
        return new ResponseEntity<List<ReportDto>>(
                reportDtoConverter.convertToDto(reports), HttpStatus.OK
        );
    }
}
