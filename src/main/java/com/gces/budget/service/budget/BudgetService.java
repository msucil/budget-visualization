package com.gces.budget.service.budget;

import com.gces.budget.domain.dto.BudgetSheetDTO;
import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.repository.ExpenseBudgetRepository;
import com.gces.budget.repository.IncomeBudgetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by minamrosh on 12/9/15.
 */
@Service
public class BudgetService {

    private final Logger log = LoggerFactory.getLogger(BudgetService.class);

    private IncomeBudgetRepository incomeRepo;

    private ExpenseBudgetRepository expenseBudgetRepo;

    private ISheetSerivce sheetService;

    MultipartFile file;

    @Autowired
    public void setIncomeRepo(IncomeBudgetRepository incomeRepo){
        this.incomeRepo = incomeRepo;
    }

    @Autowired
    public void setExpenseBudgetRepo(ExpenseBudgetRepository expenseRepo){
        this.expenseBudgetRepo = expenseRepo;
    }

    @Autowired
    public void setSheetService(ISheetSerivce sheetService){
        this.sheetService = sheetService;
    }


    public IncomeBudget saveIncomeBudget(MultipartFile incomeBudgetFile, BudgetSheetDTO budgetSheetDTO, String userId){
        IncomeBudget savedIncomeBudget = new IncomeBudget();
        try{
            IncomeBudget incomeBudget = sheetService.getIncomeBudgetFromFile(incomeBudgetFile);

            incomeBudget.setFiscalYear(budgetSheetDTO.getFiscalYear());
            incomeBudget.setUserId(userId);
//            return incomeBudget;

            savedIncomeBudget = incomeRepo.save(incomeBudget);
            return  savedIncomeBudget;
        }
        catch(IOException ex){
            log.info("IOException " + ex.getMessage());
        }
        catch (NullPointerException ex){
            log.info(ex.getLocalizedMessage());
        }

        log.info("Income Budget Read Frome File : \n"+savedIncomeBudget);
        return savedIncomeBudget;
    }

    public ExpenseBudget saveExpenseBudget(MultipartFile expenseBudgetFile, BudgetSheetDTO budgetSheetDTO, String userId){
        ExpenseBudget expenseBudget = null;
        try{
            expenseBudget = sheetService.getExpenseBudgetFromFile(expenseBudgetFile);
            expenseBudget.setFiscalYear(budgetSheetDTO.getFiscalYear());
            expenseBudget.setUserId(userId);

            return expenseBudgetRepo.save(expenseBudget);

        }
        catch (IOException ex){
            log.debug(ex.getMessage());
        }

        log.info("Expense Budget Read From File : \n"+expenseBudget);
        return null;
    }


}
