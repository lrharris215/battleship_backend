package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;

public class Game {
    BoardRepository boardRepository;
    ComputerPlayer computerPlayer;
    Ship[] shipList;
    Validator placeShipsValidator;
    Validator hitRequestValidator;
    boolean isGameStarted;

    public Game(BoardRepository boardRepository, Validator[] validators, Ship[] shipList){
        this.boardRepository = boardRepository;
        this.shipList = shipList;
//        setUpShipList();
        placeShipsValidator = validators[0];
        hitRequestValidator = validators[1];
        computerPlayer = new ComputerPlayer(boardRepository, shipList, placeShipsValidator, hitRequestValidator);
        isGameStarted = false;
    }

    public void start(){
        setUpComputerBoard();
        isGameStarted = true;
    }

    public void placeShip(Boardable board, Request placeRequest){
        board.addShip(placeRequest.getShip(), placeRequest.getRow(), placeRequest.getCol());
    }

    public void fire(Boardable board, Request hitRequest){
        board.hitSection(hitRequest.getRow(), hitRequest.getCol());
    }

    // will move to controller
    private void setUpShipList(){
        Ship carrier = new Ship("Carrier", 5);
        Ship battleShip = new Ship("BattleShip", 4);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship submarine = new Ship("Submarine", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        shipList = new Ship[] { carrier, battleShip, cruiser, submarine, destroyer};
    }

    private void setUpComputerBoard(){
        Boardable computerBoard = boardRepository.getComputerBoard();
        for(Ship ship : computerPlayer.getShipList()){
            Request placeRequest = computerPlayer.generateValidPlaceRequest(ship);
            placeShip(computerBoard, placeRequest);
            computerPlayer.removeShip(ship);
        }

    }

    //Getters

    public Ship[] getShipList(){
        return shipList;
    }

    public Boardable getComputerBoard(){
        return boardRepository.getComputerBoard();
    }

    public Boardable getPlayerBoard(){
       return boardRepository.getPlayerBoard();
    }

    public boolean getIsGameStarted(){
        return isGameStarted;
    }

}
