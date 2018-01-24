package com.nullteam.ragpicker;

import com.github.javafaker.Faker;
import com.nullteam.ragpicker.model.WxUser;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@RunWith(SpringRunner.class)
public class ModelTests {

    private Faker faker = new Faker(new Locale("zh-CN"));

    private Logger logger = Logger.getLogger(this.getClass().toString());

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void createTestData() {
        entityManager.createNativeQuery("SET foreign_key_checks = 0;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `address`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `admin`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `cate`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `collector`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `orders`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `order_detail`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `user`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `wx_user`;").executeUpdate();
        logger.info("=================================================");
        logger.info("database truncated success, generating test data...");
        logger.info("=================================================");
        Long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i += 1) {
            WxUser wxUser = new WxUser();
            wxUser.setWxid(faker.letterify(StringUtils.repeat("?", 28)));
            wxUser.setNickname(faker.name().fullName());
            wxUser.setAvatar(faker.internet().avatar());
            entityManager.persist(wxUser);
            entityManager.flush();
            logger.info("create wxuser " + i + " id: " + wxUser.getId().toString());
        }

        // TODO: generate data of all models
        Long spendTime = System.currentTimeMillis() - startTime;
        logger.info("=================================================");
        logger.info("test data generated success in " + spendTime + "ms");
        logger.info("=================================================");
        entityManager.createNativeQuery("SET foreign_key_checks = 1;").executeUpdate();
    }

    @Test
    public void test() {
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "wx_user");
        assertThat(rows).isEqualTo(10);
        // TODO: test with JPA repository
        // TODO: test whether entity serialize result json is same with design

        // TODO: travis.yml
    }
}
