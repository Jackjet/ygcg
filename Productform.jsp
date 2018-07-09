<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
 <html>
	 <head>
	     <title>�����б�ϵͳ</title>
		 <meta name="renderer" content="webkit|ie-comp|ie-stand,initial-scale=1"">
		 <meta http-equiv="Content-Type" content="text/html; charset=GB2312">
		 <meta http-equiv="X-UA-Compatible" content="IE=edge">
		 <meta name="viewport" content="width=device-width, initial-scale=1">
		 <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		 <link rel="stylesheet" href="bootstrap/css/awesome-bootstrap-checkbox.css">
		 <link rel="stylesheet" href="bootstrap/Font-Awesome/css/font-awesome.min.css"/>
         <link rel="stylesheet" href="bootstrap/css/build.css"/>
         <script src="js/jquery-2.1.4.min.js"></script>
		 <script src="js/jquery.min.js"></script>
		 <script src="bootstrap/js/bootstrap.min.js"></script>
		 <script language="javascript" src="js/LodopFuncs.js"></script>
		 <script src="bootstrap/js/bootstrap.js"></script>
		 <script src="js/jquery.cookie.js"></script>
		 <script src="js/printThis.js"></script>
		 <script language="javascript" src="js/jquery.jqprint.js"></script>
		 <style type="text/css">
			body {
				background-image:url(image/bg-yellow.png);
				width:100%;
				height:100%;
				background-size:100% 100%;
			}
			 .table th, .table td { 
				text-align: center; 
				vertical-align: middle!important;
			}
		 </style>
		  <script type="text/javascript">
			 $(function(){
				//��ť����ʱִ��
				$("#table-value").click(function(){
					var account =$("#stuno").val();
					var text ;
					var True = 1 ;
					for (var i = 0 ; i < account ; i++ ) {
						if ($("#saleNumber_"+i).val() == "") {
							True = 0;
						}
						text = "saleNumber_"+i+"="+$("#saleNumber_"+i).val()+"&"+text;
					}
					if (True == 0 ) {
						 alert("��ǰ����������Ϊ�����");
                         return ;
					}
					else {
						//Ajax���ô���
						$.ajax({
							data:text+"&stuno="+account+"&username=${username}&ProductID=${ProductID}",
							type:"post",
							contentType: "application/x-www-form-urlencoded; charset=GB2312",
							url:" tablevalue.do",
							dataType:"json",
							error:function(data){ 
							   alert("�����ˣ���:"); 
							}, 
							success:function(data){
								if (data != null) {
									 var str = ""; 
									 for (var i = 0 ; i < data.length ;i++ ){
										$("#zj_"+i).text(data[i]);
									 }
									
								}
							} 

						});
					}
				 });
			});
		 </script>
		 <script type="text/javascript">
			 $(function(){
				//��ť����ʱִ��
				$("#fat-btn").click(function(){
					var obj = document.getElementById("selectzhi"); //��λid
					var index = obj.selectedIndex; // ѡ������
					var text = obj.options[index].text; // ѡ���ı�
					//Ajax���ô���
					alert(text); 
					$.ajax({
						data:"YongHuShang="+text+"&CaiGuoYuan=${username}&ProductID=${ProductID}",
						type:"POST",
						contentType: "application/x-www-form-urlencoded; charset=GB2312",
						url:"YongHuShang.do",
						error:function(data){ 
						   alert("�����ˣ���:"); 
						}, 
						success:function(data){
							if (data != null) {
								 alert(data);
							}
						} 

					});
				 });
			});
		 </script>
		 <script type="text/javascript">
			 $(function(){
				//��ť����ʱִ��
				$("#table-but").click(function(){
					var obj = document.getElementById("selectzhi"); //��λid
					var index = obj.selectedIndex; // ѡ������
					var text = obj.options[index].text; // ѡ���ı�
					//Ajax���ô���
					$.ajax({
						data:"YongHuShang="+text+"&CaiGuoYuan=${username}&ProductID=${ProductID}",
						type:"POST",
						contentType: "application/x-www-form-urlencoded; charset=GB2312",
						url:"PDFUtil.do",
						error:function(data){ 
						   alert("�����ˣ���:"); 
						}, 
						success:function(data){
							if (data != null) {
								 alert(data);
							}
						} 

					});
				 });
			});
		</script>
	
		<!-- <script>
                  function onlynum(text)
                 {
                    if(text == "" || text== null){
                     alert('Ϊ�գ�����д���֣���');
		 
                    }
	            return true;
                }
	       </script>
	   
	   <script type="text/javascript">
            function myCheck()
            {
               for(var i=0;i<document.form.elements.length-10;i++)
               {
                  if(document.form.elements[i].value=="")
                  {
                     alert("��ǰ����������Ϊ�����");
                     document.form.elements[i].focus();
                     return false;
                  }
               }
              return true;
            }
        </script>
		-->
	</head>
	<body>
	
		<div class="row">
			<div class="row">
				 <div class="jumbotron" style=" text-align:center; height:150px; " >
					<h1>�����б�����</h1> 
				 </div>
			</div>
			<div class = "row">
				<div class="row">
					<table class =" nav nav-list well  table table-hover " align="center" border="2"
					   value = "" contenteditable="false" >
						<thead>
						   <tr>
								<th>#</th>
								<th><i class = "glyphicon glyphicon-shopping-cart"></i>��Ʒ</th>
								<c:forEach var="shangjia" items="${sjlist}">
									<th><i class = "glyphicon glyphicon-user"></i>�̼�</th>
								</c:forEach>
								<th><i class = "glyphicon glyphicon-inbox">��λ</th>
								<th><i class = "glyphicon glyphicon-plus">������</th>
								<th><i class = "glyphicon glyphicon-yen">��λ</th>
						   </tr>
						</thead>
						<tbody>
							<tr class = "active">
								<td></td>
								<td></td>
								<c:forEach var="personname" items="${PersonName}">
									<td>${personname}(${has.get(personname)})</td>
								</c:forEach>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<c:set var="count" value="0"/>
							<c:forEach var="zl" items="${zllist}">
								<tr class = "active">
									<td>${zl.id}</td>
									<td>${zl.shangPin}</td>
									<c:choose>
										<c:when test="${listproduct.size() == 0 }">
											<c:forEach var="shangjia" items="${sjlist}">
											   <td>0</td>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="shangjia" items="${sjlist}">
												
												<c:forEach var="me" items="${listproduct}">
													<c:if test="${me.shangPin.equals(zl.shangPin) && 
													  shangjia==me.yongHuMing}">
														<td>${me.danJia}</td>
													</c:if> 
												</c:forEach>
											</c:forEach>
									   </c:otherwise>
									</c:choose>
									<td>${zl.danWei}</td>
									<td class="text-center">
									   <center>
											<input onBlur="onlynum(this.value)"
											 type="text" size="16" class="form-control" style="width:100px"   
											 name="saleNumber_${count}" id = "saleNumber_${count}"
											 placeholder="�� ��" value ="${listxuq.get(count)}"
											 onkeyup=
												"if(this.value.length==1){
													this.value=this.value.replace(/[^0-9]/g,'')
												}else{
												   this.value=this.value.replace(/\D/g,'')
												}"onafterpaste="if(this.value.length==1){
													this.value=this.value.replace(/[^0-9]/g,'')
												}else{
												   this.value=this.value.replace(/\D/g,'')
												}">
									   </center>
									</td>
									<td>${zl.danWei}</td>
								</tr>
								<c:set var="count" value="${count+1}"/>
							</c:forEach>
							<tr>
								<td><i class ="glyphicon glyphicon-yen"></td>
								<td>�ܼ�</td>
								 <c:set var="cou" value="0"/>
								 <c:forEach var="shangjia" items="${sjlist}">
									<th id = "zj_${cou}">0</th>
								 <c:set var="cou" value="${cou+1}"/>
								 </c:forEach>
								<td>Ԫ</td>
								<form role="form">
									<td>
										<center>
										<div class="form-group" style="width:100px">
											<select class="form-control" id = "selectzhi" name = "selectzhi">
												 <c:forEach var="shangjia" items="${sjlist}">
													 <option value = "" >${shangjia}</option>
												 </c:forEach>
											</select>
										</div>
										</center>
									 
									</td>
									<td>
										<button type="button" id="fat-btn" class="btn btn-primary">
										   ȷ���̼�
										</button>
									</td>
								</form>
							</tr>	
							<tr>
								<td><i class = "glyphicon glyphicon-phone"></td>
								<td>�绰����</td>
								<c:forEach var="number" items="${number}">
									<td>${number}</td>
								</c:forEach>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class = "container" style="height:150px;">
					<div class="row">
					   <div class="col-xs-4">
							  <input type="hidden" name="stuno" id="stuno" value="${count}">
							  <input type="hidden" name="ProductID" id="ProductID" value="${ProductID}">
							  <input type="hidden" name="zl" id="zl" value="${zllist}">
							  <input type="hidden" name="username" id="username" value="${username}">
							  <button type="button" class = "btn btn-large btn-block btn-primary
							   btn btn-primary btn-lg " onclick="return myCheck()"  id = "table-value">
									�����ܼ�
							  </button>
					   </div>
					   <div class="col-xs-4">
						  <button type="button"  class ="btn btn-large btn-block btn-primary
						   btn btn-primary btn-lg " id="table-but">
							   ���ɱ���
						  </button>
					   </div>
					   <div class="col-xs-4">
							<button type="button" class ="btn btn-large btn-block btn-primary
							  btn btn-primary btn-lg "   onclick="window.open('pdf.jsp?path=${ProductID}')"> 
							 �������
						   </button>
					   </div>
				</div>
			</div>
		</div>
		<div id="footer" class="container">
			<nav class="navbar navbar-default navbar-fixed-bottom">
				<div class="navbar-inner navbar-content-center">
					<p class="text-muted credit" style="padding: 8px ;background: #333 ; text-align:center ;">
						COPYRIGHT @ 2016 - 2017 ��ɽѧԺ����С������з� ALL RIGHTS RESERVED
						��ӭ����ʹ��
					</p>
				</div>
			</nav>
	    </div>
   </body>
</html>