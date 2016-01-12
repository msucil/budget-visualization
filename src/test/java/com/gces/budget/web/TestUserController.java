package com.gces.budget.web;

import com.gces.budget.BudgetVisualizationApplication;
import com.gces.budget.domain.dto.UserDTO;
import com.gces.budget.domain.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by minamrosh on 11/25/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@Profile("dev")
@WebAppConfiguration
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class TestUserController {
    private Logger log = LoggerFactory.getLogger(TestUserController.class);

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity())
//                .defaultRequest(get("/").with(user("user")))
                .build();
    }


    @Test
    @WithMockUser(username = "prabesh")
    public void shoudlReturnViewUserDtoWithUserdetail() throws Exception{
        mockMvc.perform(get("/user/account"))
                .andExpect(view().name("user-account"))
                .andExpect(model().attribute("user",hasProperty("username",notNullValue())))
                .andExpect(model().attribute("userDTO",isA(UserDTO.class)))
                .andExpect(model().attribute("userDTO",hasProperty("username",equalToIgnoringCase("prabesh"))))
                .andExpect(model().attribute("user",isA(User.class)));
    }
}
