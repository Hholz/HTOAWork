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
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-sm-12">
	       <div class="panel panel-danger">
	           <div class="panel-heading">
	                                            通知
	           </div>
	           <div class="panel-body">
	               <p>获取session里的数据，请详读com.ht.controller.login.IndexController里的first()方法</p>
	               <p><h4>别去我那新增学生</h4></p>
	           </div>
	           <div class="panel-footer">
	                                                    面板Footer
	           </div>
	       </div>
	   </div>
   </div>
	<div class="row">
         <div class="col-sm-4">
             <div class="ibox float-e-margins">
                 <div class="ibox-title">
                     <h5>session保存的学生信息</h5>
                 </div>
                 <div class="ibox-content">
                 	<p><i class="fa fa-send-o"></i> ID：${student.id}
                     </p>
                     <p><i class="fa fa-send-o"></i> 编号：${student.stuno}
                     </p>
                     <p><i class="fa fa-qq"></i> 姓名：${student.stuname}
                     </p>
                     <p><i class="fa fa-weixin"></i> 学校：${student.middleschool}
                     </p>
                     <p><i class="fa fa-credit-card"></i> 班级：${student.classInfo.classname}
                     </p>
                 </div>
             </div>
         </div>
         <div class="col-sm-4">
             <div class="ibox float-e-margins">
                 <div class="ibox-title">
                     <h5>session保存的员工信息</h5>
                 </div>
                 <div class="ibox-content">
                     <p><i class="fa fa-send-o"></i> 编号：${emp.id}
                     </p>
                     <p><i class="fa fa-qq"></i> 姓名：${emp.empname}
                     </p>
                     <p><i class="fa fa-weixin"></i> 部门：${emp.depid}
                     </p>
                     <p><i class="fa fa-credit-card"></i> 性别：${emp.sex}
                     </p>
                 </div>
             </div>
         </div>
     </div>
     </div>
</body>
<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</html>