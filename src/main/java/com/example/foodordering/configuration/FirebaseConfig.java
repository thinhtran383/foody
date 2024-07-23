package com.example.foodordering.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
public class FirebaseConfig {
    @Value("${fcm.json.key.url}")
    private String fcmJsonKeyUrl;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
//        URL url = new URL(fcmJsonKeyUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//            throw new IOException("Failed to fetch JSON key file: " + connection.getResponseMessage());
//        }
//
//        InputStream serviceAccount = connection.getInputStream();
//
//        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
//
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream( // from json file local
                new ClassPathResource("food-ordering-1666f-firebase-adminsdk-pel1z-f52249f369.json").getInputStream()
        );


        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(firebaseOptions, "my-app");
        }

        return FirebaseMessaging.getInstance(FirebaseApp.getInstance("my-app"));
    }



}
