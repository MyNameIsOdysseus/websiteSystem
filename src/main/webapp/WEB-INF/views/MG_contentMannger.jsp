<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/4/15
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
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
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.flushBuffer();
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
    <script type="text/javascript" src="<%=rootPath%>/components/kindereditor4.11/plugins/code/prettify.js"></script>
    <style>
    	/*table{*/
    			/*table-layout: auto !important;*/
			  /*}*/
        /*#testdemo2 tr td{
            white-space: unset;
            overflow: hidden;
            text-overflow: ellipsis;
        }*/
        #testdemo2 td{
            white-space:pre-line;
            word-wrap: break-word;
            word-break: break-all;
        }
    </style>
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
    <section id="singleList" class="MG-body" style="display: none">
        <div class="page-title tit-border">
            <h1 id="singleListname" class="title-left">栏目管理</h1>
        </div>

        <table class="table table3" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="2">
                    <label>标题<span style="color: red">*</span><span>:</span></label>
                    <input id="newssubhead" type="text" maxlength="100" style="width:80%;">
                </td>
            </tr>
            <tr>
                <td width=50%>
                    <label>发布时间<span style="color: red">*</span><span>:</span></label>
                    <input id="publishdate" type="text" class="ymt" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    <img onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="<%=rootPath%>/components/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">

                </td>
                <td width=50%>
                    <label>排序<span>:</span></label>
                    <input id="sortedcode" maxlength="4" type="text" style="width: 60%;" oninput = "value=value.replace(/[^\d]/g,'')">
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
                    <label>来源<span>:</span></label>
                    <input id="informationsource" maxlength="100" type="text" style="width: 80%;">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>简要介绍<span>:</span></label>
                    <textarea id="simpleintroduce" maxlength="200" ></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label>关键字<span>:</span></label>
                    <input id="keywords" type="text" maxlength="100" style="width: 80%;">
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
                    <input id="author" type="text" maxlength="100" style="width: 65%;">
                </td>
                <td >
                    <label>发布人<span>:</span></label>
                    <input id="issuer" type="text" maxlength="100" style="width: 60%;">
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
                    <button id="fansi" class="btn-upload btn-ml" style="margin-left:155px;" onclick="postMessage()">保存</button>
                    <button id="gohome" style="display: none; float: right;margin-right: 60%;" class="btn-upload" onclick="gohome()">返回</button>
                </td>
            </tr>
        </table>
    </section>

    <section id="doublelist" class="MG-body" style="display: none">
        <div class="page-title">
            <h1 id="doublelistname" class="title-left">栏目管理</h1>

            <input id="ImgName" type="text" placeholder="标题">
            <input id="TitleName" type="text" class="ym" placeholder="起始时间" onFocus="WdatePicker({lang:'zh-cn'})">
            <img onclick="WdatePicker({el:'TitleName'})" src="<%=rootPath%>/components/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
            -
            <input id="ChanName" type="text" class="ym" placeholder="结束时间" onFocus="WdatePicker({lang:'zh-cn'})">
            <img onclick="WdatePicker({el:'ChanName'})" src="<%=rootPath%>/components/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
            <input type="button" onclick="updatetable()" value=""  class="search-btn">

            <a class="btn-add" id="btn-add" href="#" onclick="addmessage()" >新增</a>
        </div>


        <table id="testdemo2" class="table2" cellpadding="0" cellspacing="0">
            <thead>
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>状态</th>
                <th>时间</th>
                <th>操作</th>
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
                        <span><input id="skipnum" maxlength="3" type="text"  ></span>
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
    <input id="chanId"  type="hidden"/>
    <input id="checkIsHaveProcess" type="hidden"/>
    <input id="isMutiy" type="hidden" />
    <input id="articleid" type="hidden"/>
</div>
<div id="modalDiv" >
    <div id="popContainer" class="MG-modal">
        <div id="checkName" class="MG-modal-title MG-MOdal_s" id="divtitle" >
            	文章推送
            <a href="javascript:void(0);" class="close-MG-modal" onclick ="closeDiv();"></a >
        </div>
        
        <div class="MG-modal-body" >
        	<div  class="MG-modal-tit" ></div>
        	<div id="articleNameT" class="MG-modal-new-list" >新闻列表</div>
        	
            <input id="articleIdvalue" type="hidden" />
            <table id="showName" class="tab-modal" cellpadding="0" cellspacing="0">

            </table>
            <div class="MG-modal-new-list" >推送设置</div>
            <table id="checktable" class="tab-modal" cellpadding="0" cellspacing="0">
            	<tr>
                    <td width="5%"><input id="pubdel"  type="checkbox"></td>
                    <td>同时删除当前栏目下此条新闻</td>
            	</tr>
            	<tr>
                    <td width="5%"><input id="pubnow"  type="checkbox"></td>
                    <td>将新闻发布时间更新为当前时间</td>
            	</tr>
            	<tr>
                    <td width="5%"><input id="pubzero"  type="checkbox"></td>
                    <td>将新闻点击量重置为0</td>
            	</tr>
            </table>
         <!--   <table  > 
                <tr id="showName">

                </tr>
                <tr id="checktable">
                    <td>
                        
                        <label>同时删除当前栏目下此条新闻</label>
                    </td>
                    <td>
                        <input id="pubnow"  type="checkbox">
                        <label>将新闻发布时间更新为当前时间</label>
                    </td>
                    <td>
                        <input id="pubzero"  type="checkbox">
                        <label>将新闻点击量重置为0</label>
                    </td>
                </tr>
            </table> -->
            <div class="MG-modal-btm">
                <button  class="btn-upload" onclick="post();"><strong>确定</strong ></button >
                <button  class="btn-upload btn-bg-dark" onclick ="closeDiv();">取消</button >
            </div>
        </div>

        
    </div>
</div>
</body>
<script>
    var userid="${sessionScope.UserSession.username}";
    var userUid="${sessionScope.UserSession.userId}";
    var temp=undefined;  //全局变量  主要是用来对提示变量的属性进行重置操作
    var pageindex=1;     //当前的分页的下标页
    var maxindexnum=0;   //当前页面所存在数据支持的最大分页的下标页
    var checkjump=0;

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



    var zNodes =[];

    $(function(){
        if(userid==undefined || userid==""){
            alert("您没有登录，请您先登录");
            window.location.href="${pageContext.request.contextPath}/login"
        }else{
            $("#demoxx").text(userid);
        }
        if(userid=='admin'){
            updateMessage();
        }else{
            demotest();
        }
    });

    <%--function upload(){--%>
        <%--var upjson={--%>
            <%--url:'<%=rootPath%>',--%>
            <%--path:'informations',--%>
            <%--folder:'upload',--%>
            <%--maxFileSize:'1024.0mb',--%>
            <%--browserButton:'uploader_image',--%>
            <%--up_container:'div_image',--%>
            <%--filters:{--%>
                <%--title:'*.jpg;*.png;*.bmp;*.gif',--%>
                <%--extensions:'<%=("*.jpg;*.png;*.bmp;*.gif".replaceAll(";\\*.",",").replaceAll("\\*.",""))%>'--%>
            <%--},--%>
            <%--tableName:'image',     //上传的tableName唯一--%>
            <%--queueSize:'1', //最多允许上传的文件个数--%>
            <%--canDel:'1',    //是否可以删除，0否，1是--%>
            <%--fileName:'imageFileame',   //文件名--%>
            <%--saveName:'imageName',   //保存名--%>
            <%--uploadCallBack:'', //上传回调函数--%>
            <%--delCallBack:'' //删除回调函数--%>
        <%--};--%>
        <%--mg.pluUploader._mgPluUploader(upjson);--%>
    <%--}--%>

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


    Date.prototype.format = function(format){ var args = { "M+" : this.getMonth() + 1, "d+" : this.getDate(), "h+" : this.getHours(), "m+" : this.getMinutes(), "s+" : this.getSeconds(), "q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter

        "S" : this.getMilliseconds() }; if(/(y+)/.test(format)) format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length)); for(var i in args) { var n = args[i]; if(new RegExp("("+ i +")").test(format)) format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length)); } return format;};

    // $(function(){
    //     $("#publishdate").val(new Date().format("yyyy-MM-dd"));
    //
    // });

    var setting = {
        view: {
            showLine: false,
            showIcon: false,
            selectedMulti: false,
            dblClickExpand: false,
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };

    function onClick(e,treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
        $("#fansi").css("display","block");
        clear();
        $("#articleid").val("");
        $("#changeDemo").empty();
        $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +
            "saveName=imageName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName=1");
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/chan/getChanMessageById",
            data:{
                chanId:treeNode.id
            },
            success:function(result){
                var ImgName=$("#ImgName").val();
                var TitleName=$("#TitleName").val();
                var ChanName=$("#ChanName").val();
                $("#chanId").val(result.chanId);
                $.ajax({
                    type:"POST",
                    url:"${pageContext.request.contextPath}/article/getArticleMessage",
                    data:{
                        currentpage:pageindex,
                        pageSize:$("#changePageSize").val(),
                        ImgName:ImgName,
                        TitleName:TitleName,
                        ChanName:ChanName,
                        chanid:result.chanId,
                        userid:userUid,
                        username:userid
                    },
                    async:false,
                    success:function(resultmessage){
                        if(resultmessage.article.length>0){
                            if(result.chanType=="1"){
                                if(result.chanType=="1" && resultmessage.article[0].stylestatus==false){
                                    $("#fansi").css("display","none");
                                }
                                $("#articleid").val(resultmessage.article[0].articleId);
                                $("#changeDemo").empty();
                                $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +
                                    "saveName=imageName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName="+resultmessage.article[0].image);
                                $("#upsubhead").val(resultmessage.article[0].upsubhead);
                                $("#downsubhead").val(resultmessage.article[0].downsubhead);
                                $("#newssubhead").val(resultmessage.article[0].newssubhead);
                                //$("#publishdate").val(resultmessage.article[0].publishdate.substr(0,10));
                                //$("#publishdate").val(new Date(resultmessage.article[0].publishdate).format("yyyy-MM-dd hh:mm:ss"));
                                $("#publishdate").val(NewDate(resultmessage.article[0].publishdate).format("yyyy-MM-dd hh:mm:ss"));
                                $("#informationsource").val(resultmessage.article[0].informationsource);
                                $("#simpleintroduce").val(resultmessage.article[0].simpleintroduce);
                                $("#keywords").val(resultmessage.article[0].keywords);
                                //$("#image").val(resultmessage.article[0].image);
                                resultmessage.article[0].isopenlink==0?$("#isopenlink").prop("checked",true):$("#isopenlink").prop("checked",false);
                                $("#linkaddress").val(resultmessage.article[0].linkaddress);
                                resultmessage.article[0].isstick==0?$("#isstick").prop("checked",true):$("#isstick").prop("checked",false);
                                resultmessage.article[0].isrecommend==0?$("#isrecommend").prop("checked",true):$("#isrecommend").prop("checked",false);
                                resultmessage.article[0].refusecopy==0?$("#refusecopy").prop("checked",true):$("#refusecopy").prop("checked",false);
                                $("#sortedcode").val(resultmessage.article[0].sortedcode);
                                editor.html(resultmessage.article[0].articleContentId);
                                $("#author").val(resultmessage.article[0].author);
                                $("#issuer").val(resultmessage.article[0].issuer);
                            }else{
                                pageindex=1;
                                if(resultmessage.article == null){
                                    alert("所选区间超越数据极限");
                                    return;
                                }

                                for(var a=0;a<resultmessage.article.length;a++){
                                    resultmessage.article[a].auditStatus==0?resultmessage.article[a].auditStatus="审核通过":null;
                                    resultmessage.article[a].auditStatus==1?resultmessage.article[a].auditStatus="审核中":null;
                                    //resultmessage.article[a].publishdate=resultmessage.article[a].publishdate.substr(0,10);
                                    //resultmessage.article[a].publishdate=new Date(resultmessage.article[a].publishdate).format("yyyy-MM-dd hh:mm:ss");
                                    resultmessage.article[a].publishdate = NewDate(resultmessage.article[a].publishdate).format("yyyy-MM-dd hh:mm:ss");
                                }
                                $("#livetable").empty();
                                var xx=new Array("sortedcode","newssubhead","auditStatus","publishdate","articleId");
                                var htmltable=getMessage(resultmessage.article,xx,3);
                                $("#livetable").html(htmltable);
                                $("#_indexNum").text(pageindex);
                                setMaxindex(resultmessage,parseInt($("#changePageSize").val()));
                            }
                        }else{
                            $("#livetable").empty();
                            $("#publishdate").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
                            $("#sortedcode").val(1000);
                            console.log("查询无数据");
                        }

                    }
                })
                result.chanType=="1"?$("#singleList").css("display","block") && $("#doublelist").css("display","none") && $("#singleListname").html(treeNode.name) :$("#doublelist").css("display","block") && $("#singleList").css("display","none") && $("#doublelistname").html(treeNode.name);
                $("#chanId").val(result.chanId);
                $("#isMutiy").val(result.chanType);
                if(result.processId ==null){
                    $("#checkIsHaveProcess").val(0);
                }else{
                    $("#checkIsHaveProcess").val(1);
                }

            }
        })
    }

    function flushTree(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    }

    function addmessage(){
        $("#fansi").removeAttr("disabled");
        clear();
        $("#doublelist").css("display","none");
        $("#singleList").css("display","block");
        $("#singleListname").html($("#doublelistname").html());
        $("#gohome").css("display","block");
        $("#livetable").empty();
        $("#publishdate").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
        $("#sortedcode").val(1000);
        checkjump=1;
    }

    function gohome(){
        $("#doublelist").css("display","block");
        $("#singleList").css("display","none");
        $("#gohome").css("display","none");
        updatetable();
        checkjump=0;
    }

    function demotest(){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/user/getUserRight",
            data:{
                userid:userUid
            },
            success:function(result){
                for(var b=0;b<result.length;b++){
                    var temp1={};
                    var arr=[];
                    var temp3={};
                    if(result[b].length==1){
                        temp1={
                            id:result[b][0].chanLevelCode,
                            name:result[b][0].chanName
                        }
                    }else if(result[b].length>1){
                        for(var m=0;m<result[b].length;m++) {
                            if(m==0){
                                temp1={
                                    id:result[b][m].chanLevelCode,
                                    name:result[b][m].chanName
                                }
                            }else{
                                if(temp1.id==result[b][m].chanLevelCode.slice(0,temp1.id.length) && (result[b][m].chanLevelCode.length-temp1.id.length==4)){
                                    temp1.children=arr;
                                    temp3={
                                        id:result[b][m].chanLevelCode,
                                        name:result[b][m].chanName
                                    }
                                    arr.push(temp3);
                                }else{
                                    arr=[];
                                    bbq:
                                        for(var x=0;x<temp1.children.length;x++){
                                            if(temp1.children[x].id == result[b][m].chanLevelCode.slice(0,temp1.children[x].id.length) && (result[b][m].chanLevelCode.length-temp1.children[x].id.length==4) ){
                                                if(temp1.children[x].children==undefined){
                                                    temp1.children[x].children=arr;
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName
                                                    }
                                                    arr.push(temp3);
                                                    break;
                                                }else{
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName
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
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName
                                                                    }
                                                                    arr.push(temp3);
                                                                    break bbq;
                                                                }else{
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName
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
                //updateList();



                // var temp1;
                // for(var b=0;b<result.length;b++){
                //     if(result[b].length==1){
                //         temp1={
                //             //id:result[b][0].chanLevelCode,
                //             name:result[b][0].chanName
                //         }
                //     }else if(result[b].length>1){
                //         //var temp1={};
                //         temp1={};
                //         var arr=[];
                //         var type=0;
                //         for(var a=result[b].length-1;a>=0;a--){
                //
                //             if(type==1){
                //                 temp1={};
                //                 temp1.children=arr;
                //                 arr=[];
                //                 type=0;
                //             }
                //
                //             if(a-1>=0){
                //                 if(result[b][a].chanLevel>result[b][a-1].chanLevel){
                //                     //temp1.id=result[b][a].chanLevelCode;
                //                     temp1.name=result[b][a].chanName;
                //                     arr.push(temp1);
                //                     temp1={};
                //                     type=1;
                //                 }
                //                 if(result[b][a].chanLevel==result[b][a-1].chanLevel){
                //                     //temp1.id=result[b][a].chanLevelCode;
                //                     temp1.name=result[b][a].chanName;
                //                     arr.push(temp1);
                //                     temp1={};
                //                 }
                //             }else{
                //                 //temp1.id=result[b][a].chanLevelCode;
                //                 temp1.name=result[b][a].chanName;
                //                 arr.push(temp1);
                //             }
                //         }
                //     }
                //     zNodes.push(temp1);
                // }
                // flushTree();
                //updateList();
            }
        })


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
                        temp1={
                            id:result[b][0].chanLevelCode,
                            name:result[b][0].chanName
                        }
                    }else if(result[b].length>1){
                        for(var m=0;m<result[b].length;m++) {
                            if(m==0){
                                temp1={
                                    id:result[b][m].chanLevelCode,
                                    name:result[b][m].chanName
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
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName
                                                    }
                                                    arr.push(temp3);
                                                    break;
                                                }else{
                                                    temp3={
                                                        id:result[b][m].chanLevelCode,
                                                        name:result[b][m].chanName
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
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName
                                                                    }
                                                                    arr.push(temp3);
                                                                    break bbq;
                                                                }else{
                                                                    temp3={
                                                                        id:result[b][m].chanLevelCode,
                                                                        name:result[b][m].chanName
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

                // var temp1;
                // for(var b=0;b<result.length;b++){
                //     if(result[b].length==1){
                //         temp1={
                //             //id:result[b][0].chanLevelCode,
                //             name:result[b][0].chanName
                //         }
                //     }else if(result[b].length>1){
                //         //var temp1={};
                //         temp1={};
                //         var arr=[];
                //         var type=0;
                //         for(var a=result[b].length-1;a>=0;a--){
                //
                //             if(type==1){
                //                 temp1={};
                //                 temp1.children=arr;
                //                 arr=[];
                //                 type=0;
                //             }
                //
                //             if(a-1>=0){
                //                 if(result[b][a].chanLevel>result[b][a-1].chanLevel){
                //                     //temp1.id=result[b][a].chanLevelCode;
                //                     temp1.name=result[b][a].chanName;
                //                     arr.push(temp1);
                //                     temp1={};
                //                     type=1;
                //                 }
                //                 if(result[b][a].chanLevel==result[b][a-1].chanLevel){
                //                     //temp1.id=result[b][a].chanLevelCode;
                //                     temp1.name=result[b][a].chanName;
                //                     arr.push(temp1);
                //                     temp1={};
                //                 }
                //             }else{
                //                 //temp1.id=result[b][a].chanLevelCode;
                //                 temp1.name=result[b][a].chanName;
                //                 arr.push(temp1);
                //             }
                //         }
                //     }
                //     zNodes.push(temp1);
                // }
                // flushTree();
            }
        })
    }



    function postMessage(){
        $("#fansi").attr("disabled","disabled");
        var isopenlink = $("#isopenlink").prop('checked')==true?isopenlink=0:isopenlink=1;
        var isstick = $("#isstick").prop('checked')==true?isstick=0:isstick=1;
        var isrecommend = $("#isrecommend").prop('checked')==true?isrecommend=0:isrecommend=1;
        var refusecopy = $("#refusecopy").prop('checked')==true?refusecopy=0:refusecopy=1;

        if($("#linkaddress").val()!=""){
            var type =IsURL($("#linkaddress").val())==false;
            if(type){
                $("#fansi").removeAttr("disabled");
                return alert("链接地址需以http或者https开头");
            }
        }
        // var type =IsURL($("#linkaddress").val())==false;
        // if(type){
        //     return alert("链接地址需以http或者https开头");
        // }

        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/setMessage",
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
                belongChan:$("#chanId").val(),
                isMutiy:$("#isMutiy").val(),
                createUser:userUid
            },
            success:function(result){
                $("#fansi").removeAttr("disabled");
                if(result !=="" && result !==2 && result !==8 && result !==1 && result !==0 && result !==3){
                    checkcode(result);
                    temp=result;
                    return;
                }else if(result===3){
                    alert("标题名称需唯一，该名称已经存在！");
                }else if(result===2){
                    alert("信息保存失败，因为流程环节存在缺失上级领导，请查看流程配置！")
                    return;
                }else if(result===8){
                    alert("该信息第一步审核为作者的上级领导，所以作者必须真实存在！");
                    return;
                }else if(result===0 || result===1){
                    temp=undefined;
                    alert("操作成功");
                    updatetable();
                }else{
                    alert("会话失效，请重新登录");
                }
                if(checkjump==1){
                    $("#doublelist").css("display","block");
                    $("#singleList").css("display","none");
                    $("#gohome").css("display","none");
                    checkjump=0;
                    updatetable();
                }
            }
        })
    }

    function updat(id){
        $("#fansi").removeAttr("disabled");
        $("#doublelist").css("display","none");
        $("#singleList").css("display","block");
        $("#singleListname").html($("#doublelistname").html());
        $("#gohome").css("display","block");
        checkjump=1;
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/getMessageByName",
            data:{
                updateid:id
            },
            success:function(result){
                $("#articleid").val(result.articleId);
                $("#changeDemo").empty();

                <%--$("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +--%>
                    <%--"saveName=imageName&fileTypeExts=*.jpg&button=上传附件&buttonText=请选择文件&canModify=1&canDelete=1&saveFileName="+result.image);--%>

                $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +
                    "saveName=imageName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName="+result.image);



                $("#upsubhead").val(result.upsubhead);
                $("#downsubhead").val(result.downsubhead);
                $("#newssubhead").val(result.newssubhead);
                //$("#publishdate").val(result.publishdate.substr(0,10));
                //$("#publishdate").val(new Date(result.publishdate).format("yyyy-MM-dd hh:mm:ss"));
                $("#publishdate").val(NewDate(result.publishdate).format("yyyy-MM-dd hh:mm:ss"));
                $("#informationsource").val(result.informationsource);
                $("#simpleintroduce").val(result.simpleintroduce);
                $("#keywords").val(result.keywords);
                $("#image").val(result.image);
                result.isopenlink==0?$("#isopenlink").prop("checked",true):$("#isopenlink").prop("checked",false);
                $("#linkaddress").val(result.linkaddress);
                result.isstick==0?$("#isstick").prop("checked",true):$("#isstick").prop("checked",false);
                result.isrecommend==0?$("#isrecommend").prop("checked",true):$("#isrecommend").prop("checked",false);
                result.refusecopy==0?$("#refusecopy").prop("checked",true):$("#refusecopy").prop("checked",false);
                $("#sortedcode").val(result.sortedcode);
                editor.html(result.articleContentId);
                $("#author").val(result.author);
                $("#issuer").val(result.issuer);
            }
        })
    }


    function del(id){
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/del",
            data:{
                updateid:id
            },
            success:function(result){
                alert(result);
                updatetable();
            }
        })
    }

    function pub(id){
        $.openMgModal("popContainer",{closeClickOverlay:true,width:800,height:600});
        $("#articleid").val(id);
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/getMessageByName",
            data:{
                updateid:id
            },
            success:function(result){
                $("#articleNameT").html("将“"+result.newssubhead+"”  推送到");
                $("#articleIdvalue").val(result.articleId);

                if(userid=='admin'){
                    $.ajax({
                        type:"POST",
                        url:"${pageContext.request.contextPath}/chan/getChanOrder",
                        data:{
                        },
                        success:function(result){
                            var jhtml;
                            for(var a=0;a<result.length;a++){
                                var temphtml="";
                                if(result[a].length>1){
                                    for(var x=0;x<result[a].length;x++){
                                        temphtml="";
                                        if(x==0){
                                            temphtml+="<tr>";
                                            temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                        }else if(x%3!=0){
                                            temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                        }else if(x%3==0 || x==result[a].length){
                                            if(x%3==0){
                                                temphtml+="</tr>";
                                                temphtml+="<tr>";
                                                temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                            }else if(x==result[a].length){
                                                temphtml+="</tr>";
                                                temphtml+="<tr>";
                                                temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                                temphtml+="</tr>";
                                            }
                                        }
                                        if(temphtml!=""){
                                            var hidename=result[a][x].chanName;
                                            if(hidename.length>10){
                                                hidename=hidename.substr(0,10)+"..."
                                            }
                                            temphtml=temphtml.replace(/valuename/g,result[a][x].chanId);
                                            temphtml=temphtml.replace(/namevalue/g,hidename);
                                            temphtml=temphtml.replace(/titlename/g,result[a][x].chanName);
                                            jhtml+=temphtml;
                                        }

                                        // result[a][x]==$("#chanId").val()?result[a].splice(x,1):temphtml="<td><input type='checkbox' value='valuename'><label>namevalue</label></td>";
                                        // if(temphtml!=""){
                                        //     temphtml=temphtml.replace(/valuename/g,result[a][x].chanId);
                                        //     temphtml=temphtml.replace(/namevalue/g,result[a][x].chanName);
                                        //     jhtml+=temphtml;
                                        // }
                                    }
                                }else{
                                    result[a][0].chanId==$("#chanId").val()?result.splice(a,1):temphtml="<tr><td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td></tr>";
                                    //result[a][0].chanId==$("#chanId").val()?result.splice(a,1):temphtml="<td><input type='checkbox' value='valuename'><label>namevalue</label></td>";
                                    if(temphtml!=""){
                                        var hidename=result[a][0].chanName;
                                        if(hidename.length>10){
                                            hidename=hidename.substr(0,10)+"..."
                                        }
                                        temphtml=temphtml.replace(/valuename/g,result[a][0].chanId);
                                        temphtml=temphtml.replace(/namevalue/g,hidename);
                                        temphtml=temphtml.replace(/titlename/g,result[a][0].chanName);
                                        jhtml+=temphtml;
                                    }
                                }
                            }
                            $("#showName").empty();
                            $("#showName").append(jhtml);
                        }
                    })

                }else{
                    $.ajax({
                        type:"POST",
                        url:"${pageContext.request.contextPath}/user/getUserRight",
                        data:{
                            userid:userUid
                        },
                        success:function(result){
                            var jhtml;
                            for(var a=0;a<result.length;a++){
                                var temphtml="";
                                if(result[a].length>1){
                                    for(var x=0;x<result[a].length;x++){
                                        temphtml="";
                                        if(x==0){
                                            temphtml+="<tr>";
                                            temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                        }else if(x%3!=0){
                                            temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                        }else if(x%3==0 || x==result[a].length){
                                            if(x%3==0){
                                                temphtml+="</tr>";
                                                temphtml+="<tr>";
                                                temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                            }else if(x==result[a].length){
                                                temphtml+="</tr>";
                                                temphtml+="<tr>";
                                                temphtml+="<td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td>"
                                                temphtml+="</tr>";
                                            }
                                        }
                                        if(temphtml!=""){
                                            var hidename=result[a][x].chanName;
                                            if(hidename.length>10){
                                                hidename=hidename.substr(0,10)+"..."
                                            }
                                            temphtml=temphtml.replace(/valuename/g,result[a][x].chanId);
                                            temphtml=temphtml.replace(/namevalue/g,hidename);
                                            temphtml=temphtml.replace(/titlename/g,result[a][x].chanName);
                                            jhtml+=temphtml;
                                        }



                                        // result[a][x]==$("#chanId").val()?result[a].splice(x,1):temphtml="<td><input type='checkbox' value='valuename'><label>namevalue</label></td>";
                                        // if(temphtml!=""){
                                        //     temphtml=temphtml.replace(/valuename/g,result[a][x].chanId);
                                        //     temphtml=temphtml.replace(/namevalue/g,result[a][x].chanName);
                                        //     jhtml+=temphtml;
                                        // }
                                    }
                                }else{
                                    result[a][0].chanId==$("#chanId").val()?result.splice(a,1):temphtml="<tr><td width='5%'><input   type='checkbox' value='valuename'></td><td title='titlename'>namevalue</td></tr>";
                                    //result[a][0].chanId==$("#chanId").val()?result.splice(a,1):temphtml="<td><input type='checkbox' value='valuename'><label>namevalue</label></td>";
                                    if(temphtml!=""){
                                        var hidename=result[a][0].chanName;
                                        if(hidename.length>10){
                                            hidename=hidename.substr(0,10)+"..."
                                        }
                                        temphtml=temphtml.replace(/valuename/g,result[a][0].chanId);
                                        temphtml=temphtml.replace(/namevalue/g,hidename);
                                        temphtml=temphtml.replace(/titlename/g,result[a][0].chanName);
                                        jhtml+=temphtml;
                                    }
                                }
                            }
                            $("#showName").empty();
                            $("#showName").append(jhtml);
                        }
                    })
                }

            }
        })

    }

    function closeDiv(){
        $.closeMgModal("popContainer");
    }

    function updatetable (testType){

        if(testType==1){
            $("#skipnum").val("1");
            pageindex=1;
        }

        var ImgName=$("#ImgName").val();
        var TitleName=$("#TitleName").val();
        var ChanName=$("#ChanName").val();
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/getArticleMessage",
            data:{
                currentpage:pageindex,
                pageSize:$("#changePageSize").val(),
                ImgName:ImgName,
                TitleName:TitleName,
                ChanName:ChanName,
                chanid:$("#chanId").val(),
                userid:userUid,
                username:userid
            },
            async:false,
            success:function(resultmessage){
                if( resultmessage.article==null){
                    alert("所选区间超越数据极限");
                    return;
                }
                for(var a=0;a<resultmessage.article.length;a++){
                    resultmessage.article[a].auditStatus==0?resultmessage.article[a].auditStatus="审核通过":null;
                    resultmessage.article[a].auditStatus==1?resultmessage.article[a].auditStatus="审核中":null;
                    //resultmessage.article[a].publishdate=resultmessage.article[a].publishdate.substr(0,10);
                    //resultmessage.article[a].publishdate=new Date(resultmessage.article[a].publishdate).format("yyyy-MM-dd hh:mm:ss");
                    resultmessage.article[a].publishdate=NewDate(resultmessage.article[a].publishdate).format("yyyy-MM-dd hh:mm:ss");

                }
                $("#livetable").empty();
                var xx=new Array("sortedcode","newssubhead","auditStatus","publishdate","articleId");
                var htmltable=getMessage(resultmessage.article,xx,3);
                $("#livetable").html(htmltable);
                $("#_indexNum").text(pageindex);
                setMaxindex(resultmessage,parseInt($("#changePageSize").val()));
            }
        })
    }

    function post(){
        var num1=[];
        var num2=[];
        var articleid=$("#articleid").val();
        if($("#showName").find('input[type=checkbox]:checked').length>0){
            for(var a=0;a<$("#showName").find('input[type=checkbox]:checked').length;a++){
                num1.push($("#showName").find('input[type=checkbox]:checked').eq(a).val());
            }
        }
        if($("#checktable").find('input[type=checkbox]:checked').length>0){
            for(var a=0;a<$("#checktable").find('input[type=checkbox]:checked').length;a++){
                num2.push($("#checktable").find('input[type=checkbox]:checked')[a].id);
            }
        }
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/article/setPubmessage",
            data:{
                numvalue:JSON.stringify(num1),
                numtype:JSON.stringify(num2),
                articleid:articleid
            },
            success:function(resultmessage){
                alert(resultmessage);
                $.closeMgModal("popContainer");
            }
        })


    }

    function clear(){
        $("#articleid").val("");
        $("#changeDemo").empty();
        $("#changeDemo").load("<%=rootPath%>/components/pluupload/upload_modify.jsp?path=informations&tableName=image&fileName=imageFileame&" +
            "saveName=imageName&fileTypeExts=*.jpg;*.png;*.bmp;*.gif&canModify=1&queueSizeLimit=1&canDelete=1&saveFileName=cscscs");
        $("#upsubhead").val("");
        $("#downsubhead").val("");
        $("#newssubhead").val("");
        $("#publishdate").val("");
        $("#informationsource").val("");
        $("#simpleintroduce").val("");
        $("#keywords").val("");
        $("#isopenlink").prop("checked",false);
        $("#isopenlink").prop("checked",false);
        $("#linkaddress").val("");
        $("#isstick").prop("checked",false);
        $("#isstick").prop("checked",false);
        $("#isrecommend").prop("checked",false);
        $("#isrecommend").prop("checked",false);
        $("#refusecopy").prop("checked",false);
        $("#refusecopy").prop("checked",false);
        $("#sortedcode").val("");
        editor.html("");
        $("#author").val("");
        $("#issuer").val("");
    }

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
</html>
