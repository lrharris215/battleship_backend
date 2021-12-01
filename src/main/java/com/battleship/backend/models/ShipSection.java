package com.battleship.backend.models;

public class ShipSection implements Sectionable {
    boolean isHit;
    boolean isShip;

    public ShipSection() {
        isHit = false;
        isShip = true;

    }
    public boolean getIsHit() {
        return isHit;
    }

    public boolean getIsShip(){
        return isShip;
    }

    public void receiveHit() {
        isHit = true;
    }
}
