<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>宏图教务管理系统 - 登录</title>
<meta name="keywords" content="">
<meta name="description" content="">

<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/img/favicon.ico">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.css?v=4.4.0" rel="stylesheet">
<!-- <link href="css/animate.css" rel="stylesheet"> -->

<style>
body {
	height: 100%;
	background: #16a085;
	overflow: hidden;
}

.login-box {
	width: 100%;
	max-width: 500px;
	height: 445px;
	position: absolute;
	top: 50%;
	margin-top: -220px;
	/*设置负值，为要定位子盒子的一半高度*/
}

@media screen and (min-width:500px) {
	.login-box {
		left: 50%;
		/*设置负值，为要定位子盒子的一半宽度*/
		margin-left: -250px;
	}
}

.form {
	width: 100%;
	max-width: 100%;
	height: 100%;
	margin: 25px auto 0px auto;
	padding-top: 100px;
}

.login-content {
	height: 100%;
	width: 100%;
	max-width: 500px;
	/* background-color: rgba(255, 250, 255, .5); */
	background-image: url(img/center.png);
	background-size: 200% 180%;
	background-position: 50% 42%;
}

.input-group {
	margin: 0px 0px 30px 0px !important;
}

.form-control, .input-group {
	height: 40px;
}

.form-group {
	margin-bottom: 0px !important;
}

.link p {
	line-height: 20px;
	margin-top: 30px;
}

.btn-sm {
	padding: 10px 26px !important;
	font-size: 16px !important;
}
</style>
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/login/Particleground.js"></script>
<script>
	$(document).ready(function() {
		//粒子背景特效
		$('body').particleground({
			dotColor : '#5cbdaa',
			lineColor : '#5cbdaa'
		});
	});
	function myReload() {
		document.getElementById("CreateCheckCode").src = document
				.getElementById("CreateCheckCode").src
				+ "?nocache=" + new Date().getTime();
		$("#checkcode").val("");
	}
</script>
<script language="javascript" type="text/javascript">
	if(top.location!=self.location)top.location=self.location;
</script>
</head>

<body>
	<div class="box">
		<div class="login-box">
			<div class="login-content ">
				<div class="form">
					<div style="color: white; font-size: 18px;" class="text-center">${message_login}</div>
					<form role="form" method="post"
						action="${pageContext.request.contextPath }/toLogin">
						<div class="form-group">
							<div class="col-xs-8 col-xs-offset-2">
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-user"></span></span> <input type="text"
										id="username" name="username" class="form-control"
										placeholder="id/姓名/手机号" required="">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-8 col-xs-offset-2">
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-eye-open"></span></span> <input type="password"
										id="password" name="password" class="form-control"
										placeholder="密码">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-8 col-xs-offset-2">
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-barcode"></span></span> <input
										type="number" id="checkcode" class="form-control"
										placeholder="验证码" required="" name="checkcode"> <span
										class="input-group-addon"><img id="CreateCheckCode"
										alt="点击更换验证码" title="点击更换验证码" src="page/image.jsp" onclick="myReload()"></span>
								</div>
							</div>
						</div>
						<div class="form-group form-actions">
							<div class="col-xs-4 col-xs-offset-4 ">
								<button type="submit" class="btn btn-sm btn-info">
									<span class="glyphicon glyphicon-hand-right"></span>登录<span class="glyphicon glyphicon-hand-left"></span>
								</button>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-7 link">
								<p class="text-center remove-margin">
									<small>忘记密码？</small> <a href="javascript:void(0)"><small>找回</small></a>
								</p>
							</div>
							<div class="col-xs-3 link">
								<p class="text-muted remove-margin">
									<a href="${pageContext.request.contextPath }/changePwd/page">修改密码</a>&nbsp;
									<%-- <a href="${pageContext.request.contextPath }/registerEmp">员工注册</a> --%>
								</p>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>