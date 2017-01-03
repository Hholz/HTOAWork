<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>BootStrap Table使用</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
</head>
<body class="gray-bg">
	<div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="alert alert-success alert-dismissable m-t">
                    <div>
                        <h3>学生意见反馈</h3>
                    </div>
                    <div class="input-group">
                     		<h5>标签：</h5>
                           <ul class="tag-list" style="padding: 0">
                           		<li><a href="${pageContext.request.contextPath }/student/stuOptionList/studentOptionList"><i class="fa fa-tag"></i>全部</a>
	                                </li>
	                           	<c:forEach items="${stuSort }" var ="t">
	                           	 <li><a href="${pageContext.request.contextPath }/student/stuOptionList/${t.id }"><i class="fa fa-tag"></i> ${t.sortname }</a>
	                                </li>
	                           	</c:forEach>
                            </ul> 
                    </div>
					</div>
                </div>
            </div>
        </div>
    </div>
    <div class="wrapper wrapper-content  animated fadeInRight blog">
        <div class="row">
            <div class="col-lg-4">
            	<c:forEach items="${optionlist }" var="s">
	                <div class="ibox">
	                    <div class="ibox-content">
	                        <h3>${s.title }</h3>
	                        <div class="small m-b-xs">
                           	   
	                            <span class="text-muted">
	                            	<i class="fa fa-clock-o"></i>
	                            	<fmt:formatDate type="date" value="${s.createtime}" dateStyle="full"/>
					                      <fmt:formatDate value="${s.createtime}" type="time" timeStyle="short"/> 
					            </span>
	                        </div>
	                        <p>${s.content }</p>
	                        <div class="row">
	                            <div class="col-md-6">
	                                 <ul class="tag-list" style="padding: 0">
		                                <li><a><i class="fa fa-tag"></i>&nbsp;&nbsp;${s.titleclassid }</a>
		                                </li>
		                             </ul>
	                            </div>
	                            <div class="col-md-6">
	                                <div class="small text-right">
	                                    <h5>用户：</h5>
	                                    <div> 
	                                    	<i class="glyphicon glyphicon-user"> </i> 
				                              <c:if test="${s.ishidename == 0}">
			                           	 	   	<strong>匿名</strong>
			                 			   	  </c:if>
				                   			  <c:if test="${s.ishidename == 1}">
				                           	 	 <strong>${s.studentname }</strong>
				                 			  </c:if> 	 
	                                     </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </c:forEach>
            </div>
      </div>
    </div>
 </body>
</html>