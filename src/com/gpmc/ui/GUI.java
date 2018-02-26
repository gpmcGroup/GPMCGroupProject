package com.gpmc.ui;
//import com.cloudgarden.layout.AnchorConstraint;
//import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
public class GUI extends javax.swing.JFrame implements ActionListener {

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

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI inst = new GUI();
                inst.setLayout(null);
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
            }
        });
    }

    public GUI() {
        super();
        initGUI();
    }

    private void initGUI() {
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
                jBChat.addActionListener(this);
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

        }
        if(e.getSource()==jBMove) {
            jPBasic.removeAll();
            jPBasic.repaint();
            //jPBasic.add(new movePanel()); //movelist
        }
        if(e.getSource()==jBTurn) {
            jPBasic.removeAll();
            jPBasic.repaint();
            //this.jPBasic.add(new turnPanel());
        }
        if(e.getSource()==jBChat) {
            jPBasic.removeAll();
            //chat
        }
        if(e.getSource()==jBStatisc) {
            jPBasic.removeAll();
            //report
        }
    }

}

class listPanel extends JPanel implements ActionListener{

    private JButton jBPlus;
    private JButton jBReudce;
    private JList jLTopic;

    public listPanel() {                                     				//add user a
        this.setBounds(24, 81, 290, 518);
        this.setLayout(null);

        ListModel jLTopicModel =
                new DefaultComboBoxModel(
                        new String[] {"item one", "intem two"});  			//a.getTopicList()
        jLTopic = new JList();
        jLTopic.setModel(jLTopicModel);
        jLTopic.setBounds(12, 12, 266, 444);

        jBReudce = new JButton();
        jBReudce.setText("-");
        jBReudce.setBounds(145, 462, 79, 39);

        jBPlus = new JButton();
        jBPlus.setText("+");
        jBPlus.setBounds(44, 462, 79, 39);

        this.add(jBPlus);
        this.add(jBReudce);

//		if(a.getType()= "tutor") {
//
//			jBReudce = new JButton();
//			jBReudce.setText("-");
//			jBReudce.setBounds(75, 249, 54, 36);
//
//			jBPlus = new JButton();
//			jBPlus.setText("+");
//			jBPlus.addActionListener(this);
//			jBPlus.setBounds(16, 249, 54, 36);
//
//			this.add(jBPlus);
//			this.add(jBReudce);
//		}
        this.add(jLTopic);
        this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//newTopic nt = new newTopic();
	}
}

class introductionPanel extends JPanel{
    public introductionPanel() {
        String title = "Title";
        String content = "asdffffffffffffff" ;
        JTextArea introduction = new JTextArea(title + "\n" +content,25,60);
        introduction.setLineWrap(true);// 婵�娲昏嚜鍔ㄦ崲琛屽姛鑳�?
        introduction.setWrapStyleWord(true);
        introduction.setBounds(7, 7, 748, 443);
        JScrollPane sp = new JScrollPane(introduction);

        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( 	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(sp);
        this.setBounds(0, 23, 800, 400);

    }

}

class turnPanel extends JPanel{
    JScrollPane sp = new JScrollPane();

    public turnPanel(List ori_turn[]) {
        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( 	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setBounds(0,23,800,400);
        this.setLayout(new GridLayout(0,1,10,10));
        JButton JBCreate = new JButton("Create");
        JButton JBRemove = new JButton("Remove");
        for(int i=0; i <= ori_turn.length;i++) {
            //addTurn(ori_turn[i].title, ori_turn[i].content);
        }
        this.add(sp);
    }

    public void addTurn(String title, String content) {
        JTextArea turn = new JTextArea("team" + title + "\n" +content,25,60);
        turn.setLineWrap(true);// 婵�娲昏嚜鍔ㄦ崲琛屽姛鑳�?
        turn.setWrapStyleWord(true);
        sp.add(turn);
    }

}

class movePanel extends JPanel{
    JScrollPane sp = new JScrollPane();

    public movePanel(List ori_move[]) {
        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( 	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setBounds(0,23,800,400);
        this.setLayout(new GridLayout(0,1,10,10));
        JButton JBCreate = new JButton("Create");
        JButton JBRemove = new JButton("Remove");
        for(int i=0; i <= ori_move.length;i++) {
            //addmove(ori_move[i].title, ori_move[i].content);
        }
        this.add(sp);
    }

    public void addmove(String title, String content) {
        JTextArea turn = new JTextArea(title + "\n" +content,25,60);
        turn.setLineWrap(true);// 婵�娲昏嚜鍔ㄦ崲琛屽姛鑳�?
        turn.setWrapStyleWord(true);
        sp.add(turn);
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

