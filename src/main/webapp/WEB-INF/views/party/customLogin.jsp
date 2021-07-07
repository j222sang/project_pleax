<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log-In Start</title>

</head>
<body>
	<h1>Custom Login Page</h1>
	<h2>${error}</h2>
	<h2>${logout}</h2>
	
	<form method='post' action="/login" role="form">
		<fieldset>
			<div class='form-group'>
				<input type="text" name='username' class='form-control' autofocus>
			</div>
			<div class='form-group'>
				<input type="password" name='password' class='form-control'>
			</div>
			<div class='checkbox'>
				<label><input type="checkbox" name='remember-me'>자동로그인</label>
			</div>
		</fieldset>
		<input type="submit">
		<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>
	</form>
</body>
</html>