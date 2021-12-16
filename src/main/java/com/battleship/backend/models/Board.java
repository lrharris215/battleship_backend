package com.battleship.backend.models;

import java.util.ArrayList;

public class Board implements Boardable{
    Sectionable[][] grid;
    String name;
    int size;
    ArrayList<Ship> shipList;

    public Board(String name){
        this.name = name;
        size = 10;
        grid = new Sectionable[size][size];
        shipList = new ArrayList<Ship>();
        setUp();
    }

    public void addShip(Ship ship, int row, int col){
        for(int i = 0; i < ship.getWidth(); i++){
            addSection(ship.getShipSection(i), row, col + i);
        }
        for(int i = 0; i < ship.getHeight(); i++){
            addSection(ship.getShipSection(i), row + i, col);
        }
        shipList.add(ship);
    }

    public void removeShip(Ship ship){
        Ship shipToRemove = null;
        for(Ship boardShip : shipList ){
            if(boardShip.equals(ship)){
                shipToRemove = boardShip;
                for(int i = 0; i < grid.length; i++){
                    for(int j = 0; j < grid[i].length; j++){
                        Sectionable section = grid[i][j];
                        if(section.getIsShip() && section.getShipName().equals(ship.getName())){
                            grid[i][j] =  new NullSection();
                        }
                    }
                }

            }
        }
        shipList.remove(shipToRemove);
    }

    public void addSection(Sectionable section, int row, int col){
        grid[row][col] = section;
    }

    public void hitSection(int row, int col){
        getSection(row, col).receiveHit();
    }

    public boolean hasShips(){
        return shipList.size() > 0;
    }

    public boolean hasShip(Ship maybeShip){
        for(Ship ship : shipList){
            if(ship.equals(maybeShip)){
                return true;
            }
        }
        return false;
    }

    public boolean isPermutationOfShipList(ArrayList<Ship> otherShipList){
        int count = 0;
        for(Ship boardShip : shipList){
            for(Ship otherShip : otherShipList){
                if(boardShip.equals(otherShip)){
                    count += 1;
                    break;
                }
            }
        }
        return count == shipList.size() && count == otherShipList.size();
    }

    // Getters
    public Sectionable[][] getGrid(){
        return grid;
    }

    public String getName(){
        return name;
    }

    public Sectionable getSection(int row, int col){
        return grid[row][col];
    }

    public int getSize(){
        return size;
    }

    public ArrayList<Ship> getShipList() {
        return shipList;
    }

    // private
    private void setUp() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                addSection(new NullSection(), i, j);
            }
        }
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", grid: " + getGrid().length;
    }

}
