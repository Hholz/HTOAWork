<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起反馈</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
<!-- <script type="text/javascript" src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script> -->
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
			$("#window_add #startEmpid").val($("#window_add #addid").val());
			//发请求取出教员的课程
			var url = "${pageContext.request.contextPath}/education/feedbackstart/getTeacherByClassId";
			$.post(
				url,
				{
					_method : 'PUT',
					id : $("#window_add #classId").val()
				},
				resetEmp,
				"text"
			);
			function resetEmp(data){
				var datas = JSON.parse(data);
				if (datas.rows.length > 0) {
					var str = "";
					for (var i = 0; i<datas.rows.length; i++) {
						str += '<option value="'+datas.rows[i].emp.id+'">'
								+ datas.rows[i].emp.empname + '</option>';
					}
					$('#window_add #empId').html(str);
				}else{
					$('#window_add #empId').html("<option value=>暂无教员信息</option>");
				}
			}
		});
		//修改按钮事件
		$("#btn_edit").click(
				function() {
					//把内容放到更新的列表
					$('#window_update #id ').val(rows.id);
					$('#window_update #empId ').val(rows.empId);
					$('#window_update #classId ').val(rows.classId);
					/* $('#window_update #startEmpid ').val(rows.startEmpid); */
					$('#window_update #feedbackStatus ').val(
							rows.feedbackStatus);
					$('#window_update #startTime ').val(
							rows.startTime.substring(0, 19));
					$('#window_update #endTime ').val(
							rows.endTime.substring(0, 19));
					var url = "${pageContext.request.contextPath}/education/feedbackstart/getTeacherByClassId";
					$.post(
							url,
							{
								_method : 'PUT',
								id : $("#window_update #classId").val()
							},
							resetEmp,
							"text"
						);
					function resetEmp(data){
						var datas = JSON.parse(data);
						if (datas.rows.length > 0) {
							var str = "";
							for (var i = 0; i<datas.rows.length; i++) {
								str += '<option value="'+datas.rows[i].emp.id+'">'
										+ datas.rows[i].emp.empname + '</option>';
							}
							$('#window_update #empId').html(str);
						}else{
							$('#window_update #empId').html("<option value=>暂无教员信息</option>");
						}
					}	
				});
		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		$("#clear").click(function() {
			$('#formSearch #startEmpid').val('');
			$('#formSearch #empid').val('');
			$('#formSearch #classid').val('');
			/* feedbackStatus : $('#formSearch #status').val(''); */
			$('#formSearch #start').val('');
			$('#formSearch #end').val('');
			$("#feedbackstart_Tab").bootstrapTable('refresh');
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
					deleteStart();
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
			$('#feedbackstart_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/education/feedbackstart/startlist', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
				showExport: true, //是否显示导出
				//exportDataType: "selected", //basic', 'all', 'selected'.好像默认basic不写也罢
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : oTableInit.queryParams,//传递参数（*）
				queryParamsType: "limit",
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 10, //每页的记录行数（*）
				pageList : [ 10, 25, 50, 100, 'ALL'], //可供选择的每页的行数（*
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
				},//单击row事件
				onUncheck: function(row) {
		             if(rows != null){
		             	rows = null;
		             }
		         },
				columns : [ {
					align:"center",
					checkbox : true
				}, {
					align:"center",
					field : 'id',
					title : 'ID',
					visible : false
				}, {
					align:"center",
					field : 'empname',
					title : '反馈人',
					formatter : function(value, row, index) {
						var empBeStart = row.empBeStart;
						if(empBeStart==null){
							return "-";
						}else{
							return empBeStart.empname;
						}
					} 
				}, {
					align:"center",
					field : 'classname',
					title : '反馈班级',
					formatter : function(value, row, index) {
						var stuClass = row.stuClass;
						if(stuClass==null){
							return "-";
						}else{
							return stuClass.classname;
						}
					}
				} , {
					align:"center",
					field : 'startEmpname',
					title : '反馈发起人',
					formatter : function(value, row, index) {
						var empStart = row.empStart;
						if(empStart==null){
							return "-";
						}else{
							return empStart.empname;
						}
					} 
				},  {
					align:"center",
					field : 'feedbackStatus',
					title : '反馈状态'
				}, {
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
				} ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				startEmpid : $('#formSearch #startEmpid').val() ,
				empId : $('#formSearch #empid').val() ,
				classId : $('#formSearch #classid').val(),
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
	function addStart() {
		if(!validateForm($("#addForm"))){
			return;
		}
		var url = "${pageContext.request.contextPath }/education/feedbackstart/add";
		$.post(url, {
			empId : $('#window_add #empId').val(),
			classId : $('#window_add #classId').val(),
			startEmpid : $('#window_add #startEmpid').val(),
			feedbackStatus : $('#window_add #feedbackStatus').val(),
			startTime : $('#window_add #startTimeadd').val(),
			endTime : $('#window_add #endTimeadd').val(),
			remark : $('#window_add #remark').val()
		}, addStartHandle, "text");
		$("#window_add").modal('hide')
		$("#window_add #empId").val('');
		$("#window_add #classId").val('');
		$("#window_add #startEmpid").val('');
		$("#window_add #feedbackStatus").val('');
		$("#window_add #startTimeadd").val('');
		$("#window_add #endTimeadd").val('');
		$("#window_add #remark").val('');
	}

	function addStartHandle(data) {
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		$('#feedbackstart_Tab').bootstrapTable('refresh');
	}

	//修改学生，ajax提交
	function updateStart() {
		if(null==$("#window_update #id").val() || $("#window_update #id").val()==undefined || $("#window_update #id").val()==''){
			swal("修改失败", "选择一个你要修改的数据！", "error");
			$("#window_update").modal('hide');
			return;
		}
		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/education/feedbackstart/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			empId : $('#window_update #empId').val(),
			classId : $('#window_update #classId').val(),
			//startEmpid : $('#window_update #startEmpid').val(),
			feedbackStatus : $('#window_update #feedbackStatus').val(),
			startTime : $('#window_update #startTime').val(),
			endTime : $('#window_update #endTime').val(),
			remark : $('#window_update #remark').val()
		}, updateStartHandle, "text");
		$("#window_update").modal('hide');
		$("#window_update #id").val('');
		$("#window_update #empId").val('');
		$("#window_update #classId").val('');
		//$("#window_add #startEmpid").val('');
		$("#window_update #feedbackStatus").val('');
		$("#window_update #startTime").val('');
		$("#window_update #endTime").val('');
		$("#window_update #remark").val('');
	}
	function updateStartHandle(data) {
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
		$('#feedbackstart_Tab').bootstrapTable('refresh');
		//把保存选中行的rows变量清零，很重要，防止缓存
		rows = null;
	}

	function deleteStart() {
		if(rows==null){
			swal("删除失败", "选择一个你要删除的数据！", "error");
			$("#window_update").modal('hide');
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/education/feedbackstart/" + id;
		//alert(url);
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : rows.id
		//从选中的rows中获取id
		}, deleteStartHandle, "text");
	}
	function deleteStartHandle(data) {
		if (data > 0) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else {
			swal("删除失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#feedbackstart_Tab').bootstrapTable('refresh');
	}
	function seleClassAdd(){
		var url = "${pageContext.request.contextPath}/education/feedbackstart/getTeacherByClassId";
		$.post(
			url,
			{
				_method : 'PUT',
				id : $("#window_add #classId").val()
			},
			resetEmp,
			"text"
		);
		function resetEmp(data){
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				var str = "";
				for (var i = 0; i<datas.rows.length; i++) {
					str += '<option value="'+datas.rows[i].emp.id+'">'
							+ datas.rows[i].emp.empname + '</option>';
				}
				$('#window_add #empId').html(str);
			}else{
				$('#window_add #empId').html("<option value=>暂无教员信息</option>");
			}
		}		
	}
	function seleClassUpdate(){
		var url = "${pageContext.request.contextPath}/education/feedbackstart/getTeacherByClassId";
		$.post(
			url,
			{
				_method : 'PUT',
				id : $("#window_update #classId").val()
			},
			resetEmp,
			"text"
		);
		function resetEmp(data){
			//alert(data);
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				var str = "";
				for (var i = 0; i<datas.rows.length; i++) {
					str += '<option value="'+datas.rows[i].emp.id+'">'
							+ datas.rows[i].emp.empname + '</option>';
				}
				$('#window_update #empId').html(str);
			}else{
				$('#window_update #empId').html("<option value=>暂无教员信息</option>");
			}
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
								<label class="col-sm-1 control-label">发起人：</label>
								<div class="col-sm-3">
									<!-- <input class="form-control" type="text" id="startEmpid"> -->
									<select class="form-control" name="startEmpid"
										id="startEmpid">
										<option value=>----不选择----</option>
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">班级：</label>
								<div class="col-sm-3">
									<!-- <input class="form-control" type="text" id="classid"> -->
									<select class="form-control" name="classid"
										id="classid">
										<option value=>----不选择----</option>
										<c:forEach items="${allClass }" var="c">
											<option value="${c.id }">${c.classname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">反馈人：</label>
								<div class="col-sm-3">
									<!-- <input class="form-control" type="text" id="empid"> -->
									<select class="form-control" name="empid"
										id="empid">
										<option value=>----不选择----</option>
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<!-- <label class="col-sm-1 control-label">状态：</label>
								<div class="col-sm-3">
									<select class="form-control" name="fdstatus" id="status">
										<option value=>----不选择----</option>
										<option value="开始反馈">开始反馈</option>
										<option value="结束反馈">结束反馈</option>
										<option value="无效反馈">无效反馈</option>
									</select>
								</div> -->
								<label class="col-sm-1 control-label">时间：</label>
								<div class="col-sm-3">
									<input placeholder="反馈开始日期" class="form-control layer-date"
										id="start" name="start">
								</div>
								<div class="col-sm-3">
									<input placeholder="反馈结束日期" class="form-control layer-date"
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
				<div class="modal-content animated rotateInUpRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改发起反馈通知</h4>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dataForm1"
							novalidate="novalidate">
							<input type="hidden" name="id" id="id" />
							<!-- <div class="form-group">
								 <label
									class="col-sm-3 control-label">反馈发起人：</label>
								<div class="col-sm-8">
									<input id="startEmpid" name="startEmpid" class="form-control"
										type="text">
								</div>
							</div> -->
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈班级：</label>
								<div class="col-sm-8">
									<!-- <input id="classId" name="classId" class="form-control"
										type="text" aria-required="true" aria-invalid="false"> -->
									<select class="form-control" name="classId"
										id="classId" onchange="seleClassUpdate();">
										<c:forEach items="${allClass }" var="c">
											<option value="${c.id }">${c.classname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈人：</label>
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
								<label class="col-sm-3 control-label">反馈状态：</label>
								<div class="col-sm-8">
									<select class="form-control" name="feedbackStatus"
										id="feedbackStatus">
										<option value="有效反馈">有效反馈</option>
										<option value="无效反馈">无效反馈</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈时间：</label>
								<div class="col-sm-8">
									<input placeholder="反馈开始日期" class="form-control layer-date"
										id="startTime" name="startTime"> <input
										placeholder="反馈结束日期" class="form-control layer-date"
										id="endTime" name="endTime">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈说明：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="反馈说明…"
										style="resize: none" id="remark"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" type="button" class="btn btn-white"
									data-dismiss="modal">关闭</button>
								<button type="button" onclick="updateStart()"
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
				<div class="modal-content animated lightSpeedIn">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加发起反馈通知</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<!-- <div class="form-group">
								<input type="hidden" name="id" id="id" /> 
								<label class="col-sm-3 control-label">反馈发起人：</label>
								<div class="col-sm-8">
									<input id="startEmpid" name="startEmpid" class="form-control"
										type="text">
								</div>
							</div> -->
							<div class="form-group">
								<input type="hidden" name="addid" id="addid" value="${empId}"/>
								<label class="col-sm-3 control-label">反馈发起人：</label>
								<div class="col-sm-8">
									<select class="form-control col-sm-8" name="startEmpid"
										id="startEmpid" disabled="disabled">
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈班级：</label>
								<div class="col-sm-8">
									<!-- <input id="classId" name="classId" class="form-control"
										type="text" aria-required="true" aria-invalid="false"
										class="form-control"> -->
									<select class="form-control" name="classId"
										id="classId" onchange="seleClassAdd();">
										<c:forEach items="${allClass }" var="c">
											<option value="${c.id }">${c.classname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈人：</label>
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
								<label class="col-sm-3 control-label">反馈状态：</label>
								<div class="col-sm-8">
									<select class="form-control" name="feedbackStatus"
										id="feedbackStatus">
										<option value="有效反馈">有效反馈</option>
										<option value="无效反馈">无效反馈</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈时间：</label>
								<div class="col-sm-8">
									<input placeholder="反馈开始日期" class="form-control layer-date"
										id="startTimeadd" name="startTimeadd"> <input
										placeholder="反馈结束日期" class="form-control layer-date"
										id="endTimeadd" name="endTimeadd">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">反馈说明：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="反馈说明…"
										style="resize: none" id="remark"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="addStart()"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- table代码就这些，用js构建表格 -->
		<table id="feedbackstart_Tab"></table>
		 
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
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
	            	startTimeadd: "required",
	            	endTimeadd: {
	            		required : true
	            	}
	            	
	            },
	            messages: {
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