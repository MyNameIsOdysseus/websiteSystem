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
    
    <style>
        .table{
            width: 33%;
            float: left;
            height:auto;
        }
        .table tr td label{
        	width:80px;
        }
        .menu{float: left;height: auto;}
        .selDemo{position: absolute;right: 200px;}
    </style>
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
                <li class="select">
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
        <div class="page-title tit-border">
            <h1 id="titlename">新增用户</h1>
        </div>

        <table class="table" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <label>用户名<span id="nameone" style="color: red">*</span><span>:</span></label>
                    <input id="username" type="text" class="wth-250" maxlength="50">
                </td>
            </tr>
            <tr>
                <td>
                    <label>密码<span id="passone" style="color: red">*</span><span>:</span></label>
                    <input id="password" type="password" class="wth-250" maxlength="50">
                </td>
            </tr>
            <tr>
                <td>
                    <label>确认密码<span id="passtwo" style="color: red">*</span><span>:</span></label>
                    <input id="passWordReload" type="password" class="wth-250" maxlength="50">
                    <%--<span id="isDelete" class="table-tip">如果不修改请留空</span>--%>
                </td>
            </tr>
            <tr>
                <td>
                    <label>上级领导<span>:</span></label>
                    <select id="uploader" class="select-bg">
                        <option value=""></option>
                    </select>
                </td>
            </tr>
        </table>

        <div class="menu" style="width:67%;position: relative;">
            <div class="menu-title">
                栏目权限
            </div>
            <div>
                <div class="zTree-list">
                    <div style="position: absolute;top: 50px;left: 86px;font-size: 14px;">
                        <h1 style="font-size: 16px; padding-bottom: 8px;">栏目名称</h1>
                        <input id="quanxuanlanmu" type="checkbox" style="width: 15px !important;height: 15px !important;">全选
                    </div>
                    <div style="position: absolute;top: 50px;right: 205px;font-size: 14px;">
                        <h1 style="font-size: 16px;padding-bottom: 8px;">维护范围</h1>
                        <input id="quanxuanbenren" type="checkbox" style="width: 15px !important;height: 15px !important;">全选
                        <input id="quanxuanquanbu" type="checkbox" style="width: 15px !important;height: 15px !important;">全选
                    </div>
                    <ul id="treeDemo" class="ztree" style="margin-top: 60px"></ul>
                </div>
            </div>

        </div>
		<div style="clear:both;"></div>
        <div class="bottom-btn" style="text-align:center;">
            <button class="btn-upload" onclick="saveOrUpdate()">保存</button>
        </div>
       <!--  <input id="userStatus" type="hidden" value="0" /> -->
    </section>
    <input id="checktype" type="hidden" value="${updateid}" />
</div>
</body>
<script>
    var temp=undefined;
    $(function(){
        //load头部
        updateAllPeople();
        $(".header-page").load("<%=basePath%>/page/header");
        updateMessage();

        if($("#checktype").val() !=""){
            $("#isDelete").css("display","none");
            $("#passone").css("display","none");
            $("#passtwo").css("display","none");
            $("#nameone").css("display","none");
        }

    });


    $("#quanxuanlanmu").change(function(){
        if($("#quanxuanlanmu")[0].checked==true){
            for(var xx=0;xx<$(".chk").length;xx++){
                $(".chk").eq(xx).click();
            }
            for(var xx=0;xx<$(".chk").length;xx++){
                $(".chk").eq(xx).attr("class","button chk checkbox_true_full");
            }
        }else{
            for(var xx=0;xx<$(".chk").length;xx++){
                $(".chk").eq(xx).click();
            }
            for(var xx=0;xx<$(".chk").length;xx++){
                $(".chk").eq(xx).attr("class","button chk checkbox_false_full");
            }
        }
    });

    $("#quanxuanbenren").change(function(){
        if($("#quanxuanbenren")[0].checked==true){
            if($("#quanxuanquanbu")[0].checked==true){
                $("#quanxuanquanbu").click();
                for(var cc=0;cc<$(".checkbox_true_full").length;cc++){
                    $(".checkbox_true_full").eq(cc).next().next().find('input')[0].click();
                }
            }else{
                for(var cc=0;cc<$(".checkbox_true_full").length;cc++){
                    $(".checkbox_true_full").eq(cc).next().next().find('input')[0].click();
                }
            }
        }
    });

    $("#quanxuanquanbu").change(function(){
        if($("#quanxuanquanbu")[0].checked==true){
            if($("#quanxuanbenren")[0].checked==true){
                $("#quanxuanbenren").click();
                for(var cc=0;cc<$(".checkbox_true_full").length;cc++){
                    $(".checkbox_true_full").eq(cc).next().next().find('input')[1].click();
                }
            }else{
                for(var cc=0;cc<$(".checkbox_true_full").length;cc++){
                    $(".checkbox_true_full").eq(cc).next().next().find('input')[1].click();
                }
            }
        }
    })


    function updateAllPeople() {
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/user/updateAllPeople",
            async:false,
            data:{
            },
            success:function(result){
                var xx=new Array("userId","username");
                $("#uploader").append(addSelectOption(result,xx));
                var x=$("#checktype").val();
                $("#uploader option[value="+x+"]").remove();

            }
        })
    }
    
    function saveOrUpdate(){

        if($("#username").val().indexOf("&nbsp")>-1){
            alert("非法字符！");
            $("#username").css("border","1px solid red");
            return;
        }

        if($("#password").val() != $("#passWordReload").val()){
            alert("请保持密码一致!");
            return;
        }
        if($("#username").val()=='admin' || $("#username").val()=='ADMIN'){
            alert("admin用户为系统级用户无法新增!");
            return;
        }
        var ynode=$(".checkbox_true_full");
        var gnode=$(".checkbox_true_part");
        var yarr=[];
        var garr=[];
        var ytype=[];
        var gtype=[];
        if(ynode.length>0){
            for(var a=0;a<ynode.length;a++){
                var temp=$(".checkbox_true_full").eq(a).next().next()[0].id;
                yarr.push(temp);
                if($(".checkbox_true_full").eq(a).next().next().find('input')[0].checked==false && $(".checkbox_true_full").eq(a).next().next().find('input')[1].checked==false ){
                    alert("您在“"+$(".checkbox_true_full").eq(a).next().eq(0).text()+"”栏目 没有勾选权限范围（本人或全部）");
                }else{
                    $(".checkbox_true_full").eq(a).next().next().find('input')[0].checked==true?ytype.push(0):ytype.push(1);
                }
            }
        }
        if(gnode.length>0){
            for(var a=0;a<gnode.length;a++){
                var temp=$(".checkbox_true_part").eq(a).next().next()[0].id;
                garr.push(temp);
                if($(".checkbox_true_part").eq(a).next().next().find('input')[0].checked==false && $(".checkbox_true_part").eq(a).next().next().find('input')[1].checked==false ){
                    alert("您在“"+$(".checkbox_true_part").eq(a).next().eq(0).text()+"”栏目 没有勾选权限范围（本人或全部）");
                }else{
                    $(".checkbox_true_part").eq(a).next().next().find('input')[0].checked==true?gtype.push(0):gtype.push(1);
                }
            }
        }

        // for(var a=0;a<garr.length;a++){
        //     for(var b=0;b<yarr.length;b++){
        //         yarr[b].substr(0,garr[a].length)==garr[a]?yarr.splice(b,1)&&ytype.splice(b,1):null;
        //     }
        // }

        var allarr=yarr.concat(garr);
        var alltype=ytype.concat(gtype);
        $("#username").css("border","");
        if(temp != undefined){
            uncheckcode(temp);
        }
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/user/setUserMessage",
            data:{
                userId:$("#checktype").val(),
                username:$("#username").val(),
                password:$("#password").val(),
                upUserid:$("#uploader").val(),
                upUsername:$("#uploader").find("option:checked").text(),
                userStatus:$("#userStatus").val(),
                chanList:JSON.stringify(allarr),
                chantype:JSON.stringify(alltype)
            },
            success:function(result){
                if ( result !== "" && result !== 3 && result !== 0 && result !== 1) {
                    checkcode(result);
                    temp=result;
                }else if(result === 3){
                    alert("保存失败，用户名已存在，请重新输入");
                    $("#username").css("border","1px solid red");
                }else  if(result === 6){
                    alert("admin为内置用户，不能创建");
                    return;
                }else if(result === 0 || result === 1){
                    temp=undefined;
                    alert("操作成功");
                    window.history.go(-1);
                }else{
                    alert("会话失效，请重新登录");
                }
            }
        })
    }

    function updateList(){
        if($("#checktype").val() !=""){
            $("#titlename").html("修改用户");

            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/user/getSingleMessage",
                data:{
                    userid:$("#checktype").val()
                },
                success:function(result){
                    $("#username").val(result.username);
                    //$("#username").val(result.username);
                    if(result.upUserid !=0){
                        $("#uploader").val(result.upUserid);
                    }
                    var chanList = JSON.parse(result.chanList);
                    var typelist = JSON.parse(result.chantype);
                    // var chanlist2=chanList;
                    // var typelist2=typelist;
                    // var chanList = chanList.sort();
                    // typelist=[];
                    // for(var x=0;x<chanList.length;x++){
                    //     for(var y=0;y<chanlist2.length;y++){
                    //         if(chanList[x]==chanlist2[y]){
                    //             typelist.push(typelist2[y]);
                    //         }
                    //     }
                    // }

                    for(var i=chanList.length-1;i>=0;i--){
                        $("#"+chanList[i]).prev().prev().click();
                        typelist[i]==0?$("#"+chanList[i]).find('input').eq(0).prop('checked',true):$("#"+chanList[i]).find('input').eq(1).prop('checked',true);
                    }


                    var ynode=$(".checkbox_true_full");
                    var gnode=$(".checkbox_true_part");
                    if(ynode.length>0){
                        for(var a=0;a<ynode.length;a++){
                            if($(".checkbox_true_full").eq(a).next().next().find('input')[0].checked==false && $(".checkbox_true_full").eq(a).next().next().find('input')[1].checked==false ){
                                //$(".checkbox_true_full").eq(a).click();
                                $(".checkbox_true_full").eq(a).removeClass();
                                ynode=$(".checkbox_true_full");
                                a=0;
                            }
                        }
                    }
                    if(gnode.length>0){
                        for(var a=0;a<gnode.length;a++){
                            if($(".checkbox_true_part").eq(a).next().next().find('input')[0].checked==false && $(".checkbox_true_part").eq(a).next().next().find('input')[1].checked==false ){
                                //$(".checkbox_true_part").eq(a).click();
                                $(".checkbox_true_full").eq(a).removeClass();
                                gnode=$(".checkbox_true_part");
                                a=0;
                            }
                        }
                    }

                    // if(ynode.length>0){
                    //     for(var a=0;a<ynode.length;a++){
                    //         if($(".checkbox_true_full").eq(a).next().next().find('input')[0].checked==true || $(".checkbox_true_full").eq(a).next().next().find('input')[1].checked==true ){
                    //             $(".checkbox_true_full").eq(a).click();
                    //             ynode=$(".checkbox_true_full");
                    //             a=0;
                    //         }
                    //     }
                    // }
                    // if(gnode.length>0){
                    //     for(var a=0;a<gnode.length;a++){
                    //         if($(".checkbox_true_part").eq(a).next().next().find('input')[0].checked==true || $(".checkbox_true_part").eq(a).next().next().find('input')[1].checked==true ){
                    //             $(".checkbox_true_part").eq(a).click();
                    //             gnode=$(".checkbox_true_part");
                    //             a=0;
                    //         }
                    //     }
                    // }



                }
            })


        }
    }


    function updateMessage(){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/chan/getChanOrder",
            data:{
            },
            success:function(result){
                for(var b=0;b<result.length;b++){
                    var temp1={};
                    var arr=[];
                    var temp3={};
                    if(result[b].length==1){
                        if(result[b][0].chanName.length<30){
                            temp1={
                                id:result[b][0].chanLevelCode,
                                name:result[b][0].chanName
                            }
                        }else{
                            temp1={
                                id:result[b][0].chanLevelCode,
                                name:result[b][0].chanName.substr(0,30)
                            }
                        }

                    }else if(result[b].length>1){
                        for(var m=0;m<result[b].length;m++) {
                            if(m==0){
                                if(result[b][m].chanName.length<30){
                                    temp1={
                                        id:result[b][m].chanLevelCode,
                                        name:result[b][m].chanName
                                    }
                                }else{
                                    temp1={
                                        id:result[b][m].chanLevelCode,
                                        name:result[b][m].chanName.substr(0,30)
                                    }
                                }
                            }else{
                                if(temp1.id==result[b][m].chanLevelCode.slice(0,temp1.id.length) && (result[b][m].chanLevelCode.length-temp1.id.length==4)){
                                    if(temp1.children==undefined){
                                        temp1.children=arr;
                                        if(result[b][m].chanName.length<30){
                                            temp3={
                                                id:result[b][m].chanLevelCode,
                                                name:result[b][m].chanName
                                            }
                                        }else{
                                            temp3={
                                                id:result[b][m].chanLevelCode,
                                                name:result[b][m].chanName.substr(0,30)
                                            }
                                        }
                                        arr.push(temp3);
                                    }else{
                                        if(result[b][m].chanName.length<30){
                                            temp3={
                                                id:result[b][m].chanLevelCode,
                                                name:result[b][m].chanName
                                            }
                                        }else{
                                            temp3={
                                                id:result[b][m].chanLevelCode,
                                                name:result[b][m].chanName.substr(0,30)
                                            }
                                        }
                                        temp1.children.push(temp3);
                                    }
                                }else{
                                    arr=[];
                                    bbq:
                                    for(var x=0;x<temp1.children.length;x++){
                                        if(temp1.children[x].id == result[b][m].chanLevelCode.slice(0,temp1.children[x].id.length) && (result[b][m].chanLevelCode.length-temp1.children[x].id.length==4) ){
                                            if(temp1.children[x].children==undefined){
                                                temp1.children[x].children=arr;
                                                if(result[b][m].chanName.length<30){
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName
                                                    }
                                                }else{
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName.substr(0,30)
                                                    }
                                                }
                                                arr.push(temp3);
                                                break;
                                            }else{
                                                if(result[b][m].chanName.length<30){
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName
                                                    }
                                                }else{
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName.substr(0,30)
                                                    }
                                                }
                                                temp1.children[x].children.push(temp3);
                                            }
                                        }else{
                                            for(var xx=0;xx<temp1.children.length;xx++){
                                                if(temp1.children[xx].children != undefined){
                                                    for(var k=0;k<temp1.children[xx].children.length;k++){
                                                        if(temp1.children[xx].children[k].id == result[b][m].chanLevelCode.slice(0,temp1.children[xx].children[k].id.length) && (result[b][m].chanLevelCode.length-temp1.children[xx].children[k].id.length==4) ){
                                                            if(temp1.children[xx].children[k].children==undefined){
                                                                temp1.children[xx].children[k].children=arr;
                                                                if(result[b][m].chanName.length<30){
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName
                                                                    }
                                                                }else{
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName.substr(0,30)
                                                                    }
                                                                }
                                                                arr.push(temp3);
                                                                break bbq;
                                                            }else{
                                                                if(result[b][m].chanName.length<30){
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName
                                                                    }
                                                                }else{
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName.substr(0,30)
                                                                    }
                                                                }
                                                                temp1.children[xx].children[k].children.push(temp3);
                                                                break bbq;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    zNodes.push(temp1);

                }
                flushTree();
                updateList();

            }
        })
    }

    /*树形菜单*/
    IDMark_A = "_a";
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "", "N": ""}
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            showIcon:  false,
            showLine: false,
            addDiyDom: addDiyDom,
        }
    };


    var zNodes =[
    ];

    var code;
    function setCheck() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            py = $("#py").attr("checked") ? "p" : "",
            sy = $("#sy").attr("checked") ? "s" : "",
            pn = $("#pn").attr("checked") ? "p" : "",
            sn = $("#sn").attr("checked") ? "s" : "",
            type = {"Y": py + sy, "N": pn + sn};
        zTree.setting.check.chkboxType = type;
        zTree.expandAll(true);
        showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
    }
    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        setCheck();
        $("#py").bind("change", setCheck);
        $("#sy").bind("change", setCheck);
        $("#pn").bind("change", setCheck);
        $("#sn").bind("change", setCheck);
    });

    function showCode(str) {
        if (!code) code = $("#code");
        code.empty();
        code.append("<li>" + str + "</li>");
    }



    function addDiyDom(treeId, treeNode) {
        if (treeNode.parentNode && treeNode.parentNode.id!=10) return;
        var aObj = $("#" + treeNode.tId + IDMark_A);
        // if (treeNode.id == 23) {
            var editStr = "<span class='selDemo' id='" + treeNode.id + "'><input type='radio' name=\"typexx\" class='mune-inp'>本人 &nbsp;&nbsp;<input type='radio' name=\"typexx\" class='mune-inp'>全部</span>";
            editStr=editStr.replace(/typexx/g,treeNode.tId);
            aObj.after(editStr);
            //var btn = $("#diyBtn_" + treeNode.id);
            /*if (btn) btn.bind("change", function () {
             alert("diy Select value=" + btn.attr("value") + " for " + treeNode.name);
             });*/
        // }
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });

    function flushTree(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandAll(true);
    }


</script>
</html>

