package com.gpmc.modelClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.gpmc.util.xmlUtil;

public class Team {
	private String setTeamBelonged;
	private String teamName;
	private List<String> teamMemberList;
	private List<Topic> topicList;
	private String teamLeader;
	private List<Move> turnFileList;
	private String topicView; // replace team
	private List<Element> eleList;
	private List<String> teamMemberListB;
	private String teamBName;
	private String teamBLeader;

	public void setAnotherTeam(List<String> ls, String teambl, String teambn) {
		this.teamMemberListB = ls;
		this.teamBLeader = teambl;
		this.teamBName = teambn;
	}

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
			Document doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(setTeamBelonged, "Team")));

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

	public String getTeamLeader() {
		String xpath = "//team[@name='" + teamName + "']/user[@teamLeader='true']/username";
		try {
			System.out.println(xmlUtil.getTopicFilePath(setTeamBelonged, "team"));
			Document doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(setTeamBelonged, "team")));

			// 1.
			Element ele = (Element) doc.selectSingleNode(xpath);
			teamLeader = ele.getStringValue();
			// 2.
			// String x = doc.valueOf(xpath);
			// return x;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teamLeader;

	}

	public void setTeamLeader(String teamLeader) {
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

	// private String setTeamBelonged;
	// private String teamName;
	// private List<String> teamMemberList;
	// private String teamLeader;
	// private List<String> teamMemberListB;
	// private String teamBName;
	// private String teamBLeader;
	
	public void writeNewTeamXml() throws IOException {

		String filePath = xmlUtil.getTopicFilePath(setTeamBelonged, "Team");
		Document doc = DocumentHelper.createDocument();

		Element root = DocumentHelper.createElement("root");

		Element ele_team = root.addElement("team");
		ele_team.addAttribute("id", "1").addAttribute("name", teamName);

		int usercount = 1;
		Element ele;
		Element ele1;
		for (String str : teamMemberList) {
			ele = ele_team.addElement("user");
			ele.addAttribute("id", String.valueOf(usercount));
			ele1 = ele.addElement("username");
			ele1.setText(str);
			if(str.equals(teamLeader)) {
				ele.addAttribute("teamLeader", "true");
			}
			usercount++;
		}
		Element ele_port = ele_team.addElement("chatPort");
		ele_port.setText(String.valueOf(xmlUtil.Number_port++));
		
		usercount = 1;
		Element ele_teamB = root.addElement("team");
		ele_teamB.addAttribute("id", "2").addAttribute("name", teamBName);
		for (String str : teamMemberListB) {
			ele = ele_teamB.addElement("user");
			ele.addAttribute("id", String.valueOf(usercount));
			ele1 = ele.addElement("username");
			ele1.setText(str);
			if(str.equals(teamBLeader)) {
				ele.addAttribute("teamLeader", "true");
			}
			usercount++;
		}
		Element ele_port1 = ele_teamB.addElement("chatPort");
		ele_port1.setText(String.valueOf(xmlUtil.Number_port++));
		
		doc.setRootElement(root);

		System.out.println(doc.asXML());
		OutputFormat format = OutputFormat.createPrettyPrint();
		// Create the xml writer by passing outputstream and format
		// get teamName
		XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
		// Write to the xml document, flush and close
		writer.write(doc);
		writer.flush();
		writer.close();
	}
	public void changeNewTeamXml() throws IOException {
		try {
			String filePath = xmlUtil.getTopicFilePath(setTeamBelonged,"Team");
			Document doc = new SAXReader()
					.read(new File(xmlUtil.getTopicFilePath(setTeamBelonged,"Team")));
			doc.clearContent();
			Element root = DocumentHelper.createElement("root");

			Element ele_team = root.addElement("team");
			ele_team.addAttribute("id", "1").addAttribute("name", teamName);

			int usercount = 1;
			Element ele;
			Element ele1;
			for (String str : teamMemberList) {
				ele = ele_team.addElement("user");
				ele.addAttribute("id", String.valueOf(usercount));
				ele1 = ele.addElement("username");
				ele1.setText(str);
				if(str.equals(teamLeader)) {
					ele.addAttribute("teamLeader", "true");
				}
				usercount++;
			}
			Element ele_port = ele_team.addElement("chatPort");
			ele_port.setText(String.valueOf(xmlUtil.Number_port++));
			
			usercount = 1;
			Element ele_teamB = root.addElement("team");
			ele_teamB.addAttribute("id", "2").addAttribute("name", teamBName);
			for (String str : teamMemberListB) {
				ele = ele_teamB.addElement("user");
				ele.addAttribute("id", String.valueOf(usercount));
				ele1 = ele.addElement("username");
				ele1.setText(str);
				System.out.println(teamBLeader);
				if(str.equals(teamBLeader)) {
					ele.addAttribute("teamLeader", "true");
				}
				usercount++;
			}
			Element ele_port1 = ele_teamB.addElement("chatPort");
			ele_port1.setText(String.valueOf(xmlUtil.Number_port++));
			
			doc.setRootElement(root);

			System.out.println(doc.asXML());
			OutputFormat format = OutputFormat.createPrettyPrint();
			// Create the xml writer by passing outputstream and format
			// get teamName
			XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
			// Write to the xml document, flush and close
			writer.write(doc);
			writer.flush();
			writer.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}