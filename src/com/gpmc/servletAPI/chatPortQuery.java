package com.gpmc.servletAPI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.gpmc.util.xmlUtil;

/**
 * Servlet implementation class chatPortQuery
 * receive team chat port query and return port data
 */
@WebServlet("/chatPortQuery")
public class chatPortQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public chatPortQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String teamName = request.getParameter("teamName");
		String topicName = request.getParameter("topicName");
		
		try {
			String port = xmlUtil.queryTeamChatPort(topicName,teamName);
			System.out.println("teamport :" + port);
			response.getWriter().write(xmlUtil.queryTeamChatPort(topicName,teamName));
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
