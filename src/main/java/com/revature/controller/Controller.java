//package com.revature.controller;
//
//import java.util.Scanner;
//
//import com.revature.model.Employee;
//import com.revature.service.Service;
//
///**
// * I think I need to use the request parameters to set up an employee object to pass into the service package.
// * @author tchao
// *
// */
//public class Controller {
//
////	Have every class check for loginStatus.
//
//	public Controller() {}
//	
//	public controller()
//
//		Employee user = new Employee();
//
//		String userAction = scanner.nextLine();
////			Handles when null or "" is passed
//		if (userAction == null || userAction.length() == 0) {
//			try {
//				throw new IllegalArgumentException();
//			} catch (IllegalArgumentException e) {
//				System.out.println("Expected an action request");
//			}
//		}
//
//		String normalizedUserAction = userAction.toLowerCase();
//		Service service = Service.getInstance();
//
//		switch (normalizedUserAction) {
//		case "login":
//			service.login(user, scanner);
//			break;
//		case "exit":
//			infiniteLoop = false;
//		case "logout":
//			service.logout(user);
//			break;
//		case "view":
//			service.viewBalance(user);
//			break;
//		case "deposit":
//			service.depositMoney(user, scanner);
//			;
//			break;
//		case "withdraw":
//			service.withdrawMoney(user, scanner);
//			break;
//		case "register":
//			service.logout(user);
//			service.register(scanner);
//			break;
//		default:
//			try {
//				throw new IllegalArgumentException();
//			} catch (IllegalArgumentException e) {
//				System.out.println("Not a valid action request");
//			}
//		}
//
//		System.out.println("Exiting program");
//		scanner.close();
//	}
//}
