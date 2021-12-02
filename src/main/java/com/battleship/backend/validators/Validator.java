package com.battleship.backend.validators;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Ship;

public interface Validator {
    boolean isValid(Boardable board, Ship ship, int row, int col);
}
