//--EMPLOYEE----------------------------------------
//--Name       Null?    Type          
//------------ -------- ------------- 
//--E_ID       NOT NULL NUMBER        
//--FIRST_NAME NOT NULL VARCHAR2(100) 
//--LAST_NAME  NOT NULL VARCHAR2(100) 
//--USERNAME   NOT NULL VARCHAR2(100) 
//--PASSWORD   NOT NULL VARCHAR2(100) 
//--T_ID       NOT NULL NUMBER
package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.service.Service;

public class LoginController {
	
	/*
	 * Dependency
	 */
	private static Service service = Service.getInstance();
	
//	public static Object getAllCustomers(HttpServletRequest request) {
//		//Calls the service which calls the DAO to get the list of customers
//		return customerService.listAllCustomers();
//	}
	
	/**
	 * If the method is GET, it will return the login view.
	 * 
	 * If the method is POST.
	 * -> If service layer returns null, we return a message
	 * stating that authentication failed.
	 * 
	 * -> Else, it will return the Customer information
	 * (and store it in the session).
	 */
	public static Object login(HttpServletRequest request) {
		
		Employee employee = service.login(new Employee(request.getParameter("username"), request.getParameter("password")));
		
		if (employee == null) {
//			We'll send a URI that sends to a different version of the login.html page.
//			It will be named 'incorrectLogin.html' and it will have the message
//			"Login authentication was unsuccessful. Please use a valid username and password."
//			Eventually, we'll plan to return a JSON object or something that can update a <p></p> field in the original login.html page.
			return "incorrectLogin.html";
		}
		request.getSession().setAttribute("loggedEmployee", employee);
		return employee;
	}
	
	/**
	 * Invalidates the session and returns the login view.
	 */
	public static String logout(HttpServletRequest request) {
		/*
		 * If session.invalidate() doesn't work for you
		 */
//		throw new RuntimeException("Something went wrong");
		
		/*Also if session.invalidate() doesn't work*/
//		request.getSession().setAttribute("loggedCustomer", null);
		
		request.getSession().invalidate();
//		We'll send a URI that sends to a different version of the login.html page.
//		It will be named 'loggedOutLogin.html' and it will have the message
//		"Logout was successful!"
//		Eventually, we'll plan to return a JSON object or something that can update a <p></p> field in the original login.html page.
		return "loggedOutLogin.html";
	}
}
