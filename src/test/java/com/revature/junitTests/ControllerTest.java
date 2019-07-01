package com.revature.junitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.model.Employee;
import com.revature.service.Service;
import com.revature.util.ConnectionUtil;

public class ControllerTest {

	private static final Service controllerService = Service.getInstance();

	Employee user = new Employee();

	Scanner scanner = new Scanner(System.in);

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Connection test
	 * @throws SQLException 
	 */
	@Test
	public void connectionTest() throws SQLException {
		System.out.println("Input username.");
		final String username = scanner.nextLine();
		System.out.println("Input password.");
		final String password = scanner.nextLine();
		if (username.equals("TEST_USER") && password.equals("p4ssw0rd")) {
			ExpectedException.none();
			ConnectionUtil.getConnection(username, password);
		} else {
			expectedException.expect(SQLException.class);
			ConnectionUtil.getConnection(username, password);
		}
	}
	
	/**
	 * Login test
	 */
	@Test
	public void loginTest() {
		controllerService.login(user, scanner);
		if (user.getUsername().equals("TEST_USER") && user.getPassword().equals("p4ssw0rd")) {
			assertTrue(user.isLoginStatus());
		} else {
			assertFalse(user.isLoginStatus());
		}		
	}
	
	@Test
	public void logoutTest() {
		controllerService.logout(user);
		assertFalse(user.isLoginStatus());
	}
}
