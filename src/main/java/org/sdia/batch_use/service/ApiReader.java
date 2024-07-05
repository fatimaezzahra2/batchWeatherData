package org.sdia.batch_use.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sdia.batch_use.Main;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@StepScope
public class ApiReader implements ItemReader<JsonNode> {
    private final String baseUrl = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private final String location = "London";
    private final String apiKey = "JN69GT5FUL5LLDTL6CGQGTR4W";
    private final int totalMonths = 12;

    private final LocalDate startDate = LocalDate.of(2024, 7, 1); // Commencez le 1er JANVIER 2024
    private final LocalDate endDate = startDate.plusMonths(1).minusDays(1); // Jusqu'à la fin de janvier 2024

    private int currentMonth = 0;
    private boolean batchJobState = false;
    private boolean isReadComplete = false;


    @Override
    public JsonNode read() throws Exception {
        if (isReadComplete) {
            return null;
        }

        // Lire les données d'un mois entier
        String rawResult = Main.timelineRequestHttpClient(baseUrl, location, startDate.toString(), endDate.toString(), apiKey);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode weatherData = objectMapper.readTree(rawResult);

        isReadComplete = true;

        return weatherData;
        /* ici if (currentMonth >= totalMonths) {
            return null;
        }
        /*
        if (batchJobState) {
            return null;
        }

        LocalDate startDate = LocalDate.of(2023, 1, 1).plusMonths(currentMonth);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // Appel de l'API Visual Crossing pour obtenir les données météo
        String rawResult = Main.timelineRequestHttpClient(baseUrl, location, startDate.toString(), endDate.toString(), apiKey);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode weatherData = objectMapper.readTree(rawResult);

        currentMonth++;

        //batchJobState = true;
        return weatherData;ICI*/
    }
}
