<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<title>员工基本工资设置</title>
<script type="text/javascript">
	//全局变量，用来保存选中行的数据
	var lows = null;
	var num = 0;//用来判断一个班还可以新增几个人
	//全局变量，用来保存选中行的数据
	$(function() {
		$('#btn_changeStudent').click(function() {
			//刷新数据
			$('#tb_salType').bootstrapTable('refresh');
		});
		$('#tb_salType')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/finance/FinanceSalarySet/salTypeList', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							//toolbar : '#toolbar_stu', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							sortable : false, //是否启用排序
							sortOrder : "asc", //排序方式
							//queryParams : queryParams2,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 5, //每页的记录行数（*）
							pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
							minimumCountColumns : 2, //最少允许的列数
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							columns : [ {
								checkbox : true
							}, {
								field : 'id',
								title : 'ID'
							}, {
								field : 'name',
								title : '工资类别'
							}, {
								field : 'salary',
								title : '金额'
							}, {
								field : '操作',
								title : '操作',
								align : 'center',
							}, ]
						});
	});
	//得到查询的参数
	function queryParams2(params) {

		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			//***这里的参数传到后台，用来进行分页处理*************************
			offset : params.offset, //页码
			stuname : $("#div_stu input[placeholder=搜索]").val(),
			limit : function() {
				if (lows != null) {
					return lows.count - lows.countstu;
				} else {
					return 1;
				}
			}
		};
		return temp;
	};

	function findSalType() {
		$('#tb_student').bootstrapTable('refresh');
		$('#window_add').modal('show');
	}
	//新增学生，ajax提交
	function addSalarySet() {
		var ids = null;
		var names = null;
		var salarys = null;
		var dep = $("#window_add #depid").val();
		var emp = $("#window_add #empid").val();
		var list = $('#tb_salType').bootstrapTable("getSelections");
		for (var i = 0; i < list.length; i++) {
			if (i != list.length - 1) {
				ids = ids + list[i].id + ",";
				names = names + list[i].name + ",";
				salarys = salarys + list[i].salary + ",";
			} else {
				ids = ids + list[i].id;
				names = names + list[i].name;
				salarys = salarys + list[i].salary;
			}
		}
		if (ids == list.length - 1 || ids == null) {
			parent.layer.alert('请先选择工资类别！');
			return;
		}

		if (i != list.length - 1) {
			parent.layer.alert('你设置的工资来别包括：' + names);
		}
		var url = "${pageContext.request.contextPath }/finance/FinanceSalarySet/add";
		$.post(url, {
			_method : 'POST',//改成PUT提交
			depid : dep,
			empid : emp,
			ids : ids,
			salarys : salarys
		}, addHandle, "text");
	}
	function addHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
			$('#tb_departments').bootstrapTable('refresh');
		} else {
			swal("添加失败", "此数据已添加！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}
	function findEmp() {
		var depid = $("#formSearch #depid").val();
		var url = "${pageContext.request.contextPath }/finance/FinanceSalarySet/findEmp/"
				+ depid;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
		}, InitEmp, "text");
	}
	function InitEmp(data) {
		var opt = '<option value="">选择员工</option>';
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			for (var i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].empname+'">'
						+ datas.rows[i].empname + '</option>';
			}
		}
		$('#formSearch #empid').html(opt);
	}
	function selectEmp() {
		var depid = $("#window_add #depid").val();
		var url = "${pageContext.request.contextPath }/finance/FinanceSalarySet/findEmp/"
				+ depid;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
		}, initSelect, "text");
	}
	function initSelect(data) {
		var opt = '<option value="">选择员工</option>';
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			for (var i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].empname+'">'
						+ datas.rows[i].empname + '</option>';
			}
		}
		$('#window_add #empid ').html(opt);
	}
</script>
<script type="text/javascript">
	//全局变量，用来保存选中行的数据
	var rows = null;
	$(function() {

		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();

		//修改按钮事件
		/*$("#btn_edit").click(function() {
			if (rows == null) {
				parent.layer.alert('请选你要修改的数据！');
				return;
			}
			//把内容放到更新的列表
			$("#save").attr("onclick", "updateStudent()");
		});*/
		$("#clear").click(function() {
			$("#formSearch #empid").val('');
			$("#formSearch #depid").val('0');
		});
		//新增按钮事件*************************
		$("#query").click(function() {
			$("#tb_departments").bootstrapTable('refresh');
		});
		//删除按钮事件
		//*************************************************************************按钮事件
		$('#btn_delete').click(function() {
			if (rows == null) {
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
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
					deleteSal();
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
			$('#tb_departments')
					.bootstrapTable(
							{
								url : '${pageContext.request.contextPath}/finance/FinanceSalarySet/findList', //请求后台的URL（*）
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
								pageSize : 6, //每页的记录行数（*）
								pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
								search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
								strictSearch : false,
								showColumns : true, //是否显示所有的列
								showRefresh : true, //是否显示刷新按钮
								minimumCountColumns : 2, //最少允许的列数
								clickToSelect : true, //是否启用点击选中行
								//height : 400, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
								uniqueId : "id", //每一行的唯一标识，一般为主键列
								showToggle : true, //是否显示详细视图和列表视图的切换按钮
								cardView : false, //是否显示详细视图
								detailView : true, //是否显示父子表
								singleSelect : true, //设置为单选
								onCheck : function(row) {
									//$element是当前tr的jquery对象
									if (rows != null) {
										rows = null;
									}
									rows = row;
								},
								onUncheck : function(row) {
									if (rows != null) {
										rows = null;
									}
								},
								columns : [ {
									field : 'id',
									title : '序号'
								}, {
									field : 'empname',
									title : '员工姓名'
								}, {
									field : 'dep.depname',
									title : '部门'
								}, {
									field : 'basicsalary',
									title : '基本工资',
									align : 'center',
								}, ],
								//注册加载子表的事件。注意下这里的三个参数！
								onExpandRow : function(index, row, $detail) {
									oTableInit
											.InitSubTable(index, row, $detail);
								}
							});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一至，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码  
				depid : $("#formSearch #depid").val(),
				empname : $("#formSearch #empid").val()
			};
			return temp;
		};

		//初始化子表格(无线循环)
		oTableInit.InitSubTable = function InitSubTable(index, row, $detail) {
			var id = row.id;//学期
			var cur_table = $detail.html('<table></table>').find('table');
			$(cur_table)
					.bootstrapTable(
							{
								//classes:'table-no-bordered',
								url : '${pageContext.request.contextPath}/finance/FinanceSalarySet/detailList/'
										+ id, //请求后台的URL（*）
								method : 'post', //请求方式（*）
								contentType : "application/x-www-form-urlencoded",
								cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
								pagination : true, //是否显示分页（*）
								queryParams : queryParams,//传递参数（*）
								sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
								pageSize : 10, //每页的记录行数（*）
								pageList : [ 5, 10, 15, 20, 30 ], //可供选择的每页的行数（*）
								clickToSelect : true, //是否启用点击选中行
								uniqueId : "id", //每一行的唯一标识，一般为主键列
								columns : [ {
									field : 'id',
									title : '编号'
								}, {
									field : 'type.name',
									title : '类别'
								}, {
									field : 'money',
									title : '金额'
								},/* {
									field : '操作',
									title : '操作',
									align : 'center',
									formatter : function(value, row, index) {
										if(id != 0){
											text = "<span class='glyphicon glyphicon-pencil'><font color='blue'>修改</font></span>&nbsp;&nbsp;&nbsp;&nbsp;";
											text += "<span class='glyphicon glyphicon-remove'><font color='red'>删除</font></span>";
											return text;
										}
									}
								},*/ ]
							});
		};
		return oTableInit;
	}
	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
		};
		return temp;
	}
	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	};
	//********************************************************删除
	//删除学生，ajax提交
	function deleteSal() {
		var id = rows.id;
		if (id == 0 || id == null) {
			parent.layer.alert('请选择需要删除的数据');
			return;
		}
		var url = "${pageContext.request.contextPath }/finance/FinanceSalarySet/delete/"
				+ id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			empid : rows.empid,
		}, deleteStudentHandle, "text");
	}
	function deleteStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else {
			swal("删除失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
	}
</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px; height: 80px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>查询条件</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">部门：</label>
								<div class="col-sm-2">
									<select id="depid" class="form-control"
										onchange="findEmp();">
										<option value="0">选择部门</option>
										<c:forEach items="${deplist}" var="dep">
											<option value="${dep.id}">${dep.depname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">员工：</label>
								<div class="col-sm-2">
									<select id="empid" class="form-control">
										<option value="">选择员工</option>
										<c:forEach items="${emplist}" var="emp">
											<option value="${emp.empname}">${emp.empname}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4">
									<button type="button" class="btn btn-primary" id="query">查询</button>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-primary" id="clear">清空</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" onclick="findSalType()" type="button"
				class="btn btn-w-m btn-primary" data-toggle="modal">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button><!-- 
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success"
				data-toggle="modal">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button> -->
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>

		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments" class="table">
		</table>
	</div>

	<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<h4 class="modal-title">员工基本工资设置</h4>
				</div>
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<form class="form-horizontal m-t" id="addForm"
								novalidate="novalidate">
								<div class="form-group">
									<label class="col-sm-2 control-label">部门：</label>
									<div class="col-sm-3">
										<select id="depid" class="form-control"
											onchange="selectEmp();">
											<option value="0">选择部门</option>
											<c:forEach items="${deplist}" var="dep">
												<option value="${dep.id}">${dep.depname}</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-2 control-label">员工：</label>
									<div class="col-sm-3">
										<select id="empid" class="form-control">
											<option value="">选择员工</option>
											<c:forEach items="${emplist}" var="emp">
												<option value="${emp.empname}">${emp.empname}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</form>
						</div>
						<div class="col-sm-12">
							<table id="tb_salType"></table>
						</div>
						<div class="col-sm-12">
							<div class="modal-footer">
								<button type="button" id="btn_changeStudent"
									class="btn btn-success">刷新</button>
								<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
								<button type="button" onclick="addSalarySet();"
									class="btn btn-primary" data-dismiss="modal">确认</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script type="text/javascript">
		//已经把文档下到本地，访问地址：http://localhost:8080/HTOAWork/Demo/validateDemo/jQueryValidate.html
		//详情参考：http://www.runoob.com/jquery/jquery-plugin-validate.html
		//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
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
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {

				},
				messages : {
					depid : {
						required : icon + "请先选择部门"
					},
					empid : {
						required : icon + "请选择部门"
					},
				},
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
</body>
</html>