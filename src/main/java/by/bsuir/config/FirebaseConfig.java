package by.bsuir.config;

import by.bsuir.entity.security.FirebaseConfigProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Autowired
    private FirebaseConfigProperties firebaseConfigProperties;


    @Primary
    @Bean
    public void firebaseInit() {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String s = ow.writeValueAsString(firebaseConfigProperties).replace("\\\\","\\");

            InputStream targetStream = new ByteArrayInputStream(s.getBytes());

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(targetStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            log.debug("Firebase Initialize");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}