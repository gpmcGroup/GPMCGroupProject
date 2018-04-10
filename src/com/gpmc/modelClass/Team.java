package com.gpmc.modelClass;

import java.util.List;

public class Team {
	private String teamName;
	private List<Student> teamMemberList;
	private List<Topic> topicList;
	private Student teamLeader;
	private List<Move> turnFileList;
	private String topicView;   //replace team
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public List<Student> getTeamMemberList() {
		return teamMemberList;
	}
	public void setTeamMemberList(List<Student> teamMemberList) {
		this.teamMemberList = teamMemberList;
	}
	public List<Topic> getTopicList() {
		return topicList;
	}
	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}
	public Student getTeamLeader() {
		return teamLeader;
	}
	public void setTeamLeader(Student teamLeader) {
		this.teamLeader = teamLeader;
	}
	public List<Move> getTurnFileList() {
		return turnFileList;
	}
	public void setTurnFileList(List<Move> turnFileList) {
		this.turnFileList = turnFileList;
	}
	public String getTopicView() {
		return topicView;
	}
	public void setTopicView(String topicView) {
		this.topicView = topicView;
	}
}
