package com.gces.budget.service.budget;

import com.gces.budget.domain.dto.BudgetSheetDTO;
import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.repository.ExpenseBudgetRepository;
import com.gces.budget.repository.IncomeBudgetRepository;
import com.mongodb.DuplicateKeyException;
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

    private IncomeBudget savedIncomeBudget;

    private ExpenseBudget savedExpenseBudget;

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


    public IncomeBudget saveIncomeBudget(MultipartFile incomeBudgetFile, BudgetSheetDTO budgetSheetDTO, String userId) throws IOException {

        try{
            IncomeBudget incomeBudget = sheetService.getIncomeBudgetFromFile(incomeBudgetFile);

            incomeBudget.setFiscalYear(budgetSheetDTO.getFiscalYear());
            incomeBudget.setUserId(userId);
//            return incomeBudget;
            log.info("income budget read " + incomeBudget);
            savedIncomeBudget = incomeRepo.save(incomeBudget);
            log.info("saved income budget" + savedIncomeBudget);

            return  savedIncomeBudget;
        }
        catch(IOException ex){
            log.info("IOException " + ex.getMessage());
            throw new IOException("Unable to read uploaded file!");
        }
        catch (NullPointerException ex){
            log.info(ex.getLocalizedMessage());
            throw new NullPointerException(ex.getMessage());
        }
        catch(DuplicateKeyException dup){
            log.debug(dup.getMessage());
            throw new org.springframework.dao.DuplicateKeyException("Duplicate Key found");
        }
    }

    public ExpenseBudget saveExpenseBudget(MultipartFile expenseBudgetFile, BudgetSheetDTO budgetSheetDTO, String userId) throws IOException, DuplicateKeyException {

        try{
            ExpenseBudget expenseBudget = expenseBudget = sheetService.getExpenseBudgetFromFile(expenseBudgetFile);
            expenseBudget.setFiscalYear(budgetSheetDTO.getFiscalYear());
            expenseBudget.setUserId(userId);

            log.info("Expense Budget Read From File : \n" + expenseBudget);
            savedExpenseBudget = expenseBudgetRepo.save(expenseBudget);

            return savedExpenseBudget;

        }
        catch (IOException ex){
            log.debug(ex.getMessage());
            throw new IOException(ex.getMessage());

        }
        catch (NullPointerException ex){
            log.debug(ex.getMessage());
            throw new NullPointerException(ex.getMessage());
        }
        catch (org.springframework.dao.DuplicateKeyException ex){
            log.debug(ex.getMessage());
            throw new org.springframework.dao.DuplicateKeyException(ex.getMessage());
        }

    }

    public void deleteAllIncomeBudget(){
        incomeRepo.deleteAll();
    }

    public void deleteAllExpenseBudget(){
        expenseBudgetRepo.deleteAll();
    }


}
