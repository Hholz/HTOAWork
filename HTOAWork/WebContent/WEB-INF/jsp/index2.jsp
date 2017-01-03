<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>��ͼ�������ϵͳ-��ҳ</title>

<meta name="keywords" content="">
<meta name="description" content="">

<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->

<link rel="shortcut icon" href="${pageContext.request.contextPath }/img/favicon.ico">
<link href="${pageContext.request.contextPath }/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/css/style.css?v=4.1.0" rel="stylesheet">
<link href="${pageContext.request.contextPath }/fonts/myIcon/iconfont.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>
<link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>
<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--��ർ����ʼ-->
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
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
							class="clear"> <span class="block m-t-xs"><strong
									class="font-bold">Admin</strong></span> <span
								class="text-muted text-xs block">��������Ա<b class="caret"></b></span>
						</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li><a class="J_menuItem" href="">�޸�ͷ��</a></li>
							<li><a class="J_menuItem" href="">��������</a></li>
							<li><a class="J_menuItem" href="">��ϵ����</a></li>
							<li><a class="J_menuItem" href="">����</a></li>
							<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath }/loginOut">��ȫ�˳�</a></li>
						</ul>
					</div>
					<div class="logo-element">��ͼ�������</div>
				</li>
				
				<shiro:lacksRole name="ѧ��">  
				<li><a href="#"><i class="iconfont">&#xe60c;</i> <span
						class="nav-label">��������</span><span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe652;</i><span
								class="nav-label">��Ʒ����</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/task/tasklist">��Ʒ�깺����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/ReceiveMaterialTask/tasklist">��Ʒ��������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/ReturnMaterialTask/tasklist">��Ʒ�黹����</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe652;</i><span
								class="nav-label">��������</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/HolidayTask/tasklist">Ա���������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/OverWorkTask/tasklist">Ա���Ӱ�����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/ChangeHolidayTask/tasklist">Ա����������</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe652;</i><span
								class="nav-label">��������</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/student/ComputerRepair/tasklist">����ά�����봦��</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/student/BackUpComputer/tasklist">���õ������촦��</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/student/ReturnBackUpComputer/tasklist">���õ��Թ黹����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/student/NewComputer/tasklist">���͵������ô���</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/dailyWork/BaoxiaoTask/baoxiaotasklist">
								<span class="nav-label">��������</span>
						</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/dailyWork/Approve/swapdutyTask">
								<span class="nav-label">ֵ�������������</span>
						</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/student/deladjustCls/page">
								<span class="nav-label">ѧ���ְ���������</span>
						</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/student/delStuHoliday/page">
								<span class="nav-label">ѧ�������������</span>
						</a></li>
					</ul>
					
				</shiro:lacksRole>
				<!-- ----------------------------------------------------------- -->
				
				<shiro:hasRole name="ѧ��">  
				<li><a href="#"><i class="iconfont">&#xe60c;</i> <span
						class="nav-label">�ҵ����루ѧ����</span><span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/student/adjustCls/page">����ְ�</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/stuHoliday/page">ѧ�����</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							 href="${pageContext.request.contextPath}/student/applyHourse/studentApplyHourse">�����������</a></li>
					</ul>
				</li>
				</shiro:hasRole>
				
				<shiro:lacksRole name="ѧ��">
				<li><a href="#"><i class="iconfont">&#xe60c;</i> <span
						class="nav-label">�ճ��칫</span><span class="fa arrow"></span> </a>

					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/dailyWork/dep/deplist">���Ź���</a></li>
						<li><a href="#"><i class="iconfont">&#xe6af;</i><span
								class="nav-label">Ա������</span><span class="fa arrow"></span> </a>

							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/emp/emplist">Ա����Ϣ</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/works/workslist">��������</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/edu/edulist">��������</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/family/familylist">��ͥ��Ϣ</a>
								</li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Worklog/Workloglist">������־</a>
								</li>
							</ul></li>

						<li><a href="#"><i class="iconfont">&#xe659;</i><span
								class="nav-label">���ڹ���</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/kaoqin/attendencejsp">�������ݵ���/����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/AttendanceCount/AttendanceCountList">�鿴����ͳ��</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe60f;</i><span
								class="nav-label">�칫��Ʒ����</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/MateriaType/MTList">��Ʒ������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Material/materiallist">��Ʒ����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/ApplyMaterial/ApplyMaterialList">�칫��Ʒ�깺</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/ReceiveMaterial/ReceiveMaterialList">�칫��Ʒ����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/ReturnMaterial/ReturnMateriallList">�칫��Ʒ�黹</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe654;</i><span
								class="nav-label">�ݼٹ���</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Holiday/ApplyHolidayList/1">�������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Holiday/ApplyHolidayList/2">�Ӱ�����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Holiday/ApplyHolidayList/3">��������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Holidaytype/HtList">�ݼ����</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe6ef;</i><span
								class="nav-label">ֵ�����</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Duty/dutymodelList">ֵ��ģ��</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Duty/dutyList">�����Ű�</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Duty/swapdutyList">��������</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe609;</i><span
								class="nav-label">Ѳ�����</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Patrol/patrolClassList">�༶Ѳ��</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Patrol/patrolHouseList">����Ѳ��</a></li>
							</ul></li>
						<li><a href="#"><i class="iconfont">&#xe660;</i><span
								class="nav-label">��������</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Baoxiaotype/baoxiaotypelist">�������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/dailyWork/Baoxiao/baoxiaolist">��������</a></li>
							</ul></li>
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/dailyWork/Notice/noticeList">ϵͳ����</a></li>
					</ul></li>

				<li><a href="#"><i class="iconfont">&#xe607;</i> <span
						class="nav-label">�������</span> <span class="fa arrow"></span> </a>
					
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/education/term/termIndex">ѧ�ڹ���</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/education/seminar/list">���ֻ����</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe61f;</i><span
								class="nav-label">רҵ����</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/dept/deptIndex">Ժϵ����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/education/major/majorIndex">����רҵ</a></li>
							</ul>
						</li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe659;</i><span
								class="nav-label">�γ̹���</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/education/coursetype/typeIndex">�γ�������</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/education/course/courseIndex">�γ̹���</a></li>
								<li><a class="J_menuItem" href="${pageContext.request.contextPath }/education/outline/outlineIndex">�γ̴�ٹ���</a></li>
							</ul>
						</li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xeb85;</i><span
								class="nav-label">��������</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/feedbackstart/list">������֪ͨ</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/feedback/list">������¼�鿴</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xeb85;</i><span
								class="nav-label">�γ̽��ȹ���</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/syllabus/list">�γ̽��Ȱ���</a></li>
								<!-- <li><a class="J_menuItem" href="mailbox.html">�γ̽��ȼ�¼</a></li> -->
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/education/syllabuscompare/list">�γ̽��Ȳ鿴</a></li>
							</ul></li>
					</ul></li>
				<li><a href="#"><i class="iconfont">&#xe625;</i> <span
						class="nav-label">ѧ������</span> <span class="fa arrow"></span></a>

					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe628;</i><span
								class="nav-label">���༶</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
							<li><a class="J_menuItem" href="student/fallStu">�����Ϣ</a></li>
								<li><a class="J_menuItem" href="student/classStu">�༶��Ϣ</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe603;</i><span
								class="nav-label">�������</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/FloorLayer/Floor">¥��¥��</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/studentHourse/studentHourse">�������</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe625;</i><span
								class="nav-label">ѧ����Ϣ</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<shiro:hasPermission name="student:list">  
								    <li>
										<a class="J_menuItem" href="${pageContext.request.contextPath}/student/stuList">ѧ����Ϣ</a>
									</li>
								</shiro:hasPermission>
								<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/stuheart/sheartStu"≯�ļ�¼</a></li>
							<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/sreward/srewardStu">���ͼ�¼</a></li><li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/studentScore/studentScore">�ɼ���¼</a></li>
								<%-- <li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/edufam/page">ѧ����������</a></li>
								<li><a class="J_menuItem" href="mailbox.html">ѧ����ͥ���</a></li>
								<li><a class="J_menuItem" href="mailbox.html">��������</a></li> --%>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe7a2;</i><span
								class="nav-label">ѧ�����</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-second-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/reply/page">�������ģ��</a></li>
								<!-- <li><a class="J_menuItem" href="mailbox.html">�������ֱ��</a></li> -->
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/replyScore/page">¼����ɼ�</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/stuattenjsp">ѧ��������Ϣ</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/stujob/page">ѧ����ҵ����</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/studentFeedBack/studentFeedBack">ѧ���˷ѹ���</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/student/studentOption/studentOption">ѧ���������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe67f;</i><span
								class="nav-label">���Թ���</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/cmanagejsp">���Թ������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/student/scomputer/computerjsp">ѧ�����Թ���</a></li>
							</ul></li>
					</ul>
					</li>
				<li><a href="#"><i class="iconfont">&#xe615;</i> <span
						class="nav-label">����ϵͳ</span> <span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe61f;</i><span
								class="nav-label">�ɷѹ���</span><span class="fa arrow"></span></a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/Shouldcharge/chargelist">ѧ�ѹ���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/finance/financebalance/tuitionIndex">ѧ�ӷѹ���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/finance/feeStandard/feeStandardIndex">ѧ�ӷ�����</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xe637;</i><span
								class="nav-label">�������</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financebalance/balanceIndex">��֧����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financeType/financeTypeIndex">��֧���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financefee/financeFeeIndex">ѧ���ɷ����ͳ�Ʊ���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financeIncomeAndExpense/list">�������ͳ�Ʊ���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/reimburse/list">������ȷ��</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/applyurse/list">�칫��Ʒ�깺ȷ��</a></li>
							</ul></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"><i class="iconfont">&#xf0041;</i><span
								class="nav-label">���ʹ���</span><span class="fa arrow"></span> </a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/FinanceSalarySet/FinSalarySetList">Ա��������������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/finance/financesalary/financesalarylist">�·ݹ������ɡ�����</a></li>
							</ul></li>
					</ul></li>
				<li><a href="#"> <i class="iconfont">&#xe61a;</i> <span
						class="nav-label">�г�����</span> <span class="fa arrow"></span>
				</a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/student/intentionStudentList">����ѧ������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/student/trackStudentList">ѧ������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/market/student/prestudentList">Ԥ������ѧ������</a></li>
					</ul></li>
				<li><a href="#"> <i class="iconfont">&#xe646;</i> <span
						class="nav-label">ϵͳ����</span> <span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/sysSet/fattenjsp">����ʱ������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/sysSet/frewardjsp">���ڽ�������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/sysSet/finaPolicQuantitySet/finaPolicQuantitySet">��Ʒ������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/finance/FeedBackSet/feedback">������Ϣģ��</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/finance/TuitionSet/TuitionSet">ѧ��ѧ������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/sysSet/student">ѧ���������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath}/sysSet/salType/page">����������</a></li>
					</ul>
					<ul class="nav nav-second-level">
						<li><a href="#"> <i class="iconfont">&#xe652;</i><span
								class="nav-label">���̹���</span><span class="fa arrow"></span>
						</a>
							<ul class="nav nav-third-level">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/sysSet/FlowmodelType/FTypeList">����������</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/sysSet/FlowModel/FModeList">���̹���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/sysSet/Approvetype/AptypeList">�������͹���</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/sysSet/ApprovetDot/ApprovetDotList">�����ڵ����</a></li>
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath }/sysSet/Approvaltitle/ApprovaltitleList">������¼�鿴</a></li>
							</ul>
					</ul>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/power/roleAuth">Ȩ�޹���</a></li>
						<li><a class="J_menuItem"
							href="${pageContext.request.contextPath }/user/page">�û�����</a></li>
					</ul></li>
					</shiro:lacksRole>
			</ul>
		</div>
		</nav>
		<!--��ർ������-->
		<!--�Ҳಿ�ֿ�ʼ-->
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
							<!-- ���ڻ���֪ͨ��֪ͨ -->
							<c:if test="${fn:length(list)>0}">
								<c:forEach items="${list}" var="l">
									<li class="m-t-xs">
										<div class="dropdown-messages-box">
											<a href="profile.html" class="pull-left"> <img
												alt="image" class="img-circle"
												src="${pageContext.request.contextPath }/img/a5.jpg">
											</a>

											<div class="media-body">
												<strong>����֪ͨ</strong> <br> <small class="text-muted">��ʼʱ�䣺${fn:substring(l.startTime, 0, 19)}</small><br />
												<small class="text-muted">����ʱ�䣺${fn:substring(l.endTime, 0, 19)}</small><br />
												<small class="text-muted">���鷢���ˣ�${l.sayMan.empname}</small>
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
											<strong>����֪ͨ</strong>
										</div>
									</div>
								</li>
							</c:if>
							<!-- ���ڷ���֪ͨ��֪ͨ -->
							<c:if test="${fn:length(blist)>0}">
								<c:forEach items="${blist}" var="l">
									<li class="m-t-xs">
										<div class="dropdown-messages-box">
											<a href="profile.html" class="pull-left"> <img
												alt="image" class="img-circle"
												src="${pageContext.request.contextPath }/img/a5.jpg">
											</a>
											<div class="media-body">
												<strong>����֪ͨ</strong> <br> <small class="text-muted">��ʼʱ�䣺${fn:substring(l.startTime, 0, 19)}</small><br />
												<small class="text-muted">����ʱ�䣺${fn:substring(l.endTime, 0, 19)}</small><br />
												<small class="text-muted">���������ˣ�${l.empStart.empname}</small><br />
												<small class="text-muted">������ʦ��${l.empBeStart.empname}��ʦ</small><br />
												<small class="text-muted">�����༶��${l.stuClass.classname}</small><br />
												<button id="${l.empBeStart.id}" type="button"
													class="btn btn-info" data-toggle="modal"
													data-target="#window_update" onclick="getTemplate(this);">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>���������
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
										<i class="fa fa-envelope fa-fw"></i> ����16��δ����Ϣ <span
											class="pull-right text-muted small">4����ǰ</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li><a href="profile.html">
									<div>
										<i class="fa fa-qq fa-fw"></i> 3���»ظ� <span
											class="pull-right text-muted small">12����Ǯ</span>
									</div>
							</a></li>
							<li class="divider"></li>
							<li>
								<div class="text-center link-block">
									<a class="J_menuItem" href="notifications.html"> <strong>�鿴����
									</strong> <i class="fa fa-angle-right"></i>
									</a>
								</div>
							</li>
						</ul></li> -->
					<li class="dropdown hidden-xs"><a class="right-sidebar-toggle"
						aria-expanded="false"> <i class="fa fa-tasks"></i> ����
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
						data-id="index_v1.html">��ҳ</a>
				</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						�رղ���<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>��λ��ǰѡ�</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>�ر�ȫ��ѡ�</a></li>
						<li class="J_tabCloseOther"><a>�ر�����ѡ�</a></li>
					</ul>
				</div>
				<a href="${pageContext.request.contextPath }/loginOut"
					class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out"></i> �˳�</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="${pageContext.request.contextPath}/first" frameborder="0"
					data-id="index_v1.html" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					&copy; 2015-2016 <a href="http://www.htit-china.com"
						target="_blank">��ͼ�������</a>
				</div>
			</div>
		</div>
		<!--�Ҳಿ�ֽ���-->
		<!--�Ҳ������ʼ-->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<ul class="nav nav-tabs navs-3">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> ����
					</a></li>
					<!-- <li class=""><a data-toggle="tab" href="#tab-2"> ֪ͨ </a></li>
					<li><a data-toggle="tab" href="#tab-3"> ��Ŀ���� </a></li> -->
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> ��������
							</h3>
							<small><i class="fa fa-tim"></i> ����Դ�����ѡ���Ԥ������Ĳ��ֺ���ʽ��</small>
						</div>
						<div class="skin-setttings">
							<div class="title">��������</div>
							<div class="setings-item">
								<span>�������˵�</span>

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
								<span>�̶�����</span>

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
								<span> �̶���� </span>

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
							<div class="title">Ƥ��ѡ��</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">
										Ĭ��Ƥ�� </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">
										��ɫ���� </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">
										��ɫ/��ɫ���� </a>
								</span>
							</div>
						</div>
					</div>
					<!-- <div id="tab-2" class="tab-pane">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> ����֪ͨ
							</h3>
							<small><i class="fa fa-tim"></i> ����ǰ��10��δ����Ϣ</small>
						</div>
					</div>
					<div id="tab-3" class="tab-pane">

						<div class="sidebar-title">
							<h3>
								<i class="fa fa-cube"></i> ��������
							</h3>
							<small><i class="fa fa-tim"></i> ����ǰ��14������10�������</small>
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
							<span aria-hidden="true">&times;</span><span class="sr-only">�ر�</span>
						</button>
						<h4 class="modal-title">��Ա����</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<input type="hidden" id="feedbackid"">
							<div class="form-group">
								<label class="col-sm-3 control-label">�����ˣ�</label> <input
									type="hidden" id="stuid" value="${student.id }">
								<div class="col-sm-8">
									<input id="seminarTheme" name="stuName" class="form-control"
										type="text" aria-required="true" aria-invalid="false"
										class="form-control" value="${student.stuname }"
										readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">������Ա��</label> <input
									type="hidden" id="empid">
								<div class="col-sm-8">
									<input id="empName" name="empName" class="form-control"
										type="text" aria-required="true" aria-invalid="false"
										class="form-control" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">����ʱ�䣺</label>
								<div class="col-sm-8">
									<input placeholder="��������" class="form-control layer-date"
										id="start" name="start">
								</div>
							</div>
							<div id="itemId"></div>
							<div class="form-group" id="itemScore"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label">���������</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="����˵����"
										style="resize: none" id="remark" name="remark"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">�ر�</button>
								<button type="button" onclick="addBack()"
									class="btn btn-primary">����</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ȫ��js -->
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

	<!-- �Զ���js -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/hplus.js?v=4.1.0"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/contabs.js"></script>

	<!-- ��������� -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/plugins/pace/pace.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
	<script>
		//���ڷ�Χ����
		var start = {
			elem : '#start',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //�趨��С����Ϊ��ǰ����
			max : laydate.now(), //�������
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
		//���ñ���֤�ķ�������addStudent()����ã�����form����
		//***input�ؼ�Ҫ����name����***
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
					start : icon + "��ѡ��������",
					score : {
						required : icon + "����д��������",
						range : icon + "������0-10֮�����"

					},
					remark : icon + "����д����"

				}
			});
			//���ر���֤�Ƿ�ͨ����true false
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
							+ '</label><div class="col-sm-8" id="scoreInput"><input id="score" name="score" class="form-control"type="number" placeholder="����0-'+datas.rows[i].score+'֮�������"aria-required="true" aria-invalid="false"></div><br />';
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
		//����ѧ����ajax�ύ
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
							title : "�����ɹ�",
							text : "���Ѿ���ɷ�������",
							type : "success"
						});
					} else {
						swal("���ʧ��", "��������������ݣ�", "error");
					}
				}
				location.reload();//shuaxinҳ�棬��֪ͨʧЧ
			}
		}
	</script>
</html>