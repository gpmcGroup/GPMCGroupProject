package com.gpmc.servletAPI;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;

import org.dom4j.DocumentException;

import com.gpmc.util.xmlUtil;

@WebServlet("/MoveData")
public class MoveData extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MoveData() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {		
		
		//the request can be ignored, because no params are incoming
		//2nd iteration - handle the topic and the user to filter results
		
		//2nd iteration - take in info (userid, teamid, turnid) and customise move 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
