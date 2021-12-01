package com.battleship.backend.controllers;
import com.battleship.backend.BoardRepository;
import com.battleship.backend.models.Board;
import com.battleship.backend.models.PlaceRequest;
import com.battleship.backend.models.Ship;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
    BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @GetMapping("/boards")
    public @ResponseBody
    Board[] getBoards() {

        return boardRepository.getBoards();
    }

    @PatchMapping("/board/place")
    public @ResponseBody
    Board placeShips(@RequestBody PlaceRequest placeRequest){
        Board playerBoard = boardRepository.getPlayerBoard();
        playerBoard.addShip(placeRequest.getShip(), placeRequest.getRow(), placeRequest.getCol());
        return playerBoard;
    }

}
