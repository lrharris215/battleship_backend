package com.battleship.backend.validators;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Request;

public class HitRequestValidator implements Validator{
    public boolean isValid(Boardable board, Request request) {
        return false;
    }
}
