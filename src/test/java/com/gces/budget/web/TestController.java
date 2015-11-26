package com.gces.budget.web;

import com.gces.budget.BudgetVisualizationApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by minamrosh on 11/25/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@Profile("dev")
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class);

    @Test
    public void testHomePage(){
        UserController controller = new UserController();
        log.debug("controller initialized");

        MockMvc mockMvc = standaloneSetup(controller).build();
        log.debug("controller setup");
        try {
            mockMvc.perform(get("/")).andExpect(view().name("index"));
        }
        catch (Exception ex){
            log.trace(ex.getMessage());
        }
    }
}
