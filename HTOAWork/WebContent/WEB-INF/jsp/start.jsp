<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.ht.popj.dailyWork.Dutymodeldetail"%>
<%@page import="com.ht.popj.dailyWork.DutyMaxTemp"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<link href="${pageContext.request.contextPath}/css/clockstyle.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/fonts/infoIcon/iconfont.css"
	rel="stylesheet">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
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
												<p class="lead text-left">
													<font color="#000000" size="4px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${notice1.content}</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${notice1.emp.empname}(宣)</font>
												</p>
												<%-- <p class="lead text-right">
													<font color="#000000" size="3px">${fn:substringBefore(notice1.noticeTime, " ")}</font>
												</p> --%>
											</div>
										</div>
									</div>
									<div class="carousel-caption">
										<p><font color="#000000" size="3px">${fn:substringBefore(notice1.noticeTime, " ")}</font></p>
									</div>
								</div>
								<div class="item ">
									<div class="panel-body">
										<div class="widget-head-color-box lazur-bg p-lg text-center">
											<h2>系统公告</h2>
											<div class="widget-text-box">
												<br />
												<p class="lead text-left">
													<font color="#000000" size="4px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${notice2.content}</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${notice2.emp.empname}(宣)</font>
												</p>
												<%-- <p class="lead text-right">
													<font color="#000000" size="3px">${fn:substringBefore(notice2.noticeTime, " ")}</font>
												</p> --%>
											</div>
										</div>
									</div>
									<div class="carousel-caption">
										<p><font color="#000000" size="3px">${fn:substringBefore(notice2.noticeTime, " ")}</font></p>
									</div>
								</div>
								<div class="item">
									<div class="panel-body">
										<div class="widget-head-color-box lazur-bg p-lg text-center">
											<h2>系统公告</h2>
											<div class="widget-text-box">
												<br />
												<p class="lead text-left">
													<font color="#000000" size="4px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${notice3.content}</font>
												</p>
												<p class="lead text-right">
													<font color="#000000" size="3px">${notice3.emp.empname}(宣)</font>
												</p>
												<%-- <p class="lead text-right">
													<font color="#000000" size="3px">${fn:substringBefore(notice3.noticeTime, " ")}</font>
												</p> --%>
											</div>
										</div>
									</div>
									<div class="carousel-caption">
										<p><font color="#000000" size="3px">${fn:substringBefore(notice3.noticeTime, " ")}</font></p>
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
			</div>
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5 style="color: #23c6c8;" id="thistime"></h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content" style="background: #23c6c8">
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<br />
						<div class="fill">
						<div class="reference"></div>
						<div class="clock" id="utility-clock">
						<div class="centre">
							<div class="dynamic"></div>
							<div class="expand round circle-1"></div>
							<div class="anchor hour">
								<div class="element thin-hand"></div>
								<div class="element fat-hand"></div>
							</div>
							<div class="anchor minute">
								<div class="element thin-hand"></div>
								<div class="element fat-hand minute-hand"></div>
							</div>
							<div class="anchor second">
								<div class="element second-hand"></div>
							</div>
							<div class="expand round circle-2"></div>
							<div class="expand round circle-3"></div>
						</div>
					</div>
				</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>今天值班</h5>
					</div>
					<c:forEach items="${teacList }" var="teac">
						<div class="ibox-content">
							<div class="row">
								<div class="col-xs-4">
									<small class="stats-label">值班老师</small>
									<h4>${teac.empName }</h4>
								</div>
	
								<div class="col-xs-4">
									<small class="stats-label">值班范围</small>
									<h4>${teac.empScope }</h4>
								</div>
								<div class="col-xs-4">
									<small class="stats-label">电话</small>
									<h4>${teac.empPhone }</h4>
								</div>
							</div>
						</div>
					</c:forEach>
					<c:forEach items="${clteacList }" var="clteac">
						<div class="ibox-content">
							<div class="row">
								<div class="col-xs-4">
									<small class="stats-label">值班班主任</small>
									<h4>${clteac.empName }</h4>
								</div>
	
								<div class="col-xs-4">
									<small class="stats-label">值班范围</small>
									<h4>${clteac.empScope }</h4>
								</div>
								<div class="col-xs-4">
									<small class="stats-label">电话</small>
									<h4>${clteac.empPhone }</h4>
								</div>
							</div>
						</div>
					</c:forEach>
					<c:forEach items="${leaderList }" var="leader">
						<div class="ibox-content">
							<div class="row">
								<div class="col-xs-4">
									<small class="stats-label">总值班</small>
									<h4>${leader.empName }</h4>
								</div>
	
								<div class="col-xs-4">
									<small class="stats-label">值班范围</small>
									<h4>${leader.empScope }</h4>
								</div>
								<div class="col-xs-4">
									<small class="stats-label">电话</small>
									<h4>${leader.empPhone }</h4>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="col-sm-8">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>本周值班安排</h5>
						<div class="ibox-tools">
							<span class="label label-primary">${dutyModel.modelname}</span>
						</div>
					</div>
					<div class="ibox-content no-padding">
						<table class="table  table-bordered " id="sample_1">
							<thead>
								<tr>
									<th align="center" class="hidden-480">时间</th>
									<c:forEach items="${dmtList }" var="dmt">
										<c:if test="${dmt.empType==1}">
											<c:set var="num1" value="${dmt.theMax}" />
										</c:if>
										<c:if test="${dmt.empType==2}">
											<c:set var="num2" value="${dmt.theMax}" />
										</c:if>
										<c:if test="${dmt.empType==3}">
											<c:set var="num3" value="${dmt.theMax}" />
										</c:if>
									</c:forEach>
									<c:forEach var="i" begin="1" end="${num1}">
										<th align="center" class="hidden-480">值班老师</th>
										<th align="center" class="hidden-480">值班范围</th>
									</c:forEach>
									<c:forEach var="i" begin="1" end="${num2}">
										<th align="center" class="hidden-480">值班班主任</th>
										<th align="center" class="hidden-480">值班范围</th>
									</c:forEach>
									<c:forEach var="i" begin="1" end="${num3}">
										<th align="center" class="hidden-480">总值班</th>
										<th align="center" class="hidden-480">值班范围</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody>

								<%
										List<Dutymodeldetail> modelDList = (List<Dutymodeldetail>)request.getAttribute("modelDList");
										List<String> weksList = (List<String>)request.getAttribute("weksList");
										List<DutyMaxTemp> dmtList = (List<DutyMaxTemp>)request.getAttribute("dmtList");
										String weekDays = (String)request.getAttribute("weekDays");
										int num1=0;
										int num2=0;
										int num3=0;
										for(int i=0;i<dmtList.size();i++){
											int temp = dmtList.get(i).getEmpType();
											if(temp == 1){
												num1 = dmtList.get(i).getTheMax();
											}else if(temp == 2){
												num2 = dmtList.get(i).getTheMax();
											}else if(temp == 3){
												num3 = dmtList.get(i).getTheMax();
											}
											
										}
										String week = "";
										for(int a=0;a<weksList.size();a++){
											week = weksList.get(a).toString();
											if(week.equals(weekDays)){
												%>
								<tr class="info">
									<td class="hidden-480"><%=week %></td>
									<%
											}else{
												%>
								
								<tr>
									<td class="hidden-480"><%=week %></td>
									<%
											}
											

											int temp1 = num1;
											for(int i=1;i<=temp1;){
												for(int j=0;j<modelDList.size();j++){
													if(modelDList.get(j).getWeekends().equals(week)){
														if(modelDList.get(j).getEmpType()==1){
															temp1--;
															%>
									<td class="hidden-480"><%=modelDList.get(j).getEmpName()%></td>
									<td class="hidden-480"><%=modelDList.get(j).getEmpScope()%></td>
									<% 
														}
													}
												}
												if(temp1>=1){
													temp1--;
													%>
									<td class="hidden-480">无</td>
									<td class="hidden-480">无</td>
									<% 
												}
											}
											
											int temp2 = num2;
											for(int i=1;i<=temp2;){
												for(int j=0;j<modelDList.size();j++){
													if(modelDList.get(j).getWeekends().equals(week)){
														if(modelDList.get(j).getEmpType()==2){
															temp2--;
															%>
									<td class="hidden-480"><%=modelDList.get(j).getEmpName()%></td>
									<td class="hidden-480"><%=modelDList.get(j).getEmpScope()%></td>
									<% 
														}
													}
												}
												if(temp2>=1){
													temp2--;
													%>
									<td class="hidden-480">无</td>
									<td class="hidden-480">无</td>
									<% 
												}
											}
											
											int temp3 = num3;
											for(int i=1;i<=temp3;){
												for(int j=0;j<modelDList.size();j++){
													if(modelDList.get(j).getWeekends().equals(week)){
														if(modelDList.get(j).getEmpType()==3){
															temp3--;
															%>
									<td class="hidden-480"><%=modelDList.get(j).getEmpName()%></td>
									<td class="hidden-480"><%=modelDList.get(j).getEmpScope()%></td>
									<% 
														}
													}
												}
												if(temp3>=1){
													temp3--;
													%>
									<td class="hidden-480">无</td>
									<td class="hidden-480">无</td>
									<% 
												}
											}
											%>
								</tr>
								<%
										}
									%>
							</tbody>
						</table>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
<script
		src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${pageContext.request.contextPath}/js/clockscript.js"></script>
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script type="text/javascript">
		$(function(){
			var mydate = new Date();
			var str = "" + mydate.getFullYear() + "年";
			str += (mydate.getMonth()+1) + "月";
			str += mydate.getDate() + "日";
			$('#thistime').html(str);
		});
	</script>
</html>