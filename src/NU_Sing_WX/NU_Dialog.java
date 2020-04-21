package NU_Sing_WX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NU_Dialog extends JDialog
{		
		protected static JLabel dialogLabel;
		protected static JLabel saveLabel;
		protected static JLabel markLabel;
		
		protected static JPanel dialogCenterPanel;

		public NU_Dialog(JFrame owner, String labelContent, String yesContent, String noContent)
		{	
			super(owner,"NU_Sing",true);
			
			dialogCenterPanel = new JPanel();
			dialogCenterPanel.setBackground(Color.black);
			
			dialogLabel = new JLabel(labelContent);
			Font front = new Font("Tahoma",Font.PLAIN,20);
			dialogLabel.setFont(front);
			dialogLabel.setForeground(Color.white);
			dialogLabel.setBackground(Color.black);
			dialogLabel.setOpaque(true);
			dialogLabel.setVisible(true);						
			
			NU_Text.createWorkName = new JTextField(20);
			NU_Text.createWorkName.setPreferredSize(new Dimension (200,35));
			NU_Text.createWorkName.setVisible(false);
			
			markLabel = new JLabel("    Your work score is : " + Compare.result);
			markLabel.setFont(front);
			markLabel.setForeground(Color.white);
			markLabel.setBackground(Color.black);
			markLabel.setOpaque(true);
			markLabel.setVisible(false);


			
			saveLabel = new JLabel("    Please input your work name: ");
			saveLabel.setFont(front);
			saveLabel.setForeground(Color.white);
			saveLabel.setBackground(Color.black);
			saveLabel.setOpaque(true);
			saveLabel.setVisible(false);
			
			dialogCenterPanel.add(dialogLabel);
			dialogCenterPanel.add(saveLabel);
			dialogCenterPanel.add(markLabel);
			dialogCenterPanel.add(NU_Text.createWorkName);
			add(dialogCenterPanel,BorderLayout.CENTER);
			
			
			NU_Button.yes.setFont(front);
			NU_Button.no.setFont(front);
			NU_Button.mark.setFont(front);
			NU_Button.save.setFont(front);
			JPanel dialogPanel = new JPanel();
			dialogPanel.setBackground(Color.black);													
			dialogPanel.add(NU_Button.yes);
			dialogPanel.add(NU_Button.no);
			NU_Button.mark.setVisible(false);
			NU_Button.save.setVisible(false);
			dialogPanel.add(NU_Button.save);
			NU_Button.save.setVisible(false);
			dialogPanel.add(NU_Button.mark);
			add(dialogPanel,BorderLayout.SOUTH);
			setSize(400,200);
			

		}
}


