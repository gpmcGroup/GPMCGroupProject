package com.gpmc.modelClass;

import java.util.List;
import java.util.Map;

public class Turn {
	private Team ownerTeam;
	private Map<String, Move> dcFileMap;
//	private List<Move> MoveList;  //need to discuss again
	private int IDNumber;
	
	public Team getOwnerTeam() {
		return ownerTeam;
	}
	public void setOwnerTeam(Team ownerTeam) {
		this.ownerTeam = ownerTeam;
	}
	public Map<String, Move> getDcFileMap() {
		return dcFileMap;
	}
	public void setDcFileMap(Map<String, Move> dcFileMap) {
		this.dcFileMap = dcFileMap;
	}
	public int getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(int iDNumber) {
		IDNumber = iDNumber;
	}
}
