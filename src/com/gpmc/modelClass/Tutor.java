package com.gpmc.modelClass;

import java.util.Map;

public class Tutor extends User{
	private Map<String,Topic> CreatedTopics;
	private Map<String,Team> ManagementTeams;
	public Map<String, Topic> getCreatedTopics() {
		return CreatedTopics;
	}
	public void setCreatedTopics(Map<String, Topic> createdTopics) {
		CreatedTopics = createdTopics;
	}
	public Map<String, Team> getManagementTeams() {
		return ManagementTeams;
	}
	public void setManagementTeams(Map<String, Team> managementTeams) {
		ManagementTeams = managementTeams;
	}
	
	// the parameter is decided by concrete coder.
	public void createdNewTopic(Topic topic) {
		
	}
	
	// the parameter is decided by concrete coder.
	public void editTopic(Topic topic) {
		
	}
	
}
