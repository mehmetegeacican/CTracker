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


    public Report updateReport(String id,Report report){
        // Step 1 -- Find the existing report by ID
        Optional<Report> existingReportOpt = reportRepository.findById(id);

        if (!existingReportOpt.isPresent()) {
            throw new RuntimeException("Report does not exist!");
        }

        Report existingReport = existingReportOpt.get();

        // Step 2 -- Update fields in the existing report
        existingReport.setReport(report.getReport());

        // Step 3 -- Save the updated report back to the database
        return reportRepository.save(existingReport);
    }

    /**
     * Deletes a Report Based on Id
     * @param reportId
     */
    public void deleteReport(String reportId) {
        reportRepository.deleteById(reportId);
    }

}
