package com.ja.learning.hrms.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ja.learning.hrms.domain.Department;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.ErrorCode;
import com.ja.learning.hrms.exception.HrmsException;
import com.ja.learning.hrms.repository.EmployeeDao;
import com.ja.learning.hrms.repository.EmployeeDaoImpl;


public class HRServiceImpl implements HRService {
	
	EmployeeDao dao = new EmployeeDaoImpl();

	public HRServiceImpl() {
		
	}

	@Override
	public int deleteEmployee(String employeeID) throws HrmsException {
		 Optional<String> op = Optional.ofNullable(employeeID);
		 if(op.isEmpty()) {
			 String errorTest = "Employee ID "+ErrorCode.CANNOT_BE_NULL;
			 throw new HrmsException(errorTest);
		 }
			 
		
		return dao.deleteEmployee(employeeID);
	}

	@Override
	public List<Employee> getEmployees() throws HrmsException {
		return dao.getEmployees();
	}

	@Override
	public int addEmployee(Employee emp) throws HrmsException {
		
		
		Map<String,Boolean> errorMappings= new HashMap<String,Boolean>();
		
		errorMappings = Validations.inputDataValidations(emp);
		
		//errorMappings.forEach((K,V)->System.out.println(K));
		
		if(errorMappings.size()>0) {
			throw new HrmsException(errorMappings);
		}
		//use filter to get true values and error mappings
		//if the size is not greater than 0 then continue with normal working
		//else return with the error mappings
		
		else			
			return dao.addEmployee(emp);
	}

	@Override
	public int addPayrollInfo(String empId,Payroll payroll) throws HrmsException {
		return dao.addPayrollInfo(empId,payroll);
	}
	
	public List<Department> getDepartments() throws HrmsException{
		return dao.getDepartments();
	}
	
	public int addLoginCredentials(User user) throws HrmsException{
		return dao.addLoginCredentials(user);
	}
	
	public List<Employee> getEmployeesDeptWise(int dept_id) throws HrmsException{
		return dao.getEmployeesDeptWise(dept_id);
	}
	
	

}
