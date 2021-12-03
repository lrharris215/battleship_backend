package com.battleship.backend.models;

public class PlaceRequest extends Request{
    Ship ship;
    int row;
    int col;

    public PlaceRequest(Ship ship, int row, int col){
        this.ship = ship;
        this.row = row;
        this.col = col;
    }
    public Ship getShip(){
        return ship;
    }

    @Override
    public int getRow(){
        return row;
    }

    @Override
    public int getCol(){
        return col;
    }
}
