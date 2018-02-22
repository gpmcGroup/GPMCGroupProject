package com.gpmc.util;


import org.dom4j.DocumentException;

import com.gpmc.modelClass.Student;
import com.gpmc.modelClass.User;

public class loginTest {
	public static void main(String args[]) throws DocumentException {
		
		
		String username = "frank";
		String password = "12345";
		
		User user = new Student();
		if(user.login(username, password)) {
			System.out.println("Login successful!");
		}else {
			System.out.println("pls check your username and password again!");
		}
		
	}
}
