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

    public void placeShip(Request placeRequest){
        computerBoard.addShip(placeRequest.getShip(), placeRequest.getRow(), placeRequest.getCol());
        removeShip(placeRequest.getShip());
    }

    public void placeAllShips(){
        for(Ship ship : shipList){
            Request placeRequest = generateValidPlaceRequest(ship);
            placeShip(placeRequest);
        }
    }

    private void removeShip(Ship shipToRemove){
        Ship[] oldShipList = getShipList();
        Ship[] newShipList = new Ship[oldShipList.length -  1];

        for(int i = 0, j = 0; i < oldShipList.length; i++){
            if(oldShipList[i] != shipToRemove){
                newShipList[j] = oldShipList[i];
                j++;
            }
        }
        setShipList(newShipList);
    }

    public void fire(Request hitRequest){
        playerBoard.hitSection(hitRequest.getRow(), hitRequest.getCol());
    }

    //Getters;

    public Ship[] getShipList() {
        return shipList;
    }

    // Setters
    private void setShipList(Ship[] newShipList){
        shipList = newShipList;
    }
}
