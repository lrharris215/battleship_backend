package com.battleship.backend;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.NullSection;
import com.battleship.backend.models.Sectionable;
import com.battleship.backend.models.Ship;

import java.util.ArrayList;

public class TestClasses {
    public static class TestBoard implements Boardable{
            String name;
            Sectionable[][] grid;
            ArrayList<Ship> shipList;

            public TestBoard(){
                name = "testBoard";
                grid = new Sectionable[3][3];
                shipList = new ArrayList<Ship>();
                setUpBoard();
            }
            public String getName() {
                return name;
            }

            public Sectionable getSection(int row, int col) {
                return grid[row][col];
            }

            public Sectionable[][] getGrid() {
                return grid;
            }

            public int getSize(){ return 3; }

            public void addShip(Ship ship, int row, int col) {
                grid[0][0] = ship.getShipSection(0);
                grid[0][1] = ship.getShipSection(1);

                shipList.add(ship);
            }

            public void removeShip(Ship ship) {
                
            }

        public boolean hasShips(){
                return true;
            }

        public ArrayList<Ship> getShipList() {
            return shipList;
        }

        public void addSection(Sectionable section, int row, int col) {

            }

        public void hitSection(int row, int col) {
            getSection(row, col).receiveHit();
        }

        private void setUpBoard() {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j] = new NullSection();
                    }
                }
            }
    }
}
