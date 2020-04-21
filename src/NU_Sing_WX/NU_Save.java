package NU_Sing_WX;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class NU_Save implements Serializable
{	
	static User user = new User();
	public void saveSettings() 

    { 
        try  
        {    
        	try 
			{
        	File xml = new File("james.ser"); 
			xml.createNewFile();
			}catch (IOException e){
				e.printStackTrace();};           
            FileOutputStream t_outputStream = new FileOutputStream("james.ser"); 
            XMLEncoder t_encoder = new XMLEncoder(t_outputStream); 
            t_encoder.writeObject(NU_Slider.a_volume ); 
            System.out.println(NU_Slider.a_volume );
            t_encoder.close(); 
            t_outputStream.close(); 
            System.out.println("saved"); 
        }  
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }     
    } 
    public void loadSettings() 

    { 
        try 
        {    
            FileInputStream t_input = new FileInputStream("james.ser"); 
            XMLDecoder t_decode = new XMLDecoder(t_input); 
            Object t_object = t_decode.readObject(); 
            double t_temp = (double) t_object;
            System.out.println(t_temp);
            NU_MainFrame.nu.setVolume(t_temp);             
            t_decode.close(); 
            t_input.close();                          
        } 
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }    
    }
    public void saveNUCSettings() 

    { 
        try  
        {    
        	try 
			{
        	File xml = new File("wade.ser"); 
			xml.createNewFile();
			}catch (IOException e){
				e.printStackTrace();};           
            FileOutputStream t_outputStream = new FileOutputStream("wade.ser"); 
            XMLEncoder t_encoder = new XMLEncoder(t_outputStream); 
            t_encoder.writeObject(CutMusic.NUC_index); 
            t_encoder.close(); 
            t_outputStream.close(); 
        }  
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }     
    } 
    public void loadNUCSettings() 

    { 
        try 
        {    
            FileInputStream t_input = new FileInputStream("wade.ser"); 
            XMLDecoder t_decode = new XMLDecoder(t_input); 
            Object t_object = t_decode.readObject(); 
            int t_temp = (int) t_object;
            CutMusic.cutM.setIndex(t_temp);             
            t_decode.close(); 
            t_input.close();                          
        } 
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }    
    }
    public static void saveUserIDSettings() 

    { 
        try  
        {    
        	try 
			{
        	File xml = new File("bosh.ser"); 
			xml.createNewFile();
			}catch (IOException e){
				e.printStackTrace();};           
            FileOutputStream t_outputStream = new FileOutputStream("bosh.ser"); 
            XMLEncoder t_encoder = new XMLEncoder(t_outputStream); 
            t_encoder.writeObject(User.user_id); 
            t_encoder.close(); 
            t_outputStream.close(); 
        }  
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }     
    } 
    public static void loadUserIDSettings() 

    { 
        try 
        {    
            FileInputStream t_input = new FileInputStream("bosh.ser"); 
            XMLDecoder t_decode = new XMLDecoder(t_input); 
            Object t_object = t_decode.readObject(); 
            int t_temp = (int) t_object;
            user.setId(t_temp);             
            t_decode.close(); 
            t_input.close();                          
        } 
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }    
    }
    public static void saveHistory() 

    { 
        try  
        {    
        	try 
			{
        	File xml = new File("Tmac.ser"); 
			xml.createNewFile();
			}catch (IOException e){
				e.printStackTrace();};           
            FileOutputStream t_outputStream = new FileOutputStream("Tmac.ser"); 
            XMLEncoder t_encoder = new XMLEncoder(t_outputStream); 
            t_encoder.writeObject(NU_List_searchHistory.model3); 
            t_encoder.close(); 
            t_outputStream.close(); 
        }  
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }     
    } 
    public static void loadHistory() 

    { 
        try 
        {    
            FileInputStream t_input = new FileInputStream("Tmac.ser"); 
            XMLDecoder t_decode = new XMLDecoder(t_input); 
            Object t_object = t_decode.readObject(); 
            NU_List_searchHistory.model3 = (DefaultListModel<String>) t_object;           
            t_decode.close(); 
            t_input.close();                          
        } 
        catch (Exception _ex)  
        { 
            System.out.println(_ex.getMessage()); 
        }    
    }
}
