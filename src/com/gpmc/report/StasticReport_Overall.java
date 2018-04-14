package com.gpmc.report;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import com.itextpdf.layout.element.IBlockElement;
import com.gpmc.util.xmlUtil;
public class StasticReport_Overall {
	private List moveList;
	private List turnIDList;
	private List teamNameList;
	private List userIDList;
	private List typeList;
	private List contentList;
	private String topicName;
	private String teamAName;
	private String teamAFilePath;
	private String teamBName;
	private String teamBFilePath;
	private int maxTurn;
	public StasticReport_Overall(String topicName) {
		this.topicName = topicName;
		moveList = new ArrayList<String>();
		turnIDList = new ArrayList<String>();
		teamNameList = new ArrayList<String>();
		userIDList = new ArrayList<String>();
		typeList = new ArrayList<String>();
		contentList = new ArrayList<String>();
	}
	
	/*
	 * receive topic name and return the first team's name to start the turn
	 */
	public String findFirstTeam() throws DocumentException {
		
		String path = "Topic.xml";
		Document topicDoc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(topicName, "Topic")));
		String xPath = "//topic[title='" + topicName + "']";
		//first check null
		if(!topicDoc.valueOf(xPath).equals("")) {
			Element ele =(Element)topicDoc.selectSingleNode(xPath);
			if(!ele.valueOf("//status").equals("end")) {
				return null;           // topic at pending or processing status, can not generate a stastic report
			}
			maxTurn = Integer.parseInt(ele.valueOf("//maxTurn"));
			teamAName = ele.valueOf("//teamA");
			teamBName = ele.valueOf("//teamB");
			teamAFilePath = xmlUtil.getTopicFilePath(topicName, "Move_" + teamAName);
			teamBFilePath = xmlUtil.getTopicFilePath(topicName, "Move_" + teamBName);
			Document docA = new SAXReader().read(new File(teamAFilePath));
			int t = Integer.parseInt(docA.valueOf("//move[@id='1']/turnID"));
			if(t == 1) {
				return teamAName;
			}else return teamBName;			
		
		}else {
			
			return null;
		}
	}
	
	public boolean generateReportData() throws DocumentException, FileNotFoundException {
		
		String firstTeamName = findFirstTeam();
		Document doc1,doc2;
		if(teamAName.equals(firstTeamName)) {
			doc1 = new SAXReader().read(new File(teamAFilePath));
			doc2 = new SAXReader().read(new File(teamBFilePath));
		}else {
			doc1 = new SAXReader().read(new File(teamBFilePath));
			doc2 = new SAXReader().read(new File(teamAFilePath));
		}
		
		for(int i = 1 ; i <= maxTurn; i++) {
			String xpath = "//move[turnID='" + i + "']";
			String teamName;
			System.out.println(xpath);
			List<Node> node;
			if(i%2 == 1) {
				node = doc1.selectNodes(xpath);
				teamName = teamAName;
			}else {
				node = doc2.selectNodes(xpath);
				teamName = teamBName;
			}
			for(int j = 0 ; j < node.size() ; j++) {
				moveList.add(String.valueOf(j));
				turnIDList.add(String.valueOf(i));
				teamNameList.add(teamName);
				userIDList.add(node.get(j).valueOf("createUserID"));
				typeList.add(node.get(j).valueOf("type"));
				contentList.add(node.get(j).valueOf("textBody"));
			}
		}
		new GenerateOverallReport(moveList,turnIDList,teamNameList,userIDList,typeList,contentList,topicName,teamAName,teamBName).getReport();
		return true;
	}
	public static void main(String args[]) throws DocumentException, FileNotFoundException {
		
		StasticReport_Overall sta = new StasticReport_Overall("Is Abortion is wrong?");
		sta.generateReportData();
	}
}
