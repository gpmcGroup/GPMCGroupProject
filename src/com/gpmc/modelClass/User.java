package com.gpmc.modelClass;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gpmc.util.xmlUtil;

/**
 * 
 * User model class
 *
 */
public abstract class User {
	private String ID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String fullName;
	private String department;
	private boolean loginStatus; 
	
	/**
	 * 
	 * true logout successful, false logout failed. 
	 */
	
	public boolean logout() throws DocumentException {
		//your code;
		if(this.loginStatus == true) {
			this.loginStatus = false;
			Document doc = new SAXReader().read(new File(xmlUtil.class.getClassLoader().getResource("User.xml").getPath()));
			Element ele = (Element)doc.selectSingleNode("//user[username='" + username + "']/loginStatus");
			ele.setText("false");
		}
		return true;
	}
	
	/**
	 * 
	 * 1.check username and password first 2. check loginStatus
	 */
	public boolean login(String username, String password) throws DocumentException {
		
		String pass = xmlUtil.Userlogin(username);
		System.out.println(username);
		if(pass == null)
			return false;
		else if(pass.equals(password)) {
			this.loginStatus = true;
			xmlUtil.modifyUserValue(username, "loginStatus", "true");
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
