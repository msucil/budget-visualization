package com.gces.budget.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by minamrosh on 1/8/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SheetNotFoundException extends RuntimeException {

    public SheetNotFoundException(String message){
        super(message);
    }
}
