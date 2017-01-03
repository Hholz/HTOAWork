<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<jsp:include page="../styleInclude.jsp"></jsp:include>
    <script>
		$(function () {
			//激活提示
			$("[data-toggle='tooltip']").tooltip();
			
			//表格数据
			$('#tb_tuition').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/TuitionSet/Json', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#ibox-tools', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				},{
					field : 'fallid',
					title : '届别',
					formatter : function(value, row, index) {
						var fall = row.fall;
						if(fall==null){
							return "-";
						}else{
							return fall.level;
						}
					}
				},{
					field : 'majorName',
					title : '班级',
					formatter : function(value, row, index) {
						var major = row.major;
						if(major==null){
							return "-";
						}else{
							return major.majorName;
						}
					}
				}, {
					field : 'termName',
					title : '学期',
					formatter : function(value, row, index) {
						var termname = row.termname;
						if(termname==null){
							return "-";
						}else{
							return termname.termName;
						}
					}
				}, {
					field : 'status',
					title : '状态',
					formatter : function(value, row, index) {
						var status = row.status;
						if(status==null){
							return "-";
						}else if(status ==1){
							return "<span class='label label-success'>未生成缴费</span>";
						}else if(status ==2){
							return "<span class='label label-primary'>未生成缴费</span>";
						}
					}
				}, ]
			});
		});
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
			};
			return temp;
		}
    </script>
</head>
<body>
	<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
     	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>查询条件</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <p>这里写查询条件</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学生应缴学费表</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_tuition"></table>
					</div>
                </div>
            </div>
    	</div>
     <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>