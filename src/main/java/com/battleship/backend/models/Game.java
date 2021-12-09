package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
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
        // TODO: need to add validation for playerBoard isSetup???
        if(!isGameStarted && isPlayerReadyToStart()){
            setUpComputerBoard();
            isGameStarted = true;
        }
    }

    public void placeShip(Boardable board, Request placeRequest){
        board.addShip(placeRequest.getShip(), placeRequest.getRow(), placeRequest.getCol());
    }

    public void fire(Boardable board, Request hitRequest){
        board.hitSection(hitRequest.getRow(), hitRequest.getCol());
    }

    private void setUpComputerBoard() {
        Boardable computerBoard = boardRepository.getComputerBoard();
        for (Ship ship : computerPlayer.getShipList()) {
            Request placeRequest = computerPlayer.generateValidPlaceRequest(ship);
            placeShip(computerBoard, placeRequest);
            computerPlayer.removeShip(ship);
        }
    }

    public boolean isPlayerReadyToStart(){
        return getPlayerBoard().getShipList().equals(Arrays.stream(shipList).toList());
    }


    //Getters

    public Ship[] getShipList(){
        return shipList;
    }

    public Boardable getComputerBoard(){
        System.out.println("Attempting to actually get real boards here");
        return boardRepository.getComputerBoard();
    }

    public Boardable getPlayerBoard(){
        System.out.println("Attempting to actually get real boards here");
        return boardRepository.getPlayerBoard();
    }

    public Boardable[] getBoards(){
        System.out.println("Attempting to actually get real boards here");
        return boardRepository.getBoards();
    }

    public boolean getIsGameStarted(){
        return isGameStarted;
    }

    public Validator getPlaceShipsValidator(){
        return placeShipsValidator;
    }

    public Validator getHitRequestValidator(){
        return hitRequestValidator;
    }

}
