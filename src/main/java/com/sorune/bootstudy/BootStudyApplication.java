package com.sorune.bootstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BootStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStudyApplication.class, args);
    }

}
