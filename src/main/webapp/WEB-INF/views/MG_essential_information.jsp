<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>明高网站后台</title>
    <%
        String str = request.getAttribute("saveName").toString();
        String str1 = request.getAttribute("media").toString();
        String str2 = request.getAttribute("trai").toString();
        String str3 = request.getAttribute("moblie").toString();

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
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/main.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>

</head>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
	<header class="header-page MG-header"></header>
<!--主体内容-->
<%--隐藏域 主要是判断当前的站点信息是否是新建 还是再次进入编辑--%>
<input type="hidden" id="checkcode" value="0">
<!-- left -->
    <section class="MG-side">
     	<div class="MG-side-scroll">
	     	<ul id="menu1" class="menu-xt">
	            <li class="select">
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
        <div class="page-title tit-border">
            <h1>基本信息</h1>
        </div>
        <table class="table" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <label>网站名称<span style="color: red">*</span><span>:</span></label>
                    <input id="siteName" type="text" maxlength="50">
                </td>
                <td>
                    <label>网站关键词<span style="color: red">*</span><span>:</span></label>
                    <input id="siteKeyword" type="text" maxlength="200">
                </td>
                <td>
                    <label>地址<span style="color: red">*</span><span>:</span></label>
                    <input id="address" type="text" maxlength="100">
                </td>
            </tr>
            <tr>
                <td colspan="2" rowspan="2">
                    <label>网站描述<span>:</span></label>
                    <textarea id="siteDescribe" maxlength="1000"></textarea>
                </td>
                <td>
                    <label>电话<span style="color: red">*</span><span>:</span></label>
                    <input id="telephone" type="text" maxlength="40">
                </td>
            </tr>
            <tr>
                <td>
                    <label>E-mail<span style="color: red">*</span><span>:</span></label>
                    <input id="email" type="text" maxlength="40">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>版权信息<span style="color: red">*</span><span>:</span></label>
                    <input id="copMessage" type="text" class="wth-more" maxlength="240">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>网站logo<span style="color: red">*</span><span>:</span></label>
                    <%--
                                                <button id="siteLogo" name="siteLogo" class="btn-upload">上传图片</button>
                    --%>
                    <%--<jsp:include page="/components/pluupload/upload_modify.jsp" flush="true">--%>
                        <%--<jsp:param name="path"  value="informations"/>--%>
                        <%--<jsp:param name="tableName"  value="siteLogo"/>--%>
                        <%--<jsp:param name="fileName"  value="siteLogoFileame"/>--%>
                        <%--<jsp:param name="saveName"  value="siteLogoName"/>--%>
                        <%--<jsp:param name="canModify"  value="1"/>--%>
                        <%--<jsp:param name="canDelete"  value="1"/>--%>
                        <%--<jsp:param name="fileSizeLimit"  value="1024KB"/>--%>
                        <%--<jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>--%>
                        <%--<jsp:param name="button"  value="上传图片"/>--%>
                        <%--<jsp:param name="buttonText"  value="请选择图片"/>--%>
                        <%--<jsp:param name="uploadCallBack"  value="callBack"/>--%>
                        <%--<jsp:param name="deleteCallBack"  value=""/>--%>
                        <%--<jsp:param name="queueSizeLimit"  value="1"/>--%>
                        <%--<jsp:param name="saveFileName"  value="<%=str%>"/>--%>
                    <%--</jsp:include>--%>

                    <jsp:include page="/components/pluupload/upload_modify.jsp" flush="true">
                        <jsp:param name="path"  value="informations"/>
                        <jsp:param name="tableName"  value="siteLogo"/>
                        <jsp:param name="fileName"  value="siteLogoFileame"/>
                        <jsp:param name="saveName"  value="siteLogoName"/>
                        <jsp:param name="fileTypeExts"  value="*.jpg;*.png;*.bmp;*.gif"/>
                        <jsp:param name="button"  value="上传附件"/>
                        <jsp:param name="buttonText"  value="请选择文件"/>
                        <jsp:param name="queueSizeLimit"  value="1"/>
                        <jsp:param name="canModify" value="1" />
                        <jsp:param name="canDelete"  value="1"/>
                        <jsp:param name="saveFileName"  value="<%=str%>"/>
                    </jsp:include>

                    <div  style="margin-left: 158px;" id="siteLogoOld">
                        <%--<a href="" style="TEXT-DECORATION:none;cursor:pointer;display: none" id="siteLogoOld" ></a>--%>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>宣传片标题<span>:</span></label>
                    <input id="trailerTitle" type="text" class="wth-more" maxlength="40">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>宣传片<span>:</span></label>
                    <%--<button class="btn-upload">上传图片</button>--%>
                    <%--<jsp:include page="/components/pluupload/upload.jsp" flush="true">--%>
                        <%--<jsp:param name="path"  value="informations"/>--%>
                        <%--<jsp:param name="tableName"  value="trailer"/>--%>
                        <%--<jsp:param name="fileName"  value="trailerFileame"/>--%>
                        <%--<jsp:param name="saveName"  value="trailerName"/>--%>
                        <%--<jsp:param name="canModify"  value="1"/>--%>
                        <%--<jsp:param name="canDelete"  value="1"/>--%>
                        <%--<jsp:param name="fileSizeLimit"  value="1024KB"/>--%>
                        <%--<jsp:param name="fileTypeExts" value="*.mp4"/>--%>
                        <%--<jsp:param name="button"  value="上传附件"/>--%>
                        <%--<jsp:param name="buttonText"  value="请选择mp4"/>--%>
                        <%--<jsp:param name="uploadCallBack"  value="callBack"/>--%>
                        <%--<jsp:param name="deleteCallBack"  value=""/>--%>
                        <%--<jsp:param name="queueSizeLimit"  value="1"/>--%>
                        <%--<jsp:param name="saveFileName"  value=""/>--%>
                    <%--</jsp:include>--%>

                    <jsp:include page="/components/pluupload/upload_modify.jsp" flush="true">
                        <jsp:param name="path"  value="informations"/>
                        <jsp:param name="tableName"  value="trailer"/>
                        <jsp:param name="fileName"  value="trailerFileame"/>
                        <jsp:param name="saveName"  value="trailerName"/>
                        <jsp:param name="fileTypeExts" value="*.mp4"/>
                        <jsp:param name="button"  value="上传附件"/>
                        <jsp:param name="buttonText"  value="请选择文件"/>
                        <jsp:param name="canModify" value="1" />
                        <jsp:param name="queueSizeLimit"  value="1"/>
                        <jsp:param name="canDelete"  value="1"/>
                        <jsp:param name="saveFileName"  value="<%=str1%>"/>
                    </jsp:include>
                    <span id="trailer" class="table-tip">（MP4格式）</span>
                    <div  style="margin-left: 158px;" id="trailerOld">
                        <%--<a href="" style="TEXT-DECORATION:none;cursor:pointer;display: none" id="trailerOld" ></a>--%>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>宣传片PC端封面<span>:</span></label>
                    <%--<button id="trailerCover" class="btn-upload">上传图片</button>--%>
                    <%--<jsp:include page="/components/pluupload/upload.jsp" flush="true">--%>
                        <%--<jsp:param name="path"  value="informations"/>--%>
                        <%--<jsp:param name="tableName"  value="trailerCover"/>--%>
                        <%--<jsp:param name="fileName"  value="trailerCoverFileame"/>--%>
                        <%--<jsp:param name="saveName"  value="trailerCoverName"/>--%>
                        <%--<jsp:param name="canModify"  value="1"/>--%>
                        <%--<jsp:param name="canDelete"  value="1"/>--%>
                        <%--<jsp:param name="fileSizeLimit"  value="1024KB"/>--%>
                        <%--<jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>--%>
                        <%--<jsp:param name="button"  value="上传图片"/>--%>
                        <%--<jsp:param name="buttonText"  value="请选择图片"/>--%>
                        <%--<jsp:param name="uploadCallBack"  value="callBack"/>--%>
                        <%--<jsp:param name="deleteCallBack"  value=""/>--%>
                        <%--<jsp:param name="queueSizeLimit"  value="1"/>--%>
                        <%--<jsp:param name="saveFileName"  value=""/>--%>
                    <%--</jsp:include>--%>

                    <jsp:include page="/components/pluupload/upload_modify.jsp" flush="true">
                        <jsp:param name="path"  value="informations"/>
                        <jsp:param name="tableName"  value="trailerCover"/>
                        <jsp:param name="fileName"  value="trailerCoverFileame"/>
                        <jsp:param name="saveName"  value="trailerCoverName"/>
                        <jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>
                        <jsp:param name="queueSizeLimit"  value="1"/>
                        <jsp:param name="button"  value="上传附件"/>
                        <jsp:param name="buttonText"  value="请选择文件"/>
                        <jsp:param name="canModify" value="1" />
                        <jsp:param name="canDelete"  value="1"/>
                        <jsp:param name="saveFileName"  value="<%=str2%>"/>
                    </jsp:include>

                    <div  style="margin-left: 158px;" id="trailerCoverOld">
                        <%--<a href="" style="TEXT-DECORATION:none;cursor:pointer;display: none" id="trailerCoverOld" ></a>--%>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>宣传片手机端封面<span>:</span></label>
                    <%--<button id="mobiletCover" class="btn-upload">上传图片</button>--%>
                    <%--<jsp:include page="/components/pluupload/upload.jsp" flush="true">--%>
                        <%--<jsp:param name="path"  value="informations"/>--%>
                        <%--<jsp:param name="tableName"  value="mobiletCover"/>--%>
                        <%--<jsp:param name="fileName"  value="mobiletCoverFileame"/>--%>
                        <%--<jsp:param name="saveName"  value="mobiletCoverName"/>--%>
                        <%--<jsp:param name="canModify"  value="1"/>--%>
                        <%--<jsp:param name="canDelete"  value="1"/>--%>
                        <%--<jsp:param name="fileSizeLimit"  value="1024KB"/>--%>
                        <%--<jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>--%>
                        <%--<jsp:param name="button"  value="上传图片"/>--%>
                        <%--<jsp:param name="buttonText"  value="请选择图片"/>--%>
                        <%--<jsp:param name="uploadCallBack"  value="callBack"/>--%>
                        <%--<jsp:param name="deleteCallBack"  value=""/>--%>
                        <%--<jsp:param name="queueSizeLimit"  value="1"/>--%>
                        <%--<jsp:param name="saveFileName"  value=""/>--%>
                    <%--</jsp:include>--%>

                    <jsp:include page="/components/pluupload/upload_modify.jsp" flush="true">
                        <jsp:param name="path"  value="informations"/>
                        <jsp:param name="tableName"  value="mobiletCover"/>
                        <jsp:param name="fileName"  value="mobiletCoverFileame"/>
                        <jsp:param name="saveName"  value="mobiletCoverName"/>
                        <jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>
                        <jsp:param name="queueSizeLimit"  value="1"/>
                        <jsp:param name="button"  value="上传附件"/>
                        <jsp:param name="buttonText"  value="请选择文件"/>
                        <jsp:param name="canModify" value="1" />
                        <jsp:param name="canDelete"  value="1"/>
                        <jsp:param name="saveFileName"  value="<%=str3%>"/>
                    </jsp:include>


                    <div  style="margin-left: 158px;" id="mobiletCoverOld">
                        <%--<a href="" style="TEXT-DECORATION:none;cursor:pointer;display: none" id="mobiletCoverOld" ></a>--%>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>站点目录<span style="color: red">*</span><span>:</span></label>
                    <input id="siteCatalog" type="text" class="wth-more" maxlength="120">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label>同步文件<span>:</span></label>
                    <input id="issynchro" type="checkbox" class="wth-secondary">
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <button class="btn-upload btn-ml" style="margin-left: 155px" onclick="saveOrUpdate()">保存</button>
                </td>
            </tr>
        </table>
    </section>
</div>
<%--隐藏域  用来隐藏一些不需要进行展示的字段的信息的存储--%>
<input type="hidden" id="siteId">
<input type="hidden" id="siteStatus">
<input type="hidden" id="createUser">
<input type="hidden" id="createTime">
</div>
</body>
<script>
    var temp=undefined;  //全局变量  主要是用来对提示变量的属性进行重置操作
    $(function(){
        //load头部
        $("#createUser").val("${sessionScope.UserSession.username}");
        $(".header-page").load("<%=basePath%>/page/header");
        $.ajax({
            type : "POST", //提交方式
            url : "${pageContext.request.contextPath}/getSiteMessageSingle",//路径
            data : {

            },//数据，这里使用的是Json格式进行传输
            success : function(result) {//返回数据根据结果进行相应的处理
                if ( result != "" ) {
                    $("#siteName").val(result.siteName);//网站名称
                    $("#siteKeyword").val(result.siteKeyword);//网站关键词
                    $("#siteDescribe").val(result.siteDescribe);//网站描述
                    $("#address").val(result.address);//地址信息
                    $("#telephone").val(result.telephone);//电话信息
                    $("#email").val(result.email);//邮箱信息
                    $("#copMessage").val(result.copMessage);//版权信息
                    $("#siteLogo").val(result.siteLogo);//站点图片
                    $("#trailerTitle").val(result.trailerTitle);//宣传片标题
                    $("#trailer").val(result.trailer);//宣传片影片
                    $("#trailerCover").val(result.trailerCover);//宣传封面图片
                    $("#mobiletCover").val(result.mobiletCover);//手机宣传封面
                    $("#siteCatalog").val(result.siteCatalog);//站点目录
                    $("#siteId").val(result.siteId); //站点id信息
                    $("#siteStatus").val(result.siteStatus); //站点状
                    $("#createUser").val(result.createUser); //站点信息的创建者
                    $("#createTime").val(result.createTime); //站点信息的创建时间
                    $("#checkcode").val(1)
                }



                // if(result.siteLogo !="" && result.siteLogo !=null){
                //     // $("#siteLogoOld").css("display","block");
                //     // $("#siteLogoOld").click(function(){mg.pluUploader.smartDownloadByPlu(''+result.siteLogo);});
                //     // $("#siteLogoOld").text("已上传图片："+result.siteLogo+"(点击可下载查看)")
                //     $("#siteLogoOld").append(showOldMessage(result.siteLogo,"siteLogoOld"));
                // }
                // if(result.trailer !="" && result.trailer !=null ){
                //     // $("#trailerOld").css("display","block");
                //     // $("#trailerOld").click(function(){mg.pluUploader.smartDownloadByPlu(''+result.trailer);});
                //     // $("#trailerOld").text("已上传附件："+result.trailer+"(点击可下载查看)")
                //     $("#trailerOld").append(showOldMessage(result.trailer,"trailerOld"));
                // }
                // if(result.trailerCover !="" && result.trailerCover !=null ){
                //     // $("#trailerCoverOld").css("display","block");
                //     // $("#trailerCoverOld").click(function(){mg.pluUploader.smartDownloadByPlu(''+result.trailerCover);});
                //     // $("#trailerCoverOld").text("已上传图片："+result.trailerCover+"(点击可下载查看)")
                //     $("#trailerCoverOld").append(showOldMessage(result.trailerCover,"trailerCoverOld"));
                // }
                // if(result.mobiletCover !="" && result.mobiletCover !=null ){
                //     // $("#mobiletCoverOld").css("display","block");
                //     // $("#mobiletCoverOld").click(function(){mg.pluUploader.smartDownloadByPlu(''+result.mobiletCover);});
                //     // $("#mobiletCoverOld").text("已上传图片："+result.mobiletCover+"(点击可下载查看)")
                //     $("#mobiletCoverOld").append(showOldMessage(result.mobiletCover,"mobiletCoverOld"));
                // }
            }
        });

    });


    function saveOrUpdate() {
        if($("#checkcode").val()==1){
            alert("站点信息已有数据，本次保存将会覆盖之前数据");
            $("#createTime").val(new Date($("#createTime").val()));
        }else{
            $("#createTime").val(new Date());
        }
        if(temp != undefined){
            uncheckcode(temp);
        }
        if($("#siteLogo").find('tr').eq(1).length==1){   //此四个判断是用来进行用户是否进行更新判断
            $("#siteLogo").val($("#siteLogoName").val());
        }
        if($("#trailer").find('tr').eq(1).length==1){
            $("#trailer").val($("#trailerName").val());
        }
        if($("#trailerCover").find('tr').eq(1).length==1){
            $("#trailerCover").val($("#trailerCoverName").val());
        }
        if($("#mobiletCover").find('tr').eq(1).length==1){
            $("#mobiletCover").val($("#mobiletCoverName").val());
        }
        var openTyp;
        $("#issynchro").is(':checked')==true?openTyp=0:openTyp=1;
        $.ajax({
            type : "POST", //提交方式
            url : "${pageContext.request.contextPath}/setmessage",//路径
            data : {
                siteName :$("#siteName").val(),//网站名称
                siteKeyword:$("#siteKeyword").val(),//网站关键词
                siteDescribe:$("#siteDescribe").val(),//网站描述
                address:$("#address").val(),//地址信息
                telephone:$("#telephone").val(),//电话信息
                email:$("#email").val(),//邮箱信息
                copMessage:$("#copMessage").val(),//版权信息
                siteLogo:$("#siteLogo").val(),//站点图片
                trailerTitle:$("#trailerTitle").val(),//宣传片标题
                trailer:$("#trailer").val(),//宣传片影片
                trailerCover:$("#trailerCover").val(),//宣传封面图片
                mobiletCover:$("#mobiletCover").val(),//手机宣传封面
                siteCatalog:$("#siteCatalog").val(),//站点目录
                siteId:$("#siteId").val(), //站点id信息
                siteStatus:$("#siteStatus").val(), //站点状
                createUser:$("#createUser").val(), //站点信息的创建者
                issynchro:openTyp  //是否进行数据信息的同步（0是1否）
            },//数据，这里使用的是Json格式进行传输
            success : function(result) {//返回数据根据结果进行相应的处理
                if ( result !== "" && result !==0 && result!==1 ) {
                    checkcode(result);
                    temp=result;
                }else if(result ===0 || result ===1){
                    temp=undefined;
                    if($("#checkcode").val()==1){
                        alert("更新成功");
                    }else {
                        alert("保存成功");
                    }
                }else{
                    alert("会话失效，请重新登录");
                }
            }
        });
    }

    // function callBack(param) {
    //     $("#"+param.oldName).css("display","none");
    // }


</script>
</html>
