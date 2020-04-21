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
	public class NU_Dialog_addSongswrong extends JDialog
	{
		protected static JLabel addsongswrongLabel;
		protected static JPanel addsongsCenterPanel;
		
		public NU_Dialog_addSongswrong(JFrame owner, String labelContent)
		{
			super(owner,"NU_Sing",true);
			addsongsCenterPanel = new JPanel();
			addsongsCenterPanel.setBackground(Color.black);
			
			addsongswrongLabel = new JLabel(labelContent);
			Font front = new Font("Tahoma",Font.PLAIN,18);
			addsongswrongLabel.setFont(front);
			addsongswrongLabel.setForeground(Color.white);
			addsongswrongLabel.setBackground(Color.black);
			addsongswrongLabel.setOpaque(true);
			addsongswrongLabel.setVisible(true);
			
			addsongsCenterPanel.add(addsongswrongLabel);
			add(addsongsCenterPanel,BorderLayout.CENTER);
			
			NU_Button.ok1.setFont(front);
			JPanel addPanel = new JPanel();
			addPanel.setBackground(Color.black);													
			addPanel.add(NU_Button.ok1);
			add(addPanel,BorderLayout.SOUTH);
			setSize(450,200);
			
			
		}
	}


