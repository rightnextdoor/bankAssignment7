package com.meritamerica.bank2;

import com.meritamerica.bank2.models.MeritBank;

public class MeritAmericaBankApp {
	public static void main(String[] args) {
		
		MeritBank.readFromFile("src/test/testMeritBank_good.txt");	
	}
}
