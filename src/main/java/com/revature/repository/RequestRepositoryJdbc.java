package com.revature.repository;

//--REQUEST----------------------------------------
//--Name   Null?    Type          
//-------- -------- ------------- 
//--R_ID   NOT NULL NUMBER        
//--E_ID   NOT NULL NUMBER        
//--R_BODY          VARCHAR2(200) 
//--S_ID   NOT NULL NUMBER

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Request;
import com.revature.model.Status;
import com.revature.util.ConnectionUtil;

public class RequestRepositoryJdbc implements RequestRepository {
	
	private static final Logger LOGGER = Logger.getLogger(RequestRepositoryJdbc.class);

	@Override
	public boolean create(Request request) {
		LOGGER.trace("Entering create method with parameter: " + request);
		//This is try-with-resources, so we don't need a finally block
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			String sql = "INSERT INTO request VALUES (NULL, ?, ?, 1)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setLong(++parameterIndex, request.getRequestId());
			statement.setLong(++parameterIndex, request.getEmployee().getEmployeeId());
			statement.setString(++parameterIndex, request.getRequestBody());
//			statement.setLong(++parameterIndex, request.getStatus().getStatusId());
			
			if (statement.executeUpdate() > 0) {
				LOGGER.info("Creation successful!");
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error("Could not create request.", e);
		}
		return false;
	}

	@Override
	public boolean createSecure(Request request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Request findByRequestId(long requestId) {
		LOGGER.trace("Entering find request by request ID method with parameter: " + requestId);
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM request WHERE R_ID = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(++parameterIndex, requestId);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				Request request = new Request(
						result.getLong("R_ID"),
						new Employee(result.getLong("E_ID")),
						result.getString("R_BODY"),
						new Status(result.getLong("S_ID"), "")
						);
				if (request.getStatus().getStatusId() == 3) {
					request.getStatus().setStatusName("DENIED");
				} else if (request.getStatus().getStatusId() == 2) {
					request.getStatus().setStatusName("APPROVED");
				} else {
					request.getStatus().setStatusName("PENDING");
				}
				return request;
			}
		} catch (SQLException e) {
			LOGGER.error("Could not find request.", e);
		}
		return null;
	}

	@Override
	public List<Request> findByEmployeeId(long employeeId) {
		LOGGER.trace("Entering find request by employee ID method with parameter: " + employeeId);
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM request WHERE E_ID = ? ORDER BY R_ID";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(++parameterIndex, employeeId);
			
			ResultSet result = statement.executeQuery();
			
			List<Request> requests = new ArrayList<>();
			
			while(result.next()) {
				requests.add(new Request (
						result.getLong("R_ID"),
						new Employee(result.getLong("E_ID")),
						result.getString("R_BODY"),
						new Status(result.getLong("S_ID"))
						));
			}

			LOGGER.trace("Requests list being sent from repository: " + requests);
			return requests;
		} catch (SQLException e) {
			LOGGER.error("Could not find requests.", e);
		}
		return null;
	}

	@Override
	public List<Request> findAll() {
		LOGGER.trace("Entering finding all requests");
		try(Connection connection = ConnectionUtil.getConnection()) {
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM request ORDER BY R_ID";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery();
			
			List<Request> requests = new ArrayList<>();
			
			while(result.next()) {
				requests.add(new Request (
						result.getLong("R_ID"),
						new Employee(result.getLong("E_ID")),
						result.getString("R_BODY"),
						new Status(result.getLong("S_ID"))
						));
			}

			return requests;
		} catch (SQLException e) {
			LOGGER.error("Could not find all requests.", e);
		}
		return null;
	}
	
	@Override
	public boolean update(Request request) {
		LOGGER.trace("Entering update request method");
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "UPDATE request SET S_ID = ? WHERE R_ID = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setLong(++parameterIndex, request.getStatus().getStatusId());
			statement.setLong(++parameterIndex, request.getRequestId());
			
			if (statement.executeUpdate() > 0) {
				LOGGER.info("Update successful!");
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error("Could not update request.", e);
		}
		return false;
	}
}
