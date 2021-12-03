package com.battleship.backend.models;

public class HitRequest extends Request{
    int row;
    int col;
    public HitRequest(int row, int col){
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Ship getShip() {
        return null;
    }

}
