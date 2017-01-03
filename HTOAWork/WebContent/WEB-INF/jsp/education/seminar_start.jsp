<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起研讨会</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/assets/css/css.css" />
<script type="text/javascript">
	//全局变量，读取当前行的数据
	var rows = null;
	$(function() {
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();

		//新增按钮事件
		$("#btn_add").click(function() {
			$("#window_add #operatorId").val($("#window_add #addid").val());
		});
		//修改按钮事件
		$("#btn_edit").click(
				function() {
					//把内容放到更新的列表
					$('#window_update #id ').val(rows.id);
					$('#window_update #empId ').val(rows.empId);
					$('#window_update #seminarTheme ').val(rows.seminarTheme);
					//$('#window_update #operatorId ').val(rows.operatorId);
					$('#window_update #startTime ').val(
							rows.startTime.substring(0, 19));
					$('#window_update #endTime ').val(
							rows.endTime.substring(0, 19));
				});
		//条件查询click事件注册
		$("#query").click(function() {
			$("#seminar_Tab").bootstrapTable('refresh');
		});
		$("#clear").click(function() {
			operatorId : $('#formSearch #operatorId').val('');
			seminarTheme : $('#formSearch #seminarTheme').val('');
			empId : $('#formSearch #empId').val('');
			startTime : $('#formSearch #start').val('');
			endTime : $('#formSearch #end').val('');
			$("#seminar_Tab").bootstrapTable('refresh');
		});
		//删除按钮事件
		$('#btn_delete').click(function() {
			swal({
				title : "您确定要删除这条信息吗",
				text : "删除后将无法恢复，请谨慎操作！",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "是的，我要删除！",
				cancelButtonText : "让我再考虑一下…",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					//调用删除函数
					deleteSeminar();
				} else {
					swal("已取消", "您取消了删除操作！", "error");
				}
			});
		});

	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#seminar_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/education/seminar/seminarlist', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : oTableInit.queryParams,//传递参数（*）
				//queryParamsType: "limit",
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 7, //每页的记录行数（*）
				pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
				search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
				strictSearch : false,
				showColumns : true, //是否显示所有的列
				showRefresh : true, //是否显示刷新按钮
				minimumCountColumns : 2, //最少允许的列数
				clickToSelect : true, //是否启用点击选中行
				height : 400, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
				singleSelect : true, //设置为单选
				onClickRow : function(row, $element) {//selected
					//$element是当前tr的jquery对象
					if (rows != null) {
						rows = null;
					}
					rows = row;
					
					var id = rows.id;
					var url = "${pageContext.request.contextPath }/education/seminardetail/" + id;
					//alert(url);
					$.post(url, {
						_method : 'GET'
					}, getSeminarDetailHandle, "text");
				},//单击row事件
				columns : [ {
					checkbox : true
				}, {
					field : 'id',
					title : 'ID',
					visible : false
				}, {
					align:"center",
					field : 'operator',
					title : '操作人',
					formatter : function(value, row, index) {
						var operator = row.operator;
						if(operator==null){
							return "-";
						}else{
							return operator.empname;
						}
					} 
				},{
					align:"center",
					field : 'sayMan',
					title : '发言人',
					formatter : function(value, row, index) {
						var sayMan = row.sayMan;
						if(sayMan==null){
							return "-";
						}else{
							return sayMan.empname;
						}
					} 
				},{
					align:"center",
					field : 'startTime',
					title : '开始时间',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 19);
						}
					}
				}, {
					align:"center",
					field : 'endTime',
					title : '结束时间',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 19);
						}
					}
				}, {
					field : 'seminarTheme',
					title : '研讨会主题'
				}]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				operatorId : $('#formSearch #operatorId').val(),
				seminarTheme : $('#formSearch #seminarTheme').val(),
				empId : $('#formSearch #empId').val(),
				startTime : $('#formSearch #start').val(),
				endTime : $('#formSearch #end').val()
			};
			return temp;
		};
		return oTableInit;
	}

	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	}
	//新增学生，ajax提交
	function addSeminar() {
		if(!validateForm($("#addForm"))){
			return;
		}
		var url = "${pageContext.request.contextPath }/education/seminar/add";
		$.post(url, {
			empId : $('#window_add #empId').val(),
			seminarTheme : $('#window_add #seminarTheme').val(),
			operatorId : $('#window_add #operatorId').val(),
			startTime : $('#window_add #startTimeadd').val(),
			endTime : $('#window_add #endTimeadd').val(),
			remark : $('#window_add #remark').val()
		}, addSeminarHandle, "text");
		$("#window_add").modal('hide')
		$("#window_add #empId").val('');
		$("#window_add #seminarTheme").val('');
		$("#window_add #operatorId").val('');
		$("#window_add #startTimeadd").val('');
		$("#window_add #endTimeadd").val('');
		$("#window_add #remark").val('');
	}

	function addSeminarHandle(data) {
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		$('#seminar_Tab').bootstrapTable('refresh');
	}

	//修改学生，ajax提交
	function updateSeminar() {
		if(null==$("#window_update #id").val() || $("#window_update #id").val()==undefined || $("#window_update #id").val()==''){
			swal("修改失败", "选择一个你要修改的数据！", "error");
			$("#window_update").modal('hide');
			return;
		}
		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/education/seminar/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			empId : $('#window_update #empId').val(),
			seminarTheme : $('#window_update #seminarTheme').val(),
			//operatorId : $('#window_update #operatorId').val(),
			startTime : $('#window_update #startTime').val(),
			endTime : $('#window_update #endTime').val(),
			remark : $('#window_update #remark').val()
		}, updateSeminarHandle, "text");
		
		$("#window_update").modal('hide');
		$("#window_update #id").val("");
		$("#window_update #seminarTheme").val('');
		$("#window_update #empId").val('');
		$("#window_update #startTime").val('');
		$("#window_update #endTime").val('');
		$("#window_update #remark").val('');
		
	}
	function updateSeminarHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成修改操作",
				type : "success"
			});
		} else {
			swal("修改失败", "请检查你输入的数据！", "error");
		}
		//刷新表格
		$('#seminar_Tab').bootstrapTable('refresh');
		//把保存选中行的rows变量清零，很重要，防止缓存
		rows = null;
	}
	function deleteSeminar() {
		if(rows==null){
			swal("删除失败", "选择一个你要删除的数据！", "error");
			$("#window_update").modal('hide');
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/education/seminar/" + id;
		//alert(url);
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : rows.id
		//从选中的rows中获取id
		}, deleteSeminarHandle, "text");
	}
	function deleteSeminarHandle(data) {
		if (data > 0) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else {
			swal("删除失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#seminar_Tab').bootstrapTable('refresh');
	}
	/* 明细操作 */
	
	
	function getSeminarDetailHandle(data){
		var datas = JSON.parse(data);
		//alert(data);
		//先清空
		$('#seminar_detail #id').val('');
		$('#seminar_detail #empNames').val('');
		$('#seminar_detail #empId').val('');
		$('#seminar_detail #remark').val('');
		
		//后填充
		$('#seminar_detail #id').val(datas.rows.id);
		$('#seminar_detail #empNames').val(datas.rows.empNames);
		$('#seminar_detail #empId').val(datas.rows.empId);
		$('#seminar_detail #remark').val(datas.rows.detailRemark);
		
	}
	
	function addDetail(){
		if(rows==null){
			swal("新增失败", "请先选择一条要新增会议明细的通知！", "warning");
			return;
		}
		$('#seminar_detail #empNames').val("");
		$('#seminar_detail #empId').val("");
		$('#seminar_detail #remark').val("");
	}
	
	function updateDetail(){
		if(rows==null){
			swal("修改失败", "请先选择一条要修改会议明细的通知！", "warning");
			return;
		}
		if($('#seminar_detail #empId').val()==null || $('#seminar_detail #empId').val()=='' || $('#seminar_detail #empId').val()==undefined){
			swal("操作警告", "请先选择参会人员！", "warning");
			return;
		}
		var id = rows.id;
		var url = '${pageContext.request.contextPath }/education/seminardetail/add';
		$.post(
				url,
				{
					seminarId : rows.id,
					id : $('#seminar_detail #id').val(),
					empId : $('#seminar_detail #empId').val(),
					empNames : $('#seminar_detail #empNames').val(),
					detailRemark : $('#seminar_detail #remark').val()
				},
				addDetailHandle,
				"text"
		);
	}
	function addDetailHandle(data){
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成修改操作",
				type : "success"
			});
		} else {
			swal("修改失败", "请检查你输入的数据！", "error");
		}
	}
</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>查询条件</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">操作人：</label>
								<div class="col-sm-3">
									<!-- <input class="form-control" type="text" id="operatorId"> -->
									<select class="form-control" name="operatorId"
										id="operatorId">
									<option value=>----不选择----</option>
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">主题：</label>
								<div class="col-sm-3">
									<input class="form-control" type="text" id="seminarTheme">
								</div>
								<label class="col-sm-1 control-label">发言人：</label>
								<div class="col-sm-3">
									<!-- <input class="form-control" type="text" id="empId"> -->
									<select class="form-control" name="empid"
										id="empId">
									<option value=>----不选择----</option>
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">时间：</label>
								<div class="col-sm-3">
									<input placeholder="会议开始日期" class="form-control layer-date"
										id="start" name="start">
								</div>
								<div class="col-sm-3">
									<input placeholder="会议结束日期" class="form-control layer-date"
										id="end" name="end">
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-success" id="clear"><span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary"
				data-toggle="modal" data-target="#window_add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success"
				data-toggle="modal" data-target="#window_update">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>

		<div class="modal inmodal" id="window_update" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated lightSpeedIn">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改研讨会信息</h4>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dataForm1"
							novalidate="novalidate">
							<div class="form-group">
								<input type="hidden" name="id" id="id" /><!--  <label
									class="col-sm-3 control-label">会议发起人：</label>
								<div class="col-sm-8">
									<input id="operatorId" name="operatorId" class="form-control"
										type="text">
								</div> -->
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">研讨会主题：</label>
								<div class="col-sm-8">
									<input id="seminarTheme" name="seminarTheme"
										class="form-control" type="text" aria-required="true"
										aria-invalid="false" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">发言人：</label>
								<div class="col-sm-8">
									<!-- <input id="empId" name="empId" class="form-control" type="text"
										aria-required="true" aria-invalid="true" class="form-control"> -->
									<select class="form-control" name="empId"
										id="empId">
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">会议时间：</label>
								<div class="col-sm-8">
									<input placeholder="会议开始时间" class="form-control layer-date"
										id="startTime" name="startTime"> <input
										placeholder="会议结束时间" class="form-control layer-date"
										id="endTime" name="endTime">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈说明：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="会议说明…"
										style="resize: none" id="remark"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" type="button" class="btn btn-white"
									data-dismiss="modal">关闭</button>
								<button type="button" onclick="updateSeminar()"
									class="btn btn-primary">保存</button>
							</div>
						</form>

					</div>
				</div>
			</div>
		</div>

		<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated rotateInUpLeft">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加发起研讨会通知</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<!-- <div class="form-group">
								<label class="col-sm-3 control-label">会议发起人：</label>
								<div class="col-sm-8">
									<input id="operatorId" name="operatorId" class="form-control"
										type="text">
								</div>
							</div> -->
							<div class="form-group">
								<input type="hidden" name="addid" id="addid" value="${empId}"/>
								<label class="col-sm-3 control-label">会议操作人：</label>
								<div class="col-sm-8">
									<select class="form-control col-sm-8" name="operatorId"
										id="operatorId" disabled="disabled">
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">研讨会主题：</label>
								<div class="col-sm-8">
									<input id="seminarTheme" name="seminarTheme"
										class="form-control" type="text" aria-required="true"
										aria-invalid="false" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">发言人：</label>
								<div class="col-sm-8">
									<!-- <input id="empId" name="empId" class="form-control" type="text"
										aria-required="true" aria-invalid="true" class="form-control"> -->
									<select class="form-control" name="empId"
										id="empId">
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">会议时间：</label>
								<div class="col-sm-8">
									<input placeholder="会议开始时间" class="form-control layer-date"
										id="startTimeadd" name="startTimeadd"> <input
										placeholder="会议结束时间" class="form-control layer-date"
										id="endTimeadd" name="endTimeadd">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">会议说明：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="会议说明…"
										style="resize: none" id="remark"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="addSeminar()"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal inmodal" id="window_emp" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated rotateInUpLeft">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="close">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">选择与会员工</h4>
					</div>

					<div class="ibox-content">
						<div class="col-sm-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<div>
										<button id="btn_delete" type="button" class="allclear fr btn btn-danger">
											<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>清除
										</button>
										<button id="btn_delete" type="button" class="allselect fr btn btn-info">
											<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>全选
										</button>
									</div>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="col-sm-6">
										<div id="mytree" class="col-md-12"></div>
										<div style="display: none" id="treedata">${deptree}</div>
									</div>
									<div class="col-sm-6 user_list_main">
										<div class="row">
								            <div class="col-md-12 eoffice_right_bg">待选择</div>
								        </div>
										<div class="row">
											<ul id="emp">
											</ul>
										</div>
										<br />
										<div class="row">
								            <div class="col-md-12 eoffice_right_bg">已选择</div>
								        </div>
								        <div class="row">
								            <div class="col-md-12">
								                <div class="selected_user"></div>
								            </div>
								        </div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" onclick="getEmp()"
								class="btn btn-primary">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="seminar_Tab"></table>
		<hr />
		<div class="row" id="seminar_detail">
			<div class="col-sm-12">
				<div class="tabs-container">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#tab-1"
							aria-expanded="true">研讨会明细操作</a></li>
						<!-- <li class=""><a data-toggle="tab" href="#tab-2"
							aria-expanded="false">研讨会明细操作</a></li> -->
					</ul>
					<div class="tab-content">
						<div id="tab-1" class="tab-pane active">
							<div class="panel-body">
								<div class="col-sm-12">
									<div class="ibox float-e-margins">
										<div class="form-group">
											<div class="col-sm-1">
												<button class="btn btn-primary" type="button"
													style="margin-top: 10px" onclick="addDetail();">新增明细</button>
											</div>
											<div class="col-sm-1">
												<button class="btn btn-warning" type="button"
													style="margin-top: 10px" onclick="updateDetail();">修改明细</button>
											</div>
										</div>
										<div class="ibox-title">
											<!-- <h5>发起反馈</h5> -->
											<div class="ibox-tools">
												<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
											</div>
										</div>
										<div class="ibox-content">
											<form class="form-horizontal m-t" id="dataForm">
												<div class="form-group">
													<input id="id" type="hidden">
													<label class="col-sm-3 control-label">参会人员：</label>
													<input id="empId" type="hidden">
													<div class="col-sm-6">
														<textarea id="empNames" name="empNames" readonly="readonly" class="form-control" aria-required="true" aria-invalid="true"
															class="form-control"></textarea>
													</div>
													<div class="col-sm-2">
														<button id="btn_addEmp" type="button" class="btn btn-w-m btn-info"  data-toggle="modal" data-target="#window_emp">
															<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>选择参会人员
															<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
														</button>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-3 control-label">会议总结：</label>
													<div class="col-sm-8">
														<textarea class="form-control" placeholder="会议总结…"
															style="resize: none" id="remark"></textarea>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- <div id="tab-2" class="tab-pane">
							<div class="panel-body">
								<strong>移动设备优先</strong>
								<p>在 Bootstrap 2 中，我们对框架中的某些关键部分增加了对移动设备友好的样式。而在 Bootstrap 3
									中，我们重写了整个框架，使其一开始就是对移动设备友好的。这次不是简单的增加一些可选的针对移动设备的样式，而是直接融合进了框架的内核中。也就是说，Bootstrap
									是移动设备优先的。针对移动设备的样式融合进了框架的每个角落，而不是增加一个额外的文件。</p>
							</div>
						</div> -->
					</div>
				</div>
			</div> 
		</div> 
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#start',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#end',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(),
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

	<script>
		//日期范围限制
		var start = {
			elem : '#startTime',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
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
			min : laydate.now(),
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
	<script>
		//日期范围限制
		var start = {
			elem : '#startTimeadd',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#endTimeadd',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(),
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
<script src="${pageContext.request.contextPath}/js/assets/js/jquery.nicescroll.js"></script>
<script
src="${pageContext.request.contextPath}/js/assets/js//bootstrap-treeview.js"
type="text/javascript"></script>
<script>
    //隐藏div中的滚动条
    $(".user_list_main").niceScroll({
        cursoropacitymax: 5,
    });
    $(".selected_user").niceScroll({
        cursoropacitymax: 5,
    });
</script>
<script>
    $(document).ready(function () {
        //user_list指向li同时给<a>添加hover
        $("li#user_list").mouseover(function () {
            $(this).find(".selected_tip").addClass("hover");
        });
        $("li#user_list").mouseout(function () {
            $(this).find(".selected_tip").removeClass("hover");
        });
    });
</script>

<script>
    $(document).ready(function () {
        //取消选中、remove <a>标签
        $(".selected_user").delegate("li", "click", function () {
            $(this).remove();
            var name = $(this).text();
            //alert(name);
            $("li.user_list").each(function () {
                if ($.trim($(this).text()) == name) {
                    $(this).children("span").remove();
                }
            });
        });
        //全选、清空
        $(".allselect").click(function () {
            //$(".selected_user").children("li").remove();//原来的选中的值不会清空
            //$("li#user_list").children("a").remove();
            $("li.user_list").each(function () {
                $(".selected_user").append("<li value="+$.trim($(this).attr('value'))+">" + $.trim($(this).text()) + "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span></li>");
                $(this).prepend("<a class='selected_tip' href='javascript:void(0)' role='button'></a> ");
            });
        }); 
        $(".allclear").click(function () {
            $(".selected_user").children("li").remove();
            $("li.user_list").children("a").remove();
        });
    });
    function sel(obj){
        var word = $(obj).text();
        //alert(obj.id);
        word = $.trim(word);
        var has = false;
        //alert(word);
        $(".selected_user li").each(function () {
            //alert($(this).text());
            if ($(this).text() == word) {
                has = true;
                $(this).remove();
            }
        });
        //alert(has);
        if (!has) {
            $(".selected_user").append("<li value="+obj.id+">" + word + "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span></li>");
            $(obj).prepend("<span class='glyphicon glyphicon-ok' aria-hidden='true'></span>");
        }
        else {
            $(obj).children("span").remove();
        }
	}
	$(function() {
		$('#mytree').treeview({
			data : $('#treedata').html(),
			showCheckbox : true,
			levels : 3,
			enableLinks : true,
			onNodeChecked : function(event, data) {
				var url = "${pageContext.request.contextPath }/education/feedbackstart/findemp/"
						+ data.id;
				$.post(url, {
					_method : 'PUT'
				}, initemp, "text");
			},
			onNodeUnchecked: function (event, data) {
				
			}
		});
		$('#mytree').on('nodeSelected', function(event, data) {
			// 事件代码...
			//alert(data.id);
			var url = "${pageContext.request.contextPath }/education/feedbackstart/findemp/"
				+ data.id;
			$.post(url, {
				_method : 'PUT'
			}, initemp, "text");
		});
	});
	function initemp(data) {
		var opt = "";
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			for (var i = 0; i<datas.rows.length; i++) {
				opt += '<li id="'+datas.rows[i].id+'" value="'+datas.rows[i].id+'" class="user_list" onClick="sel(this);">'+ datas.rows[i].empname + '</li>';
			}
			$('#emp').html(opt);
		}
	}
	function getEmp(){
		var ids = "";
		var names = "";
		var len = $(".selected_user").children('li').length;
		$(".selected_user li").each(function(index,d){
			if((index+1)!=len){
				ids += $(d).attr('value')+',';
				names += $(d).text()+',';
			}else{
				ids += $(d).attr('value');
				names += $(d).text();
			}
		});
		$('#seminar_detail #empNames').val(names);
		$('#seminar_detail #empId').val(ids);
		$("#window_emp").modal('hide');
	}
</script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script type="text/javascript">
    $.validator.setDefaults({
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.closest('.form-group').removeClass('has-error').addClass('has-success');
        },
        errorElement: "span",
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"


    });
	//调用表单验证的方法，在addStudent()里调用，出入form对象
	//***input控件要设置name属性***
	function validateForm(formdata){
		var icon = "<i class='fa fa-times-circle'></i> ";
        var validator = formdata.validate({
            rules: {
            	seminarTheme:"required",
            	startTimeadd: "required",
            	endTimeadd: {
            		required : true
            	}
            	
            },
            messages: {
            	seminarTheme: icon+"请填写研讨会主题",
            	startTimeadd: icon + "请选择反馈开始日期",
            	endTimeadd: {
            		required : icon + "请选择反馈结束日期"
            	}
            }
        });
        //返回表单验证是否通过，true false
        return validator.form();
	}
</script>
</body>
</html>