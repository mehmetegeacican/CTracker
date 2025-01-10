package com.example.server.service;

import com.example.server.model.Report;
import com.example.server.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    /**
     * Returns Reports
     * @return
     */
    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }


    /**
     * Returns Report By Id
     * @param id
     * @return
     */
    public Optional<Report> getReportById(String id){
        return reportRepository.findById(id);
    }

    /**
     * Inserts New Report
     * @param report Report Object
     * @return Report Object
     */
    public Report createReport(Report report){
        return  reportRepository.save(report);
    }

    /**
     * Deletes a Report Based on Id
     * @param reportId
     */
    public void deleteReport(String reportId) {
        reportRepository.deleteById(reportId);
    }

}
