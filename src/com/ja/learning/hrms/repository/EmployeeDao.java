package com.ja.learning.hrms.repository;

import java.util.List;

import com.ja.learning.hrms.domain.Department;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.HrmsException;

public interface EmployeeDao {
	
	
	public Integer addEmployee(Employee emp) throws HrmsException;
	public int modifyEmployee(Employee emp) throws HrmsException;
	public int deleteEmployee(String employeeID) throws HrmsException;
	public int addPayrollInfo(String empId,Payroll payroll) throws HrmsException;
	
	public Employee getEmployeeDetails(String employeeID) throws HrmsException;
	
	public Employee getPayrollDetails(String employeeID) throws HrmsException;
	
	public List<Employee> getEmployeesDeptWise(int dept_id) throws HrmsException;
	
	public List<Department> getDepartments() throws HrmsException;
	
	public List<Employee> getEmployees() throws HrmsException;
	public int addLoginCredentials(User user) throws HrmsException;
	public User verifyLoginCredentials(User user) throws HrmsException;
	
	public boolean checkIfEmployee(String empID) throws HrmsException;

}
