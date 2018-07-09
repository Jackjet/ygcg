import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/InsertDataBaseGHS")
public class InsertDataBaseGHS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC �����������ݿ� URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/website?useUnicode=true&characterEncoding=utf-8";
	
	// ���ݿ���û��������룬��Ҫ�����Լ�������
	static final String USER = "root";
	static final String PASS = "183411"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertDataBaseGHS() {
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
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		// ��������
		String text =new String(request.getParameter("cpsl").getBytes("ISO8859-1"),"UTF-8");
		String zh=new String(request.getParameter("zh").getBytes("ISO8859-1"),"gb2312");
		int count=Integer.parseInt(text);
		String s="";
		String s1="",s2="",s3="",s4="",s5="",s6="",s7="";
		try{
			// ע�� JDBC ������
			
			Class.forName(JDBC_DRIVER);
			// ��һ������
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// ִ�� SQL ��ѯ
			String sql;
			int x=0;
			 
			for(int i=0;i<count;i++){
				s1=new String(request.getParameter("mc"+i).getBytes("ISO8859-1"),"gb2312");
				s+="1";
				s2=new String(request.getParameter("sl"+i).getBytes("ISO8859-1"),"gb2312");
				s+="2";
				s3=new String(request.getParameter("dj"+i).getBytes("ISO8859-1"),"gb2312");
				s+="3";
				s4=new String(request.getParameter("dw"+i).getBytes("ISO8859-1"),"gb2312");
				s+="4";
				s5=new String(request.getParameter("zj"+i).getBytes("ISO8859-1"),"gb2312");
				s+="5";
				s6=new String(request.getParameter("lj"+i).getBytes("ISO8859-1"),"gb2312");
				s+="6";
				s7=(new java.util.Date()).toLocaleString();
				s+="7";
				sql= "INSERT INTO ghs(mc,sl,dj,dw,zj,tp,sj,zh) VALUES('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"','"+s7+"','"+zh+"')";
				//ʵ���� PreparedStatement
				ps = conn.prepareStatement(sql);
				x=ps.executeUpdate();
				
			}
			if(x>0){
				out.println("<script>alert('�ύ�ɹ�!');</script>");
			}
			// չ����������ݿ�
			if(x==0)
				out.println("<script>alert('û���ύ�κ�����!');</script>");
			// ��ɺ�ر�
			
			ps.close();
			conn.close();
		} catch(SQLException se) {
			// ���� JDBC ����
			out.println(s1+s2+s3+s4+s5+s6+s7);
			out.println(se.toString()+s);
		} catch(Exception e) {
			// ���� Class.forName ����
			out.println(s1+s2+s3+s4+s5+s6+s7);
			out.println(e.toString()+s);
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