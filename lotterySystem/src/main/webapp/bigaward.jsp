<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/bigaward.css" />
<link rel="shortcut icon" href="images/favicon-aaxis.png" type="image/png" />
<script type="text/javascript" src="scripts/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="scripts/plugins/bigaward.js"></script>
<title>大奖</title>
</head>
<body>
	<div>
		<table class="bigaward">
			<thead>
				<tr>
					<th colspan="11">this is <%=session.getAttribute("group")%> group (<%=session.getAttribute("lowId")%>~~~<%=session.getAttribute("highId")%>)</th>
				</tr>
				<tr id="trThead">
					<th>A~E/1~10</th>
					<th scope="col" abbr="ont">1</th>
					<th scope="col" abbr="tow">2</th>
					<th scope="col" abbr="three">3</th>
					<th scope="col" abbr="four">4</th>
					<th scope="col" abbr="Starter">5</th>
					<th scope="col" abbr="Starter">6</th>
					<th scope="col" abbr="Starter">7</th>
					<th scope="col" abbr="Starter">8</th>
					<th scope="col" abbr="Starter">9</th>
					<th scope="col" abbr="Starter">10</th>
				</tr>
			</thead>
			
			<tbody id="tbody">
					<tr>
						<th scope="row">A</th>
						<c:forEach  items='${sessionScope.users}' begin="0" end="9" var="user">
						 <c:choose>
						 	<c:when test='${user.prizeType eq "bigAward"}'>
							 <td style="background-color: red;">${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:when>
						 	<c:otherwise>
							 <td>${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:otherwise>
						 </c:choose>
						</c:forEach>
					</tr>
				
				<tr>
					<th scope="row">B</th>
					<c:forEach  items='${sessionScope.users}' begin="10" end="19" var="user">
						 <c:choose>
						 	<c:when test='${user.prizeType eq "bigAward"}'>
							 <td style="background-color: red;">${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:when>
						 	<c:otherwise>
							 <td>${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:otherwise>
						 </c:choose>
						</c:forEach>
				</tr>
				
				<tr>
					<th scope="row">C</th>
				<c:forEach  items='${sessionScope.users}' begin="20" end="29" var="user">
						 <c:choose>
						 	<c:when test='${user.prizeType eq "bigAward"}'>
							 <td style="background-color: red;">${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:when>
						 	<c:otherwise>
							 <td>${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:otherwise>
						 </c:choose>
						</c:forEach>
				</tr>
				
				<tr>
					<th scope="row">D</th>
					<c:forEach  items='${sessionScope.users}' begin="30" end="39" var="user">
						  <c:choose>
						 	<c:when test='${user.prizeType eq "bigAward"}'>
							 <td style="background-color: red;">${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:when>
						 	<c:otherwise>
							 <td>${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:otherwise>
						 </c:choose>
						</c:forEach>
				</tr>
				
				<tr>
					<th scope="row">E</th>
					<c:forEach  items='${sessionScope.users}' begin="40" end="49" var="user">
						  <c:choose>
						 	<c:when test='${user.prizeType eq "bigAward"}'>
							 <td style="background-color: red;">${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:when>
						 	<c:otherwise>
							 <td>${user.id}<br/>${user.englishName}<br/>${user.chineseName}</td>
						 	</c:otherwise>
						 </c:choose>
						</c:forEach>
				</tr>
				
			</tbody>
		</table>
		<div class="show-award">
			<div id="showAward" class="show-name">
			</div>
		</div>
	</div>
</body>
</html>