package com.battleship.backend.models;

public class ShipSection implements Sectionable{
    String status;

    public ShipSection(){
        status = "OK";
    }

    public void receiveHit() {
        status = "HIT";
    }
}
