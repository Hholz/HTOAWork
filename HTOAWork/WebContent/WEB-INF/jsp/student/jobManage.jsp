<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<!-- iframe自适用大小 -->
<script type="text/javascript" language="javascript">   
function iFrameHeight() {   
	var ifm= document.getElementById("iframepage");   
	var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
		if(ifm != null && subWeb != null) {
		   ifm.height = subWeb.body.scrollHeight;
		   ifm.width = subWeb.body.scrollWidth;
		}   
	}   
</script>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-content mailbox-content">
                    	<div class="panel panel-info">
                            <div class="panel-heading">
                                <i class="fa fa-info-circle"></i>  选择班级
                            </div>
                            <div class="panel-body">
                                <p>通过下面的面板，通知年份届别来选择班级</p>
                            </div>
                        </div>
                        <div class="panel-group" id="accordion">
	                        <c:forEach items="${fallList}" var="fall">
		                        <div class="panel panel-default">
		                            <div class="panel-heading">
		                                <h5 class="panel-title">
		                                        <a data-toggle="collapse" data-parent="#accordion" href="tabs_panels.html#collapse${fall.id}" aria-expanded="false" class="collapsed"><i class="fa fa-navicon"></i>  ${fall.level}</a>
		                                    </h5>
		                            </div>
		                            <div id="collapse${fall.id}" class="panel-collapse collapse" aria-expanded="false">
		                                <div class="panel-body">
		                                    <ul class="tag-list" style="padding: 0">
		                                    	<c:forEach items="${fall.studentclass}" var="cls">
					                                <li><a href="${pageContext.request.contextPath }/student/stujob/ifram/${cls.id}" target="iframepage"> ${cls.classname }</a>
					                                </li>
				                                </c:forEach>
				                            </ul>
				                            <div class="clearfix"></div>
		                                </div>
		                            </div>
		                        </div>
		                    </c:forEach>
	                    </div>
                    </div>
                </div>
            </div>
            <!-- 右侧页面 -->
			<iframe class="col-sm-9" src="${pageContext.request.contextPath }/student/stujob/ifram" name="iframepage" id="iframepage"  frameborder="0" scrolling="no" marginheight="0" marginwidth="0" onLoad="iFrameHeight()"></iframe>
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
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>