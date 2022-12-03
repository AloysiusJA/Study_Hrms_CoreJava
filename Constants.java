package com.ja.learning.hrms.constants;

import java.util.HashMap;

public class Constants {
	
	/*****************************DB connectiion****************************/
	public static String DB_URL="jdbc:mysql://localhost:3306/hrmsdb";
	public static String DB_USERNAME="root";
	public static String DB_PASSWORD="welcome";
	public static String DB_DRIVER="com.mysql.cj.jdbc.Driver";
	
	private static final HashMap<Integer, String> departmentsMapping = new HashMap<>();

    static {
    	departmentsMapping.put(1, "Human Resources");
    	departmentsMapping.put(2, "IT Support");
    	departmentsMapping.put(3, "R&D");
    	departmentsMapping.put(4, "Conceirge Service");
    	departmentsMapping.put(5, "Services");
    	departmentsMapping.put(6, "Analytics");
    	departmentsMapping.put(7, "Training & Development");
    	departmentsMapping.put(8, "Diversity Team");
    	departmentsMapping.put(9, "QA & Testing");
    	departmentsMapping.put(10, "Cloud");
    }
    
	/**********************************************************************************************
	*											SQL QUERIES
	/**********************************************************************************************/
	/*************************************************************************************************
	 * Insert Queries
	 *//*************************************************************************************************/
	
	
	/**
	 * Query to add employee data into the table 
	 * table name is tbl_emp
	 * primary key is emp_id
	 */
	public static String SQL_ADD_EMPLOYEE = "insert into tbl_emp(emp_id,emp_fname,emp_lname,emp_email,"
			+ "emp_designation,emp_deptId,emp_joindate) values(?,?,?,?,?,?,?)";
	
	/**
	 * Query to add address data into the table 
	 * table name is tbl_addr
	 * primary key is addr_id which is given auto incremented
	 * foreign key is emp_id
	 */
	public static String SQL_ADD_ADDRESS ="insert into tbl_addr (emp_id,addr_home_phonenbr,addr_home_1,addr_home_2,addr_home_city,addr_home_pincode,addr_office_phonenbr,addr_office_1,addr_office_2,addr_office_city,addr_office_pincode) values (?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * Query to add salary breakdown data into the table 
	 * table name is tbl_salaryinfo
	 * primary key is emp_id 
	 * 
	 */
	
	public static String SQL_ADD_SALARYDETAILS="insert into tbl_salaryinfo(emp_id,sal_net,sal_basic,sal_hra,sal_da,sal_ta) values (?,?,?,?,?,?)";
	
	public static String SQL_ADD_DEPARTMENTDETAILS="insert into tbl_dept(dept_id,dept_description) values (?,?)";
	
	/**
	 * Query to add login data into the table 
	 * table name is tbl_user
	 * primary key is user_id 
	 * 
	 */
	
	public static String SQL_ADD_LOGINCREDENTIALS ="insert into tbl_user(user_id,user_pwd,user_role) values (?,?,?)";
	
	
	/*************************************************************************************************
	 * Delete Queries
	 *//*************************************************************************************************/
	
	/**
	 * Query to delete employee data from the tables
	 * table name is tbl_emp,tbl_addr
	 * based on emp_id
	 * 
	 */
	
	
	public static String DELETE_RECORD ="DELETE e,a,s FROM tbl_emp AS e LEFT JOIN  tbl_addr AS a ON e.emp_id = a.emp_id LEFT JOIN tbl_salaryinfo AS s ON e.emp_id = s.emp_id WHERE e.emp_id = ?";
	
	
	
	/*************************************************************************************************
	 * Update Queries
	 *//*************************************************************************************************/
	
	/**
	 * Query to modify employee data into the table
	 * table name is tbl_emp,tbl_addr
	 * based on emp_id
	 * 
	 */
	
	public static String SQL_MODIFY_EMPLOYEE = "update tbl_emp set emp_fname=?,emp_lname=?,emp_email=?,"
			+ "emp_designation=?,emp_deptId=?,emp_joindate=? where emp_id=?";
	
	/**
	 * Query to modify employee address data into the table
	 * table name is tbl_addr
	 * based on emp_id
	 * 
	 */
	
	public static String SQL_MODIFY_ADDRESS ="update tbl_addr set addr_home_phonenbr=?,addr_home_1=?,addr_home_2=?,addr_home_city=?,addr_home_pincode=?,addr_office_phonenbr=?,addr_office_1=?,addr_office_2=?,addr_office_city=?,addr_office_pincode=? where emp_id=?";
	
	public static String SQL_MODIFY_SALARYDETAILS="update tbl_salaryinfo set sal_net=?,sal_basic=?,sal_hra=?,sal_da=?,sal_ta=? where emp_id=?";
	
	
	/*************************************************************************************************
	 * Select Queries
	 *//*************************************************************************************************/
	
	public static String SQL_SELECT_EMPLOYEES ="SELECT e.emp_id,e.emp_fname, e.emp_lname,e.emp_email,e.emp_designation,e.emp_deptid ,e.emp_joindate,a.addr_home_phonenbr,addr_home_1,addr_home_2,a.addr_home_city,a.addr_home_pincode,a.addr_office_phonenbr,addr_office_1,addr_office_2,a.addr_office_city,a.addr_office_pincode,s.sal_net FROM tbl_emp AS e LEFT OUTER JOIN tbl_addr AS a ON e.emp_id = a.emp_id LEFT JOIN tbl_salaryinfo AS s ON e.emp_id = s.emp_id";
	
	
	public static String SQL_SELECT_EMPID="SELECT emp_id from tbl_emp where emp_id=?";
	
	public static String SQL_SELECTWITH_EMPID = "SELECT e.emp_fname, e.emp_lname,e.emp_email,e.emp_designation,e.emp_deptid ,e.emp_joindate,a.addr_home_phonenbr,addr_home_1,addr_home_2,a.addr_home_city,a.addr_home_pincode,a.addr_office_phonenbr,addr_office_1,addr_office_2,a.addr_office_city,a.addr_office_pincode,s.sal_net FROM tbl_emp AS e LEFT OUTER JOIN tbl_addr AS a ON e.emp_id = a.emp_id LEFT JOIN tbl_salaryinfo AS s ON e.emp_id = s.emp_id where e.emp_id=?";
	
	public static String SQL_SELECTWITH_DEPTWISE = "SELECT e.emp_id,e.emp_fname, e.emp_lname,e.emp_email,e.emp_designation,e.emp_deptid ,e.emp_joindate,a.addr_home_phonenbr,addr_home_1,addr_home_2,a.addr_home_city,a.addr_home_pincode,a.addr_office_phonenbr,addr_office_1,addr_office_2,a.addr_office_city,a.addr_office_pincode,s.sal_net FROM tbl_emp AS e LEFT OUTER JOIN tbl_addr AS a ON e.emp_id = a.emp_id LEFT JOIN tbl_salaryinfo AS s ON e.emp_id = s.emp_id where e.emp_deptid=?";
	
	public static String SQL_GET_EMPDETAILS= "select e.emp_fname,e.emp_lname,e.emp_email,e.emp_designation,e.emp_department,e.emp_salary from tbl_emp as e "
			+ "left join tbl_addr as a on e.emp_id=a.emp_id";
	
	public static String SQL_SELECT_PAYSLIP_DETAILS = "select e.emp_fname,e.emp_lname,e.emp_email,e.emp_designation,a.addr_office_city,p.sal_net,p.sal_basic,p.sal_hra,p.sal_da,p.sal_ta from tbl_emp as e left join tbl_addr as a on e.emp_id=a.emp_id left join tbl_salaryinfo as p on e.emp_id=p.emp_id where e.emp_id=?";
	
	public static String SQL_SELECT_DEPARTMENTS ="select dept_id,dept_name from tbl_dept";
	
	public static String SQL_SELECT_LOGINCREDENTIALS ="select * from tbl_user where user_id=?";
	
}