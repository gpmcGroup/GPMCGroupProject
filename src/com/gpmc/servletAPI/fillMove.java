package com.gpmc.servletAPI;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.gpmc.util.xmlUtil;

/**
 * Servlet implementation class fillMove
 * deal fill move request from user client
 */
@WebServlet("/fillMove")
public class fillMove extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fillMove() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String topicName = request.getParameter("topicName");
		String username = request.getParameter("username");
		
		String teamName = xmlUtil.findTeamName(topicName, username);
		File file = new File(xmlUtil.getTopicFilePath(topicName, "Move_" + teamName));
		SAXReader xmlReader = new SAXReader();
		Document doc;
		try {
			doc = xmlReader.read(file);
			String txt = doc.asXML();
			response.getWriter().write(txt);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
