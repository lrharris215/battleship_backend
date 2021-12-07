package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {
    BoardRepository boardRepository;
    Ship[] shipList;
    ComputerPlayer computerPlayer;
    Validator hitRequestValidator;
    Validator placeShipsValidator;

    @BeforeEach
    void initBoardRepoShipListAndComputerPlayer(){
      boardRepository  = new BoardRepository();
      shipList  = new Ship[] { new Ship("testShip1", 3), new Ship("testShip2", 2)};
      computerPlayer = new ComputerPlayer(boardRepository, shipList);
      hitRequestValidator = new HitRequestValidator();
      placeShipsValidator = new PlaceShipsValidator();


    }

    @Test
    void testGenerateHitRequestReturnsValidHitRequest(){
       Request hitRequest = computerPlayer.generateHitRequest();
       assertTrue(hitRequestValidator.isValid(boardRepository.getPlayerBoard(), hitRequest));
    }

    @Test
    void testGeneratePlaceRequestReturnsValidPlaceRequest(){
        Request placeRequest = computerPlayer.generatePlaceRequest();
        assertTrue(placeShipsValidator.isValid(boardRepository.getComputerBoard(), placeRequest));
    }

    @Test
    void testComputerPlayerUpdatesPlayerBoardWithHitRequest(){
        Request hitRequest = computerPlayer.generateHitRequest();
        computerPlayer.fire(hitRequest);
        Boardable playerBoard = boardRepository.getPlayerBoard();
        Sectionable hitSection = playerBoard.getSection(hitRequest.getRow(), hitRequest.getCol());
        assertTrue(hitSection.getIsHit());
    }

    @Test
    void testComputerPlayerUpdatesComputerBoardWithPlaceRequest(){
        Request placeRequest = computerPlayer.generatePlaceRequest();
        computerPlayer.placeShip(placeRequest);
        Boardable computerBoard = boardRepository.getComputerBoard();
        Sectionable placeSection = computerBoard.getSection(placeRequest.getRow(), placeRequest.getCol());

        assertTrue(placeSection.getIsShip());
    }

    @Test
    void testPlaceShipRemovesShipFromShipListAfterItHasBeenPlaced(){
        Request placeRequest = computerPlayer.generatePlaceRequest();
        computerPlayer.placeShip(placeRequest);
        Ship placedShip = placeRequest.getShip();

        //ship list doesn't have the placed ship anymore.
        assertFalse(computerPlayer.getShips().includes(placedShip));
    }

// Getter tests
    @Test
    void getBoardRepository() {
        assertEquals(boardRepository, computerPlayer.getBoardRepository());
    }

    @Test
    void getShipList() {
        assertEquals(shipList, computerPlayer.getShipList());
    }

    @Test
    void getPlayerBoard() {
        assertEquals(boardRepository.getPlayerBoard(), computerPlayer.getPlayerBoard());
    }

    @Test
    void getComputerBoard() {
        assertEquals(boardRepository.getComputerBoard(), computerPlayer.getComputerBoard());
    }

    @Test
    void getName() {
        assertEquals("Computer Player", computerPlayer.getName());
    }
}