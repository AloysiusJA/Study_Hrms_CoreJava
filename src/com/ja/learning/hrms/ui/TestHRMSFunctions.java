package com.ja.learning.hrms.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ja.learning.hrms.controller.HrmsController;
import com.ja.learning.hrms.domain.Address;
import com.ja.learning.hrms.domain.Department;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.HrmsException;

public class TestHRMSFunctions {
	
	//private static final String DATE_FORMAT = "dd-mm-yyyy";
	
	HrmsController hrms = new HrmsController();
	
	public TestHRMSFunctions() {
	}

	public static void main(String[] args) {
		
		TestHRMSFunctions testhrms = new TestHRMSFunctions();
		Scanner sc= new Scanner(System.in);
		System.out.println("Please enter the operation you want to execute");
		
		System.out.println("1 : ADD EMPLOYEE ,2: DELETE EMPLOYEE , 3: MODIFY EMPLOYEE, 4: DISPLAY ALL , 5: DISPLAY SINGLE EMP,"
				+ " 6: ADD PAYROLL INFO , 7: SHOW PAYSLIP , 8: DISPLAY DEPTARTMENTS , 9: LOGIN ,10:SETUP LOGIN CREDENTIALS, 11: DISPLAY DEPT WISE");
		int selection=sc.nextInt();
		
		switch(selection) {
		
		
		case 1:			
			testhrms.addEmployee();
			break;
		case 2:
			testhrms.deleteEmployee();
			break;
		case 3:
			testhrms.modifyEmployee();
			break;	
		case 4:
			testhrms.displayAllRecords();
			break;
		case 5:
			testhrms.displayEmployeeData();
			break;
		case 6:
			testhrms.addPayRollInfo();
			break;
		case 7:
			testhrms.displayPaySlip();
			break;	
		case 8:
			testhrms.displayDepartments();
			break;	
		case 9:
			testhrms.verifyLogin();
			break;
		case 10:
			testhrms.addLogin();
			break;
		case 11:
			testhrms.displayEmpDeptWise();
			break;
		default:
			System.out.println("Enter valid input");
			selection=sc.nextInt();
			break;
		
		}

		sc.close();

	}
	
	
	
	
	public void addEmployee() {
		Employee emp=new Employee();
		Address officeAddr = new Address();
		Address homeAddr = new Address();
		Scanner sc= new Scanner(System.in);
		
		System.out.println("Please enter Employee Id:");
		emp.setEmpId(sc.next());
		
		System.out.println("First name:");
		emp.setFirstName(sc.next());
		
		System.out.println("Last name:");
		emp.setLastName(sc.next());
		
		System.out.println("Email ID");
		emp.setEmailID(sc.next());
		
		System.out.println("Designation:");
		emp.setDesignation(sc.next());
		
		System.out.println("Department ID: Int");
		emp.setDepartmentID(sc.nextInt());
		
		
		System.out.println("Join Date: YYYY-MM-DD");
		String strDate = sc.next();
		
		
		emp.setJoinDate(LocalDate.parse(strDate));
		
		System.out.println("Office Phone");
		officeAddr.setPhoneNbr(sc.next());
		System.out.println("Office Address 1");
		officeAddr.setAddressLine1(sc.next());
		System.out.println("Office Address 2");
		officeAddr.setAddressLine2(sc.next());
		System.out.println("Office city");
		officeAddr.setCity(sc.next());
		System.out.println("Office pincode :int");
		officeAddr.setPinCode(sc.nextInt());
		
		//Adding the office address object to employee object
		emp.setOfficeAddress(officeAddr);
		
		System.out.println("Personal Phone");
		homeAddr.setPhoneNbr(sc.next());
		System.out.println("Home Address 1");
		homeAddr.setAddressLine1(sc.next());
		System.out.println("Home Address 2");
		homeAddr.setAddressLine2(sc.next());
		System.out.println("Home city");
		homeAddr.setCity(sc.next());
		System.out.println("Home pincode : Int");
		homeAddr.setPinCode(sc.nextInt());
		emp.setHomeAddress(homeAddr);
		
		emp.setAddr_id(1000+Math.incrementExact(5));
		
		Payroll payroll = new Payroll();
		System.out.println("Enter the net salary (double)");
		payroll.setSalary_net(sc.nextDouble());
		System.out.println("Enter the basic pay (double)");
		payroll.setSalary_basic(sc.nextDouble());
		System.out.println("Enter the hra(double)");
		payroll.setSalary_hra(sc.nextDouble());
		System.out.println("Enter the ta (double)");
		payroll.setSalary_ta(sc.nextDouble());
		System.out.println("Enter the da (double)");
		payroll.setSalary_da(sc.nextDouble());
		
		emp.setPayrollInfo(payroll);
		
		
		try {						
			int count = hrms.addEmployee(emp);		
			
			
		} catch (HrmsException he) {
			//if(he.getErrorMappings().size()>0)
				he.getErrorMappings().forEach((K,V)->System.out.println(K));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		sc.close();
	}
	
	
	public void modifyEmployee() {
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the employee id of the employee");
		String empID=sc.next();		
		
		try {
			boolean isPresent=hrms.checkIfEmployee(empID);
			if(!isPresent)
				System.out.println("No matching employee ID ,please create a new Employee");
			else
				doModify(empID);
			
		} catch (HrmsException e1) {
			
			System.out.println("No matching employee ID ,please create a new Employee");
			System.out.println(e1.getErrorCode());
			e1.printStackTrace();
		}
		
		
		sc.close();
	}
	
	public void doModify(String empID) {
		
		Employee emp= populateEmpDetails(empID);
		try {
			int status = hrms.modifyEmployee(emp);
			if(status>=0)
				System.out.println("Record successfully modified");
		} catch (HrmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void deleteEmployee() {
		System.out.println("Enter the employee Id to be deleted");
		Scanner sc=new Scanner(System.in);
		String empId = sc.next();
		sc.close();
		try {
			int status = hrms.deleteEmployee(empId);
			if(status>0)
				System.out.println("Record Successfully deleted");
			else
				System.out.println("No Record found");
			
		} catch (HrmsException e) {
			
			e.printStackTrace();
		}
	}
	
	
	//Display Employee Details
	//Based on employee ID
	public void displayEmployeeData() {
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the employee id for employee details:");
		String empID=sc.next();
		sc.close();
		Employee emp = new Employee();
		try {
			emp = hrms.getEmployeeDetails(empID);
			System.out.println(emp);
		} catch (HrmsException e) {
			System.out.println(e.getErrorCode());
			
		}
	
	}
	
	public void displayEmpDeptWise() {
		List<Employee> empList = new ArrayList<Employee>();
		
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the dept id for employee list:");
		int dept_id=sc.nextInt();
		sc.close();
		
		try {
			empList = hrms.getEmployeesDeptWise(dept_id);
			empList.forEach(System.out::println);
		} catch (HrmsException e) {			
			System.out.println(e.getErrorCode());
		}
	
	}
	
	public void displayAllRecords() {
		List<Employee> empList = new ArrayList<Employee>();
		try {
			empList = hrms.getEmployees();
		} catch (HrmsException e) {
			System.out.println(e.getErrorCode());
		}
	
		//Stream<Employee> listStream = empList.stream();
		
		//List<Object>  employeeList= listStream.map(x->(x.getHomeAddress()==null?" ":x)).collect(Collectors.toList());
		
		empList.forEach(System.out::println);
		
		//List<Employee> empList = empList.stream.map(t->t.getHome!=null).sorted((str1,str2)->str1.getCountryName().compareTo(str2.getCountryName())).collect(Collectors.toList());
		
		//List<Employee> newList = 
				//empList.stream().map(x->(x.getHomeAddress()==null?" ":x)).collect(Collectors.toList(<Employee>));
		
	}
	
	
		//Add payroll info
		public void addPayRollInfo() {
		
			System.out.println("Enter the employee Id for adding payroll info");
			Scanner sc=new Scanner(System.in);
			String empId = sc.next();
		
			Payroll payroll = new Payroll();
			System.out.println("Enter the basic pay (double)");
			payroll.setSalary_basic(sc.nextDouble());
			System.out.println("Enter the hra(double)");
			payroll.setSalary_hra(sc.nextDouble());
			System.out.println("Enter the ta (double)");
			payroll.setSalary_ta(sc.nextDouble());
			System.out.println("Enter the da (double)");
			payroll.setSalary_da(sc.nextDouble());
		
			sc.close();
			
			try {
				int status = hrms.addPayrollInfo(empId, payroll);
				if(status>0)
					System.out.println("Record created");
				
			} catch (HrmsException e) {
			
				System.out.println(e.getErrorCode());
			}
		}	
	
		public void displayPaySlip() {
			
			System.out.println("Enter the employee Id for showing payroll info");
			Scanner sc=new Scanner(System.in);
			String empId = sc.next();
			sc.close();
			try {
				Employee emp = hrms.getPayrollDetails(empId);
				System.out.println(emp.getEmpId()+" Name: "+emp.getFirstName()+" "+emp.getLastName()+" Email: "+emp.getEmailID()+ " : "+
				emp.getDesignation()+" : "+emp.getOfficeAddress().getCity()+"Net Salary:"+emp.getPayrollInfo().getSalary_net()+ " Basic: "+emp.getPayrollInfo().getSalary_basic()+ " HRA: "+
						emp.getPayrollInfo().getSalary_hra()+" TA: "+emp.getPayrollInfo().getSalary_ta()+" DA: "+emp.getPayrollInfo().getSalary_da());
				//
			} catch (HrmsException e) {
				
				e.printStackTrace();
			}
		}
		
		public void displayDepartments() {
			
			List<Department> deptList = new ArrayList<Department>();
			try {
				deptList = hrms.getDepartments();
				
				deptList.forEach(System.out::println);
			} 
			catch (HrmsException e) {
				
				System.out.println(e.getErrorCode());
			}
			
		}
		
		public void verifyLogin() {
			
			User user=new User();
			User currUser=new User();
			Scanner sc=new Scanner(System.in);
			System.out.println("Please enter the user name");
			String userName = sc.next();
			user.setUserName(userName);
			System.out.println("Please enter the password");
			String password = sc.next();
			user.setPassword(password);
			sc.close();
			
			try {
				currUser=hrms.checkLoginCredentials(user);			
				
			
				if (currUser.isValid())
				{
					System.out.println("Successfully Logged In");
				}
				else {
					System.out.println("User name or password not correct");
					
				}
					 
		         
			} catch (HrmsException e) {
				
				System.out.println(e.getErrorCode());
			}
			
		}
		
		public void addLogin() {
			User user = new User();
			int status=0;
			Scanner sc=new Scanner(System.in);
			System.out.println("Please enter the user name");
			String userName = sc.next();
			user.setUserName(userName);
			System.out.println("Please enter the password");
			String password = sc.next();
			user.setPassword(password);
			System.out.println("Please enter the user role (ADMIN/USER)");
			String userRole = sc.next();
			user.setRole(userRole);	
			sc.close();
			
			
			try {
				
				status=hrms.addLoginCredentials(user);
				if(status>0)
					System.out.println("User Login details added successfully");
				else
					System.out.println("User details not added");
			}
			catch (HrmsException e) {
				System.out.println(e.getErrorCode());
				
				//e.printStackTrace();
			}
			
		}
		
		
		static Employee populateEmpDetails(String empID) {
			Employee emp=new Employee();
			Address officeAddr = new Address();
			Address homeAddr = new Address();
			Payroll payroll = new Payroll();
			Scanner sc= new Scanner(System.in);
			
			//System.out.println("Please enter Employee Id:");
			emp.setEmpId(empID);
			
			System.out.println("First name:");
			emp.setFirstName(sc.next());
			
			System.out.println("Last name:");
			emp.setLastName(sc.next());
			
			System.out.println("Email ID");
			emp.setEmailID(sc.next());
			
			System.out.println("Designation:");
			emp.setDesignation(sc.next());
			
			System.out.println("Dept ID:");
			emp.setDepartmentID(sc.nextInt());
			
					
			System.out.println("Join Date: YYYY-MM-DD");
			String strDate = sc.next();
			
			emp.setJoinDate(LocalDate.parse(strDate));
			
			
			System.out.println("Office Phone");
			officeAddr.setPhoneNbr(sc.next());
			System.out.println("Office Address 1");
			officeAddr.setAddressLine1(sc.next());
			System.out.println("Office Address 2");
			officeAddr.setAddressLine2(sc.next());
			System.out.println("Office city");
			officeAddr.setCity(sc.next());
			System.out.println("Office pincode");
			officeAddr.setPinCode(sc.nextInt());
			
			//Adding the office address object to employee object
			emp.setOfficeAddress(officeAddr);
			
			System.out.println("Personal Phone");
			homeAddr.setPhoneNbr(sc.next());
			System.out.println("Home Address 1");
			homeAddr.setAddressLine1(sc.next());
			System.out.println("Home Address 2");
			homeAddr.setAddressLine2(sc.next());
			System.out.println("Home city");
			homeAddr.setCity(sc.next());
			System.out.println("Home pincode");
			homeAddr.setPinCode(sc.nextInt());
			emp.setHomeAddress(homeAddr);
			
			emp.setAddr_id(1000+Math.incrementExact(5));
			
		
			System.out.println("Enter the net salary (double)");
			payroll.setSalary_net(sc.nextDouble());
			System.out.println("Enter the basic pay (double)");
			payroll.setSalary_basic(sc.nextDouble());
			System.out.println("Enter the hra(double)");
			payroll.setSalary_hra(sc.nextDouble());
			System.out.println("Enter the ta (double)");
			payroll.setSalary_ta(sc.nextDouble());
			System.out.println("Enter the da (double)");
			payroll.setSalary_da(sc.nextDouble());
			
			emp.setPayrollInfo(payroll);
			
			sc.close();
			return emp;
			
			
		}
	

}
