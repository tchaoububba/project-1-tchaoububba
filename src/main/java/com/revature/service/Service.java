package com.revature.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.BalanceTooSmallException;
import com.revature.exception.WrongUsernameOrPasswordException;
import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;

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

	public void login(Employee user, Scanner scanner) {
//		Check if user is already logged in
		if (user.isLoginStatus()) {
			System.out.println(
					"You are already logged in.  Please logout if you are attempting to login as someone else.");
		} else {
//			Code to log in here
			System.out.println("Please enter your username.");
			String username = scanner.nextLine();
			System.out.println("Please enter your password.");
			String password = scanner.nextLine();
			user.setUsername(username);
			user.setPassword(password);
//			Insert scanned username + password into ConnectionDriver info.
			LOGGER.trace("Entering login method with parameters: " + username + ", " + password);
			try (Connection connection = ConnectionUtil.getConnection(username, password)) {
				if (connection.isValid(0)) {
					user.setLoginStatus(true);
				} else {
					user.setLoginStatus(false);
				}
			} catch (SQLException e) {
				LOGGER.error("Could not login.", e);
			}
			if (user.isLoginStatus()) {
				System.out.println("Welcome " + user.getUsername() + "!");
			} else {
				try {
					throw new WrongUsernameOrPasswordException();
				} catch (WrongUsernameOrPasswordException e) {
					System.out.println("Wrong username and/or password was given. Please try again.");
				}
			}
		}
	}

	public void logout(Employee user) {
//		Code to log out here
		if (user.isLoginStatus()) {
			user.setLoginStatus(false);
			System.out.println("You have successfully logged out!");
		} else {
			System.out.println("You are not logged in.");
		}
	}

	public void viewBalance(Employee user) {
//		Code to print out the balance here
		if (user.isLoginStatus()) {
			LOGGER.trace("Getting connection with user parameters: " + user.getUsername() + ", " + user.getPassword());
			try (Connection connection = ConnectionUtil.getConnection(user.getUsername(), user.getPassword())) {
				String sql = "SELECT * FROM BANK_ACCOUNT";

				PreparedStatement statement = connection.prepareStatement(sql);

				ResultSet result = statement.executeQuery();

				if (result.next()) {
					double balance = result.getDouble("BALANCE");
					user.setBalance(balance);
					System.out.println("Your balance is $" + user.getBalance());
				}
			} catch (SQLException e) {
				LOGGER.error("Could not find balance.", e);
			}
		} else {
			System.out.println("You are not logged in. You must login before you may view or update your balance.");
		}
	}

//	Look for a way to check the money for no more than 2 decimal places if we have time.
	public void depositMoney(Employee user, Scanner scanner) {
//		Code to deposit money here
		double depositAmount = 0;
		if (user.isLoginStatus()) {
			viewBalance(user);
			System.out.println("How much money would you like to deposit?");
			try {
				depositAmount = scanner.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("A valid number was not received. Please enter your deposit amount as a number.");
			}
			if (depositAmount >= 0) {
				LOGGER.trace("Entering deposit method with parameters: balance = " + user.getBalance() + ", deposit = " + depositAmount);
				user.setBalance((user.getBalance() + depositAmount));
				LOGGER.trace("Getting connection with user parameters: " + user.getUsername() + ", " + user.getPassword());
				try (Connection connection = ConnectionUtil.getConnection(user.getUsername(), user.getPassword())) {
					String sql = "UPDATE bank_account SET BALANCE = ?";

					PreparedStatement statement = connection.prepareStatement(sql);
					LOGGER.info("Amount to be updated to: " + user.getBalance());
					statement.setDouble(1, user.getBalance());
					statement.executeUpdate();

				} catch (SQLException e) {
					LOGGER.error("Could not set balance.", e);
					System.out.println("There was an error in depositing your money.");
					return;
				}

				System.out.println("You have deposited $" + depositAmount + ".");
				viewBalance(user);
				scanner.nextLine();
			} else {
				depositAmount = 0;
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("A valid number was not received.  You must enter a positive amount to deposit.");
					scanner.nextLine();
				}
			}
		} else {
			System.out.println("You are not logged in. You must login before you may view or update your balance.");
		}
	}

//	Look for a way to check the money for no more than 2 decimal places if we have time.
	public void withdrawMoney(Employee user, Scanner scanner) {
//		Code to withdraw money here
		double withdrawAmount = 0;
		if (user.isLoginStatus()) {
			viewBalance(user);
			System.out.println("How much money would you like to withdraw?");
			try {
				withdrawAmount = scanner.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("A valid number was not received. Please enter your deposit amount as a number.");
			}
			if (withdrawAmount >= 0 && withdrawAmount <= user.getBalance()) {
				LOGGER.trace("Entering withdraw method with parameters: balance = " + user.getBalance() + ", withdraw = " + withdrawAmount);
				user.setBalance((user.getBalance() - withdrawAmount));
				LOGGER.trace("Getting connection with user parameters: " + user.getUsername() + ", " + user.getPassword());
				try (Connection connection = ConnectionUtil.getConnection(user.getUsername(), user.getPassword())) {
					String sql = "UPDATE bank_account SET BALANCE = ?";

					PreparedStatement statement = connection.prepareStatement(sql);
					LOGGER.info("Amount to be updated to: " + user.getBalance());
					statement.setDouble(1, user.getBalance());
					statement.executeUpdate();

				} catch (SQLException e) {
					LOGGER.error("Could not set balance.", e);
					System.out.println("There was an error in withdrawing your money.");
					return;
				}

				System.out.println("You have withdrawn $" + withdrawAmount + ".");
				viewBalance(user);
				scanner.nextLine();
			} else if (withdrawAmount < 0) {
				withdrawAmount = 0;
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("A valid number was not received.  You must enter a positive amount to withdraw.");
					scanner.nextLine();
				}
			} else {
				withdrawAmount = 0;
				try {
					throw new BalanceTooSmallException();
				} catch (BalanceTooSmallException e) {
					System.out.println("You cannot withdraw an amount larger than your available balance.");
					scanner.nextLine();
				}
			}
		} else {
			System.out.println("You are not logged in. You must login before you may view or update your balance.");
		}
	}
	
	public void register(Scanner scanner) {
		LOGGER.trace("Entering register method");
		String username;
		String password, passwordCheck;
		System.out.println("Please enter your desired username.");
		username = scanner.nextLine();
		if (username.length() <= 10) {
			System.out.println("Please enter your desired password.");
			password = scanner.nextLine();
			System.out.println("Please re-enter your desired password.");
			passwordCheck = scanner.nextLine();
			if (password.equals(passwordCheck)) {
				LOGGER.trace("Getting connection with admin parameters");
				try (Connection connection = ConnectionUtil.getConnection()) {
					String sql = "CREATE USER " + username + " IDENTIFIED BY " + password;
					PreparedStatement statement = connection.prepareStatement(sql);
					LOGGER.info("Creating user account with parameters: " + username + ", " + password);
					statement.executeUpdate();
//					Granting admin options
					String sql2 = "GRANT DBA TO " + username + " WITH ADMIN OPTION";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					LOGGER.info("Granting admin options to created account (for purpose of testing)");
					statement2.executeUpdate();

				} catch (SQLException e) {
					LOGGER.error("Could not create user account.", e);
					System.out.println("There was an error in registering your account.");
					return;
				}
				System.out.println("Account created!");
				try (Connection connection = ConnectionUtil.getConnection(username, password)){
//					Creating Bank Account Table
					String sql3 = "CREATE TABLE BANK_ACCOUNT (BALANCE NUMBER)";
					PreparedStatement statement3 = connection.prepareStatement(sql3);
					LOGGER.info("Creating test bank account table");
					statement3.executeUpdate();
					String sql4 = "INSERT INTO bank_account VALUES(0)";
					PreparedStatement statement4 = connection.prepareStatement(sql4);
					statement4.executeUpdate();
				} catch (SQLException e) {
					LOGGER.error("Could not log into user account.", e);
					System.out.println("There was an error in logging into your registered account.");
					return;
				}
			} else {
				System.out.println("Please ensure your re-entered password matches exactly what you typed in the first time.");
			}
		} else {
			System.out.println("Please restrict your username to 10 alphanumeric characters.");
		}
	}
}
