package com.model.dutta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	//private static final String USERNAME = "root";
	//private static final String PASSWORD = "bad123";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN_STRING = "jdbc:mysql://localhost/duttadb";
	//private static final String CONN_STRING = "jdbc:mysql://localhost:3306/duttadb";
	//

//	 private static final String USERNAME = "root";
//	 private static final String PASSWORD = "admin4al";
//	 private static final String CONN_STRING =
//	 "jdbc:mysql://103.240.248.244/duttadb";

	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

	}

	public static void processException(SQLException e) {
		System.out.println("Error Message : " + e.getMessage());
		System.out.println("Error Code : " + e.getErrorCode());
		System.out.println("SQL state : " + e.getSQLState());
	}

}
