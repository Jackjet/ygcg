import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/InsertYGCG")
public class InsertYGCG extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC �����������ݿ� URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/hsuygcg?useUnicode=true&characterEncoding=utf-8";
	
	// ���ݿ���û��������룬��Ҫ�����Լ�������
	static final String USER = "root";
	static final String PASS = "183411"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertYGCG() {
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
		String hm =new String(request.getParameter("hm").getBytes("ISO8859-1"),"UTF-8");
		String bm=new String(request.getParameter("bm").getBytes("ISO8859-1"),"UTF-8");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sj=df.format(new Date());
		int count=Integer.parseInt(text);
		String s="";
		String s1="",s2="",s3="",s4="",s5="",s6="",s7="";
		java.util.Date date=new java.util.Date();
		int day=2;//date.getDay();
		try{
			// ע�� JDBC ������
			
			Class.forName(JDBC_DRIVER);
			// ��һ������
			 
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			// ִ�� SQL ��ѯ
			String sql;
			int x=0;
			int y=0;
			sql = "select * from "+bm+hm+" where DATE_FORMAT(`TIMESTAMP`,'%Y-%m-%d')='"+sj+"'";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			int a=0;
			while(rs.next()){
				a++;
			}
			if(a!=0){
				for(int i=0;i<count;i++){
						s1=new String(request.getParameter(i+"count").getBytes("ISO8859-1"),"gb2312");//��Ʒ��
						s2=new String(request.getParameter(i+"count1").getBytes("ISO8859-1"),"gb2312");//����
						s3=new String(request.getParameter(i+"count2").getBytes("ISO8859-1"),"gb2312");//��λ
						s4=new String(request.getParameter(i+"count3").getBytes("ISO8859-1"),"gb2312");//����
						s5=(new java.util.Date()).toLocaleString();
						if(s4.equals("159357.111")){
							sql= "update "+bm+hm+" set DanJia='"+s2+"' where ShangPin='"+s1+"' and  DATE_FORMAT(`TIMESTAMP`,'%Y-%m-%d')='"+sj+"'";
						}
						else{
							sql= "update "+bm+hm+" set DanJia='"+s2+"',TiGongLiang='"+s4+"' where ShangPin='"+s1+"' and  DATE_FORMAT(`TIMESTAMP`,'%Y-%m-%d')='"+sj+"'";					
						}//ʵ���� PreparedStatement
						ps = conn.prepareStatement(sql);
						y=ps.executeUpdate();
						
					}
			}
			else{
					for(int i=0;i<count;i++){
						s1=new String(request.getParameter(i+"count").getBytes("ISO8859-1"),"gb2312");
						s2=new String(request.getParameter(i+"count1").getBytes("ISO8859-1"),"gb2312");
						s3=new String(request.getParameter(i+"count2").getBytes("ISO8859-1"),"gb2312");
						s4=new String(request.getParameter(i+"count3").getBytes("ISO8859-1"),"gb2312");
						s5=(new java.util.Date()).toLocaleString();
						sql= "INSERT INTO "+bm+hm+" (ShangPin,DanJia,DanWei,TiGongLiang,TIMESTAMP) VALUES('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"')";
						//ʵ���� PreparedStatement
						ps = conn.prepareStatement(sql);
						x=ps.executeUpdate();
						
					}
			}
			if(x>0){
				out.println("<script>alert('�ύ�ɹ�!');</script>");
				return;
			}
			if(y>0)
			{
				out.println("<script>alert('�޸ĳɹ�!');</script>");
				return;
			}
			// չ����������ݿ�
			if(x==0)
				out.println("<script>alert('û���ύ�κ�����!');</script>");
			// ��ɺ�ر�
			
			ps.close();
			conn.close();
		} catch(SQLException se) {
			// ���� JDBC ����
			out.println(s1+s2+s3+s4+s5+s6+s7+"1");
			out.println(se.toString()+s);
		} catch(Exception e) {
			// ���� Class.forName ����
			out.println(s1+s2+s3+s4+s5+s6+s7+"2");
			out.println(e.toString()+s);
		}finally{
			// ��������ڹر���Դ�Ŀ�
			try{
				if(ps!=null)
				ps.close();
			}catch(SQLException se2){
				out.println("se2");
			}
			try{
				if(conn!=null)
				conn.close();
			}catch(SQLException se){
				se.printStackTrace();
				out.println("se");
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