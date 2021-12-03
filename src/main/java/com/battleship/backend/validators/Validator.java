package com.battleship.backend.validators;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.PlaceRequest;
import com.battleship.backend.models.Request;
import com.battleship.backend.models.Ship;

public interface Validator {
    boolean isValid(Boardable board, Request request);
}
