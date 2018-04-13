package com.gpmc.servletAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gpmc.modelClass.Student;
import com.gpmc.modelClass.User;
import com.gpmc.util.xmlUtil;

/**
 * Servlet implementation class loginService
 */

@WebServlet("/loginService")
public class loginService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//			response.getWriter().append("Served at: ").append(request.getContextPath());
			
		
		// use getAttribute method to get request parameter 
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		
		//test
		Document doc;
		try {
			doc = new SAXReader().read(new File(xmlUtil.getTopicFilePath("Is Abortion is wrong?", "Team")));
			System.out.println(doc.asXML());
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//test
		

		
		User user = new Student();
		try {
			if(user.login(username, password)) {
				String reponseText = xmlUtil.getUserDetail(username).asXML();
				response.getWriter().write(reponseText);  //send user detail to front-end 
			}else {
				response.getWriter().write("false");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
