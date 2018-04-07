package com.nullteam.ragpicker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.model.*;
import com.nullteam.ragpicker.service.JWTService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;

import static java.lang.String.format;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Title: OrderControllerTests.java</p>
 * <p>Package: com.nullteam.ragpicker.api</p>
 * <p>Description: 订单相关接口控制器测试</p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 03/14/18
 * @author WangJie <i@i8e.net>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class OrderControllerTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Faker faker = new Faker(new Locale("zh-CN"));

    private static final String LIST_ORDERS_BY_USER_URL_TEMP = "/api/user/%d/order";
    private static final String CREATE_ORDER_URL_TEMP = "/api/user/%d/order";
    private static final String DELETE_ORDER_URL_TEMP = "/api/order/%s/cancel";
    private static final String LIST_ORDERS_BY_COLLECTOR_URL_TEMP = "/api/collector/%d/order";
    private static final String COMPLETE_ORDER_URL_TEMP = "/api/order/%s/complete";
    private static final int EXISTS_USER_ID = 1;
    private static final int EXISTS_COLLECTOR_ID = 1;
    private static final int NOT_EXISTS_USER_ID = 100000;

    private User getUser() {
        return entityManager.find(User.class, EXISTS_USER_ID);
    }

    private Collector getCollector() {
        return entityManager.find(Collector.class, EXISTS_USER_ID);
    }

    private String userToken;
    private String collectorToken;

    private String getUserToken() {
        if (this.userToken == null)
            this.userToken = jwtService.genUserToken(getUser());
        return this.userToken;
    }

    private String getCollectorToken() {
        if (this.collectorToken == null)
            this.collectorToken = jwtService.genCollectorToken(getCollector());
        return this.collectorToken;
    }

    private List<Cate> getAllCates() {
        CriteriaQuery<Cate> cq = entityManager.getCriteriaBuilder().createQuery(Cate.class);
        cq = cq.select(cq.from(Cate.class));
        return entityManager.createQuery(cq).getResultList();
    }

    private Order getLastestOrder() {
        CriteriaQuery<Order> cq = entityManager.getCriteriaBuilder().createQuery(Order.class);
        cq = cq.select(cq.from(Order.class));
        List<Order> orders = entityManager.createQuery(cq).getResultList();
        orders.sort(Comparator.comparingInt(Order::getId).reversed());
        return orders.get(0);
    }

    @Before
    public void setUp() {
        logger.info("start to test address api controller");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void listOrdersByUserWithoutToken() throws Exception {
        mockMvc.perform(get(format(LIST_ORDERS_BY_USER_URL_TEMP, EXISTS_USER_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void listOrdersByUserWithToken() throws Exception {
        mockMvc.perform(get(format(LIST_ORDERS_BY_USER_URL_TEMP, EXISTS_USER_ID))
                .header(jwtConfig.getJWTHeader(), getUserToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        // TODO: propreties verify
    }

    @Test
    public void createOrderWithoutCorrectParams() throws Exception {
        mockMvc.perform(post(format(CREATE_ORDER_URL_TEMP, EXISTS_USER_ID))
                .header(jwtConfig.getJWTHeader(), getUserToken()))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void createOrderWithCorrectParams() throws Exception {
        List<Cate> cates = getAllCates();
        List<HashMap<String, Integer>> orderDetails = new ArrayList<>();
        int ORDER_DETAILS_SIZE = 3;
        for (int i = 0; i < ORDER_DETAILS_SIZE; i += 1) {
            HashMap<String, Integer> orderDetail = new HashMap<>();
            orderDetail.put("cate_id", cates.get(faker.number().numberBetween(1, cates.size()) - 1).getId());
            orderDetail.put("sum", faker.number().numberBetween(1, 100));
            orderDetails.add(orderDetail);
        }
        String orderDetailsJsonStr = new ObjectMapper().writeValueAsString(orderDetails);
        logger.info("================================================");
        logger.info(orderDetailsJsonStr);
        logger.info("================================================");
        mockMvc.perform(post(format(CREATE_ORDER_URL_TEMP, EXISTS_USER_ID))
                .header(jwtConfig.getJWTHeader(), getUserToken())
                .param("address_id", String.valueOf(getUser().getAddresses().get(0).getId()))
                .param("remark", "")
                .param("order_details", orderDetailsJsonStr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Order order = getLastestOrder();
        assertEquals(order.getUser().getId().intValue(), EXISTS_USER_ID);
        assertEquals(order.getOrderDetail().size(), ORDER_DETAILS_SIZE);
        // TODO: detail content verify
    }

    @Transactional
    @Test
    public void deleteOrderWithToken() throws Exception {
        Order order = getLastestOrder();
        order.setCollector(null);
        order.setStatus(Order.Status.INIT);
        order.setUser(getUser());
        entityManager.persist(order);
        entityManager.flush();
        mockMvc.perform(post(format(DELETE_ORDER_URL_TEMP, getLastestOrder().getOrderNo()))
                .header(jwtConfig.getJWTHeader(), getUserToken()))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(getLastestOrder().getStatus().intValue(), Order.Status.CANCELED_BY_USER);
    }

    @Transactional
    @Test
    public void completeOrder() throws Exception {
        Order order = new Order();
        order.setUser(getUser());
        order.setTel("12345678900");
        order.setLocation(100000);
        order.setLocDetail(faker.address().streetAddress());
        order.setRemark("");
        order.setOrderNo(RandomStringUtils.randomAlphabetic(30));
        order.setStatus(Order.Status.ALLOTTED);
        order.setCollector(getCollector());
        entityManager.persist(order);
        entityManager.flush();
        int amount = 0;
        List<Cate> cates = getAllCates();
        List<HashMap<String, Integer>> orderDetails = new ArrayList<>();
        int ORDER_DETAILS_SIZE = 3;
        for (int i = 0; i < ORDER_DETAILS_SIZE; i += 1) {
            HashMap<String, Integer> orderDetail = new HashMap<>();
            orderDetail.put("cate_id", cates.get(faker.number().numberBetween(1, cates.size()) - 1).getId());
            orderDetail.put("sum", faker.number().numberBetween(1, 100));
            orderDetail.put("price", faker.number().numberBetween(1, 10000));
            orderDetails.add(orderDetail);
            amount += orderDetail.get("sum") * orderDetail.get("price");
        }
        String orderDetailsJsonStr = new ObjectMapper().writeValueAsString(orderDetails);
        mockMvc.perform(post(format(COMPLETE_ORDER_URL_TEMP, order.getOrderNo()))
                .header(jwtConfig.getJWTHeader(), getCollectorToken())
                .param("order_details", orderDetailsJsonStr))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        assertEquals(getLastestOrder().getStatus().intValue(), Order.Status.COMPLETED);
        assertEquals(getLastestOrder().getAmount().intValue(), amount);
    }
}
