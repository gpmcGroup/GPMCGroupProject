package com.gpmc.modelClass;

import java.util.List;

/**
 * 
 * @author fighting_frank
 *
 */
public class Move {
	private int idNumber;
	private String type;
	private String createUserID;
	private int turnID;
	private int moveID;
	private boolean status;
	private List<Move> LinkedDCFileList;
	
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
	public List<Move> getLinkedDCFileList() {
		return LinkedDCFileList;
	}
	public void setLinkedDCFileList(List<Move> linkedDCFileList) {
		LinkedDCFileList = linkedDCFileList;
	}
	
}
