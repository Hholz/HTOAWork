<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script>
	var srmRows = null;
	//家庭情况行数据
	var srmdRows = null;
	function test(id) {
		$("#feedbackid").val(id);
		//让模板明细表刷新
		$('#tb_feedbackdetail').bootstrapTable('refresh');
	}
	$(function() {

		//表格数据
		$('#tb_feedback').bootstrapTable({
			url : '${pageContext.request.contextPath}/education/feedback/backbacklist', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType : "application/x-www-form-urlencoded",
			toolbar : '#toolbar_stuSrm', //工具按钮用哪个容器
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			queryParams : queryParams,//传递参数（*）
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageSize : 5, //每页的记录行数（*）
			pageList : [ 5, 10, 15, 20, 30 ], //可供选择的每页的行数（*）
			clickToSelect : true, //是否启用点击选中行
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			singleSelect : true, //设置为单选
			onCheck : function(row) {
				//$element是当前tr的jquery对象
				if (srmRows != null) {
					srmRows = null;
				}
				srmRows = row;
			},
			onUncheck : function(row) {
				if (srmRows != null) {
					srmRows = null;
				}
			},
			columns : [
				{
					checkbox : true,
				},
				{
					field : 'id',
					title : '编号',
					visible : false
				},
				{
					field : 'stuname',
					title : '反馈学生',
					align : "center",
					formatter : function(value, row, index) {
						var student = row.student;
						if(student==null){
							return "-";
						}else{
							return student.stuname;
						}
					} 
				},
				{
					field : 'empname',
					title : '教员',
					align : "center",
					formatter : function(value, row, index) {
						var emp = row.emp;
						if(emp==null){
							return "-";
						}else{
							return emp.empname;
						}
					} 
				},
				{
					field : 'score',
					title : '总分',
					align : "center",
					formatter : function(value, row, index) {
						var scoresum = row.scoresum;
						if(scoresum==null){
							return "-";
						}else{
							return scoresum;
						}
					} 
				},
				{
					field : 'feedbackRemark',
					title : '建议'
				},
				{
					field : 'opreater',
					title : '查看明细',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<a onclick='test("+ id+ ")'><i class='fa fa-file-text-o'>详情</i></a>";
					}
				} ]
		});

		//表格数据
		$('#tb_feedbackdetail').bootstrapTable({
			url : '${pageContext.request.contextPath}/education/feedbackdetail/backdetaillist', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType : "application/x-www-form-urlencoded",
			//toolbar : '#toolbar_stuSrmd', //工具按钮用哪个容器
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			queryParams : queryParams,//传递参数（*）
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageSize : 5, //每页的记录行数（*）
			pageList : [ 5, 10, 15, 20, 30 ], //可供选择的每页的行数（*）
			clickToSelect : true, //是否启用点击选中行
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			singleSelect : true, //设置为单选
			onCheck : function(row) {
				//$element是当前tr的jquery对象
				if (srmdRows != null) {
					srmdRows = null;
				}
				srmdRows = row;
			},//单击row事件
			onUncheck : function(row) {
				srmdRows = null;
			},
			columns : [ {
				checkbox : true,
			}, {
				field : 'id',
				title : '编号',
				visible : false
			}, {
				field : 'name',
				title : '打分项名称',
				formatter : function(value, row, index) {
					var template = row.template;
					if(template==null){
						return "-";
					}else{
						return template.name;
					}
				} 
			}, {
				field : 'score',
				title : '打分项得分'
			}, ]

		});

	});
	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			feedbackId : $("#feedbackid").val()
		};
		return temp;
	}
</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<span class="glyphicon glyphicon-question-sign"></span> 帮助
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<p>点击模板表详情,详细表显示所有打分项</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-8">
				<div class="ibox float-e-margins">
					<input type="hidden" id="feedbackid"> 
					<div class="ibox-title">
						<h5>反馈情况总表</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<table id="tb_feedback"></table>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>反馈情况明细表</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<table id="tb_feedbackdetail"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>