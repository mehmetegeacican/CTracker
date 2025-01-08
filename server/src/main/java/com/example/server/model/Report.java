package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection = "reports")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    private String id;


    private String report;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
