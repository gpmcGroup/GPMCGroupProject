package com.gpmc.ChatTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gpmc.util.xmlUtil;

public class ChatServerManage {
	
	
	private List portList = new ArrayList<Integer>();
	private List chatServerList = new ArrayList<ChatServer>();
	public ChatServerManage() {
//		try {
//			Document doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(topicName, fileName)));
//			Element ele = doc.getRootElement();
//			for(Iterator<Element> it = ele.elementIterator("team"); it.hasNext();) {
//				Element element = it.next();
				//
			for(int i = 9998 ; i < 10098 ; i++) {
				System.out.println("port :  "  + i);
				portList.add(i);
			}
				
//			}
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void openChatServer() {
		for(int i= 0 ; i < portList.size(); i++) {
			ChatServer server = new ChatServer((int) portList.get(i));
			server.initialServer();
			server.start();
			chatServerList.add(server);
		}
	}
	
	public static void main(String args[]) {
		
		ChatServerManage chatServerManage = new ChatServerManage();
		chatServerManage.openChatServer();
	}
}
