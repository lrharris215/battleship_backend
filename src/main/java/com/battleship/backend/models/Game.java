package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Game {

    final ArrayList<Ship> shipList;
    BoardRepository boardRepository;
    ComputerPlayer computerPlayer;
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

    public String takeComputerTurn(){
        Request hitRequest = computerPlayer.generateValidHitRequest();
        fire(getPlayerBoard(), hitRequest);
        return shipHitResult(getPlayerBoard(), hitRequest);
    }

    public boolean isGameOver(){
        return getPlayerBoard().isEveryShipSunk() || getComputerBoard().isEveryShipSunk();
    }

    public String getWinner(){
        if(getPlayerBoard().isEveryShipSunk()){
            return "Computer Player is the Winner!";
        }else if(getComputerBoard().isEveryShipSunk()){
            return "Human Player is the Winner!";
        }else return "Game is not over";
    }

    //returns result of a hit request

    public String shipHitResult(Boardable board, Request hitRequest){
        String hitResult = "Missed!";
        Sectionable hitSection = board.getSection(hitRequest.getRow(), hitRequest.getCol());
         if(hitSection.getIsShip()){
            hitResult = "The " + board.getName() + "'s " + hitSection.getShipName() + " has been hit!";
            if(board.getShip(hitSection.getShipName()).checkIsSunk()){
                hitResult +=  " The " + hitSection.getShipName() +" has been sunk!";
            }
         }
        if(this.isGameOver()){
            hitResult = "The Game is Over! " + this.getWinner();
        }
        return hitResult;
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

    public ComputerPlayer getComputerPlayer() { return computerPlayer;}

    public void setComputerPlayer(ComputerPlayer computerPlayer) { this.computerPlayer = computerPlayer;}
}
