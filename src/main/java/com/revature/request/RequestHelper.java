package com.revature.request;

import javax.servlet.http.HttpServletRequest;

import com.revature.controller.EmployeeController;
import com.revature.controller.LoginController;

public class RequestHelper {

//	public static Object process(HttpServletRequest request) {
//		switch(request.getRequestURI()) {
//		case "/FrontController/getAll.do":
//			return CustomerController.getAllCustomers(request);
//		case "/FrontController/register.do":
//			return CustomerController.register(request);
//		default:
//			//We should return a 404 view here
//			return null;
//		}
//	}
//	
	private RequestHelper() {}

	public static Object process(HttpServletRequest request) {
		switch(request.getRequestURI()) {
		case "/login.do":
			return LoginController.login(request);
		case "/logout.do":
			return LoginController.logout(request);
		case "/viewHome.do":
			return LoginController.viewHome(request);
////	"/FrontController/register.do"???
//		case "/register.do":
//			return CustomerControllerAlpha.getInstance().register(request);
//		"/FrontController/getAll.do"???
		case "getEmployee.do":
			return EmployeeController.getInstance().getEmployee(request);
		case "/getAllEmployees.do":
			return EmployeeController.getInstance().getAllEmployees(request);
		case "updateEmployee.do":
			return EmployeeController.getInstance().updateEmployee(request);
		case "getRequests.do":
			return EmployeeController.getInstance().getRequests(request);
		case "getAllRequests.do":
			return EmployeeController.getInstance().getAllRequests(request);
		case "submitRequest.do":
			return EmployeeController.getInstance().submitRequest(request);
		case "updateRequest.do":
			return EmployeeController.getInstance().updateRequest(request);
		default:
			//We should return a 404 view here
			return "Invalid.html";
		}
	}
}
