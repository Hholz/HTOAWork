<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>处理学生请假</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox-title">
					<h5>处理学生请假申请</h5>
					<div class="ibox-tools">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
					</div>
				</div>
				<div class="ibox-content">
					<c:forEach items="${noList}" var="no">
						<div class="mail-box-header">
		                    <div class="pull-right tooltip-demo">
		                        <a href="${pageContext.request.contextPath}/student/delStuHoliday/succ/${no.id }"><i class="fa fa-reply"></i> 同意</a>
		                        <a href="${pageContext.request.contextPath}/student/delStuHoliday/fail/${no.id }"><i class="fa fa-reply"></i> 拒绝</a>
		                    </div>
		                    <h2>
		                   		 请假单
		                	</h2>
		                    <div class="mail-tools tooltip-demo m-t-md">
		                        <h3>
		                        <span class="font-noraml">理由： </span>${no.reason }
		                    	</h3>
		                        <h5>
		                        <span class="pull-right font-noraml">${no.stdate }</span>
		                        <span class="font-noraml">发件人： </span>${no.student.classInfo.classname } ${no.student.stuname }
		                    	</h5>
		                    </div>
                		</div>
               	 	</c:forEach>	
				</div>
			</div>
		</div>
		
	</div>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox-title">
					<h5>历史记录</h5>
					<div class="ibox-tools">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
					</div>
				</div>
				<div class="ibox-content">
                        <div>
                            <div class="chat-activity-list">
                            <c:forEach items="${delList}" var="del">
                                <div class="chat-element">
                                    <div class="media-body ">
                                        <strong>请假人：${del.student.classInfo.classname } ${del.student.stuname }</strong>
                                        <p class="m-b-xs">
                                            	理由：${del.reason }
                                        </p>
                                        <small class="pull-right text-navy marger">
                                        	<c:if test="${del.status==1}" >
                                        		<span class="label label-primary">已同意</span>
                                        	</c:if>
                                        	<c:if test="${del.status==2}" >
                                        		<span class="label label-danger">已拒绝</span>
                                        	</c:if>
                                        </small>
                                        <small class="text-muted">${del.stdate }</small>
                                    </div>
                                </div>
                            </c:forEach>
                            </div>
                        </div>
                        
                    </div>
			</div>
		</div>
		
	</div>
    <!-- 全局js -->
    <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>


    <!-- 自定义js -->
    <script src="js/content.js?v=1.0.0"></script>


    <!-- iCheck -->
    <script src="js/plugins/iCheck/icheck.min.js"></script>
    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>

    <!--统计代码，可删除-->




</body>
</html>