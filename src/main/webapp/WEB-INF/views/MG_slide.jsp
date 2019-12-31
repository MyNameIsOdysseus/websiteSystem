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
    <link rel="stylesheet" href="<%=rootPath%>/css/jquery-confirm.css">
    <link rel="shortcut icon" href="<%=rootPath%>/image/favicon.ico" />
    <link rel="bookmark" href="<%=rootPath%>/image/favicon.ico"　/>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/iconfont.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.Modal.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>
</head>
<style>
	
</style>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
<!--header-->
<header class="header-page MG-header"></header>
<!--主体内容-->

<!-- left -->
    <section class="MG-side">
	    <div class="MG-side-scroll">
	    	 <ul id="menu1" class="menu-xt">
	            <li>
	                <svg class="icon svg-icon" aria-hidden="true">
	                    <use xlink:href="#iconjibenxinxi"></use>
	                </svg>
                    <a href="<%=basePath%>/index">基本信息</a>
	            </li>
	            <li class="select">
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
<!-- right -->
    <section class="MG-body">
        <div class="page-title page-title-borderNone">
            <h1 class="none-border title-left">幻灯片PC端</h1>
            <input id="ImgName" type="text" placeholder="图片名称">
            <input id="TitleName" type="text" placeholder="标题">
            <input id="ChanName" type="text" placeholder="所属栏目">
            <input type="button" class="search-btn" onclick="updatetable(1)" value="">
            <a class="btn-add" id="btn-add" href="javascript:openDiv();">新增</a>
        </div>

        <table class="table2" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>排序</th>
                    <th>图片名称</th>
                    <th>链接地址</th>
                    <th>标题</th>
                    <th>所属栏目</th>
                    <th width="12%">操作</th>
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
                        <i title="上一页" id="disa1" class="page-icon2" onclick="skippage(1)"></i>
                    </li>
                    <li>
                        <span><input id="skipnum" maxlength="3" type="text"  ></span>
                    </li>
                    <li>
                        <i title="下一页" id="disa2" class="page-icon3" onclick="skippage(2)"></i>
                    </li>
                    <li>
                        <i title="末页" dis="disa3" class="page-icon4" onclick="skippage(3)"></i>
                    </li>
                </ul>
            </div>
            <div class="pull-right">
                <span id="maxnum" align="right"></span>
            </div>
        </div>
    </section>

<!--弹框-->

<div id="modalDiv" >
    <div id="popContainer" class="MG-modal">
        <div class="MG-modal-title" id="divtitle" >
            新增
        </div>
        <div class="MG-modal-body" >
            <table class="table-dialog " cellpadding="0" cellspacing="0">
                <tr >
                    <td >
                        <label>图片名称<span>*</span>:</label>
                        <%--<button class="btn-upload">上传图片</button>--%>
                        <div id="changeDemo">

                        </div>
                    </td>

                </tr>
                <tr id="lookOLd">

                </tr>
                <tr>
                    <td>
                        <label>链接地址:</label>
                        <input id="linkAddress" type="text" maxlength="120" placeholder="点击图片打开的页面（可空）" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>标题:</label>
                        <input id="title" type="text" placeholder="在图片下面的标题（可空）" maxlength="120">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>排序<span>*</span>:</label>
                        <input id="sorted" type="text" placeholder="排序码" maxlength="4" value="1000" oninput = "value=value.replace(/[^\d]/g,'')">
                        <em>请输入整数，排序数字越小越靠前</em>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>所属栏目<span>*</span>:</label>
                        <select name="" id="belongChan">

                        </select>
                    </td>
                </tr>
            </table>
        </div>
        <div class="MG-modal-bottom">
            <button  class="btn-upload" onclick="postPpt();"><strong>保存</strong ></button >
            <button  class="btn-upload btn-bg-dark" onclick ="closeDiv();">取消</button >
        </div >
        <a href="javascript:void(0);" class="close-MG-modal" onclick ="closeDiv();"></a >
        <input id="pptid" type="hidden">
        <input id="pptsaveName2" type="hidden">
        <input id="pptFileame2" type="hidden">
    </div>
</div>
</div>
</body>
<script>
    var temp=undefined;  //红色表框的需要的对象存储
    var pageindex=1;     //当前的分页的下标页
    var maxindexnum=0;   //当前页面所存在数据支持的最大分页的下标页

    $('#skipnum').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            blurFunc();
        }
    });

    $('#ImgName').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            updatetable(1);
        }
    });

    $('#TitleName').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            updatetable(1);
        }
    });

    $('#ChanName').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
            updatetable(1);
        }
    });


    $(function(){
        //load头部
        $(".header-page").load("<%=basePath%>/page/header");
        updatetable();
        updateList();
    });

    function updateList(){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/chan/getAllMessage",
            data:{
            },
            success:function(result){
                var xx=new Array("chanCode","chanName");
                $("#belongChan").empty();
                $("#belongChan").append("<option value='1'>请选择所属栏目</option>")
                $("#belongChan").append(addSelectOption(result,xx));

            }
        })
    }

    function updatetable(testType){      //用来对列表的数据进行刷新的函数
        var ImgName=$("#ImgName").val();
        var TitleName=$("#TitleName").val();
        var ChanName=$("#ChanName").val();

        if(testType==1){
            $("#skipnum").val("1");
            pageindex=1;
        }


        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/ppt/getPptList",
            data:{
                currentpage:pageindex,
                pageSize:$("#changePageSize").val(),
                ImgName:ImgName,
                TitleName:TitleName,
                ChanName:ChanName
            },
            success:function(result){
                if(result.pptlist==null){
                    alert("所选区间超越数据极限");
                    return;
                }
                $("#livetable").empty();
                var xx=new Array("sorted","pptName","linkAddress","title","belongChanName","pptId");
                var htmltable=getMessage(result.pptlist,xx);
                $("#livetable").html(htmltable);
                $("#_indexNum").text(pageindex);
                setMaxindex(result,parseInt($("#changePageSize").val()));
            }
        })
    }


    //弹出框调用（可配置弹出框的高宽参数）
    function  openDiv(){
        $("#divtitle").html("新增");
        $("#changeDemo").empty();
        $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=ppt&fileName=pptFileame&" +
            "saveName=pptsaveName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName=1");
        clear();
        $("#belongChan").val("1");
        $.openMgModal("popContainer",{closeClickOverlay:true,width:600,height:540});
    }
    function closeDiv(){
        $.closeMgModal("popContainer");
        clear();
        updatetable();
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

    function updateppt(pptid) {  //更新的时候使用  主要的作用是进行数据的渲染
        $("#changeDemo").empty();
        openDiv();
        $("#divtitle").html("修改");
        $("#pptid").val(pptid);
        $.ajax({
            type : "POST", //提交方式
            url : "${pageContext.request.contextPath}/ppt/getSingleMessage",//路径
            data : {
                pptid:pptid
            },//数据，这里使用的是Json格式进行传输
            success : function(result) {//返回数据根据结果进行相应的处理
                if ( result !=null ) {
                    //var oldhtml="";

                    // $("#lookOLd").html(showOldMessage(result.pptsavename,"lookOLd",result.pptName));
                    $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=ppt&fileName=pptFileame&" +
                        "saveName=pptsaveName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName="+result.pptsavename);
                    $("#linkAddress").val(result.linkAddress);
                    $("#title").val(result.title);
                    $("#sorted").val(result.sorted);
                    $("#belongChan").val(result.belongChan);
                    $("#pptsaveName2").val(result.pptsavename);
                    $("#pptFileame2").val(result.pptName);
                } else {
                    alert("获取数据失败");
                }
            }
        });
    }

    function delppt(pptid){  //删除方法
        $.ajax({
            type : "POST", //提交方式
            url : "${pageContext.request.contextPath}/ppt/deletePpt",//路径
            data : {
                pptId:pptid
            },//数据，这里使用的是Json格式进行传输
            success : function(result) {//返回数据根据结果进行相应的处理
                if ( result ==1 ) {
                    alert("删除成功");
                    updatetable();
                } else {
                    alert("删除失败");
                    updatetable();
                }
            }
        });
    }

    function postPpt() {  //数据的新增或这是更新
        if(temp != undefined){
            uncheckcode(temp);
        }
        var pptid=$("#pptid").val();
        var pptsaveName=$("#pptsaveName").val();
        var filename=$("#pptFileame").val();
        // if($("#lookOLd").find('a').length>0){
        //     pptsaveName=$("#pptsaveName2").val();
        //     filename=$("#pptFileame2").val();
        // }
        if($("#linkAddress").val()!=""){
            var type =IsURL($("#linkAddress").val())==false;
            if(type){
                return alert("链接地址需以http或者https开头");
            }
        }

        var channamexx=$("#belongChan").find("option:checked").text();
        $.ajax({
            type : "POST", //提交方式
            url : "${pageContext.request.contextPath}/ppt/setPptMessage",//路径
            data : {
                pptId:$("#pptid").val(),
                pptName:filename,
                linkAddress:$("#linkAddress").val(),
                title:$("#title").val(),
                sorted:$("#sorted").val(),
                belongChan:$("#belongChan").val(),
                belongChanName:channamexx,
                pptsavename:pptsaveName
            },//数据，这里使用的是Json格式进行传输
            success : function(result) {//返回数据根据结果进行相应的处理
                if ( result !== "" && result !==1 && result !==0 ) {
                    checkcode(result);
                    temp=result;
                } else if(result !==1 || result !==0){
                    temp=undefined;
                    alert("操作成功");
                    closeDiv();
                    window.location.reload();
                }else{
                    alert("会话失败，请重新登录");
                }
            }
        });
    }

    function clear() {  //用来清除模板的数据 避免在新增或者是更新之后的数据的污染
        //$("#lookOLd").html("");
        $("#linkAddress").val("");
        $("#title").val("");
        $("#belongChan").val("");
    }




</script>
</html>