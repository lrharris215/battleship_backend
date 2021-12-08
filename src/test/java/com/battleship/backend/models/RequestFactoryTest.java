package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestFactoryTest {
    RequestFactory requestFactory;
    int testRow;
    int testCol;
    @BeforeEach
    void initRequestFactory(){
        requestFactory = new RequestFactory();
        testRow = 0;
        testCol = 0;
    }

    @Test
    void testGeneratePlaceRequestReturnsARequestWithDesiredShipRowCol() {
        Ship testShip = new Ship("test", 3);
        Request testRequest = requestFactory.generatePlaceRequest(testShip, testRow, testCol);
        assertTrue(testRequest.getShip() ==  testShip && testRequest.getRow() == testRow && testRequest.getCol() == testCol);
    }

    @Test
    void testGenerateHitRequestReturnsARequestWithRowColAndNullShip() {
        Request testRequest = requestFactory.generateHitRequest(testRow, testCol);
        assertTrue(testRequest.getShip() == null && testRequest.getRow() == testRow && testRequest.getCol() == testCol);
    }
}