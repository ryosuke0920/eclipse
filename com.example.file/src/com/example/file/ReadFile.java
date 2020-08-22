package com.example.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class ReadFile {
	final static Logger logger = Logger.getLogger(ReadFile.class.toString());

	public static void main(String[] args) throws Exception {
		String driver = args[0];
		String url = args[1];
		String user = args[2];
		String password = args[3];
		String filepath = args[4];

		System.out.println("Driver: " + driver);
		System.out.println("URL: " + url);
		System.out.println("User: " + user);
		System.out.println("Password: " + password);
		System.out.println("OK? Y/n");

		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		scan.close();
		if (!Objects.equals(str, "Y")) {
			System.out.println("Canceled.");
			System.exit(0);
		}

		Class.forName(driver);
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);

			CsvLoader loader = new CsvLoader();
			loader.setFileFromString(filepath);
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
		ps.setString(1, line);
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