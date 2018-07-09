package login.MysqlConnection.UserProduct;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;
import login.MysqlConnection.User.*;
import login.MysqlConnection.Table.Product;

public class UserProduct {
	//��ȡ���и���Ա��Ҫ�Ĳ�Ʒ
	Product pd ;
	ArrayList<Product> product = new ArrayList<Product>();
	public UserProduct(Connection con , User user) throws SQLException {
		try{
			String sql = "select * from userproduct where username = ? and password = ?  ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1  , user.getUsername());
			ps.setString(2 ,  user.getPassword());
			ResultSet rs = ps.executeQuery();
			//��¼�ж���ҳ
			int pageamount = 0 ;
			while (rs.next()) {
				pageamount++;
				pd =  new Product();
				pd.setUsername(rs.getString("username"));
				pd.setPassword(rs.getString("password"));
				pd.setProduct(rs.getString("product")); //ʳ�������Ʒ
				pd.setAmount(rs.getString("amount"));//ʳ��������
				pd.setNuit(rs.getString("nuit")); //ʳ�õ�λ
				pd.setUserID(rs.getString("userID")); 
				pd.setPc(rs.getInt("page"));//��ǰҳ
				pd.setUrl("http://192.168.67.92:8080/matchcenter/TableName"); //��ҳ��url
				pd.setTp(pageamount); //���м�ҳ
				
				product.add(pd);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Product> getProduct(){
		return product;
	}

}