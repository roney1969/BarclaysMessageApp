package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BarclayMessageApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("This test simple ensure the context loads.  It will throw an exception if it doesn't.");
		assertTrue(true);
	}

}
