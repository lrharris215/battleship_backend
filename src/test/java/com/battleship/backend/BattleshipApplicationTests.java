package com.battleship.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BattleshipApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testTest(){
		int five = 5;
		assertEquals(8, five + 3);
	}

}
