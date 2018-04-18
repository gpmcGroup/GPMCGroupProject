package com.gpmc.servletAPI;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gpmc.util.xmlUtil;

/**
 * Servlet implementation class queryTopicMaterial
 */
@WebServlet("/queryTopicMaterial")
public class queryTopicMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public queryTopicMaterial() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String topicName = request.getParameter("topicName");
		String requestFileName = request.getParameter("requestFileName");
		
		String fileFolderUrl = xmlUtil.getTopicFilePath(topicName, requestFileName);
		File file = new File(fileFolderUrl);
		if(file.exists() == false) {
			file.mkdirs();
		}else {
			String fileList[] = file.list();
			if(fileList.length == 0 || fileList == null) {
				response.getWriter().write("false");
			}else {
				String responseTxt ="";
				for(int i = 0 ; i < fileList.length-1;i++) {
					responseTxt += fileList[i] +";";
				}
				responseTxt += fileList[fileList.length-1];
				response.getWriter().write(responseTxt);
			}
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
