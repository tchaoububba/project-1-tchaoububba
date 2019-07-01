package com.revature.exception;

public class BalanceTooSmallException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BalanceTooSmallException() {
		super("Your balance is too small for this withdrawal.");
	}
}
