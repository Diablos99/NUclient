package NU_Sing_WX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;




public class NU_MainFrame extends JPanel 
{	
	//

	static String zip;
	//playWindow
	protected static String workWavPath;
	static int audioLength; 
	static int audioPosition = 0;
	static Clip clip;
	static JSlider waveProgress = new JSlider(0,audioLength, 0);
	static JLabel timeWav = new JLabel("0");
	public static void Skip(int Position) { // Called when user drags the slider
		if (Position < 0 || Position > audioLength)
			return;
		audioPosition = Position;

			clip.setMicrosecondPosition(Position * 1000);
		waveProgress.setValue(Position); // in case skip( ) is called from outside
	}
	static void Tick() {
		
		audioPosition = (int) (clip.getMicrosecondPosition() / 1000);
		waveProgress.setValue(audioPosition);
	
	}
	//mark
	protected static int value;
	protected static int value_pass;
	protected static CutMusic cutM =  new CutMusic();
	protected static String origin;
	protected static Convert convert = new Convert();
	protected static Compare compare = new Compare();
	protected static String workPath;
	protected static String workName;
	protected static int value_waveANDbuffer;
	protected static int jumpTime;
	protected static boolean jumpFlag = true;
	protected static int cutTimeWork;
	//class objects
	protected static NU_MainFrame nu;
	protected static MediaPlayer mediaPlayer;
	protected static MediaPlayer accomPlayer;
	protected static int songLength; 
	protected static Media media;
	protected static NU_SingFrame singFrame;
	protected static NU_RecordSound recordSound;
	protected static LyricDisplayer center_panel = new LyricDisplayer();
	protected static NU_Dialog finishDialog;
	protected static NU_Dialog_addSongs addSongsDialog;
	protected static NU_Dialog_addSongswrong addSongswrongDialog;

	protected static NU_Dialog_quitREC quitDialog;
	protected static NU_Save save = new NU_Save();
	protected static JFrame mainFrame;
	// try connecting MYSQL
	protected static Connection connect; 
	//NU_MainFrame basic frame information
	private static final int DEFAULT_WIDTH = 1240; 
	private static final int DEFAULT_HEIGHT = 826; 
	protected static int screenHeight;
	protected static int screenWidth; 
	private Image image = (Image) new ImageIcon("MainFrame.jpg").getImage();
	//ProgressSlider information
	protected static JSlider progress = new JSlider();
	protected static JLabel time = new JLabel("0");
	protected static javax.swing.Timer timer;
	
	protected static int songPosition = 0;	
	protected static String formatTime;
	protected  String formatTime(int songPosition) 
    { 	      	         	 	
		int durationMinutes = songPosition / 60; 
        int durationSeconds = songPosition - durationMinutes * 60; 
        return formatTime =String.format("%02d:%02d",durationMinutes,durationSeconds);               
    }   
	protected  String formatStartTime(long firstTime) 
    { 	     
		int durationSeconds = (int)firstTime/1000; 
		System.out.println(durationSeconds);
		int durationMSeconds = (int)(firstTime - durationSeconds*1000);
		System.out.println(durationMSeconds);
        return formatTime =String.format("%02d/%02d%/02d%/02d",durationMSeconds,durationSeconds,0,0);               
    }  
	public static void tick() 
	{				
			songPosition = (int) mediaPlayer.getCurrentTime().toMillis()/1000;			
			progress.setValue(songPosition);					
	}

	// Threads
	protected static boolean suspended=false;
	protected static boolean flag = true;
	protected static boolean flag2 = true;
	protected static boolean flag3 = true;
	protected static boolean flag4 = true;
	protected static boolean flag6 = true;
	protected static boolean flag8 = true;
	protected static boolean flagOK = true;
	protected static Thread drawLyricThread = null;
	protected static Thread west;
	protected static Thread recordThread;
	// Thread class
	static  class drawLyric extends Thread
	{			
		public void run()
		{				
				center_panel.prepareDisplay(LyricReader.statements);
		        center_panel.setBackGroundImagePath("/playAndrecord.png");    	    	    		   	    	    		
		        center_panel.timeinterval();
		        
			    while(true) 
			    {
			    	if(Thread.currentThread().isInterrupted()) 
		    		{
		    			System.out.println("The thread is interrupted!");
		    			break;
		    		}
		    		else
		    		{
		        	try {
			                 Thread.sleep(LyricDisplayer.timeInterval);
			             } catch (InterruptedException e) {
			            	 
			                 e.printStackTrace();
			                 break;
			             }
		        	 center_panel.timeinterval();   		    	    		        	 
		        	 center_panel.displayLyric(LyricDisplayer.index);    				    		    				    		 
		    		 LyricDisplayer.index++;	    		 
		    		 synchronized (NU_MainFrame.drawLyricThread) {
							try {
									if(NU_MainFrame.suspended) {
										NU_MainFrame.drawLyricThread.wait();
									}
							}catch(InterruptedException e) {
								System.out.println("Current thread is interrupted.");
								break;
							}
			    		   		 
			    		
								   
			    		 }
			    		}
			    		 

			        }	
		}		
	}
	// Timer class to delay play
	static class task extends TimerTask
	{
		public void run()
		{
			accomPlayer.play();
			mediaPlayer.play();
			accomPlayer.setMute(true);
		}
	}
	//set background of NU_mainframe (panel)
	protected void paintComponent(Graphics g) 
	{  
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);  
	}
	// set volume
	public void setVolume(double a_volume)
	{
		NU_Slider.a_volume = a_volume;
		mediaPlayer.setVolume(a_volume);
		accomPlayer.setVolume(a_volume);
	}
	public  double getVolume()
	{
		return NU_Slider.a_volume;
	}
	// Create Works
	private static void testRenameFile(String rename) 
	   {
	        workPath = "D://NU_Garbage//NUS.wav";
	        try {
	            File src = new File(workPath);
	            workPath = "D://NU_Garbage//" + rename + ".wav";
	            File des = new File(workPath);
	            if (des.exists()) {
	                boolean res = des.delete();
	                if (!res) {
	                    System.out.println("Failed to delete file");
	                }
	            }
	            if (!src.renameTo(des)) {
	                System.out.println("Failed to renameTo file");
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }

	// Constructor
	NU_MainFrame()
	{
		add(NU_Button.sing );
		add(NU_Button.search);
		add(NU_Button.localWorks);
		add(NU_Button.orderedSongs);
		add(NU_Button.library);
		add(NU_Button.click);
		add(NU_Button.song1);
		add(NU_Button.song2);
		add(NU_Button.intro);
		add(NU_Button.intr);
		add(NU_Button.song1_name);
		add(NU_Button.song2_name);
		add(NU_Button.recommend);
		add(NU_Button.album);
		add(NU_Button.profile);
		add(NU_Button.add);	
		add(NU_Button.add_song1);
		add(NU_Button.add_song2);
		add(NU_Button.playWav);	
		add(NU_Button.singmarked);
		add(NU_Button.deletemarked);
		add(NU_Button.deletelocal);
		add(NU_Button.uploadlocal);
		add(NU_Button.history);
		add(NU_Button.download);
		add(NU_Button.avatar);


	}
	public static void main(String[] args) 
	{	
		//try connecting MYSQL
		try{
		   Class.forName("com.mysql.cj.jdbc.Driver");
		   System.out.println("Success loading Mysql Driver!");
		  }catch (Exception e)
		  {
		   System.out.println("Error loading Mysql Driver");
		   e.printStackTrace();
		  }
		  try{
		   connect = DriverManager.getConnection("jdbc:mysql://172.20.10.2:3306/nusing?serverTimezone=UTC","root","Wx0912...");
		   System.out.println("Success connect Mysql server!");
		  }catch (Exception e)
		  {
			  System.out.println("get data error!");
		   e.printStackTrace();
		  }
		  login loginWindow= new login();
			loginWindow.setLocationRelativeTo(mainFrame);
			new GroupFileView();
		  //EventQueue
		  EventQueue.invokeLater (()-> 
			{	
				//create main object
				
				new NU_Button();
				nu = new NU_MainFrame();
				NU_List_songlibrary.getFile();
				new NU_List_songlibrary();
				new NU_List_orderedSongs();
				new NU_List_searchHistory();
				NU_Save.loadUserIDSettings();
				save.loadHistory();
				String tname = login.username.getText();
				System.out.println("tname"+tname);
				//create NU_MainFrame
				Toolkit kit = Toolkit.getDefaultToolkit(); 
				Dimension screenSize = kit.getScreenSize(); 
				screenHeight = screenSize.height; 
				screenWidth = screenSize.width;					
				mainFrame = new JFrame();
				mainFrame.setLocation((screenWidth - DEFAULT_WIDTH) / 2, (screenHeight - DEFAULT_HEIGHT )/2); 
				mainFrame.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT); 
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setTitle("NU_Sing");
				mainFrame.setContentPane(nu);
				mainFrame.setLayout(null);	
				mainFrame.setVisible(false);
				
				//Buttons and Labels Position
				NU_Button.sing.setBounds(1050,50,50,50);
				NU_Button.search.setBounds(1050,50,50,50);
				NU_Button.localWorks.setBounds(520,55,200,50);
				NU_Button.orderedSongs.setBounds(290,55,200,50);
				NU_Button.library.setBounds(50,55,250,50);
				NU_Button.add.setBounds(780,50,50,50);
				NU_Button.click.setBounds(1100,50,100,50);
				NU_Button.song1.setBounds(250,570,55,55);
				NU_Button.song2.setBounds(650,570,55,55);
				NU_Button.song1_name.setBounds(320,570,200,50);
				NU_Button.song2_name.setBounds(720,570,200,50);
				NU_Button.add_song1.setBounds(430,570,150,50);
				NU_Button.add_song2.setBounds(830,570,200,50);
				NU_Button.recommend.setBounds(380,500,400,30);
				NU_Button.album.setBounds(20,570,125,50);
				NU_Button.profile.setBounds(20,670,125,50);
				NU_Button.singmarked.setBounds(400, 630, 200, 50);
				NU_Button.deletemarked.setBounds(700, 630, 200, 50);
				NU_Button.playWav.setBounds(300, 630, 200, 50);
				NU_Button.deletelocal.setBounds(800, 630, 200, 50);
				NU_Button.uploadlocal.setBounds(550, 630, 200, 50);
				NU_Button.history.setBounds(1000,670,175,50);
				NU_Button.download.setBounds(1010,570,175,50);
				NU_Button.intro.setBounds(600,630,200,50);
				NU_Button.intr.setBounds(300,630,200,50);
				NU_Button.avatar.setBounds(300,500,100,100);



				NU_List_orderedSongs.orderedsongsList.setOpaque(false);
				nu.add(NU_List_orderedSongs.orderedsongsList);
				//create sliders
				
				NU_Slider.volume = new JSlider(SwingConstants.VERTICAL,0,100,50);	
				
				//Events of Buttons
					//NU_MainFrame Events
				
				NU_Button.deletemarked.addActionListener(event ->
				{
					int selectedIndex = NU_List_orderedSongs.orderedsongsList.getSelectedIndex();
					if(selectedIndex !=-1) {
						NU_List_orderedSongs.model2.remove(selectedIndex);
					}
				});
				
				NU_Button.playWav.addActionListener(event ->  				
				{	
					int selectedIndex = NU_List_songlibrary.songnamesList.getSelectedIndex();
					if (selectedIndex != -1) 
					{
						String selectWav = NU_List_songlibrary.songnamesList.getSelectedValue();
						System.out.println(selectWav);
						String sql_wav = "select work_path from worklibrary where work_name = '" + selectWav+"'";
						try {
							Statement stmt = connect.createStatement();
							ResultSet rst = stmt.executeQuery(sql_wav );
							workWavPath = "";
							if(rst.next())
							{
								workWavPath = rst.getString("work_path");
								if(workWavPath == null)
								{
									workWavPath = "The work does not exist. ";
								}	
								System.out.println(workWavPath);
									WaveformDemo.loadAudio(workWavPath);
									
							}}
							catch (Exception e)
							{e.printStackTrace();}	
						
					}		
					
							Thread wavePlay = new Thread(new Runnable() {
							        public void run() 
							        { 					        	
					    				new WaveformDemo();						    									    				
							        }
							    });	
					        	wavePlay.start();
					        	waveProgress.setMinimum(0);
								System.out.println(audioLength);
								waveProgress.setMaximum(audioLength);
								waveProgress.setValue(0);				
								WaveformDemo.playbackTools.add(waveProgress);
								waveProgress.setVisible(false);
								WaveformDemo.playbackTools.add(timeWav);
				});
				NU_Button.deletelocal.addActionListener(event ->
				{
					int selectedIndex = NU_List_songlibrary.songnamesList.getSelectedIndex();
					if(selectedIndex !=-1) {
						NU_List_songlibrary.model.remove(selectedIndex);
					}
				});
		//choose frame
				
				NU_Button.library.addActionListener(event -> 
				{	

					NU_Button.song1.setVisible(true);
					NU_Button.song2.setVisible(true);
					NU_Button.recommend.setVisible(true);
					NU_Button.song1_name.setVisible(true);
					NU_Button.song2_name.setVisible(true);
					NU_Button.add_song1.setVisible(true);
					NU_Button.add_song2.setVisible(true);
					NU_Button.singmarked.setVisible(false);
					NU_Button.deletemarked.setVisible(false);
					NU_Button.playWav.setVisible(false);
					NU_Button.deletelocal.setVisible(false);	
					NU_Button.uploadlocal.setVisible(false);	
					NU_Button.avatar.setVisible(false);
					NU_Button.intro.setVisible(false);
					NU_Button.intr.setVisible(false);
					NU_List_songlibrary.songnamesList.setVisible(false);
					NU_List_orderedSongs.orderedsongsList.setVisible(false);

				});
				
				NU_Button.orderedSongs.addActionListener(event -> 
				{	
					NU_Button.song1.setVisible(false);
					NU_Button.song2.setVisible(false);
					NU_Button.song1_name.setVisible(false);
					NU_Button.song2_name.setVisible(false);
					NU_Button.add_song1.setVisible(false);
					NU_Button.add_song2.setVisible(false);
					NU_Button.recommend.setVisible(false);
					NU_Button.singmarked.setVisible(true);
					NU_Button.deletemarked.setVisible(true);
					NU_Button.playWav.setVisible(false);
					NU_Button.deletelocal.setVisible(false);
					NU_Button.uploadlocal.setVisible(false);
					NU_Button.avatar.setVisible(false);
					NU_Button.intro.setVisible(false);
					NU_Button.intr.setVisible(false);
					

					
					if(flag2 == true)
					{
						flag2 = false;
						
						NU_List_orderedSongs.orderedsongsList.setBounds(220, 200, 800, 550);
						NU_List_orderedSongs.orderedsongsList.setVisible(true);
						NU_List_songlibrary.songnamesList.setVisible(false);

					}
					else if(flag2 == false)
					{
						NU_List_orderedSongs.orderedsongsList.setVisible(false);
						flag2 = true;
					}


				});
				
				NU_Button.localWorks.addActionListener(event -> 
				{	

					NU_Button.song1.setVisible(false);
					NU_Button.song2.setVisible(false);
					NU_Button.song1_name.setVisible(false);
					NU_Button.song2_name.setVisible(false);
					NU_Button.add_song1.setVisible(false);
					NU_Button.add_song2.setVisible(false);
					NU_Button.recommend.setVisible(false);
					NU_Button.singmarked.setVisible(false);
					NU_Button.deletemarked.setVisible(false);
					NU_Button.playWav.setVisible(true);
					NU_Button.deletelocal.setVisible(true);		
					NU_Button.uploadlocal.setVisible(true);		
					NU_Button.avatar.setVisible(false);
					NU_Button.intro.setVisible(false);
					NU_Button.intr.setVisible(false);
					NU_List_orderedSongs.orderedsongsList.setVisible(false);
					
					
					if(flag2 == true)						
					{	
						flag2 = false;
						NU_List_songlibrary.getFile();
						NU_List_songlibrary.songnamesList = new JList<>(NU_List_songlibrary.model);
						NU_List_songlibrary.songnamesList.setVisible(true);
						nu.add(NU_List_songlibrary.songnamesList);
						NU_List_songlibrary.songnamesList.setBounds(220, 200, 800, 550);
						NU_List_songlibrary.songnamesList.setOpaque(false);
						
					}
					else if(flag2 == false)
					{
						NU_List_songlibrary.songnamesList.setVisible(false);
						flag2 = true;
					}
					
				});
				
				
				NU_Button.add.addActionListener(event ->  				
				{	NU_Text.userInput = NU_Text.searchSongs.getText();
				String sql_song = "select song_name from songlibrary where song_name= '"+NU_Text.userInput+"'";
				try {
					
					Statement stmt = connect.createStatement();							
					
					ResultSet rst = stmt.executeQuery(sql_song );
					String name="";
					if(rst.next()) {
					name = rst.getString("song_name");
					NU_List_orderedSongs.model2.addElement(NU_Text.userInput);
					addSongsDialog = new NU_Dialog_addSongs(singFrame ,"The song has been successfully added to BookMraked");
					addSongsDialog.setLocationRelativeTo(singFrame);
					addSongsDialog.setVisible(true);							
					}else {
						addSongswrongDialog = new NU_Dialog_addSongswrong(singFrame ,"This song does not exist.");
						addSongswrongDialog.setLocationRelativeTo(singFrame);
						addSongswrongDialog.setVisible(true);	
						
					}
				}												
				catch (SQLException e) {
					e.printStackTrace();
				}
					
				
					});
				NU_Button.add_song1.addActionListener(event ->  				
				{	
					String sql_song1 = "select song_name from songlibrary where song_id= 1";
					try {
						Statement stmt=connect.createStatement();
						ResultSet rs=stmt.executeQuery(sql_song1);
						String name="";
						while(rs.next()) {
							name=rs.getString("song_name");
							NU_List_orderedSongs.model2.addElement(name);
							addSongsDialog = new NU_Dialog_addSongs(singFrame ,"The song has been successfully added to BookMraked");
							addSongsDialog.setLocationRelativeTo(singFrame);
							addSongsDialog.setVisible(true);	
						}
					}catch (SQLException e) {
						e.printStackTrace();
					}
											

				});
				NU_Button.add_song2.addActionListener(event ->  				
				{	
					String sql_song2 = "select song_name from songlibrary where song_id= 2";
					try {
						Statement stmt=connect.createStatement();
						ResultSet rs=stmt.executeQuery(sql_song2);
						String name="";
						while(rs.next()) {
							name=rs.getString("song_name");
							NU_List_orderedSongs.model2.addElement(name);
							addSongsDialog = new NU_Dialog_addSongs(singFrame ,"The song has been successfully added to BookMraked");
							addSongsDialog.setLocationRelativeTo(singFrame);
							addSongsDialog.setVisible(true);	
						}
					}catch (SQLException e) {
						e.printStackTrace();
					}					
				});
				
				
				NU_Button.search.addActionListener(event ->  				
				{	
					NU_Text.searchSongs = new JTextField("search a song or singer", 20);
					nu.add(NU_Text.searchSongs);
					NU_Text.searchSongs.setBounds(845,60,200,35);
					NU_Button.search.setVisible(false);
					NU_Button.sing.setVisible(true);	
					NU_Button.add.setVisible(true);
				});
				NU_Button.sing.addActionListener(event ->  				
				{	
					flag8 = true;
					jumpFlag = true;
					singFrame = new NU_SingFrame();	
					NU_Text.userInput = NU_Text.searchSongs.getText();						
					NU_List_searchHistory.model3.addElement(NU_Text.userInput);
					NU_List_searchHistory.list.add(NU_Text.userInput);
					String sql_song = "select song_name from songlibrary where song_name= '"+NU_Text.userInput+"'";
					try {
						
						Statement stmt = connect.createStatement();							
						
						ResultSet rst = stmt.executeQuery(sql_song );
						String name="";
						if(rst.next()) {
						name = rst.getString("song_name");
				
						singFrame.add(center_panel,BorderLayout.CENTER);					
					
						recordSound = new NU_RecordSound();
					
						save.loadNUCSettings();
					try 
					{
					NU_RecordSound.NUF = new File("D://NU_Garbage","NUS.wav");
					NU_RecordSound.NUF.createNewFile();
					}catch (IOException e){
						e.printStackTrace();};										
					//start recording
						recordThread = new Thread(new Runnable() {
					        public void run() { 
					        	recordSound.start();
					        }
					    });	
					//SQL Search
						//1
						String sql_lyric = "select song_lyric from songlibrary where song_name = '" + NU_Text.userInput+"'";
						try {
							Statement stmt1 = connect.createStatement();
							ResultSet rst1 = stmt1.executeQuery(sql_lyric );
							String lyric = "";
							if(rst1.next())
							{
								lyric = rst1.getString("song_lyric");
								System.out.println(lyric);
								if(lyric == null)
								{
									lyric = "The song does not have lyrics. ";
								}
								try {
									new LyricReader(lyric);					
								} catch (IOException e) 
								{e.printStackTrace();} 
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						//2
						String sql_origin = "select song_origin from songlibrary where song_name = '" + NU_Text.userInput+"'";
						try {
							Statement stmt1 = connect.createStatement();
							ResultSet rst1 = stmt1.executeQuery(sql_origin );
							origin = "";
							if(rst1.next())
							{
								origin = rst1.getString("song_origin");
								System.out.println(origin);
								if(origin == null)
								{
									origin = "The song does not have original singing. ";
								}	
									
									JFXPanel fxPanel = new JFXPanel();
									media = new Media(new File(origin).toURI().toString());		                  
									mediaPlayer = new MediaPlayer(media);  

							}}
							catch (Exception e)
							{e.printStackTrace();}	
						//3
						String sql_accom = "select song_accom from songlibrary where song_name = '" + NU_Text.userInput+"'";
						try {
							Statement stmt1 = connect.createStatement();
							ResultSet rst1 = stmt1.executeQuery(sql_accom );
							String accomm = "";
							if(rst1.next())
							{
								accomm = rst1.getString("song_accom");
								if(accomm == null)
								{
									accomm = "The song does not have an accompany. ";
								}	
									
									JFXPanel fxPanel = new JFXPanel();
									Media media = new Media(new File(accomm).toURI().toString());		                  
									accomPlayer = new MediaPlayer(media);   
							}}
							catch (Exception e)
							{e.printStackTrace();}						
						//Threads
						drawLyricThread =new Thread(new drawLyric());
						drawLyricThread.start();						
						recordThread.start();	
						//add progress slider
						mediaPlayer.setOnReady(new Runnable() 
						{
					        public void run() 
					        {
					        	songLength = (int) media.getDuration().toSeconds();
								nu.formatTime(songLength);
								NU_Button.songTotalTime.setText(formatTime);
								progress.setOrientation(SwingConstants.HORIZONTAL);
								progress.setMinimum(0);
								
								progress.setMaximum(songLength);
								progress.setValue(0);
								
								progress.setUI(new javax.swing.plaf.metal.MetalSliderUI(){
							    	@Override
							    	public void paintThumb(Graphics g) {
							    		Graphics2D g2d = (Graphics2D) g;
							    		g2d.setColor(Color.magenta);
							    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							    		g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width,
						                        thumbRect.height); 
							    	}
							    	public void paintTrack(Graphics g) {
							    		int cy,cw;
							    		Rectangle trackBounds = trackRect;
							    		if (slider.getOrientation() == JSlider.HORIZONTAL) {
							    			Graphics2D g2 = (Graphics2D) g;
							    			g2.setPaint(Color.white);
							    			cy = (trackBounds.height/2) - 2;
							    			cw = trackBounds.width;					    			
							    			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							    					RenderingHints.VALUE_ANTIALIAS_ON);
							    			g2.translate(trackBounds.x, trackBounds.y + cy);
							    			g2.fillRect(0, -cy + 5, cw, cy);					    			
							    			int trackLeft = 0;
							    			int trackRight = 0;
							    			trackRight = trackRect.width - 1;				 
							    			int middleOfThumb = 0;
							    			int fillLeft = 0;
							    			int fillRight = 0;
							    			middleOfThumb = thumbRect.x + (thumbRect.width / 2);
							    			middleOfThumb -= trackRect.x;					    			
							    			if (!drawInverted()) {
							    				fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
							    				fillRight = middleOfThumb;
							    				} else {
							    				fillLeft = middleOfThumb;
							    				fillRight = !slider.isEnabled() ? trackRight - 1
							    				: trackRight - 2;
							    				}
							    			g2.setPaint(new GradientPaint(0, 0, Color.magenta, cw, 0,
							    					Color.blue, true));
							    			g2.fillRect(0, -cy + 5, fillRight - fillLeft, cy);					    					
							    			g2.setPaint(slider.getBackground());
							    			g2.fillRect(10, 10, cw, 5);			 
							    			g2.setPaint(Color.WHITE);
							    			g2.drawLine(0, cy, cw - 1, cy);				 
							    			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							    			RenderingHints.VALUE_ANTIALIAS_OFF);
							    			g2.translate(-trackBounds.x, -(trackBounds.y + cy));   					
							    		}
							    		else {
						    				super.paintTrack(g);
						    				}
							    	}
							    
								});
								progress.setPreferredSize(new Dimension(400, 10));
					        	
					        }
						});																		
						timer = new javax.swing.Timer(100, new ActionListener() 
						{
							public void actionPerformed(ActionEvent e) {
								tick();
							}
						});
						timer.start();
						time.setFont(NU_Button.front);
						time.setForeground(Color.white);
						NU_SingFrame.south_panel.add(time);
						NU_SingFrame.south_panel.add(progress);
						NU_SingFrame.south_panel.add(NU_Button.songTotalTime);
						NU_SingFrame.south_panel.add(NU_Button.changeVolume);
						NU_SingFrame.south_panel.add(NU_Button.finish);
						NU_SingFrame.south_panel.add(NU_Button.quit);
					//close jump button						
						 west = new Thread(new Runnable() 
						  {
		        			public void run() 
		        			{ 					        	
		        				try {
					                 Thread.sleep(LyricReader.statements.get(4).getTime()+center_panel.timeintervalCut()-3000);
					             } catch (InterruptedException e) {						            	 
					                 e.printStackTrace();
					             }
		        				if(flag8 == true)
		        				{
			        				NU_Button.jump.setVisible(false);
			        				NU_Button.three.setVisible(true);
			        				try {
						                 Thread.sleep(2000);
						             } catch (InterruptedException e) {					            	 
						                 e.printStackTrace();
						             }
			        				NU_Button.three.setVisible(false);
			        				NU_Button.two.setVisible(true);
			        				try {
						                 Thread.sleep(2000);
						             } catch (InterruptedException e) {					            	 
						                 e.printStackTrace();
						             }
			        				NU_Button.two.setVisible(false);
			        				NU_Button.one.setVisible(true);
			        				try {
						                 Thread.sleep(2000);
						             } catch (InterruptedException e) {					            	 
						                 e.printStackTrace();
						             }
			        				NU_Button.one.setVisible(false);
			        			}
		        				else if (flag8 == false)
		        				{
		        					System.out.println("JUMP!");
		        				}
		        			}
						  });	
						 west.start();
				 //construct Timer Delay
						Timer timer=new Timer();
						task delayPlay=new task();
						timer.schedule(delayPlay,800+LyricDisplayer.timeInterval);						
						//go back to mainframe original status
						NU_Button.search.setVisible(true);
						NU_Button.sing.setVisible(false);
						NU_Text.searchSongs.setVisible(false);
						//REC 
						new Timer().schedule(new TimerTask() {
				            @Override
				            public void run() 
				            {
				            	if(flag == true)
				            	{
				            		NU_SingFrame.recordLabel.setVisible(false);
				            		flag = false;
				            	}
				            	else if(flag == false)
				            	{
				            		NU_SingFrame.recordLabel.setVisible(true);
				            		flag = true;
				            	}
				                
				            }
				        },1000,2000);
						NU_SingFrame.south_panel.setBackground(Color.BLACK);
						NU_SingFrame.north_panel.setBackground(Color.BLACK);
						NU_SingFrame.east_panel.setBackground(Color.BLACK);
						NU_SingFrame.west_panel.setBackground(Color.BLACK);
						NU_Button.add.setVisible(false);
						save.saveHistory();}
						
						else {
							singFrame.setVisible(false);
							addSongswrongDialog = new NU_Dialog_addSongswrong(singFrame ,"This song does not exist.");
							addSongswrongDialog.setLocationRelativeTo(singFrame);
							addSongswrongDialog.setVisible(true);	
							
						}
					}												
					catch (SQLException e) {
						e.printStackTrace();
					}
						
				});
				
				
				
				 		
		//NU_SingFrame Events
				NU_Button.vocal.addActionListener(event ->  				
				{	
					mediaPlayer.setMute(false);
					accomPlayer.setMute(true);
				}); 
				NU_Button.accom.addActionListener(event ->  				
				{	
					mediaPlayer.setMute(true);
					accomPlayer.setMute(false);
				}); 
				NU_Button.pause.addActionListener(event ->  				
				{	
					if(flag3 ==true )
					{	
						NU_Button.pause.setIcon(new ImageIcon("play.png"));
						suspended = true;
						mediaPlayer.pause();
						accomPlayer.pause();
						flag3=false;
					}
					else if (flag3 ==false)
					{
						NU_Button.pause.setIcon(new ImageIcon("pause.png"));
						synchronized(drawLyricThread) {               
							drawLyricThread.notify();                   
		                }
						suspended = false;	
						mediaPlayer.play();
						accomPlayer.play();
						flag3=true;
					}					
				}); 
				NU_Button.jump.addActionListener(event -> 
				{	
					long startTime = LyricReader.statements.get(4).getTime();	
					jumpTime = (int) ((startTime -mediaPlayer.getCurrentTime().toMillis())/1000);
					System.out.println(jumpTime);
					mediaPlayer.seek(new Duration(startTime -mediaPlayer.getCurrentTime().toMillis()));
					LyricDisplayer.index = 4;
					NU_Button.jump.setVisible(false);
					west.interrupt();
					flag8 =false;		
					jumpFlag = false;
				});
				NU_Button.quit.addActionListener(event -> 
				{	
					quitDialog = new NU_Dialog_quitREC(singFrame ,"Are you sure you wanna give up recording?");
					quitDialog.setLocationRelativeTo(singFrame);
					quitDialog.setVisible(true);
				    
				    
				    
				});
				NU_Button.finish.addActionListener(event ->  				
				{								
					finishDialog = new NU_Dialog(singFrame ," Are you sure you wanna finish recording?", "Yes" , "   ContinueRecording");
					finishDialog.setLocationRelativeTo(singFrame);
					finishDialog.setVisible(true);
				});
					//Sliders Events
				NU_Slider.volume.addChangeListener(event ->
				{
				
				{	
					double setting = 0.0;
					if(event.getSource() == NU_Slider.volume)
					{
						setting = (((JSlider)event.getSource()).getValue())/100d;
						nu.setVolume(setting);
					}
				}
				});
				progress.addChangeListener(event ->
				{
				
				{	
					//some mistakes (if else)
					value = progress.getValue();
					nu.formatTime(value);
					time.setText(formatTime);						
						
				}
				});
					//WaveformDemo Events
				WaveformDemo.playWav.addActionListener(event ->
  				{	
  					waveProgress.setVisible(true);
					clip.start();
	            	timer = new javax.swing.Timer(100, new ActionListener() {
	          			public void actionPerformed(ActionEvent e) {
	          				Tick();
	          				;
	          			}
	          		});
					timer.start();
	            	 waveProgress.addChangeListener(new ChangeListener() {
	          			public void stateChanged(ChangeEvent e) {
	          				int value = waveProgress.getValue();
	          				// Update the time label

	          					timeWav.setText(value / 1000 + "." + (value % 1000) / 100);
	          				// If we're not already there, skip there.
	          				if (value != audioPosition)
	          					Skip(value);
	          			}
	          		});
				});
					//Dialog Events
				NU_Button.yes.addActionListener(event ->
				{	
					recordSound.finish();
				    drawLyricThread.interrupt();
				    accomPlayer.stop();
				    mediaPlayer.stop();
				    NU_Dialog.dialogLabel.setVisible(false);
				    NU_Dialog.saveLabel.setVisible(true);
				    value_pass = value;
				    System.out.println(value);
					CutMusic.cut1(origin,value);					
				    NU_Button.yes.setVisible(false);
				    NU_Button.no.setVisible(false);				    
				    NU_Button.save.setVisible(true);
				    NU_Text.createWorkName.setVisible(true);
	    		    NU_Dialog.saveLabel.setVisible(true);
	    		    save.saveSettings();				    
				});
				NU_Button.no.addActionListener(event -> 
				{	
					finishDialog.setVisible(false);
					 
				});
				NU_Button.save.addActionListener(event -> 
				{	
					 int NUC_index_save = CutMusic.NUC_index-1;					 
					 NU_Text.workNameInput = NU_Text.createWorkName.getText();
					 workName = NU_Text.workNameInput;
					 nu.testRenameFile(workName);
						try 
						{
						CutWork.NUCW = new File("D://NU_Works",workName+".wav");
 						CutWork.NUCW.createNewFile();
						}catch (IOException e){
							e.printStackTrace();};
							cutTimeWork = center_panel.timeintervalCut()+1;
							System.out.println("vs"+value_pass);
							System.out.println("jt"+jumpTime);
					 if(jumpFlag == true)
					 {
					 value_waveANDbuffer = value_pass + center_panel.timeintervalCut();
					 //
	
					 
					 }
					 else if(jumpFlag == false)
					 {
						 value_waveANDbuffer = value_pass + center_panel.timeintervalCut() - jumpTime;
						 System.out.println("buffer"+value_waveANDbuffer);
					 }
					 CutWork.cut(workPath,"D://NU_Works//"+workName+".wav",cutTimeWork,value_waveANDbuffer);
					 NU_Text.createWorkName.setVisible(false);
					 NU_Button.save.setVisible(false);
					 NU_Button.mark.setVisible(true);
					 NU_Dialog.saveLabel.setVisible(false);
					 String cutPath = "D://NU_Garbage//CUT"+ NUC_index_save+".mp3";
					 convert.doWork(cutPath);
					 String cutPathwav = cutPath + ".wav";
					 String cutWorkPAthwav = "D://NU_Works//"+workName+".wav";
					 compare.match(cutWorkPAthwav,cutPathwav);
					 NU_Dialog.markLabel.setText("    Your work score is : " + Compare.result);
					 NU_Button.mark.setVisible(true);
					 NU_Dialog.markLabel.setVisible(true);
					 save.saveNUCSettings();
					 NU_Button.jump.setVisible(true);
					 flag8 = false;
					 west.interrupt();
					 Connection conn;
						PreparedStatement stmt;
					 String sql = "insert into worklibrary values (?,?)";
					 String driver = "com.mysql.jdbc.Driver";
						String url = "jdbc:mysql://172.20.10.2:3306/nusing?serverTimezone=UTC";
						String user = "root";
						String password = "Wx0912...";																		
						try {
							Class.forName(driver);
							conn = DriverManager.getConnection(url, user, password);
							stmt = (PreparedStatement) conn.prepareStatement(sql);
							stmt.setString(1, workName+".wav");
							stmt.setString(2, "D://NU_Works//"+workName+".wav");
							stmt.executeUpdate();							
						} catch (ClassNotFoundException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						NU_List_songlibrary.model.addElement(workName);
					 
				});
				NU_Button.ok.addActionListener(event -> 
				{	
					addSongsDialog.setVisible(false);					 
				});
				NU_Button.ok1.addActionListener(event -> 
				{	
					addSongswrongDialog.setVisible(false);					 
				});
				NU_Button.mark.addActionListener(event -> 
				{	
					 singFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					 finishDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					 singFrame.setVisible(false);
					 finishDialog.setVisible(false);
					 NU_Button.yes.setVisible(true);
					 NU_Button.no.setVisible(true);

					 
				});
				NU_Button.changeVolume.addActionListener(event -> 
				{	
					if(flag4 == true)
					{
						
						NU_Slider.volume.setVisible(true);
						save.loadSettings();
						NU_Slider.volume.setValue((int)(NU_Slider.a_volume*100));
						System.out.println(NU_Slider.a_volume*100);
						flag4 = false;
					}
					else if(flag4 == false)
					{
						NU_Slider.volume.setVisible(false);
						flag4 = true;
					}
					 
				});
				NU_Button.quitREC.addActionListener(event -> 
				{	
					drawLyricThread.interrupt();
				    accomPlayer.stop();
				    mediaPlayer.stop();
				    recordSound.finish();
				    NU_RecordSound.NUF.delete();
				    singFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    quitDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					singFrame.setVisible(false);
					quitDialog.setVisible(false);
					try{
			            File file = new File("D://NU_Garbage//NUS.wav");
			            if(file.delete()){
			                System.out.println(file.getName() + " File has been deleted!");
			            }else{
			                System.out.println("Fail!");
			            }
			        }catch(Exception e){
			            e.printStackTrace();
			        }
					west.interrupt();
					flag8 = false;
					NU_Button.jump.setVisible(true);
				});
				NU_Button.history.addActionListener(event -> 
				{	
					NU_Button.song1.setVisible(false);
					NU_Button.song2.setVisible(false);
					NU_Button.song1_name.setVisible(false);
					NU_Button.song2_name.setVisible(false);
					NU_Button.add_song1.setVisible(false);
					NU_Button.add_song2.setVisible(false);
					NU_Button.recommend.setVisible(false);
					NU_Button.singmarked.setVisible(false);
					NU_Button.deletemarked.setVisible(false);
					NU_Button.playWav.setVisible(false);
					NU_Button.deletelocal.setVisible(false);		
					NU_Button.uploadlocal.setVisible(false);	
					NU_Button.avatar.setVisible(false);
					NU_Button.intro.setVisible(false);
					NU_Button.intr.setVisible(false);
					if(flag6 == true)						
					{	
						flag6 = false;
						NU_List_searchHistory.historyList = new JList<>(NU_List_searchHistory.model3);
						nu.add(NU_List_searchHistory.historyList);
						NU_List_searchHistory.historyList.setBounds(220, 200, 800, 550);
						NU_List_searchHistory.historyList.setOpaque(false);
						
					}
					else if(flag6 == false)
					{

						NU_List_searchHistory.historyList.setVisible(false);
						

					
						flag6 = true;
					}
					 
				});




				NU_Button.download.addActionListener(event -> 
				{	
					NU_Button.avatar.setVisible(false);
					NU_Button.intro.setVisible(false);
					NU_Button.intr.setVisible(false);
					
					GroupFileView.downloadSavePath  = "D://NU_Songs";
					NU_Text.userInput = NU_Text.searchSongs.getText();
			
					//1
					String pathzip = "select pathzip from songlibrary where song_name = '" + NU_Text.userInput+"'";
					try {
						Statement stmt1 = connect.createStatement();
						ResultSet rst1 = stmt1.executeQuery(pathzip );
						zip = "";
						if(rst1.next())
						{
							zip = rst1.getString("pathzip");
							System.out.println(zip);
							System.out.println(zip);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}					
					GroupFileView.client_out.println("@action=Download["+zip+":null:null]");
					Thread decompression = new Thread(new Runnable() {
				        public void run() { 
				        	try {
				                 Thread.sleep(30000);
				             } catch (InterruptedException e) {
				            	 
				                 e.printStackTrace();
				                
				             }
				        	try {
								UnZipFile.unZipFiles(new File("D:NU_Songs//"+zip), "D://NU_Zip");
								NU_Dialog_wait DW = new NU_Dialog_wait(mainFrame,"The song has been downloaded! ");
								DW.setLocationRelativeTo(singFrame);
								DW.setVisible(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
				       
				        }
				    });	
					decompression.start();
					
					 
				});
				NU_Button.profile.addActionListener(event -> 
				{	
					System.out.println("ns"+login.nname);
					String introduction = "select introduction from userInformation where name = '" + login.nname+"'";
					NU_Button.intr.setVisible(true);
					NU_Button.song1.setVisible(false);
					NU_Button.song2.setVisible(false);
					NU_Button.song1_name.setVisible(false);
					NU_Button.song2_name.setVisible(false);
					NU_Button.add_song1.setVisible(false);
					NU_Button.add_song2.setVisible(false);
					NU_Button.recommend.setVisible(false);
					NU_Button.singmarked.setVisible(false);
					NU_Button.deletemarked.setVisible(false);
					NU_Button.playWav.setVisible(false);
					NU_Button.deletelocal.setVisible(false);
					NU_Button.uploadlocal.setVisible(false);
					
					try {
						Statement stmt1 = connect.createStatement();
						ResultSet rst1 = stmt1.executeQuery(introduction );
						String intro = "";
						if(rst1.next())
						{
							intro = rst1.getString("introduction");	
							System.out.println(intro);
						}
						NU_Button.intro.setText(intro);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					NU_Button.intro.setVisible(true);
					
					JFileChooser f = new JFileChooser(); // 查找文件
	                f.showOpenDialog(null);
	                File file = f.getSelectedFile();

	                if(file != null){
	                	String headPicturePath;
	                	headPicturePath = file.getPath();
	                	System.out.println(headPicturePath);
	                	NU_Button.avatar.setIcon(new ImageIcon(headPicturePath));
	                	
	                }
					NU_Button.avatar.setVisible(true);
	             
				});	
				
		});

	}
}
