package com.battleship.backend.validators;

import com.battleship.backend.models.Board;
import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Ship;


public class PlaceShipsValidator implements Validator {

    public boolean isValid(Boardable board, Ship ship, int row, int col) {
        //check row within bounds, check col within bounds
        //check ship does not exceed bounds
        //Check space is not occupied

        return false;
    }

    private boolean isPositionValid(Board board, int row, int col){
        if(row < 0 || row > board.getSize()){
            return false;
        }else return col >= 0 && col <= board.getSize();
    }

    private boolean isPositionEmpty(Board board, int row, int col){
        return !board.getSection(row, col).getIsShip();
    }
}
