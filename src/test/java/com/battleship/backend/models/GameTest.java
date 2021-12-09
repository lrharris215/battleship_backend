package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.TestClasses;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
    BoardRepository boardRepository;
    Validator placeShipsValidator;
    Validator hitRequestValidator;
    Ship[] shipList;
    Ship testShip1;
    Ship testShip2;

    @BeforeEach
    void initNewGame(){
        boardRepository = new BoardRepository();
        placeShipsValidator = new PlaceShipsValidator();
        hitRequestValidator = new HitRequestValidator();
        testShip1 =  new Ship("testShip1", 3);
        testShip2 = new Ship("testShip2", 2);
        shipList = new Ship[] { testShip1, testShip2};
        game = new Game(boardRepository, new Validator[]{placeShipsValidator, hitRequestValidator}, shipList);
    }

    @Test
    void testStartFillsInComputerBoard() {
        game.start();
        assertTrue(game.getComputerBoard().hasShips());
    }

    @Test
    void testStartStartsTheGame(){
        game.start();
        assertTrue(game.getIsGameStarted());
    }

    @Test
    void testPlaceShipPutsAShipOnTheBoard() {
        Boardable testBoard = boardRepository.getComputerBoard();
        Request testRequest = new Request(testShip2, 0, 0);
        game.placeShip(testBoard, testRequest);
        assertTrue(testBoard.getShipList().contains(testRequest.getShip()));
    }

    @Test
    void testFireUpdatesHitStatusOfSectionOnBoard() {
        Boardable testBoard = boardRepository.getPlayerBoard();
        Request testRequest = new Request(null, 0, 0);
        game.fire(testBoard, testRequest);

        assertTrue(testBoard.getSection(0, 0).getIsHit());

    }

    @Test
    void testIsPlayerReadyToStartReturnsTrueIfAllShipsAreOnPlayerBoard(){
        Boardable playerBoard = game.getPlayerBoard();
        Request placeRequest1 = new Request(testShip1, 0,0);
        Request placeRequest2 = new Request(testShip2, 1, 0);
        game.placeShip(playerBoard, placeRequest1);
        game.placeShip(playerBoard, placeRequest2);

        assertTrue(game.isPlayerReadyToStart());
    }

    @Test
    void testIsPlayerReadyToStartReturnsFalseIfNotAllShipsAreOnPlayerBoard(){
        Boardable playerBoard = game.getPlayerBoard();
        Request placeRequest1 = new Request(testShip1, 0,0);
        game.placeShip(playerBoard, placeRequest1);

        assertFalse(game.isPlayerReadyToStart());
    }

    @Test
    void getShipList() {
        assertEquals(game.getShipList(), game.shipList);
    }
}