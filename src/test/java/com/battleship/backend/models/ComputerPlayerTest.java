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
    Ship testShip1;
    Ship testShip2;
    ComputerPlayer computerPlayer;
    Validator hitRequestValidator;
    Validator placeShipsValidator;


    @BeforeEach
    void initBoardRepoShipListAndComputerPlayer(){
      boardRepository  = new BoardRepository();
      testShip1 = new Ship("testShip1", 3);
      testShip2 = new Ship("testShip2", 2);
      shipList  = new Ship[] { testShip1, testShip2 };

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
    void testRemoveShipRemovesShipFromShipList(){
        computerPlayer.removeShip(testShip1);
        assertFalse(Arrays.stream(computerPlayer.getShipList()).anyMatch(ship -> ship == testShip1));

    }

// Getter tests

    @Test
    void getShipList() {
        assertEquals(shipList, computerPlayer.getShipList());
    }

}