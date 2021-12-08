package com.battleship.backend.controllers;
import com.battleship.backend.BoardRepository;
import com.battleship.backend.exceptions.InvalidHitException;
import com.battleship.backend.exceptions.InvalidShipPlacementException;
import com.battleship.backend.models.*;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO: maybe rename to GameController????
@Controller
public class GameController {
    BoardRepository boardRepository;
    PlaceShipsValidator placeShipsValidator;
    HitRequestValidator hitRequestValidator;


    //TODO: refactor so BC takes in Game as an arg.
    public GameController(BoardRepository boardRepository, PlaceShipsValidator placeShipsValidator, HitRequestValidator hitRequestValidator){
        this.boardRepository = boardRepository;

        this.placeShipsValidator = placeShipsValidator;

        this.hitRequestValidator = hitRequestValidator;
    }

    @GetMapping("/boards")
    public @ResponseBody
    Boardable[] getBoards() {

        return boardRepository.getBoards();
    }

    // TODO: refactor for Game;
    // TODO: validate game start???
    @PatchMapping("/board/place")
    public @ResponseBody
    Boardable placeShips(@RequestBody Request placeRequest) throws Exception{
        Boardable playerBoard = boardRepository.getPlayerBoard();
        if(placeShipsValidator.isValid(playerBoard, placeRequest)){
            playerBoard.addShip(placeRequest.getShip(), placeRequest.getRow(), placeRequest.getCol());
            return playerBoard;
        }else {
            throw new InvalidShipPlacementException();
        }
    }


    // TODO: refactor for Game;
    // TODO: validate game start???
    @PatchMapping("/board/hit")
    public @ResponseBody
    Boardable hitShip(@RequestBody Request hitRequest) throws Exception{
        Boardable computerBoard = boardRepository.getComputerBoard();
        if(hitRequestValidator.isValid(computerBoard, hitRequest)){
            computerBoard.hitSection(hitRequest.getRow(), hitRequest.getCol());
            return computerBoard;
        }else {
            throw new InvalidHitException();
        }
    }

}
