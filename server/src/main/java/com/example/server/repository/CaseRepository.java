package com.example.server.repository;

import com.example.server.model.Case;
import com.example.server.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CaseRepository extends MongoRepository<Case,String> {
    List<Case> findByReportLocationAndReportDateBetween(String reportLocation, Date startDate, Date endDate);

    List<Case> findByReportLocation(String reportLocation);

    List<Case> findByReportDateBetween(Date startDate, Date endDate);

    Optional<Case> findByReportId(String reportId);
}
