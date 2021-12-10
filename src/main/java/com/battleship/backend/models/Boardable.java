package com.battleship.backend.models;

import java.util.ArrayList;

public interface Boardable {
    String getName();
    Sectionable getSection(int row, int col);
    Sectionable[][] getGrid();
    void addShip(Ship ship, int row, int col);
    void removeShip(Ship ship);

    int getSize();

    void addSection(Sectionable section, int row, int col);

    void hitSection(int row, int col);

    boolean hasShips();

    ArrayList<Ship> getShipList();
}
