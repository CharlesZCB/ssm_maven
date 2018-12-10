<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<%
    pageContext.setAttribute("path",request.getContextPath());
%>
<body>
<h2>Hello World! Mr Maven</h2>
<br>
<form action="${path}/user/insert" method="post">
    <p>name: <input type="text" name="name" /></p>
    <p>age: <input type="text" name="age" /></p>
    <p>sex:
        <input type="radio" name="sex" value='1'/><label>男</label>
        <input type="radio" name="sex" value='0'/><label>女</label>
    </p>

    <input type="submit" value="提交" />
</form>
<h6><a href="${path}/user/listall">查询所有</a></h6>
</body>
</html>
