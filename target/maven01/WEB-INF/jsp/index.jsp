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
<h4>Welcome,all the users!</h4>
<h5><a href="${path}"><span class="glyphicon glyphicon-plus"></span>添加</a></h5>
<table class="table table-striped">
    <thead class="modal-header">人员名单</thead>
    <tr>
        <td><span class="glyphicon glyphicon-wrench">ID</span></td>
        <td><span class="glyphicon glyphicon-user">姓名</span></td>
        <td><span class="glyphicon glyphicon-paperclip">年龄</span></td>
        <td><span class="glyphicon glyphicon-tent">性别</span></td>
        <td><span class="glyphicon glyphicon-wrench">操作</span></td>
    </tr>
    <c:forEach var="user" items="${listuser}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <c:if test="${user.sex==1}">
                <td>男</td>
            </c:if>
            <c:if test="${user.sex==0}">
                <td>女</td>
            </c:if>
            <td>
                <a href="${path}/user/deleteone?id=${user.id}">
                    删除
                </a>&nbsp;
            </td>
        </tr>
    </c:forEach>
</table>


    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:if test="${pn-1> 0}">
                <li>
                    <a href="${path}/user/listall?pn=${pn-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>

            <c:forEach begin="${startpage}" end="${endpage}" var="pagenum">
                <c:if test="${pn == pagenum}">
                    <li class="active"><a href="${path}/user/listall?pn=${pagenum}">${pagenum}</a></li>
                </c:if>
                <c:if test="${pn != pagenum}">
                    <li><a href="${path}/user/listall?pn=${pagenum}">${pagenum}</a></li>
                </c:if>
            </c:forEach>

            <c:if test="${pn+1 < pageTotal}">
                <li>
                    <a href="${path}/user/listall?pn=${pn+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>

<div class="row" >
    <!-- 分頁文字信息 -->
    <div class="col-md-4">
        <c:if test="${currentPn==1}">
            <span>上一页</span>
        </c:if>
        <c:if test="${currentPn>1}">
            <span><a href="${path}/user/listall?pn=${currentPn-1}">上一页</a></span>
        </c:if>
        <c:if test="${currentPn>=pageTotal}">
            <span>下一页</span>
        </c:if>
        <c:if test="${currentPn<pageTotal}">
            <span><a href="${path}/user/listall?pn=${currentPn+1}">下一页</a></span>
        </c:if>

        当前第：<font  size="3">${currentPn }</font>页，总共：<font  size="3">${pageTotal}</font>页，总

        共：<font  size="3">${listNum}</font>条记录
    </div>
</div>



<!--模态框的使用-->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">信息修改</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form action="${path}/user/updateuser">
                    <div class="col-xs-8 col-sm-6">
                        姓名:
                    </div>
                    <div class="col-xs-8 col-sm-6">
                       <input id="name_modal" name="name_modal">
                    </div>
                        <input id="id_modal" name="id_modal" type="hidden">
                        <button type="submit" value="提交"></button>
                    </form>
                </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    function Info(NAME,ID) {
        $("#name_modal").val(NAME);
        $("#id_modal").val(ID);
    }
</script>
</body>
</html>