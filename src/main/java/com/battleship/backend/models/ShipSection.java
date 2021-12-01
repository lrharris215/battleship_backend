package com.battleship.backend.models;

public class ShipSection implements Sectionable {
    boolean isHit;
    boolean isShip;
    String shipName;

    public ShipSection() {
        isHit = false;
        isShip = true;
        shipName = "";
    }

    public ShipSection(String shipName){
        isHit = false;
        isShip = true;
        this.shipName = shipName;
    }

    public void receiveHit() {
        isHit = true;
    }

    //Getters

    public boolean getIsHit() {
        return isHit;
    }

    public boolean getIsShip(){
        return isShip;
    }

    public String getShipName(){
        return shipName;
    }
}
