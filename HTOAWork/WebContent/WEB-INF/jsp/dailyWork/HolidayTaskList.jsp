<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<meta charset="utf-8">
<link
	href="${pageContext.request.contextPath}/css/bootstrap.min14ed.css?v=3.3.6"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/css/font-awesome.min93e3.css?v=4.4.0"
	rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/animate.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/css/style.min862f.css?v=4.1.0"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<title>每日任务</title>

<script type="text/javascript">
	function yes(id) {
		var url = "${pageContext.request.contextPath }/dailyWork/HolidayTask/over"
		$.post(url, {
			id : id,
			approvalstatus : 3,
		}, updateStudentHandle, "text");
	}
	function no(id) {
		var url = "${pageContext.request.contextPath }/dailyWork/HolidayTask/over"
		$.post(url, {
			id : id,
			approvalstatus : 2,
		}, updateStudentHandle, "text");
	}
	function updateStudentHandle() {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/dailyWork/HolidayTask/tasklist";
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox">
					<div class="ibox-content">
						<h3>任务列表</h3>
						<p class="small">
							<i class="fa fa-hand-o-up"></i> 在列表之间拖动任务面板
						</p>
						<ul class="sortable-list connectList agile-list">
							<c:forEach items="${materiallist }" var="data">
								<li class="warning-element">
									${data.holidaytypename }的申请:${data.empname}请假${data.holidayday }天,理由是:${data.holidayRemark}.
									<div class="agile-detail">
										<input type="hidden" id="" value="${data.id}" /> <a href="#"
											onclick="yes(this.id);" id="${data.id}"
											class="pull-right btn btn-xs btn-white">同意</a> <a href="#"
											onclick="no(this.id);" id="${data.id}"
											class="pull-right btn btn-xs btn-white">不同意</a><i
											class="fa fa-clock-o"></i>${data.updateTime}

									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox">
					<div class="ibox-content">
						<h3>已完成</h3>
						<p class="small">
							<i class="fa fa-hand-o-up"></i> 在列表之间拖动任务面板
						</p>
						<ul class="sortable-list connectList agile-list">
							<c:forEach items="${overtasklist }" var="data">
								<li class="warning-element"><c:if
										test="${data.approvalstatus eq 2 ||data.approvalstatus eq 4 }">
										否定了
									</c:if> <c:if test="${data.approvalstatus eq 3 || data.approvalstatus eq 5}">
										通过了
									</c:if> ${data.empname}的申请.
									<div class="agile-detail">
										<i class="fa fa-clock-o"></i>${data.updateTime}

									</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>