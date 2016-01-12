package com.gces.budget.web;

import com.gces.budget.domain.dto.BudgetSheetDTO;
import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.exception.SheetNotFoundException;
import com.gces.budget.repository.UserRepository;
import com.gces.budget.service.UserService;
import com.gces.budget.service.budget.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

/**
 * Created by minamrosh on 1/5/16.
 */

@Controller
public class DashboardController {

    private BudgetService budgetService;

    private UserRepository userRepo;

    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    public void setBudgetService(BudgetService budgetService){
        this.budgetService = budgetService;
    }

    @Autowired
    public void setUserRepo(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/dashboard")
    public String dashboard(Model model){
        model.addAttribute("pageTitle","Dashboard | Budget Visualization & Analysis Tool");
        return "dashboard";
    }

    @RequestMapping(value = "/dashboard/income-budget-sheets")
    public String incomeBudgetSheets(Model model, Principal principal){
        String userId = userRepo.findOneByUsername(principal.getName()).getId();
        List<IncomeBudget> incomeBudgets = budgetService.getAllIncomeBudgetSheets(userId);

        if(incomeBudgets != null) {
            model.addAttribute("budgetSheets", incomeBudgets);
            log.info("budget Sheets" + incomeBudgets.toString());
        }
        model.addAttribute("income",true);
        model.addAttribute("pageTitle","Income Budget Sheets | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Income Budget Sheets");

        return "budget-sheets";
    }

    @RequestMapping(value = "/dashboard/view-income-budget-sheet/{sheetId}",method = RequestMethod.GET)
    public String viewIncomeBudgetSheet(@PathVariable("sheetId") String sheetId, Model model){
        IncomeBudget incomeBudget = budgetService.getIncomeBudgetSheetById(sheetId);

        model.addAttribute("pageTitle","Detail Income Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Detail Income Budget Sheet");
        model.addAttribute("income",true);
        model.addAttribute("budget",incomeBudget);
        BigDecimal totalIncome = budgetService.getTotalIncomeBudget(incomeBudget);
        log.info("total income" + totalIncome);


        model.addAttribute("total", budgetService.getTotalIncomeBudget(incomeBudget));
        return "detail-budget-sheet";
    }



    @RequestMapping(value = "/dashboard/upload-income-budget-sheet", method = RequestMethod.GET)
    public String uploadIncomeBudgetSheetForm(Model model, BudgetSheetDTO budgetSheetDTO){
        model.addAttribute("budget", budgetSheetDTO);
        model.addAttribute("pageTitle","Upload Income Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Upload Income Budget Sheet");
        model.addAttribute("income",true);
        return "upload-budget-sheet";
    }

    @RequestMapping(value = "/dashboard/upload-income-budget-sheet", method = RequestMethod.POST)
    public String uploadIncomeBudgetSheet(@ModelAttribute("budget") @Valid BudgetSheetDTO budgetSheetDTO,
                                          @RequestParam("budgetSheet") MultipartFile budgetSheet,
                                          BindingResult result,
                                          Principal principal,
                                          Model model) throws IOException {

        model.addAttribute("pageTitle","Upload Income Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Upload Income Budget Sheet");
        model.addAttribute("income",true);

        if(result.hasErrors()){
            return "upload-budget-sheet";
        }

        if(budgetSheet.isEmpty()){
            result.addError(new FieldError("budget","budgetSheet","Select Income Budget Sheet"));
            return "upload-budget-sheet";
        }
        try {
            IncomeBudget incomeBudget = budgetService.saveIncomeBudget(budgetSheet, budgetSheetDTO,
                    userService.getCurrentUserId(principal));
        }
        catch (IOException ex){
            throw new IOException(ex.getMessage());
        }
        catch (NullPointerException ex){
            log.debug(ex.getStackTrace().toString());
            throw new NullPointerException("Unable to Save file");
        }
        catch (DuplicateKeyException dup){
            result.addError(new FieldError("budget","fiscalYear","Fiscal Year Already Exist!"));
            return "upload-budget-sheet";
        }


        return "redirect:/dashboard/income-budget-sheets";
    }

    @RequestMapping(value = "/dashboard/delete-income-budget-sheet",method = RequestMethod.POST)
    public String delteIncomeBudgetSheet(@ModelAttribute("sheetId") String sheetId) throws Exception{

        budgetService.deleteIncomeBudgetSheetById(sheetId);

        return "redirect:/dashboard/income-budget-sheets";
    }

    @RequestMapping(value = "/dashboard/update-income-budget-sheet/{sheetId}")
    public String displayIncomeBudgetUpdateForm(@PathVariable("sheetId") String sheetId, Model model)
     throws SheetNotFoundException{
        IncomeBudget budget = budgetService.getIncomeBudgetSheetById(sheetId);
        if(budget != null){
            BudgetSheetDTO budgetSheetDTO = new BudgetSheetDTO();
            budgetSheetDTO.setSheetId(sheetId);
            model.addAttribute("budget", budgetSheetDTO);
        }

        model.addAttribute("income",true);
        model.addAttribute("pageTitle","Update Income Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Update Income Budget Sheet");
        return "update-sheet";
    }

    @RequestMapping(value = "/dashboard/expense-budget-sheets")
    public String expenseBudgetSheets(Model model, Principal principal){
        String userId = userRepo.findOneByUsername(principal.getName()).getId();
        model.addAttribute("budgetSheets",
                budgetService.getAllExpenseBudgetSheetByUser(userId)
        );
        model.addAttribute("income",false);

        model.addAttribute("pageTitle","Expense Budget Sheets | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Expense Budget Sheets");

        return "budget-sheets";
    }

    @RequestMapping(value = "/dashboard/upload-expense-budget-sheet",method = RequestMethod.GET)
    public String uploadExpenseBudgetSheetForm(Model model, BudgetSheetDTO budgetSheetDTO){
        model.addAttribute("budget", budgetSheetDTO);
        model.addAttribute("pageTitle","Upload Expense Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Upload Expense Budget Sheet");
        model.addAttribute("income",false);
        return "upload-budget-sheet";
    }

    @RequestMapping(value="/dashboard/upload-expense-budget-sheet", method = RequestMethod.POST)
    public String saveExpenseBudgetSheet(@ModelAttribute("budget") @Valid BudgetSheetDTO budgetSheetDTO,
                                         @RequestParam("budgetSheet") MultipartFile budgetSheet,
                                         BindingResult result,
                                         Principal principal,
                                         Model model) throws IOException{
        model.addAttribute("pageTitle","Upload Expense Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Upload Expense Budget Sheet");
        model.addAttribute("income",false);

        if(result.hasErrors()){
            return "upload-budget-sheet";
        }

        if(budgetSheet.isEmpty()){
            result.addError(new FieldError("budget","budgetSheet","Select Expense Budget Sheet"));
            return "upload-budget-sheet";
        }
        try {
            ExpenseBudget expenseBudget = budgetService.saveExpenseBudget(budgetSheet, budgetSheetDTO,
                    userService.getCurrentUserId(principal));
        }
        catch (IOException ex){
            throw new IOException(ex.getMessage());
        }
        catch (NullPointerException ex){
            log.debug(ex.getStackTrace().toString());
            throw new NullPointerException("Unable to Save file");
        }
        catch (DuplicateKeyException dup){
            result.addError(new FieldError("budget","fiscalYear","Fiscal Year Already Exist!"));
            return "upload-budget-sheet";
        }


        return "redirect:/dashboard/expense-budget-sheets";
    }

    @RequestMapping(value = "/dashboard/view-expense-budget-sheet/{sheetId}",method = RequestMethod.GET)
    public String viewExpenseBudgetSheet(@PathVariable("sheetId") String sheetId, Model model){

        ExpenseBudget expenseBudget = budgetService.getExpenseBudgetSheetById(sheetId);
        model.addAttribute("pageTitle","Detail Expense Budget Sheet | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Detail Expense Budget Sheet");
        model.addAttribute("income",false);
        model.addAttribute("budget",expenseBudget);
        log.info("expense budget" + expenseBudget);
        BigDecimal totalExpense = budgetService.getTotalExpenseBudget(expenseBudget);
        log.info("expense" + totalExpense);
        model.addAttribute("total", totalExpense);
        return "detail-budget-sheet";
    }

    @RequestMapping(value = "/dashboard/delete-expense-budget-sheet",method = RequestMethod.POST)
    public String deleteExpenseBudgetSheet(@ModelAttribute("sheetId") String sheetId) throws Exception{

        budgetService.deleteExpenseBudgetById(sheetId);

        return "redirect:/dashboard/expense-budget-sheets";
    }

}
