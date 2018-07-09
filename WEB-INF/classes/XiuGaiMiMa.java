import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/XiuGaiMiMa")
public class XiuGaiMiMa extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC �����������ݿ� URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL ="jdbc:mysql://localhost:3306/hsuygcg?useUnicode=true&characterEncoding=utf-8";
	
	// ���ݿ���û��������룬��Ҫ�����Լ�������
	static final String USER = "root";
	static final String PASS = "183411"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XiuGaiMiMa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ps=null;
		// ������Ӧ��������
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String title = "��¼����";
		// ��������
		String text =new String(request.getParameter("username").getBytes("ISO8859-1"),"UTF-8");
		String pass=new String(request.getParameter("password1").getBytes("ISO8859-1"),"UTF-8");
		try{
			// ע�� JDBC ������
			
			Class.forName(JDBC_DRIVER);
			// ��һ������
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// ִ�� SQL ��ѯ
			String sql;
			sql = "update denglu set Password='"+pass+"' where Name='"+text+"'";
			ps=conn.prepareStatement(sql);
			int x=0;
			x=ps.executeUpdate(sql); 
			if(x==0)out.println("<script>alert('�޸�ʧ��!');</script>");
			// չ����������ݿ�
			else
				out.println("<script>alert('�޸ĳɹ�!')</script>");

			// ��ɺ�ر�
			
			ps.close();
			conn.close();
		} catch(SQLException se) {
			// ���� JDBC ����
			out.println(se.toString());
		} catch(Exception e) {
			// ���� Class.forName ����
			out.println(e.toString());
		}finally{
			// ��������ڹر���Դ�Ŀ�
			try{
				if(ps!=null)
				ps.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
				conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}