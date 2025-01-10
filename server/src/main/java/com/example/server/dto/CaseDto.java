package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseDto {
    private String id;
    private String reportLocation;
    private String mainReport;
    private Date reportDate;
    private int newCaseNumber;
    private int dischargedCaseNumber;
    private int deathCaseNumber;
}
