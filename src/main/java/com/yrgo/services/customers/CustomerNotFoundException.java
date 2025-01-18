package com.yrgo.services.customers;

public class CustomerNotFoundException extends Exception {

	// Standardkonstruktör
	public CustomerNotFoundException() {
		super("Customer not found");
	}

	// Konstruktor med orsak
	public CustomerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
