/**
 * 
 */
package com.ja.learning.hrms.service;


import java.util.List;

import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.HrmsException;

/**
 * @author Jiby
 *
 */
public interface EmployeeService {

	

	public int modifyEmployee(Employee emp) throws HrmsException;
	
	
	
	public Employee getEmployeeDetails(String employeeID) throws HrmsException;
	
	public Employee getPayrollDetails(String employeeID) throws HrmsException;
	
	public User verifyLoginCredentials(User user) throws HrmsException;
	
	public boolean checkIfEmployee(String empID) throws HrmsException;
	

}
