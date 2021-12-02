package com.battleship.backend;

import com.battleship.backend.models.Board;
import com.battleship.backend.models.Boardable;
import org.springframework.stereotype.Component;

@Component
public class BoardRepository {

    private final Board[] boards;
    private final Board playerBoard;
    private final Board computerBoard;

    public BoardRepository() {
        playerBoard = new Board("Player Board");
        computerBoard = new Board("Computer Board");
        boards = new Board[] { playerBoard, computerBoard };
    }

    public Boardable[] getBoards() {
        return boards;
    }

    public Boardable getPlayerBoard() {
        return playerBoard;
    }

    public Boardable getComputerBoard() {
        return computerBoard;
    }
}
