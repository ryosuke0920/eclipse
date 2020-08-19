package com.example.db.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class.toString());

	public static void main(String[] args) throws Exception{
		//logger.setLevel(Level.ALL);
		logger.info("start main...");

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521";
		String user = "hr";
		String password = "password";

		try{
			logger.info("try to connect.");
			conn = DriverManager.getConnection(url, user, password);
			Statement statement=conn.createStatement();
			ResultSet result=statement.executeQuery("select EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER,"
					+ " HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID from employees");
			while(result.next()){
				ArrayList<String> list = new ArrayList<String>();
				for(int i=1; i<=11; i++) {
					list.add(result.getString(i));
				}
				logger.info(list.toString());
			}
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			if(conn != null && !conn.isClosed()) {
				logger.info("try to close.");
				conn.close();
			}
		}
		logger.info("end of main.");
	}
}
