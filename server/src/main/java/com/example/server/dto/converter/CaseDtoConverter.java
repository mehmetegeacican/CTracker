package com.example.server.dto.converter;

import com.example.server.dto.CaseDto;
import com.example.server.dto.ReportDto;
import com.example.server.model.Case;
import com.example.server.model.Report;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaseDtoConverter {
    /**
     * Using Builder Pattern for scalable dtos
     * @param caseParam
     * @return
     */
    public CaseDto convertToDto(Case caseParam) {
        String idParam = caseParam.getId();
        return CaseDto.builder()
                .id(idParam)
                .reportLocation(caseParam.getReportLocation())
                .newCaseNumber(caseParam.getNewCaseNumber())
                .dischargedCaseNumber(caseParam.getDischargedCaseNumber())
                .deathCaseNumber(caseParam.getDeathCaseNumber())
                .reportDate(caseParam.getReportDate())
                .mainReport(caseParam.getMainReport())
                .build();
    }

    public List<CaseDto> convertToDto(List<Case> caseList){
        return caseList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
