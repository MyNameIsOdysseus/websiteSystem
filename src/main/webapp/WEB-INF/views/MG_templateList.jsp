<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/3/26
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="<%=rootPath%>/css/laydate.css">
    <link rel="shortcut icon" href="<%=rootPath%>/image/favicon.ico" />
    <link rel="bookmark" href="<%=rootPath%>/image/favicon.ico"　/>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/laydate.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/iconfont.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>
</head>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
    <!--header-->
    <header class="header-page MG-header"></header>
    <!--left-->
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
                <li>
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconlanmupeizhi"></use>
                    </svg>
                    <a href="<%=basePath%>/page/MG_columnList">栏目管理</a>
                </li>
                <li class="select">
                    <span class="icon-mb"></span>
                    <a href="<%=basePath%>/page/MG_templateList">模板管理</a>
                </li>
                <li>
                    <svg class="icon svg-icon" aria-hidden="true">
                        <use xlink:href="#iconliucheng"></use>
                    </svg>
                    <a href="<%=basePath%>/page/MG_process">流程管理</a>
                </li>
                <li>
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
                <%--<li>--%>
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
            <h1 class="title-left">模板管理</h1>

            <input id="templateName" type="text" placeholder="模板名称">
            <input type="button" onclick="updatetable(1)" value=""  class="search-btn">

            <a class="btn-add" id="btn-add" href="<%=basePath%>/page/MG_template" >新增</a>
        </div>
        <%--<div class="page-date clearfix">--%>
            <%--&lt;%&ndash;<input type="text" class="test-date" placeholder="起始时间">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<span>一一</span>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<input type="text" class="test-date" placeholder="截至时间">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="search-inp">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input type="text" placeholder="标题/内容">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input class="search-btn">&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--<a class="btn-add" id="btn-add" href="javascript:openDiv();">新增</a>--%>
        <%--</div>--%>

        <table class="table2" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <%--<th style="width: 100px;"></th>--%>
                    <th>模板编码</th>
                    <th>模板名称</th>
                    <th>模板类型</th>
                    <th width="11%">操作</th>
                </tr>
            </thead>
            <tbody id="livetable">

            </tbody>
        </table>

        <div class="pagination">
            <div class="pull-left paging">
                <select id="changePageSize" onchange="updatetable(1)">
                    <option value="15">15</option>
                    <option value="30">30</option>
                    <option value="60">60</option>
                    <option value="120">120</option>
                </select>
                &nbsp;&nbsp;第<span id="_indexNum"></span>
                /<span id="_totalPageSpan"></span>
                页&nbsp;&nbsp;
                <ul>
                    <li>
                        <i title="首页" id="disa0" class="page-icon1" onclick="skippage(0)"></i>
                    </li>
                    <li>
                        <i title="首页" id="disa1" class="page-icon2" onclick="skippage(1)"></i>
                    </li>
                    <li>
                        <span><input id="skipnum" maxlength="3" type="text" ></span>
                    </li>
                    <li>
                        <i title="首页" id="disa2" class="page-icon3" onclick="skippage(2)"></i>
                    </li>
                    <li>
                        <i title="首页" dis="disa3" class="page-icon4" onclick="skippage(3)"></i>
                    </li>
                </ul>
            </div>
            <div class="pull-right">
                <span id="maxnum" align="right"></span>
            </div>
        </div>
    </section>

</div>

</body>
<script>
    var pageindex=1;     //当前的分页的下标页
    var maxindexnum=0;   //当前页面所存在数据支持的最大分页的下标页

    $('#skipnum').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            blurFunc();
        }
    });

    $('#templateName').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            updatetable(1);
        }
    });




    $(function(){
        //头部页面加载
        $(".header-page").load("<%=basePath%>/page/header");
        updatetable();
    });

    //日历控件调用
    $('.test-date').each(function(){
        laydate.render({
            elem: this
        });
    });

    function updatetable(testType){ //对列表的数据进行更新的函数

        var templateName=$("#templateName").val();

        if(testType==1){
            $("#skipnum").val("1");
            pageindex=1;
        }

        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/TemplateController/getPageList",
            data:{
                currentpage:pageindex,
                pageSize:$("#changePageSize").val(),
                templateName:templateName
            },
            success:function(result){
                if(result.templatelist==null){
                    alert("所选区间超越数据极限");
                    return;
                }
                for(var a=0;a<result.templatelist.length;a++){
                    result.templatelist[a].templateType==1?result.templatelist[a].templateType="公用":null;
                    result.templatelist[a].templateType==2?result.templatelist[a].templateType="专用":null;
                    //result.chanlist[a].chanType==3?result.chanlist[a].chanType="特殊表单":null;
                    //result.chanlist[a].chanStatus==0?result.chanlist[a].chanStatus="启用状态":result.chanlist[a].chanStatus="禁用状态";
                } 
                $("#livetable").empty();
                var xx=new Array("templateCode","templateName","templateType","templateId");
                var htmltable=getMessage(result.templatelist,xx,6);
                $("#livetable").html(htmltable);
                $("#_indexNum").text(pageindex);
                setMaxindex(result,parseInt($("#changePageSize").val()));
            }
        })
    }


    //分页一直位于内容底部
    function setheight() {
        var container=document.getElementById('container');
        var cliHeight = document.documentElement.clientHeight;
        // 如果 #right 小于屏幕的高度, 则设置 #main 的高度为屏幕高度
        // 同时, #left 让它有滚动条
        if($('.right').outerHeight() < cliHeight){
            $(container).css('height', cliHeight  - 100 + 'px');
            /*$('.left').css( {'overflow-y':'scroll', 'height':cliHeight-113+'px'} );*/
        }
    }
    setheight();

    function delppt(updateid){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/TemplateController/del",
            data:{
                updateid:updateid
            },
            success:function(result){
                alert(result);
                updatetable();
            }
        })
    }
    
    function updateppt(updateid) {
        window.location.href ="${pageContext.request.contextPath}/TemplateController/upda?updateid="+updateid;
    }

</script>
</html>