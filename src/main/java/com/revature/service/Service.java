package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.model.Status;
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
	 * We're going to take the entered username and password parameters, passed in
	 * through the Employee object, and then return some kind of validation that the
	 * login was successful or not. I'm currently undecided between passing back a
	 * boolean and an Employee object (null if unsuccessful).
	 * 
	 * @param employee
	 */
	public Employee login(Employee employee) {
////		Check if user is already logged in
//		if (employee.isLoginStatus()) {
//			System.out.println(
//					"You are already logged in.  Please logout if you are attempting to login as someone else.");
//		} else {

//		System.out.println("Please enter your username.");
//		String username = scanner.nextLine();
//		System.out.println("Please enter your password.");
//		String password = scanner.nextLine();
//		employee.setUsername(username);
//		employee.setPassword(password);
////		Insert scanned username + password into ConnectionDriver info.

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
//		if (employee.isLoginStatus()) {
//			System.out.println("Welcome " + employee.getUsername() + "!");
//		} else {
//			try {
//				throw new WrongUsernameOrPasswordException();
//			} catch (WrongUsernameOrPasswordException e) {
//				System.out.println("Wrong username and/or password was given. Please try again.");
//			}
//		}
	}

	/**
	 * We'll respond with the string that sends the client to the right URI for their home.
	 * @param employee
	 * @return
	 */
	public String viewHome(Employee employee) {
		if (employee.getTitle().getTitleId() == 2) {
			return "managerHome";
		}
		return "employeeHome";
	}

	// This should probably go in a servlet that will end the current session and
	// bring client to home
	public boolean logout(Employee employee) {
//		Code to log out here
//		if (employee.isLoginStatus()) {
//			employee.setLoginStatus(false);
//			System.out.println("You have successfully logged out!");
//		} else {
//			System.out.println("You are not logged in.");
//		}
		return false;
	}

	/**
	 * This method will only be accessible if the user is logged in (handled up the
	 * app chain in a servlet probably). We're going to take the passed in Request
	 * object and pass it straight to the create method in the RequestRepository
	 * object. Then we'll return some kind of validation that the submission was
	 * successful or not. I'm currently undecided between passing back a boolean and
	 * a Request object with a 'Pending' status ID (null if unsuccessful). I may
	 * have to take in an Employee object as well, but not sure. I may be able to
	 * simply put the Employee object into the Request object further up the chain.
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

//	For now, we're going to lump pending requests with resolved ones.
	/**
	 * This method will only be accessible if the user is logged in. Thus, the
	 * passed in object should have the whole Employee object filled out. We'll use
	 * the filled in ID field to get any requests belonging to that Employee.
	 * We will give an employee the option to enter this method with his own info only,
	 * but a manager will have the option to do that and to select a different employee ID.
	 * With that ID, we'll make a new Employee object to pass into here.
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
	
//	For now, pending and resolved requests are lumped together, but this functionality will change when the project nears completion.
	/**
	 * This method will only be accessible to managers.
	 * @param employee
	 * @return
	 */
	public List<Request> managerViewRequest(Employee employee) {
		RequestRepositoryJdbc repository = new RequestRepositoryJdbc();
		List<Request> requests = new ArrayList<>();
		LOGGER.trace(
				"Calling all requests.");
		requests = repository.findAll();
		return requests;
	}
	
	/**
	 * This method will be accessible to managers only and is how they will approve/deny requests.
	 * Up the application pipeline, we'll figure out how to change only the request status ID
	 * (or we'll come back here and update the Service class).  Then we'll pass in a request object with that changed status
	 * and use that object to update the database entry for that request.
	 * We need to make sure to preserve the R_ID of the original request.
	 * @param request
	 * @return
	 */
	public boolean updateRequest(Request request) {
		LOGGER.trace("Updating request info with parameter request object: " + request);
		RequestRepositoryJdbc repository = new RequestRepositoryJdbc();
		return repository.update(request);
	}

	/**
	 * Just as before, this method is only accessible if user is logged in.
	 * @param employee
	 */
	public Employee viewInfo(Employee employee) {
		LOGGER.trace("Viewing employee info with parameter employee ID: " + employee.getEmployeeId());
		EmployeeRepositoryJdbc repository = new EmployeeRepositoryJdbc();
		return repository.findByEmployeeId(employee.getEmployeeId());
	}

	/**
	 * Up the application pipeline, we'll figure out how to change only the values we want to update
	 * (or we'll come back here and update the Service class).  Then we'll pass in an employee object with those changed values
	 * (not the user's actual employee object) and use that object to update the database entry for that user.
	 * We need to make sure to preserve the E_ID of the original user.
	 * @param employee
	 */
	public boolean updateInfo(Employee employee) {
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
		List<Employee> employees = new ArrayList<>();
		LOGGER.trace(
				"Calling all employees.");
		employees = repository.findAll();
		return employees;
	}

	public static void main(String[] args) {
		Service service = new Service();
		new Status();
		Employee testEmployee = new Employee("TestE", "password");
		System.out.println(service.managerViewEmployee(testEmployee));
//		testEmployee.setFirstName("Test");
//		testEmployee.setUsername("TestE");
//		service.updateInfo(testEmployee);

//		Request testRequest = new Request(3, new Status(2));
//		service.updateRequest(testRequest);
		
		
	}
}
