package com.balki.gamer.util;

import java.io.IOException;
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
}
