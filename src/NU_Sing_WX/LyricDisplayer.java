package NU_Sing_WX;
import java.awt.AlphaComposite;  
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;  
import java.awt.FontMetrics;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.RenderingHints;  
import java.io.File;  
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;  
import java.net.URL;  
import java.util.List;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
import java.io.FileOutputStream;    
import java.util.Arrays;  
import java.util.Iterator;    
import javax.imageio.ImageReadParam;  
import javax.imageio.ImageReader;  
import javax.imageio.metadata.IIOMetadata;  
import javax.imageio.stream.ImageInputStream;  
  

public class LyricDisplayer extends JPanel 
{  	  		  
	    protected final Color CURRENT_LINE_COLOR = Color.magenta;  
	    protected final Color OTHER_LINE_COLOR = Color.	magenta;  
	    //the lines other than the current line to be displayed   
	    protected final int UP_DOWN_LINES = 8;  
	    //the list of lyric statements to be displayed   
	    protected static List<LyricStatement> statements;  
	    //the index of next statement to be dispalyed in the statements   
	    protected static int index;  
	    protected Image backgroundImage = null;  
	    private String backGroundImagePath = null;  
	    protected Image bufferImage = null;  
	    //the size when the bufferImage is drawn   
	    private Dimension bufferedSize;  
	    protected static long timeInterval;
	    protected static int firstInterval;
	   
	    public String getBackGroundImagePath() 
	    {  
	    	return backGroundImagePath;  
	    }  
	  
	    public void setBackGroundImagePath(String backGroundImagePath) 
	    {  
	    	this.backGroundImagePath = backGroundImagePath;  
	    }  
	  
	    /** 
	     * get ready to display 
	     * @param statements  
	     */  
	    public void prepareDisplay(List<LyricStatement> statements) 
	    {  
		    this.statements = statements;  
		    this.index = 0;
		    this.setFont(new Font("Tahoma", Font.PLAIN, 20));  
	    }  
	  
	    /** 
	     * display a lyric by the index 
	     * @param index  
	     */  
	    public void displayLyric(int index) 
	    {  	  
		    this.index = index;  
		    this.drawBufferImage();  
		//  System.out.println("draw " + index + " " + this.statements.get(index).getLyric());   
		    this.paint(this.getGraphics());  
	    }  
	  
	    /** 
	     * draw a line of lyric in the middle of the Graphics2D 
	     * @param lyric 
	     * @param g2d  
	     */  
	    protected void drawLineInMiddle(int height, String lyric, Graphics2D g2d, Color color) 
	    {  
		    int width = this.getWidth();  
		    FontMetrics fm = g2d.getFontMetrics();  
		    g2d.setColor(color);  
		    int x = (this.getWidth() - fm.stringWidth(lyric)) / 2;  
		    g2d.drawString(lyric, x, height);  
	    }  
	  
	    /** 
	     * Draw the buffered image. Used to realize the double-buffering. 
	     * @throws IOException 
	     */  
	    protected void drawBufferImage() 
	    {	    
	    	Image tempBufferedImage = this.createImage(this.getWidth(), this.getHeight());
	    	this.bufferedSize = this.getSize();
	    	if (this.backgroundImage == null) 
	    	{
	    	    //get background image
	    	    URL url = getClass().getResource(this.backGroundImagePath);	           
	    	    try 	    	    {
	    		backgroundImage = ImageIO.read(url);
	    		backgroundImage = this.backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), 20);
	    	    } catch (IOException ex) {
	    		ex.printStackTrace();
	    	    }
	    	}	    	    
		Graphics2D g2d = (Graphics2D) tempBufferedImage.getGraphics();
	    g2d.setFont(new Font("Tahoma", Font.PLAIN, 25));  
	    g2d.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);  
	  
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
	        RenderingHints.VALUE_ANTIALIAS_ON);  
	    if (this.statements != null && this.statements.size() != 0) 
	    {  
	        //draw current line   
	    	
	        g2d.setFont(new Font("Tahoma", Font.BOLD, 35));  
	        this.drawLineInMiddle(this.getHeight() / 2,  
	            this.statements.get(index).getLyric(), g2d, this.CURRENT_LINE_COLOR);  
	        int perHeight = g2d.getFontMetrics().getHeight() + 5;  
	        
	        g2d.setFont(new Font("Tahoma", Font.PLAIN, 25));  
	        //draw down lines   
	        for (int i = index - UP_DOWN_LINES; i < index; i++) 
	        {  
	        	
	        	if (i < 0) 
	        	{  
	        		
	        		continue;  
	        	}  
	        	
	        	if (index - i > UP_DOWN_LINES / 2)
	        	{  
		            //set transparance   
		            float ratio = (float) (i - index + UP_DOWN_LINES) / (UP_DOWN_LINES / 2) / 1.2f; 
		         
		            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
	                ratio));  
	        	} 
	        	else 
	        	{  
	            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
	                1.0f));  
	        	}  
	        	this.drawLineInMiddle(this.getHeight() / 2 - (index - i) * perHeight,  
	            this.statements.get(i).getLyric(), g2d, this.OTHER_LINE_COLOR);  
	        }  
	        //draw up lines   
	        for (int i = index + 1; i < index + UP_DOWN_LINES; i++) 
	        {  
		        if (i >= this.statements.size()) 
		        {  
		            break;  
		        }  
	        if (i - index > UP_DOWN_LINES / 2) 
	        {  
	            //set transparance   
	            float ratio = (float) (index + UP_DOWN_LINES - i) / (UP_DOWN_LINES / 2) / 1.2f;  
	            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
	                ratio));  
	        } 
	        else 
	        {  
	            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  
	                1.0f));  
	        }  
	        this.drawLineInMiddle(this.getHeight() / 2 + (i - index) * perHeight,  
	        this.statements.get(i).getLyric(), g2d, this.OTHER_LINE_COLOR);  
	        }  
	    } 
	    else {  
	        //statements is empty   
	        this.drawLineInMiddle(this.getHeight() / 2,  
	            "未找到相应的歌词文件", g2d, this.CURRENT_LINE_COLOR);  
	    }  
	  
	    //copyt the buffered image   
	    this.bufferImage = tempBufferedImage;  
	    }  
	  
	    /** 
	     * This method is override in order to display the lyric in the panel 
	     * @param g  
	     */  
	    @Override  
	    public void paint(Graphics g) {  
	    if (this.isVisible() == false) {  
	        return;  
	    }  
	    super.paint(g);  	  
	    //draw buffered image    
	    if (this.bufferImage == null || this.getWidth() != this.bufferedSize.getWidth()  
	        || this.getHeight() != this.bufferedSize.getHeight()) {  
	        this.drawBufferImage();  
	    }  
	    //copy the double buffer   
	    g.drawImage(bufferImage, 0, 0, null);  
	  
	    }  
	    public long timeinterval()
	    {	    	
	    	timeInterval = this.statements.get(index+1).getTime()-this.statements.get(index).getTime();
	    	return timeInterval;
	    }
	    public int timeintervalCut()
	    {
	    	firstInterval = (int) ((this.statements.get(1).getTime()-this.statements.get(0).getTime())/1000);
	    	return firstInterval;
	    }
	    
	}  