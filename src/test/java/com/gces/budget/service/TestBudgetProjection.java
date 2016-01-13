package com.gces.budget.service;

import com.gces.budget.BudgetVisualizationApplication;
import com.gces.budget.domain.dto.BudgetDTO;
import com.gces.budget.service.budget.BudgetService;
import com.gces.budget.service.budget.analysis.BudgetAnalysisService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by minamrosh on 1/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@Profile("dev")
public class TestBudgetProjection {

    private final Logger log = LoggerFactory.getLogger(TestBudgetProjection.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    private String userId;

    List<BudgetDTO> budgetDTos;

    BudgetAnalysisService budgetAnalysisService;

    @Before
    public void setUserId(){
        this.userId = userService.getUserByUsername("prabesh").getId();
        budgetDTos = budgetService.getAllTotalIncomeBudget(userId);
        budgetAnalysisService = new BudgetAnalysisService(budgetDTos);
    }

    @Test
    public void shouldReturnYearInterval(){
        int[] yearInterval = budgetAnalysisService.getYearInterval();
        for(int i = 0; i < yearInterval.length; i++){
            log.info(String.valueOf(yearInterval[i]));
        }
    }

    @Test
    public void shouldReturnSumofYearInterval(){
        double yearIntervalSum = budgetAnalysisService.getYearIntervalSum();
        assertThat(yearIntervalSum, is(equalTo(6)));
        log.info("year interval sum : " + yearIntervalSum);
    }

    @Test
    public void shouldReturnSumofSquareOfYearInterval(){
        double yearIntervalSqrSum = budgetAnalysisService.getYearIntervalSqrSum();
        assertThat(yearIntervalSqrSum,is(equalTo(14)));
    }

    @Test
    public void shouldReturnSumofAllBudget(){
        double budgetSum = budgetAnalysisService.getBudgetSum();
        assertThat(budgetSum, not(equalTo(0.0)));
        log.info("Total budget sum : " + budgetSum);

    }

    @Test
    public void shouldReturnBudgetYrIntevalSum(){
        double budgetYrIntervalSum = budgetAnalysisService.getBudgetYrIntervalSum();
        assertThat(budgetYrIntervalSum, is(not(equalTo(0.0))));
        log.info("Budget Yr Interval Sum :" + budgetYrIntervalSum);
    }

    @Test
    public void shouldReturnSlope(){
        double slope = budgetAnalysisService.getSlope();
        assertThat(slope, not(equalTo(0.0)));
        log.info("slope : " + slope);
    }

    @Test
    public void shouldReturnIntercept(){
        double intercept = budgetAnalysisService.getIntercept();
        assertThat(intercept, not(equalTo(0.0)));
        log.info("intercept :" + intercept);
    }

}
