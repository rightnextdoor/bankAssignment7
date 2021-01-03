package com.meritamerica.bank2.exception;

public class ExceedsAvailableBalanceException  extends Exception{

	
	public ExceedsAvailableBalanceException(String e) {
		
		super (e);
		//System.out.println("not enough balance");
	}
	
	
}
