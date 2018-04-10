package com.gpmc.ui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.jdesktop.application.Application;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class AddMove extends JFrame {
	private JPanel jPanel1;
	private JTextArea moveDetails;
	private JScrollPane moveScroll;
	private JButton cancel;
	private JButton save;
	private JLabel moveDetailsLabel;
	private JComboBox moveType;
	private JLabel moveTypeLabel;

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	//textbodylabel, textbody, movetypelabel, movetypefield, save, cancel
	public AddMove() {
		super();
		initGUI();
		setupEventHandlers();
		this.setName("Sse");
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				{
					moveTypeLabel = new JLabel();
					moveTypeLabel.setText("Type");
				}
				{
					moveType = new JComboBox();
					ComboBoxModel options = new DefaultComboBoxModel(new String[] {"Claim", "Counterclaim" , "Evidence" , "Counterevidence"});
					moveType.setModel(options);
					
				}
				{
					moveDetailsLabel = new JLabel();
					moveDetailsLabel.setText("Text");
				}
				{
					moveDetails = new JTextArea();
					moveScroll = new JScrollPane(moveDetails);
				}
				{
					save = new JButton();
					save.setName("Save");
				}
				{
					cancel = new JButton();
					cancel.setName("Cancel");
				}
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(30, 30)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(moveTypeLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					    .addComponent(moveDetailsLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(moveScroll, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 8, Short.MAX_VALUE))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addGap(114)
					                .addComponent(save, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addComponent(moveType, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					                .addGap(98)))
					        .addGap(27)
					        .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 0, Short.MAX_VALUE)))
					.addContainerGap(29, 29));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(23, 23)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(moveType, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(moveTypeLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(48)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(moveDetailsLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					        .addGap(56))
					    .addComponent(moveScroll, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
					.addGap(0, 15, GroupLayout.PREFERRED_SIZE)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(save, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(cancel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap());
			}
			pack();
			this.setSize(537, 334);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void setupEventHandlers() {
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String claimType = moveDetails.getText();
				String claimDetails = moveDetails.getText();
				
				//create an okhttp request containing the field information
				//send request
				OkHttpClient client = new OkHttpClient();
				RequestBody body = new FormBody.Builder().add("type", claimType).add("textBody", claimDetails).build();
				Request request = new Request.Builder().post(body).url("http://localhost:8080/GPMCGroupProject/CreateMove").build();
				try {
					Response response = client.newCall(request).execute();
					if(!response.isSuccessful()) {
						JOptionPane.showMessageDialog(jPanel1, "No server access");
					}	else {
						String txt = response.body().string();
						JOptionPane.showMessageDialog(jPanel1, txt);
					}
					
				} catch (IOException i) {
					i.printStackTrace();
				}
				
				//2nd iteration - add the values of the userid and datetime, and turn and team etc
	
				
				//receive response and control with text statements
				
				
				
				
				//then close the window
				dispose();
			}
			
		});
		
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
	}

}