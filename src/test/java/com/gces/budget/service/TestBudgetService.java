package com.gces.budget.service;

/**
 * Created by minamrosh on 12/9/15.
 */

import com.gces.budget.BudgetVisualizationApplication;
import com.gces.budget.domain.dto.BudgetSheetDTO;
import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import com.gces.budget.service.budget.BudgetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by minamrosh on 11/26/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@Profile("dev")
public class TestBudgetService {
    private final Logger log = LoggerFactory.getLogger(TestBudgetService.class);

    private BudgetService budgetService;

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Autowired
    public void setBudgetService(BudgetService budgetService){
        this.budgetService = budgetService;
    }


    @Before
    public void deleteAllIncomeBudget(){
        budgetService.deleteAllIncomeBudget();
    }

    @Before
    public void deleteAllExpenseBudget(){
        budgetService.deleteAllExpenseBudget();
    }

    @Test
    public void shouldDisplayIncomeFromFile() {
        try{
        MockMultipartFile uploadFile = new MockMultipartFile("incomeBudget",
                new FileInputStream(new File("/home/minamrosh/Desktop/budget/income 2070-2071.xls")));

            BudgetSheetDTO budgetSheetDTO = new BudgetSheetDTO();

            budgetSheetDTO.setFiscalYear("2068-69");

            log.info(budgetSheetDTO.toString());

            User user = userRepository.findOneByUsername("prabesh");

            log.info(user.toString());

            IncomeBudget budget = this.budgetService.saveIncomeBudget(uploadFile, budgetSheetDTO, user.getId());
//            budget = this.incomeBudgetRepository.save(budget);
            log.info("Saved One : \n" + budget);
        }
        catch (IOException ex){
            log.info(ex.getMessage());
        }
        catch (NullPointerException ex){
            log.info(ex.getMessage());
        }
        catch(DuplicateKeyException dup){
            log.info((dup.getStackTrace().toString()));
        }
    }

    @Test
    public void shouldDisplayExpenseFromFile(){
        try{
            MockMultipartFile uploadFile = new MockMultipartFile("expenseBudget",
                    new FileInputStream(new File("/home/minamrosh/Desktop/budget/expense 2070-2071.xls")));

            BudgetSheetDTO budgetSheetDTO = new BudgetSheetDTO();
            budgetSheetDTO.setFiscalYear("2077-78");
            User user = userRepository.findOneByUsername("prabesh");
            ExpenseBudget expense = this.budgetService.saveExpenseBudget(uploadFile, budgetSheetDTO, user.getId());
            log.info("saved one : \n"+expense);
        }
        catch (IOException ex){
            log.debug(ex.getMessage());
        }
        catch (DuplicateKeyException ex){
            log.debug(ex.getStackTrace().toString());
        }
    }
}
