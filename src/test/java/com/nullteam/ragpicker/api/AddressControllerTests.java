package com.nullteam.ragpicker.api;

import com.github.javafaker.Faker;
import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.service.JWTService;
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
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class AddressControllerTests {

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

    private static final String LIST_ADDRESSES_API_URL_TEMP = "/api/user/%d/address";
    private static final String ADD_ADDRESS_API_URL_TEMP = "/api/user/%d/address";
    private static final String GET_AN_ADDRESS_API_URL_TEMP = "/api/address/%d";
    private static final String UPDATE_AN_ADDRESS_API_URL_TEMP = "/api/address/%d";
    private static final String DELETE_AN_ADDRESS_API_URL_TEMP = "/api/address/%d/delete";
    private static final int EXISTS_USER_ID = 1;
    private static final int NOT_EXISTS_USER_ID = 100000;

    private User getUser() {
        return entityManager.find(User.class, EXISTS_USER_ID);
    }

    private String token;

    private String getToken() {
        if (this.token == null)
            this.token = jwtService.genUserToken(getUser());
        return this.token;
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
    public void listAddressesOfNotExistsUser() throws Exception {
        logger.info("list address of not exists user should return status 404");
        mockMvc.perform(get(format(LIST_ADDRESSES_API_URL_TEMP, NOT_EXISTS_USER_ID)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listAddressesWithoutAuthorization() throws Exception {
        logger.info("list address without authorization should return status 401");
        mockMvc.perform(get(format(LIST_ADDRESSES_API_URL_TEMP, EXISTS_USER_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void listAddressesSuccess() throws Exception {
        logger.info("list address success should return JSON with status 200");
        mockMvc.perform(get(format(LIST_ADDRESSES_API_URL_TEMP, EXISTS_USER_ID))
                .header(jwtConfig.getJWTHeader(), getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void listAddressOfOthers() throws Exception {
        logger.info("list address of others should return status 403");
        mockMvc.perform(get(format(LIST_ADDRESSES_API_URL_TEMP, EXISTS_USER_ID + 1))
                .header(jwtConfig.getJWTHeader(), getToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateAddress() throws Exception {
        Address address = getUser().getAddresses().get(0);
        String newDetail = faker.address().streetAddress();

        logger.info("update address success should return JSON of new address with status 200");
        mockMvc.perform(post(format(UPDATE_AN_ADDRESS_API_URL_TEMP, address.getId()))
                .header(jwtConfig.getJWTHeader(), getToken())
                .param("detail", newDetail)
                .param("tel", address.getTel())
                .param("location", address.getLocation().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("detail").value(newDetail));
    }

    @Test
    public void addAddress() throws Exception {
        Integer location = 100000;
        String detail = faker.address().streetAddress();
        String tel = faker.phoneNumber().cellPhone();

        logger.info("add an address success should return JSON of new address with status 200");
        mockMvc.perform(post(format(ADD_ADDRESS_API_URL_TEMP, getUser().getId()))
                .header(jwtConfig.getJWTHeader(), getToken())
                .param("location", location.toString())
                .param("detail", detail)
                .param("tel", tel))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("detail").value(detail))
                .andExpect(jsonPath("tel").value(tel));
    }

    @Test
    public void getAnAddressDetail() throws Exception {
        Address address = getUser().getAddresses().get(0);
        mockMvc.perform(get(format(GET_AN_ADDRESS_API_URL_TEMP, address.getId()))
                .header(jwtConfig.getJWTHeader(), getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("detail").value(address.getDetail()))
                .andExpect(jsonPath("tel").value(address.getTel()));
    }

    @Test
    public void deleteAnAddress() throws Exception {
        List<Address> addressList = getUser().getAddresses();
        Address address = addressList.get(addressList.size() - 1);
        mockMvc.perform(post(format(DELETE_AN_ADDRESS_API_URL_TEMP, address.getId()))
                .header(jwtConfig.getJWTHeader(), getToken()))
                .andExpect(status().isOk());

        List<Address> newAddressList = getUser().getAddresses();
        assertEquals(newAddressList.size(), addressList.size() - 1);
        assertFalse(newAddressList.contains(address));
    }
}
