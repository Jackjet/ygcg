package login.MysqlConnection.Table;  
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Product {
	private String id;
	private String username;
	private String password;
	private String product;
	private String amount ;
	private String nuit ;
	private String userID ;
	private int pc;// ��ǰҳ��page code
	private int tp;// ��ҳ��total page
	private String url;//������url���������
	public Product(){
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
	//����
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	//��Ʒ
	public void setProduct(String product){
		this.product = product;
	}
	public String getProduct(){
		return product ;
	}
	//������
	public void setAmount(String amount){
		this.amount = amount;
	}
	public String getAmount(){
		return amount ;
	}
	//��������λ
	public void setNuit(String nuit){
		this.nuit = nuit;
	}
	public String getNuit(){
		return nuit ;
	}
	//ʳ��ʶ��
	public void setUserID(String userID){
		this.userID = userID;
	}
	public String getUserID(){
		return userID ;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	/**
	* ������ҳ��
	* @return
	*/
	public int getTp() {
		// ͨ���ܼ�¼����ÿҳ��¼����������ҳ��
		return tp;
	}
	public void setTp(int tp) {
	    this.tp = tp;
	}
}