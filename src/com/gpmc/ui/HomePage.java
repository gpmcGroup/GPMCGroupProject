package com.gpmc.ui;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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

    private String name;
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
                jBLogout.addActionListener(l->{
                		
                	
                });

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
            movePanel mp = new movePanel();
			jPBasic.add(mp);
			mp.setVisible(true);
            jPBasic.updateUI();
            jPBasic.repaint();

            //jPBasic.add(new movePanel()); //movelist
        }
        if(e.getSource()==jBTurn) {
            jPBasic.removeAll();
            turnPanel tp = new turnPanel();
            jPBasic.add(tp);
            tp.setVisible(true);
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
//here
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

    class turnPanel extends JPanel {
    	private JTable moveTable;
    	private JLabel moveDetailsLabel;
    	private JTextArea moveDetails;
    	private JScrollPane moveScroll;
    	private JTable turnTable;
      	private JScrollPane turnScroll;
    	private GroupLayout turnPanelLayout;
    	private DefaultTableModel t;
    	
    	public turnPanel(){
    		super();
    		try {
    		initGUI();
    		setupEventHandlers();
    		displayData();
    	
    		} catch(DocumentException d) {
    			d.printStackTrace();
    		}
    		this.setVisible(true);
    	}
    	
    
    	
      	public void displayData() throws DocumentException{
      		OkHttpClient client = new OkHttpClient();
    		RequestBody req = new FormBody.Builder().build();
    		Request getreq = new Request.Builder().post(req).url("http://localhost:8080/GPMCGroupProject/MoveData").build();
      		
    		try {
    			Response response = client.newCall(getreq).execute();
    			
    			if(!response.isSuccessful()) {
    				JOptionPane.showMessageDialog(moveTable, "Problems accessing server");
    		} else {
    			t = new DefaultTableModel();
    			t = xmlUtil.fillTurnData();
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
    					turnPanelLayout.setHorizontalGroup(turnPanelLayout.createSequentialGroup()
    					.addContainerGap(17, 17)
    					.addComponent(turnScroll, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
    					.addGap(42)
    					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    					.addGroup(turnPanelLayout.createParallelGroup()
    					    .addGroup(turnPanelLayout.createSequentialGroup()
    					        .addComponent(moveTable, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
    					        .addGap(0, 0, Short.MAX_VALUE))
    					    .addGroup(GroupLayout.Alignment.LEADING, turnPanelLayout.createSequentialGroup()
    					        .addGap(0, 0, Short.MAX_VALUE)
    					        .addComponent(moveDetailsLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
    					        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    					        .addComponent(moveDetails, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
    					.addContainerGap(41, 41));
    					turnPanelLayout.setVerticalGroup(turnPanelLayout.createSequentialGroup()
    					.addContainerGap(17, 17)
    					.addGroup(turnPanelLayout.createParallelGroup()
    					    .addGroup(turnPanelLayout.createSequentialGroup()
    					        .addGroup(turnPanelLayout.createParallelGroup()
    					            .addComponent(moveTable, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
    					            .addGroup(GroupLayout.Alignment.LEADING, turnPanelLayout.createSequentialGroup()
    					                .addGap(198)))
    					        .addGap(28)
    					        .addGroup(turnPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    					            .addComponent(moveDetailsLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
    					            .addComponent(moveDetails, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
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
    				Integer turnID = Integer.parseInt((String) turnTable.getValueAt(row, 1));
    				
    				try {
    					DefaultTableModel table = new DefaultTableModel();
						table = xmlUtil.fillMoveData(turnID);
						moveTable.setModel(table);
					} catch (DocumentException e1) {
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
    	
    	
    	public void displayData() throws DocumentException{
    			DefaultTableModel t = new DefaultTableModel();
    			t= xmlUtil.fillMoveData();
    			listMoves.setModel(t);
    	}
    	
    	

    	//movescroll = move table
    	//adNewMove = new move button
    	//textscroll = move details
    	public void setupComponents() throws DocumentException {
    		//setup the components
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
    		//setup the layout for the components - grouplayout
    		moveLayout.setHorizontalGroup(moveLayout.createSequentialGroup()
    				.addContainerGap()
    				.addGroup(moveLayout.createParallelGroup()
    				    .addGroup(moveLayout.createSequentialGroup()
    				        .addComponent(moveScroll, GroupLayout.PREFERRED_SIZE, 726, GroupLayout.PREFERRED_SIZE)
    				        .addGap(0, 0, Short.MAX_VALUE))
    				    .addGroup(GroupLayout.Alignment.LEADING, moveLayout.createSequentialGroup()
    				        .addComponent(detailsLabel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
    				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    				        .addGroup(moveLayout.createParallelGroup()
    				            .addComponent(textScroll, GroupLayout.Alignment.LEADING, 0, 518, Short.MAX_VALUE)
    				            .addGroup(GroupLayout.Alignment.LEADING, moveLayout.createSequentialGroup()
    				                .addGap(0, 409, Short.MAX_VALUE)
    				                .addComponent(addNewMove, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
    				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, GroupLayout.PREFERRED_SIZE)))))
    				.addGap(7));
    				moveLayout.setVerticalGroup(moveLayout.createSequentialGroup()
    				.addContainerGap()
    				.addComponent(moveScroll, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
    				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    				.addGroup(moveLayout.createParallelGroup()
    				    .addComponent(textScroll, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
    				    .addGroup(GroupLayout.Alignment.LEADING, moveLayout.createSequentialGroup()
    				        .addComponent(detailsLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
    				        .addGap(17)))
    				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    				.addComponent(addNewMove, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
    				.addContainerGap(37, Short.MAX_VALUE));
    		//load the initial data into the jtable - for now all moves. next iteration will take the filtered information - ie which topic is selected, which team is logged in.	
    		this.setSize(750,600);
    	
    	}
    	
    	public void setupEventHandlers() {
    		//listselectionlistener for jtable
    		listMoves.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    			public void valueChanged(ListSelectionEvent e) {
    				//find the index of the row selected, that is turnid
    				//display the message from that row....
    				int row = listMoves.getSelectedRow();
    				String s = (String) listMoves.getValueAt(row, 3);
    				textEntry.setText(s);
    			}
    		});
    		//actionlistener> new form for 'new' button
    		addNewMove.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				//setup the new move class.. 
    				String[] s = initialData.split("<username>");
    				String[] ss = s[s.length-1].split("</username>");
    				String user = ss[0];
    				
    				AddMove m = new AddMove(user);
    				m.setVisible(true);
    			
    			}
    		});
    		
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
    
}






