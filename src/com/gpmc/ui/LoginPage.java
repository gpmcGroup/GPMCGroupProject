package com.gpmc.ui;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * User login page
 *
 */
public class LoginPage extends JFrame{

	public static void main(String[] args) {
		LoginPage page = new LoginPage();
		
		
	}
	
	public LoginPage() {
		setName("Login");
		setSize(350, 177);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		this.add(panel);
		this.placeComponents(panel);

		this.setVisible(true);
		this.setPreferredSize(new java.awt.Dimension(350, 177));
	}
/**
 * place components in fixed location
 * @param panel
 */
	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("Email Address:");
		userLabel.setBounds(10, 10, 120, 25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(123, 10, 160, 25);
		panel.add(userText);
 
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(123, 40, 160, 25);
		panel.add(passwordText);

		JButton jBLogin = new JButton("Login");
		jBLogin.setBounds(200, 80, 80, 30);
		panel.add(jBLogin);
		
		jBLogin.addActionListener(l -> {
			
			String username = userText.getText();
			String password = new String(passwordText.getPassword());
			
			System.out.println(username + "  " + password);
			//use OkHttp send post http request , request body contains username & password
			OkHttpClient client = new OkHttpClient();
			RequestBody requestBoday = new FormBody.Builder().add("username",username).add("password", password).build();
			
			Request request = new Request.Builder().post(requestBoday).url("http://localhost:8080/GPMCGroupProject/loginService").build();
			try {
				Response response = client.newCall(request).execute();
				if(!response.isSuccessful()) {	
					JOptionPane.showMessageDialog(panel, "Can't request server, please check server status");
				}else {
					String txt = response.body().string();
					if(txt.equals("false")) {
						JOptionPane.showMessageDialog(panel, "username or password is wrong!");
					}else {
						this.dispose();
						SwingUtilities.invokeLater(new Runnable() {
				            public void run() {
				                HomePage inst = new HomePage(txt);
				                inst.setLayout(null);
				                inst.setLocationRelativeTo(null);
				                inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				                inst.setVisible(true);
				            }
				        });
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}