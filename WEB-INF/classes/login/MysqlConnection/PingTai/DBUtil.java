package login.MysqlConnection.PingTai;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
public class DBUtil {
	private Connection conn;
	public  Connection getConn() throws Exception {
		 Context ctx = new InitialContext();
	     DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ewsdb1");
		 conn = ds.getConnection();
		 return conn;
	}	
}
