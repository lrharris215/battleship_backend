package com.battleship.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GameHasAlreadyStartedAdvice {
    @ResponseBody
    @ExceptionHandler(GameHasAlreadyStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String gameAlreadyStartedHandler(GameHasAlreadyStartedException exception){
        return exception.getMessage();
    }

}
