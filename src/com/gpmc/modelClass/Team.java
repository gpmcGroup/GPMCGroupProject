package com.gpmc.modelClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.gpmc.util.xmlUtil;

public class Team {
	private String teamName;
	private List<String> teamMemberList;
	private List<Topic> topicList;
	private Student teamLeader;
	private List<Move> turnFileList;
	private String topicView; // replace team
	private List<Element> eleList;
	private String setTeamBelonged;

	public String getTeamName() {
		return teamName;
	}
	
	public void setTitleName(String str) {
		this.setTeamBelonged = str;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamMemberList() {
		String xpath = new String("//team[@name ='" + teamName + "']/user/username");
		eleList = new ArrayList<Element>();
		String s = "";

		try {
			Document doc = new SAXReader()
					.read(new File(xmlUtil.getTopicFilePath(setTeamBelonged, "Team")));

			for (Node nd : doc.selectNodes(xpath)) {
				eleList.add((Element) nd);
			}
			// "//team[@name='York Duck']/user"
			for (Element e : eleList) {
				s += e.getStringValue() + ' ';
			}
			return s;
			//
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void setTeamMemberList(List<String> teamMemberList) {
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