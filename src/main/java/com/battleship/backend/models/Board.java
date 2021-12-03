package com.battleship.backend.models;

public class Board implements Boardable{
    Sectionable[][] grid;
    String name;
    int size;

    public Board(String name){
        this.name = name;
        size = 10;
        grid = new Sectionable[size][size];

        setUp();
    }

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

    public void addShip(Ship ship, int row, int col){
        for(int i = 0; i < ship.getWidth(); i++){
            addSection(ship.getShipSection(i), row, col + i);
        }
        for(int i = 0; i < ship.getHeight(); i++){
            addSection(ship.getShipSection(i), row + i, col);
        }
    }

    public void addSection(Sectionable section, int row, int col){
        grid[row][col] = section;
    }

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
