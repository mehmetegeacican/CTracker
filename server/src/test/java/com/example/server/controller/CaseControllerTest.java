package com.example.server.controller;

import com.example.server.dto.CaseDto;
import com.example.server.dto.converter.CaseDtoConverter;
import com.example.server.model.Case;
import com.example.server.service.CaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private CaseService caseService;

    @Mock
    private CaseDtoConverter caseDtoConverter;


    @InjectMocks
    private CaseController caseController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(caseController).build();
    }

    @Test
    void testGetCasesSuccess() throws Exception {
        // Given
        String reportLocation = "Ankara";
        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2020");
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse("31.12.2020");

        List<Case> mockCases = Arrays.asList(new Case(), new Case());  // Mocking the list of cases
        List<CaseDto> mockCaseDtos = Arrays.asList(new CaseDto(), new CaseDto());

        // Mocking service behavior
        Mockito.when(caseService.getCases(reportLocation, startDate, endDate)).thenReturn(mockCases);
        Mockito.when(caseDtoConverter.convertToDto(mockCases)).thenReturn(mockCaseDtos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cases/all")
                        .param("reportLocation", reportLocation)
                        .param("startDate", "01.01.2020")
                        .param("endDate", "31.12.2020"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Expecting 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void testGetCasesBadRequest() throws Exception {
        // Given
        String reportLocation = "Ankara";
        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2020");

        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("message", "Both of the startDate and endDates must be given");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cases/all")
                        .param("reportLocation", reportLocation)
                        .param("startDate", "01.01.2020"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Expecting 400 Bad Request
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Both of the startDate and endDates must be given"));
    }

    @Test
    void testGetCasesInternalServerError() throws Exception {
        // Given
        String reportLocation = "Ankara";
        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2020");
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse("31.12.2020");

        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("message", "Error fetching cases: Database Error");

        // Mocking service behavior to throw an exception
        Mockito.when(caseService.getCases(reportLocation, startDate, endDate)).thenThrow(new RuntimeException("Database Error"));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cases/all")
                        .param("reportLocation", reportLocation)
                        .param("startDate", "01.01.2020")
                        .param("endDate", "31.12.2020"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())  // Expecting 500 Internal Server Error
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error fetching cases: Database Error"));
    }

}