package com.battleship.backend;

import com.battleship.backend.models.Ship;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

    @Bean
    public Ship[] shipList(){
        Ship carrier = new Ship("Carrier", 5);
        Ship battleShip = new Ship("BattleShip", 4);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship submarine = new Ship("Submarine", 3);
        Ship destroyer = new Ship("Destroyer", 2);

        return new Ship[] { carrier, battleShip, cruiser, submarine, destroyer};
    }
}
