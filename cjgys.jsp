<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title> ����Ȩ�� </title>
  <meta name="Generator" content="EditPlus">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
 </head>

 <body>
	<h1>
		<p align="center">
			ѡ������Ҫ��õ�Ȩ�ޡ�
		</p>
	<h1>
	<div>
		<%
		String zh="3001";
		String mima="123456";
		String zm="����";
		String bm="DongHuo1";
		session.setAttribute("pa",mima);
		session.setAttribute("zh",zm);
		session.setAttribute("bz",zh);
		session.setAttribute("bm",bm);
		%>
		<p>
			<input type="button" id="btn1" name="btn1" value="������" onClick="window.open('DL.jsp')">
			<input type="button" id="btn2" name="btn2" value="�ɹ���" onClick="window.open('index.jsp')">
			<input type="button" id="btn3" name="btn3" value="�ලԱ" onClick="window.open('/Online_E_System/index.jsp')">
			<input type="button" id="btn4" name="btn4" value="����Ա" onClick="window.open('/admin/index.jsp')">
		</p>
		
	</div>
 </body>
</html>
