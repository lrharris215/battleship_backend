package com.battleship.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GameNotReadyToStartAdvice {
    @ResponseBody
    @ExceptionHandler(GameNotReadyToStartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String gameNotReadyHandler(GameNotReadyToStartException exception){
        return exception.getMessage();
    }

}
