package com.gpmc.servletAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gpmc.modelClass.Team;
import com.gpmc.modelClass.Topic;

/**
 * Servlet implementation class changeDetail
 */
@WebServlet("/changeDetail")
public class changeDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public changeDetail() {
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
		String title = (String) request.getParameter("title");
		String content = (String) request.getParameter("content_teturn");
		String teamAName = (String) request.getParameter("teamAName");
		String teamBName = (String) request.getParameter("teamBName");
		String teamAMember = (String) request.getParameter("teamAMember");
		String teamBMember = (String) request.getParameter("teamBMember");
		String maxTurn = (String) request.getParameter("maxTurn");
		String freHour = (String) request.getParameter("freHour");
		String freMin = (String) request.getParameter("freMin");
		String startTIme = (String) request.getParameter("startTime");
		String teamALeader = (String) request.getParameter("teamALeader");
		String teamBLeader = (String) request.getParameter("teamBLeader");

		Topic np = new Topic();

		int fre = Integer.parseInt(freHour) * 3600000 + Integer.parseInt(freMin) * 60000;

		np.setTitle(title);
		np.setChangeTurnTimeLeft(String.valueOf(fre));
		np.setContent(content);
		np.setMaxTurn(maxTurn);
		np.setPresentTurnOwner(teamAName);
		np.setStartTime(startTIme);
		np.setStatus("pending");
		np.setTeamA(teamAName);
		np.setTeamB(teamBName);
		np.setTurnCycleFrequency(String.valueOf(fre));
		np.setWinner("null");

		np.changeTopic();

		Team tm = new Team();

		tm.setTitleName(title);
		tm.setTeamName(teamAName);
		tm.setTeamLeader(teamALeader);
		System.out.println(teamAMember + teamBMember);
		String[] tempMemA = teamAMember.split("_");
		List<String> lsmemA = new ArrayList<String>();
		for (int i = 0; i < tempMemA.length; i++) {
			lsmemA.add(tempMemA[i]);
		}

		String[] tempMemB = teamBMember.split("_");
		List<String> lsmemB = new ArrayList<String>();
		for (int i = 0; i < tempMemB.length; i++) {
			lsmemB.add(tempMemB[i]);
		}
		tm.setTeamMemberList(lsmemA);
		tm.setAnotherTeam(lsmemB, teamBLeader, teamBName);
		tm.changeNewTeamXml();

		System.out.println("shoudaola");
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
