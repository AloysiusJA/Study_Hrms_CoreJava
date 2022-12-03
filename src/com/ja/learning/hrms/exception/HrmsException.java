package com.ja.learning.hrms.exception;

import java.util.Map;

public class HrmsException extends Exception{

	public Map<String, Boolean> getErrorMappings() {
		return errorMappings;
	}
	public void setErrorMappings(Map<String, Boolean> errorMappings) {
		this.errorMappings = errorMappings;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6801315777845801056L;

	public HrmsException() {
		// TODO Auto-generated constructor stub
	}
	
	private String errorCode;
	private Map<String, Boolean> errorMappings;
	
	public HrmsException(String errorCode) {
		super();
		this.errorCode=errorCode;
		
	}
	public HrmsException(String message,Throwable cause ,String errorCode){
		super(message,cause);
		this.errorCode=errorCode;
	}
	
	public HrmsException(Throwable cause,String errorCode) {
		super(cause);
		this.errorCode=errorCode;
		
	}
	public HrmsException(String message,String errorCode) {
		super(message);
		this.errorCode=errorCode;
	
	}
	public HrmsException(Map<String,Boolean> errorMappings) {
		
		this.errorMappings=errorMappings;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
