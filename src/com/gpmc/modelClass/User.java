package com.gpmc.modelClass;

import org.dom4j.DocumentException;

import com.gpmc.util.xmlUtil;

//import org.dom4j.DocumentException;

//import com.gpmc.util.xmlUtil;

public abstract class User {
	private String ID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String fullName;
	private String department;
	private boolean loginStatus; 
	
	/*
	 * true logout successful, false logout failed. 
	 */
	
	public boolean logout() {
		//your code;
		if(this.loginStatus == true)
			this.loginStatus = false;
		return true;
	}
	
	/*
	 * 1.check username and password first 2. check loginStatus
	 */
	public boolean login(String username, String password) throws DocumentException {
		
		String pass = xmlUtil.Userlogin(username);
		System.out.println(pass);
		if(pass == null)
			return false;
		else if(pass.equals(password)) {
			this.loginStatus = true;
			return true;
		}else return false;
	}
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
