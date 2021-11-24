package com.battleship.backend.models;

public class Ship {
    int width;
    int height;

    String name;
    String status;



    public Ship(String name, int length){
        this.name = name;
        this.height = 1;
        this.width = length;
        this.status = "OK";
    }

    public void rotate(){};

    public void receiveHit(){};


}
