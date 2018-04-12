package com.gpmc.ui;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUploadTest extends JFrame{
	
	
	private JLabel jLUploadFile;
	private JTextField JTextFileName;
	private JButton JBSelectFile;
	private JButton JBUploadFile;
	private JFileChooser jfc;
	private File file;
	
	public static void main(String args[]) {
		new FileUploadTest().intialUI();
	}
	
	public void intialUI() {
		jLUploadFile = new JLabel("Upload File");
		JTextFileName = new JTextField();
		JBUploadFile = new JButton("Upload File");
		JBSelectFile = new JButton("...");
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		//add actionListener
		
		JBSelectFile.addActionListener(l->{
			int state = jfc.showOpenDialog(null);
			if(state == 1) {
					return ;
			}else {
				file= jfc.getSelectedFile();
				JTextFileName.setText(file.getAbsolutePath());
			}
		});
		//send request here
		JBUploadFile.addActionListener(l->{
			if(file!=null) {  //
				OkHttpClient client = new OkHttpClient();
				RequestBody mediaBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
				
				FormBody formbody = new FormBody.Builder().add("topicName", "Test").build();
				String fileHeader = "form-data; name=\"file\"; filename=\"" + file.getName() + "\"";
				RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
						.addPart(Headers.of(
					            "Content-Disposition",
					            "form-data; name=\"name\"")
					                ,formbody)
						.addPart(Headers.of(
					            "Content-Disposition",
					            fileHeader)
					                , mediaBody).build();
				Request request = new Request.Builder().url("http://localhost:8080/GPMCGroupProject/fileUpload").post(requestBody).build();
			
				client.newCall(request).enqueue(new Callback() {
					@Override
					public void onResponse(Call arg0, Response arg1) throws IOException {
						JOptionPane.showMessageDialog(null, "Upload Successful! " + arg1.body().string());
					}
					@Override
					public void onFailure(Call arg0, IOException arg1) {
						JOptionPane.showMessageDialog(null,"Upload File failed: " + arg1);
					}
				});
			}else JOptionPane.showConfirmDialog(null, "Select a file first");
		});
		
		this.setLayout(new FlowLayout());
		this.add(jLUploadFile);
		this.add(JTextFileName);
		this.add(JBSelectFile);
		this.add(JBUploadFile);
		this.setSize(200,200);
		
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
        this.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));
		
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
	}
	
	
}
