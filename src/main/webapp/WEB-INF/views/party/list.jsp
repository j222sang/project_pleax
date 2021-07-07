<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- html의 영역, 그간의 고생을 끝내고 이제 Sql의 Data를 직접 web에서 형식을 만들어서 눈으로 확인 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이곳은 살아있는 지옥이지</title>
</head>
<body>

<table border="2px">
<caption>Party 목록</caption>
	<tr>
	<th>아이디</th>
	<th>이름</th>
	<th>생년월일</th>
	<th>성별</th>
	<th>등록일</th>
	<th>수정일</th>
	</tr>
	<tr>
		<th>유형</th>
		<th>연락처</th>
		<th>등록일</th>
		<th>수정일</th>
	</tr>
	
	<c:forEach items="${listParty}" var="party">
		<tr>
			<td>${party.userId}</td>
			<td>${party.name}</td>
			<td>${party.birthDate}</td>
			<td>${party.male}</td>
			<td>${party.registrationDate}</td>
			<td>${party.updateDate}</td>
		</tr>
		<c:forEach items="${party.listContactPoint}" var="cp">
		<tr>
			<td>${cp.contactPointType}</td>
			<td>${cp.info}</td>
			<td>${cp.registrationDate}</td>
			<td>${cp.updateDate}</td>
		</tr>
		</c:forEach>
	</c:forEach>
	
</table>
</body>
</html>