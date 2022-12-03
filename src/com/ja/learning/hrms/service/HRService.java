package com.ja.learning.hrms.service;

import java.util.List;

import com.ja.learning.hrms.domain.Department;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.HrmsException;



/**
 * @author Jiby
 * This interface is HR Service which handles all the functions of the HR Login
 *
 */
public interface HRService {	
	
	public int deleteEmployee(String employeeID) throws HrmsException;	
	public List<Employee> getEmployees() throws HrmsException;	
	public int addEmployee(Employee emp) throws HrmsException;
	
	public int addPayrollInfo(String empId,Payroll payroll) throws HrmsException;
	
	public List<Department> getDepartments() throws HrmsException;
	public int addLoginCredentials(User user) throws HrmsException;
	
	public List<Employee> getEmployeesDeptWise(int dept_id) throws HrmsException;

}
