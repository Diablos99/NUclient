package NU_Sing_WX;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;



import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import NU_Sing_WX.mysql;

public class login extends JFrame implements ActionListener{
	boolean flag;
	static String tname;
	static String nname;
	private static final long serialVersionUID = 1L;
	JPanel mb;
	JLabel lb,lb1,lb2,lb3;
	JLabel namelb ,passwordlab;
	JButton bt,bt1,bt2;
	protected static JTextField username;	
	JPasswordField password;
	JCheckBox select1,select2;
	public login() {
		lb=new JLabel(new ImageIcon("MS.jpg"));
		mb=new JPanel();
		username =new JTextField(20);
		password=new JPasswordField(20);
		namelb = new JLabel("Name");
		passwordlab = new JLabel("Code");
		bt1 = new JButton("register");
		bt2 = new JButton("Forget");
		select1=new JCheckBox("remember me");
		select2=new JCheckBox("Auto login");
		bt=new JButton("login");
		setCheckBoxInfo();
		setButtonInfo();
		setLabInfo();
		setPanelInfo();
		addComponent();
		setFrame();
	}
	public void setCheckBoxInfo() {
		select1.setFont(new Font("Arial",Font.PLAIN,15));
		select1.setBackground(Color.WHITE);
		select2.setFont(new Font("Arial",Font.PLAIN,15));
		select2.setBackground(new Color(255,250,250));
		select1.setBounds(130,90,120,20);
		select2.setBounds(290,90,120,20);	
	}
	public void setButtonInfo() {
		bt.setBounds(130,130,250,37);
		bt.setFont(new Font("Arial",Font.PLAIN,16));
		bt.setBackground(new Color(0,178,238));
		bt.setForeground(Color.white);
		bt.addActionListener(this);
		bt1.setBounds(420,10,90,30);
		bt2.setBounds(420,50,90,30);
		bt1.setForeground(new Color(28,134,238));
		bt1.setFont(new Font("Arial",Font.PLAIN,13));
		bt1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bt1.addActionListener(this);
		bt2.setForeground(new Color(28,134,238));
		bt2.setFont(new Font("Arial",Font.PLAIN,13));
		bt2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bt2.addActionListener(this);
	}
	public void setFrame() {
		this.setSize(550, 400);
		this.setTitle("Load");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(400, 180);
		this.setResizable(false);
		this.setVisible(true);
	}
	public void setLabInfo() {
		username.setBounds(130, 14, 250, 37);
		username.setFont(new Font("Arial",Font.PLAIN,16));	
		password.setBounds(130,48, 250, 37);
		password.setFont(new Font("Arial",Font.PLAIN,16));	
		namelb.setBounds(50,14,40,37);
		passwordlab.setBounds(50, 48, 40, 37);
	}
	public void setPanelInfo() {	
		mb.setLayout(null);
		mb.setBackground(Color.white);
	}
	public void addComponent() {
		mb.add(bt2);mb.add(bt1);mb.add(select1);mb.add(select2);
		mb.add(bt);mb.add(username);mb.add(password);
		mb.setSize(540,190);mb.add(namelb);mb.add(passwordlab);
		this.add(lb,BorderLayout.NORTH);	
		this.add(mb,BorderLayout.CENTER);
	}
	public void findPassword() {
		this.dispose();
		forgetPassword fw  = new forgetPassword();
		fw.setVisible(true);
		
	}
	public void regist() {
		this.dispose();
		register rw  = new register();
		rw.setLocationRelativeTo(NU_MainFrame.mainFrame);
		 rw.setVisible(true);
	}
	public void loginJudge() {
		mysql mysql = new mysql();
		mysql.connectSQL();
		
		tname = username.getText();
		nname = username.getText();
		String tpass = new String (password.getPassword());
		try {
			this.flag = mysql.loginMatch(tname,tpass);
		} catch (SQLException e2) {
			
			e2.printStackTrace();
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}
		username.setText("");
		password.setText("");
		if(flag) {
			this.dispose();
			NU_MainFrame.mainFrame.setVisible(true);
				
		}
		else {
			warm ww = new warm();
			ww.loginFail();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt)
		{
			this.loginJudge();			

		}
		else if(e.getSource()==bt1) {
			this.regist();
		}
		else if(e.getSource()==bt2) {
			this.findPassword();
		}
	}
}
