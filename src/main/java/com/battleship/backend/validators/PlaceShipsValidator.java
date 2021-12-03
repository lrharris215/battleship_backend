package com.battleship.backend.validators;


import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.PlaceRequest;
import com.battleship.backend.models.Ship;

import org.springframework.stereotype.Component;

@Component
public class PlaceShipsValidator implements Validator {

    public PlaceShipsValidator(){}

    public boolean isValid(Boardable board, PlaceRequest placeRequest) {
        //check row within bounds, check col within bounds
        //check ship does not exceed bounds
        //Check space is not occupied
        Ship ship = placeRequest.getShip();
        int row = placeRequest.getRow();
        int col = placeRequest.getCol();

        if(ship == null || !ship.getClass().getName().equals("com.battleship.backend.models.Ship")){
            return false;
        }

        for(int i = 0; i < ship.getWidth(); i++){
            if(!isPositionValid(board, row, col + i) || !isPositionEmpty(board, row, col + i)){

                return false;
            }
        }
        for(int i = 0; i < ship.getHeight(); i++){
            if(!isPositionValid(board,row + i, col) || !isPositionEmpty(board, row + i, col)){
                return false;
            }
        }
        return true;
    }

    private boolean isPositionValid(Boardable board, int row, int col){
        if(row < 0 || row >= board.getSize()){
            return false;
        }else return col >= 0 && col < board.getSize();
    }

    private boolean isPositionEmpty(Boardable board, int row, int col){
        return !board.getSection(row, col).getIsShip();
    }
}
