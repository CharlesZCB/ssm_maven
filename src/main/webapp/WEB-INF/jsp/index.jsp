<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%
        pageContext.setAttribute("path",request.getContextPath());
    %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/static/css/bootstrap-theme.css">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <script type="text/javascript" src="${path }/static/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="${path }/static/js/bootstrap.js"></script>
</head>
<body>
<h4>Welcome,all the users!<h5><a href="${path}">添加</a></h5></h4>
<table>
    <thead>人员名单</thead>
    <tr>
        <td><span class="glyphicon glyphicon-user">姓名</span></td>
        <td><span class="glyphicon glyphicon-paperclip">年龄</span></td>
        <td><span class="glyphicon glyphicon-tent">性别</span></td>
        <td><span class="glyphicon glyphicon-wrench">操作</span></td>
    </tr>
    <c:forEach var="user" items="${pageInfo.list}">
        <tr>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <c:if test="${user.sex==1}">
                <td>男</td>
            </c:if>
            <c:if test="${user.sex==0}">
                <td>女</td>
            </c:if>
            <td><a href="${path}/user/deleteone?id=${user.id}">删除</a>&nbsp;<a>修改</a></td>
        </tr>
    </c:forEach>
</table>
<div class="row" >
    <!-- 分頁文字信息 -->
    <div class="col-md-4">
        当前第：<font  size="3">${pageInfo.pageNum }</font>页，总共：<font  size="3">${pageInfo.pages }</font>页，总

        共：<font  size="3">${pageInfo.total}</font>条记录
    </div>
    <!-- 分頁條信息 -->
    <div class="col-md-5">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li><a href="${path }/user/listall?pn=1">首页</a></li>
                <c:if test="${pageInfo.hasPreviousPage }">
                    <li>
                        <a href="${path }/user/listall?pn=${pageInfo.pageNum-1 }" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach items="${pageInfo.navigatepageNums }" var="pagenum">
                    <c:if test="${pagenum==pageInfo.pageNum }">
                        <li  class="active"><a href="#">${pagenum }</a></li>
                    </c:if>
                    <c:if test="${pagenum!=pageInfo.pageNum }">
                        <li><a href="${path }/user/listall?pn=${pagenum }">${pagenum }</a></li>
                    </c:if>
                </c:forEach>
                <c:if test="${pageInfo.hasNextPage }">
                    <li>
                        <a href="${path }/user/listall?pn=${pageInfo.pageNum+1 }" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>

                </c:if>

                <li><a href="${path }/user/listall?pn=${pageInfo.pages }">尾页</a></li>
            </ul>
        </nav>
    </div>
</div>
</div>
</body>
</html>