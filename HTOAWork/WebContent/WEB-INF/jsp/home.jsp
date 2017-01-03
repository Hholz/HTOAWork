<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
	href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/css/font-awesome.css?v=4.4.0"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/animate.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css?v=4.1.0"
	rel="stylesheet">
	<link href="${pageContext.request.contextPath }/fonts/infoIcon/iconfont.css" rel="stylesheet">
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<c:if test="${student!=null}">
			<div class="col-sm-4">
				<div class="widget-head-color-box lazur-bg p-lg text-center">
					<div class="m-b-md">
						<h2 class="font-bold no-margins">${student.stuname }</h2>
						<small>学生</small>
					</div>
					<img src="img/a8.gif" class="img-rounded circle-border m-b-md"
						alt="profile" width="100" height="100">
					<div>
						您当前有<span class="label label-warning">${baoxiaotaskcount}</span>个通知
					</div>
				</div>
				<div class="widget-text-box">
					<h2>登录人详细信息</h2>
					<ul class="list-unstyled m-t-md">
						<li><i class="iconfont">&#xe67b;</i> <label>学生编号：</label>${student.stuno}</li>
						<li>
							<i class="iconfont">&#xe62e;</i> <label>学生电话：</label>${student.phone}
						</li>
						<li>
							<i class="iconfont">&#xe712;</i> <label>性别：</label>${student.sex}
						</li>
						<li>
							<i class="iconfont">&#xe600;</i> <label>班级：</label>${student.classInfo.classname} 
						</li>
						<li>
							<i class="iconfont">&#xe603;</i> <label>宿舍：</label>${student.hourse.hoursename}
						</li> 
						<li>
							<i class="iconfont">&#xe887;</i> <label>家庭地址：</label>${student.addr}
						</li>
					</ul>
				</div>
			</div>
			</c:if>
			<c:if test="${emp!=null}">
			<div class="col-sm-4">
				<div class="widget-head-color-box lazur-bg p-lg text-center">
					<div class="m-b-md">
						<h2 class="font-bold no-margins">${emp.empname }</h2>
						<small>员工</small>
					</div>
					<img src="img/a8.gif" class="img-rounded circle-border m-b-md"
						alt="profile" width="100" height="100">
					<div>
						您当前还有<span class="label label-warning">${newcomputercount+returncount+revicecount+baoxiaotaskcount+stuHoliCount+adjustClsCount+applychangecount+applyovercount+applyholidaycount+applymaterialcount+rematerialcount+returnmaterialcount+repaircount+FeedBackcount}</span>个待办任务
					</div>
				</div>
				<div class="widget-text-box">
					<h2>登录人详细信息</h2>
					<ul class="list-unstyled m-t-md">
						<li><span class="fa fa-envelope m-r-xs"></span> <label>部门:</label>
							${emp.dep.depname}</li>
						<li><i class="iconfont">&#xe712;</i> <label><label>性别:</label>
							${emp.sex}</li><Button onclick="addStudent();" class="btn btn-outline btn-info">打卡</Button>
						<li><i class="iconfont">&#xe887;</i><label>籍贯:</label>
							${emp.nation }</li>
						<li><i class="iconfont">&#xe62e;</i><label>电话:</label>
							${emp.phone }</li>
					</ul>
				</div>
			</div>
			</c:if>
			<div class="col-sm-8">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>公告栏</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content ">
						<div class="carousel slide" id="carousel2">
							<ol class="carousel-indicators">
								<li data-slide-to="0" data-target="#carousel2" class="active"></li>
								<li data-slide-to="1" data-target="#carousel2"></li>
								<li data-slide-to="2" data-target="#carousel2" class=""></li>
							</ol>
							<div class="carousel-inner">
								<div class="item active">
									<div class="panel-body">
										<div class="widget-head-color-box lazur-bg p-lg text-center">
											<h2>系统公告</h2>
											<div class="widget-text-box">
												<br />
												<p class="lead text-center">
													<font color="#000000" size="4px">${notice1.content}</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${notice1.emp.empname}(宣)</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${fn:substringBefore(notice1.noticeTime, " ")}</font>
												</p>
											</div>
										</div>
									</div>
									<div class="carousel-caption">
										<p>${fn:substringBefore(notice1.noticeTime, " ")}</p>
									</div>
								</div>
								<div class="item ">
									<div class="panel-body">
										<div class="widget-head-color-box lazur-bg p-lg text-center">
											<h2>系统公告</h2>
											<div class="widget-text-box">
												<br />
												<p class="lead text-center">
													<font color="#000000" size="4px">${notice2.content}</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${notice2.emp.empname}(宣)</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${fn:substringBefore(notice2.noticeTime, " ")}</font>
												</p>
											</div>
										</div>
									</div>
									<div class="carousel-caption">
										<p>${fn:substringBefore(notice2.noticeTime, " ")}</p>
									</div>
								</div>
								<div class="item">
									<div class="panel-body">
										<div class="widget-head-color-box lazur-bg p-lg text-center">
											<h2>系统公告</h2>
											<div class="widget-text-box">
												<br />
												<p class="lead text-center">
													<font color="#000000" size="4px">${notice3.content}</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${notice3.emp.empname}(宣)</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${fn:substringBefore(notice3.noticeTime, " ")}</font>
												</p>
											</div>
										</div>
									</div>
									<div class="carousel-caption">
										<p>${fn:substringBefore(notice3.noticeTime, " ")}</p>
									</div>
								</div>
							</div>
							<a data-slide="prev" href="carousel.html#carousel2"
								class="left carousel-control"> <span class="icon-prev"></span>
							</a> <a data-slide="next" href="carousel.html#carousel2"
								class="right carousel-control"> <span class="icon-next"></span>
							</a>
						</div>
					</div>
				</div>
				<!-- 不是员工不显示这个div -->
				<c:if test="${emp!=null}">
				<div>
					<table class="table">
						<tbody>
							<tr>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/task/tasklist"><button type="button" class="btn btn-success m-r-sm">${applymaterialcount}</button></a>
									用品申购任务
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/ReceiveMaterialTask/tasklist"><button type="button" class="btn btn-primary m-r-sm">${rematerialcount }</button></a>
									用品申领任务
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/ReturnMaterialTask/tasklist"><button type="button" class="btn btn-info m-r-sm">${returnmaterialcount }</button></a>
									用品归还任务
								</td>
							</tr>
							<tr>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/HolidayTask/tasklist"><button type="button" class="btn btn-danger m-r-sm">${applyholidaycount }</button></a>
									员工请假任务
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/OverWorkTask/tasklist"><button type="button" class="btn btn-primary m-r-sm">${applyovercount }</button></a>
									员工加班任务
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/ChangeHolidayTask/tasklist"><button type="button" class="btn btn-info m-r-sm">${applychangecount }</button></a>
									员工调休任务
								</td>
							</tr>
							<tr>
								<td>
									<a href="${pageContext.request.contextPath }/student/deladjustCls/page"><button type="button" class="btn btn-warning m-r-sm">${adjustClsCount }</button></a>
									分班申请处理
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/student/delStuHoliday/page"><button type="button" class="btn btn-success m-r-sm">${stuHoliCount }</button></a>
									学生请假申请处理
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/dailyWork/BaoxiaoTask/baoxiaotasklist"><button type="button" class="btn btn-warning m-r-sm">${baoxiaotaskcount }</button></a>
									报销审批
								</td>
							</tr>
							<tr>
								<td>
									<a href="${pageContext.request.contextPath }/student/ComputerRepair/tasklist"><button type="button" class="btn btn-danger m-r-sm">${repaircount }</button></a>
									电脑维修
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/student/BackUpComputer/tasklist"><button type="button" class="btn btn-primary m-r-sm">${revicecount }</button></a>
									备用电脑申领
								</td>
								<td>
									<a href="${pageContext.request.contextPath }/student/ReturnBackUpComputer/tasklist"><button type="button" class="btn btn-info m-r-sm">${returncount }</button></a>
									备用电脑归还
								</td>
							</tr>
							<tr>
								<td>
									<a href="${pageContext.request.contextPath }/student/NewComputer/tasklist"><button type="button" class="btn btn-success m-r-sm">${newcomputercount }</button></a>
									新生领电脑
								</td>
								<td>
									<a href="${pageContext.request.contextPath}/student/studentFeedBack/studentFeedBack"><button type="button" class="btn btn-success m-r-sm">${FeedBackcount }</button></a>
									退费申请
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				</c:if>
				
			</div>
		</div>
	</div>
	<!-- 全局js -->
	
	<script type="text/javascript">
	function addStudent() {
		var url = "${pageContext.request.contextPath }/dailyWork/kaoqin/add";
		$.post(url, {}, addStudentHandle, "text");

		//刷新数据
		$('#tb_departments').bootstrapTable('refresh');
		//清空新增div中的数据
	}
	function addStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
		} else {
			swal("添加失败", "本月数据已生成！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}
	
	</script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<!-- iCheck -->
</body>
</html>