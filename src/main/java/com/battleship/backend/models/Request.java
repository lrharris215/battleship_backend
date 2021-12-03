package com.battleship.backend.models;

public abstract class Request {
    public int getRow(){
        return 0;
    };
    public int getCol(){
        return 0;
    };

    public abstract Ship getShip();

}
