package com.gpmc.ui;

//import com.cloudgarden.layout.AnchorConstraint;
//import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.text.*;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gpmc.modelClass.User;
import com.gpmc.ui.HomePage.newTopic;
import com.gpmc.util.xmlUtil;
import com.sun.xml.internal.ws.api.Component;
import com.sun.xml.internal.ws.server.sei.InvokerTube;
import com.gpmc.ChatServer.ChatClient;
import com.gpmc.modelClass.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.swing.SwingUtilities;

public class HomePage extends javax.swing.JFrame {

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String name;
	String selectName;
	private String initialData;
	int selectNumber;
	private String content_content;
	private String content_time;
	private String content_maxTurn;
	private String content_turnFre;
	private String teamAName;
	private String teamBName;
	private String[] teamMem;

	private JTextArea test;
	private JLabel jLaWelcome;
	private JPanel jPBasic;
	private JPanel jPShow;

	private JButton jBTurn;
	private JButton jBMove;
	private JButton jBIntroduction;
	private JButton jBStatisc;
	private JButton jBChat;
	private JButton jBLogout;
	private JButton jBPlus;
	private JButton jBReudce;

	private JList jLTopic;

	JScrollPane jScrollPane = new JScrollPane();

	private User loginUser;

	private Document doc;

	private Vector topicList;

	public HomePage(String initialData) {

		super();
		this.initialData = initialData;
		topicList = new Vector<String>();
		initGUI();
	}

	private void initGUI() {

		try {
			doc = DocumentHelper.parseText(initialData);

			name = doc.valueOf("//user/username");
			// topic list data
			Element eleTopic = (Element) doc.selectSingleNode("//user/topicList");
			if (eleTopic != null) {
				for (Iterator<Element> it = eleTopic.elementIterator("topic"); it.hasNext();) {
					Element ele = it.next();
					topicList.add(ele.elementText("topicName"));
				}
			}

		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			{
				this.setTitle("system");
				this.setSize(688, 456);
				this.setResizable(false);
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				getContentPane().setLayout(null);
			}

			jPBasic = new JPanel();
			jPBasic.setLayout(null);
			jPBasic.setBounds(299, 122, 797, 473);

			jPShow = new JPanel();
			jPShow.setBounds(320, 120, 700, 500);
			getContentPane().add(jPShow);
			getContentPane().add(jPBasic);

			// jPBasic.add(new introductionPanel());

			{

				jLaWelcome = new JLabel();
				getContentPane().add(jLaWelcome);
				jLaWelcome.setText("welcome, " + name);
				jLaWelcome.setBounds(38, 12, 136, 46);
			}
			{
				jBLogout = new JButton();
				getContentPane().add(jBLogout);
				jBLogout.setText("Logout");
				jBLogout.setBounds(985, 11, 87, 35);
				jBLogout.addActionListener(l -> {// logout button action event
					String msg = "Do you want log out?";
					int l1 = JOptionPane.showConfirmDialog(null, "Do you want to logout?", "confirm",
							JOptionPane.YES_NO_OPTION);
					if (l1 == 0) {
						// return login
					}
				});
			}

			{
				jBChat = new JButton();
				getContentPane().add(jBChat);
				jBChat.setText("Chat");
				jBChat.setBounds(927, 76, 108, 35);
				jBChat.addActionListener(l -> {
					// Handler information
					Document doc;
					try {
						doc = DocumentHelper.parseText(initialData);
						String teamName = doc.valueOf("//user/teamList/team/teamName");

						if (!teamName.equals("")) {
							// request chat port from server
							OkHttpClient client = new OkHttpClient();
							RequestBody requestBoday = new FormBody.Builder().add("teamName", teamName)
									.add("topicName", selectName).build();
							Request request = new Request.Builder().post(requestBoday)
									.url("http://localhost:8080/GPMCGroupProject/chatPortQuery").build();

							Response response = client.newCall(request).execute();
							if (!response.isSuccessful()) {
								JOptionPane.showMessageDialog(null, "Can't request server, please check server status");
							} else {
								String txt = response.body().string();
								if (txt.equals("null")) {
									JOptionPane.showMessageDialog(null, "team Not port");
								} else {
									ChatClient inst = new ChatClient(name, "127.0.0.1", txt);
									inst.setLocationRelativeTo(null);
									inst.setVisible(true);
									inst.setResizable(false);
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "You should have a team first");
						}

					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				});
			}
			{
				jBStatisc = new JButton();
				getContentPane().add(jBStatisc);
				jBStatisc.setText("Statisc");
				jBStatisc.setBounds(788, 76, 97, 35);
				 jBStatisc.addActionListener(l->{
					 System.out.println("select name in Statisc : " + selectName);
					 new downLoadReportPanel(this,selectName);
				 });
			}
			{
				jBMove = new JButton();
				getContentPane().add(jBMove);
				jBMove.setText("Move");
				jBMove.setBounds(644, 76, 102, 35);
				jBMove.addActionListener(l -> {
					movePanel mp = new movePanel();
					jPShow.removeAll();
					jPShow.add(mp);
					jPShow.updateUI();
				});
			}
			{
				this.jBTurn = new JButton();
				getContentPane().add(jBTurn);
				this.jBTurn.setText("Turns");
				this.jBTurn.setBounds(496, 76, 101, 35);
				jBTurn.addActionListener(l -> {
					turnPanel tp = new turnPanel();
					jPShow.removeAll();
					jPShow.add(tp);
					jPShow.updateUI();
				});
				;
			}

			// list add/remove
			{
				jBPlus = new JButton();
				jBPlus.setText("+");
				getContentPane().add(jBPlus);
				jBPlus.setBounds(64, 580, 79, 39);
				jBPlus.addActionListener(l -> {
					newTopic ntPage = new newTopic(this, jBPlus.getText());
					ntPage.setVer();
				});
			}
			{
				jBReudce = new JButton();
				jBReudce.setText("-");
				getContentPane().add(jBReudce);
				jBReudce.setBounds(165, 580, 79, 39);
				jBReudce.addActionListener(l -> {
					if (selectName != null) {
						String msg = new String("Are you sure to delete topic :" + selectName);
						int n = JOptionPane.showConfirmDialog(null, msg, "confirm", JOptionPane.YES_NO_OPTION);
						if (n == 0) {
							topicList.remove(selectNumber);
							jLTopic.setListData(topicList);
						}
					}
				});
			}
			{
				jLTopic = new JList(topicList);
				// jLTopic.setListData(topicList);
				jLTopic.setBounds(30, 76, 266, 490);

				getContentPane().add(jLTopic);
				jScrollPane.setViewportView(jLTopic);
				jLTopic.addListSelectionListener(listener -> {
					getSelectedTopicData();
				});
			}

			{
				jBIntroduction = new JButton();
				getContentPane().add(jBIntroduction);
				jBIntroduction.setText("Introduction");
				jBIntroduction.setBounds(310, 76, 102, 35);
				jBIntroduction.addActionListener(l -> {

					getSelectedTopicData();

				});
			}
			this.add(jLTopic);
			pack();
			this.setSize(1142, 655);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	public void getSelectedTopicData() {
//		if(jLTopic.getSelectedValue()==null) {
//			return;
//		}
		selectNumber = jLTopic.getSelectedIndex();
		selectName = (String) topicList.get(selectNumber);
		System.out.printf("LeadSelectionIndex is %s%n", selectName);

		OkHttpClient client1 = new OkHttpClient();
		RequestBody requestBoday1 = new FormBody.Builder().add("selectName", selectName).build();

		Request request1 = new Request.Builder().post(requestBoday1)
				.url("http://localhost:8080/GPMCGroupProject/queryTopicDetail").build();

		try {
			Response response = client1.newCall(request1).execute();
			if (!response.isSuccessful()) {
				JOptionPane.showMessageDialog(null, "Can't request server, please check server status1");
			} else {
				String txt = response.body().string();
				// System.out.println("fanhuide :" + txt);
				Document doc = DocumentHelper.parseText(txt);
				Element ele = (Element) doc.selectSingleNode("//topic[title='" + selectName + "']/content");
				content_content = ele.getStringValue();
				// System.out.print("content:"+content_content+"\n");

				ele = (Element) doc.selectSingleNode("//topic[title='" + selectName + "']/startTime");
				content_time = ele.getStringValue();
				// System.out.print("startTime:"+content_time+"\n");

				ele = (Element) doc.selectSingleNode("//topic[title='" + selectName + "']/maxTurn");
				content_maxTurn = ele.getStringValue();
				// System.out.println("content_maxTurn:"+ele.getStringValue());

				ele = (Element) doc.selectSingleNode("//topic[title='" + selectName + "']/turnCycleFrequency");
				content_turnFre = ele.getStringValue();
				// System.out.print("content_turnFre:"+ele.getStringValue()+"\n");

				ele = (Element) doc.selectSingleNode("//topic[title='" + selectName + "']/teamA");
				teamAName = ele.getStringValue();
				// System.out.print("teamA:"+ele.getStringValue()+"\n");

				ele = (Element) doc.selectSingleNode("//topic[title='" + selectName + "']/teamB");
				teamBName = ele.getStringValue();
				// System.out.print("teamB:"+ele.getStringValue()+"\n");
				response.close();
			}
			OkHttpClient client2 = new OkHttpClient();
			RequestBody requestBoday2 = new FormBody.Builder().add("topicName", selectName).add("teamAName", teamAName)
					.add("teamBName", teamBName).build();
			Request request2 = new Request.Builder().post(requestBoday2)
					.url("http://localhost:8080/GPMCGroupProject/getUserList").build();
			Response response1 = client2.newCall(request2).execute();
			if (!response.isSuccessful()) {
				JOptionPane.showMessageDialog(null, "Can't request server again, please check server status2");
			} else {
				String txt1 = response1.body().string();
				teamMem = txt1.split(";");
				// for(int i =0;i<2;i++) {
				// System.out.println(teamMem[i]);
				// }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jPShow.removeAll();
		GroupLayout groupLayout1 = new GroupLayout(jPShow);
		jPShow.setLayout(groupLayout1);

		JTextField jt_title = new JTextField(selectName);
		jt_title.setEditable(false);

		JTextArea jt_content = new JTextArea(content_content);
		jt_content.setBackground(null);
		jt_content.setEditable(false);

		JList jls_teamA = new JList();
		JList jls_teamB = new JList();

		String[] aa = teamMem[0].split(" ");
		String[] bb = teamMem[1].split(" ");
		Vector teamAVc = new Vector<String>();

		for (int j = 0; j < aa.length; j++) {
			teamAVc.add(aa[j]);
		}
		Vector teamBVc = new Vector<String>();
		for (int j = 0; j < bb.length; j++) {
			teamBVc.add(bb[j]);
		}

		JScrollPane jsp1 = new JScrollPane();
		JScrollPane jsp2 = new JScrollPane();
		JScrollPane jsp3 = new JScrollPane();

		jsp1.setViewportView(jt_content);

		jls_teamA.setListData(teamAVc);
		jls_teamA.setBackground(null);
		jls_teamA.setForeground(Color.BLACK);
		jls_teamB.setListData(teamBVc);
		jls_teamB.setBackground(null);
		jls_teamB.setForeground(Color.BLACK);

		jls_teamA.setEnabled(false);
		jls_teamB.setEnabled(false);
		jsp2.setViewportView(jls_teamA);
		jsp3.setViewportView(jls_teamB);

		JLabel jl_startTiem = new JLabel(content_time);
		JLabel jl_maxTurn = new JLabel(content_maxTurn);
		JLabel jl_turnFre = new JLabel(content_turnFre);

		// JLabel jlt_title = new JLabel("title:");
		// JLabel jlt_content = new JLabel("content:" );
		JLabel jlt_teamA = new JLabel("TeamA:" + teamAName);
		JLabel jlt_teamB = new JLabel("TeamB:" + teamBName);
		JLabel jlt_startTime = new JLabel("StartTime:");
		JLabel jlt_maxTurn = new JLabel("MaxTurn:");
		JLabel jlt_TurnFre = new JLabel("TurnCycleFrequency:");

		GroupLayout.SequentialGroup vg1 = groupLayout1.createSequentialGroup();
		// vg1.addComponent(jlt_title);
		vg1.addGap(5);
		vg1.addComponent(jt_title);
		vg1.addGap(5);

		GroupLayout.SequentialGroup vg2 = groupLayout1.createSequentialGroup();
		// vg2.addComponent(jt_content);
		vg2.addGap(5);
		vg2.addComponent(jsp1);
		vg2.addGap(5);

		GroupLayout.SequentialGroup vg3 = groupLayout1.createSequentialGroup();
		vg3.addComponent(jlt_teamA);
		vg3.addGap(5);
		vg3.addComponent(jsp2);
		vg3.addGap(10);
		vg3.addComponent(jlt_teamB);
		vg3.addGap(5);
		vg3.addComponent(jsp3);

		GroupLayout.SequentialGroup vg4 = groupLayout1.createSequentialGroup();
		vg4.addComponent(jlt_maxTurn);
		vg4.addGap(5);
		vg4.addComponent(jl_maxTurn);
		vg4.addGap(50);
		vg4.addComponent(jlt_TurnFre);
		vg4.addGap(5);
		vg4.addComponent(jl_turnFre);

		GroupLayout.SequentialGroup vg5 = groupLayout1.createSequentialGroup();
		vg5.addComponent(jlt_startTime);
		vg5.addGap(20);
		vg5.addComponent(jl_startTiem);

		JButton jBEdit = new JButton("Edit");
		jBEdit.addActionListener(l -> {

			newTopic np1 = new newTopic(this, jBEdit.getText());
			// ele = (Element) doc.selectSingleNode("//topic[title='" + selectName +
			// "']/startTime");
			// content_time = ele.getStringValue();
			// // System.out.print("startTime:"+content_time+"\n");

			OkHttpClient clientTeam = new OkHttpClient();
			RequestBody requestBodayTeam = new FormBody.Builder().add("title", selectName).add("teamAName", teamAName)
					.add("teamBName", teamBName).build();
			Request requestTeam = new Request.Builder().post(requestBodayTeam)
					.url("http://localhost:8080/GPMCGroupProject/queryTeamLeader").build();
			String bothLeader = "";
			try {
				Response responseTeam = clientTeam.newCall(requestTeam).execute();
				if (!responseTeam.isSuccessful()) {
					JOptionPane.showMessageDialog(null, "Can't request server again, please check server statusTeam");
				} else {
					bothLeader = responseTeam.body().string();
					// System.out.println(bothLeader);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			np1.setData(selectName, content_content, teamAName, teamBName, bothLeader, content_maxTurn, content_time,
					content_turnFre, teamAVc, teamBVc);
			np1.setVisible(true);
		});
		GroupLayout.SequentialGroup vg6 = groupLayout1.createSequentialGroup();
		vg6.addGap(350);
		vg6.addComponent(jBEdit);

		groupLayout1.setHorizontalGroup(groupLayout1.createParallelGroup().addGap(20).addGroup(vg1).addGroup(vg2)
				.addGroup(vg3).addGroup(vg4).addGroup(vg5).addGroup(vg6));

		groupLayout1.setVerticalGroup(groupLayout1.createSequentialGroup().addGap(15).addComponent(jt_title, 30, 30, 30)
				.addGap(10).addGroup(groupLayout1.createParallelGroup().addComponent(jsp1, 200, 200, 200)).addGap(10)
				.addGroup(groupLayout1.createParallelGroup().addComponent(jlt_teamA).addComponent(jsp2, 100, 100, 100)
						.addComponent(jlt_teamB).addComponent(jsp3, 100, 100, 100))
				.addGap(10)
				.addGroup(groupLayout1.createParallelGroup().addComponent(jlt_maxTurn).addComponent(jl_maxTurn)
						.addComponent(jlt_TurnFre).addComponent(jl_turnFre))
				.addGap(10)
				.addGroup(groupLayout1.createParallelGroup().addComponent(jlt_startTime).addComponent(jl_startTiem))
				.addGap(10)
				.addGroup(groupLayout1.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jBEdit)));

		jPShow.setVisible(true);

		jPShow.updateUI();

	}

	class newTopic extends JDialog {
		private JScrollPane sp = new JScrollPane();
		private JScrollPane sp1 = new JScrollPane();
		private JScrollPane sp2 = new JScrollPane();

		private JPanel dialogPane;

		private JLabel textLabel_title;
		private JLabel textLabel_content;
		private JLabel textlabel_allocate;
		private JLabel textLabel_allocate1;
		private JLabel textLabel_allocate2;
		private JLabel textLabel_StartTime;
		// private JLabel textLabel_startTimeS;
		private JLabel textLabel_MaxTurn;
		private JLabel textLabel_TurnTime;
		private JLabel textLabel_hour1;
		private JLabel textLabel_hour2;
		private JLabel textLabel_min;
		private JLabel textLabel_year;
		private JLabel textLabel_month;
		private JLabel textLabel_day;
		private JLabel textLabel_TeamName;
		private JLabel textLabel_TeamA;
		private JLabel textLabel_TeamB;
		private JLabel textLabel_Leader;
		private JLabel textLabel_teamALd;
		private JLabel textLabel_teamBLd;

		private JTextField textField_Year;
		private JTextField textField_Mon;
		private JTextField textField_Day;
		private JTextField textField_Hour1;
		private JTextField textField_Hour2;
		private JTextField textField_Min;
		private JTextField textField_MaxTurn;
		private JTextField textField_title;
		private JTextArea textField_content;
		private JTextField textFieLd_teamANa;
		private JTextField textField_teamBNa;
		private JTextField textField_LeaderA = new JTextField();
		private JTextField textField_LeaderB = new JTextField();

		private JButton buttonSave;
		private JButton buttonCancel;

		private JList studentListA;
		private JList studentListB;

		private String maxTurn_return;
		private String nameB_return;
		private String nameA_return;
		private String title_return;
		private String content_teturn;
		private String freHour_return;
		private String freMin_return;
		private String starYear_return;
		private String starMon_return;
		private String starDay_return;
		private String starHour_return;
		private String startTime_return;
		private String teamALeader;
		private String teamBLeader;
		private JComboBox comboBoxTeamA;
		private JComboBox comboBoxTeamB;
		private DefaultComboBoxModel modelA;
		private DefaultComboBoxModel modelB;

		private String[] studentString = new String[] { "frank", "Tom", "Andy", "Ryan", "Test1", "Test2", "Test3",
				"Test4" };

		Vector studentList = new Vector<String>();
		Vector teamAV = new Vector<String>();
		Vector teamBV = new Vector<String>();
		List<String> teamAList = new ArrayList<String>();
		List<String> teamBList = new ArrayList<String>();

		private String sourceButton;

		//
		// private JComboBox comboBoxTurn;
		// private JComboBox comboBoxFre;
		//
		public newTopic(JFrame f, String sourceButton) {
			super(f, "create a new topic", true);
			this.sourceButton = sourceButton;
			Container container = getContentPane();
			setLocation(450, 225);
			setSize(1000, 600);
			doDialog();
		}

		public void doDialog() {
			for (int i = 0; i < studentString.length; i++) {
				studentList.addElement(studentString[i]);
			}

			studentListA = new JList(studentList);
			studentListA.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			sp.setViewportView(studentListA);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			studentListA.addListSelectionListener(SharedListSelectionHandler -> {
				teamAList = studentListA.getSelectedValuesList();

			});

			studentListB = new JList(studentList);
			studentListB.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			sp2.setViewportView(studentListB);
			sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			studentListB.addListSelectionListener(SharedListSelectionHandler -> {
				teamBList = studentListB.getSelectedValuesList();
				teamBV = new Vector<String>(teamBList);

				comboBoxTeamB = new JComboBox(modelB);
				dialogPane.updateUI();
			});

			dialogPane = new JPanel();

			textLabel_title = new JLabel("Title:");
			textLabel_content = new JLabel("Content:");
			textLabel_allocate1 = new JLabel("TeamA:");
			textLabel_allocate2 = new JLabel("TeamB:");
			textLabel_StartTime = new JLabel("StartTime:");
			textLabel_MaxTurn = new JLabel("MaxTurn:");
			textLabel_TurnTime = new JLabel("TurnFrequency:");
			textlabel_allocate = new JLabel("Allocate:");
			textLabel_StartTime = new JLabel("Start at:");
			// textLabel_startTimeS = new JLabel("eg: xxxx/xx/xx xx:xx ");

			textFieLd_teamANa = new JTextField();
			textField_teamBNa = new JTextField();
			textField_title = new JTextField();
			textField_Year = new JTextField();
			textField_Mon = new JTextField();
			textField_Day = new JTextField();
			textField_Hour1 = new JTextField();
			textField_Hour2 = new JTextField();
			textField_Min = new JTextField();
			textField_MaxTurn = new JTextField();
			textField_content = new JTextArea(10, 70);
			textField_content.setLineWrap(true);

			sp1.setViewportView(textField_content);
			sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			{

				comboBoxTeamA = new JComboBox();
				comboBoxTeamA.addItemListener(aListener -> {
					teamALeader = (String) comboBoxTeamA.getSelectedItem();
				});
			}

			{
				modelB = new DefaultComboBoxModel(teamBV);
				comboBoxTeamB = new JComboBox(modelB);

				comboBoxTeamB.setEditable(true);
				comboBoxTeamB.addItemListener(aListener -> {
					dialogPane.updateUI();
					teamBLeader = (String) comboBoxTeamB.getSelectedItem();
				});
			}

			textLabel_year = new JLabel("year");
			textLabel_month = new JLabel("month");
			textLabel_day = new JLabel("day");
			textLabel_hour1 = new JLabel("hour");
			textLabel_min = new JLabel("mins");
			textLabel_hour2 = new JLabel("hours");
			textLabel_TeamName = new JLabel("TeamName:");
			textLabel_TeamA = new JLabel("TeamA:");
			textLabel_TeamB = new JLabel("TeamB:");
			textLabel_Leader = new JLabel("Leader:");
			textLabel_teamALd = new JLabel("TeamA:");
			textLabel_teamBLd = new JLabel("TeamB:");

			{
				buttonSave = new JButton("Save");
				buttonSave.addActionListener(listener -> {
					int n = JOptionPane.showConfirmDialog(null, "sure to save", "confirm", JOptionPane.YES_NO_OPTION);
					if (n == 0) {
						title_return = textField_title.getText();
						content_teturn = textField_content.getText();
						maxTurn_return = textField_MaxTurn.getText();
						nameB_return = textField_teamBNa.getText();
						nameA_return = textFieLd_teamANa.getText();
						freHour_return = textField_Hour2.getText();
						freMin_return = textField_Min.getText();
						starYear_return = textField_Year.getText();
						starMon_return = textField_Mon.getText();
						starDay_return = textField_Day.getText();
						starHour_return = textField_Hour1.getText();
						teamALeader = textField_LeaderA.getText();
						teamBLeader = textField_LeaderB.getText();

						if (sourceButton.equals("Edit")) {
							if (judge()) {
								System.out.println("topic completed");
								// topicList.addElement(title_return);
								// jLTopic.setListData(topicList);
								changeData();
								this.dispose();
							}
						} else {
							if (judgeSame() && judge()) {
								System.out.println("topic completed");
								// topicList.addElement(title_return);
								// jLTopic.setListData(topicList);
								sendData();
								this.dispose();
							}
						}

					}
				});
			}

			GroupLayout groupLayout = new GroupLayout(dialogPane);
			dialogPane.setLayout(groupLayout);

			GroupLayout.SequentialGroup vpg1 = groupLayout.createSequentialGroup();
			vpg1.addComponent(textLabel_allocate1);
			vpg1.addGap(10);
			vpg1.addComponent(sp, 200, 200, 200);
			vpg1.addGap(50);
			vpg1.addComponent(textLabel_allocate2);
			vpg1.addGap(10);
			vpg1.addComponent(sp2, 200, 200, 200);

			GroupLayout.SequentialGroup vpg2 = groupLayout.createSequentialGroup();
			vpg2.addGap(5);
			vpg2.addComponent(textField_MaxTurn, 50, 50, 50);
			vpg2.addGap(50);
			vpg2.addComponent(textLabel_TurnTime);
			vpg2.addGap(20);
			vpg2.addComponent(textField_Hour2, 50, 50, 50);
			vpg2.addGap(10);
			vpg2.addComponent(textLabel_hour2);
			vpg2.addGap(10);
			vpg2.addComponent(textField_Min, 50, 50, 50);
			vpg2.addGap(10);
			vpg2.addComponent(textLabel_min);

			GroupLayout.SequentialGroup vpg3 = groupLayout.createSequentialGroup();
			vpg3.addGap(400);
			vpg3.addComponent(buttonSave);

			GroupLayout.SequentialGroup vpg4 = groupLayout.createSequentialGroup();
			vpg4.addComponent(textField_Year, 70, 70, 70);
			vpg4.addGap(10);
			vpg4.addComponent(textLabel_year);
			vpg4.addGap(10);
			vpg4.addComponent(textField_Mon, 50, 50, 50);
			vpg4.addGap(10);
			vpg4.addComponent(textLabel_month);
			vpg4.addGap(10);
			vpg4.addComponent(textField_Day, 50, 50, 50);
			vpg4.addGap(10);
			vpg4.addComponent(textLabel_day);
			vpg4.addGap(10);
			vpg4.addComponent(textField_Hour1, 50, 50, 50);
			vpg4.addGap(10);
			vpg4.addComponent(textLabel_hour1);

			GroupLayout.SequentialGroup vpg5 = groupLayout.createSequentialGroup();
			vpg5.addComponent(textLabel_TeamA);
			vpg5.addGap(10);
			vpg5.addComponent(textFieLd_teamANa, 150, 150, 150);
			vpg5.addGap(50);
			vpg5.addComponent(textLabel_TeamB);
			vpg5.addGap(10);
			vpg5.addComponent(textField_teamBNa, 150, 150, 150);

			GroupLayout.SequentialGroup vpg6 = groupLayout.createSequentialGroup();
			vpg6.addGap(10);
			vpg6.addComponent(textLabel_teamALd);
			vpg6.addGap(10);
			vpg6.addComponent(textField_LeaderA, 90, 90, 90);
			vpg6.addGap(50);
			vpg6.addComponent(textLabel_teamBLd);
			vpg6.addGap(10);
			vpg6.addComponent(textField_LeaderB, 90, 90, 90);

			groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup().addGap(20)
					.addGroup(groupLayout.createParallelGroup().addComponent(textlabel_allocate)
							.addComponent(textLabel_title).addComponent(textLabel_content)
							.addComponent(textLabel_MaxTurn).addComponent(textLabel_StartTime)
							.addComponent(textLabel_TeamName).addComponent(textLabel_Leader))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(textField_title).addComponent(sp1).addGroup(vpg1).addGroup(vpg2)
							.addGroup(vpg3).addGroup(vpg4).addGroup(vpg5).addGroup(vpg6))
					.addGap(20));

			groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGap(15)
					.addGroup(groupLayout
							.createParallelGroup().addComponent(textLabel_title).addComponent(textField_title))
					.addGap(15)
					.addGroup(groupLayout
							.createParallelGroup().addComponent(textLabel_content, 100, 100, 100).addComponent(sp1))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup().addComponent(textLabel_TeamName)
							.addComponent(textLabel_TeamA).addComponent(textFieLd_teamANa).addComponent(textLabel_TeamB)
							.addComponent(textField_teamBNa))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup().addComponent(textlabel_allocate)
							.addComponent(textLabel_allocate1).addComponent(sp).addComponent(textLabel_allocate2)
							.addComponent(sp2))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup().addComponent(textLabel_Leader)
							.addComponent(textLabel_teamALd).addComponent(textField_LeaderA, 20, 20, 20)
							.addComponent(textLabel_teamBLd).addComponent(textField_LeaderB, 20, 20, 20))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(textLabel_MaxTurn).addComponent(textField_MaxTurn)
							.addComponent(textLabel_TurnTime).addComponent(textField_Hour2)
							.addComponent(textLabel_hour2).addComponent(textField_Min).addComponent(textLabel_min))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup().addComponent(textLabel_StartTime)
							.addComponent(textField_Year).addComponent(textLabel_year).addComponent(textField_Mon)
							.addComponent(textLabel_month).addComponent(textField_Day).addComponent(textLabel_day)
							.addComponent(textField_Hour1).addComponent(textLabel_hour1))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(buttonSave))
					.addGap(20));

			groupLayout.linkSize(SwingConstants.VERTICAL, textLabel_title, textField_title);
			groupLayout.linkSize(SwingConstants.VERTICAL, textLabel_MaxTurn, textField_MaxTurn, textLabel_TurnTime,
					textField_Hour2, textLabel_hour2, textField_Min, textLabel_min);
			groupLayout.linkSize(SwingConstants.VERTICAL, textLabel_StartTime, textField_Year, textLabel_year,
					textField_Mon, textLabel_month, textField_Day, textLabel_day, textField_Hour1, textLabel_hour1);
			getContentPane().add(dialogPane);

		}

		// selectName, content_content,teamAName,teamBName, bothLeader, content_maxTurn,
		// content_turnFre, content_time,teamAVc,teamBVc
		/**
		 * @param set_title
		 * @param set_content
		 * @param set_teamANam
		 * @param set_teamBNam
		 * @param set_bothLeader
		 * @param set_maxTurn
		 * @param set_startTime
		 * @param set_turnFre
		 * @param teamA
		 * @param teamB
		 */
		public void setData(String set_title, String set_content, String set_teamANam, String set_teamBNam,
				String set_bothLeader, String set_maxTurn, String set_startTime, String set_turnFre,
				Vector<String> teamA, Vector<String> teamB) {
			textField_title.setText(set_title);
			textField_title.setEditable(false);
			textField_content.setText(set_content);
			String[] tempLe = set_bothLeader.split(",");
			textField_LeaderB.setText(tempLe[0]);

			textField_LeaderA.setText(tempLe[1]);
			textFieLd_teamANa.setText(set_teamANam);
			textField_teamBNa.setText(set_teamBNam);
			textField_MaxTurn.setText(set_maxTurn);

			Vector<Integer> a = new Vector<Integer>();
			Vector<Integer> b = new Vector<Integer>();
			for (String s : teamA) {
				studentListA.setSelectedValue(s, true);
				System.out.println(s);
				int i = studentListA.getSelectedIndex();
				a.add(i);
			}

			for (String s : teamB) {
				studentListB.setSelectedValue(s, true);
				System.out.println(s);
				int i = studentListB.getSelectedIndex();
				b.add(i);
			}
			Integer[] A = a.toArray(new Integer[a.size()]);
			int[] a1;
			a1 = Arrays.stream(A).mapToInt(Integer::valueOf).toArray();
			studentListA.setSelectedIndices(a1);

			Integer[] B = b.toArray(new Integer[b.size()]);
			int[] b1;
			b1 = Arrays.stream(B).mapToInt(Integer::valueOf).toArray();
			studentListB.setSelectedIndices(b1);

			int fre = Integer.parseInt(set_turnFre);
			int hour = fre / 3600000;
			int minute = (fre % 3600000) / 60000;
			textField_Hour2.setText(String.valueOf(hour));
			textField_Min.setText(String.valueOf(minute));
			String[] s1 = set_startTime.split(" ");
			String[] s2 = s1[0].split("-");
			String[] s3 = s1[1].split(":");

			textField_Year.setText(s2[0]);
			textField_Mon.setText(s2[1]);
			textField_Day.setText(s2[2]);
			textField_Hour1.setText(s3[0]);

			this.dialogPane.updateUI();

		}

		public void changeData() {
			String teamAmem = "";
			String teamBmem = "";
			teamAmem = String.join("_", teamAList);
			teamBmem = String.join("_", teamBList);

			OkHttpClient clientSend = new OkHttpClient();
			RequestBody requestBodaySend = new FormBody.Builder().add("title", title_return)
					.add("content_teturn", content_teturn).add("teamAName", nameA_return).add("teamBName", nameB_return)
					.add("teamAMember", teamAmem).add("teamBMember", teamBmem).add("maxTurn", maxTurn_return)
					.add("freHour", freHour_return).add("freMin", freMin_return).add("startTime", startTime_return)
					.add("teamALeader", teamALeader).add("teamBLeader", teamBLeader).build();

			Request requestSend = new Request.Builder().post(requestBodaySend)
					.url("http://localhost:8080/GPMCGroupProject/changeDetail").build();
			try {
				Response responseSend = clientSend.newCall(requestSend).execute();
				if (!responseSend.isSuccessful()) {
//					System.out.println("wanchengle/");
					JOptionPane.showMessageDialog(null, "Can't request server, please check server status");
				} else {
					JOptionPane.showMessageDialog(null, "Finish adding a topic");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void setVer() {
			this.setVisible(true);
		}

		public void sendData() {
			// String keyStr = "++";
			String teamAmem = "";
			String teamBmem = "";
			teamAmem = String.join("_", teamAList);
			teamBmem = String.join("_", teamBList);

			OkHttpClient clientSend = new OkHttpClient();
			RequestBody requestBodaySend = new FormBody.Builder().add("title", title_return)
					.add("content_teturn", content_teturn).add("teamAName", nameA_return).add("teamBName", nameB_return)
					.add("teamAMember", teamAmem).add("teamBMember", teamBmem).add("maxTurn", maxTurn_return)
					.add("freHour", freHour_return).add("freMin", freMin_return).add("startTime", startTime_return)
					.add("teamALeader", teamALeader).add("teamBLeader", teamBLeader).build();

			Request requestSend = new Request.Builder().post(requestBodaySend)
					.url("http://localhost:8080/GPMCGroupProject/storeTopic").build();
			try {
				Response responseSend = clientSend.newCall(requestSend).execute();
				if (!responseSend.isSuccessful()) {
					System.out.println("wanchengle/");
					JOptionPane.showMessageDialog(null, "Can't request server, please check server status");
				} else {
					JOptionPane.showMessageDialog(null, "Finish adding a topic");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public boolean judgeSame() {
			OkHttpClient clientjudge = new OkHttpClient();
			RequestBody requestBodayJudge = new FormBody.Builder().add("selectName", title_return).build();

			Request requestjudge = new Request.Builder().post(requestBodayJudge)
					.url("http://localhost:8080/GPMCGroupProject/queryTopicDetail").build();

			try {
				Response response = clientjudge.newCall(requestjudge).execute();
				if (!response.isSuccessful()) {
					JOptionPane.showMessageDialog(null, "Can't request server, please check server statusjudge");
					return false;
				} else {
					String txt = response.body().string();
					if (!txt.equals("false")) {
						JOptionPane.showMessageDialog(null, "There is a topic named your topic name.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return false;

					} else
						return true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;

			}
		}

		public boolean judge() {
			if (title_return == null || content_teturn == null || maxTurn_return == null || nameB_return == null
					|| nameA_return == null || freHour_return == null || freMin_return == null
					|| starYear_return == null || starMon_return == null || starDay_return == null
					|| starHour_return == null || teamAList.size() == 0 || teamBList.size() == 0) {
				JOptionPane.showMessageDialog(null, "Please complet all blank", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				if (!Collections.disjoint(teamAList, teamBList)) {
					JOptionPane.showMessageDialog(null, "Please choose different members in different team", "Error",
							JOptionPane.ERROR_MESSAGE);
					return false;
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						if (teamAList.size() != teamBList.size()) {
							JOptionPane.showMessageDialog(null,
									"TeamA and TeamB should contain the same number of students.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return false;
						} else if ((!teamAList.contains(teamALeader)) || (!teamBList.contains(teamBLeader))) {
							JOptionPane.showMessageDialog(null, "Team leader should be chooesen from members", "Error",
									JOptionPane.ERROR_MESSAGE);
							return false;
						}
						format.setLenient(false);
						int test1 = Integer.parseInt(maxTurn_return);
						if (test1 <= 0 || test1 % 2 != 0) {
							JOptionPane.showMessageDialog(null, "MaxTurn should be oushu", "Error",
									JOptionPane.ERROR_MESSAGE);
							return false;
						}
						if (starDay_return.length() < 2) {
							starDay_return = "0" + starDay_return;
						}
						if (starMon_return.length() < 2) {
							starMon_return = "0" + starMon_return;
						}
						if (starHour_return.length() < 2) {
							starHour_return = "0" + starHour_return;
						}
						double test2 = Double.parseDouble(freHour_return);
						double test3 = Double.parseDouble(freMin_return);
						String str = starYear_return + '-' + starMon_return + '-' + starDay_return + ' '
								+ starHour_return + ":00:00";
						Date settime = format.parse(str);
						Date now = new Date();
						format.format(now);
						format.format(settime);
						if (now.after(settime)) {
							throw new ParseException(null, 1);
						}
						startTime_return = str;
						return true;
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, "date is not valid", "Error", JOptionPane.ERROR_MESSAGE);
						return false;
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Number is not valid", "Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}

				}
			}
		}
	}

	class turnPanel extends JPanel {
		private JTable moveTable;
		private JLabel moveDetailsLabel;
		private JTextArea moveDetails;
		private JScrollPane moveScroll;
		private JTable turnTable;
		private JScrollPane turnScroll;
		private GroupLayout turnPanelLayout;
		private DefaultTableModel t;

		public turnPanel() {
			super();
			try {
				initGUI();
				setupEventHandlers();
				displayData();

			} catch (DocumentException d) {
				d.printStackTrace();
			}
			this.setVisible(true);
		}

		public Vector<String> prepareData() {
			Vector<String> columnNames = new Vector<String>();
			columnNames.addElement("ownerTeam");
			columnNames.addElement("turnID");
			return columnNames;
		}

		public void displayData() throws DocumentException {

			Vector<String> columnNames = prepareData();
			Vector<Vector> rowData = new Vector<Vector>();
			OkHttpClient client = new OkHttpClient();
			RequestBody req = new FormBody.Builder().add("topicName", selectName).build();
			Request getreq = new Request.Builder().post(req).url("http://localhost:8080/GPMCGroupProject/TurnData")
					.build();
			try {
				Response response = client.newCall(getreq).execute();
				if (!response.isSuccessful()) {
					JOptionPane.showMessageDialog(moveTable, "Problems accessing server");
				} else {
					String str = response.body().string();
					Document doc = DocumentHelper.parseText(str);
					Element root = doc.getRootElement();
					// iterate through child elements of root with element name "move"
					for (Iterator i = root.elementIterator("turn"); i.hasNext();) {
						Element element = (Element) i.next();
						String type = element.elementText("ownerTeam");
						String turnID = element.elementText("turnID");
						Vector<String> rowIt = new Vector<String>();
						rowIt.addElement(type);
						rowIt.addElement(turnID);
						rowData.addElement(rowIt);
					}
					t = new DefaultTableModel(rowData, columnNames);
					turnTable.setModel(t);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void initGUI() {

			turnPanelLayout = new GroupLayout(this);
			this.setLayout(turnPanelLayout);

			{
				turnTable = new JTable();
				turnScroll = new JScrollPane(turnTable);
			}
			{
				DefaultTableModel tmodel = new DefaultTableModel();
				moveTable = new JTable(tmodel);
				moveTable.setVisible(true);
				moveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				moveTable.setRowSelectionAllowed(true);
				moveTable.setColumnSelectionAllowed(false);
				moveScroll = new JScrollPane(moveTable);
			}
			{
				moveDetails = new JTextArea();
				moveScroll = new JScrollPane(moveDetails);
				moveScroll.setVisible(true);

			}
			{
				moveDetailsLabel = new JLabel();
				moveDetailsLabel.setText("Move details");
			}
			turnPanelLayout.setHorizontalGroup(turnPanelLayout.createSequentialGroup().addContainerGap(17, 17)
					.addComponent(turnScroll, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE).addGap(42)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(turnPanelLayout.createParallelGroup()
							.addGroup(turnPanelLayout.createSequentialGroup()
									.addComponent(moveTable, GroupLayout.PREFERRED_SIZE, 420,
											GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
							.addGroup(GroupLayout.Alignment.LEADING,
									turnPanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
											.addComponent(moveDetailsLabel, GroupLayout.PREFERRED_SIZE, 75,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(moveDetails, GroupLayout.PREFERRED_SIZE, 300,
													GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(41, 41));
			turnPanelLayout.setVerticalGroup(turnPanelLayout.createSequentialGroup().addContainerGap(17, 17)
					.addGroup(turnPanelLayout.createParallelGroup().addGroup(turnPanelLayout.createSequentialGroup()
							.addGroup(turnPanelLayout.createParallelGroup()
									.addComponent(moveTable, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
											208, GroupLayout.PREFERRED_SIZE)
									.addGroup(GroupLayout.Alignment.LEADING,
											turnPanelLayout.createSequentialGroup().addGap(198)))
							.addGap(28)
							.addGroup(turnPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(moveDetailsLabel, GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
									.addComponent(moveDetails, GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
							.addGap(9))
							.addComponent(turnScroll, GroupLayout.Alignment.LEADING, 0, 307, Short.MAX_VALUE))
					.addContainerGap());
			this.setSize(723, 407);
		}

		private void setupEventHandlers() {

			turnTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {

					moveDetails.setText(" ");
					int row = turnTable.getSelectedRow();
					Integer ID = Integer.parseInt((String) turnTable.getValueAt(row, 1));

					OkHttpClient client = new OkHttpClient();
					RequestBody req = new FormBody.Builder().add("topicName", selectName).add("username", name)
							.add("turnID", String.valueOf(ID)).build();
					Request getreq = new Request.Builder().post(req)
							.url("http://localhost:8080/GPMCGroupProject/MoveDataQuery").build();

					try {
						Response response = client.newCall(getreq).execute();

						String txt = response.body().string();
						Vector<String> columnNames = new Vector<String>();
						Vector<Vector> rowData = new Vector<Vector>();
						columnNames.addElement("Type");
						columnNames.addElement("TurnID");
						columnNames.addElement("Created By");
						columnNames.addElement("Text Body");
						Document doc = DocumentHelper.parseText(txt);
						Element root = doc.getRootElement();
						// iterate through child elements of root with element name "move"
						for (Iterator i = root.elementIterator("move"); i.hasNext();) {
							Element element = (Element) i.next();
							String type = element.elementText("type");
							String turnID = element.elementText("turnID");
							String createUserID = element.elementText("createUserID");
							String textBody = element.elementText("textBody");
							Vector<String> rowIt = new Vector<String>();
							int turnNumber = Integer.parseInt(turnID);
							if (turnNumber == ID) {
								rowIt.addElement(type);
								rowIt.addElement(turnID);
								rowIt.addElement(createUserID);
								rowIt.addElement(textBody);
								rowData.addElement(rowIt);
							}
						}
						DefaultTableModel table = new DefaultTableModel(rowData, columnNames);
						moveTable.setModel(table);
					} catch (DocumentException | IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			moveTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					moveDetails.updateUI();
					int row = moveTable.getSelectedRow();
					String s = (String) moveTable.getValueAt(row, 3);
					moveDetails.setText(s);

				}
			});
		}
	}

	class movePanel extends JPanel {

		private JTable listMoves;
		private JScrollPane moveScroll;
		private JTextArea textEntry;
		private JScrollPane textScroll;
		private JButton addNewMove;
		private GroupLayout moveLayout;
		private String initialText;
		private JLabel detailsLabel;
		private DefaultTableModel t;

		public movePanel() {
			super();
			try {
				setupComponents();
				setupEventHandlers();
				displayData();
			} catch (DocumentException d) {
				d.printStackTrace();
			}

		}

		public Vector prepareTagData() {
			Vector<String> columnNames = new Vector<String>();
			columnNames.addElement("Type");
			columnNames.addElement("TurnID");
			columnNames.addElement("Created By");
			columnNames.addElement("Text Body");
			return columnNames;
		}

		public void displayData() throws DocumentException {
			DefaultTableModel t;
			// post a request to get data
			Vector<Vector> rowData = new Vector<Vector>();
			Vector<String> columnNames = this.prepareTagData();
			OkHttpClient client = new OkHttpClient();
			RequestBody body = new FormBody.Builder().add("topicName", selectName).add("username", name).build();
			Request request = new Request.Builder().post(body).url("http://localhost:8080/GPMCGroupProject/fillMove")
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (!response.isSuccessful()) {
					JOptionPane.showMessageDialog(null, "Can't request server, please check server status");
				} else {
					String txt = response.body().string();
					Document doc = DocumentHelper.parseText(txt);
					Element root = doc.getRootElement();
					// iterate through child elements of root with element name "move"
					for (Iterator i = root.elementIterator("move"); i.hasNext();) {
						Element element = (Element) i.next();
						String type = element.elementText("type");
						String turnID = element.elementText("turnID");
						String createUserID = element.elementText("createUserID");
						String textBody = element.elementText("textBody");
						Vector<String> rowIt = new Vector<String>();
						rowIt.addElement(type);
						rowIt.addElement(turnID);
						rowIt.addElement(createUserID);
						rowIt.addElement(textBody);
						rowData.addElement(rowIt);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t = new DefaultTableModel(rowData, columnNames);
			listMoves.setModel(t);
		}

		// movescroll = move table
		// adNewMove = new move button
		// textscroll = move details
		public void setupComponents() throws DocumentException {
			// setup the components
			moveLayout = new GroupLayout(this);
			this.setLayout(moveLayout);
			textEntry = new JTextArea();
			textEntry.setText(initialText);
			textScroll = new JScrollPane(textEntry);
			addNewMove = new JButton();
			addNewMove.setText("NEW");
			detailsLabel = new JLabel();
			detailsLabel.setText("Move Text");
			listMoves = new JTable();
			listMoves.setVisible(true);
			listMoves.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listMoves.setRowSelectionAllowed(true);
			listMoves.setColumnSelectionAllowed(false);
			moveScroll = new JScrollPane(listMoves);
			// setup the layout for the components - grouplayout
			moveLayout
					.setHorizontalGroup(moveLayout.createSequentialGroup().addContainerGap()
							.addGroup(moveLayout.createParallelGroup().addGroup(moveLayout
									.createSequentialGroup()
									.addComponent(moveScroll, GroupLayout.PREFERRED_SIZE, 726,
											GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
									.addGroup(GroupLayout.Alignment.LEADING, moveLayout.createSequentialGroup()
											.addComponent(detailsLabel, GroupLayout.PREFERRED_SIZE, 76,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(moveLayout.createParallelGroup()
													.addComponent(textScroll, GroupLayout.Alignment.LEADING, 0, 518,
															Short.MAX_VALUE)
													.addGroup(GroupLayout.Alignment.LEADING, moveLayout
															.createSequentialGroup().addGap(0, 409, Short.MAX_VALUE)
															.addComponent(addNewMove, GroupLayout.PREFERRED_SIZE,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0,
																	GroupLayout.PREFERRED_SIZE)))))
							.addGap(7));
			moveLayout.setVerticalGroup(moveLayout.createSequentialGroup().addContainerGap()
					.addComponent(moveScroll, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(moveLayout.createParallelGroup()
							.addComponent(textScroll, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83,
									GroupLayout.PREFERRED_SIZE)
							.addGroup(GroupLayout.Alignment.LEADING,
									moveLayout.createSequentialGroup()
											.addComponent(detailsLabel, GroupLayout.PREFERRED_SIZE, 66,
													GroupLayout.PREFERRED_SIZE)
											.addGap(17)))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(addNewMove, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(37, Short.MAX_VALUE));
			// load the initial data into the jtable - for now all moves. next iteration
			// will take the filtered information - ie which topic is selected, which team
			// is logged in.
			this.setSize(750, 600);

		}

		public void setupEventHandlers() {
			// listselectionlistener for jtable
			listMoves.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					// find the index of the row selected, that is turnid
					// display the message from that row....
					int row = listMoves.getSelectedRow();
					String s = (String) listMoves.getValueAt(row, 3);
					textEntry.setText(s);
				}
			});
			// actionlistener> new form for 'new' button
			addNewMove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// setup the new move class..
					String[] s = initialData.split("<username>");
					String[] ss = s[s.length - 1].split("</username>");
					String user = ss[0];

					AddMove m = new AddMove(selectName, user);
					m.setVisible(true);
				}
			});
		}
	}

	class downLoadReportPanel  extends JDialog {
		private JLabel jLUploadFile;
		private JTextField JTextFileName;
		private JButton JBStoreFile;
		private JButton JBUploadFile;
		private JFileChooser jfc;
		private String downLoadPath;  
		private String downLoadFileName; 
		private String topicName;
		public downLoadReportPanel(JFrame f, String topicName) {
			super(f, "Download file", true);
			
			Container container = getContentPane();

			setSize(500, 100);
			this.topicName = topicName;
			System.out.println("topic name fomr download panel : " + topicName);
			intialUI();
		}
		public void intialUI() {
			jLUploadFile = new JLabel("Store Folder");
			JTextFileName = new JTextField();
//			JTextFileName.setSize();
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
					JTextFileName.setText(downLoadPath);
					System.out.println(downLoadFileName);
					
				}
			});
			//send request here
			JBUploadFile.addActionListener(l->{
				if(downLoadPath.equals("") == false) {  //
					OkHttpClient client = new OkHttpClient();
					RequestBody requestBody = new FormBody.Builder().add("topicName", topicName).build();
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
							downLoadPath = downLoadPath + File.separator + "report.pdf";
							File file = new File(downLoadPath);
							fileOutputStream = new FileOutputStream(file);
							while((len = input.read(buf))!=-1) {
								fileOutputStream.write(buf,0,len);
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
			
			this.setLayout(null);
			this.setResizable(false);
			jLUploadFile.setBounds(20, 20, 100, 30);
			JTextFileName.setBounds(120, 20, 100, 30);
			JBStoreFile.setBounds(220, 20, 100, 30);
			JBUploadFile.setBounds(320, 20, 100, 30);
			this.add(jLUploadFile);
			this.add(JTextFileName);
			this.add(JBStoreFile);
			this.add(JBUploadFile);
			
			double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
	        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
	        this.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));
			
	        
	        this.setVisible(true);
		}
	}
}
