package observability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"observability", "com.bankapp.auth"})
public class ObservabilityApplication {
    public static void main(String[] args) {
        System.out.println("This is it");
        SpringApplication.run(ObservabilityApplication.class, args);
    }
}