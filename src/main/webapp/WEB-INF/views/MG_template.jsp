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
    <link rel="shortcut icon" href="<%=rootPath%>/image/favicon.ico" />
    <link rel="bookmark" href="<%=rootPath%>/image/favicon.ico"　/>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/iconfont.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.Modal.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>
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
<!-- right -->
    <section class="MG-body">
        <div class="page-title tit-border">
            <h1 id="editorcreate" class="title-left">新增模板</h1>
            <%--<p class="none-bg"><< 返回上一页</p>--%>
        </div>

        <table class="table table3" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <label>模板编码<span style="color: red">*</span><span>:</span></label>
                    <input id="templateCode" type="text" maxlength="20" style="width:250px;">
                </td>
            </tr>
            <tr>
                <td>
                    <label>模板类型<span>:</span></label>
                    <%--<select id="templateType" name="" class="select-bg">--%>
                        <%--<option value="1">公用</option>--%>
                        <%--<option value="2">专用</option>--%>
                    <%--</select>--%>
                    <input id="templateType1" type="radio" name="ta" value="1">公用
                    <input id="templateType2" type="radio" name="ta" value="2">专用
                </td>
            </tr>
            <tr>
                <td>
                    <label>模板名称<span style="color: red">*</span><span>:</span></label>
                    <input id="templateName" type="text" maxlength="20" style="width:250px;">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>模板内容<span style="color: red">*</span><span>:</span></label>
                    <textarea id="templateContent" style="height: 500px;"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label></label>
                    <button id="taxdemo" class="btn-upload btn-ml" style="margin-left: 155px" onclick="postTemplate()">保存</button>
                    <button id="tt" class="btn-upload btn-ml" onclick="gohome()">返回</button>
                </td>
            </tr>
        </table>
    </section>
    <input id="checktype" type="hidden" value="${updateid}" />
    <input id="checkCode" type="hidden" value="" />
</div>
</body>
<script>
    var temp=undefined;
    $(function(){
        //load头部
        $(".header-page").load("<%=basePath%>/page/header");
        if($("#checktype").val()==""){
            $("#templateType2").prop('checked',true);
        }else{
            $("#editorcreate").html("修改模板");
            //$("#templateCode").attr("readonly",true);
            lookMessage();
        }
    });

      function postTemplate(){
          $("#taxdemo").attr('disabled',true);
          if(temp != undefined){
              uncheckcode(temp);
          }
          if($("#templateContent").val().length>20000){
              alert("模板内容过长！");
              $("#taxdemo").attr('disabled',false);
              return;
          }

          if($("#templateName").val().indexOf("&nbsp")>-1){
              alert("非法字符！");
              $("#templateName").css("border","1px solid red");
              return;
          }


          var size = $.ajax({url:"${pageContext.request.contextPath}/TemplateController/ajaxRepeat?templateCode="+$("#templateCode").val(),async:false});
          var flag = false;
          if($("#checktype").val()==""){
        	  if(size.responseText!=""){flag=true;}
          }else{
        	  if(size.responseText!="" && size.responseText!=$("#checktype").val()){flag=true;}
          }
          if(flag){
        	  alert("模板编码不能重复");
              $("#taxdemo").attr('disabled',false);
              return;
          }else{
        	  $.ajax({
                  type : "POST", //提交方式
                  url : "${pageContext.request.contextPath}/TemplateController/saveTemplate",//路径
                  data : {
                	  templateId:$("#checktype").val(),
                	  templateCode:$("#templateCode").val(),
                	  templateType:$('input:radio:checked').val(),
                	  templateName:$("#templateName").val(),
                	  templateContent:$("#templateContent").val()
                  },//数据，这里使用的是Json格式进行传输
                  success : function(result) {//返回数据根据结果进行相应的处理
                      if ( result !== "" && result !==3 && result !==1 && result !==0 ) {
                          checkcode(result);
                          temp=result;
                          $("#taxdemo").attr('disabled',false);
                      }else if(result === 3){
                          alert("模板名称已经存在");
                          $("#taxdemo").attr('disabled',false);
                      }else if(result !==1 || result !==0){
                          temp=undefined;
                          alert("操作成功");
                          $("#taxdemo").attr('disabled',false);
                          window.history.go(-1);
                      }else{
                          alert("会话失效，请重新登录");
                      }
                  }
              });
          }
      }

    function gohome(){
        window.history.go(-1);
    }

    function lookMessage(){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/TemplateController/lookMessage",
            data:{
                updateid:$("#checktype").val()
            },
            success:function(result){
                $("#templateCode").val(result.templateCode);
                $("#templateType").val(result.templateType);
                if(result.templateType==1){
                    $("[name='ta'][value='1']").prop("checked", "checked");
                }else if(result.templateType==2){
                    $("[name='ta'][value='2']").prop("checked", "checked");
                }
                $("#templateName").val(result.templateName);
                $("#templateContent").val(result.templateContent);
                $("#checkCode").val(result.templateCode);
                if(result.templateCode == 'index' || result.templateCode == 'search'){
                    $("#templateCode").attr('disabled','disabled');
                    $("#templateType1").attr('disabled','disabled');
                    $("#templateType2").attr('disabled','disabled');
                    $("#templateName").attr('disabled','disabled');
                }
            }
        })
    }
</script>
</html>