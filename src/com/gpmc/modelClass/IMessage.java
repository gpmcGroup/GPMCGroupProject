package com.gpmc.modelClass;

import java.util.Date;


/**
 * 
 * Message class
 *
 */
public class IMessage {
	private String message;
	private Date Time;
	private User createUser;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	
}
