package VegetablePrice;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;
import login.MysqlConnection.PingTai.DBUtil;
import VegetablePrice.Table.VegetBean.ProductXinxi;
import VegetablePrice.Table.TableConn.TableConnProduct;
public class VegetableServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void goPost(HttpServletRequest request , HttpServletResponse response) 
	   throws ServletException , IOException {
	   doGet(request , response );
	}
	public void doGet(HttpServletRequest request , HttpServletResponse response) 
	   throws ServletException , IOException {
		String username =request.getParameter("username"); //获取名字
	    String ProductID = request.getParameter("ID"); //产品种类的ID
		username = new String(username.getBytes("ISO8859-1"), "GB2312");
		System.out.println(username+"\t"+ProductID);
	//	ProductID = new String(ProductID.getBytes("ISO8859-1"), "GB2312");
		try	{
			boolean True =  false;
    		Connection con = new DBUtil().getConn();
			String sql = "select * from denglu where Name = ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1  , username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				True = true;
			}
			//判断是否为该用户
			if (True == false ) {
			    request.setAttribute("error","#myModal");
				request.setAttribute("username" ,"");
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}else{
				//判断是哪一个产品种类
				if (ProductID.equals("1"))	{
					//获取产品中有多少个商家
					ProductID = "粮油";
				}
				if (ProductID.equals("2"))	{
					ProductID = "猪肉";
				}
				if (ProductID.equals("3"))	{
					ProductID = "冻货";
				}
				if (ProductID.equals("4"))	{
					ProductID = "干货调味";
				}
				if (ProductID.equals("5"))	{
					ProductID = "鸡蛋";
				}
				if (ProductID.equals("6"))	{
					ProductID = "豆制品";
				}
				if (ProductID.equals("7"))	{
					ProductID = "蔬菜";
				}
				if (ProductID.equals("8"))	{
					ProductID = "鲜鱼";
				}
				if (ProductID.equals("9"))	{
					ProductID = "牛肉";
				}
				if (ProductID.equals("10"))	{
					ProductID = "奶制品";
				}
				if (ProductID.equals("11"))	{
					ProductID = "一次性用品";
				}
				if (ProductID.equals("12"))	{
					ProductID = "面制品";
				}
				if (ProductID.equals("13"))	{
					ProductID = "厨房用品";
				}
				if (ProductID.equals("14"))	{
					ProductID = "日用百货";
				}
				if (ProductID.equals("15"))	{
					ProductID = "活禽";
				}
				ArrayList<String> ShangJian = new ArrayList<String>();
				ArrayList<String> number = new ArrayList<String>();
				ArrayList<String> PersonName = new ArrayList<String>();
				Hashtable<String,String> has=new Hashtable<String,String>();
				String sql1 = "select * from denglu where ZuMing = ? ";
				PreparedStatement ps1 = con.prepareStatement(sql1);
				ps1.setString(1  , ProductID);
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()) {
					ShangJian.add(rs1.getString("Name"));
					number.add(rs1.getString("ContactNumber"));
					PersonName.add(rs1.getString("ContactPerson"));
					has.put(rs1.getString("ContactPerson"),rs1.getString("Name"));
				}
				//Collections.shuffle(ShangJian);
				//获取该产品种类的数量及产品
				ArrayList<ProductXinxi> Zhonglei = new ArrayList<ProductXinxi>();
				ProductXinxi px ;
				PreparedStatement ps2 = con.prepareStatement( "select * from danwei where zhonglei = ? ");
				ps2.setString(1  , ProductID);
				ResultSet rs2 = ps2.executeQuery();
				int sun = 0 ;
				while(rs2.next()) {
					sun++;
					px = new ProductXinxi();
					px.setId(""+sun);
					px.setShangPin(rs2.getString("name"));
					px.setDanWei(rs2.getString("danwei"));
					Zhonglei.add(px);
				}
				ArrayList<ProductXinxi> userProduct = new TableConnProduct(con , ProductID).getProductxinxi();
				HttpSession session = request.getSession();
				session.setAttribute("sjlist" ,ShangJian);
				session.setAttribute("zllist" ,Zhonglei);  //菜品种类
				session.setAttribute("username" ,username);
				session.setAttribute("PersonName" ,PersonName);
				session.setAttribute("ProductID" ,ProductID);
				session.setAttribute("number" , number);
				session.setAttribute("listproduct" , userProduct);
				session.setAttribute("has" , has);
				System.out.println(Zhonglei.size());
				response.sendRedirect("Productform.jsp");
				
			}
		}
		catch (Exception e){
			request.setAttribute("error",e);
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			e.printStackTrace();
		}
	}	
}