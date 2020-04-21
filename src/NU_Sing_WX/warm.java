package NU_Sing_WX;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class warm {
	//warm = warn
	JFrame temp ;
	public warm() {
		temp = new JFrame();
		temp.setVisible(true);
		temp.setLocation(500, 250);
		temp.setSize(200, 200);
	}
	public void reputCheckWarning() {
		JLabel templb = new JLabel("Two password are inconsistent!");
		temp.add(templb);
	}
	public void findFail() {
		JLabel templb = new JLabel("Wrong answer!");
		temp.add(templb);
	}
	public void registerSuccessful() {
		JLabel templb = new JLabel("Registration success!");
		temp.add(templb);
	}
	public void loginFail() {
		JLabel templb = new JLabel("Incorrect password!");
		temp.add(templb);
	}
	public void sqlConnectFail() {
		JLabel templb = new JLabel("Database connection error!");
		temp.add(templb);
	}
	public void registIncomplete() {
		JLabel templb = new JLabel("Rwgistraton information error!");
		temp.add(templb);
	}
}
