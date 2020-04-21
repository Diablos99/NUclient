package NU_Sing_WX;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class NU_Button 
{	
	//NU_MainFrame Buttons 
	protected static JButton sing;
	protected static JButton search;
	protected static JButton localWorks; 
	protected static JButton orderedSongs;
	protected static JButton library;
	protected static JButton add;
	protected static JButton album;
	protected static JButton profile;
	protected static JButton singmarked;
	protected static JButton deletemarked;
	protected static JButton deletelocal;
	protected static JButton uploadlocal;
	protected static JButton add_song1;
	protected static JButton add_song2;
	protected static JButton history ;
	protected static JButton download ;

	// NU_MainFrame Labels
	protected static JLabel click=new JLabel("Click me!");
	protected static JLabel song1=new JLabel(new ImageIcon("icon_ls.jpg"));
	protected static JLabel song2=new JLabel(new ImageIcon("icon_sen.jpg"));
	//NU_SingFrame Buttons
	protected static JButton accom;
	protected static JButton vocal;
	protected static JButton pause;
	protected static JButton finish;
	protected static JButton quit;
	protected static JButton changeVolume;
	protected static JButton jump;
	protected static JButton three;
	protected static JButton two;
	protected static JButton one;
	protected static JButton logout;
	//NU_SingFrame Label
	protected static JLabel songTotalTime = new JLabel();
	protected static JLabel intr = new JLabel("Introduction:");
	protected static JLabel intro = new JLabel();
	protected static JLabel avatar = new JLabel();

	protected static Font front = new Font("Tahoma",Font.PLAIN,20);
	//NU_PlayWindow Buttons
	protected static JButton playWav;
	
	// Dialog Buttons 
	protected static JButton yes = new JButton("yes");
	protected static JButton no = new JButton("no");
	protected static JButton save = new JButton("save");
	protected static JButton ok = new JButton("OK");
	protected static JButton ok1 = new JButton("OK");
	protected static JButton mark = new JButton("OK");
	protected static JButton quitREC = new JButton("OK");
	
	


	protected static JLabel recommend=new JLabel("Recommended ");
	protected static JLabel song1_name=new JLabel("<html> Lovingstangers <br> Russian Red </html>");
	protected static JLabel song2_name=new JLabel("<html> Someone like you <br> Adele </html>");

	
	private Font font1=new Font (null,Font.BOLD+Font.ITALIC,20);
	private Font font2=new Font (null,Font.BOLD+Font.PLAIN,50);
	private Font font3=new Font (null,Font.PLAIN,18);



	NU_Button()
	{	
		//construct buttons
		sing = new JButton(new ImageIcon("sing.png"));
		search = new JButton(new ImageIcon("search.png"));
		localWorks = new JButton(" LocalWorks",new ImageIcon("radio.png"));
		orderedSongs = new JButton(" Bookmarked",new ImageIcon("check.png"));
		library = new JButton(" Songbook",new ImageIcon("library.png"));
		album = new JButton(" Album",new ImageIcon("album.png"));
		profile = new JButton(" Profile",new ImageIcon("profile.png"));
		add = new JButton(new ImageIcon("add.png"));
		add_song1 = new JButton("Add",new ImageIcon("add2.png"));
		add_song2 = new JButton("Add",new ImageIcon("add2.png"));
		singmarked = new JButton("Sing",new ImageIcon("sing.png"));
		deletemarked = new JButton("Delete", new ImageIcon("bin1.png"));
		playWav = new JButton("Play", new ImageIcon("playwav.png"));
		deletelocal = new JButton("Delete", new ImageIcon("bin.png"));
		uploadlocal = new JButton("Upload", new ImageIcon("upload.png"));
		history = new JButton("History", new ImageIcon("menu.png"));
		download = new JButton("Download", new ImageIcon("download.png"));



		
		//singFrame buttons
		accom = new JButton(new ImageIcon("record.png"));
		vocal = new JButton(new ImageIcon("vocal.png"));
		pause = new JButton(new ImageIcon("pause.png"));
		finish = new JButton(new ImageIcon("finish.png"));
		quit = new JButton(new ImageIcon("quit.png"));
		changeVolume = new JButton(new ImageIcon("audio.png"));
		jump = new JButton(new ImageIcon("jump.png"));
		three = new JButton(new ImageIcon("3.png"));
		two = new JButton(new ImageIcon("2.png"));
		one = new JButton(new ImageIcon("1.png"));
		
		
		//set buttons
		sing.setVisible(false);								
		sing.setOpaque(true);
		sing.setContentAreaFilled(false);
		sing.setBorder(null);					
		search.setOpaque(true);
		search.setContentAreaFilled(false);
		search.setBorder(null);
		
		add.setOpaque(true);
		add.setContentAreaFilled(false);
		add.setBorder(null);
		add.setVisible(false);
		
		singmarked.setOpaque(true);
		singmarked.setContentAreaFilled(false);
		singmarked.setBorder(null);
		singmarked.setVisible(false);
		singmarked.setFont(font1);
		singmarked.setForeground(Color.WHITE);
		
		deletemarked.setOpaque(true);
		deletemarked.setContentAreaFilled(false);
		deletemarked.setBorder(null);
		deletemarked.setVisible(false);
		deletemarked.setFont(font1);
		deletemarked.setForeground(Color.WHITE);
		
		playWav.setOpaque(true);
		playWav.setContentAreaFilled(false);
		playWav.setBorder(null);
		playWav.setVisible(false);
		playWav.setFont(font1);
		playWav.setForeground(Color.WHITE);
		
		deletelocal.setOpaque(true);
		deletelocal.setContentAreaFilled(false);
		deletelocal.setBorder(null);
		deletelocal.setVisible(false);
		deletelocal.setFont(font1);
		deletelocal.setForeground(Color.WHITE);
		
		uploadlocal.setOpaque(true);
		uploadlocal.setContentAreaFilled(false);
		uploadlocal.setBorder(null);
		uploadlocal.setVisible(false);
		uploadlocal.setFont(font1);
		uploadlocal.setForeground(Color.WHITE);
		
		history.setOpaque(true);
		history.setContentAreaFilled(false);
		history.setBorder(null);
		history.setFont(font1);
		history.setForeground(Color.WHITE);
		
		three.setOpaque(true);
		three.setContentAreaFilled(false);
		three.setBorder(null);
		
		two.setOpaque(true);
		two.setContentAreaFilled(false);
		two.setBorder(null);
		
		one.setOpaque(true);
		one.setContentAreaFilled(false);
		one.setBorder(null);
		
		library.setOpaque(true);
		library.setContentAreaFilled(false);
		library.setBorder(null);
		library.setFont(font1);
		library.setForeground(Color.WHITE);
		

		download.setOpaque(true);
		download.setContentAreaFilled(false);
		download.setBorder(null);
		download.setFont(font1);
		download.setForeground(Color.WHITE);
		
		orderedSongs.setOpaque(true);
		orderedSongs.setContentAreaFilled(false);
		orderedSongs.setBorder(null);
		orderedSongs.setFont(font1);
		orderedSongs.setForeground(Color.WHITE);
		

		
		localWorks.setOpaque(true);
		localWorks.setContentAreaFilled(false);
		localWorks.setBorder(null);
		localWorks.setFont(font1);
		localWorks.setForeground(Color.WHITE);

		album.setOpaque(true);
		album.setContentAreaFilled(false);
		album.setBorder(null);
		album.setFont(font1);
		album.setForeground(Color.WHITE);

		profile.setOpaque(true);
		profile.setContentAreaFilled(false);
		profile.setBorder(null);
		profile.setFont(font1);
		profile.setForeground(Color.WHITE);

		click.setFont(font1);
		click.setForeground(Color.WHITE);


		song1.setOpaque(true);
		song1.setVisible(false);
		song2.setOpaque(true);
		song2.setVisible(false);
		
		avatar.setOpaque(true);
		avatar.setVisible(false);
		
		intr.setFont(font1);
		intr.setForeground(Color.white);
		intr.setVisible(false);
		
		intro.setFont(font1);
		intro.setForeground(Color.white);
		intro.setVisible(false);
		
		recommend.setFont(font1);
		recommend.setForeground(Color.white);
		recommend.setVisible(false);
		
		song1_name.setFont(font3);
		song1_name.setForeground(Color.white);
		song1_name.setVisible(false);
		
		song2_name.setFont(font3);
		song2_name.setForeground(Color.white);
		song2_name.setVisible(false);
		
		add_song1.setOpaque(true);
		add_song1.setContentAreaFilled(false);
		add_song1.setBorder(null);
		add_song1.setFont(font1);
		add_song1.setForeground(Color.WHITE);
		add_song1.setVisible(false);
		
		add_song2.setOpaque(true);
		add_song2.setContentAreaFilled(false);
		add_song2.setBorder(null);
		add_song2.setFont(font1);
		add_song2.setForeground(Color.WHITE);
		add_song2.setVisible(false);
		
		accom.setOpaque(true);
		accom.setContentAreaFilled(false);
		accom.setBorder(null);
		
		vocal.setOpaque(true);
		vocal.setContentAreaFilled(false);
		vocal.setBorder(null);
		
		jump.setOpaque(true);
		jump.setContentAreaFilled(false);
		jump.setBorder(null);
		
		pause.setOpaque(true);
		pause.setContentAreaFilled(false);
		pause.setBorder(null);
		
		finish.setOpaque(true);
		finish.setContentAreaFilled(false);
		finish.setBorder(null);
		
		quit.setOpaque(true);
		quit.setContentAreaFilled(false);
		quit.setBorder(null);
		
		changeVolume.setOpaque(true);
		changeVolume.setContentAreaFilled(false);
		changeVolume.setBorder(null);
		
		yes.setBackground(Color.black);
		yes.setForeground(Color.red);
		yes.setOpaque(true);
		yes.setContentAreaFilled(false);
		yes.setBorder(null);
		
		no.setBackground(Color.black);
		no.setForeground(Color.green);
		no.setOpaque(true);
		no.setContentAreaFilled(false);
		no.setBorder(null);
		
		save.setBackground(Color.black);
		save.setForeground(Color.magenta);
		save.setOpaque(true);
		save.setContentAreaFilled(false);
		save.setBorder(null);
		
		ok.setBackground(Color.black);
		ok.setForeground(Color.red);
		ok.setOpaque(true);
		ok.setContentAreaFilled(false);
		ok.setBorder(null);
		
		ok1.setBackground(Color.black);
		ok1.setForeground(Color.red);
		ok1.setOpaque(true);
		ok1.setContentAreaFilled(false);
		ok1.setBorder(null);
		
		mark.setBackground(Color.black);
		mark.setForeground(Color.magenta);
		mark.setOpaque(true);
		mark.setContentAreaFilled(false);
		mark.setBorder(null);
		
		quitREC.setBackground(Color.black);
		quitREC.setForeground(Color.red);
		quitREC.setOpaque(true);
		quitREC.setContentAreaFilled(false);
		quitREC.setBorder(null);

		songTotalTime.setFont(front);
		songTotalTime.setForeground(Color.white);				
		
		
	}
	
}
