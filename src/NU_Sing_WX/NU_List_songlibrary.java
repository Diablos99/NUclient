package NU_Sing_WX;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;


public class NU_List_songlibrary
{	
	//song library 
	protected static DefaultListModel<String> model;
	protected static JList<String> songnamesList;
	protected static String[] songnames;
	// read Works
	protected static void getFile()
	{	
		String path = "D://NU_Works";
		File file = new File(path);
		File[] array = file.listFiles();
		songnames = new String[array.length];
		for(int i=0; i<array.length; i++)
		{
			if(array[i].isFile())
			{								
				songnames[i] = String.valueOf(array[i].getName());				
			}
		}

	}
	public NU_List_songlibrary()
	{	
		//construct songnamesList
		model = new DefaultListModel<>();
		for(int i = 0; i<songnames.length; i++)
		{
			model.addElement(songnames[i]);
			
		}
			
			
	}

}
