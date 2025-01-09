package com.example.server.request.converter;

import com.example.server.dto.ReportDto;
import com.example.server.model.Report;
import com.example.server.request.ReportRequest;
import org.springframework.stereotype.Component;

@Component
public class ReportRequestConverter {
    public Report convertToEntity(ReportRequest reportRequest) {
        return Report.builder()
                .report(reportRequest.getReport()).build();
    }
}
