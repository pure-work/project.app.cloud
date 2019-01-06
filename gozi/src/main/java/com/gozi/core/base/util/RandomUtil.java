package com.gozi.core.base.util;

import java.util.Random;

public class RandomUtil {
	
	public static int getRandomInt(int minInt,int maxInt) {
		Random random = new Random();
		Boolean flag = true;
		int num = minInt;
		if(minInt == maxInt) {
			return num;
		}
		while(flag) {
			num = random.nextInt(maxInt);
			if(num >= minInt) {
				return num;
			}
		}
		return num;
	}
	public static String getRandomNumber(int length) {
		String randomNumber = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String rand = String.valueOf(random.nextInt(10));
			randomNumber += rand;
		}
		return randomNumber;
	}
}
