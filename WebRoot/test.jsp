<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/androidInterface/androidLogin" method="post">
		用户名：<input name="username" /><br/>
		密码：  <input name="password"/><br/>
		法人：<input name="cpny_id"/><br/>
		语言：<input name="lang"/><br/>
		<input type="hidden" name="key" value="EHR_SD_ANDRIOD"><br/>
		<input type="submit" value="登陆">
	</form>
</body>
</html>