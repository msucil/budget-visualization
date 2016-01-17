package com.gces.budget.web;

import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.service.UserService;
import com.gces.budget.service.budget.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

import java.security.Principal;

/**
 * Created by minamrosh on 1/15/16.
 */

@Controller
public class VisualizeController {

    private final Logger log = LoggerFactory.getLogger(VisualizeController.class);

    private BudgetService budgetService;

    private UserService userService;

    @Autowired
    public void setBudgetService(BudgetService budgetService){
        this.budgetService = budgetService;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/visualize/income")
    public String visualizeIncome(Principal principal,Model model ){

        IncomeBudget incomeBudget = budgetService.getLatestIncomeBudget(userService.getCurrentUserId(principal));
//        ObjectMapper jsonmapper = new ObjectMapper();


//        log.info("json " +jsonmapper.writeValueAsString(incomeBudget));

        model.addAttribute("budget",incomeBudget);
        model.addAttribute("totalBudget",budgetService.getTotalIncomeBudget(incomeBudget));
        model.addAttribute("pageTitle","Pie Chart | Income | Visualization | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","Pie Chart of Income Budget for year "+ incomeBudget.getFiscalYear());
        return "income-piechart";
    }

    @RequestMapping(value="/visualize/income/{chart}")
    public String visualizeIncome(Principal principal, Model model, @PathVariable("chart") String chart){
        IncomeBudget incomeBudget = budgetService.getLatestIncomeBudget(userService.getCurrentUserId(principal));
        model.addAttribute("budget",incomeBudget);
        model.addAttribute("totalBudget",budgetService.getTotalIncomeBudget(incomeBudget));
        if(chart.equalsIgnoreCase("barchart")) {
            model.addAttribute("pageTitle", "Barchart | Income | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle", "Pie Chart of Income Budget for year " + incomeBudget.getFiscalYear());
            return "income-barchart";
        }
        else if(chart.equalsIgnoreCase("piechart")){
            model.addAttribute("pageTitle","Pie Chart | Income | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Pie Chart of Income Budget for year "+ incomeBudget.getFiscalYear());
            return "income-piechart";
        }
        else if(chart.equalsIgnoreCase("stackchart")){
            model.addAttribute("pageTitle","Stack Column Chart | Income Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Stack Column Chart of Income Budget for year "+ incomeBudget.getFiscalYear());
            return "income-stackchart";
        }
        else{
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/visualize/income/{year}/{chart}")
    public String visualizeIncomeByYear(Principal principal, Model model,
                                            @PathVariable("year") String year, @PathVariable("chart") String chart){
        IncomeBudget incomeBudget = budgetService.getIncomeBudgetByYear(year, userService.getCurrentUserId(principal));
        model.addAttribute("budget",incomeBudget);
        model.addAttribute("totalBudget",budgetService.getTotalIncomeBudget(incomeBudget));
        if(chart.equalsIgnoreCase("barchart")) {
            model.addAttribute("pageTitle", "Barchart | Income Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle", "Pie Chart of Income Budget for year " + incomeBudget.getFiscalYear());
            return "income-barchart";
        }
        else if(chart.equalsIgnoreCase("piechart")){
            model.addAttribute("pageTitle","Pie Chart | Income Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Pie Chart of Income Budget for year "+ incomeBudget.getFiscalYear());
            return "income-piechart";
        }
        else if(chart.equalsIgnoreCase("stackchart")){
            model.addAttribute("pageTitle","Stack Column Chart | Income Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Stack Column Chart of Income Budget for year "+ incomeBudget.getFiscalYear());
            return "income-stackchart";
        }
        else{
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value="/visualize/expense/{chart}")
    public String visualizeExpense(Principal principal, Model model,@PathVariable("chart") String chart){
        ExpenseBudget expenseBudget = budgetService.getLatestExpenseBudget(userService.getCurrentUserId(principal));
        model.addAttribute("budget",expenseBudget);
        model.addAttribute("totalBudget",budgetService.getTotalExpenseBudget(expenseBudget));
        if(chart.equalsIgnoreCase("barchart")) {
            model.addAttribute("pageTitle", "Barchart | Expense Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle", "Pie Chart of Expense Budget for year " + expenseBudget.getFiscalYear());
            return "expense-barchart";
        }
        else if(chart.equalsIgnoreCase("piechart")){
            model.addAttribute("pageTitle","Pie Chart | Expense Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Pie Chart of Expense Budget for year "+ expenseBudget.getFiscalYear());
            return "expense-piechart";
        }
        else if(chart.equalsIgnoreCase("stackchart")){
            model.addAttribute("pageTitle","Stack Chart | Expense Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Stack Chart of Expense Budget for year "+ expenseBudget.getFiscalYear());
            return "expense-stackchart";
        }
        else{
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value="/visualize/expense/{year}/{chart}")
    public String visualizeExpenseByYear(Principal principal, Model model,@PathVariable("year") String year, @PathVariable("chart") String chart){
        ExpenseBudget expenseBudget = budgetService.getExpenseBudgetByYear(year,userService.getCurrentUserId(principal));
        model.addAttribute("budget",expenseBudget);
        model.addAttribute("totalBudget",budgetService.getTotalExpenseBudget(expenseBudget));
        if(chart.equalsIgnoreCase("barchart")) {
            model.addAttribute("pageTitle", "Barchart | Expense Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle", "Pie Chart of Expense Budget for year " + expenseBudget.getFiscalYear());
            return "expense-barchart";
        }
        else if(chart.equalsIgnoreCase("piechart")){
            model.addAttribute("pageTitle","Pie Chart | Expense Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Pie Chart of Expense Budget for year "+ expenseBudget.getFiscalYear());
            return "expense-piechart";
        }
        else if(chart.equalsIgnoreCase("stackchart")){
            model.addAttribute("pageTitle","Stack Chart | Expense Budget | Visualization | Budget Visualization & Analysis Tool");
            model.addAttribute("sectionTitle","Stack Chart of Expense Budget for year "+ expenseBudget.getFiscalYear());
            return "expense-stackchart";
        }
        else{
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

    }


}
