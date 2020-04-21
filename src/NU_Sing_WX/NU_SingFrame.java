package NU_Sing_WX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class NU_SingFrame extends JFrame
{
	private static final int DEFAULT_WIDTH = 900; 
	private static final int DEFAULT_HEIGHT = 900; 
	
	
	protected static JPanel south_panel = new JPanel();
	protected static JPanel north_panel = new JPanel();
	protected static JPanel east_panel = new JPanel();
	protected static JPanel west_panel = new JPanel();
	protected static JLabel recordLabel = new JLabel("REC");
	protected static JLabel displayLabel = new JLabel("NU_SING");
	protected static Font front = new Font("Tahoma",Font.BOLD,20);
	

			
	NU_SingFrame()
	{
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenHeight = screenSize.height; 
		int screenWidth = screenSize.width; 
		setLocation((screenWidth - DEFAULT_WIDTH) / 2, (screenHeight - DEFAULT_HEIGHT )/2); 
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("SING");
		
		NU_Button.three.setVisible(false);
		NU_Button.two.setVisible(false);
		NU_Button.one.setVisible(false);
				
		add(north_panel,BorderLayout.NORTH);		
		add(south_panel,BorderLayout.SOUTH);
		add(east_panel,BorderLayout.EAST);		
		add(west_panel,BorderLayout.WEST);
		
		displayLabel.setFont(front);
		recordLabel.setFont(front);
		recordLabel.setForeground(Color.white);
		displayLabel.setForeground(Color.white);
		north_panel.add(recordLabel);
		north_panel.add(displayLabel);

		
		

		NU_Slider.volume.setVisible(false);
		east_panel.add(NU_Slider.volume);
		south_panel.add(NU_Button.accom);
		south_panel.add(NU_Button.vocal);
		south_panel.add(NU_Button.pause);
		west_panel.add(NU_Button.jump);
		west_panel.add(NU_Button.three);
		west_panel.add(NU_Button.two);
		west_panel.add(NU_Button.one);


		
		
		
		
		
	}
}
