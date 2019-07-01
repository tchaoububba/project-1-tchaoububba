package com.revature.repository;

import java.util.List;

import com.revature.model.Employee;

/**
 * 
 * This is the Employee DAO (Data Access Object).
 * 
 * -> Defines CRUD operations for this particular POJO
 * -> NO BUSINESS LOGIC SHOULD BE PRESENT on these kinds of Objects.
 *
 */
public interface EmployeeRepository {
	
	/**Will insert an employee into the database table, but using a stored procedure
	 * 
	 * @param employee
	 * @return if the record was inserted
	 */
	public boolean create(Employee employee);
	
	/**
	 * Will insert an employee into the database table, but using a stored procedure
	 * 
	 * @param employee
	 * @return if the record was inserted
	 */
	public boolean createSecure(Employee employee);
	
	public Employee findByEmployeeId(long employeeId);
	
	public List<Employee> findByLastName(String lastName);
	
	public List<Employee> findAll();

}
