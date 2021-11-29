package com.battleship.backend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShipTest {

    @Test
    void fillShipPlacesShipSections() {
        Ship testShip = new Ship("Test", 3);

        assertNotNull(testShip.getShipSections());
    }

    @Test
    void rotate() {
    }

    @Test
    void checkIsSunk() {
    }

    @Test
    void setWidth() {
    }

    @Test
    void setHeight() {
    }
}