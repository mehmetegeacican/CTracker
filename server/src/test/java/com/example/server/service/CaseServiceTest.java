package com.example.server.service;

import com.example.server.model.Case;
import com.example.server.repository.ReportRepository;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaseServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private CaseService caseService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetCasesSuccessfulAllParams() {
        // Given
        String locationName = "TestLocation";
        Date startDate = new Date(2025, 0, 1); // Jan 1, 2025
        Date endDate = new Date(2025, 0, 31);  // Jan 31, 2025
        Case case1 = new Case();
        Case case2 = new Case();
        case1.setId("1");
        case2.setId("2");
        List<Case> mockCases = new ArrayList<>();
        mockCases.add(case1);
        mockCases.add(case2);
        Mockito.when(mongoTemplate.find(Mockito.any(Query.class), Mockito.eq(Case.class))).thenReturn(mockCases);
        // When
        List<Case> cases = caseService.getCases(locationName, startDate, endDate);
        // Then
        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        Mockito.verify(mongoTemplate, Mockito.times(1)).find(queryCaptor.capture(), Mockito.eq(Case.class));
        Query capturedQuery = queryCaptor.getValue();
        Criteria locationCriteria = Criteria.where("reportLocation").is(locationName.toLowerCase());
        Criteria expectedDateCriteria = Criteria.where("reportDate").gte(startDate).lte(endDate);
        assertEquals(locationCriteria.getCriteriaObject().get("reportLocation"), capturedQuery.getQueryObject().get("reportLocation"));
        assertEquals(expectedDateCriteria.getCriteriaObject().get("reportDate"), capturedQuery.getQueryObject().get("reportDate"));
        assertEquals(mockCases, cases);
    }

    @Test
    void testIsLocationParameterValid() {
        // Given
        String validLocation = "TestLocation";
        // When
        boolean result = caseService.isLocationParameterValid(validLocation);
        // Then
        assertTrue(result);
    }

    @Test
    void testIsLocationParameterValidEmptyCase(){
        // Given
        String validLocation = null;
        // When
        boolean result = caseService.isLocationParameterValid(validLocation);
        // Then
        assertFalse(result);
    }

    @Test
    void testAreDateParametersValidSuccessfulScenario() {
        // Given
        Date startDate = new Date(2025,1,1);
        Date endDate = new Date(2025,2,1);
        // When
        boolean result = caseService.areDateParametersValid(startDate,endDate);
        // Then
        assertTrue(result);
    }

    @Test
    void testAreDateParamsValidNullScenario(){
        // Given
        Date startDate = new Date(2025,1,1);
        Date endDate = null;
        // When
        boolean result = caseService.areDateParametersValid(startDate,endDate);
        // Then
        assertFalse(result);
    }

    @Test
    void testAreDateParamsValidBeforeScenario(){
        // Given
        Date startDate = new Date(2025,2,1);
        Date endDate = new Date(2025,1,1);
        // When
        boolean result = caseService.areDateParametersValid(startDate,endDate);
        // Then
        assertFalse(result);
    }

    @Test
    void testAddCase() {
        // Given: Input parameters
        String locationName = "TestLocation";
        String reportId = "Report123";
        Date reportDate = new Date(2025 - 1900, 0, 15);
        int newCase = 50;
        int deathCase = 5;
        int dischargedCase = 40;

        // When
        caseService.addCase(locationName, reportId, reportDate, newCase, deathCase, dischargedCase);

        // Then
        ArgumentCaptor<Case> caseCaptor = ArgumentCaptor.forClass(Case.class);
        Mockito.verify(mongoTemplate, Mockito.times(1)).save(caseCaptor.capture());

        // Assert: Validate the captured entity
        Case capturedCase = caseCaptor.getValue();
        assertEquals(locationName, capturedCase.getReportLocation());
        assertEquals(reportId, capturedCase.getReportId());
        assertEquals(reportDate, capturedCase.getReportDate());
        assertEquals(newCase, capturedCase.getNewCaseNumber());
        assertEquals(deathCase, capturedCase.getDeathCaseNumber());
        assertEquals(dischargedCase, capturedCase.getDischargedCaseNumber());
    }

    @Test
    void testDeleteCaseSuccessful() {
        // Given
        String reportId = "Report123";
        DeleteResult mockResult = Mockito.mock(DeleteResult.class);
        Mockito.when(mockResult.getDeletedCount()).thenReturn(1L);
        Mockito.when(mongoTemplate.remove(Mockito.any(Query.class), Mockito.eq(Case.class))).thenReturn(mockResult);
        // When
        caseService.deleteCase(reportId);
        // Then
        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        Mockito.verify(mongoTemplate, Mockito.times(1)).remove(queryCaptor.capture(), Mockito.eq(Case.class));
        Query capturedQuery = queryCaptor.getValue();
        assertEquals(reportId, capturedQuery.getQueryObject().get("reportId"));
    }

    @Test
    void testDeleteCaseEmpty(){
        // Given
        String reportId = "NonExistentId";
        DeleteResult mockResult = Mockito.mock(DeleteResult.class);
        Mockito.when(mockResult.getDeletedCount()).thenReturn(0L); // Failed Case Simulation
        Mockito.when(mongoTemplate.remove(Mockito.any(Query.class), Mockito.eq(Case.class))).thenReturn(mockResult);
        // When
        caseService.deleteCase(reportId);
        // Then
        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        Mockito.verify(mongoTemplate, Mockito.times(1)).remove(queryCaptor.capture(), Mockito.eq(Case.class));
        Query capturedQuery = queryCaptor.getValue();
        assertEquals(reportId, capturedQuery.getQueryObject().get("reportId"));
    }
}