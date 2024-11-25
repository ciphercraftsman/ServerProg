package com.yrgo.services.customers;

public class CustomerNotFoundException extends Exception {

	// Standardkonstruktör
	public CustomerNotFoundException(String message) {
		super(message);
	}

	// Konstruktor med orsak
	public CustomerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
