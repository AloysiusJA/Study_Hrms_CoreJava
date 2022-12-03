/**
 * 
 */
package com.ja.learning.hrms.service;


import java.util.Comparator;


import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.HrmsException;
import com.ja.learning.hrms.repository.EmployeeDao;
import com.ja.learning.hrms.repository.EmployeeDaoImpl;

/**
 * @author Jiby
 *
 */
public class EmployeeServiceImpl implements EmployeeService{

	/**
	 * 
	 */
	
	EmployeeDao dao = new EmployeeDaoImpl();
	
	public EmployeeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public int modifyEmployee(Employee emp) throws HrmsException {	
		
		
		
		return dao.modifyEmployee(emp);
	}

	

	

	@Override
	public Employee getEmployeeDetails(String employeeID) throws HrmsException {
		
		return dao.getEmployeeDetails(employeeID);
	}

	@Override
	public Employee getPayrollDetails(String employeeID) throws HrmsException {
		// TODO Auto-generated method stub
		return dao.getPayrollDetails(employeeID);
	}



	@Override
	public User verifyLoginCredentials(User user) throws HrmsException {
		
		
		return dao.verifyLoginCredentials(user);
	}



	@Override
	public boolean checkIfEmployee(String empID) throws HrmsException {
		return dao.checkIfEmployee(empID);
	}
	
	

}

class CheckUser implements Comparator<User>{

	@Override
	public int compare(User user1, User user2) {
		
		 // Comparing customers
        int userNameCompare = user1.getUserName().compareTo(user2.getUserName());

        int passwordCompare = user1.getPassword().compareTo(user2.getPassword());

        if(userNameCompare!=0 || passwordCompare!=0)
        	return userNameCompare;
        else
        	return 0;
	}
	
}
