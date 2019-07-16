package com.revature.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.controller.EmployeeController;
import com.revature.controller.LoginController;

public class RequestHelper {
	private static final Logger LOGGER = Logger.getLogger(RequestHelper.class);
	private RequestHelper() {}

	public static Object process(HttpServletRequest request) {
//		Login Controller (Static Methods)
		LOGGER.trace("RequestHelper process method");
		switch(request.getRequestURI()) {
		case "/ReimbursementProject/login.do":
			return LoginController.login(request);
		case "/ReimbursementProject/logout.do":
			return LoginController.logout(request);
		case "/ReimbursementProject/home.do":
			return LoginController.viewHome(request);
		case "/ReimbursementProject/giveLoggedEmployee.do":
			return LoginController.giveLoggedEmployee(request);

//		Employee Controller (Single Instance)
		case "/ReimbursementProject/getEmployee.do":
			return EmployeeController.getInstance().getEmployee(request);
		case "/ReimbursementProject/getAllEmployees.do":
			return EmployeeController.getInstance().getAllEmployees(request);
		case "/ReimbursementProject/updateEmployee.do":
			return EmployeeController.getInstance().updateEmployee(request);
		case "/ReimbursementProject/getRequests.do":
			return EmployeeController.getInstance().getRequests(request);
		case "/ReimbursementProject/getAllRequests.do":
			return EmployeeController.getInstance().getAllRequests(request);
		case "/ReimbursementProject/submitRequest.do":
			return EmployeeController.getInstance().submitRequest(request);
		case "/ReimbursementProject/updateRequest.do":
			return EmployeeController.getInstance().updateRequest(request);
//		case "/ReimbursementProject/test.do":
		default:
			//We should return a 404 view here
			return "404.html";
		}
	}
}
