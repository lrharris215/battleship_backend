package com.battleship.backend.models;

import java.util.Arrays;

public class Ship {
    int width;
    int height;

    String name;
    String status;

    ShipSection[] shipSections;



    public Ship(String name, int length){
        this.name = name;
        this.height = 1;
        this.width = length;
        this.status = "OK";

        shipSections = new ShipSection[length];
        fillShip();
    }

    public void fillShip(){
        for(int i = 0; i < shipSections.length; i++){
            shipSections[i] = new ShipSection(this);
        }
    }

    public void rotate(){
        int temp = getWidth();
        setWidth(getHeight());
        setHeight(temp);
    }

    public void receiveHit(){}


    // getters
    public String getName(){
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }

    public ShipSection[] getShipSections(){
        return shipSections;
    }


}
