package com.nullteam.ragpicker;

import com.github.javafaker.Faker;
import com.nullteam.ragpicker.model.*;
import org.apache.commons.lang3.RandomStringUtils;
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

/**
 * <p>Title: ModelTests.java</p>
 * <p>Package: com.nullteam.ragpicker</p>
 * <p>Description: 模型测试</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/25/18
 * @author WangJie <i@i8e.net>
 */
@SpringBootTest
@Rollback(false)
@Transactional
@RunWith(SpringRunner.class)
public class ModelTests {

    private Faker faker = new Faker(new Locale("zh-CN"));

    private Logger logger = Logger.getLogger(this.getClass().toString());

    private static final String[][] cates_seed = {
        {"废钢","两","10"},
        {"废铁","两","8"},
        {"废铝","两","50"},
        {"废铜","两","9"},
        {"废报纸","两","13"},
        {"废纸箱","两","11"},
        {"旧书刊杂志","两","12"},
        {"玻璃渣","斤","6"},
        {"通用废塑料","两","22"},
        {"工程废塑料","两","26"}
    };
    private static final Integer[] status_seed = {0, 10, 12, 2, 40};
    private static final Integer[] location_seed = {110000, 110101, 110102, 110105, 110106, 110107, 110108, 110109, 110111, 110112, 110113, 110114, 110115, 110116, 110117};

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
        entityManager.createNativeQuery("TRUNCATE TABLE `order`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `order_detail`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `user`;").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE `wx_user`;").executeUpdate();
        logger.info("=================================================");
        logger.info("database truncated success, generating test data...");
        logger.info("=================================================");
        Long startTime = System.currentTimeMillis();

        for (int i = 0; i < 60; i += 1) {
            WxUser wxUser = new WxUser();
            wxUser.setWxid(faker.letterify(StringUtils.repeat("?", 28)));
            wxUser.setNickname(faker.name().fullName());
            wxUser.setAvatar(faker.internet().avatar());
            entityManager.persist(wxUser);
            entityManager.flush();
        }
        for (int i = 0; i < 30; i += 1) {
            User user = new User();
            user.setInfo(entityManager.find(WxUser.class, i + 1));
            entityManager.persist(user);
            entityManager.flush();
        }
        for (int i = 0; i < 30; i += 1) {
            Collector collector = new Collector();
            collector.setInfo(entityManager.find(WxUser.class, i + 31));
            collector.setName(faker.name().fullName());
            collector.setTel(faker.phoneNumber().cellPhone());
            collector.setLocation(location_seed[i / 2]);
            entityManager.persist(collector);
            entityManager.flush();
        }
        for (int i = 0; i < 45; i += 1) {
            Address address = new Address();
            address.setLocation(location_seed[i / 3]);
            address.setDetail(faker.address().streetAddress());
            address.setTel(faker.phoneNumber().cellPhone());
            address.setUser(entityManager.find(User.class, (int)(i / 1.5) + 1));
            entityManager.persist(address);
            entityManager.flush();
        }
        for (String[] i : cates_seed) {
            Cate cate = new Cate();
            cate.setName(i[0]);
            cate.setUnit(i[1]);
            cate.setPrice(Integer.valueOf(i[2]));
            entityManager.persist(cate);
            entityManager.flush();
        }
        for (int i = 0; i < 60; i += 1) {
            Order order = new Order();
            order.setUser(entityManager.find(User.class, faker.number().numberBetween(1, 30)));
            order.setCollector(entityManager.find(Collector.class, faker.number().numberBetween(1, 30)));
            order.setTel(faker.phoneNumber().cellPhone());
            order.setLocation(location_seed[i / 4]);
            order.setLocDetail(faker.address().streetAddress());
            order.setRemark(RandomStringUtils.randomAlphabetic(faker.number().numberBetween(0, 200)));
            order.setOrderNo(faker.letterify(StringUtils.repeat("?", 30)).toUpperCase());
            order.setStatus(status_seed[faker.number().numberBetween(0, status_seed.length)]);
            entityManager.persist(order);
            entityManager.flush();
            Integer amount = 0;
            for (int j = 0; j < faker.number().numberBetween(1, 10); j += 1) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setCate(entityManager.find(Cate.class, faker.number().numberBetween(1, 10)));
                orderDetail.setSum(faker.number().numberBetween(1, 20));
                orderDetail.setPrice(faker.number().numberBetween(1, 10000));
                orderDetail.setOrder(order);
                entityManager.persist(orderDetail);
                entityManager.flush();
                amount += orderDetail.getPrice() * orderDetail.getSum();
            }
            if (order.getStatus() == Order.Status.COMPLETED) {
                order.setAmount(amount);
                entityManager.persist(order);
                entityManager.flush();
            }
        }
        Long spendTime = System.currentTimeMillis() - startTime;
        logger.info("=================================================");
        logger.info("test data generated success in " + spendTime + "ms");
        logger.info("=================================================");
        entityManager.createNativeQuery("SET foreign_key_checks = 1;").executeUpdate();
    }

    @Test
    public void test() {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "wx_user")).isEqualTo(60);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user")).isEqualTo(30);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "collector")).isEqualTo(30);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "address")).isEqualTo(45);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "cate")).isEqualTo(10);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "`order`")).isEqualTo(60);
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "order_detail")).isGreaterThan(60);
        // TODO: test with JPA repository
        // TODO: test whether entity serialize result json is same with design

    }
}
