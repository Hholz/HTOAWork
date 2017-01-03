<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script>
	var salRows = null;
	$(function() {
		//激活提示
		$("[data-toggle='tooltip']").tooltip();

		$("#btn_saltype_add").click(function() {
			//清空新增div中的数据
			$("#window_saltype #id").val('');
			$("#window_saltype #name").val('');
			$("#window_saltype #salary").val('');
			$("#window_saltype input[type='radio']").attr("checked", false);
			$("#btn_sal_save").attr("onclick", "addSaltype()");
		});
		//修改按钮事件
		$("#btn_saltype_update").click(
				function() {
					if (salRows == null) {
						parent.layer.alert('请选你要修改的数据！');
						return;
					}
					//把内容放到更新的列表
					$("#window_saltype #id").val(salRows.id);
					$("#window_saltype #name").val(salRows.name);
					$("#window_saltype #salary").val(salRows.salary);
					$(
							"#window_saltype input[type=radio][name=typeStatus][value="
									+ salRows.typeStatus + "]").attr("checked",
							true);

					$('#window_saltype').modal('show');
					$("#btn_sal_save").attr("onclick", "updateSaltype()");
				});
		//修改按钮事件
		$("#btn_saltype_delete").click(function() {
			if (salRows == null) {
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			parent.layer.confirm('您确定删除该信息吗？', {
				btn : [ '是的', '取消' ], //按钮
				shade : false
			//不显示遮罩
			}, function() {
				deleteSaltype();
				//parent.layer.msg('正在删除');
			}, function() {

			});

		});

		//表格数据
		$('#tb_saltype')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/sysSet/salType/salTypeListJson', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							toolbar : '#toolbar_saltype', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							queryParams : queryParams,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageSize : 10, //每页的记录行数（*）
							pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
							clickToSelect : true, //是否启用点击选中行
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							singleSelect : true, //设置为单选
							onCheck : function(row) {
								//$element是当前tr的jquery对象
								if (salRows != null) {
									salRows = null;
								}
								salRows = row;
							},
							onUncheck : function(row) {
								if (salRows != null) {
									salRows = null;
								}
							},
							columns : [
									{
										checkbox : true,
									},
									{
										field : 'id',
										title : '编号'
									},
									{
										field : 'name',
										title : '工资类别'
									},
									{
										field : 'salary',
										title : '工资'
									},
									{
										field : 'typeStatus',
										title : '状态',
										align : 'center',
										formatter : function(value, row, index) {
											var status = row.typeStatus;
											if (status == 1) {
												text = "<span class='label label-primary'>启用</span>";
											} else if (status == 0) {
												text = "<span class='badge badge-danger'>禁用</span>";
											}
											return text;
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
	//新增学生状态，ajax提交
	function addSaltype() {
		if (!validateForm($("#salForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/sysSet/salType/addSalType";
		$.post(url, {
			name : $("#window_saltype #name").val(),
			salary : $("#window_saltype #salary").val(),
			typeStatus : $("#window_saltype input[name=typeStatus]:checked")
					.val()
		}, addSaltypeHandle, "text");
		//用来关闭新增窗口***********
		$('#window_saltype').modal('hide');
	}
	function addSaltypeHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('添加成功！');
		} else {
			parent.layer.alert('添加失败！');
		}
		//刷新数据
		$('#tb_saltype').bootstrapTable('refresh');
	}

	//新增学生状态，ajax提交
	function updateSaltype() {
		if (!validateForm($("#salForm"))) {
			return;
		}
		var id = salRows.id;
		var url = "${pageContext.request.contextPath }/sysSet/salType/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#window_saltype #id").val(),
			name : $("#window_saltype #name").val(),
			salary : $("#window_saltype #salary").val(),
			typeStatus : $("#window_saltype input[name=typeStatus]:checked")
					.val()
		}, updateSaltypeHandle, "text");
		$('#window_saltype').modal('hide');
	}
	function updateSaltypeHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('修改成功！');
		} else {
			parent.layer.alert('修改失败！');
		}
		//刷新表格
		$('#tb_saltype').bootstrapTable('refresh');
	}

	function deleteSaltype() {
		var id = salRows.id;
		var url = "${pageContext.request.contextPath }/sysSet/salType/" + id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : salRows.id
		//从选中的rows中获取id
		}, deleteSaltypeHandle, "text");
	}
	function deleteSaltypeHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			parent.layer.alert('删除成功！');
		} else {
			parent.layer.alert('删除失败！');
		}
		//刷新表格
		$('#tb_saltype').bootstrapTable('refresh');
	}
</script>
</head>
<body>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>员工相关设置</h5>
					<div class="ibox-tools">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="dropdown-toggle" data-toggle="dropdown"
							href="tabs_panels.html#"> <i class="fa fa-wrench"></i>
						</a>
						<ul class="dropdown-menu dropdown-user">
							<li><a href="tabs_panels.html#">选项1</a></li>
							<li><a href="tabs_panels.html#">选项2</a></li>
						</ul>
						<a class="close-link"> <i class="fa fa-times"></i>
						</a>
					</div>
				</div>
				<div class="ibox-content">
					<p>这里是所有员工相关设置信息</p>
				</div>
			</div>
		</div>
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>工资类别</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="dropdown-toggle" data-toggle="dropdown"
						href="tabs_panels.html#"> <i class="fa fa-wrench"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
					</ul>
					<a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<div id="toolbar_saltype" class="btn-group">
					<button id="btn_saltype_add" type="button"
						class="btn btn-w-m btn-primary" data-toggle="modal"
						data-target="#window_saltype">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					</button>
					<button id="btn_saltype_update" type="button"
						class="btn btn-w-m btn-success">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
					</button>
					<button id="btn_saltype_delete" type="button"
						class="btn btn-w-m btn-danger">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>
				</div>
				<!-- table代码就这些，用js构建表格 -->
			<table id="tb_saltype"></table>
			</div>
		</div>
	</div>
	<!--修改的弹窗  -->
	<div class="modal inmodal" id="window_saltype" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">编辑</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="salForm"
						novalidate="novalidate">
						<input id="id" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">类别名称：</label>
							<div class="col-sm-8">
								<input id="name" name="name" type="text" required
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">金额：</label>
							<div class="col-sm-8">
								<input id="salary" name="salary" type="text" required
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">是否启用：</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									name="typeStatus" value="1">&nbsp;&nbsp;&nbsp;启用</label> <label
									class="control-label" style="padding-left: 20px"><input
									type="radio" name="typeStatus" value="0">&nbsp;&nbsp;禁用</label>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" id="btn_sal_save" onclick=""
								class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
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
					name : "required",
					salary : {
						required : true,
						digits : true
					}
				},
				messages : {
					name : icon + "请输入工资类别",
					salary : {
						required : icon + "请输入数目",
						digits : icon + "数目必须是数字"
					}
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
</body>
</html>