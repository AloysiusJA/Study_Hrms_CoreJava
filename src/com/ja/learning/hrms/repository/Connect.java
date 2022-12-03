package com.ja.learning.hrms.repository;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;

import com.ja.learning.hrms.constants.Constants;


public class Connect {
	static Connection getConnection() throws SQLException, ClassNotFoundException {
			Class.forName(Constants.DB_DRIVER);
			Connection con = (Connection)DriverManager.getConnection(Constants.DB_URL,Constants.DB_USERNAME,Constants.DB_PASSWORD);
			return con;
	}
}