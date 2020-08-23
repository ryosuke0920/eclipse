package com.example.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class ReadFile {
	final static Logger logger = Logger.getLogger(ReadFile.class.toString());
	static Connection conn = null;

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
			System.exit(1);
		}

		Class.forName(driver);

		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);

			CsvReader reader = new CsvReader();
			reader.setFileFromString(filepath);
			reader.registerLineReadListener(new LineReadListener() {
				@Override
				public void onLineRead(String line) {
					logger.info("onLinedRead: " + line);
					try {
						PreparedStatement ps = conn.prepareStatement("INSERT INTO sample (name) VALUES (?)");
						ps.setString(1, line);
						int i = ps.executeUpdate();
						logger.info(i + " rows affected.");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			reader.registerLineReadListener(line -> {
				logger.info("test call...");
			});
			reader.read();

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

interface LineReadListener {
	public void onLineRead(String line);

}

class CsvReader {
	final static Logger logger = Logger.getLogger(CsvReader.class.toString());
	String encoding = "Windows-31J";
	String crlf = "\n";

	File file;

	private List<LineReadListener> listeners = new ArrayList<>();

	public void setFileFromString(String str) {
		setFile(new File(str));
	}

	public void read() {
		BufferedReader reader = null;
		try {
			logger.info("Reading file is " + file.toString());
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			String line = null;
			while ((line = reader.readLine()) != null) {
				notifyLineReadListener(convertLine(line));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String convertLine(String line) {
		return line.replace(crlf, "");
	}

	public void registerLineReadListener(LineReadListener listener) {
		this.listeners.add(listener);
	}

	protected void notifyLineReadListener(String line) {
		this.listeners.forEach(listener -> listener.onLineRead(line));
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}