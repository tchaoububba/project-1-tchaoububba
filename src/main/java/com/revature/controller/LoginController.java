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

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.service.Service;

public class LoginController {
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);
	
	/*
	 * Dependency
	 */
	private static Service service = Service.getInstance();
	
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
		LOGGER.trace("LoginController login method");
		
		if(request.getMethod().equals("GET")) {
			return "login.html";
		}
		
		Employee employee = service.login(new Employee(request.getParameter("username"), request.getParameter("password")));
		
		if (employee == null) {
//			We'll send a URI that sends to a different version of the login.html page.
//			It will be named 'incorrectLogin.html' and it will have the message
//			"Login authentication was unsuccessful. Please use a valid username and password."
//			Eventually, we'll plan to return a JSON object or something that can update a <p></p> field in the original login.html page.
//			return new EmployeeMessage("AUTHENTICATION FAILED");
			LOGGER.info("We need to look into implementing a slightly different form of login.html");
			return "login.html";
		}
		LOGGER.trace("Employee to be set in the session: " + employee);
		request.getSession().setAttribute("loggedEmployee", employee);
		return LoginController.viewHome(request);
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
		LOGGER.trace("We need some kind of 'loggedOutLogin.html' for this");
		return "login.html";
	}
	
	public static String viewHome(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		return service.viewHome((Employee)request.getSession().getAttribute("loggedEmployee"));
	}
}
