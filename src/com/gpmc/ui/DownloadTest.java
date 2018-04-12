package com.gpmc.ui;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class DownloadTest extends JFrame {
	private JLabel jLUploadFile;
	private JTextField JTextFileName;
	private JButton JBStoreFile;
	private JButton JBUploadFile;
	private JFileChooser jfc;
	private String downLoadPath;  //下载存储的路径
	private String downLoadFileName; //下载文件的名字
	public static void main(String args[]) {
		new DownloadTest().intialUI();
	}
	
	public void intialUI() {
		jLUploadFile = new JLabel("DownLoadFile");
		JTextFileName = new JTextField();
		JTextFileName.setSize(50, 20);
		JBUploadFile = new JButton("DownLoad");
		JBStoreFile = new JButton("...");
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		//add actionListener
		
		JBStoreFile.addActionListener(l->{
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int state = jfc.showOpenDialog(null);
			if(state == 1) {
					return ;
			}else {
				downLoadFileName = JTextFileName.getText();  //get downLoadFile name from text input
				downLoadPath = jfc.getSelectedFile().getAbsolutePath() + File.separator + downLoadFileName;  // get store directory
				System.out.println(downLoadFileName);
				
			}
		});
		//send request here
		JBUploadFile.addActionListener(l->{
			if(downLoadFileName.equals("") == false) {  //
				OkHttpClient client = new OkHttpClient();
				RequestBody requestBody = new FormBody.Builder().add("requestFileName",downLoadFileName).add("topicName", "Test").build();
				Request request = new Request.Builder().url("http://localhost:8080/GPMCGroupProject/fileDownload").post(requestBody).build();
				client.newCall(request).enqueue(new Callback() {
					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						InputStream input = null;
						byte[] buf = new byte[2048];
						int len = 0;
						FileOutputStream fileOutputStream = null;
						//downLoadPath is the path contains fileName
						input = response.body().byteStream();
						long totalByte = response.body().contentLength();
						File file = new File(downLoadPath);
						fileOutputStream = new FileOutputStream(file);
						while((len = input.read(buf))!=-1) {
							fileOutputStream.write(buf,0,len);
							//插入更新进度条
						}
						fileOutputStream.flush();
						input.close();
						fileOutputStream.close();
						JOptionPane.showMessageDialog(null, "Download Successful!");
					}
					@Override
					public void onFailure(Call arg0, IOException arg1) {
						JOptionPane.showMessageDialog(null,"Download failed" + arg1);
					}
				});
			}	
		});
		
		this.setLayout(new FlowLayout());
		this.add(jLUploadFile);
		this.add(JTextFileName);
		this.add(JBStoreFile);
		this.add(JBUploadFile);
		this.setSize(200,200);
		
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
        this.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));
		
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
	}
}
