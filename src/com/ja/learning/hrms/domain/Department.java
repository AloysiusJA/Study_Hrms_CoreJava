package com.ja.learning.hrms.domain;

import java.util.HashMap;
import java.util.Map;

public class Department {
	
	@Override
	public String toString() {
		return "Department [dept_id=" + dept_id + ", dept_description=" + dept_description + "]";
	}



	private int dept_id;
	private String dept_description;
	
	

	public Department() {
		
	}



	public int getDept_id() {
		return dept_id;
	}



	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}



	public String getDept_description() {
		return dept_description;
	}



	public void setDept_description(String dept_description) {
		this.dept_description = dept_description;
	}

}
