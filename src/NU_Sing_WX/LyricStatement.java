package NU_Sing_WX;
public class LyricStatement 
{
	 private long time = 0; // 10ms  
	 private String lyric = "";
	 
	 public long getTime()
	 {  
		 return time;  
	 }  	   
	 public void setTime(int time) 
	 {  
	    this.time = time;  
	 }  	    	  
	 public void setTime(String time) 
	 {  
		 String str[] = time.split(":|\\.");  
		 this.time = (Integer.parseInt(str[0]) * 6000 + Integer.parseInt(str[1]) * 100 + Integer.parseInt(str[2]))*10;  
	 }  	 
	 public String getLyric() 
	 {  
	    return lyric;  
	 }  	   
	 public void setLyric(String lyric) 
	 {  
	    this.lyric = lyric;  
	 }  	    
	 public void printLyric() 
	 {  
	    System.out.println(time + ": " + lyric);  
	 }  	  
}
