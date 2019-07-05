package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Request;
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
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get an employee
		return service.viewInfo(employee);
	}
	
	@Override
	public Object getAllEmployees(HttpServletRequest request) {
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get the list of employees
		return service.managerViewEmployee(employee);
	}

	@Override
	public String updateEmployee(HttpServletRequest request) {
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		employee.setFirstName(request.getParameter("firstName"));
		employee.setLastName(request.getParameter("lastName"));
		employee.setUsername(request.getParameter("username"));
		employee.setPassword(request.getParameter("password"));
		//Calls the service which calls the DAO to update an employee
		if (service.updateInfo(employee) == true) {
			return "Your information was updated successfully!";
		}
		return "Your information was not updated.";
	}

//	I may have to remember to add options to html page for Manager to view his own requests or those of the selected ID.
	@Override
	public Object getRequests(HttpServletRequest request) {
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get a list of requests
		if (employee.getTitle().getTitleId() == 2) {
			employee.setEmployeeId(Long.parseLong(request.getParameter("employeeId")));
		}
		return service.employeeViewRequest(employee);
	}

	@Override
	public Object getAllRequests(HttpServletRequest request) {
		Employee employee = (Employee)request.getSession().getAttribute("loggedEmployee");
		//Calls the service which calls the DAO to get the list of requests
		return service.managerViewRequest(employee);
	}

	@Override
	public String submitRequest(HttpServletRequest request) {
		//Calls the service which calls the DAO to submit a request
		if (service.submitRequest(new Request((Employee)request.getSession().getAttribute("loggedEmployee"),request.getParameter("requestBody")))) {
			return "Your request was submitted successfully.";
		}
		return "Your request was not submitted.";
	}

	@Override
	public String updateRequest(HttpServletRequest request) {
		if (service.updateRequest(new Request(Long.parseLong(request.getParameter("requestId"))))) {
			return "The request was successfully updated.";
		}
		return "The request was not updated";
	}

}
