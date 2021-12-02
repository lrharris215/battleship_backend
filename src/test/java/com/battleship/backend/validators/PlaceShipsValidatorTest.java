package com.battleship.backend.validators;

import com.battleship.backend.TestClasses;
import com.battleship.backend.models.Boardable;
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
       assertFalse(validator.isValid(testBoard, testShip, 100, 2));
    }

    @Test
    void testIsValidReturnsFalseIfColIsLessThanZero(){
        assertFalse(validator.isValid(testBoard, testShip, 5, -5));
    }

    @Test
    void testIsValidReturnsTrueIfEverythingIsOK(){
        //Row and Col are within boundaries,
        //the ship won't go over the edge of the board,
        // and all needed spaces are empty
        assertTrue(validator.isValid(testBoard, testShip, 0, 0));
    }

    @Test
    void testIsValidReturnsFalseIfShipLengthWouldGoOverEdgeOfBoard(){
        assertFalse(validator.isValid(testBoard, testShip, 0, 2));
    }

    @Test
    void testIsValidReturnsFalseIfAnySpacesWithinShipAreAlreadyOccupied(){
        testBoard.addShip(testShip, 0 ,0);

        assertFalse(validator.isValid(testBoard, testShip, 0,0));
    }
}