package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

public interface ModelController {	
	
	/**
	 * Get an employee in the database based on a parameter.
	 */
	public Object getEmployee(HttpServletRequest request);
	
	/**
	 * Get all employees in the database.
	 * 
	 * -> If it's GET with no parameters, then we return the view.
	 * -> If it's GET with a parameter, then we return the list of users.
	 */
	public Object getAllEmployees(HttpServletRequest request);
	
	/**
	 * Update an employee in the database.
	 * Return a string stating whether the update was successful or not. 
	 */
	public String updateEmployee(HttpServletRequest request);
	
	/**
	 * Get requests in the database based on a parameter.
	 */
	public Object getRequests(HttpServletRequest request);
	
	/**
	 * Get all requests in the database.
	 * 
	 * -> If it's GET with no parameters, then we return the view.
	 * -> If it's GET with a parameter, then we return the list of parameters.
	 */
	public Object getAllRequests(HttpServletRequest request);
	
	/**
	 * Enter a request into the database.
	 * Return a string stating whether the new entry was successful or not. 
	 */
	public String submitRequest(HttpServletRequest request);
	
	/**
	 * Update a request (the status) in the database.
	 * Return a string stating whether the update was successful or not. 
	 */
	public String updateRequest(HttpServletRequest request);	

}
