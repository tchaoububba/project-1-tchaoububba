package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.model.Status;
import com.revature.service.Service;

/**
 * I think I need to use the request parameters to set up an employee object to pass into the service package.
 * @author tchao
 *
 */
public final class EmployeeController implements ModelController {
	
	private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);
	
	private static EmployeeController instance;

	private EmployeeController() {
		LOGGER.trace("Instantiation of EmployeeController class has been restricted.");
	}

	public static EmployeeController getInstance() {
		if (instance == null) {
			instance = new EmployeeController();
		}
		return instance;
	}
	
	/*
	 * Dependency
	 */
	private static Service service = Service.getInstance();
	
//	We'll consider putting a register method for employees eventually.
	
//	public static Object register(HttpServletRequest request) {
//		if(request.getMethod().equals("GET")) {
//			return "register.html";
//		}
//		/*At this point, the request must have been POST*/
//		//request.getInputStream()
//		
		//new ObjectMapper().method(string, Customer.class)
//		
//		customerService.register(new Customer(0, request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("username"), request.getParameter("password")));
//		
//		return "REGISTRATION_SUCCESSFUL";
//	}

	@Override
	public Object getEmployee(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get an employee
		return service.viewInfo(employee);
	}
	
	@Override
	public Object getAllEmployees(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get the list of employees
		return service.managerViewEmployee(employee);
	}

	@Override
	public String updateEmployee(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		LOGGER.trace("EmployeeController updateEmployee method with parameters, firstName: " + request.getParameter("firstName") + " lastName: " + request.getParameter("lastName"));
		employee.setFirstName(request.getParameter("firstName"));
		employee.setLastName(request.getParameter("lastName"));
//		employee.setUsername(request.getParameter("username"));
//		employee.setPassword(request.getParameter("password"));
		//Calls the service which calls the DAO to update an employee
		if (service.updateInfo(employee)) {
			LOGGER.trace("Your profile information was updated successfully!<<<<We need to incorporate that message somehow");
			return "profile.html";
		}
		LOGGER.trace("Your profile information was not updated.<<<<We need to incorporate that message somehow");
		return "profile.html";
	}

//	I may have to remember to add options to html page for Manager to view his own requests or those of the selected ID.
	@Override
	public Object getRequests(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee stored. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get a list of requests
		if (employee.getTitle().getTitleId() == 2) {
			if (request.getParameter("employeeId") == null) {
				return service.employeeViewRequest(employee);
			}
			employee.setEmployeeId(Long.parseLong(request.getParameter("employeeId")));
		}
		return service.employeeViewRequest(employee);
	}

	@Override
	public Object getAllRequests(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get the list of requests
		return service.managerViewRequest(employee);
	}

	@Override
	public String submitRequest(HttpServletRequest request) {
		//Calls the service which calls the DAO to submit a request
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		if (service.submitRequest(new Request((Employee)request.getSession().getAttribute("loggedEmployee"),request.getParameter("requestBody")))) {
			LOGGER.trace("Your request was submitted successfully.<<<<We need to incorporate that message somehow");
			return LoginController.viewHome(request);
		}
		LOGGER.trace("Your request was not submitted.<<<<We need to incorporate that message somehow");
		return LoginController.viewHome(request);
	}

	@Override
	public String updateRequest(HttpServletRequest request) {
		if (request.getSession().getAttribute("loggedEmployee") == null) {
			LOGGER.trace("There is no longer a loggedEmployee storeda. We need to implement a way of telling the client he was logged out.");
			return "login.html";
		}
		if (service.updateRequest(new Request(Long.parseLong(request.getParameter("requestId")), new Status(Long.parseLong(request.getParameter("statusId")))))) {
			LOGGER.trace("The request was successfully updated.<<<<We need to incorporate that message somehow");
			return "manager.html";
		}
		LOGGER.trace("The request was not updated.<<<<We need to incorporate that message somehow");
		return "manager.html";
	}

}
