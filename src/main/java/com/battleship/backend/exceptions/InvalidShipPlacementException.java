package com.battleship.backend.exceptions;

public class InvalidShipPlacementException extends RuntimeException{
    public InvalidShipPlacementException() {
        super("That is not a valid placement! Ships must be placed within the boundaries of the board, and not overlap any other ships!");
    }
}
