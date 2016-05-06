<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Saved Contracts - Debug View</title>
<style type="text/css">
a { margin-left: 10px; }
</style>
</head>
<body>
<h1>Saved ${label}:</h1>

<ol>
	<c:forEach var="id" items="${ids}">
		<li>
		${id}
		<a href="${id}.xml">[XML]</a>
		<a href="documents/${id}">[PDF]</a>
		<a href="documents/original/${id}">[original PDF]</a>
		</li>
	</c:forEach>
</ol>
</body>
</html>