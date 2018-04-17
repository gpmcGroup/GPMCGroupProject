package com.gpmc.modelClass;

import java.util.List;
import java.util.Map;

/**
 * 
 * Student model class
 *
 */
public class Student extends User{
	private Map<String, Team> Teams;
	public Map<String, Team> getTeams() {
		return Teams;
	}
	public void setTeams(Map<String, Team> teams) {
		Teams = teams;
	}
	public List<Move> getCreatedDCFiles() {
		return CreatedDCFiles;
	}
	public void setCreatedDCFiles(List<Move> createdDCFiles) {
		CreatedDCFiles = createdDCFiles;
	}
	private List<Move> CreatedDCFiles;
}
