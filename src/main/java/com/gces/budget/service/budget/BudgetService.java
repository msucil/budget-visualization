package com.gces.budget.service.budget;

import com.gces.budget.domain.dto.BudgetSheetDTO;
import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.exception.SheetNotFoundException;
import com.gces.budget.repository.ExpenseBudgetRepository;
import com.gces.budget.repository.IncomeBudgetRepository;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by minamrosh on 12/9/15.
 */
@Service
public class BudgetService {

    private final Logger log = LoggerFactory.getLogger(BudgetService.class);

    private IncomeBudgetRepository incomeBudgetRepo;

    private ExpenseBudgetRepository expenseBudgetRepo;

    private IncomeBudget savedIncomeBudget;

    private ExpenseBudget savedExpenseBudget;

    private ISheetSerivce sheetService;

    MultipartFile file;

    @Autowired
    public void setIncomeBudgetRepo(IncomeBudgetRepository incomeBudgetRepo){
        this.incomeBudgetRepo = incomeBudgetRepo;
    }

    @Autowired
    public void setExpenseBudgetRepo(ExpenseBudgetRepository expenseRepo){
        this.expenseBudgetRepo = expenseRepo;
    }

    @Autowired
    public void setSheetService(ISheetSerivce sheetService){
        this.sheetService = sheetService;
    }


    public IncomeBudget saveIncomeBudget(MultipartFile incomeBudgetFile,
                                         BudgetSheetDTO budgetSheetDTO, String userId) throws IOException {

        try{
            IncomeBudget incomeBudget = sheetService.getIncomeBudgetFromFile(incomeBudgetFile);

            log.info(incomeBudget.toString());

            incomeBudget.setFiscalYear(budgetSheetDTO.getFiscalYear());
            incomeBudget.setUserId(userId);
//            return incomeBudget;
            log.info("income budget read " + incomeBudget);

            if(incomeBudget.getMeiseMasterDegree() == null){
                incomeBudget.setMeiseMasterDegree(BigDecimal.ZERO);
            }
            savedIncomeBudget = incomeBudgetRepo.save(incomeBudget);
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
            ExpenseBudget expenseBudget = sheetService.getExpenseBudgetFromFile(expenseBudgetFile);
            expenseBudget.setFiscalYear(budgetSheetDTO.getFiscalYear());
            expenseBudget.setUserId(userId);

            log.info("Expense Budget Read From File : \n" + expenseBudget);

            if(expenseBudget.getRdActivity() == null){
                expenseBudget.setRdActivity(BigDecimal.ZERO);
            }

            if(expenseBudget.getAppProcedureMProg() == null){
                expenseBudget.setAppProcedureMProg(BigDecimal.ZERO);
            }

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

    public void deleteIncomeBudgetSheetById(String sheetId){
        incomeBudgetRepo.delete(sheetId);
    }

    public void deleteAllIncomeBudget(){
        incomeBudgetRepo.deleteAll();
    }

    public void deleteAllExpenseBudget(){
        expenseBudgetRepo.deleteAll();
    }

    public List<IncomeBudget> getAllIncomeBudgetSheets(String userId){
        return incomeBudgetRepo.findAllByUserIdOrderByFiscalYearDesc(userId);

    }


    public IncomeBudget getIncomeBudgetSheetById(String sheetId){
        IncomeBudget incomeBudget = incomeBudgetRepo.findOne(sheetId);
        if(incomeBudget == null)
//            throw new NullPointerException("Budget sheet doesn't exist!");
            throw new SheetNotFoundException("Requested Income Budget Sheet Doesn't Exist!");
        else

            return incomeBudget;
    }

    public BigDecimal getTotalIncomeBudget(IncomeBudget incomeBudget){
        log.info(incomeBudget.toString());
        BigDecimal total = BigDecimal.ZERO;
        return total.add(incomeBudget.getAppAdmitionFee()).add(incomeBudget.getInternetLibraryFee())
                .add(incomeBudget.getMeiseMasterDegree()).add(incomeBudget.getPhotocopyIncome())
                .add(incomeBudget.getSemesterFee()).add(incomeBudget.getTelephoneIncome())
                .add(incomeBudget.getMiscellaneousIncome()).add(incomeBudget.getStudentCard());
    }

    public ExpenseBudget getExpenseBudgetSheetById(String sheetId){
        ExpenseBudget expenseBudget = expenseBudgetRepo.findOne(sheetId);

        if(expenseBudget == null){
            throw new SheetNotFoundException("Requested Expense Budget Sheet Doesn't Exist!");
        }

        return expenseBudget;
    }

    public List<ExpenseBudget> getAllExpenseBudgetSheetByUser(String userId){
        return expenseBudgetRepo.findAllByUserIdOrderByFiscalYearDesc(userId);
    }

    public BigDecimal getTotalExpenseBudget(ExpenseBudget expense){
        BigDecimal total = BigDecimal.ZERO;

        return total.add(expense.getAdvPubRelation()).add(expense.getAppProcedureMProg())
                .add(expense.getDepreciation()).add(expense.getElectricity())
                .add(expense.getExtraCurricular()).add(expense.getFurniture())
                .add(expense.getGratuity()).add(expense.getHouseRent())
                .add(expense.getInternetExpense()).add(expense.getLibraryBooks())
                .add(expense.getMiscellaneous()).add(expense.getNewsMagazine())
                .add(expense.getOfficeEqupComputers()).add(expense.getOtInsurance())
                .add(expense.getProjectedSurplus()).add(expense.getProvidentFund())
                .add(expense.getRdActivity()).add(expense.getRebateForFee())
                .add(expense.getRepairMaintenance()).add(expense.getSalaryAllowances())
                .add(expense.getScholarshipAward()).add(expense.getSickLeaveOthers())
                .add(expense.getStationaryExamMaterials()).add(expense.getStudentWalefareMahotsav())
                .add(expense.getTelephone()).add(expense.getTransportationFare());
    }


    public void deleteExpenseBudgetById(String sheetId){
        expenseBudgetRepo.delete(sheetId);
    }

    public void getAllTotalIncomeBudget(String userid){
        List<IncomeBudget> incomeBudgets = incomeBudgetRepo.findAllByUserIdOrderByFiscalYearDesc(userid);


    }

}
