package org.leap;

import com.sforce.soap.apex.ExecuteAnonymousResult;
import com.sforce.ws.ConnectionException;

public class ExecuteAnonymousTask extends LeapTask {

	private String m_code = null;
	public void setCode(String code){
		m_code = code;
	}
	
	public void execute() {
		this.validateConnectionParams();
		ExecuteAnonymousResult result = null;
		try {
			result = this.salesforceConnection().getApexConnection().executeAnonymous(this.m_code);
		} catch (ConnectionException e) {
			e.printStackTrace();
			if(this.getFailOnError()){ System.exit(1); }
		} finally {
			System.out.println("Execute Anonymous results");
			if(result == null){
				System.out.println("Failed to execute. See stack trace output.");
				if(this.getFailOnError()){ System.exit(1); }
				return;
			}
			System.out.println("\tCompiled: " + result.isCompiled() );
			if( !result.isCompiled() ){
				System.out.println("\tCompiler Error:");
				System.out.println(result.getCompileProblem() );
				if(this.getFailOnError()){ System.exit(1); }
			}
			System.out.println("");
			System.out.println("\tSuccess: " + result.isSuccess());
			if( !result.isSuccess() ){
				System.out.println(result.getExceptionMessage());
				System.out.println(result.getExceptionStackTrace() );
				if(this.getFailOnError()){ System.exit(1); }
			}
		}
	}
}