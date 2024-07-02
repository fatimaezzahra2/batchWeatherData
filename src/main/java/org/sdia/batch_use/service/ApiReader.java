package org.sdia.batch_use.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sdia.batch_use.Main;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class ApiReader implements ItemReader<List<JsonNode>> {
    private final String baseUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private final String location = "London";
    private final String apiKey = "DPS5SA9X3GE8DZ3YHEP8MWWNU";
    private final int totalMonths = 12;

    private int currentMonth = 0;
    private boolean batchJobState = false;
    private boolean isReadComplete = false;


    private LocalDate currentStartDate = LocalDate.of(2024, 5, 1); // Commencez le 1er mai 2024
    private final LocalDate endDate = currentStartDate.plusMonths(1); // Jusqu'à la fin de mai 2024

    @Override
    public List<JsonNode> read() throws Exception {
        if (isReadComplete) {
            return null;
        }
        /*
        if (currentStartDate.isAfter(endDate)) {
            return null;
        }*/
        List<JsonNode> weatherDataList = new ArrayList<>();

        while (currentStartDate.isBefore(endDate)) {
            LocalDate currentEndDate = currentStartDate.plusDays(4);
            if (currentEndDate.isAfter(endDate)) {
                currentEndDate = endDate;
            }

            String rawResult = Main.timelineRequestHttpClient(baseUrl, location, currentStartDate.toString(), currentEndDate.toString(), apiKey);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode weatherData = objectMapper.readTree(rawResult);

            currentStartDate = currentStartDate.plusDays(5);
        }
       /* if (currentMonth >= totalMonths) {
            return null;
        }
        /*
        if (batchJobState) {
            return null;
        }/

        LocalDate startDate = LocalDate.of(2023, 1, 1).plusMonths(currentMonth);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // Appel de l'API Visual Crossing pour obtenir les données météo
        String rawResult = Main.timelineRequestHttpClient(baseUrl, location, startDate.toString(), endDate.toString(), apiKey);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode weatherData = objectMapper.readTree(rawResult);

        currentMonth++;*/
        isReadComplete = true;

        //batchJobState = true;
        return weatherDataList;
    }
}
