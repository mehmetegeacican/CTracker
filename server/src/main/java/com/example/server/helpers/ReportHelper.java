package com.example.server.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportHelper {
    private static final String DATE_REGEX = "\\d{2}\\.\\d{2}\\.\\d{4}";

    private static final String[] REQUIRED_KEYWORDS = {"taburcu", "vefat", "vaka"};

    private static final String[] CITIES = {
            "ankara", "istanbul", "izmir", "bursa", "adana",
            "konya", "gaziantep", "antalya", "eskişehir", "samsun"
    };

    public static boolean isValidReport(String report) {
        Locale turkishLocale = Locale.forLanguageTag("tr");
        boolean dateExists =  report.matches(".*" + DATE_REGEX + ".*");
        boolean keywordsExists = true;
        boolean cityExists = false;
        for (String keyword : REQUIRED_KEYWORDS) {
            if (!report.toLowerCase(turkishLocale).contains(keyword.toLowerCase(turkishLocale))) {
                keywordsExists = false;
            }
        }
        for(String city: CITIES){
            if(report.toLowerCase(turkishLocale).contains(city.toLowerCase(turkishLocale))){
                cityExists = true;
            }
        }
        return keywordsExists && cityExists && dateExists;
    }

    public static String extractDate(String report) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = pattern.matcher(report);
        return matcher.find() ? matcher.group() : null;
    };

    public static String removeDateFromReport(String report) {
        Pattern datePattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = datePattern.matcher(report);
        return matcher.replaceAll("");
    }

    public static String extractCity(String report) {
        Locale turkishLocale = Locale.forLanguageTag("tr");
        String cityExtracted = "";
        for(String city: CITIES){
            if(report.toLowerCase(turkishLocale).contains(city.toLowerCase(turkishLocale))){
                cityExtracted =  city;
            }
        }
        return cityExtracted;
    };

    public static Map<String,Integer> extractNumbers(String report){
        String[] sentences = report.split("\\.\\s*");
        Map<String, Integer> resultMap = new HashMap<>();
        String dateStr = extractDate(report);
        resultMap.put("newCases", 0);
        resultMap.put("dischargedCases", 0);
        resultMap.put("deathCases", 0);
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains("yeni vaka")) {
                resultMap.put("newCases", extractNumberFromSentence(sentence,dateStr));
            }
            if (sentence.toLowerCase().contains("taburcu")) {
                resultMap.put("dischargedCases", extractNumberFromSentence(sentence,dateStr));
            }
            if (sentence.toLowerCase().contains("vefat")) {
                resultMap.put("deathCases", extractNumberFromSentence(sentence,dateStr));
            }
        }
        return resultMap;
    }

    public static int extractNumberFromSentence(String sentence,String date) {
        // Pattern to match all numbers
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(sentence);

        // If a number is found, return it
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        // Return a default value if no valid number is found
        return 0;
    }

}
