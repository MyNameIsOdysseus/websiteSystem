<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>明高网站后台</title>
    <%
        String rootPath = request.getContextPath();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    %>
    <link rel="stylesheet" href="<%=rootPath%>/css/cssresets.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/iconfont.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/style.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/zTreeStyle.css">
    <link rel="shortcut icon" href="<%=rootPath%>/image/favicon.ico" />
    <link rel="bookmark" href="<%=rootPath%>/image/favicon.ico"　/>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/iconfont.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/main.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>
</head>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
    <header class="header-page MG-header"></header>
    <!--主体内容-->
    <section class="MG-side">
        <div class="MG-side-scroll">
            <ul id="menu1" class="menu-xt">
                <li>
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconjibenxinxi"></use>
                    </svg>
                    <a href="<%=basePath%>/index">基本信息</a>
                </li>
                <li>
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconhuandengpian"></use>
                    </svg>
                    <a href="<%=basePath%>/page/MG_slide">幻灯片</a>
                </li>
                <%--<li>--%>
                    <%--<span class="icon-xf"></span>--%>
                    <%--<a href="MG_suspended.html">悬浮窗管理</a>--%>
                <%--</li>--%>
                <li >
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconlanmupeizhi"></use>
                    </svg>
                    <a href="<%=basePath%>/page/MG_columnList">栏目管理</a>
                </li>
                <li>
                    <span class="icon-mb"></span>
                    <a href="<%=basePath%>/page/MG_templateList">模板管理</a>
                </li>
                <li>
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconliucheng"></use>
                    </svg>
                    <a href="<%=basePath%>/page/MG_process">流程管理</a>
                </li>
                <li >
                    <span class="icon-yh"></span>
                    <a href="<%=basePath%>/page/MG_userlist">用户管理</a>
                </li>
                <li>
                    <span class="icon-lxwm"></span>
                    <a href="<%=basePath%>/page/MG_contactMe">联系我们</a>
                </li>
                <li>
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconGroup-"></use>
                    </svg>
                    <a href="<%=basePath%>/page/MG_friendlyList">友情链接</a>
                </li>
                <li disabled="disabled">

                </li>
                <%--<li class="select">--%>
                    <%--<svg class="icon svg-icon" aria-hidden="true">--%>
                        <%--<use xlink:href="#icongenggaimima"></use>--%>
                    <%--</svg>--%>
                    <%--<a href="<%=basePath%>/page/MG_changePassword">更改密码</a>--%>
                <%--</li>--%>
            </ul>
        </div>
    </section>
    <section class="MG-body">
        <div class="page-title">
            <h1 id="titlename">修改密码</h1>
        </div>

        <table class="table" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <label>新密码</label>
                    <input id="passWord" type="password" class="wth-250">
                </td>
            </tr>
            <tr>
                <td>
                    <label>确认密码</label>
                    <input id="passWordReload" type="password" class="wth-250">
                </td>
            </tr>
        </table>

        <div class="bottom-btn">
            <button class="btn-upload btn-ml" style="margin-left: 120px;" onclick="saveOrUpdate()">保存</button>
        </div>
        <input id="userStatus" type="hidden" value="0" />
    </section>
    <input id="checktype" type="hidden" value="${updateid}" />
</div>
</body>
<script>
    $(function(){
        //load头部
        $(".header-page").load("<%=basePath%>/page/header");
        var userid="${sessionScope.UserSession.username}";
        if(userid!="admin"){
            var lilength=$("#menu1").find('li');
            for(var a=0;a<lilength.length-1;a++){
                lilength.eq(a).css("display","none");
            }
        }

    });

    function saveOrUpdate() {
        var userid="${sessionScope.UserSession.userId}";
        var username="${sessionScope.UserSession.username}";
        if($("#passWord").val()=="" ||  $("#passWordReload").val()==""){
            alert("修改密码时，两个输入框均不能为空！");
            return;
        }

        if($("#passWord").val() != $("#passWordReload").val()){
            alert("请保持密码的一致性！");
            return;
        }
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/user/changePassword",
            data:{
                userId:userid,
                username:username,
                password:$("#passWord").val()
            },
            success:function(result){
                if(result != null){
                    alert("操作成功！");
                }
            }
        })
    }
    
</script>
</html>

