package com.revature;

import com.revature.controller.Controller;

/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
public class Main {

	public static void main(String[] args) {
		
		/**
		 * We put a method into Controller that lets the user choose actions from there instead of doing them here
		 */
		new Controller();
	}
}
