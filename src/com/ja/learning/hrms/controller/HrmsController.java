/**
 * 
 */
package com.ja.learning.hrms.controller;



import java.util.List;

import com.ja.learning.hrms.domain.Department;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.HrmsException;
import com.ja.learning.hrms.service.EmployeeService;
import com.ja.learning.hrms.service.EmployeeServiceImpl;
import com.ja.learning.hrms.service.HRService;
import com.ja.learning.hrms.service.HRServiceImpl;


/**
 * @author Jiby
 *
 */
public class HrmsController {

	/**
	 * 
	 */
	public HrmsController() {
		
	}
	HRService hrService = new HRServiceImpl();
	EmployeeService employeeService = new EmployeeServiceImpl();
	
	//The functions accessible for only HR
	public int addEmployee(Employee emp) throws HrmsException {
		
		return hrService.addEmployee(emp);
	}
	
	public int modifyEmployee(Employee emp) throws HrmsException{		
		return employeeService.modifyEmployee(emp);
	}	
	
	public int deleteEmployee(String employeeID) throws HrmsException{
		return hrService.deleteEmployee(employeeID);
	}
	
	public Employee getEmployeeDetails(String employeeID) throws HrmsException{
		return employeeService.getEmployeeDetails(employeeID);
	}	
	
	public List<Employee> getEmployees() throws HrmsException{
		return hrService.getEmployees();
	}
	
	
	public int addPayrollInfo(String empId,Payroll payroll) throws HrmsException{
		return hrService.addPayrollInfo(empId, payroll);
	}
	
	public Employee getPayrollDetails(String employeeID) throws HrmsException{
		return employeeService.getPayrollDetails(employeeID);
	}
	
	public List<Department> getDepartments() throws HrmsException{
		return hrService.getDepartments();
	}
	
	public User checkLoginCredentials(User user) throws HrmsException{
		return employeeService.verifyLoginCredentials(user);
	}
	
	public int addLoginCredentials(User user) throws HrmsException{
		return hrService.addLoginCredentials(user);
	}
	
	public boolean checkIfEmployee(String empID) throws HrmsException{
		return employeeService.checkIfEmployee(empID);
	}
	
	public List<Employee> getEmployeesDeptWise(int dept_id) throws HrmsException{
		return hrService.getEmployeesDeptWise(dept_id);
	}
	
}
