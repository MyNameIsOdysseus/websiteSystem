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
    <style>
    .table tr td input{width:59.5%;}
    	.sel-wit{
    		width:60% !important;
    	}
    </style>
</head>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
	<header class="header-page"></header>
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
		            <li class="select">
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
            <h1 id="editorcreate" class="title-left">新增栏目</h1>
            <%--<p class="none-bg"><< 返回上一页</p>--%>
        </div>

        <table class="table table3" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <label>栏目名称<span style="color: red">*</span><span>:</span></label>
                    <input id="chanName" type="text" maxlength="50">
                </td>
                <td  style="display:block;">
                    <label>栏目模板<span>:</span></label>
                    <select id="chanTemplate" name="" class="select-bg sel-wit">
                        <option value="1">选择模板</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>所属栏目<span>:</span></label>
                    <select id="belongChan" name="" class="select-bg sel-wit">
                        <option value="0">选择所属栏目（不选默认为一级栏目）</option>
                    </select>
                </td>
                <td id="demolanmu" style="display: block">
                    <label>信息模板<span>:</span></label>
                    <select id="infoTemplate" name=""  class="select-bg sel-wit">
                        <option value="1">选择模板</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>栏目类型<span>:</span></label>
                    <select id="chanType" name="" class="select-bg sel-wit" onchange="changeStyle()">
                        <option value="1">单篇</option>
                        <option value="2" selected="selected">新闻列表</option>
                        <option value="3">特殊表单（留言等）</option>
                    </select>
                </td>
                <td id="demoxinxi">
                    <label>栏目流程<span>:</span></label>
                    <select id="processId" name="" class="select-bg sel-wit">
                        <option value="">默认不选择流程</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>栏目描述<span>:</span></label>
                    <textarea id="chenDisc" maxlength="1000"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>栏目图片<span>:</span></label>
                    <%--<input type="text">--%>
                    <%--<button class="btn-upload">选择图片</button>--%>
                    <%--<jsp:include page="/components/pluupload/upload.jsp" flush="true">--%>
                        <%--<jsp:param name="path"  value="informations"/>--%>
                        <%--<jsp:param name="tableName"  value="chanImage"/>--%>
                        <%--<jsp:param name="fileName"  value="chanImageFileame"/>--%>
                        <%--<jsp:param name="saveName"  value="chanImagesaveName"/>--%>
                        <%--<jsp:param name="canModify"  value="1"/>--%>
                        <%--<jsp:param name="canDelete"  value="1"/>--%>
                        <%--<jsp:param name="fileSizeLimit"  value="1024KB"/>--%>
                        <%--<jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>--%>
                        <%--<jsp:param name="button"  value="上传图片"/>--%>
                        <%--<jsp:param name="buttonText"  value="请选择图片"/>--%>
                        <%--<jsp:param name="uploadCallBack"  value=""/>--%>
                        <%--<jsp:param name="deleteCallBack"  value=""/>--%>
                        <%--<jsp:param name="queueSizeLimit"  value="1"/>--%>
                        <%--<jsp:param name="saveFileName"  value=""/>--%>
                    <%--</jsp:include>--%>

                    <div id="changeDemo">

                    </div>

                </td>
            </tr>
            <%--<tr>--%>
                <%--<td id="chanImageOld" style="padding-left: 150px;">--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td colspan="2">
                    <label>栏目banner<span>:</span></label>
                    <%--<jsp:include page="/components/pluupload/upload.jsp" flush="true">--%>
                        <%--<jsp:param name="path"  value="informations"/>--%>
                        <%--<jsp:param name="tableName"  value="chanBanner"/>--%>
                        <%--<jsp:param name="fileName"  value="chanBannerFileame"/>--%>
                        <%--<jsp:param name="saveName"  value="chanBannersaveName"/>--%>
                        <%--<jsp:param name="canModify"  value="1"/>--%>
                        <%--<jsp:param name="canDelete"  value="1"/>--%>
                        <%--<jsp:param name="fileSizeLimit"  value="1024KB"/>--%>
                        <%--<jsp:param name="fileTypeExts" value="*.jpg;*.png;*.bmp;*.gif"/>--%>
                        <%--<jsp:param name="button"  value="上传图片"/>--%>
                        <%--<jsp:param name="buttonText"  value="请选择图片"/>--%>
                        <%--<jsp:param name="uploadCallBack"  value=""/>--%>
                        <%--<jsp:param name="deleteCallBack"  value=""/>--%>
                        <%--<jsp:param name="queueSizeLimit"  value="1"/>--%>
                        <%--<jsp:param name="saveFileName"  value=""/>--%>
                    <%--</jsp:include>--%>

                    <div id="changeDemo2">

                    </div>

                    <span class="table-tip">（若当前栏目没有banner图，则使用上级栏目banner图）</span>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td id="chanBannerOld" style="padding-left: 150px;">--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td colspan="2">
                    <label>栏目外链<span>:</span></label>
                    <input id="linkAddress" type="text" maxlength="50" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')">
                    <input id="openlink" type="checkbox">启用
                    <%--<span class="table-tip">（需包含 http:// 或 https://）</span>--%>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>打开方式<span style="color: red">*</span><span>:</span></label>
                    <input name="demo" type="radio" value="1">_blank — 新窗口或新标签
                    <input name="demo" type="radio" value="2">_self — 当前窗口或标签
                    <input name="demo" type="radio" value="3">_top — 不包含框架的当前窗口或标签
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>排序<span style="color: red">*</span><span>:</span></label>
                    <input id="sortCode" type="text" maxlength="4" oninput = "value=value.replace(/[^\d]/g,'')" style="width:79.5% !important;">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label></label>
                    <button class="btn-upload btn-ml" style="margin-left: 155px;" onclick="postChan()">保存</button>
                    <button class="btn-upload btn-ml" onclick="gohome()">返回</button>
                </td>
            </tr>
        </table>
    </section>
    <input id="checktype" type="hidden" value="${updateid}" />
    <input id="typeStatus" type="hidden"  />
    <input id="chanLevelCode" type="hidden">
</div>
</body>
<script>
    var temp=undefined;
    $(function(){
        //load头部

        $("#changeDemo").empty();
        $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=chanImage&fileName=chanImageFileame&" +
            "saveName=chanImagesaveName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName=111");
        $("#changeDemo2").empty();
        $("#changeDemo2").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=chanBanner&fileName=chanBannerFileame&" +
            "saveName=chanBannersaveName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName=222");

        updateList();
        updateprocess();
        updateTemplate();
        $(".header-page").load("<%=basePath%>/page/header");
        if($("#checktype").val()==""){
            $("#sortCode").val(1000);
        }else{
            $("#editorcreate").html("修改栏目");
            lookMessage();
        }

    });


    function updateList(){
        $.ajax({
            type:"POST",
            async:false,
            url:"${pageContext.request.contextPath}/chan/getAllMessage",
            data:{
            },
            success:function(result){
                var xx=new Array("chanCode","chanName");
                // $("#belongChan").empty();
                $("#belongChan").append(addSelectOption(result,xx));
            }
        })
    }

    function changeStyle(){
        if($("#chanType").find("option:checked").text()=="单篇"){
            $("#demolanmu").css('display','none');
            $("#demoxinxi").css('display','none');
        }else{
            $("#demolanmu").css('display','block');
            $("#demoxinxi").css('display','block');
        }
    }

    function updateTemplate(){
        $.ajax({
            type:"POST",
            async:false,
            url:"${pageContext.request.contextPath}/TemplateController/getList",
            data:{
            },
            success:function(result){
                var xx=new Array("templateId","templateName");
                // $("#belongChan").empty();
                $("#chanTemplate").append(addSelectOption(result,xx));
                $("#infoTemplate").append(addSelectOption(result,xx));
            }
        })
    }

    function updateprocess() {
        $.ajax({
            type:"POST",
            async:false,
            url:"${pageContext.request.contextPath}/Process/getAllMessage",
            data:{
            },
            success:function(result){
                var xx=new Array("processId","processName");
                $("#processId").append(addSelectOption(result,xx));
            }
        })
    }

      function postChan(){
          $("#chanName").css("border","");
          if(temp != undefined){
              uncheckcode(temp);
          }

          if($("#linkAddress").val()!=""){
              var type =IsURL($("#linkAddress").val())==false;
              if(type){
                  return alert("链接地址需以http或者https开头");
              }
          }

          if($("#chanName").val().indexOf("&nbsp")>-1){
              alert("非法字符！");
              $("#chanName").css("border","1px solid red");
              return;
          }



          // var type =IsURL($("#linkAddress").val())==false;
          // if(type){
          //     return alert("链接地址需以http或者https开头");
          // }

          var openlin;
          var openTyp;
          $("#openlink").is(':checked')==true?openlin=0:openlin=1;
          $("[name='demo']:checked").val() !=undefined?openTyp=$("[name='demo']:checked").val():openTyp=undefined;
          $.ajax({
              type : "POST", //提交方式
              url : "${pageContext.request.contextPath}/chan/setChanMessage",//路径
              data : {
                  chanId:$("#checktype").val(),
                  chanName:$("#chanName").val(),
                  belongChan:$("#belongChan").val(),
                  belongChanname:$("#belongChan").find("option:checked").text(),
                  chanTemplate:$("#chanTemplate").val(),
                  infoTemplate:$("#infoTemplate").val(),
                  chanTemplatename:$("#chanTemplate").find("option:checked").text(),
                  infoTemplatename:$("#infoTemplate").find("option:checked").text(),
                  processId:$("#processId").val(),
                  chanType:$("#chanType").val(),
                  chenDisc:$("#chenDisc").val(),
                  chanImage:$("#chanImagesaveName").val(),
                  chanBanner:$("#chanBannersaveName").val(),
                  openlink:openlin,
                  linkAddress:$("#linkAddress").val(),
                  openType:openTyp,
                  sortCode:$("#sortCode").val(),
                  chanStatus:$("#typeStatus").val(),
                  chanLevelCode:$("#chanLevelCode").val()
              },//数据，这里使用的是Json格式进行传输
              success : function(result) {//返回数据根据结果进行相应的处理
                  if ( result !== "" && result !==3 && result !==4 && result !==9  && result !==0 && result !==1 && result !==8) {
                      checkcode(result);
                      temp=result;
                  }else if(result === 3){
                        alert("保存失败，栏目名已存在，请重新更改栏目名");
                      $("#chanName").css("border","1px solid red");
                  }else if(result === 4){
                        alert("栏目层级不能超过4级，请重新选择所属栏目");
                  }else if(result === 9){
                        alert("当前栏目为列表栏目，并且拥有多条信息，所以不能更改为单篇类型栏目");
                  }else if(result ===8){
                        alert("当前栏目下存在子栏目，禁止变更栏目所属");
                  }else if(result ===0 || result ===1) {
                      temp=undefined;
                      alert("操作成功");
                      window.history.go(-1);
                  }else{
                      alert("会话失效，请重新登录")
                  }
              }
          });
      }

      function gohome(){
          window.history.go(-1);
      }

    function lookMessage(){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/chan/lookMessage",
            data:{
                updateid:$("#checktype").val()
            },
            success:function(result){
                $("#chanLevelCode").val(result.chanLevelCode);
                $("#chanName").val(result.chanName);
                $("#belongChan").val(result.belongChan);
                $("#chanTemplate").val(result.chanTemplate);
                $("#infoTemplate").val(result.infoTemplate);
                $("#chanType").val(result.chanType);
                $("#chenDisc").val(result.chenDisc);
                $("#linkAddress").val(result.linkAddress);
                $("#sortCode").val(result.sortCode);
                $("#processId").val(result.processId);
                $("#typeStatus").val(result.chanStatus);
                result.openlink==0?$("#openlink").prop("checked",true):null;
                $("input[name='demo']").eq(result.openType-1).attr("checked",'checked');
                //$("#chanImageOld").html(showOldMessage(result.chanImage,"chanImageOld",result.chanImage));
                //$("#chanBannerOld").html(showOldMessage(result.chanBanner,"chanBannerOld",result.chanBanner));
                $("#changeDemo").empty();
                $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=chanImage&fileName=chanImageFileame&" +
                    "saveName=chanImagesaveName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName="+result.chanImage);
                $("#changeDemo2").empty();
                $("#changeDemo2").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=chanBanner&fileName=chanBannerFileame&" +
                    "saveName=chanBannersaveName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName="+result.chanBanner);
                if(result.chanType=='1'){
                    $("#demolanmu").css('display','none');
                    $("#demoxinxi").css('display','none');
                }else{
                    $("#demolanmu").css('display','block');
                    $("#demoxinxi").css('display','block');
                }
            }
        })
    }


    jQuery(function($){
        var el;

        $("select").each(function() {
            el = $(this);
            el.data("origWidth", el.css("width"));
        })
            .focusin(function(){
                el=$(this);
                el.css("width", "auto");
            })
            .bind("blur change ", function(){
                el = $(this);
                el.css("width", el.data("origWidth"));
            });
    });



</script>
</html>