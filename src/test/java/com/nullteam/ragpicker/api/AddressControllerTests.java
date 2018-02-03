package com.nullteam.ragpicker.api;

import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.model.User;
import com.nullteam.ragpicker.service.JWTService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
        MockHttpServletRequest req = new MockHttpServletRequest();

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
}
