package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {
    Request testRequest;
    Ship testShip;
    @BeforeEach
    void initNewPlaceRequest(){
        testShip = new Ship("TestShip", 2);
        testRequest = new Request(testShip, 0, 0);
    }
    @Test
    void testGetShip() {
        assertEquals(testShip, testRequest.getShip());
    }

    @Test
    void testGetRow() {
        assertEquals(0, testRequest.getRow());
    }

    @Test
    void getCol() {
        assertEquals(0, testRequest.getCol());
    }
}