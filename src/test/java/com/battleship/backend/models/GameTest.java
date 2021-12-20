package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.TestClasses;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
        game = new Game(boardRepository, placeShipsValidator, hitRequestValidator, shipList);
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
        //I remove these in the controller
        game.removePlayerShip(placeRequest1.getShip());
        game.removePlayerShip(placeRequest2.getShip());

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
    void testRemovePlayerShipRemovesShipFromPlayerShipList(){
        game.removePlayerShip(testShip1);

        assertFalse(game.playerShipList.contains(testShip1));
    }

    @Test
    void testRemovePlayerShipDoesNotRemoveShipFromGameShipList(){
        game.removePlayerShip(testShip1);

        assertTrue(game.shipList.contains(testShip1));
    }

    @Test
    void testTakeComputerTurnFiresOnPlayerBoard(){
        game.takeComputerTurn();

        assertTrue(hasPlayerBoardBeenFiredUpon(game.getPlayerBoard()));
    }

    @Test
    void testIsGameOverReturnsTrueIfTheComputerHasWon(){
        Ship testShip3 = new Ship("testShip3", 2);
        Boardable playerBoard = game.getPlayerBoard();
        Boardable computerBoard = game.getComputerBoard();
        playerBoard.addShip(testShip1, 0,0);
        playerBoard.addShip(testShip2, 1,0);
        computerBoard.addShip(testShip3, 0,0);

        sinkShip(testShip1);
        sinkShip(testShip2);

        assertTrue(game.isGameOver());
    }

    @Test
    void testIsGameOverReturnsTrueIfHumanHasWon(){
        Ship testShip3 = new Ship("testShip3", 2);
        Boardable computerBoard = game.getComputerBoard();
        Boardable playerBoard = game.getPlayerBoard();
        computerBoard.addShip(testShip1, 0,0);
        computerBoard.addShip(testShip2, 1,0);
        playerBoard.addShip(testShip3, 0, 0);

        sinkShip(testShip1);
        sinkShip(testShip2);

        assertTrue(game.isGameOver());
    }

    @Test
    void testIsGameOverReturnsFalseIfGameIsNotOver(){
        Ship testShip3 = new Ship("testShip3", 2);
        Boardable computerBoard = game.getComputerBoard();
        Boardable playerBoard = game.getPlayerBoard();
        computerBoard.addShip(testShip1, 0,0);
        computerBoard.addShip(testShip2, 1,0);
        playerBoard.addShip(testShip3, 0 ,0);

        sinkShip(testShip1);

        assertFalse(game.isGameOver());
    }

    @Test
    void testGetWinnerReturnsAMessageThatHumanIsTheWinner(){
        Ship testShip3 = new Ship("testShip3", 2);
        Boardable computerBoard = game.getComputerBoard();
        Boardable playerBoard = game.getPlayerBoard();
        computerBoard.addShip(testShip1, 0,0);
        computerBoard.addShip(testShip2, 1,0);
        playerBoard.addShip(testShip3, 0, 0);

        sinkShip(testShip1);
        sinkShip(testShip2);

        assertEquals("Human Player is the Winner!", game.getWinner());
    }

    @Test
    void testGetWinnerReturnsMessageThatComputerIsTheWinner(){
        Ship testShip3 = new Ship("testShip3", 2);
        Boardable playerBoard = game.getPlayerBoard();
        Boardable computerBoard = game.getComputerBoard();
        playerBoard.addShip(testShip1, 0,0);
        playerBoard.addShip(testShip2, 1,0);
        computerBoard.addShip(testShip3, 0,0);

        sinkShip(testShip1);
        sinkShip(testShip2);

        assertEquals("Computer Player is the Winner!", game.getWinner());
    }

    @Test
    void testGetWinnerReturnsMessageThatGameIsNotOverIfNoWinner(){
        Ship testShip3 = new Ship("testShip3", 2);
        Boardable computerBoard = game.getComputerBoard();
        Boardable playerBoard = game.getPlayerBoard();
        computerBoard.addShip(testShip1, 0,0);
        computerBoard.addShip(testShip2, 1,0);
        playerBoard.addShip(testShip3, 0 ,0);

        sinkShip(testShip1);

        assertEquals("Game is not over", game.getWinner());
    }

    @Test
    void testShipHitResultReturnsMissedMessageIfMissed(){
        Boardable testBoard = new TestClasses.TestBoard();
        testBoard.addShip(testShip1, 1, 0);
        game.getPlayerBoard().addShip(testShip1, 0, 0);
        game.getComputerBoard().addShip(testShip1, 0, 0);
        Request hitRequest = new Request(null, 0, 0);
        assertEquals("Missed!", game.shipHitResult(testBoard, hitRequest));
    }

    @Test
    void testShipHitResultReturnsHitShipMessageIfShipIsHit(){
        game.getPlayerBoard().addShip(testShip1, 0, 0);
        game.getComputerBoard().addShip(testShip1, 0, 0);
        Request hitRequest = new Request(null, 0, 0);

        assertEquals("The Player Board's testShip1 has been hit!", game.shipHitResult(game.getPlayerBoard(), hitRequest));
    }

    @Test
    void testShipHitResultReturnsShipSunkMessageIfShipIsSunk(){
        game.getPlayerBoard().addShip(testShip1, 0, 0);
        game.getPlayerBoard().addShip(testShip2, 1, 0);

        game.getComputerBoard().addShip(testShip1, 0, 0);
        game.getComputerBoard().addShip(testShip2, 1, 0);

        Request hitRequest = new Request(null, 0, 0);
        Request hitRequest2 =  new Request(null, 0, 1);
        Request hitRequest3 =  new Request(null,0, 2);
        game.fire(game.getPlayerBoard(), hitRequest);
        game.fire(game.getPlayerBoard(), hitRequest2);
        game.fire(game.getPlayerBoard(), hitRequest3);

        assertEquals("The Player Board's testShip1 has been hit! The testShip1 has been sunk!", game.shipHitResult(game.getPlayerBoard(), hitRequest3));
    }

    @Test
    void testShipHitResultReturnsWinnerMessageIfGameIsOver(){
        Ship testShip3 =  new Ship("testShip3", 2);
        Ship testShip4 =  new Ship("testShip4", 2);
        game.getPlayerBoard().addShip(testShip1, 0, 0);

        game.getComputerBoard().addShip(testShip3, 0, 0);
        game.getComputerBoard().addShip(testShip4, 1, 0);

        Request hitRequest = new Request(null, 0, 0);
        Request hitRequest2 =  new Request(null, 0, 1);
        Request hitRequest3 =  new Request(null,0, 2);

        game.fire(game.getPlayerBoard(), hitRequest);
        game.fire(game.getPlayerBoard(), hitRequest2);
        game.fire(game.getPlayerBoard(), hitRequest3);

        assertEquals("The Player Board's testShip1 has been hit! The testShip1 has been sunk! The Game is Over! Computer Player is the Winner!", game.shipHitResult(game.getPlayerBoard(), hitRequest3));
    }

   private boolean hasPlayerBoardBeenFiredUpon(Boardable board){
        for(int i = 0; i < board.getGrid().length; i++){
            for(int j = 0; j < board.getGrid()[i].length; j++){
                Sectionable section = board.getSection(i, j);
                if(section.getIsHit()){
                    return true;
                }
            }
        }
        return false;
    }

    private void sinkShip(Ship ship){
        for(ShipSection shipSection : ship.getShipSections()) {
            shipSection.receiveHit();
        }
    }

    @Test
    void getShipList() {
        assertEquals(game.getShipList(), game.shipList);
    }
}