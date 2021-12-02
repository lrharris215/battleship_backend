package com.battleship.backend.models;

public class PlaceShipsValidator implements Validator{

    public boolean isValid(Board board, Ship ship, int row, int col) {
        //check row within bounds, check col within bounds
        //check ship does not exceed bounds
        //Check space is not occupied

        return false;
    }

    public boolean isPositionValid(Board board, int row, int col){
        if(row < 0 || row > board.getSize()){
            return false;
        }else return col >= 0 && col <= board.getSize();
    }

    public boolean isPositionEmpty(Board board, int row, int col){
        return !board.getSection(row, col).getIsShip();
    }
}
