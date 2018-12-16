package com.balki.gamer.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 
 * @author Balki
 * @since 15/12/2018
 *
 */
public class FileManager {
	public static void log(String fileName, String text) {
	    Path path = Paths.get(fileName);
	    if(!Files.exists(path)) {
	    	try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    else {
	    	text = "\n" + text;
	    }
	    
	    byte[] strToBytes = text.getBytes();
	 
	    try {
			Files.write(path, strToBytes, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String read(String fileName, int lineNumber) throws IOException, InterruptedException {
        String line;
        int number = 0;
	    Path path = Paths.get(fileName);
	    if(!Files.exists(path)) {
            Thread.sleep(3000);
            return read(fileName, lineNumber);
	    }
	    
        LineNumberReader lnr = new LineNumberReader(new FileReader(fileName));
        while (true)
        {
            line = lnr.readLine();
            if (line == null) {
                System.out.println("waiting 4 more");
                Thread.sleep(3000);
                continue;
            }
            
            number++;
            if(lineNumber == number) {
            	lnr.close();
            	return line;
            }
            else {
                System.out.println("waiting 4 more");
                Thread.sleep(3000);
                continue;
            }
        }
	}
}
