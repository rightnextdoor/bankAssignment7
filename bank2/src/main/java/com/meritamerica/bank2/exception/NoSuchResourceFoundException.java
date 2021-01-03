package com.meritamerica.bank2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResourceFoundException extends Exception {
	
	public NoSuchResourceFoundException(String msg) {
			super(msg);
		}
}
