package  NightAudit.TimeThreadNight;
import login.MysqlConnection.PingTai.DBUtil;
import java.util.TimerTask; 
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.sql.*;
import java.util.*;
import java.sql.*;
public class TimeThreadNight extends TimerTask {
	public void run(){
		try{    
			  Connection con = null ;
			  String product = "";
			  String username = "";
			  String time = "";
			  PreparedStatement ps = null;
			  //��ȡ����ʱ��
			  String webUrl4 = "http://www.ntsc.ac.cn";
			  //��ȡ����ʱ��
			  Date now = new Date(); 
			  SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");//���Է�����޸����ڸ�ʽ
			  String heretime = dateFormat.format(now); 
			  System.out.println(getWebsiteDatetime(webUrl4)+"\t"+heretime);

			  try {
				  ArrayList<String> a = new ArrayList<String>();
				  a.add("�߲�");
				  a.add("����");
				  a.add("������Ʒ");
				  a.add("���ðٻ�");
				  con = new DBUtil().getConn();
				  try  {
					    if (heretime.indexOf("һ")>=0) {
							 ps=con.prepareStatement("DELETE FROM infobiao");
							 ps.executeUpdate();
					    }
						else {
							con.setAutoCommit(false);
							ps = con.prepareStatement("DELETE FROM infobiao WHERE LeiBie = ?");
							ps.clearParameters();
							for (int i = 0; i < a.size() ; i++ ) {
								ps.setString(1,  a.get(i));
								ps.addBatch();
							}
							ps.executeBatch();
							con.commit(); //�����ύ
						}
				  }
				  catch (Exception e2) {
					  e2.printStackTrace();
				  }

			  }
			  catch (Exception e1){
				  e1.printStackTrace();
			  }

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	private static String getWebsiteDatetime(String webUrl){
		try	{
			URL url = new URL(webUrl);//��ȡ��Դ����
			URLConnection uc = url.openConnection(); //�������Ӷ���
			uc.connect(); //��������
			long ld = uc.getDate(); //��ȡ��վʱ��
			Date date = new Date(ld);// ת��Ϊ��׼ʱ�����
			SimpleDateFormat sdf  = new SimpleDateFormat("EEEE" , Locale.CHINA);
			
			return sdf.format(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
		