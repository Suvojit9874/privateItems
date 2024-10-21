package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = ExceptionReasons.USEREXIST)
public class UserAlreadyExistExceptions extends RuntimeException {

    public UserAlreadyExistExceptions(String username){
        super(username.concat(" is already exist"));
    }

}
