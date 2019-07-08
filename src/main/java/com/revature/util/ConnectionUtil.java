package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class ConnectionUtil {
	//REMEMBER TO USE THIS INSTEAD OF SYSOUT
	private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);
	
	/* Make Tomcat know which database driver to use */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			LOGGER.warn("Exception thrown adding oracle driver.", e);
		}
	}

	
	//static method so you only have to connect and use connection parameters once
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@mydbinstance.ceamzblcgdau.us-east-1.rds.amazonaws.com:1521:ORCL";
		String username = "TEST_USER";
		String password = "p4ssw0rd";
		
		return DriverManager.getConnection(url, username, password);
		
	}
	
	public static void main(String[] args) {
		try {
			getConnection();
			LOGGER.info("Connection successful");
		} catch (SQLException e) {
			LOGGER.error("Could not connect", e);
		}
	}
}
