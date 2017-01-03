<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
</head>
<body>
	<div class="panel panel-info">
       <div class="panel-heading">
          	 <h2>处理分班申请</h2>
          	<c:forEach items="${toDelList}" var="toDel">
                   <div class="alert alert-info">
                	   <div class="row">
		                   <div class="col-sm-12">
		                   		<p><i class="iconfont">&#xe67b;</i>学生：${toDel.student.stuname }</p>
                                      <p>理由：${toDel.reason }</p>
                                      <p>申请时间：${toDel.createTime}</p>   
                                      <p>调换到班级：${toDel.tocls.classname}</p>
                           </div>
                           <div class="col-sm-3">
			                   <form  method="post" action="${pageContext.request.contextPath}/student/deladjustCls/succ">
			                   		<input type="hidden" id="id" name="id" value ="${toDel.id }" /> 
			                   		<input type="submit" value = "同意">
			                   </form> 
		                   </div> 
		                   
		                   <div class="col-sm-3">
			                   <form  method="post" action="${pageContext.request.contextPath}/student/deladjustCls/fail">
			                   		<input type="hidden" id="id" name="id" value ="${toDel.id }" /> 
			                   		<input type="submit" value = "拒绝" >
			                   </form> 
		                   </div>                                     
		           </div>
                   </div>                     
	            </c:forEach>
	            <c:forEach items="${toDelList2}" var="toDel2">
	                    <div class="alert alert-danger">
	                 	   <div class="row">
	                            <div class="col-sm-12">
				                   <div class="col-sm-6">
				                   		学生：${toDel2.student.stuname }&nbsp;&nbsp; &nbsp;&nbsp;
				                                                                                理由：${toDel2.reason }&nbsp;&nbsp; &nbsp;&nbsp;  
				                                                                        申请时间：${toDel2.createTime }&nbsp;&nbsp; &nbsp;&nbsp;   
				                                                                    调换到班级：${toDel2.tocls.classname }&nbsp;&nbsp;    &nbsp;&nbsp;  
				                   <form class="col-sm-3" method="post" action="${pageContext.request.contextPath}/student/deladjustCls/succ2">
				                   		<input type="hidden" id="id" name="id" value ="${toDel2.id }" /> 
				                   		<input type="submit" value = "同意">
				                   </form>  
				                   <form class="col-sm-3" method="post" action="${pageContext.request.contextPath}/student/deladjustCls/fail2">
				                   		<input type="hidden" id="id" name="id" value ="${toDel2.id }" /> 
				                   		<input type="submit" value = "拒绝" >
				                   </form>                                      
				                   </div>
				                   <div class="col-sm-12">
				                   <br>                                                
				                   </div>
				                </div>
				           </div>
	                    </div>                     
	            </c:forEach>
       </div>
       <div class="panel-body ">
       		<div class="panel-body well">
              	
              	<c:forEach items="${adjList}" var="ajc">
	                    <div class="alert alert-info">
	                 	   <div class="row">
	                            <div class="col-sm-12">
				                   <div class="col-sm-12">
				                   	学生：${ajc.student.stuname }&nbsp;&nbsp; &nbsp;&nbsp;
				                                                                                理由：${ajc.reason }&nbsp;&nbsp; &nbsp;&nbsp;  
				                                                                        申请时间：${ajc.createTime }&nbsp;&nbsp; &nbsp;&nbsp;   
				                                                                    调换到班级：${ajc.tocls.classname }&nbsp;&nbsp;    &nbsp;&nbsp;                                         
				                   </div>
				                   <div class="col-sm-12">
				                     <br>                                                
				                   </div>
				                   <div class="col-sm-12">
			                   			<span <c:if test="${ajc.acStatus==0}">class="badge badge-info"</c:if>>
			                   				等待自己班主任处理
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==1}"> class="badge badge-info" </c:if>>
			                   				班主任通过
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==2}"> class="badge badge-info" </c:if>>
			                   				班主任拒绝
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==3}"> class="badge badge-info" </c:if>>
			                   				另外班主任通过
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==4}"> class="badge badge-info" </c:if>>
			                   				另外班主任拒绝
			                   			</span>
				                   </div>
				                </div>
				           </div>
	                    </div>                     
	            </c:forEach>
	         	<div class="clearfix"></div>
           </div>
           
       </div>
   </div>
   <!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>