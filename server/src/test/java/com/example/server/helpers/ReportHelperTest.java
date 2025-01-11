package com.example.server.helpers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class ReportHelperTest {

    @InjectMocks
    private ReportHelper reportHelper;
    @Test
    void testIsValidReportAllCases() {
        // Given
        String validReport = "Vaka sayısı 1000. Taburcu 800. Vefat 100. Tarih: 01.01.2025, Şehir: Ankara.";
        String invalidReportNoDate = "Vaka sayısı 1000. Taburcu 800. Vefat 100. Şehir: Ankara.";
        String invalidReportNoKeyword = "Vaka sayısı 1000. Taburcu 800. Şehir: Ankara.";
        String invalidReportNoCity = "Vaka sayısı 1000. Taburcu 800. Vefat 100. Tarih: 01.01.2025.";
        // When
        boolean resultValid = ReportHelper.isValidReport(validReport);
        boolean resultInvalidDate = ReportHelper.isValidReport(invalidReportNoDate);
        boolean resultInvalidDeath = ReportHelper.isValidReport(invalidReportNoKeyword);
        boolean resultInvalidCity = ReportHelper.isValidReport(invalidReportNoCity);

        // Then
        assertTrue(resultValid);
        assertFalse(resultInvalidDate);
        assertFalse(resultInvalidDeath);
        assertFalse(resultInvalidCity);
    }

    @Test
    void testExtractDateValid() {
        // Given
        String reportWithDate = "Vaka sayısı 1000, taburcu 800, vefat 100. Tarih: 01.01.2025.";
        // When
        String extractedDate = ReportHelper.extractDate(reportWithDate);
        // Then
        assertEquals("01.01.2025", ReportHelper.extractDate(reportWithDate));
    }

    @Test
    void testExtractDateNotFound() {
        // Given
        String reportWithDate = "Vaka sayısı 1000, taburcu 800, vefat 100";
        // When
        String extractedDate = ReportHelper.extractDate(reportWithDate);
        // Then
        assertNull(ReportHelper.extractDate(reportWithDate));
    }

    @Test
    void testRemoveDateFromReport() {
        // Given
        String reportWithDate = "Vaka sayısı 1000, taburcu 800, vefat 100. Tarih: 01.01.2025.";
        // When
        String result = ReportHelper.removeDateFromReport(reportWithDate);
        // Then
        assertEquals("Vaka sayısı 1000, taburcu 800, vefat 100. Tarih: .", ReportHelper.removeDateFromReport(reportWithDate));
    }

    @Test
    void testRemoveDateFromReportNoDateFound(){
        // Given
        String reportWithNoDate = "Vaka sayısı 1000, taburcu 800, vefat 100.";
        // When
        String result = ReportHelper.removeDateFromReport(reportWithNoDate);
        // Then
        assertEquals(result,reportWithNoDate);

    }

    @Test
    void testExtractCityCityFound() {
        // Given
        String reportWithCity = "Vaka sayısı 1000, taburcu 800, vefat 100, şehir: Ankara.";
        // When
        String result = ReportHelper.extractCity(reportWithCity);
        // Then
        assertEquals("ankara",result);
    }

    @Test
    void testExtractCityNotFound(){
        // Given
        String reportWithoutCity = "Vaka sayısı 1000, taburcu 800, vefat 100.";
        // When
        String result = ReportHelper.extractCity(reportWithoutCity);
        // Then
        assertEquals("",result);
    }

    @Test
    void testExtractCityUnidentifiedCity(){
        // Given
        String reportWithCity = "Vaka sayısı 1000, taburcu 800, vefat 100, şehir: New York.";
        // When
        String result = ReportHelper.extractCity(reportWithCity);
        // Then
        assertEquals("",result);
    }

    @Test
    void testExtractNumberFromSentenceSuccessful() {
        // Given
        String sentenceWithNumber = "Vaka sayısı 1000.";
        String date = "1.1.2020";
        // When
        int number = ReportHelper.extractNumberFromSentence(sentenceWithNumber,date);
        // Then
        assertEquals(1000,number);
    }

    @Test
    void testExtractNumberFromSentenceNotFound(){
        // Given
        String sentenceWithoutNumber = "Vaka sayısı.";
        String date = "1.1.2020";
        // When
        int number = ReportHelper.extractNumberFromSentence(sentenceWithoutNumber,date);
        // Then
        assertEquals(0,number);
    }
}