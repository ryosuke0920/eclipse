package com.example.web.dbcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



public class Main extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		out.println("<!doctype html><html><head><meta charset=\"UTF-8\"/></head><body>");
		out.println("<p>Sample</p>");
		out.println("<p>Hello World!　こんにちは！</p>");

		ArrayList<String> list = null;

		try {
			list = DBtester.main();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Iterator<String> it = list.iterator();
		out.println("<pre>");
		while(it.hasNext()){
			out.println(it.next());
		}
		out.println("</pre>");

		out.println("</body></html>");
	}
}

class DBtester {
	final static Logger logger = Logger.getLogger(DBtester.class.toString());

	public static ArrayList<String> main() throws Exception{
		//logger.setLevel(Level.ALL);
		logger.info("start main...");

		Connection conn = null;

		ArrayList<String> list = new ArrayList<String>();

		try{
			logger.info("try to connect.");

			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			conn = ds.getConnection();

			Statement statement=conn.createStatement();
			ResultSet result=statement.executeQuery("select * from sample");
			while(result.next()){
				ArrayList<String> line = new ArrayList<String>();
				for(int i=1; i<=1; i++) {
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

