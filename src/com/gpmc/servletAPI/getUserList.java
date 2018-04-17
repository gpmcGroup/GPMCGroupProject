package com.gpmc.servletAPI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gpmc.modelClass.Team;

/**
 * Servlet implementation class getUserList
 * return User list to user client
 */
@WebServlet("/getUserList")
public class getUserList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getUserList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// System.out.println("\nhello");

		// response.getWriter().write("Hello");
		String topicName = (String) request.getParameter("topicName");
		String teamAName = (String) request.getParameter("teamAName");
		String teamBName = (String) request.getParameter("teamBName");
		Team team = new Team();
		team.setTeamName(teamAName);
		team.setTitleName(topicName);
		String s1 = team.getTeamMemberList();
		team.setTeamName(teamBName);
		String s2 = team.getTeamMemberList();
		String s = s1 + ";" + s2;
		response.getWriter().write(s);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}