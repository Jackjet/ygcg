package VegetablePrice.Table.TableConn;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;
import VegetablePrice.Table.VegetBean.ProductXinxi;

public class TableConnProduct {
	//��ȡ���и���Ա��Ҫ�Ĳ�Ʒ
	ProductXinxi px ;
	ArrayList<ProductXinxi> productxinxi = new ArrayList<ProductXinxi>();
	public TableConnProduct(Connection con  , String LeiBie) throws SQLException {
		try{
			String sql = "select * from infobiao where LeiBie =  ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1 ,LeiBie);
			ResultSet rs = ps.executeQuery();
			//��¼�ж���ҳ
			int id = 0 ;

			while (rs.next()) {
				id++;
				px =  new ProductXinxi();
				px.setId(""+id);
				px.setYongHuMing(rs.getString("YongHuMing"));//�û���
				px.setGongYingShang(rs.getString("GongYingShang"));//������
				px.setShangPin(rs.getString("ShangPin")); //��Ʒ
				px.setTiGongLiang(rs.getString("TiGongLiang")); //�ṩ��
				px.setDanJia(rs.getString("DanJia"));//����
				px.setDanWei(rs.getString("DanWei"));	//��λ
				px.setXuQiuLiang(rs.getString("XuQiuLiang"));
				
				productxinxi.add(px);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<ProductXinxi> getProductxinxi(){
		return productxinxi;
	}

}