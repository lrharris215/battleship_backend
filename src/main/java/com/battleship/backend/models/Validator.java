package com.battleship.backend.models;

public interface Validator {
    boolean isValid(Board board, Ship ship, int row, int col);
}
