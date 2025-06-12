package org.Scsp.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmokingCessationSupportPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmokingCessationSupportPlatformApplication.class, args);
    }

}
