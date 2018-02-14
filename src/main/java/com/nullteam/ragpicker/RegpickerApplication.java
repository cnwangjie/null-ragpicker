package com.nullteam.ragpicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RegpickerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegpickerApplication.class, args);
    }
}
