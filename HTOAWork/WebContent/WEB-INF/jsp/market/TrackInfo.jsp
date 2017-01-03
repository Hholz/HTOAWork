<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>跟踪情况</title>
	
	<jsp:include page="../styleInclude.jsp"></jsp:include>
	<link href="${pageContext.request.contextPath }/fonts/infoIcon/iconfont.css" rel="stylesheet">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
		       <div class="panel panel-default">
		           <div class="panel-heading">
		                                            跟踪情况
		           </div>
		           <div class="panel-heading">
		              
		           </div>
		           <div class="panel-body">
		           		<div class="row">
		           			<div class="col-sm-3">
		           				<p><i class="iconfont">&#xe67b;</i> 跟踪学生：${trackstu.studid}
			                     </p>
			                     <p><i class="iconfont">&#xe62c;</i> 跟踪方式：${trackstu.trackways}
			                     </p>
			                     <p><i class="iconfont">&#xe61b;</i> 洽谈内容：${trackstu.trackcontent}
			                     </p>
			                     <p><i class="iconfont">&#xe636;</i> 跟踪时间：${trackstu.tracktime}
			                     </p>
			                     <p><i class="iconfont">&#xe712;</i> 跟踪情况：${trackstu.trackresult}
			                     </p>
			                     <p><i class="iconfont">&#xe712;</i> 跟踪老师：${trackstu.empid}
			                     </p>
		           			</div>
		           		</div>
		           </div>
		           <div class="panel-footer">
		                                                   
		           </div>
		       </div>
		   </div>
	   </div>

	</div>
</body>
</html>