<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="MultipartRequest사용 파일 업로드 처리"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#wrap{ margin:0px auto; width:800px; height:860px; }
#header{  width:800px; height:140px; background: #FFFFFF url(http://localhost:8080/jsp_prj/common/images/header_bg.png) repeat-x;
			position: relative;	}
#headerTitle{ font-family: HT견고딕,고딕; font-size: 30px; font-weight: bold;text-align: center;
				position:absolute; top: 40px;left: 290px; }
#container{  width:800px; height:600px; }
#footer{  width:800px; height:120px; }
#footerTitle{ float: right;font-size: 15px; padding-top: 20px; padding-right: 20px }

</style>
</head>
<body>
<div id="wrap">
	<div id="header">
	<div id="headerTitle">SIST Class4 </div>
	</div>
	<div id="container">
	<%
	//1. 생성- 파일 업로드 실행
	int maxSize=1024*1024*10;
	
	File repository = new File("C:/dev/workspace/jsp_prj/WebContent/upload");
	
	MultipartRequest mr= new MultipartRequest(request, repository.getAbsolutePath(), maxSize, "UTF-8", new DefaultFileRenamePolicy());
	
	//2. 파라메터의 처리
	String name= mr.getParameter("uploader");
	String age= mr.getParameter("age");
	
	//3. 업로드 된 파일명 받기
	//DefaultFileRenamePolicy에 의해 변경된 이름이 될수도 있음.
	String fileName= mr.getFilesystemName("upfile");
	//4. 원본 파일명 받기
	String originalfileName= mr.getOriginalFileName("upfile");
	%>
	<div>
		<strong>파일업로드 성공</strong>
		<ul>
			<li>업로더 : <%=name %></li>
			<li>연령대 : <%=age%></li>
			<li>업로드된 파일명 : <%=fileName%></li>
			<li>원본 파일명 : <%=originalfileName%></li>
			<li><a href="mr_upload_form.jsp">업로드폼</a></li>
			<li><a href="../day0319/file_list.jsp">파일리스트</a></li>
		</ul>
	</div>
	
	
	</div>
	<div id="footer">
		<div id="footerTitle">copyright&copy; all right reserved. class 4</div>
	</div>
</div>
</body>
</html>








