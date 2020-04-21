package NU_Sing_WX;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import NU_Sing_WX.mysql;
import NU_Sing_WX.User;

public class register extends JFrame implements ActionListener{
	JPanel mb;
	JButton bt,bt1;
	JComboBox cb = null;
	Vector<String> v ;
	JLabel namelb,introductionlb,answerlb,securityQuestionlb;
	JLabel passwordlb,passwordlb2;
	JTextField name,introduction,answer;	
	JPasswordField password,rePassword;
	public register(){
		password = new JPasswordField(20);
		rePassword = new JPasswordField(20);
		name = new JTextField(20);
		introduction = new JTextField(20);
		answer = new JTextField(20);
		introductionlb = new JLabel("introduction");
		namelb = new JLabel("name");
		passwordlb = new JLabel("password");
		passwordlb2 = new JLabel("R-password");
		answerlb = new JLabel("Answer");
		securityQuestionlb = new JLabel("safe question");
		setComboBox();
		mb=new JPanel();
		bt=new JButton("register");
		bt1=new JButton("empty");
		setLocation();
		setPanelInfo();
		setFrame();
		setButton();
		setTextfieldInfo();
	}
	public void setComboBox() {
		v = new Vector<String> ();
		cb = new JComboBox(v);
		v.add("Whre are you from");
		v.add("what is your name");
		v.add("where are you living");
		cb.setBorder(BorderFactory.createTitledBorder("Choose you security Question"));
		cb.setMaximumRowCount(3);
	}
	public void setButton() {
		bt.setFont(new Font("",Font.PLAIN,15));
		//bt.setBackground(new Color(0,178,238));
		bt.setForeground(Color.black);
		bt1.setFont(new Font("Arial",Font.PLAIN,15));
		//bt1.setBackground(new Color(0,178,238));
		bt1.setForeground(Color.black);
		bt.addActionListener(this);
		bt1.addActionListener(this);
	}
	public void setTextfieldInfo() {
		name.setFont(new Font("Arial",Font.PLAIN,20));
		answer.setFont(new Font("Arial",Font.PLAIN,20));
		introduction.setFont(new Font("Arial",Font.PLAIN,20));
		password.setFont(new Font("Arial",Font.PLAIN,20));
		rePassword.setFont(new Font("Arial",Font.PLAIN,20));
		name.setBounds(130, 48, 250, 37);
		introduction.setBounds(130,180, 250, 37);
		answer.setBounds(130,268, 250, 37);
		password.setBounds(130,92, 250, 37);
		rePassword.setBounds(130,136, 250, 37);
	}
	public void setFrame() {
		this.setLocation(400, 180);
		this.setSize(700, 450);
		this.setVisible(true);
	}
	public void setLocation() {
		namelb.setBounds(50,48,100,37);
		passwordlb.setBounds(50, 92, 100, 37);
		passwordlb2.setBounds(50, 136, 100,37);
		introductionlb.setBounds(50,180,100,37);
		securityQuestionlb.setBounds(50,224,100,37);
		answerlb.setBounds(50,268,100,37);
		name.setBounds(160, 48, 250, 37);
		introduction.setBounds(160,180, 250, 37);
		answer.setBounds(160,268, 250, 37);
		password.setBounds(160,92, 250, 37);
		rePassword.setBounds(160,136, 250, 37);
		bt.setBounds(160,350,100,37);
		bt1.setBounds(270,350,100,37);
		cb.setBounds(130,224,250,45);
	}
	public void setPanelInfo() {
		mb.setLayout(null);
		mb.setBackground(Color.white);
		mb.add(namelb); mb.add(answerlb); mb.add(introductionlb);
		mb.add(passwordlb);mb.add(passwordlb2);
		mb.add(securityQuestionlb);
		mb.add(answer); mb.add(introduction); mb.add(name);
		mb.add(password); mb.add(rePassword); mb.add(bt); mb.add(bt1);
		mb.add(cb);
		this.add(mb);
	}
	public void clearTextfield() {
		password.setText("");
		rePassword.setText("");
		name.setText("");
		introduction.setText("");
		answer.setText("");
		password.setText("");
	}

	public boolean checkLegal(String tpass, String repass) {
		if(tpass.equals(repass)) return true;
		else return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt)
		{
			String tpass = new String (password.getPassword());
			String repass = new String (rePassword.getPassword());
			if (this.checkLegal(tpass, repass)) {
				if(introduction.getText().length()==0||name.getText().length()==0||answer.getText().length()==0) {
					warm ww = new warm();
					ww.registIncomplete();
				}
				else {
					User user = new User(); 
					user.setName(name.getText());
					String str = new String(password.getPassword());
					user.setPassword(str);					
					user.setId(User.user_id);
					user.setIntroduction(introduction.getText());
					user.setSecurityQuestion((String) cb.getSelectedItem());
					user.setSecurityAnswer(answer.getText());
					User.user_id++;
					NU_Save.saveUserIDSettings();
					mysql mq = new mysql();
					mq.connectSQL();
					try {
						if(mq.register(user)) {
							warm ww = new warm();
							ww.registerSuccessful();
							login lw = new login();
							this.dispose();
						}else {
							warm ww = new warm();
							ww.sqlConnectFail();
						}
					}catch (SQLException e1) {e1.printStackTrace();}	
				}
			}else{
					warm ww = new warm();
					ww.reputCheckWarning();
					clearTextfield();
				}
		}
		else if(e.getSource()==bt1) {
			clearTextfield();
		}
	}
}
