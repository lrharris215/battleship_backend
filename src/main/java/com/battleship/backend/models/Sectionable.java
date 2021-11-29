package com.battleship.backend.models;

public interface Sectionable {

    void receiveHit();

    boolean getIsHit();

    String getShip();
}

