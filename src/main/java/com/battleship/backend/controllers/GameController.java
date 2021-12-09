package com.battleship.backend.controllers;
import com.battleship.backend.BoardRepository;
import com.battleship.backend.exceptions.InvalidHitException;
import com.battleship.backend.exceptions.InvalidShipPlacementException;
import com.battleship.backend.models.*;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO: maybe rename to GameController????
@Controller
public class GameController {
    BoardRepository boardRepository;
    PlaceShipsValidator placeShipsValidator;
    HitRequestValidator hitRequestValidator;
    Ship[] shipList;
    Game game;


    //TODO: refactor so BC takes in Game as an arg.
    public GameController(BoardRepository boardRepository, PlaceShipsValidator placeShipsValidator, HitRequestValidator hitRequestValidator){
        Ship carrier = new Ship("Carrier", 5);
        Ship battleShip = new Ship("BattleShip", 4);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship submarine = new Ship("Submarine", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        shipList = new Ship[] { carrier, battleShip, cruiser, submarine, destroyer};

        game = new Game(boardRepository, new Validator[] { placeShipsValidator, hitRequestValidator}, shipList);

        this.placeShipsValidator = placeShipsValidator;
        this.hitRequestValidator = hitRequestValidator;
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
        return "yay game has started";
    }

}
