package com.battleship.backend.controllers;
import com.battleship.backend.exceptions.GameHasAlreadyStartedException;
import com.battleship.backend.exceptions.GameNotReadyToStartException;
import com.battleship.backend.exceptions.InvalidHitException;
import com.battleship.backend.exceptions.InvalidShipPlacementException;
import com.battleship.backend.models.*;
import com.battleship.backend.validators.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class GameController {
    Validator placeShipsValidator;
    Validator hitRequestValidator;
    Game game;


    public GameController(Game game){
        this.game = game;
        this.placeShipsValidator = game.getPlaceShipsValidator();
        this.hitRequestValidator = game.getHitRequestValidator();

    }

    @GetMapping("/boards")
    public @ResponseBody
    Boardable[] getBoards() {
        return game.getBoards();
    }

    @PatchMapping("/board/place")
    public @ResponseBody
    Boardable placeShip(@RequestBody Request placeRequest) throws Exception{
        Boardable playerBoard = game.getPlayerBoard();
        if(game.getIsGameStarted()){
            throw new GameHasAlreadyStartedException();
        }else if(placeShipsValidator.isValid(playerBoard, placeRequest)){
            if(playerBoard.hasShip(placeRequest.getShip())){
                playerBoard.removeShip(placeRequest.getShip());
            }
            game.placeShip(playerBoard, placeRequest);
            game.removePlayerShip(placeRequest.getShip());
            return playerBoard;
        }else {
            throw new InvalidShipPlacementException();
        }
    }

    // change to return message instead of board.
    @PatchMapping("/board/hit")
    public @ResponseBody
    String hitShip(@RequestBody Request hitRequest) throws Exception{
        Boardable computerBoard = game.getComputerBoard();
        if(!game.getIsGameStarted()){
            throw new GameNotReadyToStartException();
        }else if(hitRequestValidator.isValid(computerBoard, hitRequest)){
            game.fire(computerBoard, hitRequest);
            return game.shipHitResult(computerBoard, hitRequest);
        }else {
            throw new InvalidHitException();
        }
    }

    @PostMapping("/board/computer_turn")
    public @ResponseBody
    String computerTurn() throws Exception {
        return game.takeComputerTurn();
    }

    @PostMapping("/game/start")
    public @ResponseBody
    String startGame() throws Exception {
        if(game.getIsGameStarted()){
            throw new GameHasAlreadyStartedException();
        }
        if(game.isPlayerReadyToStart()){
            game.start();
            return "Yay game has started";
        }else{
            throw new GameNotReadyToStartException();
        }
    }

    @GetMapping("/ships")
    public @ResponseBody
    ArrayList<Ship> getShipList(){
        return game.getPlayerShipList();
    }

}
