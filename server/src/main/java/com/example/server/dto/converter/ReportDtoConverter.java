package com.example.server.dto.converter;

import com.example.server.dto.ReportDto;
import com.example.server.model.Report;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportDtoConverter {

    /**
     * Using Builder Pattern for scalable dtos
     * @param reportParam
     * @return
     */
    public ReportDto convertToDto(Report reportParam) {
        String idParam = reportParam.getId();
        return ReportDto.builder()
                .id(idParam)
                .report(reportParam.getReport())
                .createdAt(reportParam.getCreatedAt())
                .build();
    }

    public List<ReportDto> convertToDto(List<Report> reportList){
        return reportList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
