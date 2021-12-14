package com.battleship.backend.exceptions;

public class GameNotReadyToStartException extends RuntimeException{
    public GameNotReadyToStartException() {
        super("The game cannot be started: Not all ships have been placed on the Board!");
    }
}
