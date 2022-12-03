/**
 * 
 */
package com.ja.learning.hrms.domain;

import java.time.LocalDate;
import java.util.Date;



/**
 * @author Jiby
 *
 */
public class Employee {

	

	

	/**
	 * 
	 */
	
	
	private String empId;	
	
	private String firstName;
	private String lastName;
	private String emailID;
	private String designation;
	private int departmentID;
	//private Date hireDate;	
	
	private LocalDate joinDate;
	public LocalDate getJoinDate() {
		return joinDate;
	}


	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}


	private Address homeAddress;
	private Address officeAddress;	
	private Payroll payrollInfo;
	private int addr_id;
	
	
	/**
	 * No argument constructor
	 */
	public Employee() {
		
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmailID() {
		return emailID;
	}


	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public int getDepartmentID() {
		return departmentID;
	}


	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}


//	public Date getHireDate() {
//		return hireDate;
//	}
//
//
//	public void setHireDate(Date hireDate) {
//		this.hireDate = hireDate;
//	}


	public Address getHomeAddress() {
		return homeAddress;
	}


	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}


	public Address getOfficeAddress() {
		return officeAddress;
	}


	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}


	public Payroll getPayrollInfo() {
		return payrollInfo;
	}


	public void setPayrollInfo(Payroll payrollInfo) {
		this.payrollInfo = payrollInfo;
	}


	public int getAddr_id() {
		return addr_id;
	}


	public void setAddr_id(int addr_id) {
		this.addr_id = addr_id;
	}
	
		
	@Override
	public String toString() {
		
		
		return empId + "  " + firstName +" "+ lastName + "  EmailID="
				+ emailID + "  Designation:" + designation + "  Department ID:" +departmentID+ "  Join Date:" + joinDate + "  HomeAddress:" + homeAddress + "  Office Address:" + officeAddress;
				
	}



	
	
	
	

}
