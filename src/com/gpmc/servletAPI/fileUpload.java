package com.gpmc.servletAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.gpmc.util.xmlUtil;

/**
 * Servlet implementation class fileUpload
 * solve file upload request, such as topic related material upload
 */
@WebServlet("/fileUpload")
@MultipartConfig
public class fileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int MAX_UPLOAD_SIZE = 1024*1024*40; // 40 MB
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final String UPLOAD_DIRECTORY = "_Materil_upload";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)) {  //whether it is a file upload request
			response.getWriter().write("false");
			return ;
		}
		String topicName =  (inputStream2String(request.getPart("name").getInputStream())).split("=")[1]; //get TopicName
		topicName = topicName.replaceAll(" ", "_");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		
		
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		upload.setFileSizeMax(MAX_UPLOAD_SIZE);
		
		//setUp Upload file basic directory
		String uploadPath = xmlUtil.getTopicFilePath(topicName, "material");
		
		
		
		//filefolder
		System.out.println("Upload Path : " + uploadPath);
		//file
		Collection<Part> parts = request.getParts();
		System.out.println(parts.size());
		Part filePart = 	request.getPart("file");
		System.out.println("filePart" + filePart);
		String fileName = getFileName(filePart.getHeader("content-disposition"));
		System.out.println("file upload name: " + fileName);
		String filePath = uploadPath + File.separator + fileName;
		if(writeTo(fileName,filePart,filePath)) {
			response.getWriter().write("true");
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String getFileName(String header) {  
		  
        String[] tempArr1 = header.split(";");  
  
        String[] tempArr2 = tempArr1[2].split("=");  
  
        String fileName = tempArr2[1].substring(  
                tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");  
        return fileName;  
    }  
	
	private boolean writeTo(String fileName, Part part,String path){  
        InputStream in;
		try {
			in = part.getInputStream();
			OutputStream out = new FileOutputStream(path);  
	        byte[] buffer = new byte[1024];  
	        int length = -1;  
	        while ((length = in.read(buffer)) != -1) {  
	            out.write(buffer, 0, length);  
	        }  
	        in.close();  
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  
        return true;
    }  
	
	public String inputStream2String(InputStream in) throws IOException {  
        StringBuffer out = new StringBuffer();  
        byte[] b = new byte[1024];  
        for (int n; (n = in.read(b)) != -1;) { 
        		
            out.append(new String(b, 0, n));  
        }  
        return out.toString();  
    } 

}
