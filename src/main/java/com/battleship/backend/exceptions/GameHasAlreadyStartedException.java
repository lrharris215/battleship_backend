package com.battleship.backend.exceptions;

public class GameHasAlreadyStartedException extends RuntimeException{
    public GameHasAlreadyStartedException() {
        super("The game has already started! You cannot move your ships!");
    }
}
