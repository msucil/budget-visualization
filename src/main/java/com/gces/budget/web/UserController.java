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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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
    public String home(Model model){
        model.addAttribute("pageTitle","Home | Budget Visualization and Analysis Tool");
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
        User user = userService.getUserByUsername(logedInUser.getUsername());
        log.info("user details of loged in user " + user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        model.addAttribute("user",user);
        model.addAttribute("userDTO",userDTO);
        model.addAttribute("pageTitle","User Account | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","User Account");

//        model.addAttribute("userDetail",authentication.getDetails());
        return "user-account";
    }

    @RequestMapping(value = "/user/account",method = RequestMethod.POST)
    public String saveAccount(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult result,Model model){

        model.addAttribute("pageTitle","User Account | Budget Visualization & Analysis Tool");
        model.addAttribute("sectionTitle","User Account");

        if(result.hasErrors()){
            log.error("\n form Error" + result.toString());
            return "user-account";
        }

        log.info("userinfo " + userDTO.toString());

        userService.updateUserInfo(userDTO);
        return "redirect:/user/account";

    }

    @RequestMapping(value = "/user/upload-income-budget")
    public String uploadIncomeBudgetForm(Model model, BudgetSheetDTO budget){
        model.addAttribute("budget",budget);
        return "upload-income-budget";
    }

    @RequestMapping(value = "/user/upload-income-budget", method = RequestMethod.POST)
    public String saveIncomeBudget(@ModelAttribute("budget") @Valid BudgetSheetDTO budget, BindingResult result,
                                  @RequestParam("budgetSheet") MultipartFile budgetSheet,
                                   Principal principal) throws IOException {

        if(result.hasErrors()){
            return "upload-income-budget";
        }

        if(budgetSheet.isEmpty()){
            log.info("budget sheet empty");
            result.addError(new FieldError("budget","budgetSheet","Select Income Budget file"));
            return "upload-income-budget";
        }
        try {
            IncomeBudget incomeBudget = budgetService.saveIncomeBudget(budgetSheet, budget,
                    userRepository.findOneByUsername(principal.getName()).getId());

            log.info("Income Budget \n" + incomeBudget);
        }
        catch (IOException ex){
            log.info(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
        catch (NullPointerException ex){
            log.info("data not saved");
            throw new NullPointerException("Unable to Save file");
        }
        catch (DuplicateKeyException dup){
            result.addError(new FieldError("budget","fiscalYear","Fiscal Year Already Exist!"));
            return "upload-income-budget";
        }

        return "redirect:/";

    }

    @RequestMapping(value = "/user/upload-expense-budget", method = RequestMethod.GET)
    public String uploadExpenseBudgetForm(BudgetSheetDTO budgetSheetDTO, Model model){
        model.addAttribute("budget",budgetSheetDTO);
        return "upload-expense-budget";

    }

    @RequestMapping(value = "/user/upload-expense-budget", method = RequestMethod.POST)
    public String saveExpenseBudget(@ModelAttribute("budget") @Valid BudgetSheetDTO budget, BindingResult result,
                                   @RequestParam("budgetSheet") MultipartFile budgetSheet,
                                   Principal principal) throws IOException {

        if(result.hasErrors()){
            return "upload-expense-budget";
        }



        if(budgetSheet.isEmpty()){
            log.info("budget sheet empty");
            result.addError(new FieldError("budget", "budgetSheet", "Select Income Budget file"));
            return "upload-expense-budget";
        }

        try{
            ExpenseBudget expenseBudget = budgetService.saveExpenseBudget(budgetSheet, budget,
                    userRepository.findOneByUsername(principal.getName()).getId());

            log.info("Expense Budget \n" + expenseBudget);

        }
        catch (IOException ex){
            log.info(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
        catch (NullPointerException ex){
            throw new NullPointerException("Expense budge can't be saved now!");
        }
        catch (DuplicateKeyException dup){
            result.addError(new FieldError("budget","fiscalYear","Fiscal Year Already Exist!"));
            return "upload-expense-budget";
        }

        return "redirect:/";

    }

    @RequestMapping(value = "/user/demo",method = RequestMethod.GET)
    public String demoJs(Model model){
        model.addAttribute("jstitle","Hello Fromm Spring Controller");
        return "demojs";
    }

}
