package org.sdia.batch_use.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
//@AllArgsConstructor
@StepScope

public class ApiWriter implements ItemWriter<List<JsonNode>> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    //private String projectId="test";
    private int documentId = 1; // Start with 1 and increment for each write


    @Override
    public void write(Chunk<? extends List<JsonNode>> chunk) throws Exception {

        Firestore db = FirestoreClient.getFirestore();
        System.out.println("writer chunk is : "+ chunk);

/*
        Map<String, Integer> map = new HashMap<>();

        // Ajout d'éléments à la Map
        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);

        DocumentReference docRef = db.collection("weather_forecast").document();
        WriteBatch batch = db.batch();
        batch.set(docRef, map);
        ApiFuture<List<WriteResult>> future = batch.commit();*/

        // int count=0;

        for (List<JsonNode> itemList : chunk.getItems()) {
            System.out.println("---------------------------");
            for (JsonNode item : itemList) {
                System.out.println("item: "+item);
            try {
                // ... (Your data conversion and writing logic) ...
                //  System.out.println("writer chunk is : "+ chunk);
                //weatherRepository.saveAll(chunk);


                //     System.out.println("chunk: "+ chunk);


                // Écrire chaque élément individuellement dans Firestore
                //      System.out.println("chunk items: "+ chunk.getItems());


               /* JsonNode firstElement=chunk.getItems().get(0);
System.out.println("first element: "+firstElement);

                DocumentReference docRef = db.collection("weather_forecast").document(projectId);
                WriteBatch batch = db.batch();
                batch.set(docRef, firstElement);
                ApiFuture<List<WriteResult>> future = batch.commit();*/
                // Convertir l'élément JSON en une carte (Map) compatible avec Firestore
                Map<String, Object> map = objectMapper.convertValue(item, Map.class);
                DocumentReference docRef = db.collection("project_id").document(String.valueOf(documentId));
                WriteBatch batch = db.batch();
                batch.set(docRef, map);
                ApiFuture<List<WriteResult>> future = batch.commit();
                documentId++; // Increment the document ID for the next write

/*

                count++;

                if (count == chunk.getItems().size()) {
                    break; // Sortir de la boucle après avoir écrit tous les éléments
                }

*/
                //String it= chunk.getItems().get(0).toString();
                //System.out.println("it : "+it );
                //docRef.set(chunk.getItems().get(0).toString()); // Assurez-vous que cela correspond à la structure de vos données Firestore

            }catch (Exception e) {
                // Log or handle the exception appropriately
                e.printStackTrace();
            }
        }
        }

    }
}
