<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.bts.model.User"%>
<%
	if(request.getAttribute("user")==null){
		String userName=null;
		String password=null;
		
		Cookie[] cookies=request.getCookies();
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals("user")){
				userName=cookies[i].getValue().split("-")[0];
				password=cookies[i].getValue().split("-")[1];
			}
		}
		
		if(userName==null){
			userName="";
		}
		
		if(password==null){
			password="";
		}
		
		pageContext.setAttribute("user", new User(URLDecoder.decode(userName, "utf-8"),password));
		
	}
%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人日记本登录</title>
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<style type="text/css">
	  body {
        padding-top: 200px;
        padding-bottom: 40px;
        background-image: url('images/timg.gif');
      }
      
      .form-signin-heading{
      	text-align: center;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 0px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
	  /* .form-signin{ 
	  width:100%; 
	  background-color: rgba(0, 0, 0, 0.5); 
	  position:relative; 
	  top:0; left:0;
	  } */
</style>
<script type="text/javascript">
	function checkForm(){
		
		var userName=document.getElementById('userName').value;
		var password=document.getElementById('password').value;
		if(userName==null||userName==''){
			document.getElementById('error').innerHTML="用户名不能为空";
			return false;
		}
		if(password==null||password==''){
			document.getElementById('error').innerHTML="密码不能为空";
			return false;
		}
		
		return true;
	};
</script>
</head>
<body>
<div class="container">
      <form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkForm()">
        <h2 class="form-signin-heading">个人日记本</h2>
        <input id="userName" name="userName"  value="${user.userName }" type="text" class="input-block-level" placeholder="用户名">
        <input id="password" name="password"   value="${user.password }" type="password" class="input-block-level" placeholder="密码" >
        <label class="checkbox">
          <input id="remember" name="remember" type="checkbox" value="remember-me">记住我 &nbsp;&nbsp;&nbsp;&nbsp; <font id="error" color="red">${error }</font>  
        </label>
        <button class="btn btn-info" type="submit">登录</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn btn-info" type="reset" >重置</button>
        <p align="center" style="padding-top: 15px;">版权所有  2018 @stevenxy <br>
        <a href="http://47.97.222.41:8080" target="_blank">http://47.97.222.41:8080</a></p>
      </form>
</div>
</body>
</html>