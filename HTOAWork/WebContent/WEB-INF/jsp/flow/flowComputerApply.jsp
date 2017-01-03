<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
<script type="text/javascript">
	var srmcomputer = null;
	$(function() {
		
		$('#query').click(function() {
			$("#flow_computerApply").bootstrapTable('refresh');
		});
		
		$('#flow_computerApply').bootstrapTable({
			url : '${pageContext.request.contextPath}/flow/flowComputerApply/findComputerApplyList', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType : "application/x-www-form-urlencoded",
			striped : true, //是否显示行间隔色
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			sortable : false, //是否启用排序
			queryParams : queryParams,//传递参数（*）
			queryParamsType: "limit",
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 10, //每页的记录行数（*）
			pageList : [ 10, 25, 50, 100], //可供选择的每页的行数（*
			strictSearch : false,
			showRefresh : true, //是否显示刷新按钮
			clickToSelect : true, //是否启用点击选中行
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			showToggle : true, //是否显示详细视图和列表视图的切换按钮
			singleSelect : true, //设置为单选
			onCheck : function(row, $element) {
				//$element是当前tr的jquery对象
				if (srmcomputer != null) {
					srmcomputer = null;
				}
				srmcomputer = row;
			},//单击row事件
			onUncheck : function(row) {
				srmcomputer = null;
			},
			columns : [ {
				checkbox : true
			},{
				field : 'fallid',
				align : 'center',
				title : '届别',
				formatter : function(value, row, index) {
					var student = row.student;
					if(student==null){
						return "-";
					}else if(student.classInfo==null){
						return "-";
					}else if(student.classInfo.studentfall==null){
						return "-";
					}else{
						return student.classInfo.studentfall.level
					}
				}
			},{
				field : 'classid',
				align : 'center',
				title : '班级',
				formatter : function(value, row, index) {
					var student = row.student;
					if(student==null){
						return "-";
					}else if(student.classInfo==null){
						return "-";
					}else{
						return student.classInfo.classname
					}
				}
			},{
				field : 'studid',
				align : 'center',
				title : '学生姓名',
				formatter : function(value, row, index) {
					var student = row.student;
					if (student == null) {
						return "-";
					} else {
						return student.stuname;
					}
				}
			},{
				field : 'computerapply',
				align : 'center',
				title : '电脑编号',
				formatter : function(value, row, index) {
					var computerapply = row.computerapply;
					if (computerapply == null) {
						return "-";
					} else {
						return computerapply;
					}
				}
			},{
				field : 'remark',
				align : 'center',
				title : '备注',
				formatter : function(value, row, index) {
					if(row.remark==null || row.remark==""){
						return "我是新生,已交学费,申请领取学生电脑";
					}else{
						return row.remark;
					}
				}
			},{
				field : 'status',
				title : '状态',
				align : 'center',
				formatter : function(value, row, index) {
					if(row.status==1){
						return '<button type="button" class="btn btn-sm btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>已发放</button>';;
					}else if(row.status==0){
						return "<button type='button' onclick='putComputerApply("+row.id+");' class='btn btn-sm btn-danger'><span class='glyphicon glyphicon-plus' aria-hidden='true'></span>发放电脑</button>";
					}
				}
			},]
		});
		
	});
	
	function putComputerApply(id){
		$('#window_computerApply #applyid').val(id);
		$('#window_computerApply').modal('show')
	}
	
	function surepay(){
		var url = "${pageContext.request.contextPath}/flow/flowComputerApply/putComputerApply";
		$.post(
			url,
			{
				id : $('#window_computerApply #applyid').val(),
				computerapply : $('#window_computerApply #computerApply').val(),
				materialid : $('#window_computerApply #materialid').val(),
			},
			isSure,
			"text"
		);
		$('#window_computerApply').modal('hide');
	}
	
	function isSure(data){
		if (data > 0) {
			swal({
				title : "发放成功",
				text : "该学生电脑发放成功",
				type : "success"
			});
		} else {
			swal("发放失败", "请稍后再确认操作！", "error");
		}
		$("#flow_computerApply").bootstrapTable('refresh');
	}
	
	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			fallid : $("#fallid").val(),
			classid : $("#classid").val(),
			studid : $("#studId").val(),
			status : $("#status").val()
		};
		return temp;
	}
	
	function selectClass(){
		var url = "${pageContext.request.contextPath }/flow/flowComputerApply/findComputerClassList";
		$.post(url, {
			id : $("#fallid").val(),
		}, findclass, "json");
	}
	
	function findclass(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<option value="">--------</option>';
		for(var i=0;i<len;i++){
			opt += '<option value="'+data.rows[i].id+'" hassubinfo="true">'
				+ data.rows[i].classname + '</option>';
		}
		$("#classid").html(opt);
	}
	
	function selectStud(){
		var url = "${pageContext.request.contextPath }/flow/flowComputerApply/findComputerStudentList";
		$.post(url, {
			id : $("#classid").val(),
		}, findStud, "json");
	}
	
	function findStud(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<option value="">--------</option>';
		for(var i=0;i<len;i++){
			opt += '<option value="'+data.rows[i].id+'" hassubinfo="true">'
				+ data.rows[i].stuname + '</option>';
		}
		$("#studId").html(opt);
	}
</script>
<title>Insert title here</title>
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
								<label class="col-sm-1 control-label">届别:</label>
								<div class="col-sm-2">
									<select class="form-control" id="fallid" onchange="selectClass();">
										<option value="">--------</option>
										<c:forEach items="${falls }" var="f">
											<option value="${f.id }">${f.level }</option>
										</c:forEach>
									</select>
		                        </div>
								<label class="control-label col-sm-1">班级名称:</label>
		                        <div class="col-sm-2">
									<select class="form-control" id="classid" onchange="selectStud();">
									</select>
		                        </div>
								<label class="control-label col-sm-1">学生姓名:</label>
		                        <div class="col-sm-2">
									<select class="form-control" id="studId">
									</select>
		                        </div>
		                        <label class="control-label col-sm-1">状态:</label>
		                        <div class="col-sm-1">
									<select class="form-control" id="status">
										<option value="">--------</option>
										<option value="0">未发放</option>
										<option value="1">已发放</option>
									</select>
		                        </div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query"><span class="glyphicon glyphicon-zoom-in"></span>查询</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal inmodal" id="window_computerApply" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated lightSpeedIn">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">发放学生电脑</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="computerApplyFrom"
							novalidate="novalidate">
							<input type="hidden" id="applyid"/>
							<div class="form-group">
								<label class="col-sm-3 control-label">电脑类别:</label>
								<div class="col-sm-8">
									<select class="form-control" id="materialid">
										<c:forEach items="${materials }" var="m">
											<option value="${m.id }">${m.materialname }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">电脑编号:</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="computerApply"> 
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="surepay();"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-body" style="padding-bottom: 0px;">
			<div class="row">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>发放学生电脑</h5>
							<div class="ibox-tools">
								<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
								</a> <a class="dropdown-toggle"
									href="tabs_panels.html#"> <i class="fa fa-wrench"></i>
								</a>
								<ul class="dropdown-menu dropdown-user">
								</ul>
							</div>
						</div>
						<div class="ibox-content">
							<table id="flow_computerApply"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</body>
</html>