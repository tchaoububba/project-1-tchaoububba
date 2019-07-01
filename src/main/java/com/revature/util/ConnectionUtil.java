package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class ConnectionUtil {
	//REMEMBER TO USE THIS INSTEAD OF SYSOUT
	private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);
	
	//static method so you only have to connect and use connection parameters once
	public static Connection getConnection(String username, String password) throws SQLException {
		String url = "jdbc:oracle:thin:@mydbinstance.ceamzblcgdau.us-east-1.rds.amazonaws.com:1521:ORCL";
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@mydbinstance.ceamzblcgdau.us-east-1.rds.amazonaws.com:1521:ORCL";
		String username = "tchaoububba";
		String password = "St042192";
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			getConnection(scanner.nextLine(), scanner.nextLine());
			LOGGER.info("Connection succesful");
		} catch (SQLException e) {
			LOGGER.error("Could not connect", e);
		}
		scanner.close();
	}
}
