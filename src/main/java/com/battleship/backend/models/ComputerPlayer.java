package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;

public class ComputerPlayer {
    BoardRepository boardRepository;
    Ship[] shipList;
    Boardable playerBoard;
    Boardable computerBoard;
    String name;

    public ComputerPlayer(BoardRepository boardRepository, Ship[] shipList){
        this.boardRepository = boardRepository;
        this.shipList = shipList;
        this.playerBoard = boardRepository.getPlayerBoard();
        this.computerBoard = boardRepository.getComputerBoard();
        name = "Computer Player";
    }

    public Request generateHitRequest(){}

    public Request generatePlaceRequest(){}


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
