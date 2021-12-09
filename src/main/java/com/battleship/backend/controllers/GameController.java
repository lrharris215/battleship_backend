package com.battleship.backend.controllers;
import com.battleship.backend.BoardRepository;
import com.battleship.backend.exceptions.GameNotReadyToStartException;
import com.battleship.backend.exceptions.InvalidHitException;
import com.battleship.backend.exceptions.InvalidShipPlacementException;
import com.battleship.backend.models.*;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: maybe rename to GameController????
@Controller
public class GameController {
    Validator placeShipsValidator;
    Validator hitRequestValidator;
    Game game;


    //TODO: refactor so BC takes in Game as an arg.
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

    // TODO: refactor for Game;
    // TODO: validate game start???
    @PatchMapping("/board/place")
    public @ResponseBody
    Boardable placeShips(@RequestBody Request placeRequest) throws Exception{
        Boardable playerBoard = game.getPlayerBoard();
        if(placeShipsValidator.isValid(playerBoard, placeRequest)){
            game.placeShip(playerBoard, placeRequest);
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
        Boardable computerBoard = game.getComputerBoard();
        if(hitRequestValidator.isValid(computerBoard, hitRequest)){
            game.fire(computerBoard, hitRequest);
            return computerBoard;
        }else {
            throw new InvalidHitException();
        }
    }

    // TODO: fill in endpoint after refactor.

    @PostMapping("/game/start")
    public @ResponseBody
    String startGame() throws Exception {
        if(game.isPlayerReadyToStart()){
            game.start();
            return "Yay game has started";
        }else{
            throw new GameNotReadyToStartException();
        }
    }

    // TODO: Fix so that it returns the human player's shiplist.
    @GetMapping("/ships")
    public @ResponseBody
    ArrayList<Ship> getShipList(){
        return game.getShipList();
    }

}
