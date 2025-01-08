package com.example.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class Report {
    @Id
    private String id;

    private String report;

    /**
     * Getters Id
     * @return Id string
     */
    public String getId() {
        return id;
    }

    /**
     * Setter
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getters and Setters : Report
     * @return Id string
     */
    public String getReport() {
        return report;
    }

    /**
     * Setters Report
     * @param report string
     */
    public void setReport(String report) {
        this.report = report;
    }
}
