package com.nullteam.ragpicker;


import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.model.Collector;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.service.JWTService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTServiceTests {

    @Autowired
    private JWTService jwtService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JWTConfig jwtConfig;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setSecret() {
        String jwtSecret = RandomStringUtils.randomAlphanumeric(30);
        jwtConfig.setJWTSecret(jwtSecret);
        logger.info("==================================================");
        logger.info("set JWT secret as: " + jwtSecret);
        logger.info("==================================================");
    }

    @Test
    public void testJwtService() {
        assertThat(jwtService.getIdentityFromToken("")).isNull();
        assertThat(jwtService.getUserFromToken("")).isNull();
        assertThat(jwtService.getCollectorFromToken("")).isNull();
        String token = jwtService.genUserToken(entityManager.find(User.class, 1));
        assertThat(token).isNotNull();
        assertThat(jwtService.getIdentityFromToken(token)).isEqualTo("user");
        assertThat(jwtService.getUserFromToken(token)).isNotNull();
        assertThat(jwtService.getUserFromToken(token).getId()).isEqualTo(1);
        token = jwtService.genCollectorToken(entityManager.find(Collector.class, 1));
        assertThat(token).isNotNull();
        assertThat(jwtService.getIdentityFromToken(token)).isEqualTo("collector");
        assertThat(jwtService.getUserFromToken(token)).isNotNull();
        assertThat(jwtService.getUserFromToken(token).getId()).isEqualTo(1);
    }
}
