<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--header-->
    <%
        String rootPath = request.getContextPath();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.flushBuffer();

    %>
    <div class="header-logo mg-left">
        <img src="<%=rootPath%>/image/mg_logo.png" alt="">
    </div>

    <div class="header-center mg-left">
        <ul id="header-list" class="mg-left">
            <li class="mg-left"><a href="<%=basePath%>/page/MG_contentMannger">内容管理</a></li>
            <li class="mg-left"><a href="<%=basePath%>/page/demo">内容审核</a></li>
            <li class="mg-left"><a href="<%=basePath%>/page/MG_changePassword">系统设置</a></li>
        </ul>
        <%--<div class="header-search mg-left">--%>
            <%--<input type="text" placeholder="搜索">--%>
            <%--<a href="javasrcipt:;"></a>--%>
        <%--</div>--%>
    </div>

    <div class="header-right mg-right">
        <p id="demoxx" class="mg-left">欢迎您</p>
        <i class="mg-down"></i>
        <ul class="header-ul">
        	<li><a href="<%=basePath%>/page/MG_changePassword" >修改密码</a></li>
        	<li><a  href="<%=basePath%>/logout" >退出</a></li>
        </ul>
        <%-- <a  href="<%=basePath%>/logout" >退出</a> --%>
    </div>
<script>
    var userid="${sessionScope.UserSession.username}";
    var javascriptData ="<%=session.getAttribute("UserSession")%>";
    $(function(){
        //头部导航
        $('#header-list li a').click(function(){
            $(this).addClass('current')
                    .parent().siblings().children().removeClass('current');
        });
        //左侧导航
        $('#menu1 li').click(function(){
            $(this).addClass('select')
                    .siblings().removeClass('select');
        });
        if(userid==undefined || userid==""){
            alert("您没有登录，请您先登录");
            window.location.href="${pageContext.request.contextPath}/login"
        }else{
            $("#demoxx").text(userid);
        }

    });

</script>