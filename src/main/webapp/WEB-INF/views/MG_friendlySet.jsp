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
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.Modal.js"></script>
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
                <li >
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
                <li class="select">
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
            <h1 class="title-left">链接管理</h1>
            <a class="btn-add" id="btn-add" href="javascript:openDiv();">新增</a>
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
                <th>链接名称</th>
                <th>链接地址</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="livetable">

            </tbody>
        </table>
    </section>

    <!--分页-->
    <%--<div class="paging clearfix">--%>
    <%--&lt;%&ndash;<ul>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="total">跳转至</li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="total"><input type="text" class="page-inp"></li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="total">页</li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="page-turning"><a href="#">上一页</a></li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="page-turning"><a href="#">下一页</a></li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="page-turning"><a href="#">尾页</a></li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<li class="total">共100条记录</li>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
    <%--<ul>--%>
    <%--<li class="total">跳转至</li>--%>
    <%--<li class="total"><input id="skipnum"  type="text" class="page-inp" value="1"></li>--%>
    <%--<li class="total">页</li>--%>
    <%--<li class="page-turning"><a id="disa0" class="disa" href="#" onclick="skippage(0)" >首页</a></li>--%>
    <%--<li class="page-turning"><a id="disa1" class="disa" href="#" onclick="skippage(1)" >上一页</a></li>--%>
    <%--<li class="page-turning"><a id="disa2" class="disa" href="#" onclick="skippage(2)">下一页</a></li>--%>
    <%--<li class="page-turning"><a id="disa3" class="disa" href="#" onclick="skippage(3)">尾页</a></li>--%>
    <%--<li id="maxnum" class="total"></li>--%>
    <%--<li class="total">每页6条记录</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
</div>
<div id="modalDiv" >
    <div id="popContainer" class="MG-modal">
        <div id="checkName" class="MG-modal-title" id="divtitle" >
            新增
        </div>
        <div class="MG-modal-body" >
            <table class="table-dialog " cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <label>链接名称<span>*</span>:</label>
                        <input id="linkname" type="text" maxlength="50"/>
                        <%--<select id="processactiNum">--%>
                            <%--<option id="num0" style="display: none" value="0"></option>--%>
                            <%--<option id="num1">1</option>--%>
                            <%--<option id="num2">2</option>--%>
                            <%--<option id="num3">3</option>--%>
                            <%--<option id="num4">4</option>--%>
                            <%--<option id="num5">5</option>--%>
                        <%--</select>--%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%--<label>参与人<span>*</span>:</label>--%>
                        <%--<input id="choose1" name="demo" type="radio" value="选择人员" onclick="showdemo()" >选择人员--%>
                        <%--<input id="choose2" name="demo" type="radio" value="发起人的上级领导" onclick="hidddemo()" checked="checked">发起人的上级领导--%>
                        <label>链接地址<span>*</span>:</label>
                        <input id="linkaddress" type="text" maxlength="50" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')" />
                    </td>
                </tr>
                <%--<tr id="people" style="display: none">--%>
                    <%--<td>--%>
                        <%--<label>具体人员<span>*</span>:</label>--%>
                        <%--<select id="knowPeople">--%>
                            <%--<option value="123">张三</option>--%>
                            <%--<option value="124">李四</option>--%>
                            <%--<option value="125">王五</option>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            </table>
        </div>
        <div class="MG-modal-bottom">
            <button  class="btn-upload" onclick="post();"><strong>保存</strong ></button >
            <button  class="btn-upload btn-bg-dark" onclick ="closeDiv();">取消</button >
        </div>
        <a href="javascript:void(0);" class="close-MG-modal" onclick ="closeDiv();"></a >
        <input id="checktype" type="hidden" value="${updateid}" />
        <input id="linkid" type="hidden"  />
    </div>
</div>
</body>
<script>
    var pageindex=1;     //当前的分页的下标页
    var maxindexnum=0;   //当前页面所存在数据支持的最大分页的下标页
    var temp=undefined;
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

    function updatetable(){ //对列表的数据进行更新的函数
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/FriLink/getFrilinkList",
            data:{
                frlinkId:$("#checktype").val()
            },
            success:function(result){
                $("#livetable").empty();
                var xx=new Array("linkname","linkaddress","linkId");
                var htmltable=getMessage(result,xx);
                $("#livetable").html(htmltable);

            }
        })
    }


    //分页一直位于内容底部
    // function setheight() {
    //     var container=document.getElementById('container');
    //     var cliHeight = document.documentElement.clientHeight;
    //     // 如果 #right 小于屏幕的高度, 则设置 #main 的高度为屏幕高度
    //     // 同时, #left 让它有滚动条
    //     if($('.right').outerHeight() < cliHeight){
    //         $(container).css('height', cliHeight  - 100 + 'px');
    //         /*$('.left').css( {'overflow-y':'scroll', 'height':cliHeight-113+'px'} );*/
    //     }
    // }
    // setheight();


    function  openDiv(){
        clear();
        $.openMgModal("popContainer",{closeClickOverlay:true,width:400,height:300});
    }
    function closeDiv(){
        clear();
        $.closeMgModal("popContainer");
        updatetable();
    }

    // function showdemo(){
    //     $("#people").css("display","block");
    //     $("#choose2").removeAttr("checked");
    //     $("#choose1").attr("checked","checked");
    // }
    // function hidddemo() {
    //     $("#people").css("display","none");
    //     $("#choose1").removeAttr("checked");
    //     $("#choose2").attr("checked","checked");
    // }

    function post(){
        $("#linkname").css("border","");

        if($("#linkname").val().indexOf("&nbsp")>-1){
            alert("非法字符！");
            $("#linkname").css("border","1px solid red");
            return;
        }

        if(temp != undefined){
            uncheckcode(temp);
        }

        var type =IsURL($("#linkaddress").val())==false;
        if(type){
            return alert("链接地址需以http或者https开头");
        }


        var num=$("#linkid").val();
        var proid=$("#checktype").val();
        var linkname=$("#linkname").val();
        var linkaddress=$("#linkaddress").val();


        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/FriLink/setFriLinkMessage",
            data:{
                frlinkId:proid,
                linkname:linkname,
                linkaddress:linkaddress,
                linkId:num
            },
            success:function(result){
                if ( result !== "" && result !==3 && result !==0 && result !==1) {
                    checkcode(result);
                    temp=result;
                }else if(result === 3){
                    alert("保存失败，链接名称已经存在，请重新输入");
                    $("#linkname").css("border","1px solid red");
                }else if(result !==0 || result !==1){
                    temp=undefined;
                    alert("操作成功");
                    closeDiv();
                    updatetable();
                }else{
                    alert("会话失效，请重新登录");
                }
            }
        })
    }


    function updateppt(id){
        openDiv();
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/FriLink/getSingleMessage",
            data:{
                linkId:id
            },
            success:function(result){
                $("#linkid").val(result.linkId);
                $("#linkname").val(result.linkname);
                $("#linkaddress").val(result.linkaddress);
            }
        })
    }

    function delppt(id){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/FriLink/delSingleMessage",
            data:{
                linkId:id
            },
            success:function(result){
                if(result != 0){
                    alert("操作成功");
                    updatetable();
                }else{
                    alert("操作失败");
                    updatetable();
                }
            }
        })
    }

    function clear(){
        $("#linkid").val("");
        $("#linkname").val("");
        $("#linkaddress").val("");
    }

</script>
</html>