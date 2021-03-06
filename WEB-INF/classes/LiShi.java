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
@WebServlet("/LiShi")
public class LiShi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL ="jdbc:mysql://localhost:3306/hsuygcg?useUnicode=true&characterEncoding=utf-8";
	
	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "183411"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiShi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ps=null;
		// 设置响应内容类型
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String title = "历史查询";
		// 处理中文
		String zh =new String(request.getParameter("zh").getBytes("ISO8859-1"),"UTF-8");
		String bm=new String(request.getParameter("bm").getBytes("ISO8859-1"),"UTF-8");
		String sj=new String(request.getParameter("sj").getBytes("ISO8859-1"),"UTF-8");
		String bz=new String(request.getParameter("bz").getBytes("ISO8859-1"),"UTF-8");
	    String dex=bz.substring(0,2);
		String jl="";
	  if(dex.equals("10")||dex.equals("70")||dex.equals("13")||dex.equals("14"))
	  {
		  jl="day";
		}
	  else {
		 jl="week";
	  }
	  
		out.println("<!DOCTYPE html>\n" +
		"<html>\n" +"<link rel='stylesheet' href='https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css'>"+  
	    "<script src='https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js'></script>"+
		"<script src='https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>"+
		"<head><title>" + title + "</title></head>\n" +
		"<body bgcolor=\"#f0f0f0\">\n" +
		"<h1 align=\"center\">" + title + "</h1>\n");
		try{
			// 注册 JDBC 驱动器
			
			Class.forName(JDBC_DRIVER);
			// 打开一个连接
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// 执行 SQL 查询
			String sql;//SELECT * from jidan3 WHERE DATE_FORMAT(`TIMESTAMP`,'%Y-%m-%d')='2017-08-09'; 
			sql = "select * from "+bm+" where DATE_FORMAT(`TIMESTAMP`,'%Y-%m-%d')='"+sj+"'";
			ps=conn.prepareStatement(sql);
			int x=0; 
			ResultSet rs = ps.executeQuery(sql);
			if(jl.equals("day")){
			out.println("<div class='panel panel-info'><div class='panel-heading'>"+
			"<h3 class='panel-title'>我的订单</h3></div><table class='table'><tr><th>产品</th><th>单价</th><th>单位</th><th>时间</th></tr>");
			}
			else{
			out.println("<div class='panel panel-info'><div class='panel-heading'>"+
			"<h3 class='panel-title'>我的订单</h3></div><table class='table'><tr><th>产品</th><th>单价</th><th>数量</th><th>单位</th><th>时间</th></tr>");
			}
			while(rs.next()){
				String s1=rs.getString("ShangPin");
				String s2=rs.getString("DanJia");
				String s3=rs.getString("TiGongLiang");
				String s4=rs.getString("DanWei");
				String s5=rs.getString("TIMESTAMP");
				out.println("<tr><td>"+
				"<input type='text' class='form-control' style='width:30%' readonly= 'true' value='"+s1+"'>"+
				"</td>");
				out.println("<td>"+
				"<input type='text' class='form-control' style='width:30%' readonly= 'true' value='"+s2+"'>"+
				"</td>");
				if(jl.equals("week")){
				out.println("<td>"+
				"<input type='text' class='form-control' style='width:30%' readonly= 'true' value='"+s3+"'>"+
				"</td>");
				}
				out.println("<td>"+
				"<input type='text' class='form-control' style='width:30%' readonly= 'true' value='"+s4+"'>"+
				"</td>");
				out.println("<td>"+
				"<input type='text' class='form-control' style='width:30%' readonly= 'true' value='"+s5+"'>"+
				"</td></tr>");
				x++;
			}
			 
			out.println("</table></div></div>");
			if(x==0){
				out.println("<h1 align='center'>此日没有进行报价</h1>");
			}
			// 完成后关闭
			
			ps.close();
			conn.close();
		} catch(SQLException se) {
			// 处理 JDBC 错误
			out.println(se.toString()+"123");
		} catch(Exception e) {
			// 处理 Class.forName 错误
			out.println(e.toString()+"456");
		}finally{
			// 最后是用于关闭资源的块
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