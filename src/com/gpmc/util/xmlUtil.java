package com.gpmc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
public class xmlUtil {
	
	public static String Userpath = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
	
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
}
