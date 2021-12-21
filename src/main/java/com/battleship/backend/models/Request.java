package com.battleship.backend.models;

public class Request {
    Ship ship;
    int row;
    int col;

    public Request(){}

    public Request(Ship ship, int row, int col){
        this.ship = ship;
        this.row = row;
        this.col = col;
    }

    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

    public Ship getShip(){
        return ship;
    }

}
