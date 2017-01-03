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
	var srmStud = null;
	$(function() {
		//查找按钮事件
		$('#btn_query').click(function () {
			//刷新数据
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if(startTime != "" && endTime == ""){
				parent.layer.alert('请输入结束时间');
				return;
			}
			if(startTime == "" && endTime != ""){
				parent.layer.alert('请输入开始时间');
				return;
			}
			$('#tb_FlowAllStud').bootstrapTable('refresh');
		});
		
		//表格数据
		$('#tb_FlowAllStud').bootstrapTable({
			url : '${pageContext.request.contextPath}/flow/flowAll/findFlowAllStud', //请求后台的URL（*）
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
				if (srmStud != null) {
					srmStud = null;
				}
				srmStud = row;
			},//单击row事件
			onUncheck : function(row) {
				srmStud = null;
			},
			columns : [
				{
					field : 'studid',
					title : '姓名',
					formatter : function(value, row, index) {
						var student = row.student;
						if (student == null) {
							return "-";
						} else {
							return student.stuname;
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
						} else {
							return "-";
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
			fallid : $("#fallid").val(),
			startTime : $("#startTime").val(),
			endTime : $("#endTime").val(),
			classid : $("#classid").val(),
			studid : $("#studId").val()
		};
		return temp;
	}
	
	function selectClass(){
		var url = "${pageContext.request.contextPath }/flow/flowAll/findClassList";
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
		var url = "${pageContext.request.contextPath }/flow/flowAll/findStudentList";
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
			                        <label class="control-label col-sm-1">界别:</label>
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
			                   	</div>
			               	</div>
			               	<div class="form-group">
			                   	<div class="row">
			                   		<label class="control-label col-sm-1">开始时间:</label>       
									<div class="col-sm-2">
										<input placeholder="请选择开始时间" class="form-control layer-date" id="startTime" name="startTime"></input>         
									</div>
									<label class="control-label col-sm-1">结束时间:</label>
									<div class="col-sm-2">
										<input placeholder="请选择结束时间" class="form-control layer-date" id="endTime" name="endTime"></input>
									</div>
			                        <div class="col-sm-1">
			                        	<button type="button" class="btn btn-primary" id="btn_query">查询</button>
			                        </div>
			                        <div class="col-sm-1">
										<a href="${pageContext.request.contextPath }/flow/flowAll/flowAllStudent">
											<button type="button" class="btn btn-primary">清空</button></a>
									</div>
									<div class="col-sm-4">
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
						<h5>已通过的申请</h5>
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
						<table id="tb_FlowAllStud"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script type="text/javascript">
		//日期范围限制
		var start = {
			elem : '#startTime',
			format : 'YYYY/MM/DD hh:mm:ss',
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#endTime',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59',
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		laydate(start);
		laydate(end);
	</script>
</body>
</html>