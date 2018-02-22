package com.gpmc.modelClass;

import java.util.List;

public class DCFile {
	private int idNumber;
	private String type;
	private String createUserID;
	private int turnID;
	private int moveID;
	private boolean status;
	private List<DCFile> LinkedDCFileList;
	
	public int getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}
	public int getTurnID() {
		return turnID;
	}
	public void setTurnID(int turnID) {
		this.turnID = turnID;
	}
	public int getMoveID() {
		return moveID;
	}
	public void setMoveID(int moveID) {
		this.moveID = moveID;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<DCFile> getLinkedDCFileList() {
		return LinkedDCFileList;
	}
	public void setLinkedDCFileList(List<DCFile> linkedDCFileList) {
		LinkedDCFileList = linkedDCFileList;
	}
	
}
