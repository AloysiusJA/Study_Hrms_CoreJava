package com.ja.learning.hrms.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.ja.learning.hrms.domain.Address;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;

public class Validations {

	public Validations() {
		
	}
	
	public static boolean patternMatches(String emailAddress, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}
	
	@Test
	public static void testUsingSimpleRegex() {
	   String emailAddress = "username@domain.com";
	   //String regexPattern = "^(.+)@(\\S+)$";
	   String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	    System.out.println((Validations.patternMatches(emailAddress, regexPattern)));
	}
	
	public static boolean checkPhoneNbr() {
		boolean isValid=false;
		
		return isValid;
	}
	
	public static boolean checkSalaryComponents(Payroll payroll){
		
		boolean isValid=true;
		Double sal_total = payroll.getSalary_basic()+payroll.getSalary_da()+payroll.getSalary_hra()+payroll.getSalary_ta();
		System.out.println(sal_total +":::"+payroll.getSalary_net());
		if(sal_total>payroll.getSalary_net())
			isValid=false;
			return isValid;
		
	}
	
	public static boolean checkEmpID(String empId){		
		boolean isValid=false;
		if(empId!=null && empId.matches("^[a-zA-Z0-9-]+$"))
			isValid=true;
		return isValid;
		
	}
	
	public static boolean checkDate(){		
		boolean isValid=false;
		
			return isValid;
		
	}
	
	
	
	public static void main(String[] args) {
		testUsingSimpleRegex();
	}
	
	
	public static Map<String,Boolean> inputDataValidations(Employee emp) {
	
		Address homeAddress = emp.getHomeAddress();
		Address officeAddress = emp.getOfficeAddress();
		Payroll payroll = emp.getPayrollInfo();
		
		Map<String,Boolean> errorMapping = new HashMap<String,Boolean>();
		if(!Validations.checkEmpID(emp.getEmpId()))
			errorMapping.put("Employee ID can't be empty / should be Alphanumeric only", false);		
		if(emp.getFirstName().isBlank())
			errorMapping.put("First Name can't be empty", false);
		if(emp.getLastName().isBlank())
			errorMapping.put("Last name can't be empty", false);
		if(emp.getPayrollInfo().getSalary_net()<=0)
			errorMapping.put("Salary should be greater then 0", false);
		if(!Validations.checkSalaryComponents(payroll))
			errorMapping.put("Salary components are not matching with net salary", false);
	
	return errorMapping;
		
	}
	
}
