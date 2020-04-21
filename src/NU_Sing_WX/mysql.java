package NU_Sing_WX;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


import NU_Sing_WX.User;
import NU_Sing_WX.warm;
public class mysql {
	public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://172.20.10.2:3306/nusing?serverTimezone=UTC ";
	public static final String DBUSER ="root";
	public static final String DBPW = "Wx0912...";
	private static String tname;
	private static String tpass;
	Statement stmt = null;
	String pass = null,pass1 = null ;
	Connection connection = null;
	//register
	public mysql() {	
	}
	public boolean register(User user) throws SQLException {
		stmt = connection.createStatement();
		String sql = "insert  into  userInformation "
				+"values('"+user.getId()+"','"+user.getName()+"','"+user.getPassword()+"','"+user.getIntroduction()+"','"
				+user.getSecurityAnswer()+"','"+user.getSecurityQuestion()+"')";
		
		if(stmt.execute(sql) ) return false;
		else return true;
	}
	public void connectSQL() {
		try {
			Class.forName(DBDRIVER);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPW);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(connection);
	}

	
	//forgetPassword
	public String findPassword(User user) throws SQLException {
		mysql.tname = user.getName();
		mysql.tpass = user.getSecurityAnswer();
		stmt = connection.createStatement();
		String sql2 = "SELECT securityAnswer FROM User WHERE user_id ='"+mysql.tname+"'";
		ResultSet rs = stmt.executeQuery(sql2);
		try {
			while(rs.next()){ 
			 pass = rs.getString("securityAnswer");
			}
		}catch(SQLException ex) {
			warm ww = new warm();
			ww.registIncomplete();
		}
		 pass1 = mysql.tpass ;
		 if(pass1.equals(pass)) {
			 String sql = "SELECT password FROM User WHERE user_id ='"+mysql.tname+"'";
			 ResultSet rs2 = stmt.executeQuery(sql);
			 while(rs2.next()){ 
				 pass = rs2.getString("password");
			 }
			 return pass;
		 }
		 else return pass;
		
	}
	//login
	public boolean loginMatch(String tname,String tpass)throws SQLException,Exception {
		mysql.tname = tname;
		mysql.tpass = tpass;
		stmt = connection.createStatement();
		String sql = "SELECT password FROM userInformation WHERE name ='"+mysql.tname+"'";
		ResultSet rs = stmt.executeQuery(sql);
		 while(rs.next()){ 
			 pass = rs.getString("password");
		 }
		 pass1 = mysql.tpass ;
		 if(pass1.equals(pass)) return true;
		 else return false;
	}
}
