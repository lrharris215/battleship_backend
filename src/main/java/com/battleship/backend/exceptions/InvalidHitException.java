package com.battleship.backend.exceptions;

public class InvalidHitException extends RuntimeException{
    public InvalidHitException() {
        super("That is not a valid target! You may only fire within the boundaries of the board, and cannot hit the same spot twice!");
    }
}
