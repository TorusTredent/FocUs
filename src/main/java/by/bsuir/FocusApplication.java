package by.bsuir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FocusApplication {

    public static void main(String[] args) {
        SpringApplication.run(FocusApplication.class, args);
    }

}
