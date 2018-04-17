package com.gpmc.servletAPI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gpmc.modelClass.Team;

/**
 * Servlet implementation class queryTeamLeader
 */
@WebServlet("/queryTeamLeader")
public class queryTeamLeader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public queryTeamLeader() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String topicName= (String) request.getParameter("title");
		String teamBName = (String)request.getParameter("teamAName");
		String teamAName = (String)request.getParameter("teamBName");
		Team tm = new Team();
		tm.setTitleName(topicName);
		tm.setTeamName(teamAName);
		String LeaderA= tm.getTeamLeader();
		tm.setTeamName(teamBName);
		String LeaderB = tm.getTeamLeader();
		String content = LeaderA + ',' +LeaderB;
		response.getWriter().write(content);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
