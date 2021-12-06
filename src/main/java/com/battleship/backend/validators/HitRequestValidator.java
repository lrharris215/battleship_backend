package com.battleship.backend.validators;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Request;
import org.springframework.stereotype.Component;

@Component
public class HitRequestValidator implements Validator{
    public HitRequestValidator(){}
    public boolean isValid(Boardable board, Request request) {
        return false;
    }
}
