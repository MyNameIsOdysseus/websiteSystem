<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    <link rel="stylesheet" href="<%=rootPath%>/css/style.css">
    <link rel="shortcut icon" href="<%=rootPath%>/image/favicon.ico" />
    <link rel="bookmark" href="<%=rootPath%>/image/favicon.ico"　/>
      
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/jquery.min.js"></script>
    <script type="text/javascript" src="<%=rootPath%>/scripts/webstyle/verify.js"></script>

</head>
<body>
<div class="login-main">
    <div class="login-main-son">
        <div class="login-logo"></div>
    </div>
    <div class="login-content">
        <div class="login-content-son">
            <h1>用户登录</h1>
                <div class="login-inp">
                    <form id="testform" action="${pageContext.request.contextPath}/postlogin" method="POST">
                    <input id="username" name="username" type="text" class="login-manager" placeholder="请输入用户名">
                    <%--<input id="password" name="password" type="password" class="login-pass" placeholder="请输入密码">--%>
                    <input type="password" id="password" name="password" class="login-pass" placeholder="请输入密码" />
                    <%--<input  type="hidden"  id="password" name="password"/>--%>
                    <div id="mpanel2" ></div>
                    <button id="check-btn" type="button" class="login-btn" >登录系统</button>
                    </form>
                </div>
        </div>
    </div>
</div>
<script >
    var errormessage='<%=request.getAttribute("errormessage")%>';
</script>
<script>

    // function hiddenPass(e){
    //     e = e ? e : window.event;
    //     var kcode = e.which ? e.which : e.keyCode;
    //     var pass = document.getElementById("testdemo");
    //     var j_pass = document.getElementById("password");
    //     if(kcode!=8)
    //     {
    //         var keychar=String.fromCharCode(kcode);
    //         j_pass.value=j_pass.value+keychar;
    //         j_pass.value=j_pass.value.substring(0,pass.length);
    //     }
    // }

    $(function() {
        $("#testdemo").keyup(function(event){
            if(event.keyCode==8) {//keycode为8表示退格键
                var text=$("#password").val();
                if(text.length>1){
                    $("#password").val(text.substring(0,$("#testdemo").val().length));
                }
            }
        })
    })


    $(function(){
        if(errormessage != "null"){
            alert(errormessage);
            window.location.href="${pageContext.request.contextPath}/login";
        }
    });

    $('#mpanel2').codeVerify({
        type : 1,
        width : '134px',
        height : '35px',
        fontSize : '26px',
        codeLength : 4,
        btnId : 'check-btn',
        ready : function() {
        },
        success : function() {
            postform();

        },
        error : function() {
            if($(".varify-input-code").val()==""){
                alert('验证码不能为空，请重新输入！');
            }else{
                alert('验证码输入错误，请重新输入！');
            }
            return;
        }
    });

    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#check-btn").click();
        }
    });

    function postform(){
            $("#testform").submit();
    }

</script>
</body>
</html>
