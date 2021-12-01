package com.battleship.backend.models;

public class ShipSection implements Sectionable {
    boolean isHit;

    public ShipSection() {
        isHit = false;

    }
    public boolean getIsHit() {
        return isHit;
    }

    public void receiveHit() {
        isHit = true;
    }
}
