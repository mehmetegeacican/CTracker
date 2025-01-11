package com.example.server.controller;

import com.example.server.dto.ReportDto;
import com.example.server.dto.converter.ReportDtoConverter;
import com.example.server.helpers.ReportHelper;
import com.example.server.model.Report;
import com.example.server.request.ReportRequest;
import com.example.server.request.converter.ReportRequestConverter;
import com.example.server.service.CaseService;
import com.example.server.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @Mock
    private CaseService caseService;

    @InjectMocks
    private ReportController reportController;


    @Mock
    private ReportDtoConverter reportDtoConverter;

    @Mock
    private ReportRequestConverter reportRequestConverter;


    private ReportHelper reportHelper;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }
    @Test
    void testGetAllReportsSuccess() throws Exception{
        // Given
        Report report1 = new Report();
        Report report2 = new Report();
        List<Report> reports = Arrays.asList(report1, report2);
        ReportDto reportDto1 = new ReportDto();
        ReportDto reportDto2 = new ReportDto();
        List<ReportDto> reportDtos = Arrays.asList(reportDto1, reportDto2);
        Mockito.when(reportService.getAllReports()).thenReturn(reports);
        Mockito.when(reportDtoConverter.convertToDto(reports)).thenReturn(reportDtos);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

    }

    @Test
    void testGetAllReportsServerError() throws Exception{
        // Given
        Mockito.when(reportService.getAllReports()).thenThrow(new RuntimeException("Database connection error"));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()) // Expect HTTP 500
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error fetching reports: Database connection error"));
    }

    @Test
    void testGetSpecificReportSuccess() throws Exception {
        // Given
        String reportId = "12345";
        Report mockReport = new Report();
        ReportDto mockReportDto = new ReportDto();
        mockReportDto.setId(reportId);
        mockReport.setId(reportId);
        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.of(mockReport));
        Mockito.when(reportDtoConverter.convertToDto(mockReport)).thenReturn(mockReportDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/{reportId}", reportId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect HTTP 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(reportId));

    }


    @Test
    void testGetSpecificReportReportNotFound() throws Exception{
        // Given
        String reportId = "nonexistentId";
        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.empty());
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/{reportId}", reportId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Expect HTTP 404
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Report not found with ID: " + reportId));
    }


    @Test
    void testGetSpecificReportServerError() throws Exception{
        // Given
        String reportId = "123";
        Mockito.when(reportService.getReportById(reportId)).thenThrow(new RuntimeException("Database error"));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/{reportId}", reportId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()) // Expect HTTP 500
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error fetching reports: Database error"));
    }

    @Test
    void testPostNewReportSuccess() throws Exception{
        // Given
        String report = "21.04.2020 tarihinde Ankara için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 18 yeni vaka tespit edildi. Taburcu sayısı ise 8 oldu. 1 kişinin de vefat ettiği öğrenildi.";
        ReportRequest mockRequest = new ReportRequest(report);
        Report mockReport = new Report();
        mockReport.setId("12345");
        mockReport.setReport(mockRequest.getReport());
        Mockito.when(reportRequestConverter.convertToEntity(mockRequest)).thenReturn(mockReport);
        Mockito.when(reportService.createReport(mockReport)).thenReturn(mockReport);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"21.04.2020 tarihinde Ankara için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 18 yeni vaka tespit edildi. Taburcu sayısı ise 8 oldu. 1 kişinin de vefat ettiği öğrenildi.\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report Inserted Successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.report.id").value("12345"));

    }


    @Test
    void testPostNewReportEmptyReport() throws Exception{
        // Given
        String report = "";
        ReportRequest mockRequest = new ReportRequest(report);
        Report mockReport = new Report();
        mockReport.setId("12345");
        mockReport.setReport(mockRequest.getReport());

        // Mocking service behavior
        Mockito.when(reportRequestConverter.convertToEntity(mockRequest)).thenReturn(mockReport);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"\"}"))  // Empty report content
                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Expecting 400 BAD_REQUEST
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report section can not be empty"));
    }


    @Test
    void testPostNewReportInvalidReport() throws Exception{
        // Given
        String report = "This is an Invalid Report";
        ReportRequest mockRequest = new ReportRequest(report);
        Report mockReport = new Report();
        mockReport.setId("12345");
        mockReport.setReport(mockRequest.getReport());

        // Mocking service behavior
        Mockito.when(reportRequestConverter.convertToEntity(mockRequest)).thenReturn(mockReport);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"This is an Invalid Report\"}"))  // Invalid report content
                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Expecting 400 BAD_REQUEST
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report content is invalid. Must include City, Date, Death, Discharged and New Case information."));
    }


    @Test
    void testPostNewReportInternalServer() throws Exception{
        // Given
        String report = "This is an Invalid Report";
        ReportRequest mockRequest = new ReportRequest(report);
        Report mockReport = new Report();
        mockReport.setId("12345");
        mockReport.setReport(mockRequest.getReport());

        // Mocking service behavior
        Mockito.when(reportRequestConverter.convertToEntity(mockRequest)).thenThrow(new RuntimeException("Database Error"));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"This is an Invalid Report\"}"))  // Invalid report content
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())  // Expecting 500 INTERNAL_SERVER_ERROR
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error deleting report java.lang.RuntimeException: Database Error"));
    }


    @Test
    void testUpdateExistingReportSuccess() throws Exception {
        // Given
        String reportId = "existingReportId";
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setReport("Sehir: Istanbul, Date: 01.01.2025, Yeni Vaka: 10, Vefat: 2, Taburcu: 5");

        Report existingReport = new Report();
        existingReport.setId(reportId);
        existingReport.setReport("Old Report Content");

        Report updatedReport = new Report();
        updatedReport.setId(reportId);
        updatedReport.setReport(reportRequest.getReport());

        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.of(existingReport));
        Mockito.when(reportRequestConverter.convertToEntity(reportRequest)).thenReturn(updatedReport);
        Mockito.when(reportService.updateReport(Mockito.eq(reportId), Mockito.any(Report.class))).thenReturn(updatedReport);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reports/{id}", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"Sehir: Istanbul, Date: 01.01.2025, Yeni Vaka: 10, Vefat: 2, Taburcu: 5\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report updated successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.report.report").value(reportRequest.getReport()));

    }


    @Test
    void testUpdateExistingReportReportNotFound() throws Exception {
        // Given
        String reportId = "nonExistingReportId";
        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.empty());
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reports/{id}", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"Sehir: Istanbul, Date: 01.01.2025, Yeni Vaka: 10, Vefat: 2, Taburcu: 5\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report not found"));
    }


    @Test
    void testUpdateExistingReportEmptyReport() throws  Exception {
        // Given
        String report = "";
        ReportRequest mockRequest = new ReportRequest(report);
        Report mockReport = new Report();
        mockReport.setId("12345");
        mockReport.setReport(mockRequest.getReport());

        Mockito.when(reportService.getReportById("12345")).thenReturn(Optional.of(mockReport));
        Mockito.when(reportRequestConverter.convertToEntity(mockRequest)).thenReturn(mockReport);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reports/{id}", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"\"}"))  // Ensure the content is empty
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report section cannot be empty"));
    }

    @Test
    void testUpdateExistingReportInvalidContent() throws Exception {
        String reportId = "existingReportId";
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setReport("Invalid Content");

        Report existingReport = new Report();
        existingReport.setId(reportId);
        existingReport.setReport("Old Content");

        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.of(existingReport));
        Mockito.when(reportRequestConverter.convertToEntity(reportRequest)).thenReturn(existingReport);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reports/{id}", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"Invalid Content\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report content is invalid. Must include City, Date, Death, Discharged and New Case information."));
    }


    @Test
    void testUpdateExistingReportInternalServerError() throws Exception {
        // Given
        String reportId = "existingReportId";
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setReport("Sehir: Istanbul, Date: 01.01.2025, Yeni Vaka: 10, Vefat: 2, Taburcu: 5");
        Mockito.when(reportService.getReportById(reportId)).thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reports/{id}", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"report\":\"Sehir: Istanbul, Date: 01.01.2025, Yeni Vaka: 10, Vefat: 2, Taburcu: 5\"}"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error updating report: Database error"));
    }


    @Test
    void testDeleteReportSuccess() throws Exception {
        // Given
        String reportId = "12345";
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("message", "Report deleted successfully");

        // Mocking service behavior
        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.of(new Report()));  // Report exists
        Mockito.doNothing().when(reportService).deleteReport(reportId);  // Simulate successful deletion
        Mockito.doNothing().when(caseService).deleteCase(reportId);  // Simulate successful case deletion

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reports/{reportId}", reportId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Report deleted successfully"));
    }

    @Test
    void testDeleteReportNotFoundCase() throws Exception{
        // Given
        String reportId = "12345";
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("message", "The following id could not be found");

        // Mocking service behavior
        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.empty());  // Report does not exist

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reports/{reportId}", reportId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Expecting 400 Bad Request
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The following id could not be found"));
    }


    @Test
    void testDeleteReportInternalServerError() throws Exception{
        // Given
        String reportId = "12345";
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("message", "Error deleting report: Database Error");
        // Mock Behaviour
        Mockito.when(reportService.getReportById(reportId)).thenReturn(Optional.of(new Report()));
        Mockito.doThrow(new RuntimeException("Database Error")).when(reportService).deleteReport(reportId);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reports/{reportId}", reportId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())  // Expecting 500 Internal Server Error
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error deleting report java.lang.RuntimeException: Database Error"));
    }
}