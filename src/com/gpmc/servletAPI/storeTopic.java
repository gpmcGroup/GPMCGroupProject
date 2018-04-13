package com.gpmc.servletAPI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Servlet implementation class storeTopic
 */
@WebServlet("/storeTopic")
public class storeTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public storeTopic() {
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
		// RequestBody requestBodaySend = new FormBody.Builder().add("title",
		// title_return).add("content_teturn",
		// content_teturn).add("teamAName",nameA_return)
		// .add("teamBName",
		// nameB_return).add("teamAMember",teamAmem).add("teamBMember",teamBmem).add("maxTurn",
		// maxTurn_return)
		// .add("freHour",freHour_return ).add("freMin",
		// freMin_return).add("startTime",startTime_return).build();
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
		System.out.println(title);
		System.out.println(content);
		System.out.println(teamAName);
		System.out.println(teamBName);
		System.out.println(teamAMember);
		System.out.println(teamBMember);
		System.out.println(maxTurn);
		System.out.println(freHour);
		System.out.println(freMin);
		System.out.println(startTIme);
		System.out.println(teamALeader);
		System.out.println(teamBLeader);

		// System.out.println("shoudaola");
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