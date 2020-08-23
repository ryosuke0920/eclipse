package com.example.web.dynamic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> list = null;
		try{
			list = DBtester.main();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!doctype html><html><head><meta charset=\"UTF-8\"></head><body>");
		out.println("<p>Sample</p>");
		out.println("<p>Hello World!　こんにちは！</p>");
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			out.println(it.next() + "<br />");
		}
		out.println("</body></html>");
	}
}

class DBtester {
	final static Logger logger = Logger.getLogger(DBtester.class.toString());

	public static ArrayList<String> main() throws Exception{
		//logger.setLevel(Level.ALL);
		logger.info("start main...");

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521";
		String user = "hr";
		String password = "password";

		ArrayList<String> list = new ArrayList<String>();

		try{
			logger.info("try to connect.");
			conn = DriverManager.getConnection(url, user, password);
			Statement statement=conn.createStatement();
			ResultSet result=statement.executeQuery("select EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER,"
					+ " HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID from employees");
			while(result.next()){
				ArrayList<String> line = new ArrayList<String>();
				for(int i=1; i<=11; i++) {
					line.add(result.getString(i));
				}
				list.add(line.toString());
				//logger.info(list.toString());
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
		return list;
	}
}

