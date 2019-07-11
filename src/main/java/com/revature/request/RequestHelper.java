package com.revature.request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.controller.EmployeeController;
import com.revature.controller.LoginController;
import com.revature.zjsonexperiment.JacksonPojoToJson;

public class RequestHelper {
	private static final Logger LOGGER = Logger.getLogger(RequestHelper.class);

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
		LOGGER.trace("RequestHelper process method");
		switch(request.getRequestURI()) {
		case "/ReimbursementProject/login.do":
			return LoginController.login(request);
		case "/ReimbursementProject/logout.do":
			return LoginController.logout(request);
		case "/ReimbursementProject/home.do":
			return LoginController.viewHome(request);
////	"/FrontController/register.do"???
//		case "/register.do":
//			return CustomerControllerAlpha.getInstance().register(request);
//		"/FrontController/getAllEmployees.do"???
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
		case "/ReimbursementProject/test.do":
			try {
				JacksonPojoToJson.createEmployeeJsonFile(request);;
			} catch (IOException e) {
				LOGGER.error(e);
			}
			return "TestAttempted2.html";
		default:
			//We should return a 404 view here
			return "404.html";
		}
	}
}
