package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Game {
    BoardRepository boardRepository;
    ComputerPlayer computerPlayer;
   final ArrayList<Ship> shipList;
    Validator placeShipsValidator;
    Validator hitRequestValidator;
    boolean isGameStarted;
    ArrayList<Ship> playerShipList;

    public Game(BoardRepository boardRepository, Validator placeShipsValidator, Validator hitRequestValidator, Ship[] shipList){
        this.boardRepository = boardRepository;
        this.shipList = new ArrayList<Ship>(Arrays.stream(shipList).toList());
        this.placeShipsValidator = placeShipsValidator;
        this.hitRequestValidator = hitRequestValidator;
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
    }

    public void fire(Boardable board, Request hitRequest){
        board.hitSection(hitRequest.getRow(), hitRequest.getCol());
    }

    public boolean isPlayerReadyToStart(){

        return getPlayerBoard().isPermutationOfShipList(shipList) && playerShipList.isEmpty();
    }

    public void removePlayerShip(Ship shipToRemove){
        Ship deletedShip = null;
        for(Ship ship : shipList){
            if(ship.equals(shipToRemove)){
                deletedShip = ship;
            }
        }
        playerShipList.remove(deletedShip);
    }

    public void takeComputerTurn(){
        Request hitRequest = computerPlayer.generateValidHitRequest();
        fire(getPlayerBoard(), hitRequest);
    }

    private void setUpComputerBoard() {
        Boardable computerBoard = boardRepository.getComputerBoard();
        for (Ship ship : computerPlayer.getShipList()) {
            Request placeRequest = computerPlayer.generateValidPlaceRequest(ship);
            placeShip(computerBoard, placeRequest);
        }
    }


    //Getters

    public ArrayList<Ship> getShipList(){
        return shipList;
    }

    public ArrayList<Ship> getPlayerShipList(){
        return playerShipList;
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
