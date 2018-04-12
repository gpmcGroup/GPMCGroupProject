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

/**
 * Servlet implementation class fileDownload
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
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String requestFileName = request.getParameter("requestFileName");
		String topicName = request.getParameter("topicName");
		String fileUrl = request.getServletContext().getRealPath("./")+File.separator + topicName + "_Materil_upload" + File.separator + requestFileName;
//		System.out.println(fileUrl);
		
		File file = new File(fileUrl);
		if(file.exists() == true) {
			response.setContentType("application/octet-stream");  //
            Long length=file.length();
            response.setContentLength(length.intValue());
//            fileName = URLEncoder.encode(downloadFile.getName(), enc);
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
