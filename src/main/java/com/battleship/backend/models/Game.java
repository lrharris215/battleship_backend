package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.exceptions.GameNotReadyToStartException;
import com.battleship.backend.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Game {
    BoardRepository boardRepository;
    ComputerPlayer computerPlayer;
   final ArrayList<Ship> shipList;
    Validator placeShipsValidator;
    Validator hitRequestValidator;
    boolean isGameStarted;
    ArrayList<Ship> playerShipList;

    public Game(BoardRepository boardRepository, Validator[] validators, Ship[] shipList){
        this.boardRepository = boardRepository;
        this.shipList = new ArrayList<Ship>(Arrays.stream(shipList).toList());
        placeShipsValidator = validators[0];
        hitRequestValidator = validators[1];
        computerPlayer = new ComputerPlayer(boardRepository, shipList, placeShipsValidator, hitRequestValidator);
        isGameStarted = false;
        playerShipList = new ArrayList<Ship>(Arrays.stream(shipList).toList());
    }

    public void start(){
        if(!isGameStarted){
            setUpComputerBoard();
            isGameStarted = true;
        }
    }

    public void placeShip(Boardable board, Request placeRequest){
        board.addShip(placeRequest.getShip(), placeRequest.getRow(), placeRequest.getCol());
        playerShipList.remove(placeRequest.getShip());
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
        return getPlayerBoard().getShipList().equals(shipList) && playerShipList.isEmpty();
    }


    //Getters

    public ArrayList<Ship> getShipList(){
        return shipList;
    }

    public Boardable getComputerBoard(){
        return boardRepository.getComputerBoard();
    }

    public Boardable getPlayerBoard(){
        return boardRepository.getPlayerBoard();
    }

    public Boardable[] getBoards(){
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
