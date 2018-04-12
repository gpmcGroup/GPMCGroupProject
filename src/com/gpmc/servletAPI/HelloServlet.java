package com.gpmc.servletAPI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().write("Hello, this is your first servlet!");
		
		String path = HelloServlet.class.getClassLoader().getResource("NewFile.xml").getPath();
		try {
			File file = new File(path);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
		
//			Document doc = new SAXReader.read(file);
			Element ele =(Element) doc.selectSingleNode("//Hello/tag");
//			ele.setText("7878");
//			FileWriter  out = new FileWriter(path);
//			doc.write(out);
//			out.close();
			
		
//			response.getWriter().write("taks complete!/n");
		
			response.getWriter().write(ele.asXML());
			response.getWriter().write(path);
			
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
