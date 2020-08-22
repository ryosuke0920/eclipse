package com.example.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ReadFile {
	final static Logger logger = Logger.getLogger(ReadFile.class.toString());

	public static void main(String[] args) throws Exception {
		logger.info("Start main...");

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521";
		String user = "A1";
		String password = "password";

		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);

			CsvLoader loader = new CsvLoader();
			loader.setFileFromString(args[0]);
			loader.setConn(conn);
			loader.setSql("INSERT INTO sample (name) VALUES (?)");
			loader.readAndInsert();

			conn.commit();

		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				logger.info("Connection was closed.");
			}
		}
	}
}

class CsvLoader {
	final static Logger logger = Logger.getLogger(CsvLoader.class.toString());

	String encoding = "Windows-31J";
	String crlf = "\n";
	File file;
	Connection conn;
	String sql;

	public void setFileFromString(String str) {
		setFile(new File(str));
	}

	public void readAndInsert() {
		BufferedReader reader = null;
		try {
			logger.info("Reading file is " + file.toString());
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			String line = null;
			while ((line = reader.readLine()) != null) {
				loadedLine(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadedLine(String line) throws SQLException {
		line = line.replace(crlf, "");
		logger.info("A line is " + line);

		/* イベントハンドラにしたい */
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1,line);
		int i = ps.executeUpdate();
		logger.info(i + " rows affected.");
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}