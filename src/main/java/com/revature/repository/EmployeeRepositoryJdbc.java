package com.revature.repository;

//--EMPLOYEE----------------------------------------
//--Name       Null?    Type          
//------------ -------- ------------- 
//--E_ID       NOT NULL NUMBER        
//--FIRST_NAME NOT NULL VARCHAR2(100) 
//--LAST_NAME  NOT NULL VARCHAR2(100) 
//--USERNAME   NOT NULL VARCHAR2(100) 
//--PASSWORD   NOT NULL VARCHAR2(100) 
//--T_ID       NOT NULL NUMBER

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Title;
import com.revature.util.ConnectionUtil;

public class EmployeeRepositoryJdbc implements EmployeeRepository {
	
	private static final Logger LOGGER = Logger.getLogger(EmployeeRepositoryJdbc.class);

	//We'll make it so only managers can use this method to register employees.
	@Override
	public boolean create(Employee employee) {
		LOGGER.trace("Entering create method with parameter: " + employee);
		//This is try-with-resources, so we don't need a finally block
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			String sql = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(++parameterIndex, employee.getEmployeeId());
			statement.setString(++parameterIndex, employee.getFirstName());
			statement.setString(++parameterIndex, employee.getLastName());
			statement.setString(++parameterIndex, employee.getUsername());
			statement.setString(++parameterIndex, employee.getPassword());
			statement.setLong(++parameterIndex, employee.getTitle().getTitleId());
			
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error("Could not create employee.", e);
		}
		return false;
	}

	@Override
	public boolean createSecure(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Employee findByEmployeeId(long employeeId) {
		LOGGER.trace("Entering find employee by employee ID method with parameter: " + employeeId);
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM employee WHERE E_ID = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(++parameterIndex, employeeId);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				Employee employee = new Employee(
						result.getLong("E_ID"),
						result.getString("FIRST_NAME"),
						result.getString("LAST_NAME"),
						result.getString("USERNAME"),
						result.getString("PASSWORD"),
						new Title(result.getLong("T_ID"))
						);

				return employee;
			}
		} catch (SQLException e) {
			LOGGER.error("Could not find employee.", e);
		}
		return null;
	}

	@Override
	public List<Employee> findByLastName(String lastName) {
		LOGGER.trace("Entering find employee by last name method with parameter: " + lastName);
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM employee WHERE LAST_NAME = ? ORDER BY E_ID";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, lastName);
			
			ResultSet result = statement.executeQuery();
			
			List<Employee> employees = new ArrayList<>();
			
			while(result.next()) {
				employees.add(new Employee (
						result.getLong("E_ID"),
						result.getString("FIRST_NAME"),
						result.getString("LAST_NAME"),
						result.getString("USERNAME"),
						result.getString("PASSWORD"),
						new Title(result.getLong("T_ID"))
						));
			}

			return employees;
		} catch (SQLException e) {
			LOGGER.error("Could not find employees.", e);
		}
		return null;
	}

	@Override
	public List<Employee> findAll() {
		LOGGER.trace("Entering finding all employees");
		try(Connection connection = ConnectionUtil.getConnection()) {
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM employee ORDER BY E_ID";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			List<Employee> employees = new ArrayList<>();
			
			while(result.next()) {
				employees.add(new Employee (
						result.getLong("E_ID"),
						result.getString("FIRST_NAME"),
						result.getString("LAST_NAME"),
						result.getString("USERNAME"),
						result.getString("PASSWORD"),
						new Title(result.getLong("T_ID"))
						));
			}

			return employees;
		} catch (SQLException e) {
			LOGGER.error("Could not find all employees.", e);
		}
		return null;
	}
	
	@Override
	public Employee update(Employee employee) {
		LOGGER.trace("Entering update employee method");
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "UPDATE employee SET FIRST_NAME = ?, LAST_NAME = ? WHERE E_ID = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(++parameterIndex, employee.getFirstName());
			statement.setString(++parameterIndex, employee.getLastName());
//			statement.setString(++parameterIndex, employee.getUsername());
//			statement.setString(++parameterIndex, employee.getPassword());
			statement.setLong(++parameterIndex, employee.getEmployeeId());
			
			if (statement.executeUpdate() > 0) {
				LOGGER.info("Update successful!");
				return employee;
			}
		} catch (SQLException e) {
			LOGGER.error("Could not update employee.", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		EmployeeRepositoryJdbc repository = new EmployeeRepositoryJdbc();
		List<Employee> employees = new ArrayList<>();
		employees = repository.findAll();
		System.out.println(employees);
	}
}
