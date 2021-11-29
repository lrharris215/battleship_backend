package com.battleship.backend.controllers;


import com.battleship.backend.models.Ship;
import com.battleship.backend.models.ShipSection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShipController {
    @GetMapping("/testShip")
    public @ResponseBody
    Ship getShip(){
        Ship ship = new Ship("Test Ship", 4);
        ShipSection[] shipSections = ship.getShipSections();
        for(ShipSection shipSection : shipSections){
            shipSection.receiveHit();
        }
        ship.checkIsSunk();
        return ship;
    }
}
