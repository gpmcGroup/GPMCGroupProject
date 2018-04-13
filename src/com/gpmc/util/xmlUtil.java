package com.gpmc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class xmlUtil {

	public static String Userpath = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();

	public static String getTopicFilePath(String topicName, String fileName) {
		topicName = topicName.replaceAll("[^0-9a-zA-Z]", "_");
		return xmlUtil.class.getClassLoader().getResource("").getPath() + ".." + File.separator + topicName
				+ File.separator + fileName + ".xml";
	}

	// input username, return password
	public static String Userlogin(String username) throws DocumentException {
		// String path =
		// xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
		File file = new File(Userpath);
		System.out.println("userpath : " + Userpath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);

		String xpath = "//user[username='" + username + "']/password";
		System.out.println(xpath);
		String password = doc.valueOf(xpath);
		return password;
	}

	public static Element getUserDetail(String username) throws DocumentException {
		File file = new File(Userpath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);

		Element element = (Element) doc.selectSingleNode("//user[username='" + username + "']");
		return element;
	}

	public static void modifyUserValue(String username, String tagName, String tagValue) throws DocumentException {
		Document doc = new SAXReader().read(new File(Userpath));
		Element ele = (Element) doc.selectSingleNode("//user[username='" + username + "']/" + tagName);
		System.out.println(ele.getStringValue());
		ele.setText(tagValue);
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			XMLWriter output = new XMLWriter(new FileWriter(Userpath), format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * param topicName, teamName
	 */
	public static String queryTeamChatPort(String topicName, String teamName) throws DocumentException {
		Document doc = new SAXReader().read(xmlUtil.getTopicFilePath(topicName, "Team"));
		return doc.valueOf("//team[@name='" + teamName + "']/chatPort");
	}

	public static String findTeamName(String topicName, String username) {
		// first find Team xml,find team user
		try {
			Document doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(topicName, "Team")));
			Element ele = doc.getRootElement();
			String name;
			for(Iterator<Element> it= ele.elementIterator();it.hasNext();) {
				Element e = it.next();
				for(Iterator<Element> usr = e.elementIterator();usr.hasNext();) {
					Element user = usr.next();
					if(user.valueOf("username").equals(username)) {
						name = e.attribute("name").getValue();
						return name;
					}
				}
			}
			return null;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public static DefaultTableModel fillMoveData(String topicName, String username, int turn) throws DocumentException {

		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("Type");
		columnNames.addElement("TurnID");
		columnNames.addElement("Created By");
		columnNames.addElement("Text Body");

		Vector<Vector> rowData = new Vector<Vector>();

		String teamName = xmlUtil.findTeamName(topicName, username);
		File file = new File(xmlUtil.getTopicFilePath(topicName, "Move_" + teamName));
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);

		// create the list

		Element root = doc.getRootElement();

		// iterate through child elements of root with element name "move"
		for (Iterator i = root.elementIterator("move"); i.hasNext();) {
			Element element = (Element) i.next();
			String type = element.elementText("type");
			String turnID = element.elementText("turnID");
			String createUserID = element.elementText("createUserID");
			String textBody = element.elementText("textBody");
			Vector<String> rowIt = new Vector<String>();
			int turnNumber = Integer.parseInt(turnID);
			if (turnNumber == turn) {
				rowIt.addElement(type);
				rowIt.addElement(turnID);
				rowIt.addElement(createUserID);
				rowIt.addElement(textBody);
				rowData.addElement(rowIt);
			}
		}
		return new DefaultTableModel(rowData, columnNames);
	}


	public static void AddMove(String topicName, String claimType, String claimDetails, String username)
			throws IOException, DocumentException {

		String teamName = xmlUtil.findTeamName(topicName, username);
		String MovePath = xmlUtil.getTopicFilePath(topicName, "Move_" + teamName);
		File file = new File(MovePath);
		Document document = new SAXReader().read(file);
		// Create the root element of xml file

		Element root = document.getRootElement();
		// do a bunch of create elements then add them all to the root.

		Element move = DocumentHelper.createElement("move");
		move.addAttribute("id", "++");

		Element type = DocumentHelper.createElement("type");
		type.setText(claimType);

		Element user = DocumentHelper.createElement("createUserID");
		user.setText(username);

		Element turn = DocumentHelper.createElement("turnID");
		turn.setText("1");

		Element linkedFiles = DocumentHelper.createElement("linkedDCFileList");
		linkedFiles.setText("none");

		Element status = DocumentHelper.createElement("status");
		status.setText("none");

		Element textBody = DocumentHelper.createElement("textBody");
		textBody.setText(claimDetails);

		move.add(type);
		move.add(user);
		move.add(turn);
		move.add(linkedFiles);
		move.add(status);
		move.add(textBody);
		root.add(move);

		document.setRootElement(root);

		// Create the compact format of xml document.
		OutputFormat format = OutputFormat.createPrettyPrint();
		// Create the xml writer by passing outputstream and format
		//get teamName
		
		
		XMLWriter writer = new XMLWriter(new FileWriter(MovePath), format);
		// Write to the xml document, flush and close
		writer.write(document);
		writer.flush();
		writer.close();

		Document testDoc = new SAXReader().read(new File(MovePath));
		Element ele = (Element) testDoc.selectSingleNode("//move[textBody='123abc']");
		System.out.println("添加后的move：" + testDoc.asXML());
	}

}
