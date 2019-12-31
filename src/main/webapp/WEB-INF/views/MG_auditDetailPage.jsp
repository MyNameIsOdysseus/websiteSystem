<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <link rel="stylesheet" href="<%=rootPath%>/css/zTreeStyle.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/cssresets.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/iconfont.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/style.css">
    <link rel="stylesheet" href="<%=rootPath%>/css/jquery-confirm.css">

    <link rel="shortcut icon" href="<%=rootPath%>/image/favicon.ico" />
    <link rel="bookmark" href="<%=rootPath%>/image/favicon.ico"　/>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/iconfont.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.Modal.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/components/kindereditor4.11/kindeditor-all-min.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/components/kindereditor4.11/lang/zh-CN.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/components/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/components/pluupload/pluUploader2.3.1/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/mg.pluUploader.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/jquery-confirm.js"></script>
</head>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
    <!--header-->
    <header class="header-page MG-header">
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
    	</div>
    </header>

    <!--left-->
    <section class="MG-side">
        <div class="MG-side-scroll">
            <ul id="treeDemo" class="ztree ztree-menu"></ul>
        </div>
    </section>

    <!--right-->
    <section id="singleList" class="MG-body" >
        <div class="page-title">
            <h1 id="singleListname" class="title-left">内容审核</h1>
        </div>

        <table class="table table3" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2">
                <label>新闻标题<span style="color: red">*</span><span>:</span></label>
                <input id="newssubhead" type="text" maxlength="100" style="width: 80%;">
            </td>
        </tr>
        <tr>
            <td >
                <label>发布时间<span style="color: red">*</span><span>:</span></label>

                <input id="publishdate" type="text" class="ymt" style="width: 65%;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                <img onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="<%=rootPath%>/components/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">

            </td>
            <td >
                <label style="width: 50px;">排序<span>:</span></label>
                <input id="sortedcode" type="text" style="width: 70.5%;">
                <%--<span class="table-tip">请输入0-1000的整数，排序值越小越靠前</span>--%>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label>上副标题<span>:</span></label>
                <input id="upsubhead" maxlength="100" type="text" style="width: 80%;">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label>下副标题<span>:</span></label>
                <input id="downsubhead" maxlength="100" type="text" style="width: 80%;">
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <label>信息来源<span>:</span></label>
                <input id="informationsource" maxlength="100" type="text" style="width: 80%;">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label>简要介绍<span>:</span></label>
                <textarea id="simpleintroduce" ></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label>关键字<span>:</span></label>
                <input id="keywords" maxlength="100" type="text" style="width: 80%;">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label>缩略图<span>:</span></label>
                <%--<input id="image" type="text">--%>
                <%--<button class="btn-transparent" id="uploader_image">选择图片</button>--%>
                <%--<div id="div_image">--%>
                <%--<input type="button" class="btn-transparent" id="uploader_image" value="上传图片"/>--%>
                <%--</div>--%>
                <%--<div id="div_demo">--%>
                <%--<input type="button" class="btn-transparent" id="uploader_demo" value="上传图片"/>--%>
                <%--</div>--%>
                <div id="changeDemo">

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label>标题外链<span>:</span></label>
                <input id="linkaddress" maxlength="100" type="text" style="width: 72%;" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')">
                <input id="isopenlink" type="checkbox">启用
                <%--<span class="table-tip">（需包含 http:// 或 https://）</span>--%>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="line-height: 35px;">
                <label>属性<span>:</span></label>
                <input id="isstick" type="checkbox">置顶

                <input id="isrecommend" type="checkbox">推荐

                <input id="refusecopy" type="checkbox">拒绝复制
            </td>
        </tr>
        <%--<tr>--%>
        <%--<td colspan="2">--%>
        <%--<label>推荐<span>:</span></label>--%>
        <%--<input id="isrecommend" type="checkbox">--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td colspan="2">--%>
        <%--<label>拒绝复制<span>:</span></label>--%>
        <%--<input id="refusecopy" type="checkbox">--%>
        <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td >
                <label>作者<span>:</span></label>
                <input id="author" maxlength="100" type="text" style="width: 65%;">
            </td>
            <td >
                <label style="width: 50px;">发布人<span>:</span></label>
                <input id="issuer" maxlength="100" type="text" style="width: 70.5%;">
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <label style="margin-right: 10px;">内容<span>:</span></label>
                <textarea id="editor_id" name="content" style="width:80%;height:300px;">
                    </textarea>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <button id="changename" class="btn-upload" style="margin-left:155px;" onclick="postMessage()"></button>
                <button id="gohome" style=" float: right;margin-right: 60%;"  class="btn-upload" onclick="gohome()">删除</button>
            </td>
        </tr>
        </table>
        <input id="checktype" type="hidden" value="${updateid}" />
        <input id="articleid" type="hidden">
    </section>
</div>
</body>
<script>
    var userid="${sessionScope.UserSession.username}";
    var userUid="${sessionScope.UserSession.userId}";
    $(function(){
        if(userid==undefined || userid==""){
            alert("您没有登录，请您先登录");
            window.location.href="${pageContext.request.contextPath}/login"
        }else{
            $("#demoxx").text(userid);
            ddd();
        }

    });

    KindEditor.ready(function(K) {
        window.editor = K.create("textarea[id='editor_id']",{
            uploadJson : '${pageContext.request.contextPath}/Kindeditor/uploadFile',
            fileManagerJson : '${pageContext.request.contextPath}/Kindeditor/fileManager',
            allowImageUpload : true, //多图上传
            allowFileManager : true, //浏览图片空间
            filterMode : false, //HTML特殊代码过滤
            afterBlur : function() {
                this.sync();
            }, //编辑器失去焦点(blur)时执行的回调函数（将编辑器的HTML数据同步到textarea）
            afterUpload : function(url, data, name) { //上传文件后执行的回调函数，必须为3个参数
                if (name == "image"
                    || name == "multiimage") { //单个和批量上传图片时
                    if (K("#pic").length > 0) { //文本框存在
                        document
                            .getElementById('piclist').options[document
                            .getElementById('piclist').length] = new Option(
                            url, url); //下拉列表框增加一条
                        document
                            .getElementById('piclist').selectedIndex += 1; //选定刚新增的这一条
                        K("#indexpicimg")
                            .html(
                                "<img src='" + url + "' width='150' height='95' />"); //重置图片展示区DIV的HTML内容
                        K("#pic").val(url); //重置文本框值
                    }
                }
            }
        });
    });





    function  ddd(){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/getAuditMessage",
            data:{
                auditid:$("#checktype").val()
            },
            success:function(resultmessage) {
                $("#articleid").val(resultmessage.articleId);
                $("#changeDemo").empty();
                <%--$("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +--%>
                    <%--"saveName=imageName&fileTypeExts=*.jpg&button=上传附件&buttonText=请选择文件&canModify=1&canDelete=1&saveFileName=" + resultmessage.image);--%>
                $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +
                    "saveName=imageName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName="+resultmessage.image);


                $("#upsubhead").val(resultmessage.upsubhead);
                $("#downsubhead").val(resultmessage.downsubhead);
                $("#newssubhead").val(resultmessage.newssubhead);
               // $("#publishdate").val(new Date(resultmessage.publishdate).format("yyyy-MM-dd hh:mm:ss"));
                $("#publishdate").val(NewDate(resultmessage.publishdate).format("yyyy-MM-dd hh:mm:ss"));
                $("#informationsource").val(resultmessage.informationsource);
                $("#simpleintroduce").val(resultmessage.simpleintroduce);
                $("#keywords").val(resultmessage.keywords);
                $("#image").val(resultmessage.image);
                resultmessage.isopenlink == 0 ? $("#isopenlink").prop("checked", true) : $("#isopenlink").prop("checked", false);
                $("#linkaddress").val(resultmessage.linkaddress);
                resultmessage.isstick == 0 ? $("#isstick").prop("checked", true) : $("#isstick").prop("checked", false);
                resultmessage.isrecommend == 0 ? $("#isrecommend").prop("checked", true) : $("#isrecommend").prop("checked", false);
                resultmessage.refusecopy == 0 ? $("#refusecopy").prop("checked", true) : $("#refusecopy").prop("checked", false);
                $("#sortedcode").val(resultmessage.sortedcode);
                editor.html(resultmessage.articleContentId);
                $("#author").val(resultmessage.author);
                $("#issuer").val(resultmessage.issuer);
                if(resultmessage.createUser==0){
                    $("#changename").html("发布");
                }else{
                    $("#changename").html("发送");
                }
            }
        })
    }

    function gohome(){
        var r=confirm("确认删除么");
        if(r==true){
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/article/delMessage",
                data:{
                    auditid:$("#checktype").val()
                },
                success:function(resultmessage) {
                    if(resultmessage==0){
                        alert("删除成功");
                        window.history.go(-1);
                    }else{
                        alert("删除失败");
                    }
                }
            })
        }

    }

    function postMessage(){
        var isopenlink = $("#isopenlink").prop('checked')==true?isopenlink=0:isopenlink=1;
        var isstick = $("#isstick").prop('checked')==true?isstick=0:isstick=1;
        var isrecommend = $("#isrecommend").prop('checked')==true?isrecommend=0:isrecommend=1;
        var refusecopy = $("#refusecopy").prop('checked')==true?refusecopy=0:refusecopy=1;
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/changeMessageAudit",
            data:{
                articleId:$("#articleid").val(),
                upsubhead:$("#upsubhead").val(),
                downsubhead:$("#downsubhead").val(),
                newssubhead:$("#newssubhead").val(),
                publishdate:$("#publishdate").val(),
                informationsource:$("#informationsource").val(),
                simpleintroduce:$("#simpleintroduce").val(),
                keywords:$("#keywords").val(),
                image:$("#imageName").val(),
                isopenlink:isopenlink,
                linkaddress:$("#linkaddress").val(),
                isstick:isstick,
                isrecommend:isrecommend,
                refusecopy:refusecopy,
                sortedcode:$("#sortedcode").val(),
                auditStatus:$("#checkIsHaveProcess").val(),
                articleContentId:editor.html(),
                author:$("#author").val(),
                issuer:$("#issuer").val(),
                belongChan:$("#chanId").val()
            },
            success:function(result){
                alert(result);
                window.history.go(-1);
            }
        })
    }

    Date.prototype.format = function(format){ var args = { "M+" : this.getMonth() + 1, "d+" : this.getDate(), "h+" : this.getHours(), "m+" : this.getMinutes(), "s+" : this.getSeconds(), "q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter

        "S" : this.getMilliseconds() }; if(/(y+)/.test(format)) format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length)); for(var i in args) { var n = args[i]; if(new RegExp("("+ i +")").test(format)) format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length)); } return format;};


    function NewDate(str){
        if(!str){
            return 0;
        }
        arr=str.split("T");
        d=arr[0].split("-");
        t=arr[1].split(":");
        var date = new Date();
        date.setUTCFullYear(d[0], d[1] - 1, d[2]);
        date.setUTCHours(t[0]-8, t[1], t[2].substr(0,2), 0);
        return date;
    }

</script>