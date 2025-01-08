package com.example.server.service;

import com.example.server.model.Report;
import com.example.server.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    /**
     * Returns All of the Reports
     * @return
     */
    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }
}
