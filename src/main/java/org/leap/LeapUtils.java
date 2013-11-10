package org.leap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LeapUtils {

	// read the generated file and return byte[]
	public static byte[] readFileInBytes(File file) throws IOException {
		byte[] result = null;
		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException("Cannot find the file on path:"+ file.getAbsolutePath());
		}

		FileInputStream inputStream = new FileInputStream(file);
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			while (-1 != (bytesRead = inputStream.read(buffer))) {
				bos.write(buffer, 0, bytesRead);
			}
			result = bos.toByteArray();
		} finally {
			inputStream.close();
		}
		return result;
	}
	
	// simple helper method to copy a file to another file
	public static void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(fromFile);
        FileOutputStream fileOutputStream = new FileOutputStream(toFile);
        
        int bufferSize;
        byte[] bufffer = new byte[1024];
        while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
            fileOutputStream.write(bufffer, 0, bufferSize);
        }
        fileInputStream.close();
        fileOutputStream.close();
	}
	
	// delete a folder recursively
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for (String name : files) {
					File fileToDelete = new File(file, name);
					// delete file recursively
					deleteFile(fileToDelete);
				}
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {	// this is a file
			file.delete();
		}
	}
}
