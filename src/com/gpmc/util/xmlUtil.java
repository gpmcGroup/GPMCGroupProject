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
	public static String TeamPath = xmlUtil.class.getClassLoader().getResource("Team.xml").getPath();
	public static String MovePath = xmlUtil.class.getClassLoader().getResource("Move_York Bird.xml").getPath();
	public static String TurnPath = xmlUtil.class.getClassLoader().getResource("Turn.xml").getPath();
	
	
	
	public static String getTopicFilePath(String topicName,String fileName) {
		topicName = topicName.replaceAll("[^0-9a-zA-Z]", "_");
		return xmlUtil.class.getClassLoader().getResource("").getPath() + ".." + File.separator + topicName + File.separator + fileName +".xml";
	}
	
	
	//input username, return password
	public static String Userlogin(String username) throws DocumentException {
//		String path = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
		File file = new File(Userpath);
		System.out.println("userpath : " +Userpath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		
		String xpath = "//user[username='" + username +"']/password";
		System.out.println(xpath);
		String password = doc.valueOf(xpath);
		return password;
	}
	
	public static Element getUserDetail(String username) throws DocumentException {
		File file = new File(Userpath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		
		Element element = (Element)doc.selectSingleNode("//user[username='" + username + "']");
		return element;
	}
	
	public static void modifyUserValue(String username, String tagName,String tagValue) throws DocumentException{
		Document doc = new SAXReader().read(new File(Userpath));
		Element ele = (Element)doc.selectSingleNode("//user[username='" + username + "']/" + tagName);
		System.out.println(ele.getStringValue());
		ele.setText(tagValue);
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			XMLWriter output = new XMLWriter(new FileWriter(Userpath),format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String queryTeamChatPort(String teamName) throws DocumentException {
		Document doc = new SAXReader().read(new File(TeamPath));
//		Element ele = 
		return doc.valueOf("//team[@name='" + teamName + "']/chatPort");
	}
	
	public static DefaultTableModel fillMoveData() throws DocumentException {
		
		 Vector<Vector> rowData = new Vector<Vector>();
		 Vector<String> columnNames = new Vector<String>();
		    columnNames.addElement("Type");
		    columnNames.addElement("TurnID");
		    columnNames.addElement("Created By");
		    columnNames.addElement("Text Body");
		
		File file = new File(MovePath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
			
		//create the list
		
		 Element root = doc.getRootElement();

		    // iterate through child elements of root with element name "move"
		    for ( Iterator i = root.elementIterator( "move" ); i.hasNext(); ) {
		        Element element = (Element) i.next();
		        String type = element.elementText("type");
		        String turnID = element.elementText("turnID");
		        String createUserID = element.elementText("createUserID");
		        String textBody = element.elementText("textBody");
		        Vector<String> rowIt = new Vector<String>();
		        rowIt.addElement(type);
		        rowIt.addElement(turnID);
		        rowIt.addElement(createUserID);
		        rowIt.addElement(textBody);
		        rowData.addElement(rowIt);
		        
		    }
    
	    return new DefaultTableModel(rowData, columnNames);
	 
	}
	
	public static DefaultTableModel fillMoveData(int turn) throws DocumentException {
		
		 
		 Vector<String> columnNames = new Vector<String>();
		    columnNames.addElement("Type");
		    columnNames.addElement("TurnID");
		    columnNames.addElement("Created By");
		    columnNames.addElement("Text Body");
		    
		    Vector<Vector> rowData = new Vector<Vector>();
		
		File file = new File(MovePath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
			
		//create the list
		
		 Element root = doc.getRootElement();

		    // iterate through child elements of root with element name "move"
		    for ( Iterator i = root.elementIterator( "move" ); i.hasNext(); ) {
		        Element element = (Element) i.next();
		        String type = element.elementText("type");
		        String turnID = element.elementText("turnID");
		        String createUserID = element.elementText("createUserID");
		        String textBody = element.elementText("textBody");
		        Vector<String> rowIt = new Vector<String>();
		        int turnNumber = Integer.parseInt(turnID);
		        if(turnNumber == turn) {
		        rowIt.addElement(type);
		        rowIt.addElement(turnID);
		        rowIt.addElement(createUserID);
		        rowIt.addElement(textBody);
		        rowData.addElement(rowIt);
		        }
		        
		    }
		    
		   
   
	    return new DefaultTableModel(rowData, columnNames);
	 
	}

	public static DefaultTableModel fillTurnData() throws DocumentException {
		
		Vector<Vector> rowData = new Vector<Vector>();
		 Vector<String> columnNames = new Vector<String>();
		    columnNames.addElement("ownerTeam");
		    columnNames.addElement("turnID");
		    columnNames.addElement("dcFileMap");
		
		File file = new File(TurnPath);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
			
		//create the list
		
		 Element root = doc.getRootElement();

		    // iterate through child elements of root with element name "move"
		    for ( Iterator i = root.elementIterator( "turn" ); i.hasNext(); ) {
		        Element element = (Element) i.next();
		        String type = element.elementText("ownerTeam");
		        String turnID = element.elementText("turnID");
		        String createUserID = element.elementText("dcFileMap");
		        Vector<String> rowIt = new Vector<String>();
		        rowIt.addElement(type);
		        rowIt.addElement(turnID);
		        rowIt.addElement(createUserID);
		        rowData.addElement(rowIt);
		        
		    }
   
	    return new DefaultTableModel(rowData, columnNames);
	}
	
	public static void AddMove(String claimType, String claimDetails, String username) throws IOException, DocumentException {
		
		 Document document = new SAXReader().read(new File(MovePath));
	        // Create the root element of xml file
		 
		 
		 	Element root = document.getRootElement(); 
		 	//do a bunch of create elements then add them all to the root.
		 	
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
	        XMLWriter writer = new XMLWriter(new FileWriter(MovePath), format);
	        // Write to the xml document, flush and close
	        writer.write(document);
	        writer.flush();
	        writer.close();
	        
	        
	        Document testDoc = new SAXReader().read(new File(MovePath));
	        Element ele = (Element)testDoc.selectSingleNode("//move[textBody='123abc']");
	        System.out.println(testDoc.asXML());
	}
	

}
