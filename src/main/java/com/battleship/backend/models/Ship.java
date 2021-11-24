package com.battleship.backend.models;

import java.util.Arrays;

public class Ship {
    int width;
    int height;

    String name;
    boolean isSunk;

    ShipSection[] shipSections;



    public Ship(String name, int length){
        this.name = name;
        this.height = 1;
        this.width = length;
        isSunk = false;

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

    public boolean isSunk(){
        for (ShipSection shipSection : shipSections) {
            if (!shipSection.getStatus()) {
                return false;
            }
        }
        isSunk = true;
        return true;
    }




    // getters
    public String getName(){
        return name;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ShipSection[] getShipSections(){
        return shipSections;
    }

    //setters
    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }




}
