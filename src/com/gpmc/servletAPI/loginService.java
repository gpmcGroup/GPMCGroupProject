package com.gpmc.servletAPI;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.dom4j.Element;

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
