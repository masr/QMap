<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>QMap---PassionCoder</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		
	<style>
	#submit{display:block;margin-left:200px;margin-top:10px;}
	#simple_map img{width:800px;}
	#simple_map {margin-top:20px;}
	</style>
</head>
<body>
<div id="outer">
<div id="wrapper">
<div id="header">
<h1><a href="#"></a></h1>
</div>
<div id="nav">
<ul>
	<li><a href="index.html">Home</a></li>
	<li><a href="shapefile.jsp">ERIS ShapeFile</a></li>
	<li><a href="#">Contact Us</a></li>
	<li><a href="#">About Us</a></li>
</ul>
</div>
<div id="left_column">
<h2>Shape File Process</h2>
				


	<div>
		<form name="form" action="ShapeFile" method="post" enctype="multipart/form-data">
		<p>Please upload your .shp file</p>
		<input id="shp_file_upload_input" type="file" size="45" name="shp_file" class="input" />
			<p>Please upload your .dbf file</p>
		<input id="dbf_file_upload_input" type="file" size="45" name="dbf_file" class="input" />

		<input type="submit" value="Submit" id="submit"></input>
		</form>
	</div>

<% 
String imgId=request.getParameter("imgId");
if (imgId!=null){ %>
<div id="simple_map">
<h2> Please right click and save the image.</h2>
<img src="<%="ShapeFile?imgId="+imgId %>"></img></div>
</div>
<%} %>
<div class="page_bottom"></div>
<div id="footer">Generated By <a href="#">PassionCoder</a></div>
</div>
</div>
</body>
</html>