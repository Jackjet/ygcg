package login;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;
import login.MysqlConnection.PingTai.DBUtil;
import login.MysqlConnection.User.User;
public class LoginServlet extends HttpServlet {
	Connection con = null;
	UserDao userDao = new UserDao();

	private static final long serialVersionUID = 1L;
	public void goGet(HttpServletRequest request , HttpServletResponse response) 
	   throws ServletException , IOException {
	   doPost(request , response );
	}
	public void doPost(HttpServletRequest request , HttpServletResponse response) 
	   throws ServletException , IOException {
		String username =request.getParameter("username");
		username = new String(username.getBytes("ISO8859-1"), "GB2312");
	    String password  = request.getParameter("password");
		try	{
    		con = new DBUtil().getConn();
			User user = new User(username , password);
			User currentUser = userDao.login(con , user);
		
			if (currentUser == null) {
			    request.setAttribute("error","#myModal");
				request.setAttribute("username" ,"");
				request.setAttribute("password", "");
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}else{
				String BiaoZhi = currentUser.getBiaozhi();
				if (BiaoZhi.equals("1")) {
					HttpSession session = request.getSession();
					session.setAttribute("username" ,currentUser.getUsername());
					response.sendRedirect("index.jsp");
				}
		
			}
		}
		catch (Exception e){
			request.setAttribute("error",e);
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			e.printStackTrace();
		}
	}	
	class UserDao  {
	    public User login(Connection con , User user) throws SQLException {
			User resultUser = null;
			try{
				String sql = "select * from denglu where Name = ? and Password = ?  ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1  , user.getUsername());
				ps.setString(2 ,  user.getPassword());
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					resultUser = new User();
					resultUser.setUsername(rs.getString("Name"));
					resultUser.setPassword(rs.getString("Password"));
					resultUser.setBiaozhi(rs.getString("BiaoZhi"));
				}
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
			return resultUser;
		}
	}
}