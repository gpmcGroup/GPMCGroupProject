package com.gpmc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
public class xmlUtil {
	
	public static String Userpath = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
	public static String TeamPath = xmlUtil.class.getClassLoader().getResource("Team.xml").getPath();
	public static String MovePath = xmlUtil.class.getClassLoader().getResource("Move.xml").getPath();
	public static String TurnPath = xmlUtil.class.getClassLoader().getResource("Turn.xml").getPath();
	
	//input username, return password
	public static String Userlogin(String username) throws DocumentException {
//		String path = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
		File file = new File(Userpath);
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
		String path = xmlUtil.class.getClassLoader().getResource("Move.xml").getPath();
		File file = new File(path);
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		
		//don't know how to iterate through the values in the xml?? how to get all db values / aka how to know how many values are in the xml db at any one time..
		String xpath1 = "//move[turnID='1']/type";
		String value1 = doc.valueOf(xpath1);
		System.out.println(value1);
		
		String xpath2 = "//move[turnID='1']/turnID";
		String value2 = doc.valueOf(xpath2);
		System.out.println(value2);
		
		String xpath3 = "//move[turnID='1']/createUserID";
		String value3 = doc.valueOf(xpath3);
		System.out.println(value3);
		
		String xpath4 = "//move[turnID='1']/textBody";
		String value4 = doc.valueOf(xpath4);
		System.out.println(value4);
		
		String xpath5 = "//move[turnID='2']/type";
		String value5 = doc.valueOf(xpath5);
		System.out.println(value5);
		
		String xpath6 = "//move[turnID='2']/turnID";
		String value6 = doc.valueOf(xpath6);
		System.out.println(value6);
		
		String xpath7 = "//move[turnID='2']/createUserID";
		String value7 = doc.valueOf(xpath7);
		System.out.println(value7);
		
		String xpath8 = "//move[turnID='2']/textBody";
		String value8 = doc.valueOf(xpath8);
		System.out.println(value8);
		
		
		Vector<String> rowOne = new Vector<String>();
	    rowOne.addElement(value1);
	    rowOne.addElement(value2);
	    rowOne.addElement(value3);
	    rowOne.addElement(value4);
	    
	    Vector<String> rowTwo = new Vector<String>();
	    rowTwo.addElement(value5);
	    rowTwo.addElement(value6);
	    rowTwo.addElement(value7);
	    rowTwo.addElement(value8);
	    
	    Vector<Vector> rowData = new Vector<Vector>();
	    rowData.addElement(rowOne);
	    rowData.addElement(rowTwo);
	    
	    Vector<String> columnNames = new Vector<String>();
	    columnNames.addElement("Type");
	    columnNames.addElement("TurnID");
	    columnNames.addElement("Created By");
	    columnNames.addElement("Text Body");
	    
	    return new DefaultTableModel(rowData, columnNames);
	 
	}
	
	public static void AddMove(String claimType, String claimDetails, String username) throws IOException, DocumentException {
		
		 Document document = new SAXReader().read(new File(MovePath));
	        // Create the root element of xml file
		 
		 	Element root = document.getRootElement(); 
		 	//do a bunch of create elements then add them all to the root.
		 	
		 	Element move = DocumentHelper.createElement("move");
		 	move.addAttribute("id", "!");
		 	 	
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
	        
	        
	        
	        
	        // Add some attributes to root element.
	        //these should all be populated from known attributes... 2nd iteration
	        
//	        //id should increment with each new element
//	        entry.addAttribute("id", "1");
//	        // Add the element name in root element.
//	        Element type = entry.addElement("type");
//	        type.addText(claimType);
//	        Element user = entry.addElement("createUser");
//	        //populate with the user who created the move
//	        user.addText(username);
//	        Element turnID = entry.addElement("turnID");
//	        //mirror the turnid integer
//	        turnID.addText("3");
//	        Element linkedFiles = entry.addElement("linkedDCFileList");
//	        //not sure how to populate this badboy yet
//	        linkedFiles.addText("none");
//	        Element status = entry.addElement("status");
//	        //should actually be an enum with 3 statuses
//	        status.addText("true");
//	        Element textBody = entry.addElement("textBody");
//	        textBody.addText(claimDetails);
	       
	        // Create the compact format of xml document.
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        // Create the xml writer by passing outputstream and format
	        XMLWriter writer = new XMLWriter(System.out, format);
	        // Write to the xml document, flush and close
	        writer.write(document);
	        writer.flush();
	        writer.close();
	
	}
}
