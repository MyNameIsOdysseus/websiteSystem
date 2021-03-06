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

	<script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/laydate.js"></script>
	<script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/iconfont.js"></script>
	<script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.Modal.js"></script>
	<script type="text/javascript" src="<%=rootPath%>/scripts/checkstyle.js"></script>
	<script type="text/javascript" src="<%=rootPath%>/components/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="MG-layout-body">
<div class="MG-layout MG-layout-admin">
	<!--header-->
	<header class="header-page MG-header"></header>
	<!--left-->
	<section class="MG-side">
		<div class="MG-side-scroll">
			<ul id="menu1" class="menu-xt">
				<li class="select">
					<svg class="icon svg-icon" aria-hidden="true">
						<use xlink:href="#iconjibenxinxi"></use>
					</svg>
					<a href="<%=basePath%>/page/demo">待审内容</a>
				</li>
				<li>
					<svg class="icon svg-icon" aria-hidden="true">
						<use xlink:href="#iconhuandengpian"></use>
					</svg>
					<a href="<%=basePath%>/page/MG_aduitPage">已审内容</a>
				</li>

			</ul>
		</div>
	</section>
	<section class="MG-body">

		<div class="page-title">
			<h1 id="doublelistname" class="title-left">待审内容</h1>
			<input id="ImgName" type="text" placeholder="输入标题名称">
			<input id="TitleName" type="text" placeholder="选择起始时间">
			<img onclick="WdatePicker({el:'TitleName'})" src="<%=rootPath%>/components/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
			-
			<input id="ChanName" type="text" placeholder="选择结束时间">
			<img onclick="WdatePicker({el:'ChanName'})" src="<%=rootPath%>/components/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
			<input type="button" onclick="updatetable()" value=""  class="search-btn">

		</div>
		<table class="table2" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<%--<th style="width: 100px;"></th>--%>
				<th>标题</th>
				<th>发布时间</th>
				<th>排序</th>
				<th>栏目</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody id="livetable">

			</tbody>
		</table>

			<div class="pagination">
				<div class="pull-left paging">
					<select id="changePageSize" onchange="updatetable()">
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
							<span><input id="skipnum" maxlength="3" type="text" onblur="blurFunc()" ></span>
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
						<label>步骤序号<span>*</span>:</label>
						<select id="processactiNum">
							<option id="num0" style="display: none" value="0"></option>
							<option id="num1">1</option>
							<option id="num2">2</option>
							<option id="num3">3</option>
							<option id="num4">4</option>
							<option id="num5">5</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>参与人<span>*</span>:</label>
						<input id="choose1" name="demo" type="radio" value="选择人员" onclick="showdemo()" >选择人员
						<input id="choose2" name="demo" type="radio" value="发起人的上级领导" onclick="hidddemo()" checked="checked">发起人的上级领导
					</td>
				</tr>
				<tr id="people" style="display: none">
					<td>
						<label>具体人员<span>*</span>:</label>
						<select id="knowPeople">
							<option value="123">张三</option>
							<option value="124">李四</option>
							<option value="125">王五</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div class="MG-modal-bottom">
			<button  class="btn-upload" onclick="post();"><strong>保存</strong ></button >
			<button  class="btn-upload btn-bg-dark" onclick ="closeDiv();">取消</button >
		</div>
		<a href="javascript:void(0);" class="close-MG-modal" onclick ="closeDiv();"></a >
		<%--<input id="checktype" type="hidden" value="${updateid}" />--%>
		<%--<input id="processAtId" type="hidden"  />--%>
		<input id="username"  type="hidden"  value="${sessionScope.UserSession.username}" />
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
	
	function showdemo(){
		$("#people").css("display","block");
		$("#choose2").removeAttr("checked");
		$("#choose1").attr("checked","checked");
	}
	function hidddemo() {
		$("#people").css("display","none");
		$("#choose1").removeAttr("checked");
		$("#choose2").attr("checked","checked");
	}

	function updatetable(){
		var ImgName=$("#ImgName").val();
		var TitleName=$("#TitleName").val();
		var ChanName=$("#ChanName").val();

		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/article/getWaitAuditMessage",
			data:{
				currentpage:pageindex,
				pageSize:$("#changePageSize").val(),
				ImgName:ImgName,
				TitleName:TitleName,
				ChanName:ChanName,
				username:$("#username").val()
			},
			async:false,
			success:function(result){
				for(var a=0;a<result.auditLsit.length;a++){
					result.auditLsit[a].publisdate=result.auditLsit[a].publisdate.substr(0,10);
				}
				$("#livetable").empty();
				var xx=new Array("auditname","publisdate","sorted","channame","auditid");
				var htmltable=getMessage(result.auditLsit,xx,4);
				$("#livetable").html(htmltable);
				$("#_indexNum").text(pageindex);
				setMaxindex(result,parseInt($("#changePageSize").val()));
			}
		})
	}

	function audit(id) {
        window.location.href ="${pageContext.request.contextPath}/article/updatePage?updateid="+id;
	}

</script>
</html>