package com.meritamerica.bank2.exception;

public class NegativeAmountException extends Exception{

	public NegativeAmountException(String errorMessage) {
	//public NegativeAmountException(String errorMessage, Throwable e) {

		super(errorMessage);
	}

}
