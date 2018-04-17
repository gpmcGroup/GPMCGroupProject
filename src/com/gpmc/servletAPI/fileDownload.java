package com.gpmc.servletAPI;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.gpmc.report.StasticReport_Overall;
import com.gpmc.util.xmlUtil;

/**
 * Servlet implementation class fileDownload
 * solve topic material and statistic report download request
 */
@WebServlet("/fileDownload")
public class fileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestFileName = request.getParameter("requestFileName");
		String topicName = request.getParameter("topicName");
		String fileUrl = null;
		File file = null;
		
		try {
			boolean status = new StasticReport_Overall(topicName).generateReportData();
			if(status == true) {
				if(requestFileName.equals("report")) {
					fileUrl = xmlUtil.getTopicFilePath(topicName, "report.pdf");
					file = new File(fileUrl);
				}
				
				if(file.exists() == true) {
					response.setContentType("application/octet-stream");  
		            Long length=file.length();
		            response.setContentLength(length.intValue());
		            response.addHeader("Content-Disposition", "attachment; filename=" + requestFileName);
		            ServletOutputStream servletOutputStream=response.getOutputStream();
		            FileInputStream fileInputStream=new FileInputStream(file);
		            BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
		            int size=0;
		            byte[] b=new byte[2048];
		            while ((size=bufferedInputStream.read(b))!=-1) {
		                servletOutputStream.write(b, 0, size);
		            }
		            servletOutputStream.flush();
		            servletOutputStream.close();
		            bufferedInputStream.close();
				}else response.getWriter().write("false");   //file doesn't exists 
			}else response.getWriter().write("false");
		} catch (DocumentException e) {
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
