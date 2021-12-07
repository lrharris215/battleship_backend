package com.battleship.backend.validators;

import com.battleship.backend.TestClasses;
import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.Request;
import com.battleship.backend.models.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HitRequestValidatorTest {
    Validator validator;
    Boardable testBoard;
    Ship testShip;

    @BeforeEach
    void initTestBoardAndValidator(){
        validator = new HitRequestValidator();
        testBoard = new TestClasses.TestBoard();
        testShip = new Ship("TestShip", 2);
    }

    @Test
    void testIsValidReturnsFalseIfRowIsGreaterThanBoardSize() {
        Request badRequest = new Request(null,100, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfRowIsLessThanZero() {
        Request badRequest = new Request(null,-5, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfColIsGreaterThanBoardSize() {
        Request badRequest = new Request(null,0, 100);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfColIsLessThanZero() {
        Request badRequest = new Request(null,0, -5);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsFalseIfSpaceIsAlreadyHit(){
        testBoard.hitSection(0, 0);
        Request badRequest = new Request(null,0, 0);
        assertFalse(validator.isValid(testBoard, badRequest));
    }

    @Test
    void testIsValidReturnsTrueIfSpaceAndOnBoardAndNotAlreadyHit(){
        Request goodRequest = new Request(null,0, 0);
        assertTrue(validator.isValid(testBoard, goodRequest));
    }

    @Test
    void testIsValidReturnsTrueIfSpaceIsAUnhitShip(){
        testBoard.addShip(testShip, 0 , 0);
        Request goodRequest = new Request(null,0, 0);
        assertTrue(validator.isValid(testBoard, goodRequest));
    }
}