package com.nullteam.ragpicker.api;

import com.nullteam.ragpicker.config.JWTConfig;
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

import static java.lang.String.format;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Title: UserControllerTests.java</p>
 * <p>Package: com.nullteam.ragpicker.api</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 03/14/18
 * @author WangJie <i@i8e.net>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class UserControllerTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTService jwtService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JWTConfig jwtConfig;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String GET_USER_INFO_API_URL_TEAP = "/api/user/%d/info";
    private static final int EXISTS_USER_ID = 1;

    private User getUser() {
        return entityManager.find(User.class, EXISTS_USER_ID);
    }

    private String token;

    private String getToken() {
        if (this.token == null)
            this.token = jwtService.genUserToken(getUser());
        logger.info("token: " + this.token);
        return this.token;
    }

    @Before
    public void setUp() {
        logger.info("start to test cate api controller");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getUserInfoWithoutToken() throws Exception {
        mockMvc.perform(get(format(GET_USER_INFO_API_URL_TEAP, EXISTS_USER_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getUserInfoWithToken() throws Exception {
        mockMvc.perform(get(format(GET_USER_INFO_API_URL_TEAP, EXISTS_USER_ID))
                .header(jwtConfig.getJWTHeader(), getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
