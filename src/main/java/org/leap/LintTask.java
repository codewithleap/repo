package org.leap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LintTask extends LeapTask {
	
	String strMaxFileLines = "500";
    public void setMaxFileLines(String lines) {
    	strMaxFileLines = lines;
    }
    
    String strMaxMethodLines = "75";
    public void setMaxMethodLines(String lines) {
    	strMaxMethodLines = lines;
    }
    
    String ignoreFiles = "";
    public void setIgnoreFiles(String files) {
    	ignoreFiles = files;
    }
    
	public void execute() {
		/*
		 * TODO: Review all local files for common coding conventions.
		 * No file longer than 500 lines.
		 * No method longer than 75 lines.
		 */
		String rootPath = new java.io.File("").getAbsolutePath();
		File srcFile = new File(rootPath + "/src");
		try {
			traverseFileRecursively(srcFile, rootPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void traverseFileRecursively(File srcFile, String path) throws IOException {
		if (srcFile.isDirectory()) {
			File allFiles[] = srcFile.listFiles();
			for (File aFile : allFiles) {
				traverseFileRecursively(aFile, path + "/" + srcFile.getName());
			}
		} else if (srcFile.isFile()) {
			this.analyzeFile(path, srcFile);
		}
	}
	
	private void analyzeFile(String path, File srcFile) throws IOException {
		if( !isHumanEditable( srcFile.getName() ) || this.isIgnored(srcFile.getName()) || this.isMetaData( srcFile.getName() )){
			return;
		}
		int numLines = this.countLines(path + "/" + srcFile.getName());
		if(numLines > Integer.parseInt(this.strMaxFileLines) ){
			System.out.println("  " + srcFile.getName() + " " + numLines + " lines.");
		}
		
		if(this.isApex( srcFile.getName() )){
			// parse file. Check method lengths.
			// TODO: Use Tooling REST API to get symbol table for file.
			// http://www.salesforce.com/us/developer/docs/api_tooling/index.htm
		}
	}
	
	private boolean isMetaData(String fileName){
		return fileName.toLowerCase().endsWith("meta.xml");
	}
	
	private boolean isHumanEditable(String fileName){
		return ( fileName.endsWith(".trigger") || fileName.endsWith(".cls") || fileName.endsWith(".page") || 
				fileName.endsWith(".component") || fileName.endsWith(".resource") );
	}
	
	private boolean isIgnored(String fileName){
		return (this.ignoreFileList().contains(fileName.toLowerCase()) );
	}
	
	private boolean isApex(String fileName){
		return ( fileName.endsWith(".trigger") || fileName.endsWith(".cls") );
	}
	
	private List<String> m_fileList = null;
    public List<String> ignoreFileList(){
    	if(m_fileList == null){
    		m_fileList = new ArrayList<String>();
    		if(this.ignoreFiles == null || this.ignoreFiles.equals("")){
    			return m_fileList;
    		} else {
    			for(String f : this.ignoreFiles.split(",")){
    				f = f.replaceAll(" ", "");
    				m_fileList.add(f.toLowerCase());
    			}
    		}
    	}
    	return m_fileList;
    }
	
	public int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
}