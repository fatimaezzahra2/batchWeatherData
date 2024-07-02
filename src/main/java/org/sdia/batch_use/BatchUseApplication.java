package org.sdia.batch_use;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class BatchUseApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BatchUseApplication.class, args);
		InputStream serviceAccountStream = BatchUseApplication.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");

		//FileInputStream serviceAccount =new FileInputStream("src/main/resources/static/accountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
				.build();

		FirebaseApp.initializeApp(options);
		Firestore db = FirestoreClient.getFirestore();
	}

}
