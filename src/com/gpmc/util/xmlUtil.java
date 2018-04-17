package com.gpmc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class xmlUtil {
	
	public static int Number_port = 10000;

	public static String Userpath = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();

	/**
	 * provide file path search function
	 * @param topicName 
	 * @param fileName
	 * @return
	 */
	public static String getTopicFilePath(String topicName, String fileName) {
		topicName = topicName.replaceAll("[^0-9a-zA-Z]", "_");
		if(fileName.equals("report.pdf"))
			return xmlUtil.class.getClassLoader().getResource("").getPath() + ".." + File.separator + topicName
					+ File.separator + fileName;
		else return xmlUtil.class.getClassLoader().getResource("").getPath() + ".." + File.separator + topicName
				+ File.separator + fileName + ".xml";
	}
	
	/**
	 * search username data from xml file 
	 * @param username
	 * @return user password
	 * @throws DocumentException
	 */
	// input username, return password
	public static String Userlogin(String username) throws DocumentException {
		// String path =
		// xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
		File file = new File(Userpath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);

		String xpath = "//user[username='" + username + "']/password";
		String password = doc.valueOf(xpath);
		return password;
	}
	
	/**
	 * query user detail
	 * @param username
	 * @return
	 * @throws DocumentException
	 */
	public static Element getUserDetail(String username) throws DocumentException {
		File file = new File(Userpath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		Element element = (Element) doc.selectSingleNode("//user[username='" + username + "']");
		return element;
	}
	
	/**
	 * change user data detail and write to xml file
	 * @param username
	 * @param tagName
	 * @param tagValue
	 * @throws DocumentException
	 */
	public static void modifyUserValue(String username, String tagName, String tagValue) throws DocumentException {
		Document doc = new SAXReader().read(new File(Userpath));
		Element ele = (Element) doc.selectSingleNode("//user[username='" + username + "']/" + tagName);
		ele.setText(tagValue);
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			XMLWriter output = new XMLWriter(new FileWriter(Userpath), format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * query team chat port
	 * @param topicName
	 * @param teamName
	 * @return
	 * @throws DocumentException
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
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get max turn information with fixed topicName
	 * @param topicName
	 * @return
	 * @throws DocumentException
	 */
	public static String getMaxTurn(String topicName) throws DocumentException {
		Document doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(topicName, "Topic")));
		return doc.selectSingleNode("//topic/maxTurn").getStringValue();
	}
	
	/**
	 * get present turn ID with fixed topicName
	 * @param topicName
	 * @return
	 * @throws DocumentException
	 */
	public static String getNowTurnID(String topicName) throws DocumentException {
		Document doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(topicName, "Turn")));
		List<Node> nodes = doc.selectNodes("//turn");
		if(nodes.get(nodes.size()-1).valueOf("status").equals("false")) {
			return nodes.get(nodes.size()-1).valueOf("@id");
		}else return "false";  //now turn status is true, which means it closed
	}
	
	/**
	 * add a move and store data into xml file
	 * @param topicName
	 * @param claimType
	 * @param claimDetails
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static boolean AddMove(String topicName, String claimType, String claimDetails, String username)
			throws IOException, DocumentException {
		String teamName = xmlUtil.findTeamName(topicName, username);
		String MovePath = xmlUtil.getTopicFilePath(topicName, "Move_" + teamName);
		int maxTurn = Integer.parseInt(getMaxTurn(topicName));
		int nowTurnID = Integer.parseInt(getNowTurnID(topicName));
		File file = new File(MovePath);
		Document document = new SAXReader().read(file);
		// Create the root element of xml file

		Element root = document.getRootElement();
		List<Node> nodes = root.selectNodes("//move");
		if(nowTurnID<=maxTurn) {  //
			int nowMoveID = nodes.size();
			Element move = DocumentHelper.createElement("move");
			move.addAttribute("id", nowMoveID+"");
			Element type = DocumentHelper.createElement("type");
			type.setText(claimType);
			Element user = DocumentHelper.createElement("createUserID");
			user.setText(username);
			Element createAt = DocumentHelper.createElement("createAt");
//			2018-04-04 20:53:45
			createAt.setText(new SimpleDateFormat("yyyy-mm-mm HH:mm:ss").format(new Date()));
			Element turn = DocumentHelper.createElement("turnID");
			turn.setText(nowTurnID+"");
			Element linkedFiles = DocumentHelper.createElement("linkedDCFileList");
			linkedFiles.setText("none");
			Element status = DocumentHelper.createElement("status");  
			status.setText("none");
			Element textBody = DocumentHelper.createElement("textBody");
			textBody.setText(claimDetails);
			move.add(type);
			move.add(user);
			move.add(createAt);
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
			return true;
		}else {
			return false;
		}
		// do a bunch of create elements then add them all to the root.


	}

}
