package com.nullteam.ragpicker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: JWTConfig.java</p>
 * <p>Package: com.nullteam.ragpicker.config</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 02/01/18
 * @author WangJie <i@i8e.net>
 */
@Configuration
public class JWTConfig {

    @Value("#{\"${jwt.header}\".trim()}")
    private String JWTHeader;

    @Value("${jwt.secret}")
    private String JWTSecret;

    public String getJWTHeader() {
        return JWTHeader;
    }

    public void setJWTHeader(String JWTHeader) {
        this.JWTHeader = JWTHeader;
    }

    public String getJWTSecret() {
        return JWTSecret;
    }

    public void setJWTSecret(String JWTSecret) {
        this.JWTSecret = JWTSecret;
    }
}
