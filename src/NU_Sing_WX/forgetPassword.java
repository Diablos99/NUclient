package NU_Sing_WX;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import NU_Sing_WX.mysql;
import NU_Sing_WX.User;

public class forgetPassword extends JFrame implements ActionListener{
	JPanel mb;
	JButton bt,bt1;
	JComboBox cb = null;
	Vector<String> v ;
	JLabel namelb,user_idlb,answerlb,securityQuestionlb;
	JLabel passwordlb;

	JTextField name,user_id,answer;	

	public forgetPassword(){

		name = new JTextField(20);
		user_id = new JTextField(20);
		answer = new JTextField(20);
		namelb = new JLabel("User name");
		user_idlb = new JLabel("User ID");
		answerlb = new JLabel("Answer");
		securityQuestionlb = new JLabel("Safe question");
		setComboBox();
		mb=new JPanel();
		bt=new JButton("Find");
		bt1=new JButton("Quit");
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
		bt.setFont(new Font("Arial",Font.PLAIN,15));
		bt.setForeground(Color.black);
		bt1.setFont(new Font("Arial",Font.PLAIN,15));
		bt1.setForeground(Color.black);
		bt.addActionListener(this);
		bt1.addActionListener(this);
	}
	public void setTextfieldInfo() {
		name.setFont(new Font("Arial",Font.PLAIN,16));
		answer.setFont(new Font("Arial",Font.PLAIN,16));
		user_id.setFont(new Font("Arial",Font.PLAIN,16));
		
	}
	public void setFrame() {
		this.setLocation(400, 180);
		this.setSize(700, 450);
		this.setVisible(true);
	}
	public void setLocation() {
		namelb.setBounds(50,14,100,37);
		user_idlb.setBounds(50,48,100,37);
		securityQuestionlb.setBounds(50, 136,100,37);
		cb.setBounds(160,136,250,45);
		answerlb.setBounds(50,180,100,37);
		name.setBounds(160, 14, 250, 37);
		user_id.setBounds(160,48, 250, 37);
		answer.setBounds(160,180,250,37);
		bt.setBounds(160,350,100,37);
		bt1.setBounds(270,350,100,37);
		
	}
	public void setPanelInfo() {	
		mb.setLayout(null);
		mb.setBackground(Color.white);
		mb.add(namelb); mb.add(answerlb); 
	
		mb.add(user_idlb);
		mb.add(securityQuestionlb);mb.add(user_id);
		mb.add(answer); mb.add(name);
		
		mb.add(bt); mb.add(bt1);
		mb.add(cb);
		this.add(mb);
	}
	public void clearTextfield() {

		name.setText("");
		user_id.setText("");
		answer.setText("");
	}

	public boolean checkLegal(String tpass, String repass) {
		if(tpass.equals(repass)) return true;
		else return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt)
		{
				//Determine whether the information is complete
				if(name.getText().length()==0) {
					warm ww = new warm();
					ww.registIncomplete();
				}
				//if complete
				else {
					User user = new User(); 
					user.setName(name.getText());
					user.setSecurityQuestion((String) cb.getSelectedItem());
					user.setSecurityAnswer(answer.getText());
					mysql mq = new mysql();
					mq.connectSQL();
					try {
						String pass = mq.findPassword(user);
						if(pass != null) {
							passwordlb = new JLabel("Your password is"+pass);
							mb.add(passwordlb);
							passwordlb.setBounds(160,268, 250, 37);
							passwordlb.setFont(new Font("Arial",Font.PLAIN,16));
						}else {
							warm ww = new warm();
							ww.findFail();
						}
					}catch (SQLException e1) {e1.printStackTrace();}	
				}
			}
		else if(e.getSource()==bt1) {
			this.dispose();
		}
	}
}
