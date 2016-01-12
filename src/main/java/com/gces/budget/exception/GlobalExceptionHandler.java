package com.gces.budget.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by minamrosh on 1/8/16.
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SheetNotFoundException.class)
    public void sheetNotFoundExceptionHandler(Exception exception){
        log.error("Sheet Not Found Exception",exception.getStackTrace());
        throw new SheetNotFoundException(exception.getMessage());
    }
}
