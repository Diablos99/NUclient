package NU_Sing_WX;
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;   
import java.util.ArrayList;  
import java.util.List;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.net.URLDecoder;  

public class LyricReader 
{  
  
    BufferedReader bufferReader = null;                            
    public String title = "";                                     
    public String artist = "";                                     
    public String album = "";                                     
    public String lrcMaker = "";                                
    protected static List<LyricStatement> statements = new ArrayList<LyricStatement>(); 
    
  
    public LyricReader(String fileName) throws IOException 
    {         
    	FileInputStream file = new FileInputStream((fileName));  
    	bufferReader = new BufferedReader(new InputStreamReader(file));   
    	readData();  
    }  
  
    public List<LyricStatement> getStatements() 
    {  
    	return statements;  
    }  
  
    private void readData() throws IOException 
    {  
    	statements.clear();  
    	String strLine;       
    	while (null != (strLine = bufferReader.readLine())) 
    	{  
    		if ("".equals(strLine.trim())) {  
    			continue;  
        }  
        
        if (null == title || "".equals(title.trim())) 
        {  
        	Pattern pattern = Pattern.compile("\\[ti:(.+?)\\]");  
        	Matcher matcher = pattern.matcher(strLine);  
        	if (matcher.find()) 
        	{  
        		title = matcher.group(1);  
        		continue;  
        	}  
        }  
        
        if (null == artist || "".equals(artist.trim())) 
        {  
        	Pattern pattern = Pattern.compile("\\[ar:(.+?)\\]");  
        	Matcher matcher = pattern.matcher(strLine);  
        	if (matcher.find()) 
        	{  
        		artist = matcher.group(1);  
        		continue;  
        	}  
        }  
         
        if (null == album || "".equals(album.trim())) 
        {  
        	Pattern pattern = Pattern.compile("\\[al:(.+?)\\]");  
        	Matcher matcher = pattern.matcher(strLine);  
        	if (matcher.find()) 
        	{  
            album = matcher.group(1);  
            continue;  
        	}  
        }  
        
        if (null == lrcMaker || "".equals(lrcMaker.trim())) 
        {  
        	Pattern pattern = Pattern.compile("\\[by:(.+?)\\]");  
        	Matcher matcher = pattern.matcher(strLine);  
        	if (matcher.find()) 
        	{  
        		lrcMaker = matcher.group(1);  
        		continue;  
        	}  
        }  
           
        int timeNum = 0;                                        //本行含时间个数   
        String str[] = strLine.split("\\]");                //以]分隔   
        for (int i = 0; i < str.length; ++i) 
        {  
        String str2[] = str[i].split("\\[");            //以[分隔   
        str[i] = str2[str2.length - 1];  
        	if (isTime(str[i])) 
        	{  
        		++timeNum;  
        	}  
        }  
        for (int i = 0; i < timeNum; ++i)    
        {  
        LyricStatement sm = new LyricStatement();  
        sm.setTime(str[i]);  
        	if (timeNum < str.length)    
        	{  
        		sm.setLyric(str[str.length - 1]);  
        	}  
        statements.add(sm);
       
        }  
    	}  
  
   
    	sortLyric();  
    }      
  
    private boolean isTime(String string) 
    {  
    	String str[] = string.split(":|\\.");  
    	if (3 != str.length) 
    	{  
        return false;  
    	}  
    try {  
        for (int i = 0; i < str.length; ++i) 
        {  
        	Integer.parseInt(str[i]); 
        	
        }  
    } catch (NumberFormatException e) {  
        return false;  
    }  
    	return true;  
    }
  
    private void sortLyric() 
    {  
    	for (int i = 0; i < statements.size() - 1; ++i) {  
        int index = i;  
        double delta = Double.MAX_VALUE;  
        boolean moveFlag = false;  
        for (int j = i + 1; j < statements.size(); ++j) 
        {  
        	double sub;  
        	if (0 >= (sub = statements.get(i).getTime() - statements.get(j).getTime())) 
        	{  
            continue;  
        	}  
        	moveFlag = true;  
        	if (sub < delta) 
        	{  
            delta = sub;  
            index = j + 1;  
        	}  
        }  
        if (moveFlag) 
        {  
	        statements.add(index, statements.get(i));  
	        statements.remove(i);  
	        --i;  
        }  
       
    }  
    }      
    private void printLrcDate() 
	{  
	    System.out.println("Song Name: " + title);  
	    System.out.println("Singer: " + artist);  
	    System.out.println("Album " + album);  
	    System.out.println("Lycrics Maker: " + lrcMaker);  
	    for (int i = 0; i < statements.size(); ++i) 
	    {  
	        statements.get(i).printLyric(); 
	       
	    }  
    }  
    
}  