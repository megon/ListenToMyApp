package com.listenMyApp.others;

import java.util.Random;

import org.junit.Test;

public class RandomNumberTest {
	
	@Test
	public void generateRandomNumberTest(){
		final Random random = new Random();
		final int randomNumber = random.nextInt(999999);
		System.out.println(randomNumber);
	}
}
