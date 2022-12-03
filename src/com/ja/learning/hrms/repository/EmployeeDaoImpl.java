/**
 * 
 */
package com.ja.learning.hrms.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.ja.learning.hrms.constants.Constants;
import com.ja.learning.hrms.domain.Address;
import com.ja.learning.hrms.domain.Department;
import com.ja.learning.hrms.domain.Employee;
import com.ja.learning.hrms.domain.Payroll;
import com.ja.learning.hrms.domain.User;
import com.ja.learning.hrms.exception.ErrorCode;
import com.ja.learning.hrms.exception.HrmsException;
import com.mysql.cj.jdbc.DatabaseMetaData;

/**
 * @author Jiby
 *
 */
public class EmployeeDaoImpl implements EmployeeDao {

	/**
	 * 
	 */
	public EmployeeDaoImpl() {
		
	}

	@Override
	public Integer addEmployee(Employee emp) throws HrmsException{
		
		Connection con=null;
		
		int status=0;
		
		try {
			con = Connect.getConnection();			
		
			
			PreparedStatement addEmployee = con.prepareStatement(Constants.SQL_ADD_EMPLOYEE);			
			
			addEmployee.setString(1, emp.getEmpId());
			addEmployee.setString(2, emp.getFirstName());
			addEmployee.setString(3, emp.getLastName());
			addEmployee.setString(4, emp.getEmailID());
			addEmployee.setString(5, emp.getDesignation());
			addEmployee.setInt(6, emp.getDepartmentID());
			
			Date dt = Date.valueOf(emp.getJoinDate());
			
			addEmployee.setDate(7, dt);
			
			PreparedStatement addAddress =con.prepareStatement(Constants.SQL_ADD_ADDRESS);
			
			//addAddress.setInt(1,emp.getAddr_id());
			addAddress.setString(1, emp.getEmpId());
			addAddress.setString(2, emp.getHomeAddress().getPhoneNbr());
			addAddress.setString(3,emp.getHomeAddress().getAddressLine1());
			addAddress.setString(4, emp.getHomeAddress().getAddressLine2());
			addAddress.setString(5, emp.getHomeAddress().getCity());
			addAddress.setInt(6, emp.getHomeAddress().getPinCode());
			
			addAddress.setString(7, emp.getOfficeAddress().getPhoneNbr());
			addAddress.setString(8,emp.getOfficeAddress().getAddressLine1());
			addAddress.setString(9, emp.getOfficeAddress().getAddressLine2());
			addAddress.setString(10, emp.getOfficeAddress().getCity());
			addAddress.setInt(11, emp.getOfficeAddress().getPinCode());
			
			PreparedStatement ptstAddPayroll = con.prepareStatement(Constants.SQL_ADD_SALARYDETAILS);			
			
			ptstAddPayroll.setString(1, emp.getEmpId());
			
			ptstAddPayroll.setDouble(2,Double.valueOf(emp.getPayrollInfo().getSalary_net()));
			ptstAddPayroll.setDouble(3,Double.valueOf(emp.getPayrollInfo().getSalary_basic()));
			ptstAddPayroll.setDouble(4,Double.valueOf(emp.getPayrollInfo().getSalary_hra()) );
			ptstAddPayroll.setDouble(5,Double.valueOf(emp.getPayrollInfo().getSalary_da()));
			ptstAddPayroll.setDouble(6, Double.valueOf(emp.getPayrollInfo().getSalary_ta()));
			
		//System.out.println(ptstAddPayroll.toString());
			
			//System.out.println(addEmployee.toString());
			//System.out.println(addAddress.toString());
			
			status = addEmployee.executeUpdate();
			status=addAddress.executeUpdate();
			status=ptstAddPayroll.executeUpdate();
			  
			 
		         if(status > 0) {
		            System.out.println("Record is inserted successfully !!!");
		         }
			
			
		}
		
		catch(SQLException e) {
			throw new HrmsException(e,ErrorCode.DUPLICATE_EMPLOYEE);
		}
		catch(ClassNotFoundException e) {
			throw new HrmsException(e.getMessage());
		}
		catch(Exception he){
			throw new HrmsException(he,ErrorCode.DUPLICATE_EMPLOYEE);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public Employee getEmployeeDetails(String employeeID) throws HrmsException{
		
		Employee emp=new Employee();
		
		Connection con=null;		
		
		try {
			con = Connect.getConnection();			
			PreparedStatement ptst = con.prepareStatement(Constants.SQL_SELECTWITH_EMPID,ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			ptst.setString(1, employeeID);
			
			//System.out.println(ptst.toString());
			ResultSet rs = ptst.executeQuery();
			
			if(rs.next()) {
				rs.beforeFirst();
				
				while(rs.next()) {				
					
					emp.setEmpId(employeeID);
					Address homeAddr=new Address();
					Address officeAddr=new Address();									
					emp.setFirstName(rs.getNString(1));
					emp.setLastName(rs.getNString(2));
					emp.setEmailID(Optional.ofNullable(rs.getString(3)).orElse(""));
					emp.setDesignation(rs.getNString(4));	
					emp.setDepartmentID(rs.getInt(5));	
					
					
					Date date =Optional.ofNullable(rs.getDate(6)).orElseGet(() -> new java.sql.Date(0));
					//Date date = rs.getDate(6);
					LocalDate joinDate = Instant.ofEpochMilli(date.getTime())
						      .atZone(ZoneId.systemDefault())
						      .toLocalDate();
					
					emp.setJoinDate(joinDate);
					
					
					homeAddr.setPhoneNbr(Optional.ofNullable(rs.getString(7)).orElse(""));
					homeAddr.setAddressLine1(Optional.ofNullable(rs.getString(8)).orElse(""));
					homeAddr.setAddressLine2(Optional.ofNullable(rs.getString(9)).orElse(""));
					homeAddr.setCity(Optional.ofNullable(rs.getString(10)).orElse(""));
					homeAddr.setPinCode(Optional.ofNullable(rs.getInt(11)).orElse(0));
					emp.setHomeAddress(homeAddr);
					
					officeAddr.setPhoneNbr(Optional.ofNullable(rs.getString(12)).orElse(""));
					officeAddr.setAddressLine1(Optional.ofNullable(rs.getString(13)).orElse(""));
					officeAddr.setAddressLine2(Optional.ofNullable(rs.getString(14)).orElse(""));
					officeAddr.setCity(Optional.ofNullable(rs.getString(15)).orElse(""));
					officeAddr.setPinCode(Optional.ofNullable(rs.getInt(16)).orElse(0));
					
					emp.setOfficeAddress(officeAddr);
								
				
					
					Payroll payroll = new Payroll();
					payroll.setSalary_net(Optional.ofNullable(rs.getInt(17)).orElse(0));
					emp.setPayrollInfo(payroll);
			}	
				
			}
			else
				throw new HrmsException(ErrorCode.EMPLOYEE_NOT_FOUND);					
			
		}
		
		catch(SQLException sqle) {
			
			throw new HrmsException(ErrorCode.DB_ERROR);
		}
		catch(ClassNotFoundException cnf) {
			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
		catch(Exception he){
			he.printStackTrace();
			throw new HrmsException(he,ErrorCode.EMPLOYEE_NOT_FOUND);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return emp;
		
	}
	
	public List<Employee> getEmployees() throws HrmsException{
		
		
		List<Employee> empList = new ArrayList<Employee>();
		Connection con=null;
		
		try {
			con = Connect.getConnection();
			PreparedStatement ptst = con.prepareStatement(Constants.SQL_SELECT_EMPLOYEES,ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			System.out.println(ptst.toString());
			ResultSet rs = ptst.executeQuery();
			if(rs.next()) {
				
				rs.beforeFirst();
				while(rs.next()) {
					
					Employee emp= new Employee();
					Address homeAddr=new Address();
					Address officeAddr=new Address();
					Payroll payroll = new Payroll();
					
					//Optional.of(rs.getString(2)).orElse(" ");
					emp.setEmpId(rs.getString(1));				
					emp.setFirstName(rs.getNString(2));
					emp.setLastName(rs.getNString(3));
					emp.setEmailID(Optional.ofNullable(rs.getString(4)).orElse(""));
					emp.setDesignation(rs.getNString(5));	
					emp.setDepartmentID(rs.getInt(6));
					
					
					Date date =Optional.ofNullable(rs.getDate(7)).orElseGet(() -> new java.sql.Date(0));
					LocalDate joinDate = Instant.ofEpochMilli(date.getTime())
						      .atZone(ZoneId.systemDefault())
						      .toLocalDate();
					
					emp.setJoinDate(joinDate);
					
				//	Optional.ofNullable(null)
					homeAddr.setPhoneNbr(Optional.ofNullable(rs.getString(8)).orElse(""));
					homeAddr.setAddressLine1(Optional.ofNullable(rs.getString(9)).orElse(""));
					homeAddr.setAddressLine2(Optional.ofNullable(rs.getString(10)).orElse(""));
					homeAddr.setCity(Optional.ofNullable(rs.getString(11)).orElse(""));
					homeAddr.setPinCode(Optional.ofNullable(rs.getInt(12)).orElse(0));
					emp.setHomeAddress(homeAddr);
					
					officeAddr.setPhoneNbr(Optional.ofNullable(rs.getString(13)).orElse(""));
					officeAddr.setAddressLine1(Optional.ofNullable(rs.getString(14)).orElse(""));
					officeAddr.setAddressLine2(Optional.ofNullable(rs.getString(15)).orElse(""));
					officeAddr.setCity(Optional.ofNullable(rs.getString(16)).orElse(""));
					officeAddr.setPinCode(Optional.ofNullable(rs.getInt(17)).orElse(0));
					
					emp.setOfficeAddress(officeAddr);
								
					//emp.setHireDate(new java.util.Date());	
					
					payroll.setSalary_net(Optional.ofNullable(rs.getInt(18)).orElse(0));
					emp.setPayrollInfo(payroll);
					
					empList.add(emp);
					
					}		
			}
			else
				throw new HrmsException(ErrorCode.NO_EMPLOYEE_RECORDS);
			
			
						
			
		}
		
		catch(SQLException sqle) {
			
			throw new HrmsException(ErrorCode.DB_ERROR);
		}
		catch(ClassNotFoundException cnf) {
			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
		catch(Exception he){
			he.printStackTrace();
			throw new HrmsException(ErrorCode.NO_EMPLOYEE_RECORDS);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return empList;
	}
	
	public int modifyEmployee(Employee emp) throws HrmsException{
		Connection con=null;
		int status=0;
		
		try {
			con = Connect.getConnection();

			
			PreparedStatement updateEmp = con.prepareStatement(Constants.SQL_MODIFY_EMPLOYEE);
			
			updateEmp.setString(1, emp.getFirstName());
			updateEmp.setString(2, emp.getLastName());	
			updateEmp.setString(3, emp.getEmailID());
			updateEmp.setString(4, emp.getDesignation());
			updateEmp.setInt(5, emp.getDepartmentID());
			Date dt = Date.valueOf(emp.getJoinDate());
			updateEmp.setDate(6, dt);
			updateEmp.setString(7, emp.getEmpId());
			
			//System.out.println(updateEmp.toString());
			
			PreparedStatement ptstmt_updateAddress = con.prepareStatement(Constants.SQL_MODIFY_ADDRESS);
			
			ptstmt_updateAddress.setString(1, emp.getHomeAddress().getPhoneNbr());
			ptstmt_updateAddress.setString(2, emp.getHomeAddress().getAddressLine1());			
			ptstmt_updateAddress.setString(3, emp.getHomeAddress().getAddressLine2());
			ptstmt_updateAddress.setString(4, emp.getHomeAddress().getCity());
			ptstmt_updateAddress.setInt(5, emp.getHomeAddress().getPinCode());
			ptstmt_updateAddress.setString(6, emp.getOfficeAddress().getPhoneNbr());
			ptstmt_updateAddress.setString(7, emp.getOfficeAddress().getAddressLine1());			
			ptstmt_updateAddress.setString(8, emp.getOfficeAddress().getAddressLine2());
			ptstmt_updateAddress.setString(9, emp.getOfficeAddress().getCity());
			ptstmt_updateAddress.setInt(10, emp.getOfficeAddress().getPinCode());
			ptstmt_updateAddress.setString(11, emp.getEmpId());
			
			//System.out.println(ptstmt_updateAddress.toString());
			
			PreparedStatement ptstmt_updateSalary = con.prepareStatement(Constants.SQL_MODIFY_SALARYDETAILS);
			//"update tbl_salaryinfo set sal_net=?,sal_basic=?,sal_hra=?,sal_da=?,sal_ta=? where emp_id=?
			ptstmt_updateSalary.setDouble(1,Double.valueOf(emp.getPayrollInfo().getSalary_net()));
			ptstmt_updateSalary.setDouble(2,Double.valueOf(emp.getPayrollInfo().getSalary_basic()));
			ptstmt_updateSalary.setDouble(3,Double.valueOf(emp.getPayrollInfo().getSalary_hra()) );
			ptstmt_updateSalary.setDouble(4,Double.valueOf(emp.getPayrollInfo().getSalary_da()));
			ptstmt_updateSalary.setDouble(5, Double.valueOf(emp.getPayrollInfo().getSalary_ta()));
			ptstmt_updateSalary.setString(6, emp.getEmpId());
			System.out.println(ptstmt_updateSalary.toString());
			
		
			status=updateEmp.executeUpdate();
			status=ptstmt_updateAddress.executeUpdate();
			status = ptstmt_updateSalary.executeUpdate();
		
			
		}
		
		catch(SQLException sqle) {
			
			throw new HrmsException(ErrorCode.DB_ERROR);
		}
		catch(ClassNotFoundException cnf) {
			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
		catch(Exception he){
			
			throw new HrmsException(he,ErrorCode.EMPLOYEE_NOT_FOUND);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return status;
	}
	
	
	@Override
	public int deleteEmployee(String employeeID) throws HrmsException {
		
			Connection con=null;
		
			int status=0;
		
			try {
			con = Connect.getConnection();			
			
			PreparedStatement ptst_deleteemployee = con.prepareStatement(Constants.DELETE_RECORD);
			
			ptst_deleteemployee.setString(1, employeeID);
			//System.out.println(ptst_deleteemployee.toString());
			status = ptst_deleteemployee.executeUpdate();	
				
			
			}
			catch(SQLException sqle) {
				
				throw new HrmsException(ErrorCode.DB_ERROR);
			}
			catch(ClassNotFoundException cnf) {
				
				throw new HrmsException(ErrorCode.DRIVER_ERROR);
			}
			
			catch(Exception he){
				throw new HrmsException(he,ErrorCode.EMPLOYEE_NOT_FOUND);
			
			}
			finally {
				try {
					con.close();
				} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return status;
	}
	
	
	/**
	 * Method to get payroll details 
	 * parameter is employee id
	 * querying employee , address and payroll tables
	 * returns Employee object
	 */
	
	@Override
	public Employee getPayrollDetails(String employeeID) throws HrmsException {
		
			Employee emp=new Employee();		
			
			Connection con=null;		
		
			try {
				con = Connect.getConnection();
			
							
				PreparedStatement pstmt_getPayRollDetails = con.prepareStatement(Constants.SQL_SELECT_PAYSLIP_DETAILS,ResultSet.TYPE_SCROLL_INSENSITIVE,
						  ResultSet.CONCUR_UPDATABLE);
				pstmt_getPayRollDetails.setString(1, employeeID);
				//System.out.println(pstmt_getPayRollDetails.toString());
				ResultSet rs = pstmt_getPayRollDetails.executeQuery();
				
				if(rs.next()) {
					rs.beforeFirst();
					while(rs.next()) {
						emp.setEmpId(employeeID);						
						Address officeAddr=new Address();
						Payroll payroll = new Payroll();
						emp.setFirstName(rs.getNString(1));
						emp.setLastName(rs.getNString(2));
						emp.setEmailID(Optional.ofNullable(rs.getString(3)).orElse(""));
						emp.setDesignation(rs.getNString(4));	
						
						officeAddr.setCity(rs.getNString(5));
						emp.setOfficeAddress(officeAddr);
						payroll.setSalary_net(rs.getDouble(6));
						payroll.setSalary_basic(rs.getDouble(7));
						payroll.setSalary_hra(rs.getDouble(8));
						payroll.setSalary_da(rs.getDouble(9));
						payroll.setSalary_ta(rs.getDouble(10));
						
						emp.setPayrollInfo(payroll);
				
					}
				}
				else
					throw new HrmsException(ErrorCode.EMPLOYEE_NOT_FOUND);
				
					
			}
		
			catch(SQLException sqle) {
				
				throw new HrmsException(ErrorCode.DB_ERROR);
			}
			catch(ClassNotFoundException cnf) {
				
				throw new HrmsException(ErrorCode.DRIVER_ERROR);
			}
		catch(Exception he){
			
			throw new HrmsException(ErrorCode.EMPLOYEE_NOT_FOUND);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return emp;
	}
	

	
	@Override
	public List<Department> getDepartments() throws HrmsException {
				
		
		ArrayList<Department> deptList = new ArrayList<Department>();
		Connection con=null;		
		
		try {
			con = Connect.getConnection();
		
			PreparedStatement pstmt_getDepts = con.prepareStatement(Constants.SQL_SELECT_DEPARTMENTS,ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
		
			
			ResultSet dept_rs = pstmt_getDepts.executeQuery();
			
			if(dept_rs.next()) {
				dept_rs.beforeFirst();
				while(dept_rs.next()) {
					Department newDept = new Department();
					int deptID=dept_rs.getInt(1);
					String deptDescription=dept_rs.getString(2);
					newDept.setDept_id(deptID);
					newDept.setDept_description(deptDescription);;
					deptList.add(newDept);
					
				}
			}
			else
				throw new HrmsException(ErrorCode.NO_DEPARTMENTS_FOUND);
		
		}
		catch(SQLException sqle) {			
			throw new HrmsException(ErrorCode.DB_ERROR);
		}
		catch(ClassNotFoundException cnf) {			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
		catch(Exception he){		
			throw new HrmsException(ErrorCode.NO_DEPARTMENTS_FOUND);
		
	}
	finally {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
			
		return deptList;
	}

	@Override
	public int addPayrollInfo(String empId,Payroll payroll) throws HrmsException {
			Connection con=null;
		
			int status=0;
		
			try {
				con = Connect.getConnection();			
		
			
			PreparedStatement ptstAddPayroll = con.prepareStatement(Constants.SQL_ADD_SALARYDETAILS);			
			
			ptstAddPayroll.setString(1, empId);
			ptstAddPayroll.setDouble(2, payroll.getSalary_net());;
			ptstAddPayroll.setDouble(3, payroll.getSalary_basic());
			ptstAddPayroll.setDouble(4, payroll.getSalary_hra());
			ptstAddPayroll.setDouble(5, payroll.getSalary_da());
			ptstAddPayroll.setDouble(6, payroll.getSalary_ta());
			
			System.out.println(ptstAddPayroll.toString());
			
			status=ptstAddPayroll.executeUpdate();;
			  
			 
		         if(status > 0) {
		            System.out.println("Record is inserted successfully !!!");
		         }
			
			
		}
		
		catch(SQLException e) {
			throw new HrmsException(e,ErrorCode.DUPLICATE_EMPLOYEE);
		}
		catch(ClassNotFoundException e) {
			throw new HrmsException(e.getMessage());
		}
		catch(Exception he){
			throw new HrmsException(he,ErrorCode.DUPLICATE_EMPLOYEE);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public int addLoginCredentials(User user) throws HrmsException{
		Connection con=null;
		
		int status=0;
	
		try {
			con = Connect.getConnection();			
	
		
		PreparedStatement ptstAddLoginCred = con.prepareStatement(Constants.SQL_ADD_LOGINCREDENTIALS);			
		
		ptstAddLoginCred.setString(1, user.getUserName());
		ptstAddLoginCred.setString(2, user.getPassword());
		ptstAddLoginCred.setString(3,user.getRole());
		
		//System.out.println(ptstAddLoginCred.toString());
		status=ptstAddLoginCred.executeUpdate();		  
		
	}
	
	catch(SQLException e) {
		if(e instanceof SQLIntegrityConstraintViolationException) {
			throw new HrmsException(e,ErrorCode.DUPLICATE_USER);
	    }
		else
			throw new HrmsException(e.getCause().getMessage());
	}
	catch(ClassNotFoundException e) {
		throw new HrmsException(e.getCause().getMessage());
	}
	catch(Exception he){
		throw new HrmsException(he.getCause().getMessage());
		
	}
	finally {
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	return status;
	}
	
	public User verifyLoginCredentials(User user) throws HrmsException{
		
		Connection con=null;	
		User currUser=new User();
		
		try {
			con = Connect.getConnection();
			
			//DatabaseMetaData dbmd = (DatabaseMetaData) con.getMetaData();
			//boolean isSupported = dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
			
			PreparedStatement pstmt_verifyLogin = con.prepareStatement(Constants.SQL_SELECT_LOGINCREDENTIALS,ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
		
			pstmt_verifyLogin.setString(1, user.getUserName());
		
			ResultSet login_rs = pstmt_verifyLogin.executeQuery();
			
			if(login_rs.next()) {
				login_rs.beforeFirst();
				while(login_rs.next()) {				
					
					currUser.setUserName(login_rs.getString(1));
					currUser.setPassword(login_rs.getString(2));
					currUser.setRole(login_rs.getString(3));
					currUser.setValid(true);
					
				}			
				
			}
			else
				throw new HrmsException(ErrorCode.USER_NOT_FOUND);			
			
		}
		catch(SQLException sqle) {
			
			throw new HrmsException(ErrorCode.USER_INVALID);
		}
		catch(ClassNotFoundException cnf) {
			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
	catch(Exception he){
		
		throw new HrmsException(ErrorCode.USER_NOT_FOUND);
		
	}
	finally {
		try {
			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
			
			
		return currUser;
	}
	
	
	public List<Employee> getEmployeesDeptWise(int dept_id) throws HrmsException{
		List<Employee> empList = new ArrayList<Employee>();
		Connection con=null;
		
		try {
			con = Connect.getConnection();
			PreparedStatement ptst = con.prepareStatement(Constants.SQL_SELECTWITH_DEPTWISE,ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			ptst.setInt(1, dept_id);
			//System.out.println(ptst.toString());
			ResultSet rs = ptst.executeQuery();
			if(rs.next()) {
				
				rs.beforeFirst();
				while(rs.next()) {
					
					Employee emp= new Employee();
					Address homeAddr=new Address();
					Address officeAddr=new Address();
					Payroll payroll = new Payroll();
					
					//Optional.of(rs.getString(2)).orElse(" ");
					emp.setEmpId(rs.getString(1));				
					emp.setFirstName(rs.getNString(2));
					emp.setLastName(rs.getNString(3));
					emp.setEmailID(Optional.ofNullable(rs.getString(4)).orElse(""));
					emp.setDesignation(rs.getNString(5));	
					emp.setDepartmentID(rs.getInt(6));
					
					Date date =Optional.ofNullable(rs.getDate(7)).orElseGet(() -> new java.sql.Date(0));
					//Date date = rs.getDate(7);
					LocalDate joinDate = Instant.ofEpochMilli(date.getTime())
						      .atZone(ZoneId.systemDefault())
						      .toLocalDate();
					
					emp.setJoinDate(joinDate);
					
				//	Optional.ofNullable(null)
					homeAddr.setPhoneNbr(Optional.ofNullable(rs.getString(8)).orElse(""));
					homeAddr.setAddressLine1(Optional.ofNullable(rs.getString(9)).orElse(""));
					homeAddr.setAddressLine2(Optional.ofNullable(rs.getString(10)).orElse(""));
					homeAddr.setCity(Optional.ofNullable(rs.getString(11)).orElse(""));
					homeAddr.setPinCode(Optional.ofNullable(rs.getInt(12)).orElse(0));
					emp.setHomeAddress(homeAddr);
					
					officeAddr.setPhoneNbr(Optional.ofNullable(rs.getString(13)).orElse(""));
					officeAddr.setAddressLine1(Optional.ofNullable(rs.getString(14)).orElse(""));
					officeAddr.setAddressLine2(Optional.ofNullable(rs.getString(15)).orElse(""));
					officeAddr.setCity(Optional.ofNullable(rs.getString(16)).orElse(""));
					officeAddr.setPinCode(Optional.ofNullable(rs.getInt(17)).orElse(0));
					
					emp.setOfficeAddress(officeAddr);
					
					
					payroll.setSalary_net(Optional.ofNullable(rs.getInt(18)).orElse(0));
					emp.setPayrollInfo(payroll);
					
					empList.add(emp);
					
					}		
			}
			else
				throw new HrmsException(ErrorCode.NO_EMPLOYEE_RECORDS);
			
			
						
			
		}
		
		catch(SQLException sqle) {
			
			throw new HrmsException(ErrorCode.DB_ERROR);
		}
		catch(ClassNotFoundException cnf) {
			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
		catch(Exception he){
			
			throw new HrmsException(ErrorCode.NO_EMPLOYEE_RECORDS);
			
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return empList;
	}
	
	public boolean checkIfEmployee(String empID) throws HrmsException{
		boolean isPresent=false;
		Connection con=null;		
		
		try {
			con = Connect.getConnection();
		
			PreparedStatement ptst_getEmp = con.prepareStatement(Constants.SQL_SELECT_EMPID,ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			ptst_getEmp.setString(1, empID);
			
			ResultSet rs_getEmp = ptst_getEmp.executeQuery();
			
			if(rs_getEmp.next()) {
				isPresent=true;
			}
			else
				throw new HrmsException(ErrorCode.EMPLOYEE_NOT_FOUND);
		
		}
		catch(SQLException sqle) {			
			throw new HrmsException(ErrorCode.DB_ERROR);
		}
		catch(ClassNotFoundException cnf) {			
			throw new HrmsException(ErrorCode.DRIVER_ERROR);
		}
		catch(Exception he){		
			throw new HrmsException(ErrorCode.EMPLOYEE_NOT_FOUND);
		
	}
	finally {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		return isPresent;
		
	}
	

}
