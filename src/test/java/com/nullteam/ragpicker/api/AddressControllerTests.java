package com.nullteam.ragpicker.api;

import com.github.javafaker.Faker;
import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.model.Address;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.service.serviceImpl.JWTServiceImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class AddressControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private JWTServiceImpl jwtService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Faker faker = new Faker(new Locale("zh-CN"));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testListAddresses() throws Exception {
        User user = entityManager.find(User.class, 1);
        String token = jwtService.genUserToken(user);

        mockMvc.perform(get("/api/user/1/address")
                .header(jwtConfig.getJWTHeader(), token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));


        mockMvc.perform(get("/api/user/2/address")
                .header(jwtConfig.getJWTHeader(), token))
                .andDo(print())
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/user/1/address"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateAddress() throws Exception {
        User user = entityManager.find(User.class, 1);
        String token = jwtService.genUserToken(user);
        Address address = user.getAddresses().get(0);
        String newDetail = faker.address().streetAddress();
        mockMvc.perform(post(String.format("/api/address/%d", address.getId()))
                .header(jwtConfig.getJWTHeader(), token)
                .param("detail", newDetail)
                .param("tel", address.getTel())
                .param("location", address.getLocation().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("detail").value(newDetail));
    }

    @Test
    public void testAddAddress() throws Exception {
        User user = entityManager.find(User.class, 1);
        String token = jwtService.genUserToken(user);
        Integer location = 100000;
        String detail = faker.address().streetAddress();
        String tel = faker.phoneNumber().cellPhone();
        mockMvc.perform(post(String.format("/api/user/%d/address", user.getId()))
                .header(jwtConfig.getJWTHeader(), token)
                .param("location", location.toString())
                .param("detail", detail)
                .param("tel", tel))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("detail").value(detail))
                .andExpect(jsonPath("tel").value(tel));
    }
}
