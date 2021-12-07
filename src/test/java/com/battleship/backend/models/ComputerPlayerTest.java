package com.battleship.backend.models;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.validators.HitRequestValidator;
import com.battleship.backend.validators.PlaceShipsValidator;
import com.battleship.backend.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

      hitRequestValidator = new HitRequestValidator();
      placeShipsValidator = new PlaceShipsValidator();
        computerPlayer = new ComputerPlayer(boardRepository, shipList, placeShipsValidator, hitRequestValidator);


    }

    @Test
    void testGenerateHitRequestReturnsValidHitRequest(){
       Request hitRequest = computerPlayer.generateValidHitRequest();
       assertTrue(hitRequestValidator.isValid(boardRepository.getPlayerBoard(), hitRequest));
    }

    @Test
    void testGeneratePlaceRequestReturnsValidPlaceRequest(){
        Request placeRequest = computerPlayer.generateValidPlaceRequest(computerPlayer.getShipList()[0]);
        assertTrue(placeShipsValidator.isValid(boardRepository.getComputerBoard(), placeRequest));
    }

    @Test
    void testComputerPlayerUpdatesPlayerBoardWithHitRequest(){
        Request hitRequest = computerPlayer.generateValidHitRequest();
        computerPlayer.fire(hitRequest);
        Boardable playerBoard = boardRepository.getPlayerBoard();
        Sectionable hitSection = playerBoard.getSection(hitRequest.getRow(), hitRequest.getCol());
        assertTrue(hitSection.getIsHit());
    }

    @Test
    void testComputerPlayerUpdatesComputerBoardWithPlaceRequest(){
        Request placeRequest = computerPlayer.generateValidPlaceRequest(computerPlayer.getShipList()[0]);
        computerPlayer.placeShip(placeRequest);
        Boardable computerBoard = boardRepository.getComputerBoard();
        Sectionable placeSection = computerBoard.getSection(placeRequest.getRow(), placeRequest.getCol());

        assertTrue(placeSection.getIsShip());
    }

    @Test
    void testPlaceShipRemovesShipFromShipListAfterItHasBeenPlaced(){
        Ship placedShip = computerPlayer.getShipList()[0];
        Request placeRequest = computerPlayer.generateValidPlaceRequest(placedShip);
        computerPlayer.placeShip(placeRequest);

        //ship list doesn't have the placed ship anymore.
        assertFalse(Arrays.stream(computerPlayer.getShipList()).anyMatch(ship -> ship == placedShip));
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