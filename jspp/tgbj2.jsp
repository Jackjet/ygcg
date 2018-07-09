<%@ page language="java" import="java.util.*" contentType="text/html; charset=GB2312" %> 
<%@ page import="java.io.*,java.util.*,java.util.regex.Matcher,java.util.regex.Pattern,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
   <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
   <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>����ϵͳ</title>
<style>
#nav {
    
    float:left;
    width:170px;
	margin-left:100px;
}
#section {
    padding:30px; 
	margin-left:10px;
}
#footer {
    background-color:#66CCFF;
    color:white;
    clear:both;
    text-align:center;
    padding:25px; 
	width:100%;
	position:absolute;
	bottom:0px;
}
</style>
<script>
function myCheck()
{  
	var max=document.getElementById("cpsl").value;
	for(var i=0;i<max;i++){
		var x=document.getElementById(i+"count1").value;
		var y=document.getElementById(i+"count2").value;
		if(isNaN(x)){
			window.alert("����������!");
			return false;
		}
		if(x<0)
		{
			window.alert("��������Ϊ��!");
			return false;
		}
		if(x==0||x==null)
		{
			window.alert("������������е���δ��д!");
			return false;
		}
		if(isNaN(y)){
			window.alert("����������!");
			return false;
		}
		if(y<0)
		{
			window.alert("���۲���Ϊ��!");
			return false;
		}
		if(y==0||y==null)
		{
			window.alert("�����������������δ��д!");
			return false;
		}
	}
	window.alert("�ύ�ɹ�!");
	return true;
}

</script>
</head>

<body>
<%
String zh= (String)session.getAttribute("zh");
String bz= (String)session.getAttribute("bz");
String hm1=bz.substring(bz.length()-1, bz.length());
String regEx="[^0-9]";  
Pattern p = Pattern.compile(regEx);  
Matcher m = p.matcher(hm1);  
String hm=m.replaceAll("").trim();
int i=0;
String f1=(String)session.getAttribute("bm");
String bm=f1.replaceAll("([1-9]+[0-9]*|0)(\\.[\\d]+)?", "");
%>
<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
	 url="jdbc:mysql://localhost:3306/hsuygcg?useUnicode=true&characterEncoding=utf-8"
     user="root"  password="123456"/>
<sql:query dataSource="${snapshot}" var="result" sql="SELECT * from danwei where zhonglei=?">
 <sql:param value="<%=zh%>" />
</sql:query>
<ul id="myTab" class="nav nav-tabs">
	<li class="active"><a href="#home" data-toggle="tab" onclick="display()"><%=zh%></a></li>
	<li style="float:right">
	<ol class="breadcrumb" style="float:right" >
		<li  class="active" id="year">year</li>
		<li  class="active" id="month">monthname[month]+"��"</li>
		<li  class="active" id="day">date + "��"</li>
		<li  class="active" id="xq">���� + "x"</li>
	</ol>
	  <script>		
			<!--
			var calendar = new Date();
			var day = calendar.getDay();
			var month = calendar.getMonth();
			var date = calendar.getDate();
			var year = calendar.getYear();
			year = 1900 + year;
			cent = parseInt(year/100);
			g = year % 19;
			k = parseInt((cent - 17)/25);
			i = (cent - parseInt(cent/4) - parseInt((cent - k)/3) + 19*g + 15) % 30;
			i = i - parseInt(i/28)*(1 - parseInt(i/28)*parseInt(29/(i+1))*parseInt((21-g)/11));
			j = (year + parseInt(year/4) + i + 2 - cent + parseInt(cent/4)) % 7;
			l = i - j;
			emonth = 3 + parseInt((l + 40)/44);
			edate = l + 28 - 31*parseInt((emonth/4));
			emonth--;
			var dayname = new Array ("������", "����һ", "���ڶ�", "������", "������", "������", "������");
			var monthname =
			new Array ("1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��" );
			document.getElementById("year").innerHTML=year;
			document.getElementById("month").innerHTML=monthname[month];
			document.getElementById("day").innerHTML=date + "��";
			document.getElementById("xq").innerHTML=dayname[day];
	 	-->
</script></li>
</ul>
<div class="tab-pane fade in active">
		<form action="InsertYGCG"  method="POST" id="form1" target="hiddenFrameName" onsubmit="return myCheck()">
		<p>�����ǽ���ʳ����Ҫʳ��,���ڹ涨ʱ������д����,���һ���ύΪ׼!
		<input type="hidden"  name="hm" value="<%=hm%>" style="width:3%" />
		<input type="hidden"  name="bm" value="<%=bm%>" style="width:3%" /></p>
		<div class="panel panel-info">
			<div class="panel-heading">
				<h3 class="panel-title">�ҵĶ���</h3>
			</div>
			<table class="table" id="tb1">
				<th>��Ʒ</th><th>����</th><th>����</th><th>��λ</th><th>��ע</th>
					<c:forEach var="row" items="${result.rows}">
				<tr>
					<td>
						<c:out value='${row.name}'/>
					   <input type="hidden" id="<%=i%>count" name="<%=i%>count" class="form-control" style="width:30%" value=" <c:out value='${row.name}'/>" readonly= "true" >
					</td>
					<td><input type="text" id="<%=i%>count1" name="<%=i%>count1" class="form-control" style="width:30%" value="0"></td>
					<td><input type="text" id="<%=i%>count2" name="<%=i%>count3"  class="form-control" style="width:30%" value="0" ></td>
					<td><input type="text" id="<%=i%>count3" name="<%=i%>count2"  class="form-control" style="width:30%" value=" <c:out value='${row.danwei}'/> "  readonly= "true"></td>
					<td><input type="text" id="<%=i%>count4" name="<%=i%>count4"  class="form-control" style="width:30%" value=" <c:out value='${row.beizhu}'/> "  readonly= "true"></td>
				</tr>
<%
 i+=1;
%>
				</c:forEach>
			</table>
		</div>
</div>
 
<input type="hidden" id="cpsl" name="cpsl" value="<%=i%>"/></input>

		<div class="container">
		<input type="submit"  class="btn btn-info btn-lg btn-block" ></input>
		</div>
		</form>
			

	</div>	
</div>
</body>
</html>
