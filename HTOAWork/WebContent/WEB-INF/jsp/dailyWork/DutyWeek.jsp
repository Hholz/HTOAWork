<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page import="com.ht.popj.dailyWork.Dutymodeldetail"%>
<%@page import="com.ht.popj.dailyWork.DutyMaxTemp"%>                  
<%@page import="java.util.List"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>值班模板</title>

	<jsp:include page="../styleInclude.jsp"></jsp:include>
</head>
<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
     	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><span class="glyphicon glyphicon-question-sign"></span>  帮助</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <p>点击模板表详情,详细表显示所有打分项</p>
                        <table class="table  table-bordered " id="sample_1">
							<thead>
								<tr>
									<th align="center" class="hidden-480">时间</th>
									<c:forEach items="${dmtList }" var="dmt">
										<c:if test="${dmt.empType==1}">
											<c:set var="num1"  value="${dmt.theMax}"/>
										</c:if>
										<c:if test="${dmt.empType==2}">
											<c:set var="num2"  value="${dmt.theMax}"/>
										</c:if>
										<c:if test="${dmt.empType==3}">
											<c:set var="num3"  value="${dmt.theMax}"/>
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
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	
</body>
</html>