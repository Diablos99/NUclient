package NU_Sing_WX;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
public class NU_List_searchHistory 
{
	// ordered songs
		protected static DefaultListModel<String> model3;
		protected static JList<String> historyList;
		protected static ArrayList<String> list = new ArrayList<>();
		public NU_List_searchHistory ()
		{
			//construct orderedsongsList
			model3 = new DefaultListModel<>();
				
			historyList = new JList<>(model3);	
		}
}
