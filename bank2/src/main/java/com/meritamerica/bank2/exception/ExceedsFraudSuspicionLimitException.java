package com.meritamerica.bank2.exception;

public class ExceedsFraudSuspicionLimitException extends Exception{

	public ExceedsFraudSuspicionLimitException(String e) {
	
		super (e);
	
			//System.out.println("cannot deposit more than 1000");
	}
	
}
