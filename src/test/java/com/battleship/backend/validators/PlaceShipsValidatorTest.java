package com.battleship.backend.validators;

import com.battleship.backend.TestClasses;
import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.PlaceRequest;
import com.battleship.backend.models.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceShipsValidatorTest {
    Validator validator;
    Boardable testBoard;
    Ship testShip;

    @BeforeEach
    void initPlaceShipsValidatorAndTestClasses(){
        validator = new PlaceShipsValidator();
        testBoard = new TestClasses.TestBoard();
        testShip = new Ship("TestShip", 2);

    }
    @Test
    void testIsValidReturnsFalseIfRowIsLargerThanBoardSize() {
        PlaceRequest badRequest = new PlaceRequest(testShip, 100, 2);
       assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfColIsLessThanZero(){
        PlaceRequest badRequest = new PlaceRequest(testShip, 5, -5);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsTrueIfEverythingIsOK(){
        //Row and Col are within boundaries,
        //the ship won't go over the edge of the board,
        // and all needed spaces are empty
        PlaceRequest goodRequest = new PlaceRequest(testShip, 0 , 0);
        assertTrue(validator.isValid(testBoard, goodRequest));
    }

    @Test
    void testIsValidReturnsFalseIfShipWidthWouldGoOverEdgeOfBoard(){
        PlaceRequest badRequest = new PlaceRequest(testShip, 0, 2);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfShipHeightWouldGoOverEdgeOfBoard(){
        testShip.rotate();
        PlaceRequest badRequest = new PlaceRequest(testShip, 2, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfAnySpacesWithinShipAreAlreadyOccupied(){
        testBoard.addShip(testShip, 0 ,0);
        PlaceRequest badRequest = new PlaceRequest(testShip, 0, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }
}