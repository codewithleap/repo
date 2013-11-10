package org.leap;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.tools.ant.BuildException;


public class MetaDiffTask extends LeapTask {
    int numCopied = 0;
    
	String srcFolder = null;
    public void setSrcFolder(String path) {
    	srcFolder = path;
    }
    
    String destFolder = null;
    public void setDestFolder(String path) {
    	destFolder = path;
    }
    
    String outFolder = null;
    public void setOutFolder(String path) {
    	outFolder = path;
    }
    
	public void execute() {
		this.validateConnectionParams();
		
		// validate input folder names
    	if(this.srcFolder == null){
    		throw new BuildException("Missing srcFolder param");
    	}
     	if(this.destFolder == null){
    		throw new BuildException("Missing destFolder param");
    	}
     	if(this.outFolder == null){
    		throw new BuildException("Missing outFolder param");
    	}
     	
		File srcFile = new File(srcFolder);
		File destFile = new File(destFolder);
		File outFile = new File(outFolder);
		
		if (!srcFile.exists()) {
			throw new BuildException("The srcFolder does not exisit");
		}
		
		// clear out folder, prompt if exist
		if (outFile.exists() && outFile.list().length > 0) {
			Console console = System.console();
			String input = console.readLine("The outFolder is not empty, do you want to delete? Answer yes or no:");
			if (input.equalsIgnoreCase("yes")) {
				LeapUtils.deleteFile(outFile);
			} else {
				System.out.println("Can not perform leapmetadiff task, exiting");
				return;
			}
		}
		
		// create out folder
		outFile.mkdirs();
		
		try {
			// traverse srcFolder recursively and copy a file to out folder
			traverseFileRecursively(srcFile, destFile, outFile);
		} catch (IOException e) {
			throw new BuildException("Got an exception while traversing src folder, "+e.getMessage());
		}
		
		System.out.println("Done. Copied "+numCopied+" files.");
	}
	
	private void traverseFileRecursively(File srcFile, File destFile, File outFile) throws IOException {
		
		if (srcFile.isDirectory()) {
			
			File allFiles[] = srcFile.listFiles();
			for (File aFile : allFiles) {
				
				String name = aFile.getName();
				
				// moving down in the dest/out folder hierarchy as src folder moves down to child folder or file
				destFile = new File(destFile, name);
				outFile = new File(outFile, name);
				
				traverseFileRecursively(aFile, destFile, outFile);
				
				// moving up in dest/out folder hierarchy as src folder moves up to the parent
				destFile = destFile.getParentFile();
				outFile = outFile.getParentFile();
			}
		} else if (srcFile.isFile()) {
			
			if (destFile.exists()) {
				boolean copy = false;
				// compare content by taking hash
				try {
					String srcHash = getFileHash(srcFile);
					String destHash = getFileHash(destFile);
					// file content is different?
					if (!srcHash.equals(destHash)) {
						copy = true;
					}
				} catch (Exception e) {
					// this shouldn't happen, but use timestamp if happens
					// src file is newer, copy it to out folder
					if (srcFile.lastModified() > destFile.lastModified()) {
						copy = true;
					}
				}
				// it's src file content changed or newer, copy it to out folder
				if (copy) {
					File outParent = outFile.getParentFile();
					if (!outParent.exists()) {
						outParent.mkdirs();
					}
					LeapUtils.copyFile(srcFile, outFile);
					numCopied ++;
					System.out.println("Copied the src file: "+srcFile.getPath());
				}
			} else {
				// the file exists in src, but not in dest, copy it to out folder
				File outParent = outFile.getParentFile();
				if (!outParent.exists()) {
					outParent.mkdirs();
				}
				LeapUtils.copyFile(srcFile, outFile);
				numCopied ++;
				System.out.println("Copied the src file: "+srcFile.getPath());
			}
			
		} 
	}

	private static String getFileHash(File file) throws NoSuchAlgorithmException, IOException {
		// get file content data
		byte[] data = LeapUtils.readFileInBytes(file);
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// take a hash from file content data
		byte[] digest = md.digest(data);
		String hash = new String(Hex.encodeHex(digest));
		return hash;
	}
}