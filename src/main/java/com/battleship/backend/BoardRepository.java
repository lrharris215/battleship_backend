package com.battleship.backend;

import com.battleship.backend.models.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardRepository {
    Board[] boards;
    Board playerBoard;
    Board computerBoard;

    public BoardRepository(){
        playerBoard = new Board("Player Board");
        computerBoard = new Board("Computer Board");
        boards = new Board[] { playerBoard, computerBoard};
    }
    public Board[] getBoards(){
        return boards;
    }

    public Board getPlayerBoard(){
        return playerBoard;
    }

    public Board getComputerBoard() {
        return computerBoard;
    }
}
