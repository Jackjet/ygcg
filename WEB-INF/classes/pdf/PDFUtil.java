package pdf;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.sql.*;
import java.sql.*;
import login.MysqlConnection.PingTai.DBUtil;
import VegetablePrice.Table.VegetBean.ProductXinxi;
import VegetablePrice.Table.TableConn.TableConnProduct;
import java.util.*;
import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

public class PDFUtil extends HttpServlet{
	/**
	* ����PDF�ĵ�
	* @return
	* @throws Exception
	* @throws docException
	*/
	private PrintWriter out;
	static  String ProductID;
	static String admin;
	static String inputFile ;
	static BaseFont bfChinese ;
	public void doGet(HttpServletRequest request , HttpServletResponse response ) 
		throws ServletException , IOException {
		doPost(request , response);
	}
	public void doPost(HttpServletRequest request , HttpServletResponse response ) 
	 throws ServletException , IOException {
		try{
			 response.setContentType("text/thml:charset=ISO-8859-1");
			 request.setCharacterEncoding("UTF-8");
			 response.setContentType("text/html");
			 response.setCharacterEncoding("UTF-8");
			 out = response.getWriter();
			 String user = request.getParameter("YongHuShang").trim();  //�̼��û���
			
			 String CaiGouYuan = request.getParameter("CaiGuoYuan").trim();  //
			 ProductID = request.getParameter("ProductID").trim();  //��Ʒ����
			//�������ݿ�
		    Connection  con = new DBUtil().getConn();
			//��ȡ����
			ArrayList<String> ShangJian = new ArrayList<String>();
			ArrayList<String> number = new ArrayList<String>();
			ArrayList<String> PersonName = new ArrayList<String>();
			String sql_admin = "select * from denglu where Name = ? ";
			PreparedStatement ps_admin = con.prepareStatement(sql_admin);
			ps_admin.setString(1  , CaiGouYuan);
			ResultSet rs_admin = ps_admin.executeQuery();
			while(rs_admin.next()) {
				admin = rs_admin.getString("ContactPerson");
			}
			String sql1 = "select * from denglu where ZuMing = ? ";
			PreparedStatement ps1 = con.prepareStatement(sql1);
			ps1.setString(1  , ProductID);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()) {
				ShangJian.add(rs1.getString("Name"));
				number.add(rs1.getString("ContactNumber"));
				PersonName.add(rs1.getString("ContactPerson"));
			}
			System.out.println("�û�ѡ�����"+user);
			String user1 = "NULL";
			String sql10 = "select * from denglu where Name = ? ";
			PreparedStatement ps10 = con.prepareStatement(sql10);
			ps10.setString(1  , user);
			ResultSet rs10 = ps10.executeQuery();
			while(rs10.next()) {
				user1 = rs10.getString("ContactPerson");
			}
			 System.out.println("�û�ѡ�����"+user1);
			//Collections.shuffle(ShangJian);
			//��ȡ�ò�Ʒ�������������Ʒ
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

			ArrayList<ProductXinxi> zongjia = new ArrayList<ProductXinxi>();
			ProductXinxi px1 ;
			//�����ܼ�
			for (int i = 0 ;  i < ShangJian.size(); i++) {
				String sql = "SELECT sum(DanJia * XuQiuLiang) FROM infobiao where  YongHuMing = ? and LeiBie = ? ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1 ,ShangJian.get(i));
				ps.setString(2 , ProductID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					px1 = new ProductXinxi();
					px1.setZongJia(rs.getString("sum(DanJia * XuQiuLiang)"));
					zongjia.add(px1);
				}
			}
			String path = createPDF(ShangJian,Zhonglei,PersonName,userProduct,zongjia,number,user1);
			
			stringWaterMark(inputFile , "��ɽѧԺ");
			con.close();
			out.write("�������ɳɹ�");
			out.flush();
			out.close();
			


		}
		catch (Exception e) {
			e.printStackTrace();
			try{
			  	if (out!= null) {
					out.flush();  
					out.close();
					System.out.println("����pdfʧ�ܣ���");
				}
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public static String createPDF(ArrayList<String> ShangJian ,ArrayList<ProductXinxi> Zhonglei, ArrayList<String> PersonName,ArrayList<ProductXinxi> userProduct ,ArrayList<ProductXinxi> zongjia,ArrayList<String> number, String user2) throws Exception {
		 //��ȡ����ʱ��
		 Date now = new Date(); 
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//���Է�����޸����ڸ�ʽ
		 String heretime = dateFormat.format(now);  //����ʱ��
		 //���·��
		 String outPath = "pdf/"+heretime+"/";//DataUtil.createTempPath(".pdf");
		 try
		 {
				//����ֽ��
				Rectangle rect = new Rectangle(PageSize.A3);
			
				//�����ĵ�ʵ��
				Document doc=new Document(rect);
				
				//�����������
				//BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
				BaseFont bfChinese = BaseFont.createFont("pdf/Fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);          //����������ʽ
			   
				Font textFont = new Font(bfChinese,11,Font.NORMAL); //����
				Font redTextFont = new Font(bfChinese,11,Font.NORMAL,Color.RED); //����,��ɫ
				Font boldFont = new Font(bfChinese,11,Font.BOLD); //�Ӵ�
				Font redBoldFont = new Font(bfChinese,11,Font.BOLD,Color.RED); //�Ӵ�,��ɫ
				Font firsetTitleFont = new Font(bfChinese,22,Font.BOLD); //һ������
				Font secondTitleFont = new Font(bfChinese,15,Font.BOLD); //��������
				Font underlineFont = new Font(bfChinese,11,Font.UNDERLINE); //�»���
				File file = new File(outPath);
				if(!file.exists()) {  
					file.mkdirs(); 
				}  
				inputFile = file+"//"+ProductID;
				PdfWriter.getInstance(doc, new FileOutputStream(file+"/"+ProductID+".pdf"));				
				doc.open();
				doc.newPage();
				
				//����  
				Paragraph p1 = new Paragraph();  
				//����
				Phrase ph1 = new Phrase();  
				//��
				Chunk c1 = new Chunk("��Դ:", boldFont) ;
				Chunk c11 = new Chunk("��ɽѧԺ���ڼ������ƽ̨", textFont) ;
				//������ӵ�����
				ph1.add(c1);
				ph1.add(c11);
				//��������ӵ�����
				p1.add(ph1);
				//��������ӵ�����
				doc.add(p1);
				
				p1 = new Paragraph();  
				ph1 = new Phrase(); 
				Chunk c2 = new Chunk("�����ţ�", boldFont);
				Chunk c22 = new Chunk("HSXY-2017080140001", textFont) ;
				ph1.add(c2);
				ph1.add(c22);
				p1.add(ph1);
				doc.add(p1);
				
				p1 = new Paragraph("�ɹ�ƽ̨���ݱ���", firsetTitleFont);
				p1.setLeading(50);
				p1.setAlignment(Element.ALIGN_CENTER);
				doc.add(p1);


				p1 = new Paragraph("��ѧУ�棩", textFont);
				p1.setLeading(20);
				p1.setAlignment(Element.ALIGN_CENTER);
				doc.add(p1);
					
				p1 = new Paragraph();  
				p1.setLeading(20);
				p1.setAlignment(Element.ALIGN_CENTER);
				ph1 = new Phrase(); 
				Chunk c3 = new Chunk("��ѯʱ�䣺", boldFont) ;

				Chunk c33 = new Chunk(heretime, textFont) ;
				Chunk c4 = new Chunk(leftPad("��ѯ�ˣ�", 10), boldFont) ;
				Chunk c44 = new Chunk(admin+"���û���¼����", textFont) ;
				ph1.add(c3);
				ph1.add(c33);
				ph1.add(c4);
				ph1.add(c44);
				p1.add(ph1);
				doc.add(p1);
					
				p1 = new Paragraph("����˵��", secondTitleFont);
				p1.setLeading(50);
				p1.setAlignment(Element.ALIGN_CENTER);
				doc.add(p1);
				
				p1 = new Paragraph(" ");  
				p1.setLeading(30);
				doc.add(p1);
				
				p1 = new Paragraph();  
				ph1 = new Phrase(); 
				Chunk c5 = new Chunk("1.��������", textFont) ;
				Chunk c6 = new Chunk("��ɽѧԺ���ڼ�������ɹ�����ϵͳ", underlineFont) ;
				c6.setSkew(0, 30);
				Chunk c7 = new Chunk(" ���ߣ����ݽ�ֹ����ʱ��ɹ���Ա��Ϣ���ݿ��¼����Ϣ���ɡ��������ע�Ͳ�ѯ��¼�⣬�����е���Ϣ���ɻ�ɽѧԺ���ڼ�������ɹ�����ϵͳ�ṩ��", textFont);
				Chunk c8 = new Chunk("�����ṩ���������������ݱ�񣬱�֤����ʵ�Ժ�׼ȷ�ԣ�", textFont) ;
				ph1.add(c5);
				ph1.add(c6);
				ph1.add(c7);
				ph1.add(c6);
				ph1.add(c8);
				p1.add(ph1);
				doc.add(p1);


				p1 = new Paragraph();  
				ph1 = new Phrase(); 
				Chunk c9 = new Chunk("2.�����ע��", textFont) ;
				Chunk c10 = new Chunk(" �Ա����е���Ϣ��¼�����Ϣ����������˵����", textFont);
				ph1.add(c9);
				ph1.add(c6);
				ph1.add(c10);
				p1.add(ph1);
				doc.add(p1);
					
				p1 = new Paragraph("3.��Ϣ����˵������Ϣ����Ա��������ṩ����Ϣ��¼�����ļ�Ҫ˵����", textFont);  
				doc.add(p1);
				
				p1 = new Paragraph();  
				ph1 = new Phrase(); 
				Chunk c12 = new Chunk("4.��Ϣ������Ȩ�Ա������е�����������顣�������飬����ϵ����������Ҳ�ɵ�", textFont) ;
				Chunk c13 = new Chunk(" ����������롣", textFont);
				ph1.add(c12);
				ph1.add(c6);
				ph1.add(c13);
				p1.add(ph1);
				doc.add(p1);
				
				p1 = new Paragraph("5.������ѯ�����µ��������0559-2546720��", textFont);  
				doc.add(p1);
				
				p1 = new Paragraph("�ɹ�����", secondTitleFont);
				p1.setSpacingBefore(30);
				p1.setSpacingAfter(30);
				p1.setAlignment(Element.ALIGN_CENTER);
				doc.add(p1);
					  
						
				// ����һ�����  
				PdfPTable table;
				table = new PdfPTable((5+ShangJian.size()));
				float[] tablewidth = new float[(5+ShangJian.size())]; 
				for (int i = 0 ; i < (5+ShangJian.size()) ; i++){
					tablewidth[i] = 65;
				}
				table.setTotalWidth(tablewidth); //�����п�
				table.setLockedWidth(true); //�����п�
				
				//��ӱ�ͷ��Ϣ
				ArrayList<String> tabltop = new ArrayList<String>();
				tabltop.add("#");
				tabltop.add("��Ʒ");
				for (int i = 0 ; i < ShangJian.size() ;i++ ) {
					tabltop.add("�̼�");
				}
				tabltop.add("��λ");
				tabltop.add("������");
				tabltop.add("��λ");
				table = createCell(table, tabltop, PersonName,Zhonglei,ShangJian,userProduct, zongjia, number, (4+Zhonglei.size()), tabltop.size());

				doc.add(table);

                p1 = new Paragraph();  
				p1.setSpacingBefore(20);
				p1.setSpacingAfter(10);
				ph1 = new Phrase(); 
				Chunk sh = new Chunk("����ϵͳ�Ͳɹ���Ա�ľ��񣬴˴βɹ��̼�Ϊ��"+user2, redBoldFont);
				ph1.add(sh);
				p1.add(ph1);
				doc.add(p1);

				p1 = new Paragraph();  
				p1.setSpacingBefore(20);
				p1.setSpacingAfter(10);
				ph1 = new Phrase(); 
				Chunk c89 = new Chunk("ע���������Ǹ��ݲɹ���ѡ���������ģ��������ݴ����뼰ʱ���������", redBoldFont);
				ph1.add(c89);
				p1.add(ph1);
				doc.add(p1);
				doc.close();
				
				}
				 catch (Exception e) {
					 e.printStackTrace();
				}
				return outPath;
		}

		/**
		* ������Ԫ��
		* @param table
		* @param row
		* @param cols
		* @return
		* @throws IOException 
		* @throws DocumentException 
		*/
		private static PdfPTable createCell(PdfPTable table, ArrayList<String> title,ArrayList<String> PersonName ,ArrayList<ProductXinxi> Zhonglei,ArrayList<String> ShangJian,ArrayList<ProductXinxi> userProduct ,ArrayList<ProductXinxi> zongjia,ArrayList<String> number, int row, int cols) throws DocumentException, IOException{
			//�����������
			  //  BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			bfChinese = BaseFont.createFont("pdf/Fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);          //����������ʽ

			Font font = new Font(bfChinese,11,Font.BOLD);
			for(int j = 0; j < cols; j++){
				PdfPCell cell = new PdfPCell();
				if(title!=null){//���ñ�ͷ
					cell = new PdfPCell(new Phrase(title.get(j), font)); //������ͷ���ܾ���
					if(table.getRows().size() == 0){
					 cell.setBorderWidthTop(3);
					}
				}
				if(row==1 && cols==1){ //ֻ��һ��һ��
					cell.setBorderWidthTop(3);
				}

				if(j==0){//������ߵı߿���
					cell.setBorderWidthLeft(3);
				}

				if(j==(cols-1)){//�����ұߵı߿���
					cell.setBorderWidthRight(3);
				}
			   cell.setMinimumHeight(40); //���õ�Ԫ��߶�
			   cell.setUseAscender(true); //���ÿ��Ծ���
			   cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			   cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����

			   table.addCell(cell);
			}
	        for(int j = 0; j < cols; j++){
				PdfPCell cell = new PdfPCell();
				if(j>=2 && j < 2+PersonName.size()){//���ñ�ͷ
					cell = new PdfPCell(new Phrase(PersonName.get(j-2), font)); //������ͷ���ܾ���
					if(table.getRows().size() == 0){
					 cell.setBorderWidthTop(3);
					}
				}
			   cell.setMinimumHeight(20); //���õ�Ԫ��߶�
			   cell.setUseAscender(true); //���ÿ��Ծ���
			   cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			   cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����

			   table.addCell(cell);
			}
			for (int i = 0 ;  i < Zhonglei.size();i++ ) {
				String xuqiliang = "";
				PdfPCell cell = new PdfPCell();
				cell = new PdfPCell(new Phrase(Zhonglei.get(i).getId(), font));
				cell.setMinimumHeight(20); //���õ�Ԫ��߶�
			    cell.setUseAscender(true); //���ÿ��Ծ���
			    cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			    table.addCell(cell);
				PdfPCell cell2 = new PdfPCell();
				cell2 = new PdfPCell(new Phrase(Zhonglei.get(i).getShangPin(), font));
				cell2.setMinimumHeight(20); //���õ�Ԫ��߶�
			    cell2.setUseAscender(true); //���ÿ��Ծ���
			    cell2.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			    cell2.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			    table.addCell(cell2);
				for (int j = 0 ; j < ShangJian.size() ; j++ ) {
					for (int k = 0 ; k < userProduct.size() ;k++ ) {
                        if (userProduct.get(k).getShangPin().equals(Zhonglei.get(i).getShangPin()) && 
							ShangJian.get(j).equals(userProduct.get(k).getYongHuMing())) {
							PdfPCell cella = new PdfPCell();
							cella = new PdfPCell(new Phrase(userProduct.get(k).getDanJia(), font));
							cella.setMinimumHeight(20); //���õ�Ԫ��߶�
							cella.setUseAscender(true); //���ÿ��Ծ���
							cella.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
							cella.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
							table.addCell(cella);
							xuqiliang = userProduct.get(i).getXuQiuLiang();
                        }
					}
				}
				PdfPCell cellb = new PdfPCell();
				cellb = new PdfPCell(new Phrase(Zhonglei.get(i).getDanWei(), font));
				cellb.setMinimumHeight(20); //���õ�Ԫ��߶�
			    cellb.setUseAscender(true); //���ÿ��Ծ���
			    cellb.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			    cellb.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			    table.addCell(cellb);
				PdfPCell cellc = new PdfPCell();
				cellc = new PdfPCell(new Phrase(xuqiliang, font));
				cellc.setMinimumHeight(20); //���õ�Ԫ��߶�
				cellc.setUseAscender(true); //���ÿ��Ծ���
				cellc.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
				cellc.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
				table.addCell(cellc);
				PdfPCell celld = new PdfPCell();
				celld = new PdfPCell(new Phrase(Zhonglei.get(i).getDanWei(), font));
				celld.setMinimumHeight(20); //���õ�Ԫ��߶�
			    celld.setUseAscender(true); //���ÿ��Ծ���
			    celld.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			    celld.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			    table.addCell(celld);
			  
			}
			//��Ʒ�۸�
			PdfPCell cella = new PdfPCell();
			cella = new PdfPCell(new Phrase(String.valueOf(Zhonglei.size()+1), font));
			cella.setMinimumHeight(20); //���õ�Ԫ��߶�
			cella.setUseAscender(true); //���ÿ��Ծ���
			cella.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cella.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cella);
			PdfPCell cellz = new PdfPCell();
			cellz = new PdfPCell(new Phrase("�ܼ�", font));
			cellz.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellz.setUseAscender(true); //���ÿ��Ծ���
			cellz.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellz.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellz);
			for (int i = 0 ; i < zongjia.size() ;i++ ) {
				PdfPCell cellf = new PdfPCell();
				cellf = new PdfPCell(new Phrase(zongjia.get(i).getZongJia(), font));
				cellf.setMinimumHeight(20); //���õ�Ԫ��߶�
				cellf.setUseAscender(true); //���ÿ��Ծ���
				cellf.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
				cellf.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
				table.addCell(cellf);
			}
			PdfPCell cellb = new PdfPCell();
			cellb = new PdfPCell(new Phrase("Ԫ", font));
			cellb.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellb.setUseAscender(true); //���ÿ��Ծ���
			cellb.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellb.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellb);

			PdfPCell cellc = new PdfPCell();
			cellc = new PdfPCell(new Phrase("", font));
			cellc.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellc.setUseAscender(true); //���ÿ��Ծ���
			cellc.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellc.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellc);
			PdfPCell celld = new PdfPCell();
			celld = new PdfPCell(new Phrase("", font));
			celld.setMinimumHeight(20); //���õ�Ԫ��߶�
			celld.setUseAscender(true); //���ÿ��Ծ���
			celld.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			celld.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(celld);
			//�û��ĵ绰
			PdfPCell cellphone1 = new PdfPCell();
			cellphone1 = new PdfPCell(new Phrase(String.valueOf(Zhonglei.size()+2), font));
			cellphone1.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellphone1.setUseAscender(true); //���ÿ��Ծ���
			cellphone1.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellphone1.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellphone1);
			PdfPCell cellphone2 = new PdfPCell();
			cellphone2 = new PdfPCell(new Phrase("�绰����", font));
			cellphone2.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellphone2.setUseAscender(true); //���ÿ��Ծ���
			cellphone2.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellphone2.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellphone2);
			for (int i = 0 ; i < number.size() ; i++){
				PdfPCell cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Phrase(number.get(i), font));
				cell6.setMinimumHeight(20); //���õ�Ԫ��߶�
				cell6.setUseAscender(true); //���ÿ��Ծ���
				cell6.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
				cell6.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
				table.addCell(cell6);
			}
			PdfPCell cellphone3 = new PdfPCell();
			cellphone3 = new PdfPCell(new Phrase("", font));
			cellphone3.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellphone3.setUseAscender(true); //���ÿ��Ծ���
			cellphone3.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellphone3.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellphone3);
			PdfPCell cellphone4 = new PdfPCell();
			cellphone4 = new PdfPCell(new Phrase("", font));
			cellphone4.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellphone4.setUseAscender(true); //���ÿ��Ծ���
			cellphone4.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellphone4.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellphone4);
			PdfPCell cellphone5 = new PdfPCell();
			cellphone5 = new PdfPCell(new Phrase("", font));
			cellphone5.setMinimumHeight(20); //���õ�Ԫ��߶�
			cellphone5.setUseAscender(true); //���ÿ��Ծ���
			cellphone5.setHorizontalAlignment(Cell.ALIGN_CENTER); //����ˮƽ����
			cellphone5.setVerticalAlignment(Cell.ALIGN_MIDDLE); //���ô�ֱ����
			table.addCell(cellphone5);
			return table;
		}
		/**
		 * ������߾�
		 * @param str
		 * @param i
		 * @return
		 */
		public static String leftPad(String str, int i) {
			int addSpaceNo = i-str.length();
			String space = ""; 
			for (int k=0; k<addSpaceNo; k++){
					space= " "+space;
			};
			String result =space + str ;
			return result;
		 }
		 //�������ˮӡ
		 public static void stringWaterMark(String inputFile, String waterMarkName) {
			try {
				PdfReader reader = new PdfReader(inputFile+".pdf");
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(inputFile+"1.pdf"));
				int total = reader.getNumberOfPages() + 1;
				PdfContentByte under;
				PdfGState gs = new PdfGState();  
				int j = waterMarkName.length();
				char c = 0;
				int rise = 0;
				//��ÿһҳ��ˮӡ
				for (int i = 1; i < total; i++) {
					rise = 45;
					under = stamper.getUnderContent(i);
					gs.setFillOpacity(0.2f);  
					under.beginText();  
					under.setColorFill(Color.LIGHT_GRAY);  
					under.setFontAndSize(bfChinese, 50);  
					under.setTextMatrix(70, 200);  
					under.showTextAligned(Element.ALIGN_CENTER, "��ɽѧԺ���ڼ����ڲ��ļ�����ע�Ᵽ�ܣ�", 500,550, 55);
					// ���ˮӡ����
					under.endText();
				} 
				stamper.close();
				
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		 }

}