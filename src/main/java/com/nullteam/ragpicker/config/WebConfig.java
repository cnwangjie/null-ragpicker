package com.nullteam.ragpicker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>Title: WebConfig.java</p>
 * <p>Package: com.nullteam.ragpicker.config</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/03/18
 * @author WangJie <i@i8e.net>
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    private final Config config;

    @Autowired
    public WebConfig(Config config) {
        this.config = config;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "cache-control", "x-requested-with");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/web/**")
                .addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/public/static/");

        super.addResourceHandlers(registry);
    }
}
