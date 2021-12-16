package com.battleship.backend.models;

public class RequestFactory {

    public RequestFactory(){}

    public Request generatePlaceRequest(Ship ship, int row, int col){
        return new Request(ship, row, col);
    }

    public Request generateHitRequest(int row, int col){
        return new Request(null, row, col);
    }
}
