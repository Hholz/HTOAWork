<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>宏图教务管理系统-主页</title>

<meta name="keywords" contment="">
<meta name="description" content="">

<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->

<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/img/favicon.ico">
<link
	href="${pageContext.request.contextPath }/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/css/font-awesome.min.css?v=4.4.0"
	rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/animate.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/style.css?v=4.1.0"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/fonts/myIcon/iconfont.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>
<link
	href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
</head>
<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="nav-close">
			<i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element">
						<span><img alt="image" class="img-circle"
							src="${pageContext.request.contextPath }/img/profile_small.jpg" /></span>
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span style="margin-top: 15px"
							class="clear"> <strong
									class="font-bold">欢迎</strong> <font color="#acacac"><B>
									<c:if test="${emp == null && student != null }">
										${student.stuname }
									</c:if>
									<c:if test="${student == null && emp != null }">
										${emp.empname }
									</c:if></B>
								<b class="caret"></b></font>
								<strong
									class="font-bold">登陆</strong>
						</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li><a class="J_menuItem" href="">修改头像</a></li>
							<li><a class="J_menuItem" href="">个人资料</a></li>
							<li><a class="J_menuItem" href="">信箱</a></li>
							<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath }/loginOut">安全退出</a></li>
						</ul>
					</div>
					<div class="logo-element">宏图软件教育</div>
				</li>
				<li><a href="#"><i class="iconfont">&#xe60c;</i> <span
						class="nav-label">我的待办任务</span><span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem" href="${pageContext.request.contextPath }/dailyWork/Worklog/writelog"><span
								class="nav-label">写日志</span></a>
						</li>
						<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/approveapplymaterial/ApplyMaterialList"><span
								class="nav-label">待审批任务</span></a>
						</li>
						<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/waitformaterial/waitformateriallist"><span
								class="nav-label">用品入库确认</span></a>
						</li>
						<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/flowComputerApply/flowComputerApply"><span
								class="nav-label">发放学生电脑</span></a>
						</li>
					</ul>			
				</li>
				<li><a href="#"><i class="iconfont">&#xe60c;</i> <span
						class="nav-label">流程管理</span><span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/myFlowList">我的申请</a></li>
						<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/flowAll/myFlowAll">我的流程存档</a></li>
						<li><a href="#"><i class="iconfont">&#xe6af;</i><span
								class="nav-label">员工类流程申请</span><span class="fa arrow"></span> </a>

							<ul class="nav nav-third-level">
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/flowHolidayEmp/flowHolidayEmpList">请假申请</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/ApplySupplement/flowSupplement">补签申请</a></li>
								<li><a class="J_menuItem" href="">加班申请</a></li>
								<li><a class="J_menuItem" href="">调休申请</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/ApplyChangeDuty/flowApplyChangeDuty">调班申请</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/Reimburse/ApplyBaoxiaoList">报销申请</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/ApplyMaterial/ApplyMaterialList">用品申购</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/ReceviceMaterial/ReceviceMaterialList">用品申领</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/ReturnMaterial/ReturnMaterialList">用品归还</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/flowFeedBack/flowFeedBackList">发起学生退费申请</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe6af;</i><span
								class="nav-label">学生类流程申请</span><span class="fa arrow"></span> </a>

							<ul class="nav nav-third-level">
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/flowHolidayStud/flowHolidayStudList">请假申请</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/flow/flowStudApply/flowStudApply">调换班级申请</a></li>
								<li><a class="J_menuItem" href="">电脑维修申请</a></li>
								<li><a class="J_menuItem" href="">备用电脑申请</a></li>
								<li><a class="J_menuItem" href="">备用电脑归还</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe6af;</i><span
								class="nav-label">流程单据存档</span><span class="fa arrow"></span> </a>

							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/flow/flowAll/flowAllEmp">员工类存档</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/flow/flowAll/flowAllStudent">学生类存档</a>
								</li>
							</ul></li>
					</ul></li>

				<li><a href="#"><i class="iconfont">&#xe60c;</i> <span
						class="nav-label">日常办公</span><span class="fa arrow"></span> </a>

					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/dailyWork/dep/deplist">部门管理</a></li>
						<li><a href="#"><i class="iconfont">&#xe6af;</i><span
								class="nav-label">员工管理</span><span class="fa arrow"></span> </a>

							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/emp/emplist">员工信息</a>
								</li>
								<%-- <li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/works/workslist">工作经历</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/edu/edulist">教育背景</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/family/familylist">家庭信息</a>
								</li> --%>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Worklog/Workloglist">工作日志</a>
								</li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe60f;</i><span
								class="nav-label">办公用品管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/MateriaType/MTList">用品类别管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Material/materiallist">用品管理</a></li>
							</ul>
						</li>
						<li><a href="#"><i class="iconfont">&#xe659;</i><span
								class="nav-label">考勤管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/sysSet/fattenjsp">考勤参数设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/sysSet/Setholiday">节假日设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/kaoqin/attendencejsp">考勤明细管理</a></li>
							<!--<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Attenstatis">考勤明细统计</a></li>-->
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/attenTojijsp">月度考勤统计</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe6ef;</i><span
								class="nav-label">值班管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/DutyModel/dutymodelList">值班模板</a></li>
								<li>
									<a class="J_menuItem" href="${pageContext.request.contextPath }/dailyWork/DutyModel/dutyWeek">本周值班</a>
								</li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe609;</i><span
								class="nav-label">巡查管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Patrol/patrolClassList">班级巡查</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Patrol/patrolHouseList">宿舍巡查</a></li>
							</ul></li>
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/dailyWork/Notice/noticeList">系统公告</a></li>
					</ul></li>

				<li><a href="#"><i class="iconfont">&#xe607;</i> <span
						class="nav-label">教务管理</span> <span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/education/seminar/list">研讨会管理</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe659;</i><span
								class="nav-label">课程管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/coursetype/typeIndex">课程类别管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/course/courseIndex">课程管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/outline/outlineIndex">课程大纲管理</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xeb85;</i><span
								class="nav-label">反馈管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/feedbackstart/list">发起反馈通知</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/feedback/list">反馈记录查看</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xeb85;</i><span
								class="nav-label">课程进度管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/syllabus/list">课程进度安排</a></li>
								<!-- <li><a class="J_menuItem" href="mailbox.html">课程进度记录</a></li> -->
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/syllabuscompare/list">课程进度查看</a></li>
							</ul></li>
					</ul></li>
				<li><a href="#"><i class="iconfont">&#xe625;</i> <span
						class="nav-label">学生管理</span> <span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe61f;</i><span
								class="nav-label">专业届别</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/fall/page">届别信息</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/dept/deptIndex">院系管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/major/majorIndex">所设专业</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/term/termIndex">学期管理</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe628;</i><span
								class="nav-label">班级管理</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/class/page">班级设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/myclass/page">我的班级</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/attence/page">学生考勤</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/attence/countPage">考勤统计</a></li>
							</ul>
						</li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe603;</i><span
								class="nav-label">宿舍管理</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/FloorLayer/Floor">楼栋楼层</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/studentHourse/studentHourse">宿舍信息</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/studentHourse/studentHourseAllot">正式宿舍分配</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/studentRoom/studentHourseAllot_test">试学宿舍分配</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe625;</i><span
								class="nav-label">学生信息</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/stuList">学生信息</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/stuheart/sheartStu">谈心记录</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/sreward/srewardStu">奖惩记录</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/studentScore/studentScore">成绩记录</a></li>
								<%-- <li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/edufam/page">学生教育经历</a></li>
								<li><a class="J_menuItem" href="mailbox.html">学生家庭情况</a></li>
								<li><a class="J_menuItem" href="mailbox.html">调班申请</a></li> --%>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe7a2;</i><span
								class="nav-label">学生答辩</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-second-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/StuRepSet/sturepsetList">模板明细设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/reply/page">我的评分模板</a></li>
								<!-- <li><a class="J_menuItem" href="mailbox.html">导出评分表格</a></li> -->
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/replyScore/page">录入答辩成绩</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/stujob/page">学生就业管理</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/stuOptionList/studentOptionList">学生意见反馈</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/studentOption/studentOption">我的意见</a></li>
					</ul></li>
				<li><a href="#"><i class="iconfont">&#xe615;</i> <span
						class="nav-label">财务系统</span> <span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe61f;</i><span
								class="nav-label">学生缴费管理</span><span class="fa arrow"></span></a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/finance/feeStandard/feeStandardIndex">缴费项目设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/TuitionSet/TuitionSet">学期缴费设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/finance/Shouldcharge/tuitionIndex">应缴费用生成</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/schoolFeePaySure/list">学生缴费管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financefee/financeFeeIndex">缴费情况统计</a></li>

							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe637;</i><span
								class="nav-label">财务管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financeType/financeTypeIndex">收支项管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financebalance/balanceIndex">财务结算管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financeIncomeAndExpense/list">财务统计明细总报表</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/year/list">财务统计年度报表</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/year/listmonth">财务统计月度报表</a></li>
								<!--  <li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/reimburse/list">报销单确认</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/applyurse/list">办公用品申购确认</a></li>-->
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xf0041;</i><span
								class="nav-label">工资管理</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/FinanceSalarySet/FinSalarySetList">员工基本工资设置</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financesalary/financesalarylist">月份工资生成、发放</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/sysSet/salType/page">工资类别管理</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/sysSet/frewardjsp">考勤奖罚设置</a></li>
							</ul></li>
					</ul></li>
				<li><a href="#"> <i class="iconfont">&#xe61a;</i> <span
						class="nav-label">市场管理</span> <span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/intention/page">意向学生管理</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/marketClass/page">试学班级管理</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/report/mouthPage">月度报表</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/report/yearPage">年度报表</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/allStudent/page">招生记录</a></li>
					</ul>
					<%-- <ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/predestination/page">预定学生管理</a></li>
					</ul> --%>
				</li> 
				<li><a href="#"> <i class="iconfont">&#xe646;</i> <span
						class="nav-label">系统设置</span> <span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/sysSet/finaPolicQuantitySet/finaPolicQuantitySet">用品库存管理</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/finance/FeedBackSet/feedback">反馈信息模板</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/sysSet/student">学生相关设置</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/flow/flowModelSel/flowMOdelSel">流程模板设置</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/power/roleAuth">权限管理</a></li>
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/user/page">用户管理</a></li>
					</ul></li>
			</ul>
		</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
						href="javascript:void(0);"><i class="fa fa-bars"></i> </a>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="javascript:void(0);"> <i
							class="fa fa-envelope"></i> <span class="label label-warning">${fn:length(list)+fn:length(blist)}</span>
					</a>
						<ul class="dropdown-menu dropdown-messages">
							<!-- 关于会议通知的通知 -->
							<c:if test="${fn:length(list)>0}">
								<c:forEach items="${list}" var="l">
									<li class="m-t-xs">
										<div class="dropdown-messages-box">
											<a href="profile.html" class="pull-left"> <img
												alt="image" class="img-circle"
												src="${pageContext.request.contextPath }/img/a5.jpg">
											</a>

											<div class="media-body">
												<strong>会议通知</strong> <br> <small class="text-muted">开始时间：${fn:substring(l.startTime, 0, 19)}</small><br />
												<small class="text-muted">结束时间：${fn:substring(l.endTime, 0, 19)}</small><br />
												<small class="text-muted">会议发言人：${l.sayMan.empname}</small>
											</div>
										</div>
									</li>
									<li class="divider"></li>
								</c:forEach>
							</c:if>
							<c:if test="${fn:length(list)==0 && fn:length(blist)==0}">
								<li class="m-t-xs">
									<div class="dropdown-messages-box">
										<a href="profile.html" class="pull-left"> <img alt="image"
											class="img-circle"
											src="${pageContext.request.contextPath }/img/a5.jpg">
										</a>
										<div class="media-body">
											<strong>暂无通知</strong>
										</div>
									</div>
								</li>
							</c:if>
							<!-- 关于反馈通知的通知 -->
							<c:if test="${fn:length(blist)>0}">
								<c:forEach items="${blist}" var="l">
									<li class="m-t-xs">
										<div class="dropdown-messages-box">
											<a href="profile.html" class="pull-left"> <img
												alt="image" class="img-circle"
												src="${pageContext.request.contextPath }/img/a5.jpg">
											</a>
											<div class="media-body">
												<strong>反馈通知</strong> <br> <small class="text-muted">开始时间：${fn:substring(l.startTime, 0, 19)}</small><br />
												<small class="text-muted">结束时间：${fn:substring(l.endTime, 0, 19)}</small><br />
												<small class="text-muted">反馈发起人：${l.empStart.empname}</small><br />
												<small class="text-muted">反馈教师：${l.empBeStart.empname}老师</small><br />
												<small class="text-muted">反馈班级：${l.stuClass.classname}</small><br />
												<button id="${l.empBeStart.id}" type="button"
													class="btn btn-info" data-toggle="modal"
													data-target="#window_update" onclick="getTemplate(this);">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>点击发起反馈
												</button>
											</div>
										</div>
									</li>
									<li class="divider"></li>
								</c:forEach>
							</c:if>
						</ul></li>
					<!-- <li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="#"> <i class="fa fa-bell"></i> <span
							class="label label-primary">8</span>
					</a>
						<ul class="dropdown-menu dropdown-alerts">
							<li><a href="mailbox.html">
									<div>
										<i class="fa fa-envelope fa-fw"></i> 您有16条未读消息 <span
											class="pull-right text-muted small">4分钟前</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="profile.html">
									<div>
										<i class="fa fa-qq fa-fw"></i> 3条新回复 <span
											class="pull-right text-muted small">12分钟钱</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li>
								<div class="text-center link-block">
									<a class="J_menuItem" href="notifications.html"> <strong>查看所有
									</strong> <i class="fa fa-angle-right"></i>
									</a>
								</div>
							</li>
						</ul></li> -->
					<li class="dropdown hidden-xs"><a class="right-sidebar-toggle"
						aria-expanded="false"> <i class="fa fa-tasks"></i> 主题
					</a></li>
				</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
				<div class="page-tabs-content">
					<a href="javascript:;" class="active J_menuTab"
						data-id="index_v1.html">首页</a>
				</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="${pageContext.request.contextPath }/loginOut"
					class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="${pageContext.request.contextPath}/start" frameborder="0"
					data-id="index_v1.html" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					&copy; 2015-2016 <a href="http://www.htit-china.com"
						target="_blank">宏图软件教育</a>
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<ul class="nav nav-tabs navs-3">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> 主题
					</a></li>
					<!-- <li class=""><a data-toggle="tab" href="#tab-2"> 通知 </a></li>
					<li><a data-toggle="tab" href="#tab-3"> 项目进度 </a></li> -->
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式。</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu"
											class="onoffswitch-checkbox" id="collapsemenu"> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar"
											class="onoffswitch-checkbox" id="fixednavbar"> <label
											class="onoffswitch-label" for="fixednavbar"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout"
											class="onoffswitch-checkbox" id="boxedlayout"> <label
											class="onoffswitch-label" for="boxedlayout"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">
										默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">
										蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">
										黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>
					<!-- <div id="tab-2" class="tab-pane">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 最新通知
							</h3>
							<small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>
						</div>
					</div>
					<div id="tab-3" class="tab-pane">

						<div class="sidebar-title">
							<h3>
								<i class="fa fa-cube"></i> 最新任务
							</h3>
							<small><i class="fa fa-tim"></i> 您当前有14个任务，10个已完成</small>
						</div>
					</div> -->
				</div>
			</div>
		</div>

		<div class="modal inmodal" id="window_update" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">教员反馈</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<input type="hidden" id="feedbackid"">
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈人：</label> <input
									type="hidden" id="stuid" value="${student.id }">
								<div class="col-sm-8">
									<input id="seminarTheme" name="stuName" class="form-control"
										type="text" aria-required="true" aria-invalid="false"
										class="form-control" value="${student.stuname }"
										readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈教员：</label> <input
									type="hidden" id="empid">
								<div class="col-sm-8">
									<input id="empName" name="empName" class="form-control"
										type="text" aria-required="true" aria-invalid="false"
										class="form-control" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈时间：</label>
								<div class="col-sm-8">
									<input placeholder="反馈日期" class="form-control layer-date"
										id="start" name="start">
								</div>
							</div>
							<div id="itemId"></div>
							<div class="form-group" id="itemScore"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈意见：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="反馈说明…"
										style="resize: none" id="remark" name="remark"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="addBack()"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script
		src="${pageContext.request.contextPath }/js/jquery.min.js?v=2.1.4"></script>
	<script
		src="${pageContext.request.contextPath }/js/bootstrap.min.js?v=3.3.6"></script>
	<script
		src="${pageContext.request.contextPath }/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script
		src="${pageContext.request.contextPath }/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/js/plugins/layer/layer.min.js"></script>

	<!-- 自定义js -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/hplus.js?v=4.1.0"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/contabs.js"></script>

	<!-- 第三方插件 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/plugins/pace/pace.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#start',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
			max : laydate.now(), //最大日期
			istime : true,
			istoday : true
		};
		laydate(start);
	</script>
	<script type="text/javascript">
		$.validator.setDefaults({
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success')
						.addClass('has-error');
			},
			success : function(element) {
				element.closest('.form-group').removeClass('has-error')
						.addClass('has-success');
			},
			errorElement : "span",
			errorPlacement : function(error, element) {
				if (element.is(":radio") || element.is(":checkbox")) {
					error.appendTo(element.parent().parent().parent());
				} else {
					error.appendTo(element.parent());
				}
			},
			errorClass : "help-block m-b-none",
			validClass : "help-block m-b-none"

		});
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					start : "required",
					score : {
						required : true,
						range : [ 0, 10 ]
					},
					remark : "required"

				},
				messages : {
					start : icon + "请选择反馈日期",
					score : {
						required : icon + "请填写该项评分",
						range : icon + "请输入0-10之间的数"

					},
					remark : icon + "请填写建议"

				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
	<script type="text/javascript">
		function getTemplate(obj) {
			var url = "${pageContext.request.contextPath }/getAllTemplate";
			$.post(url, {
				_method : "PUT"
			}, resetInput, "text");
			var url2 = "${pageContext.request.contextPath }/dailyWork/findemp";
			$.post(url2, {
				_method : "POST",
				id : obj.id
			}, resetInput2, "text");

		}
		function resetInput(data) {
			//alert(data);
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				var str = "";
				var str2 = "";
				for (var i = 0; i < datas.rows.length; i++) {
					str += '<input type="hidden" id="templateId" value="'+datas.rows[i].id+'">';
					str2 += '<label class="col-sm-3 control-label">'
							+ datas.rows[i].name
							+ '</label><div class="col-sm-8" id="scoreInput"><input id="score" name="score" class="form-control"type="number" placeholder="输入0-'+datas.rows[i].score+'之间的数字"aria-required="true" aria-invalid="false"></div><br />';
				}
				$('#itemId').html(str);
				$('#itemScore').html(str2);
			} else {
				$('#itemId').html("");
				$('#itemScore').html("");
			}
		}
		function resetInput2(data) {
			//alert(data);
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				$('#window_update #empid').val(datas.rows[0].id);
				$('#window_update #empName').val(datas.rows[0].empname);
			}
		}
		//新增学生，ajax提交
		function addBack() {
			if (!validateForm($("#addForm"))) {
				return;
			}
			//return;
			var url = "${pageContext.request.contextPath }/education/feedback/add";
			$.post(url, {
				stuId : $('#window_update #stuid').val(),
				empId : $('#window_update #empid').val(),
				feedbackTime : $('#window_update #start').val(),
				feedbackRemark : $('#window_update #remark').val()
			}, addBackHandle1, "text");

		}
		function addBackHandle1(data) {
			if (data > 0) {
				$("#feedbackid").val(data);
				var ids = "";
				var scores = "";
				var len = $("#itemId").children('input').length;
				//alert(len);
				$("#itemId input").each(function(index, d) {
					if ((index + 1) != len) {
						ids += $(d).val() + ',';
					} else {
						ids += $(d).val();
					}
				});
				$("#scoreInput input").each(function(index, d) {
					if ((index + 1) != len) {
						scores += $(d).val() + ',';
					} else {
						scores += $(d).val();
					}
				});
				var url2 = "${pageContext.request.contextPath }/education/feedbackdetail/add";
				$.post(url2, {
					feedbackId : $('#feedbackid').val(),
					createTime : ids,
					updateTime : scores
				}, addBackHandle, "text");
				$("#window_update").modal('hide');
				function addBackHandle(data) {
					if (data > 0) {
						swal({
							title : "反馈成功",
							text : "你已经完成反馈操作",
							type : "success"
						});
					} else {
						swal("添加失败", "请检查你输入的数据！", "error");
					}
				}
				location.reload();//shuaxin页面，让通知失效
			}
		}
	</script>
</html>