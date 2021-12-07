package com.battleship.backend.validators;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Request;
import org.springframework.stereotype.Component;

@Component
public class HitRequestValidator implements Validator{
    public HitRequestValidator(){}
    public boolean isValid(Boardable board, Request request) {
        int row = request.getRow();
        int col = request.getCol();

        return isPositionValid(board, row, col) && isPositionNotYetHit(board, row, col);
    }

    private boolean isPositionValid(Boardable board, int row, int col){
        if(row < 0 || row >= board.getSize()){

            return false;
        }else return col >= 0 && col < board.getSize();
    }

    private boolean isPositionNotYetHit(Boardable board, int row, int col){
        return !board.getSection(row, col).getIsHit();
    }
}
