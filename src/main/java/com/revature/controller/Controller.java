package com.revature.controller;

import java.util.Scanner;

import com.revature.model.Employee;
import com.revature.service.Service;

public class Controller {
	
//	Have every class check for loginStatus.
	
	public Controller() {
		
		Employee user = new Employee();
//		Scanner for username and password (and everything)
		Scanner scanner = new Scanner(System.in);

		boolean infiniteLoop = true;		
		while (infiniteLoop == true) {
			
			System.out.println("What would you like to do? You may register, login, logout, view your balance, deposit money, or withdraw money.");
			System.out.println("Enter 'REGISTER', 'LOGIN', 'LOGOUT', 'VIEW', 'DEPOSIT', 'WITHDRAW', or 'EXIT' (without the quotes).");
			
			String userAction = scanner.nextLine();
//			Handles when null or "" is passed
			if (userAction == null || userAction.length() == 0) {
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("Expected an action request");
				}
			}
			
			String normalizedUserAction = userAction.toLowerCase();
			Service service = Service.getInstance();
			
			switch(normalizedUserAction) {
			case "login":
				service.login(user, scanner);
				break;
			case "exit":
				infiniteLoop = false;
			case "logout":
				service.logout(user);
				break;
			case "view":
				service.viewBalance(user);
				break;
			case "deposit":
				service.depositMoney(user, scanner);;
				break;
			case "withdraw":
				service.withdrawMoney(user, scanner);
				break;
			case "register":
				service.logout(user);
				service.register(scanner);
				break;
			default:
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("Not a valid action request");
				}
			}
		}
		System.out.println("Exiting program");
		scanner.close();
	}
}
