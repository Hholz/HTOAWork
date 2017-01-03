<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="styleInclude.jsp"></jsp:include>
</head>
<body class="gray-bg">

<div class="middle-box text-center loginscreen   animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">HT</h1>

        </div>
        <h3>欢迎注册</h3>

        <p>员工注册</p>

        <form class="m-t" role="form" action="${pageContext.request.contextPath }/toRegisterEmp" method="post">
            <div class="form-group">
                <input type="text" id="name" name="name" class="form-control" placeholder="请输入你的名字" required="">
            </div>
            <div class="form-group">
                <input type="text" id="phone" name="phone" class="form-control" placeholder="请输入你的电话号码" required="">
            </div>
            <div class="form-group">
                <input type="password" id ="password"  name="password" class="form-control" placeholder="请输入密码" required="">
            </div>
            <div class="form-group">
                <input type="password2" id ="password2"  class="form-control" placeholder="请再次输入密码" required="">
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">注 册</button>

            <p class="text-muted text-center">
                <small>已经有账户了？</small>
                <a href="${pageContext.request.contextPath }/login">点此登录</a>
            </p>

        </form>
    </div>
</div>

<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<!-- iCheck -->
<script src="js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    });
</script>


</body>
</html>