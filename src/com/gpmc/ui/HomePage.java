package com.gpmc.ui;
//import com.cloudgarden.layout.AnchorConstraint;
//import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gpmc.ChatTest.ChatClient;
import com.gpmc.modelClass.User;
import com.gpmc.util.xmlUtil;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.swing.SwingUtilities;


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
public class HomePage extends javax.swing.JFrame implements ActionListener {

    {
        //Set Look & Feel
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    String name;
    private JTextArea test;
    private JLabel jLaWelcome;
    private JPanel jPBasic;

    private JButton jBTurn;
    private JButton jBMove;
    private JButton jBIntroduction;
    private JButton jBStatisc;
    private JButton jBChat;
    private JButton jBLogout;
    
    private User loginUser;
    
    private String initialData;
    private Document doc;
    private Vector<String> topicList;
    /**
     * Auto-generated main method to display this JFrame
     */

    
    
    class listPanel extends JPanel implements ActionListener{

        private JButton jBPlus;
        private JButton jBReudce;
        private JList jLTopic;

        public listPanel() {                                     				//add user a
            this.setBounds(24, 81, 290, 518);
            this.setLayout(null);

            	
            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.setBounds(12,12,266,444);
            jLTopic = new JList();
            jLTopic.setListData(topicList);
            jScrollPane.setViewportView(jLTopic);
            	
            jBReudce = new JButton();
            jBReudce.setText("-");
            jBReudce.setBounds(145, 462, 79, 39);

            jBPlus = new JButton();
            jBPlus.setText("+");
            jBPlus.setBounds(44, 462, 79, 39);

            this.add(jBPlus);
            this.add(jBReudce);

            this.add(jScrollPane);
            
            this.setVisible(true);
        }

    	@Override
    	public void actionPerformed(ActionEvent e) {
    		
    		//newTopic nt = new newTopic();
    	}
    }
    
    
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
			//topic list data 
			Element eleTopic = (Element) doc.selectSingleNode("//user/topicList");
			if(eleTopic!=null) {
				for(Iterator<Element> it = eleTopic.elementIterator("topic");it.hasNext();) {
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
            }

            this.jPBasic = new JPBasic();
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            getContentPane().setLayout(null);
            getContentPane().add(new listPanel());
            getContentPane().add(jPBasic);

            jPBasic.add(new introductionPanel());
            
            
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

            };

            {
                jBChat = new JButton();
                getContentPane().add(jBChat);
                jBChat.setText("Chat");
                jBChat.setBounds(927, 76, 108, 35);
                jBChat.addActionListener(l->{
	                	//Handler information
	        			Document doc;
						try {
							doc = DocumentHelper.parseText(initialData);
							String teamName = doc.valueOf("//user/teamList/team/teamName");
							
			        			if(!teamName.equals("")) {
			        				//request chat port from server
				        			OkHttpClient client = new OkHttpClient();
				        			RequestBody requestBoday = new FormBody.Builder().add("teamName",teamName).build();
				        			Request request = new Request.Builder().post(requestBoday).url("http://localhost:8080/GPMCGroupProject/chatPortQuery").build();
				        			
				        			Response response = client.newCall(request).execute();
				        			if(!response.isSuccessful()) {
				        				JOptionPane.showMessageDialog(null, "Can't request server, please check server status");
				        			}else {
				        				String txt = response.body().string();
				        				if(txt.equals("null")) {
				        					JOptionPane.showMessageDialog(null, "team Not port");
				        				}else {
				        					ChatClient inst = new ChatClient(name,"127.0.0.1",txt);
					            			inst.setLocationRelativeTo(null);
					            			inst.setVisible(true);
					            			inst.setResizable(false);
				        				}
				        			}
			        			}else {
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
                jBStatisc.addActionListener(this);
            }
            {
                jBMove = new JButton();
                getContentPane().add(jBMove);
                jBMove.setText("Move");
                jBMove.setBounds(644, 76, 102, 35);
                jBMove.addActionListener(this);
            }
            {
                this.jBTurn = new JButton();
                getContentPane().add(jBTurn);
                this.jBTurn.setText("Turns");
                this.jBTurn.setBounds(496, 76, 101, 35);
                this.jBTurn.addActionListener(this);
            }
            {
                jBIntroduction = new JButton();
                getContentPane().add(jBIntroduction);
                jBIntroduction.setText("Introduction");
                jBIntroduction.setBounds(349, 76, 102, 35);
                jBIntroduction.addActionListener(this);
            }

            jBLogout.addActionListener(new ActionListener() {//logout button action event

                public void actionPerformed(ActionEvent e) {
                    String msg="Do you want log out?";
                    JOptionPane.showConfirmDialog(null, "Do you want to logout?", "confirm",JOptionPane.YES_NO_OPTION);
                    //if(true)
                    //return to logtin
                }
            });

            pack();
            this.setSize(1142, 655);
        } catch (Exception e) {
            //add your error handling code here
            e.printStackTrace();
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jBIntroduction) {
            jPBasic.removeAll();
            jPBasic.add(new introductionPanel());
            jPBasic.updateUI();
            jPBasic.repaint();

        }
        if(e.getSource()==jBMove) {
            this.jPBasic.removeAll();
            jPBasic.updateUI();
            jPBasic.repaint();

            //jPBasic.add(new movePanel()); //movelist
        }
        if(e.getSource()==jBTurn) {
            jPBasic.removeAll();
            jPBasic.updateUI();
            jPBasic.repaint();
            //this.jPBasic.add(new turnPanel());
        }
        if(e.getSource()==jBChat) {
            jPBasic.removeAll();
            jPBasic.updateUI();
            jPBasic.repaint();
            //chat
        }
        if(e.getSource()==jBStatisc) {
            jPBasic.removeAll();
            jPBasic.updateUI();
            jPBasic.repaint();
            //report
        }
    }

}



class introductionPanel extends JPanel{
    public introductionPanel() {
        String title = "Title";
        String content = "asdffffffffffffff" ;
        JTextArea introduction = new JTextArea(title + "\n" +content,25,60);
        introduction.setLineWrap(true);
        introduction.setWrapStyleWord(true);
        introduction.setBounds(7, 7, 748, 443);
        JScrollPane sp = new JScrollPane(introduction);

        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( 	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(sp);
        this.setBounds(0, 23, 800, 400);
        this.setVisible(true);

    }

}

class turnPanel extends JPanel{
     public turnPanel(List ori_turn[]) {
    	 //add content showing in the turn panel
    }

}

class movePanel extends JPanel{
    public movePanel(List ori_move[]) {
    	//add content showing in the move panel
    }
     

    public void addmove(String title, String content) {
    	//add move if you need this method
    }
}

class newTopic extends  JDialog implements ActionListener{
    JScrollPane sp = new JScrollPane();

    public newTopic (JFrame f) {
        super(f,"please input topic and introduction",true);
        Container container = getContentPane();
        setBounds(0,23,688, 456);
        setLayout(new GridLayout(0,1,10,10));
        JTextArea title = new JTextArea(25,60);
        JTextArea content = new JTextArea(25,60);
        sp.add(content);
        this.add(title);
        this.add(sp);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}

class JPBasic extends JPanel{
    public JPBasic(){
        this.setLayout(null);
        this.setBounds(299, 122, 797, 473);
    }
}

