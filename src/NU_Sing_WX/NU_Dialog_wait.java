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
public class NU_Dialog_wait extends JDialog
{
	protected static JLabel addsongsLabel;
	protected static JPanel addsongsCenterPanel;
	
	public NU_Dialog_wait(JFrame owner, String labelContent)
	{
		super(owner,"NU_Sing",true);
		addsongsCenterPanel = new JPanel();
		addsongsCenterPanel.setBackground(Color.black);
		
		addsongsLabel = new JLabel(labelContent);
		Font front = new Font("Tahoma",Font.PLAIN,18);
		addsongsLabel.setFont(front);
		addsongsLabel.setForeground(Color.white);
		addsongsLabel.setBackground(Color.black);
		addsongsLabel.setOpaque(true);
		addsongsLabel.setVisible(true);
		
		addsongsCenterPanel.add(addsongsLabel);
		add(addsongsCenterPanel,BorderLayout.CENTER);
		
		
		setSize(400,200);
		
		
	}
}