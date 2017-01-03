<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>院系管理</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
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

		//新增按钮事件*************************
		$("#btn_add").click(function() {
			$('#myModal').modal();
		});
		$("#btn_query").click(function() {
			$('#tb_departments').bootstrapTable('refresh');
		});
		$("#btn_clear").click(function() {
			courseId: $("#txt_search_id").val("");
			outlineName: $("#txt_search_name").val("");
		});
		//修改按钮事件
		$("#btn_edit").click(
				function() {
					if (rows == null || rows.id == null) {
						swal("错误", "请算着需要修改的系！", "error");
					} else {
						$('#updateModal').modal();
						id: $("#updateModal #id").val(rows.id);
						outlineName: $("#updateModal #outlineName").val(
								rows.outlineName);
						hour: $("#updateModal #hour").val(rows.hour);
						outlineContent: $("#updateModal #outlineContent").val(
								rows.outlineContent);
						courseId: $("#updateModal #courseId")
								.val(rows.courseId);
						status: $("#updateModal #status").val(rows.status);
						createTime: $("#updateModal #createTime").val(
								rows.createTime);
					}
				});
		//删除按钮事件
		$("#btn_delete").click(function() {
			if (rows == null || rows.id == null) {
				swal("删除失败！", "请选择需要删除的系", "error");
			} else {
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
						deleteOutline();
					} else {
						swal("已取消", "您取消了删除操作！", "error");
					}
				});
			}
		});

	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_departments')
					.bootstrapTable(
							{
								url : '${pageContext.request.contextPath}/education/outline/outlineList', //请求后台的URL（*）
								method : 'post', //请求方式（*）
								contentType : "application/x-www-form-urlencoded",
								toolbar : '#toolbar', //工具按钮用哪个容器
								striped : true, //是否显示行间隔色
								cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
								pagination : true, //是否显示分页（*）
								sortable : false, //是否启用排序
								sortOrder : "asc", //排序方式
								queryParams : oTableInit.queryParams,//传递参数（*）
								sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
								pageNumber : 1, //初始化加载第一页，默认第一页
								pageSize : 10, //每页的记录行数（*）
								pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
								search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
								strictSearch : true,
								showColumns : true, //是否显示所有的列
								showRefresh : true, //是否显示刷新按钮
								minimumCountColumns : 2, //最少允许的列数
								clickToSelect : true, //是否启用点击选中行
								height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
								uniqueId : "ID", //每一行的唯一标识，一般为主键列
								showToggle : true, //是否显示详细视图和列表视图的切换按钮
								cardView : false, //是否显示详细视图
								detailView : false, //是否显示父子表
								singleSelect : true,
								onClickRow : function(row, $element) {//selected
									//$element是当前tr的jquery对象
									if (rows != null) {
										rows = null;
									}
									rows = row;
								},//单击row事件
								columns : [ {
									checkbox : true
								}, {
									field : 'id',
									title : '编号'
								}, {
									field : 'course.courseName',
									title : '课程名称'
								}, {
									field : 'outlineName',
									title : '大纲名称'
								}, {
									field : 'status',
									title : '状态',
									formatter : function(value, row, index) {
										if (row.status == "0") {
											return "启用";
										} else if (row.status == "1") {
											return "禁用";
										}
									}
								}, {
									field : 'hour',
									title : '课时'
								}, {
									field : 'outlineContent',
									title : '内容'
								}, {
									field : 'createTime',
									title : '新增时间',
									visible : false
								}, ]
							});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				departmentname : $("input[placeholder=搜索]").val(),
				courseId : $("#txt_search_id").val(),
				outlineName : $("#txt_search_name").val()
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
	function addOutline() {
		if (!validateForm($("#addForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/education/outline/addOutline";
		$.post(url, {
			outlineName : $("#myModal #outlineName").val(),
			hour : $("#myModal #hour").val(),
			outlineContent : $("#myModal #outlineContent").val(),
			courseId : $("#myModal #courseId").val(),
		}, addOutlineHandle, "text");

		//清空新增div中的数据
		$("#myModal #outlineName").val('');
		$("#myModal #hour").val('');
		$("#myModal #outlineContent").val('');
		$("#myModal #courseId").val('');
		$("#myModal").modal('hide');
	}
	function addOutlineHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
			$('#tb_departments').bootstrapTable('refresh');
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}

	//update department ，ajax提交
	function updateOutline() {
		if (!validateForm($("#addUpdateForm"))) {
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/education/outline/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#updateModal #id").val(),
			outlineName : $("#updateModal #outlineName").val(),
			hour : $("#updateModal #hour").val(),
			outlineContent : $("#updateModal #outlineContent").val(),
			courseId : $("#updateModal #courseId").val(),
			createTime : $("#updateModal #createTime").val(),
		}, addUpdateHandle, "text");

		//清空新增div中的数据
		$("#updateModal #outlineName").val('');
		$("#updateModal #hour").val('');
		$("#updateModal #outlineContent").val('');
		$("#updateModal #courseId").val('');
		$("#updateModal").modal('hide');
	}
	function addUpdateHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "Update is seccussed！",
				type : "success"
			});
			$('#tb_departments').bootstrapTable('refresh');
		} else {
			swal("修改错误！", "请检查你输入的数据！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}

	//delect Dept 
	//删除学生，ajax提交
	function deleteOutline() {
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/education/outline/" + id;
		//alert(url);
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : rows.id
		//从选中的rows中获取id
		}, deleteOutlineHandle, "text");
	}

	function deleteOutlineHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data == 1) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else if (data == 2) {
			swal("删除失败！", "这个系下面有专业，不能删除！", "error");
		} else {
			swal("删除失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
	}
</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="panel panel-default">
			<div class="panel-heading">查询条件</div>
			<div class="panel-body">
				<form id="formSearch" class="form-horizontal">
					<div class="form-group" style="margin-top: 15px">
						<label class="control-label col-sm-1" for="txt_search_id">课程名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="txt_search_id">
							      
						</div>
						<label class="control-label col-sm-1" for="txt_search_name">大纲名称</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="txt_search_name">
						</div>
						<div class="col-sm-3">
							<button type="button" id="btn_query" class="btn btn-primary">查询</button>
							<button type="button" class="btn btn-success" id="btn_clear">清除</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>

		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments"></table>
		 
	</div>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增</h4>
				</div>
				<div class="modal-body">
					<!-- 新增系别 -->
					<form id="addForm" class="form-horizontal m-t">
					<div class="form-group">
							<label for="courseId" class="control-label col-sm-3">选择课程</label>
							<div class="col-sm-8">
								<select name="courseId" id="courseId" class="form-control">
									<c:forEach items="${courseList}" var="list">
										<option value="${list.id}">${list.courseName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="outlineName" class="control-label col-sm-3">大纲名称</label>
							<div class="col-sm-8">
								<input type="text" name="outlineName" class="form-control"
									id="outlineName">
							</div>
						</div>
						<div class="form-group">
							<label for="hour" class="control-label col-sm-3">计划课时</label>
							<div class="col-sm-8">
								<input type="text" name="hour" class="form-control" id="hour">
							</div>
						</div>
						<div class="form-group">
							<label for="outlineContent" class="control-label col-sm-3">说明</label>
							<div class="col-sm-8">
								<textarea name="outlineContent" rows="3" class="form-control"
									id="outlineContent">
            					</textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
					<button type="button" class="btn btn-primary"
						onclick="addOutline()">
						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
					</button>
				</div>
			</div>
		</div>
	</div>

	<!-- update dialog -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改</h4>
				</div>
				<div class="modal-body">
					<!-- Hidden -->
					<input type="hidden" name="id" id="id"> <input
						type="hidden" name="createTime" id="createTime">
					<!-- 新增系别 -->
					<form id="addUpdateForm" class="form-horizontal m-t">
						<div class="form-group">
							<label for="courseId" class="control-label col-sm-3">选择课程</label>
							<div class="col-sm-8">
								<select name="courseId" id="courseId" class="form-control">
									<c:forEach items="${courseList}" var="list">
										<option value="${list.id}">${list.courseName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="outlineName" class="control-label col-sm-3">大纲名称</label>
							<div class="col-sm-8">
								<input type="text" name="outlineName" class="form-control"
									id="outlineName">
							</div>
						</div>
						<div class="form-group">
							<label for="hour" class="control-label col-sm-3">计划课时</label>
							<div class="col-sm-8">
								<input type="text" name="hour" class="form-control" id="hour">
							</div>
						</div>
						<div class="form-group">
							<label for="outlineContent" class="control-label col-sm-3">说明</label>
							<div class="col-sm-8">
								<textarea name="outlineContent" rows="3" class="form-control"
									id="outlineContent">
            					</textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
					<button type="button" class="btn btn-primary"
						onclick="updateOutline()">
						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
					</button>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script type="text/javascript">
		//表单兼容性插件，可忽略
		$.validator.setDefaults({
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success')
						.addClass('has-error');
			},
			success : function(element) {
				element.closest('.form-group').removeClass('has-error')
						.addClass('has-success');
			},
			errorElement : "span",
			errorPlacement : function(error, element) {
				if (element.is(":radio") || element.is(":checkbox")) {
					error.appendTo(element.parent().parent().parent());
				} else {
					error.appendTo(element.parent());
				}
			},
			errorClass : "help-block m-b-none",
			validClass : "help-block m-b-none"
		});
		// 字符验证       
		$.validator.addMethod("isChinese", function(value, element) {
			return this.optional(element)
					|| /^([A-Za-z0-9]|[\u4E00-\u9FA5])+$/.test(value);
		}, "只能包括中文字");
		//选择验证
		$.validator.addMethod("isSelect", function(value, element) {
			return value != "0";
		}, "必须选择一项");
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		function validateForm(formdata) {
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
			var validator = formdata.validate({
				rules : {
					outlineName : {
						required : true,
						rangelength : [ 2, 15 ],
						isChinese : true,
					},
					courseId : {
						isSelect : true,
					},
					hour : {
						digits : true,
						required : true
					}
				},
				messages : {
					outlineName : {
						required : icon + "请输入大纲名称！",
						rangelength : icon + "大纲名称必须在2-15字符之间！",
						isChinese : "大纲名称只能是字母，汉字，不能是数字和特殊字符",
					},
					courseId : {
						isSelect : icon + "必须选择一项！",
					},
					hour : {
						required : icon + "请输入计划课时！",
						digits : icon + "请输入数字",
					}
				},
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
</body>
</html>