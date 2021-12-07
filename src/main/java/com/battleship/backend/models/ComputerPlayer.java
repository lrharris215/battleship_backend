package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.Validator;

import java.util.Random;

public class ComputerPlayer {
    BoardRepository boardRepository;
    Ship[] shipList;
    Boardable playerBoard;
    Boardable computerBoard;
    String name;
    Validator hitRequestValidator;
    Validator placeShipsValidator;
    RequestFactory requestFactory;
    Random rand;

    public ComputerPlayer(BoardRepository boardRepository, Ship[] shipList, Validator placeShipsValidator, Validator hitRequestValidator){
        this.boardRepository = boardRepository;
        this.shipList = shipList;
        this.playerBoard = boardRepository.getPlayerBoard();
        this.computerBoard = boardRepository.getComputerBoard();
        this.hitRequestValidator = hitRequestValidator;
        this.placeShipsValidator = placeShipsValidator;

        rand = new Random();
        requestFactory = new RequestFactory();
        name = "Computer Player";
    }

    public Request generateValidHitRequest(){
        int randRow = rand.nextInt(playerBoard.getSize());
        int randCol = rand.nextInt(playerBoard.getSize());

        Request randHitRequest = requestFactory.generateHitRequest(randRow, randCol);

        while(!hitRequestValidator.isValid(playerBoard, randHitRequest)){
            randRow = rand.nextInt(playerBoard.getSize());
            randCol = rand.nextInt(playerBoard.getSize());

            randHitRequest = requestFactory.generateHitRequest(randRow, randCol);
        }
        return randHitRequest;
    }

    public Request generateValidPlaceRequest(Ship ship){
        int randRow = rand.nextInt(computerBoard.getSize());
        int randCol = rand.nextInt(computerBoard.getSize());

        Request randPlaceRequest = requestFactory.generatePlaceRequest(ship, randRow, randCol);

        while(!placeShipsValidator.isValid(computerBoard, randPlaceRequest)){
            randRow = rand.nextInt(computerBoard.getSize());
            randCol = rand.nextInt(computerBoard.getSize());

            randPlaceRequest = requestFactory.generatePlaceRequest(ship, randRow, randCol);
        }

        return randPlaceRequest;
    }

    public void placeShip(Request placeRequest){}

    public void fire(Request hitRequest){}


    //Getters;

    public BoardRepository getBoardRepository() {
        return boardRepository;
    }

    public Ship[] getShipList() {
        return shipList;
    }

    public Boardable getPlayerBoard() {
        return playerBoard;
    }

    public Boardable getComputerBoard() {
        return computerBoard;
    }

    public String getName() {
        return name;
    }
}
