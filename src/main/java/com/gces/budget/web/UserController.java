package com.gces.budget.web;

import com.gces.budget.domain.dto.BudgetSheetDTO;
import com.gces.budget.domain.dto.UserDTO;
import com.gces.budget.domain.entity.ExpenseBudget;
import com.gces.budget.domain.entity.IncomeBudget;
import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import com.gces.budget.service.UserService;
import com.gces.budget.service.budget.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

/**
 * User Controller
 * handles related to user management
 * Created by minamrosh on 11/20/15.
 */

@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    private UserRepository userRepository;

    private BudgetService budgetService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBudgetService(BudgetService budgetService){
        this.budgetService = budgetService;
    }

    @RequestMapping(value = "/")
    public String home(){
        return "index";
    }

    @RequestMapping(value = "/user/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/user/signup", method = RequestMethod.GET)
    public String signup(Model model){
        model.addAttribute("user",new UserDTO());
        return "signup";
    }

    @RequestMapping(value = "/user/signup",method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") @Valid UserDTO user, BindingResult result){

        if(result.hasErrors()){
            log.info("\n form Error" + result.toString());
//            model.addAttribute("user",user);
            return "signup";
        }

        log.info("Saving User Detail \n"+user);

        User registeredUser = userService.registerNewUser(user);
        log.info("Registered User : \n" +registeredUser);

//        model.addAttribute("user",user);
        return "success";

//        User regUser = userService.registerNewUser(user);
//
//
//        return "redirect:/user/success{regUser}";

    }


    @RequestMapping(value = "/user/success/", method = RequestMethod.GET)
    public String success(@PathVariable("regUser") User regUser, Model model){
        RedirectAttributes redirectAttributes;

        model.addAttribute("regUser", regUser);
        return "success";
    }

    @RequestMapping(value ="/test", method = RequestMethod.GET)
    public ModelAndView testRedirect(RedirectAttributes redAttr){
        redAttr.addAttribute("redirectParam", "param");
        log.info("redirect param {}", redAttr);
        return new ModelAndView("redirect:/test/success/{redirectParam}");
    }

    @RequestMapping(value = "/test/success/{redirectParam}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Url Not Found")
//    @ExceptionHandler(value = )
    public String testSuccess(Model model, @ModelAttribute("redirectParam") String param
                              )
    {

        log.info("Log Param from test/success  :" + param);
        if (param == null){
            log.info("Null parm {}", param);
        }
        log.info("Path Param : "+ param);
        model.addAttribute("success", param);
        model.addAttribute("pathParam", param);
        return "success";
    }

    @RequestMapping(value = "/user/account")
    public String account(Authentication authentication,Model model){

        UserDetails logedInUser = (UserDetails) authentication.getPrincipal();
        log.info("user details, username : " +logedInUser.getUsername());
        User user = userRepository.findOneByUsername(logedInUser.getUsername());
        log.info("user details of loged in user " + user);
        model.addAttribute("user",user);

//        model.addAttribute("userDetail",authentication.getDetails());
        return "account";
    }

    @RequestMapping(value = "/user/account",method = RequestMethod.POST)
    public String saveAccount(@Valid User user){
        User upUser = userRepository.save(user);
        if(upUser == null){
            throw new IllegalArgumentException("exception while storing information");
        }

        return "user/account";
    }

    @RequestMapping(value = "/user/upload-income-budget")
    public String uploadIncomeBudgetForm(Model model, BudgetSheetDTO budget){
        model.addAttribute("budget",budget);
        return "upload-income-budget";
    }

    @RequestMapping(value = "/user/upload-income-budget", method = RequestMethod.POST)
    public String saveIncomeBudget(@ModelAttribute("budget") @Valid BudgetSheetDTO budget, BindingResult result,
                                  @RequestParam("budgetSheet") MultipartFile budgetSheet,
                                   Principal principal){

        if(result.hasErrors()){
            return "upload-income-budget";
        }

        if(budgetSheet.isEmpty()){
            log.info("budget sheet empty");
            return "upload-income-budget";
        }

        IncomeBudget incomeBudget = budgetService.saveIncomeBudget(budgetSheet, budget,
                userRepository.findOneByUsername(principal.getName()).getId());

        if(incomeBudget != null){
            log.info("Income Budget \n" + incomeBudget);
            return "redirect:/";
        }
        else{
            log.info("data not saved");
            throw new NullPointerException("Unable to Save file");
        }

    }

    @RequestMapping(value = "/user/upload-expense-budget", method = RequestMethod.GET)
    public String uploadExpenseBudgetForm(BudgetSheetDTO budgetSheetDTO, Model model){
        model.addAttribute("budget",budgetSheetDTO);
        return "upload-expense-budget";

    }

    @RequestMapping(value = "/user/upload-expense-budget", method = RequestMethod.POST)
    public String saveExpenseBudget(@ModelAttribute("budget") @Valid BudgetSheetDTO budget, BindingResult result,
                                   @RequestParam("budgetSheet") MultipartFile budgetSheet,
                                   Principal principal){

        if(result.hasErrors()){
            return "upload-expense-budget";
        }

        if(budgetSheet.isEmpty()){
            log.info("budget sheet empty");
            return "upload-expense-budget";
        }

        ExpenseBudget expenseBudget = budgetService.saveExpenseBudget(budgetSheet, budget,
                userRepository.findOneByUsername(principal.getName()).getId());

        if(expenseBudget != null){
            log.info("Expense Budget \n" + expenseBudget);
            return "redirect:/";
        }
        else{
            log.info("data not saved");
            throw new NullPointerException("Unable to Save file");
        }

    }

}
