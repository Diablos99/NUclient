package NU_Sing_WX;

import java.io.File;

import javax.swing.JOptionPane;

import javazoom.jl.converter.Converter;

public class Convert 
{
	static Object doWork(String PATH)
    {
        
        

            if(PATH != null)
            {
                try
                {
                   
                    Converter myConverter = new Converter();
                    
                    myConverter.convert(PATH, PATH+".wav");
                    System.out.println(PATH);
                }
                catch(Exception error)
                {
                    //updateJlabel("Converting Process Fail For : "+selectedFile.getName());
                    JOptionPane.showMessageDialog(null, "Converting Process Fail", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
            	System.out.println("PATH is not avaliable!");
            }
        
        return new Object();
    }
}
