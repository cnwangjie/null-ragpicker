package com.nullteam.ragpicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>Title: RegpickerApplication.java</p>
 * <p>Package: com.nullteam.ragpicker</p>
 * <p>Description:</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/19/18
 * @author WangJie <i@i8e.net>
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class RegpickerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegpickerApplication.class, args);
    }
}
