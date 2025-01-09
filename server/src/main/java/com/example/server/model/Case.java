package com.example.server.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "cases")
public class Case {
    @Id
    private String id;

    private String reportId;
    private Date reportDate;
    private String reportLocation;
    private int newCaseNumber;
    private int dischargedCaseNumber;
    private int deathCaseNumber;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Get
     * @return id string
     */
    public String getId() {
        return id;
    }

    /**
     * Setters
     * @param id string
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get
     * @return reportId string
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * Setter
     * @param reportId
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * Get
     * @return reportDate date
     */
    public Date getReportDate() {
        return reportDate;
    }

    /**
     * Setter
     * @param reportDate
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * Get
     * @return reportLocation String
     */
    public String getReportLocation() {
        return reportLocation;
    }

    /**
     * Setter
     * @param reportLocation
     */
    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }

    /**
     * Get newly occured case number
     * @return
     */
    public int getNewCaseNumber() {
        return newCaseNumber;
    }

    /**
     * Setter
     * @param newCaseNumber
     */
    public void setNewCaseNumber(int newCaseNumber) {
        this.newCaseNumber = newCaseNumber;
    }

    /**
     * Get number of discharged cases of that day
     * @return
     */
    public int getDischargedCaseNumber() {
        return dischargedCaseNumber;
    }

    /**
     * Setter
     * @param dischargedCaseNumber
     */
    public void setDischargedCaseNumber(int dischargedCaseNumber) {
        this.dischargedCaseNumber = dischargedCaseNumber;
    }

    /**
     * Get, Number of Deaths
     * @return deathCaseNumber integer
     */
    public int getDeathCaseNumber() {
        return deathCaseNumber;
    }

    /**
     * Setter
     * @param deathCaseNumber
     */
    public void setDeathCaseNumber(int deathCaseNumber) {
        this.deathCaseNumber = deathCaseNumber;
    }
}
