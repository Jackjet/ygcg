<%@ page language="java" import="java.util.*" contentType="text/html; charset=GB2312" %> 
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title> ��ʷ��ѯ </title>
  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
   <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
   <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script>
  function display(){
		var date=document.getElementById("cp").value;
		alert(date);
}
  </script>
 </head>
<%
String zh= (String)session.getAttribute("zh");
String f1=(String)session.getAttribute("bm");
String bm=f1.substring(0,f1.length()-1);
int i=0;
%>
<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
	 url="jdbc:mysql://localhost:3306/hsuygcg?useUnicode=true&characterEncoding=utf-8"
     user="root"  password="123456"/>
<sql:query dataSource="${snapshot}" var="result" sql="SELECT * from danwei where zhonglei=?">
 <sql:param value="<%=zh%>" />
</sql:query>
 <body>
	<h1>
		<p align="center">
			��ҳ�����ڲ�ѯͬ�б���
		</p>
	</h1>
	<div>
	<form action="THLS" method="POST" id="form1">
		<p>�����������Ҫ���ҵĶ�������,Ȼ���������˵����ҵ������ѯ�Ĳ�Ʒ,Ȼ����ȷ����ť���ɲ�ѯͬ�б���</p>
		<p align="center">��ѡ������: <input type="date" id="date" name="sj" class="form-control" style="width:9%"/></p>
		<div align="center">��ѡ������: 
		
						
						<select style=" width:155px;" size="1" id="cp" name="cp" class="form-control" >
							<c:forEach var="row" items="${result.rows}">
							  <option value="<c:out value='${row.name}'/> "><c:out value='${row.name}'/></option>   
							  <%i+=1;%>
							</c:forEach>
						</select>
						
		</div>
		<input type="hidden" id="zh" name="zh"  class="form-control" style="width:10%" value="<%=zh%>">
		<input type="hidden" id="bm" name="bm"  class="form-control" style="width:10%" value="<%=bm%>">
		<input type="hidden" id="jl" name="jl"  class="form-control" style="width:10%" value="day">
		</br></br>
		<div align="center">
			<input type="submit"   class="btn btn-info btn-lg btn-block" value="ȷ��" style="width:50%"></button>
		</div>
	</div>
	</form>
</br></br>

 </body>
</html>
