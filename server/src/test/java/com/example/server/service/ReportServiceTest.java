package com.example.server.service;

import com.example.server.model.Report;
import com.example.server.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReportsSuccessful() {
        // Given
        Report report1 = new Report();
        Report report2 = new Report();
        List<Report> reports = Arrays.asList(report1, report2);
        Mockito.when(reportRepository.findAll()).thenReturn(reports);
        // When
        List<Report> result = reportService.getAllReports();
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(report1, result.get(0));
        assertSame(report2, result.get(1));
        Mockito.verify(reportRepository,Mockito.times(1)).findAll();

    }

    @Test
    void testGetAllReportsEmpty(){
        // Given
        List<Report> reports = Arrays.asList();
        Mockito.when(reportRepository.findAll()).thenReturn(reports);
        // When
        List<Report> result = reportService.getAllReports();
        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        Mockito.verify(reportRepository,Mockito.times(1)).findAll();
    }

    @Test
    void testGetReportByIdSuccessful() {
        // Given
        String reportId = "12345";
        Report report = new Report();
        report.setId(reportId);
        Mockito.when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        // When
        Optional<Report> result = reportService.getReportById(reportId);
        // Then
        assertTrue(result.isPresent());
        assertEquals(reportId, result.get().getId());
        assertSame(report, result.get());
        Mockito.verify(reportRepository, Mockito.times(1)).findById(reportId);
    }

    @Test
    void  testGetReportByIdEmpty(){
        // Given
        String reportId = "12345";
        Mockito.when(reportRepository.findById(reportId)).thenReturn(Optional.empty());
        // When
        Optional<Report> result = reportService.getReportById(reportId);
        // Then
        assertFalse(result.isPresent());
        assertEquals(result,Optional.empty());
        Mockito.verify(reportRepository, Mockito.times(1)).findById(reportId);
    }

    @Test
    void testCreateReportSuccessful() {
        // Given
        Report inputReport = new Report();
        inputReport.setReport("aaaa");
        Report savedReport = new Report();
        savedReport.setId("12345");
        savedReport.setReport("aaaa");
        Mockito.when(reportRepository.save(inputReport)).thenReturn(savedReport);
        // When
        Report result = reportService.createReport(inputReport);
        // Then
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertEquals("aaaa", result.getReport());
        Mockito.verify(reportRepository, Mockito.times(1)).save(inputReport);
    }


    @Test
    void testCreateReportEmptyReport(){
        // Given
        Report inputReport = new Report();
        Report savedReport = new Report();
        savedReport.setId("12345");
        Mockito.when(reportRepository.save(inputReport)).thenReturn(savedReport);
        // When
        Report result = reportService.createReport(inputReport);
        // Then
        assertNotNull(result);
        assertEquals("12345", result.getId());
        assertNull(result.getReport());
        Mockito.verify(reportRepository, Mockito.times(1)).save(inputReport);
    }


    @Test
    void testUpdateReportSuccess(){
        // Given
        String reportId = "existingReportId";
        Report existingReport = new Report();
        existingReport.setId(reportId);
        existingReport.setReport("Old Report Content");
        Report updatedReport = new Report();
        updatedReport.setReport("New Report Content");
        // Mock
        Mockito.when(reportRepository.findById(reportId)).thenReturn(Optional.of(existingReport));
        Mockito.when(reportRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // When
        Report result = reportService.updateReport(reportId, updatedReport);
        // Then
        assertNotNull(result);
        assertEquals("New Report Content", result.getReport());
        Mockito.verify(reportRepository).findById(reportId);
        Mockito.verify(reportRepository).save(existingReport);
    }


    @Test
    void testUpdateReportReportNotFound(){
        // Given
        String reportId = "nonExistentReportId";
        Report updatedReport = new Report();
        updatedReport.setReport("Updated Report Content");
        Mockito.when(reportRepository.findById(reportId)).thenReturn(Optional.empty());
        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reportService.updateReport(reportId, updatedReport);
        });
        // Then
        assertEquals("Report does not exist!", exception.getMessage());
        Mockito.verify(reportRepository).findById(reportId);
        Mockito.verify(reportRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteReportSuccessfulCase() {
        // Given
        String reportId = "12345";
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        // When
        reportService.deleteReport(reportId);
        // Then
        Mockito.verify(reportRepository, Mockito.times(1)).deleteById(captor.capture());
        assertEquals(reportId, captor.getValue());
    }

    @Test
    void testDeleteReportFailedCase(){
        // Given: A report ID that doesn't exist
        String reportId = "nonExistentId";
        Mockito.doNothing().when(reportRepository).deleteById(reportId);
        // When
        reportService.deleteReport(reportId);
        // Then
        Mockito.verify(reportRepository, Mockito.times(1)).deleteById(reportId);
    }
}