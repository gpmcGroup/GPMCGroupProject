package com.gpmc.util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.gpmc.modelClass.Student;
import com.gpmc.modelClass.User;

public class loginTest {
	public static void main(String args[]) throws DocumentException, IOException {
		
		String path = xmlUtil.class.getClassLoader().getResource("User.xml").getPath();
		Document doc = new SAXReader().read(new File(path));
		String xpath = "//user[username='" + "frank" + "']/loginStatus";
		System.out.println(xpath);
		Element ele = (Element)doc.selectSingleNode("//user[username='" + "frank" + "']/loginStatus");
		System.out.println(ele.getStringValue());
//		ele.setText("false");
//		OutputFormat format = OutputFormat.createPrettyPrint();
//		try {
//			XMLWriter output = new XMLWriter(new FileWriter(loginTest.class.getClassLoader().getResource("User.xml").getPath()),format);
//			output.write(doc);
//			output.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		FileWriter out = new FileWriter(path);
////		doc.write(out);
////		out.close();
////		
//		Document docnew = new SAXReader().read(new File(xmlUtil.class.getClassLoader().getResource("User.xml").getPath()));
//		System.out.println(docnew.getStringValue());
//		
	
	}
}
