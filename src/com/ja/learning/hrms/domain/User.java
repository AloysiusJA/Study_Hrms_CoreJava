package com.ja.learning.hrms.domain;

public class User {
	
	private String userName;
	private String password;
	private String role;
	private boolean isValid;
	
	

	public User() {
		
	}



	public User(String userName, String password, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	
	// Overriding using hashCode() method
//    @Override public int hashCode()
//    {
//        /* overriding hashCode() method
//        to check the length of the names */
//        return this.userName.length() % 10;
//    }
// 
//    // Boolean function to check
//    @Override public boolean equals(Object obj)
//    {
//        if (this == obj)
//            return true;
//        if (obj == null
//            || this.getClass() != obj.getClass())
//            return false;
//        User user1 = (User)obj;
// 
//        return this.userName.equals(user1.userName)
//            && this.password == user1.password ;
//    }


	public String getUserName() {
		return userName;
	}



	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", role=" + role + "]";
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public boolean isValid() {
		return isValid;
	}



	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	

}
