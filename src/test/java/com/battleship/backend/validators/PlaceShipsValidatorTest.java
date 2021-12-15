package com.battleship.backend.validators;

import com.battleship.backend.TestClasses;
import com.battleship.backend.models.Board;
import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Request;
import com.battleship.backend.models.Ship;
import org.apache.logging.log4j.message.ReusableMessage;
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
        Request badRequest = new Request(testShip, 100, 2);
       assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfColIsLessThanZero(){
        Request badRequest = new Request(testShip, 5, -5);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsTrueIfEverythingIsOK(){
        //Row and Col are within boundaries,
        //the ship won't go over the edge of the board,
        // and all needed spaces are empty
        Request goodRequest = new Request(testShip, 0 , 0);
        assertTrue(validator.isValid(testBoard, goodRequest));
    }

    @Test
    void testIsValidReturnsFalseIfShipWidthWouldGoOverEdgeOfBoard(){
        Request badRequest = new Request(testShip, 0, 2);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfShipHeightWouldGoOverEdgeOfBoard(){
        testShip.rotate();
        Request badRequest = new Request(testShip, 2, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfAnySpacesWithinShipAreAlreadyOccupied(){
        testBoard.addShip(testShip, 0 ,0);
        Request badRequest = new Request(testShip, 0, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }
}