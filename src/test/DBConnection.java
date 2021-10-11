package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	Connection con;
	
	public DBConnection(String URL,String UserName, String Password) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.con = DriverManager.getConnection(URL, UserName, Password);
		
		
	}
	
	public Connection getConnection() {
		return this.con;
	}
	
	public void closeConnection() throws SQLException{
		if(this.con != null)
			this.con.close();
	}
	

}