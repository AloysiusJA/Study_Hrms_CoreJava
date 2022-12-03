package com.ja.learning.hrms.domain;

public class Payroll {

	private double salary_net;
	private double salary_basic;
	private double salary_hra;
	private double salary_da;
	private double salary_ta;
	
	
	
	public double getSalary_net() {
		return salary_net;
	}



	public void setSalary_net(double salary_net) {
		this.salary_net = salary_net;
	}



	public double getSalary_basic() {
		return salary_basic;
	}



	public void setSalary_basic(double salary_basic) {
		this.salary_basic = salary_basic;
	}



	public double getSalary_hra() {
		return salary_hra;
	}



	public void setSalary_hra(double salary_hra) {
		this.salary_hra = salary_hra;
	}



	public double getSalary_da() {
		return salary_da;
	}



	public void setSalary_da(double salary_da) {
		this.salary_da = salary_da;
	}



	public double getSalary_ta() {
		return salary_ta;
	}



	public void setSalary_ta(double salary_ta) {
		this.salary_ta = salary_ta;
	}



	public Payroll() {}
}
		