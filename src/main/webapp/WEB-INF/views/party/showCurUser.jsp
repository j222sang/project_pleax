<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>현 사용자는 : CustomUser <sec:authentication property="principal"/></p>
<p>Party <sec:authentication property="principal.curUser"/></p>
<p>현 사용자는 이름은 <sec:authentication property="principal.curUser.name"/></p>
<p>로그인 아이디는 <sec:authentication property="principal.username"/></p>
</body>
</html>