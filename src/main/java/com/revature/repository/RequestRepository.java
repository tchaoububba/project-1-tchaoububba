package com.revature.repository;

import java.util.List;

import com.revature.model.Request;

/**
 * 
 * This is the Request DAO (Data Access Object).
 * 
 * -> Defines CRUD operations for this particular POJO
 * -> NO BUSINESS LOGIC SHOULD BE PRESENT on these kinds of Objects.
 *
 */
public interface RequestRepository {
	
	/**Will insert a request into the database table, but using a stored procedure
	 * 
	 * @param request
	 * @return if the record was inserted
	 */
	public boolean create(Request request);
	
	/**
	 * Will insert a request into the database table, but using a stored procedure
	 * 
	 * @param request
	 * @return if the record was inserted
	 */
	public boolean createSecure(Request request);
	
	public Request findByRequestId(long requestId);
	
	public List<Request> findByEmployeeId(long employeeId);
	
	public List<Request> findAll();

}
