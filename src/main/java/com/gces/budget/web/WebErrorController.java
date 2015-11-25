//package com.gces.budget.web;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.web.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by minamrosh on 11/22/15.
// */
//@Controller
//public class WebErrorController implements ErrorController{
//    private final Logger log = LoggerFactory.getLogger(WebErrorController.class);
//
//    @RequestMapping("/error")
//    public String displayError(HttpServletRequest request, Exception ex,Model model){
//        log.info(ex.getLocalizedMessage());
//        log.info("status "+ request.getAttribute("javax.servlet.error.status_code"));
//        model.addAttribute("status",ex.getMessage());
//
//        return "error";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//}
