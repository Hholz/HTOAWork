<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
	var srmYes = null;
	$(function() {
		//点击查找按钮事件
		$("#query").click(function() {
			$("#tb_myFlowAllNo").bootstrapTable('refresh');
		});
		//表格数据
		$('#tb_myFlowAllNo').bootstrapTable({
			url : '${pageContext.request.contextPath}/flow/flowAll/findMyFlowAllEmp', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType : "application/x-www-form-urlencoded",
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			queryParams : queryParams,//传递参数（*）
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 10, //每页的记录行数（*）
			pageList : [ 15, 20, 25 ], //可供选择的每页的行数（*）
			clickToSelect : true, //是否启用点击选中行
			showRefresh : true, //是否显示刷新按钮
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			showToggle : true, //是否显示详细视图和列表视图的切换按钮
			singleSelect : true, //设置为单选
			onCheck : function(row, $element) {
				//$element是当前tr的jquery对象
				if (srmYes != null) {
					srmYes = null;
				}
				srmYes = row;
			},//单击row事件
			onUncheck : function(row) {
				srmYes = null;
			},
			columns : [
				{
					field : 'empid',
					title : '姓名',
					formatter : function(value, row, index) {
						var emp = row.emp;
						if (emp == null) {
							return "-";
						} else {
							return emp.empname;
						}
					}
				},
				{
					field : 'flowmodeltypeid',
					title : '申请类型',
					formatter : function(value, row, index) {
						var modelType = row.modelType;
						if (modelType == null) {
							return "-";
						} else {
							return modelType.flowmodeltype;
						}
					}
				},
				{
					field : 'createTime',
					title : '申请时间',
				},
				{
					field : 'remark',
					title : '描述',
				},
				{
					field : 'status',
					title : '审批状态',
					formatter : function(value, row, index) {
						var status = row.status;
						if (status == 1) {
							return "申请成功";
						} else if (status == 2) {
							return "申请失败";
						}
					}
				},
			]

		});

	});
	
	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			flowmodeltypeid : $("#flowmodeltypeid").val(),
			status : $("#status").val(),
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
						<h5>查询条件</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<a class="close-link"> <i class="fa fa-times"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<div class="row">
									<label class="control-label col-sm-1">申请类型:</label>
			                        <div class="col-sm-2">
										<select class="form-control" id="flowmodeltypeid">
											<option value="">--------</option>
											<c:forEach items="${modeltypes }" var="type">
												<option value="${type.id }">${type.flowmodeltype }</option>
											</c:forEach>
										</select>
			                        </div>
									<label class="control-label col-sm-1">审批状态:</label>
			                        <div class="col-sm-2">
										<select class="form-control" id="status">
											<option value="">--------</option>
											<option value="1">申请成功</option>
											<option value="2">申请失败</option>
										</select>
			                        </div>
			                        <div class="col-sm-1">
			                        	<button type="button" class="btn btn-primary" id="query">查询</button>
			                        </div>
			                        <div class="col-sm-5">
			                        </div>
		                        </div>
	                        </div>
                        </form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>已结束的申请</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle"
								href="tabs_panels.html#"> <i class="fa fa-wrench"></i>
							</a>
							<ul class="dropdown-menu dropdown-user">
							</ul>
							<!-- <a class="close-link"> <i class="fa fa-times"></i>
							</a> -->
						</div>
					</div>

					<div class="ibox-content">

						<table id="tb_myFlowAllNo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</body>
</html>