package com.battleship.backend.validators;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Request;

public interface Validator {
    boolean isValid(Boardable board, Request request);
}
