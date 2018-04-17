package com.gpmc.modelClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.gpmc.util.xmlUtil;


/**
 * 
 * topic model class
 *
 */
public class Topic {
	private String title;
	private String teamA;
	private String teamB;
	private String startTime;
	private String maxTurn;
	private String turnCycleFrequency; // miliseconds
	private String presentTurnOwner;
	private String changeTurnTimeLeft; // miliseconds
	private String status; // three value: pending, progressing, end
	private String content;
	private String winner;

	/**
	 * get topic content detail
	 * @return return topic content string
	 */
	public String getContent() {
		if (content == null) {
			String xpath = "//topic[title='" + title + "']";
			try {
				System.out.println(xmlUtil.getTopicFilePath(title,"Topic"));
				Document doc = new SAXReader()
						.read(new File(xmlUtil.getTopicFilePath(title,"Topic")));

				// 1.
				Element ele = (Element) doc.selectSingleNode(xpath);

				return ele.getStringValue();
				// 2.
				// String x = doc.valueOf(xpath);
				// return x;
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String tile) {
		this.title = tile;
	}

	public String getTeamA() {
		return teamA;
	}

	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}

	public String getTeamB() {
		return teamB;
	}

	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getMaxTurn() {
		return maxTurn;
	}

	public void setMaxTurn(String maxTurn) {
		this.maxTurn = maxTurn;
	}

	public String getTurnCycleFrequency() {
		return turnCycleFrequency;
	}

	public void setTurnCycleFrequency(String turnCycleFrequency) {
		this.turnCycleFrequency = turnCycleFrequency;
	}

	public String getPresentTurnOwner() {
		return presentTurnOwner;
	}

	public void setPresentTurnOwner(String presentTurnOwner) {
		this.presentTurnOwner = presentTurnOwner;
	}

	public String getChangeTurnTimeLeft() {
		return changeTurnTimeLeft;
	}

	public void setChangeTurnTimeLeft(String changeTurnTimeLeft) {
		this.changeTurnTimeLeft = changeTurnTimeLeft;
	}

	public String isStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	
	public String getAll() {
		String xpath = "//topic[title='" + title + "']";
		try {
			
			System.out.println(xmlUtil.getTopicFilePath(title, "Topic"));
			System.out.println(xpath);
			Document doc = new SAXReader()
					.read(new File(xmlUtil.getTopicFilePath(title, "Topic")));
			
//			System.out.println("houtaidsad ada a asd das: " +doc.asXML());
			
			// 1.
			Element ele = (Element) doc.selectSingleNode(xpath);
			

			return ele.asXML();
			// 2.
			// String x = doc.valueOf(xpath);
			// return x;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}


	public void writeNewTopicXml() throws IOException {
		String topicName = title.replaceAll("[^0-9a-zA-Z]", "_");
		String folderpath= xmlUtil.class.getClassLoader().getResource("").getPath() + ".." + File.separator+topicName;
		File fl = new File(folderpath);
		if(fl.exists()==false) {
			fl.mkdir();
		}

		String filePath = folderpath +File.separator+ "Topic.xml";
//			File fTopic = new File(filePath);
		
		
		Document doc = DocumentHelper.createDocument();
		
		Element root = DocumentHelper.createElement("root");
		
		Element topic = root.addElement("topic");
		topic.addAttribute("id", "1");
		Element ele_title = topic.addElement("title");
		ele_title.setText(title);
		Element ele_teamA = topic.addElement("teamA");
		ele_teamA.setText(teamA);
		Element ele_teamB = topic.addElement("teamB");
		ele_teamB.setText(teamB);
		Element ele_startTime = topic.addElement("startTime");
		ele_startTime.setText(startTime);
		Element ele_maxTurn = topic.addElement("maxTurn");
		ele_maxTurn.setText(maxTurn);
		Element ele_turnCycl = topic.addElement("turnCycleFrequency");
		ele_turnCycl.setText(turnCycleFrequency);
		Element ele_presenTurnOwner = topic.addElement("presentTurnOwner");
		ele_presenTurnOwner.setText(teamA);
		Element ele_changeTurnLeft = topic.addElement("changeTurnTimeLeft");
		ele_changeTurnLeft.setText(turnCycleFrequency);
		Element ele_status = topic.addElement("status");
		ele_status.setText("pending");
		Element ele_winner = topic.addElement("winner");
		ele_winner.setText("null");
		Element ele_content = topic.addElement("content");
		ele_content.setText(content);
		
					
		doc.setRootElement(root);
		
//		System.out.println(doc.asXML());
		OutputFormat format = OutputFormat.createPrettyPrint();
		// Create the xml writer by passing outputstream and format
		//get teamName
		XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
		// Write to the xml document, flush and close
		writer.write(doc);
		writer.flush();
		writer.close();
		
	}
	
	public void changeTopic() throws IOException {
		try {
			String filePath = xmlUtil.getTopicFilePath(title,"Topic");
			Document doc = new SAXReader()
					.read(new File(xmlUtil.getTopicFilePath(title,"Topic")));
			doc.clearContent();
			Element root = DocumentHelper.createElement("root");
			
			Element topic = root.addElement("topic");
			topic.addAttribute("id", "1");
			Element ele_title = topic.addElement("title");
			ele_title.setText(title);
			Element ele_teamA = topic.addElement("teamA");
			ele_teamA.setText(teamA);
			Element ele_teamB = topic.addElement("teamB");
			ele_teamB.setText(teamB);
			Element ele_startTime = topic.addElement("startTime");
			ele_startTime.setText(startTime);
			Element ele_maxTurn = topic.addElement("maxTurn");
			ele_maxTurn.setText(maxTurn);
			Element ele_turnCycl = topic.addElement("turnCycleFrequency");
			ele_turnCycl.setText(turnCycleFrequency);
			Element ele_presenTurnOwner = topic.addElement("presentTurnOwner");
			ele_presenTurnOwner.setText(teamA);
			Element ele_changeTurnLeft = topic.addElement("changeTurnTimeLeft");
			ele_changeTurnLeft.setText(turnCycleFrequency);
			Element ele_status = topic.addElement("status");
			ele_status.setText("pending");
			Element ele_winner = topic.addElement("winner");
			ele_winner.setText("null");
			Element ele_content = topic.addElement("content");
			ele_content.setText(content);
			
						
			doc.setRootElement(root);

			System.out.println(doc.asXML());
			OutputFormat format = OutputFormat.createPrettyPrint();
			// Create the xml writer by passing outputstream and format
			//get teamName
			XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
			// Write to the xml document, flush and close
			writer.write(doc);
			writer.flush();
			writer.close();
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
}