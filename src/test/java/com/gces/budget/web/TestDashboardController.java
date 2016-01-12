package com.gces.budget.web;

import com.gces.budget.BudgetVisualizationApplication;
import com.gces.budget.domain.entity.ExpenseBudget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by minamrosh on 1/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@WebAppConfiguration
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})

public class TestDashboardController {

    private final Logger log = LoggerFactory.getLogger(TestDashboardController.class);

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
    @WithMockUser
    public void shouldReturnDashboardViewNameAndPageTitle(){

        try {
            mockMvc.perform(get("/dashboard"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("dashboard"))
//                    .andExpect(model().attribute("pageTitle").hasValue("Dashboard | Budget Visualization & Analysis Tool")));
                    .andExpect(model().attribute("pageTitle",
                            hasToString("Dashboard | Budget Visualization & Analysis Tool")));

        }

        catch (Exception ex){
            log.info(" dummy text" +ex.getLocalizedMessage());
        }
    }

    @Test
    @WithMockUser(username = "prabesh")
    public void shouldReturnViewNameBudgetSheetAndPageTitleWithIncomeTrue() throws Exception{
        mockMvc.perform(get("/dashboard/income-budget-sheets"))
                .andExpect(status().isOk())
                .andExpect(view().name("budget-sheets"))
                .andExpect(model().attribute("sectionTitle", hasToString("Income Budget Sheets")))
                .andExpect(model().attribute("income",is(true)))
                .andExpect(model().attribute("pageTitle",
                        hasToString("Income Budget Sheets | Budget Visualization & Analysis Tool")));
    }

    @Test
    @WithMockUser(username = "prabesh")
    public void shouldReturnIncomeBudgetById() throws Exception{
        mockMvc.perform(get("/dashboard/update-income-budget-sheet/568cd48c44ae383872ab89e1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-sheet"))
                .andExpect(model().attribute("incomeBudget", hasProperty("fiscalYear")));
    }

    @Test
    @WithMockUser(username = "prabesh")
    public void shouldReturnViewSectionTitleBudgetSheetDto() throws Exception{
        mockMvc.perform(
                get("/dashboard/upload-income-budget-sheet"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload-budget-sheet"))
                .andExpect(model().attribute("sectionTitle",hasToString("Upload Income Budget Sheet")))
                .andExpect(model().attribute("budget",hasProperty("budgetSheet")))
                .andExpect(model().attribute("budget",hasProperty("fiscalYear")));
    }

    @Test
    @WithMockUser(username = "prabesh")
    public void shouldReturnViewAndIncomeBudgetWithIncomeTrue() throws Exception{
        mockMvc.perform(get("/dashboard/view-income-budget-sheet/5694beb044aeb258e56a6586"))
                .andExpect(status().isOk())
                .andExpect(view().name("detail-budget-sheet"))
                .andExpect(model().attribute("total",isA(BigDecimal.class)))
                .andExpect(model().attribute("budget", hasProperty("fiscalYear")));
    }


    @Test
    @WithMockUser(username = "prabesh")
    public void shouldReturnViewBudgetSheetsAndTitleExpenseBudgetWithIncomeFalse() throws Exception{
        mockMvc.perform(get("/dashboard/expense-budget-sheets"))
                .andExpect(status().isOk())
                .andExpect(view().name("budget-sheets"))
                .andExpect(model().attribute("income",is(false)))
                .andExpect(model().attribute("sectionTitle",hasToString("Expense Budget Sheets")));
    }

    @Test
    @WithMockUser(username = "prabesh")
    public void shoudlReturnViewExpenseBudgetAndTotal() throws Exception{
        mockMvc.perform(get("/dashboard/view-expense-budget-sheet/5694da7544aee07780d84328"))
                .andExpect(status().isOk())
                .andExpect(view().name("detail-budget-sheet"))
                .andExpect(model().attribute("budget",isA(ExpenseBudget.class)))
                .andExpect(model().attribute("income", is(false)));
//                .andExpect(model().attribute("total",isA(BigDecimal.class)));
    }


}


