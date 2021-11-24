package com.battleship.backend;

import com.battleship.backend.controllers.WelcomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BattleshipApplicationTests {


	@Autowired
	private WelcomeController welcomeController;

	@Test
	void contextLoads() {
	}

	@Test
	void testTest(){
		int five = 5;
		assertEquals(8, five + 3);
	}

	@Test
	void testWelcomeControllerExists() throws Exception {
		assertThat(welcomeController).isNotNull();
	}

}
