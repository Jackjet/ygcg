package VegetablePrice.Table.VegetBean;  
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductXinxi {
	private String id;
	private String username;
	private String yongHuMing; //�û���
	private String gongYingShang ; //������
	private String shangPin ; //��Ʒ
	private String tiGongLiang ; //�ṩ��
	private String danJia ; //����
	private String danWei ; //��λ
	private String zongJia  ; //�ܼ�
	private String xuQiuLiang ;// ������
	private String biaoJi ; //���
	private String caiGouYuan ; //�ɹ�Ա
	private String leiBie; //����
	private String time ; //ʱ��
	private String biaoShi ; //��ʾ
	public ProductXinxi(){
		super();
	}
	//Id
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	//�û�
	public  String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username = username;
	}
	//��Ʒ
	public String getShangPin(){
		return shangPin;
	}
	public void setShangPin(String shangPin){
		this.shangPin = shangPin;
	}
	//�û���
	public String getYongHuMing(){
		return yongHuMing;
	}
	public void setYongHuMing(String yongHuMing){
		this.yongHuMing =yongHuMing;
	}
	//������
	public String getGongYingShang(){
		return gongYingShang;
	}
	public void setGongYingShang(String gongYingShang){
		this.gongYingShang = gongYingShang;
	}
	//�ṩ��
	public String getTiGongLiang(){
		return tiGongLiang;
	}
	public void setTiGongLiang(String tiGongLiang){
		this.tiGongLiang = tiGongLiang;
	}
	//����
	public String getDanJia(){
		return danJia;
	}
	public void setDanJia(String danJia){
		this.danJia = danJia;
	}
	//��λ
	public String getDanWei(){
		return danWei;
	}
	public void setDanWei(String danWei){
		this.danWei = danWei;
	}
	//�ܼ�
	public String getZongJia(){
		return zongJia;
	}
	public void setZongJia(String zongJia){
		this.zongJia = zongJia;
	}
	//������
	public String getXuQiuLiang(){
		return xuQiuLiang;
	}
	public void setXuQiuLiang(String xuQiuLiang){
		this.xuQiuLiang = xuQiuLiang;
	}
	//ʳ�ñ��
	public String getBiaoJi(){
		return biaoJi;
	}
	public void setBiaoJi(String biaoJi){
		this.biaoJi = biaoJi;
	}
	//�ɹ�Ա
	public String getCaiGouYuan(){
		return caiGouYuan;
	}
	public void setCaiGouYuan(String caiGouYuan){
		this.caiGouYuan = caiGouYuan;
	}
	//���
	public String getLeiBie(){
		return leiBie;
	}
	public void setLeiBie(String leiBie){
		this.leiBie = leiBie;
	}
	//ʱ��
	public String getTime(){
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	//��ʾ
	public String getBiaoShi(){
		return biaoShi;
	}
	public void setBiaoShi(String biaoShi){
		this.biaoShi = biaoShi;
	}
}