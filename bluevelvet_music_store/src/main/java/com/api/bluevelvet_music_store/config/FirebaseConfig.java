package com.api.bluevelvet_music_store.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(
                        "src/main/resources/bluevelvet-music-store-firebase-adminsdk-*****-**********.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://<your-database-name>.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
