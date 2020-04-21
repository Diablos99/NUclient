package NU_Sing_WX;
import javax.swing.DefaultListModel;
import javax.swing.JList;
public class NU_List_orderedSongs 
{
	// ordered songs
	protected static DefaultListModel<String> model2;
	protected static JList<String> orderedsongsList;
	public NU_List_orderedSongs()
	{
		//construct orderedsongsList
		model2 = new DefaultListModel<>();
		orderedsongsList = new JList<>(model2);	
	}
}
