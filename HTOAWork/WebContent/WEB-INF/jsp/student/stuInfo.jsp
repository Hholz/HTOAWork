<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>个人详细信息</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link href="${pageContext.request.contextPath }/fonts/infoIcon/iconfont.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-sm-12">
	       <div class="panel panel-default">
	           <div class="panel-heading">
	           <a href="${pageContext.request.contextPath}/student/stuList">
					<button class="btn btn-warning " type="button"><i class="fa fa-mail-reply-all"></i>返回上一级
	                </button>
               </a>
	           </div>
	           <div class="panel-body">
	           		<div class="row">
	           			<div class="col-sm-3">
	           				<p><i class="iconfont">&#xe67b;</i> ID：${student.id}
		                     </p>
		                     <p><i class="iconfont">&#xe62c;</i> 编号：${student.stuno}
		                     </p>
		                     <p><i class="iconfont">&#xe61b;</i> 姓名：${student.stuname}
		                     </p>
		                     <p><i class="iconfont">&#xe636;</i> 毕业学校：${student.middleschool}
		                     </p>
		                     <p><i class="iconfont">&#xe712;</i> 性别：${student.sex}
		                     </p>
	           			</div>
	           			<div class="col-sm-3">
	           				<p><i class="iconfont">&#xe621;</i> 年龄：${student.age}
		                     </p>
		                     <p><i class="iconfont">&#xe699;</i> 出生日期：${student.birthday}
		                     </p>
		                     <p><i class="iconfont">&#xe62e;</i> 学生电话：${student.phone}
		                     </p>
		                     <p><i class="iconfont">&#xe887;</i> 家庭地址：${student.addr}
		                     </p>
		                     <p><i class="iconfont">&#xe600;</i> 班级：${student.classInfo.classname}
		                     </p>
	           			</div>
	           			<div class="col-sm-3">
	           				<p><i class="iconfont">&#xe603;</i> 宿舍：${student.hourse.hoursename}
		                     </p>
		                     <p><i class="iconfont">&#xe603;</i> 入学时间：${student.entertime}
		                     </p>
		                     <p><i class="iconfont">&#xe6bb;</i> 介绍老师：${student.introduretech}
		                     </p>
		                     <p><i class="iconfont">&#xf00e3;</i> 学生状态：
			                     <c:if test="${student.stuStatus==1}">
									在读
								 </c:if>
								 <c:if test="${student.stuStatus==2}">
									毕业
								 </c:if>
								 <c:if test="${student.stuStatus==3}">
									退学
								 </c:if>
								 <c:if test="${student.stuStatus==4}">
									创业
								 </c:if>
		                     </p>
		                     <p><i class="iconfont">&#xe65f;</i> 民族：${student.nation}
		                     </p>
	           			</div>
	           			<div class="col-sm-3">
	           				<p><i class="iconfont">&#xe61d;</i> 籍贯：${student.naplace}
		                     </p>
		                     <p><i class="iconfont">&#xe62e;</i> 户口性质：${student.resPJ.residence}
		                     </p>
		                     <p><i class="iconfont">&#xf0147;</i> 身份证号：${student.idcard}
		                     </p>
		                     <p><i class="iconfont">&#xe63b;</i> 就读专业：${student.professional}
		                     </p>
		                     <p><i class="iconfont">&#xe62e;</i> 专业类别：${student.major.majorName}
		                     </p>
	           			</div>
	           		</div>
	           </div>
	           <div class="panel-footer">
	                                                   
	           </div>
	       </div>
	   </div>
   </div>
	<div class="row">
         <div class="col-sm-6">
             <div class="ibox float-e-margins">
                 <div class="ibox-title">
                     <h5>学生受教育情况</h5>
                 </div>
                 <div class="ibox-content">
                 	<c:forEach items="${eduList}" var="edu">
	                    <div class="alert alert-info">
	                 	   <div class="row">
	                            <div class="col-sm-12">
				                   <div class="col-sm-5">
				                        ${edu.school}
				                   </div>
				                   <div class="col-sm-7">
				                       <span class="badge badge-info">${edu.begindate}</span>
				                       	&nbsp;&nbsp;&nbsp;&nbsp;
				                       <span class="badge badge-success">${edu.enddate}</span>
				                   </div>
				                </div>
				           </div>
	                    </div>                     
	                </c:forEach>
	             </div>
             </div>
         </div>
         <div class="col-sm-6">
             <div class="ibox float-e-margins">
                 <div class="ibox-title">
                     <h5>学生家庭成员</h5>
                 </div>
                 <div class="ibox-content">
                 	<c:forEach items="${famList}" var="fam">
	                    <div class="alert alert-info">
	                 	   <div class="row">
	                            <div class="col-sm-12">
				                   <div class="col-sm-4">
				                       <div class="text-center">
				                           <div class="m-t-xs font-bold"><h2><strong>${fam.relation}</strong></h2></div>
				                       </div>
				                   </div>
				                   <div class="col-sm-8">
				                       <h3><strong>姓名：${fam.familyname}</strong></h3>
				                       <abbr title="Phone">Tel:</abbr> ${fam.familyhone}
				                   </div>
				                   <div class="clearfix"></div>
				                </div>
				           </div>
	                    </div>                     
	                </c:forEach>
	             </div>
	         </div>
	     </div>
	   </div>
	</div>
</body>
<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/contabs.js"></script>
</html>