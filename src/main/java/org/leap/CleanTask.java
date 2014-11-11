package org.leap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CleanTask extends LeapTask {
	
    /*
     * Task goals.
     * Remove metadata prior to deploy.
     * Remove all custom or by type.
     * Remove only unit test files (@IsTest)
     * Support for piping file names to be deleted using ls.
     * 
     * Example use cases:
     * Remove all unit tests.
     * Remove all metadata with a matching namespace prefix.
     * Remove all Apex classes.
     * Remove all Visualforce Pages.
     * 
     * FailOnError = true | false
     * 
     * Dependency checks. Must be declared in order of dependency.
     * 
     * Chunking. API supports 10 records at a time. Provide abstraction to delete all in one operation.
     * Local vs API. Declaration applies to cloud definition of files? or local files?
     * Make this configurable?
     * 
     * Local could support grep of files. 
     * <leap:clean type="remote" metadatatype="ApexClass".
     * Task uses metadata API to determine scope of files to delete.
     * Useful in Git as system of record use cases.
     * 
     * <leap:clean type="ApexClass" source="local" grep="@IsTest src/classes/ADM* -l" />
     * 
     * Maybe this should be a separate deleteFiles task to clearly distinguish from goals of 'clean'. 
     * 
     * Task uses the local file system for determining scope of metadata to remove. 
     * 
     * Use case: clean an org of all metadata of certain type, which may not be replicated locally.
     * 
     */
	public void execute() {
		
	}
}