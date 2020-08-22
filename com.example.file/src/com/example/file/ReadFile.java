package com.example.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Logger;

public class ReadFile {
	final static Logger logger = Logger.getLogger(ReadFile.class.toString());

	public static void main(String[] args) {
		logger.info("strat");

		String path = System.getProperty("user.dir");
		logger.info(path);

		File file = null;
		/*
		file = new File("C:\\Eclilpse\\pleiades-e4.5-java_20160312\\workspace\\com.example.file\\resource\\sample_sjis.csv");
		read(file, "Windows-31J");

		file = new File("C:\\Eclilpse\\pleiades-e4.5-java_20160312\\workspace\\com.example.file\\resource\\sample_utf8.csv");
		read(file, "UTF-8");
		*/
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
		Iterator<String> it = argsList.iterator();
		while(it.hasNext()){
			String str = it.next();
			file = new File(str);
			if(! file.exists()) continue;
			logger.info( "file is exists.(" + file.getAbsolutePath() + ")" );
			read(file, "Windows-31J");
		}
	}

	public static void read(File file, String encording) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encording));
			String line = null;
			while((line = reader.readLine()) != null){
				logger.info(line);
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}
}
