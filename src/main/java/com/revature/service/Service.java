package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.repository.EmployeeRepositoryJdbc;
import com.revature.repository.RequestRepositoryJdbc;

public final class Service {

	private static final Logger LOGGER = Logger.getLogger(Service.class);

	private static Service instance;

	private Service() {
		LOGGER.trace("Instantiation of Service class has been restricted.");
	}

	public static Service getInstance() {
		if (instance == null) {
			instance = new Service();
		}
		return instance;
	}

	/**
	 * Takes the entered username and password parameters, passed in
	 * through the Employee object, and returns an Employee object if the
	 * login is successful or null if it's not.
	 * 
	 * @param employee
	 */
	public Employee login(Employee employee) {
//		Code to log in here
		String username = employee.getUsername();
		String password = employee.getPassword();
		EmployeeRepositoryJdbc repository = new EmployeeRepositoryJdbc();
		LOGGER.trace("Searching for employee with login parameters: " + username + ", " + password);
		List<Employee> employees = new ArrayList<>();
		employees = repository.findAll();
		for (Employee model : employees) {
			if (model.getUsername().equals(username) && model.getPassword().equals(password)) {
				LOGGER.trace("Employee found.");
				return model;
			}
		}
		LOGGER.trace("Employee not found.");
		return null;
	}

	/**
	 * Responds with the string that sends the client to the right home URI for the client's title.
	 * @param employee
	 * @return
	 */
	public String viewHome(Employee employee) {
		if (employee.getTitle().getTitleId() == 2) {
			return "managerHome.html";
		}
		return "employeeHome.html";
	}

	// The logout method that ends the current session is actually in the servlet
	public boolean logout(Employee employee) {
		return false;
	}

	/**
	 * This method is only accessible if the user is logged in (handled by the Employee Controller).
	 * Takes the passed-in Request object and passes it to the create method in the RequestRepository.
	 * Returns boolean validation that the submission was successful or not.
	 * 
	 * @param request
	 */
	public boolean submitRequest(Request request) {
//		if (employee.isLoginStatus()) {

		RequestRepositoryJdbc repository = new RequestRepositoryJdbc();
		LOGGER.trace("Submitting request with parameters employee ID: " + request.getEmployee().getEmployeeId()
				+ ", request body: " + request.getRequestBody());
		if (repository.create(request)) {
			return true;
		}
		LOGGER.trace("Could not submit request.");
		return false;
	}

//	For now, pending requests are viewed alongside resolved ones.
	/**
	 * This method is only accessible if the user is logged in (handled by the Employee Controller).
	 * The passed in Employee object provides and ID field to get any requests belonging to that employee.
	 * An employee has the option to enter this method with his own info only.
	 * A manager has the option to view his own requests or to view those from a different employee ID.
	 * With that chosen ID, a new Employee object is passed into this method.
	 * 
	 * @param employee
	 */
	public List<Request> employeeViewRequest(Employee employee) {
		RequestRepositoryJdbc repository = new RequestRepositoryJdbc();
		List<Request> requests = new ArrayList<>();
		List<Request> desiredRequests = new ArrayList<>();
		LOGGER.trace(
				"Calling request with parameter employee ID: " + employee.getEmployeeId());
		requests = repository.findByEmployeeId(employee.getEmployeeId());
		for (Request request: requests) {
			if (request.getEmployee().getEmployeeId()==employee.getEmployeeId()) {
				desiredRequests.add(request);
			}
		}
		return desiredRequests;
	}
	
//	For now, pending requests are viewed alongside resolved ones.
	/**
	 * This method is only accessible to managers.
	 * @param employee
	 * @return
	 */
	public List<Request> managerViewRequest(Employee employee) {
		RequestRepositoryJdbc repository = new RequestRepositoryJdbc();
		List<Request> requests = new ArrayList<>();
		LOGGER.trace("Calling all requests.");
		requests = repository.findAll();
		return requests;
	}
	
	/**
	 * This method is accessible to managers only and is how they approve/deny requests.
	 * A request object with the changed status is passed in.
	 * That object is used to update the database entry for that request.
	 * The R_ID of the original request is preserved.
	 * @param request
	 * @return
	 */
	public boolean updateRequest(Request request) {
		LOGGER.trace("Updating request info with parameter request object: " + request);
		RequestRepositoryJdbc repository = new RequestRepositoryJdbc();
		return repository.update(request);
	}

	/**
	 * This method is only accessible if the user is logged in.
	 * @param employee
	 */
	public Employee viewInfo(Employee employee) {
		LOGGER.trace("Viewing employee info with parameter employee ID: " + employee.getEmployeeId());
		EmployeeRepositoryJdbc repository = new EmployeeRepositoryJdbc();
		return repository.findByEmployeeId(employee.getEmployeeId());
	}

	/**
	 * An employee object with the changed parameters is passed in.
	 * That object is used to update the database entry for the user, and it is also set in the session as the user's logged employee object.
	 * The E_ID of the original user is preserved.
	 * @param employee
	 */
	public Employee updateInfo(Employee employee) {
		LOGGER.trace("Updating employee info with parameter employee object: " + employee);
		EmployeeRepositoryJdbc repository = new EmployeeRepositoryJdbc();
		return repository.update(employee);
	}
	
	/**
	 * This method is only accessible to managers.
	 * @param employee
	 * @return
	 */
	public List<Employee> managerViewEmployee(Employee employee) {
		EmployeeRepositoryJdbc repository = new EmployeeRepositoryJdbc();
//		List<Employee> employees = new ArrayList<>();
		LOGGER.trace("Calling all employees.");
		return repository.findAll();
	}

	public static void main(String[] args) {
//		Service service = new Service();
//		new Status();
//		Employee testEmployee = new Employee("TestE", "password");
////		System.out.println(service.managerViewEmployee(testEmployee));
//		testEmployee.setFirstName("Test");
//		testEmployee.setUsername("TestE");
//		service.updateInfo(testEmployee);

//		Request testRequest = new Request(3, new Status(2));
//		service.updateRequest(testRequest);
		
		
	}
}
