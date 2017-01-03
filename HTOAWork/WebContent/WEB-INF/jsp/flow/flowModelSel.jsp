<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
	var srmRows = null;
	var srmRowsType = null;
	var srmRowsModel = null;
	var srmRole = null;
	function test(id, flowType, obj) {
		$("#window_flowModelType #modelselid").val(id);
		$("#window_flowModelType #modelselname").val(obj);
		$("#window_flowModel #flowType").val(flowType);
		//让模板明细表刷新
		$('#tb_flowModelType').bootstrapTable('refresh');
	}

	function test1(id, obj) {
		var flowType = $("#window_flowModel #flowType").val();
		if (flowType == 1) {
			$("#window_flowModel #flowmodeltypeid").val(id);
			$("#window_flowModel #flowmodeltype").val(obj);
			tt = document.getElementById("flowModeldiv2");
			tt.style.display = "none";
			t = document.getElementById("flowModeldiv");
			t.style.display = "block";
			//让模板明细表刷新
			$('#tb_flowModel').bootstrapTable('refresh');
		} else {
			$("#window_flowModel2 #flowmodeltypeid").val(id);
			$("#window_flowModel2 #flowmodeltype").val(obj);
			tt = document.getElementById("flowModeldiv2");
			tt.style.display = "block";
			t = document.getElementById("flowModeldiv");
			t.style.display = "none";
			//让模板明细表刷新
			$('#tb_flowModel2').bootstrapTable('refresh');
		}
	}

	$(function() {
		//-----------------------------模板字典------------------------
		//新增按钮事件*************************
		$("#btn_flowModelSel_add").click(function() {
			//清空新增div中的数据
			$("#window_flowModelSel #id").val('');
			$("#window_flowModelSel #modelname").val('');
			$("#window_flowModelSel #modelselname").val('');
			$("#window_flowModelSel #remark").val('');
			$("#window_flowModelSel #invalid").attr("disabled", true);
			$("#window_flowModelSel #flowType").attr("disabled", false);
			$("#window_flowModelSel #modelselname").attr("readonly", false);
			$("#btn_modelSel_save").attr("onclick", "addModelSel()");
		});
		//修改按钮事件
		$("#btn_flowModelSel_update")
				.click(
						function() {
							if (srmRows == null) {
								parent.layer.alert('请选你要修改的数据！');
								return;
							}
							//把内容放到更新的列表
							$("#window_flowModelSel #id").val(srmRows.id);
							$("#window_flowModelSel #modelname").val(
									srmRows.modelname);
							$("#window_flowModelSel #modelselname").val(
									srmRows.modelselname);
							$("#window_flowModelSel #remark").val(
									srmRows.remark);
							$(
									"#window_flowModelSel input[type=radio][name=flowType][value="
											+ srmRows.flowType + "]").attr(
									"checked", 'checked');
							$(
									"#window_flowModelSel input[type=radio][name=invalid][value="
											+ srmRows.invalid + "]").attr(
									"checked", 'checked');
							$("#window_flowModelSel #invalid").attr("disabled",
									false);
							$("#window_flowModelSel #flowType").attr(
									"disabled", true);
							$("#window_flowModelSel #modelselname").attr(
									"readonly", true);
							$('#window_flowModelSel').modal('show');
							$("#btn_modelSel_save").attr("onclick",
									"updateModelSel()");
						});

		//表格数据
		$('#tb_flowModelSel')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/flow/flowModelSel/flowModelSelJson', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							toolbar : '#toolbar_flowModelSel', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							queryParams : queryParams,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 5, //每页的记录行数（*）
							pageList : [ 10, 15, 20, 25 ], //可供选择的每页的行数（*）
							clickToSelect : true, //是否启用点击选中行
							showRefresh : true, //是否显示刷新按钮
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							showToggle : true, //是否显示详细视图和列表视图的切换按钮
							singleSelect : true, //设置为单选
							onCheck : function(row, $element) {
								//$element是当前tr的jquery对象
								if (srmRows != null) {
									srmRows = null;
								}
								srmRows = row;
							},//单击row事件
							onUncheck : function(row) {
								srmRows = null;
							},
							columns : [
									{
										checkbox : true,
									},
									{
										field : 'modelname',
										title : '模板类型',
									},
									{
										field : 'modelselname',
										title : '编码'
									},
									/* {
										field : 'flowType',
										title : '审批模式',
										formatter : function(value, row, index) {
											var flowType = row.flowType;
											if (flowType == 1) {
												return "选人模式";
											} else {
												return "角色模式";
											}
										}
									}, */
									{
										field : 'invalid',
										title : '状态',
										formatter : function(value, row, index) {
											var invalid = row.invalid;
											if (invalid == 0) {
												return "无效";
											} else {
												return "有效";
											}
										}
									},
									{
										field : 'id',
										title : '子模板',
										align : 'center',
										formatter : function(value, row, index) {
											var str;
											if (row.invalid == 0) {
												str = "";
											} else {
												str = "<a onclick=test("
														+ row.id
														+ ","
														+ row.flowType
														+ ",'"
														+ row.modelselname
														+ "')><i class='fa fa-file-text-o'>查看模板</i></a>";
											}
											return str;
										}
									}, ]

						});

		//-------------------------------模板类型-----------------------------

		$("#btn_flowModelType_add").click(function() {
			//清空新增div中的数据
			var modelselName = $("#window_flowModelType #modelselname").val();
			var modelselid = $("#window_flowModelType #modelselid").val();
			if (modelselName == null || modelselid == "") {
				parent.layer.alert("请先选择模板字典再新增");
			} else {
				$("#window_flowModelType #flowmodeltype").val('');
				$("#window_flowModelType #invalid").attr("disabled", true);
				$("#window_flowModelType #remark").val('');
				$('#window_flowModelType').modal('show');
				$("#btn_modelType_save").attr("onclick", "addModelType()");
			}
		});
		//修改按钮事件
		$("#btn_flowModelType_update")
				.click(
						function() {
							var modelselName = $(
									"#window_flowModelType #modelselname")
									.val();
							var modelselid = $(
									"#window_flowModelType #modelselid").val();
							if (modelselName == null || modelselid == "") {
								parent.layer.alert("请先选择模板字典再新增");
								return;
							}
							if (srmRowsType == null) {
								parent.layer.alert('请选你要修改的数据！');
								return;
							}
							//把内容放到更新的列表
							$("#window_flowModelType #id").val(srmRowsType.id);
							$("#window_flowModelType #flowmodeltype").val(
									srmRowsType.flowmodeltype);
							$(
									"#window_flowModelType input[type=radio][name=invalid][value="
											+ srmRowsType.invalid + "]").attr(
									"checked", 'checked');
							$("#window_flowModelType #invalid").attr(
									"disabled", false);
							$("#window_flowModelType #remark").val(
									srmRowsType.remark);
							//调用静态窗口
							$('#window_flowModelType').modal('show');
							$("#btn_modelType_save").attr("onclick",
									"updateModelType()");

						});
		$("#btn_flowModelType_delete").click(function() {
			if (srmRowsType == null) {
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			parent.layer.confirm('您确定删除该信息吗？', {
				btn : [ '是的', '取消' ], //按钮
				shade : false
			//不显示遮罩
			}, function() {
				deleteModelType();
			}, function() {

			});

		});

		//表格数据
		$('#tb_flowModelType')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/flow/flowModelSel/flowModelTypeJson', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							toolbar : '#toolbar_flowModelType', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							queryParams : queryParams1,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 5, //每页的记录行数（*）
							pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
							clickToSelect : true, //是否启用点击选中行
							showRefresh : true, //是否显示刷新按钮
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							showToggle : true, //是否显示详细视图和列表视图的切换按钮
							singleSelect : true, //设置为单选
							onCheck : function(row, $element) {
								//$element是当前tr的jquery对象
								if (srmRowsType != null) {
									srmRowsType = null;
								}
								srmRowsType = row;
							},//单击row事件
							onUncheck : function(row) {
								srmRowsType = null;
							},
							columns : [
									{
										checkbox : true,
									},
									{
										field : 'modelselid',
										title : '流程编码',
										formatter : function(value, row, index) {
											var flowModelsel = row.flowModelsel;
											if (flowModelsel == null) {
												return "-";
											} else {
												return flowModelsel.modelselname;
											}
										}
									},
									{
										field : 'flowmodeltype',
										title : '流程模板名称',
									},
									{
										field : 'invalid',
										title : '状态',
										formatter : function(value, row, index) {
											var invalid = row.invalid;
											if (invalid == 0) {
												return "无效";
											} else {
												return "有效";
											}
										}
									},
									{
										field : 'id',
										title : '模板审批节点',
										align : 'center',
										formatter : function(value, row, index) {
											var str;
											if (row.invalid == 0) {
												str = "";
											} else {
												str = "<a onclick=test1("
														+ row.id
														+ ",'"
														+ row.flowmodeltype
														+ "')><i class='fa fa-file-text-o'>查看审批节点</i></a>";
											}
											return str;
										}
									}, ]

						});

		//-----------------------------审批节点-----------------------
		$("#btn_flowModel_add").click(
				function() {
					//清空新增div中的数据
					var flowmodeltype = $("#window_flowModel #flowmodeltype")
							.val();
					var flowmodeltypeid = $(
							"#window_flowModel #flowmodeltypeid").val();
					if (flowmodeltype == null || flowmodeltypeid == "") {
						parent.layer.alert("请先选择模板类型");
					} else {
						$("#window_flowModel #flowmodelname").val('');
						$("#window_flowModel #empid").val('');
						$("#window_flowModel #remark").val('');
						$("#window_flowModel #planday").val('');
						$("#window_flowModel #seq").val('');
						$("#window_flowModel #invalid").attr("disabled", true);
						$('#window_flowModel').modal('show');
						$("#btn_model_save").attr("onclick", "addModel()");
					}
				});
		//修改按钮事件
		$("#btn_flowModel_update")
				.click(
						function() {
							var flowmodeltype = $(
									"#window_flowModel #flowmodeltype").val();
							var flowmodeltypeid = $(
									"#window_flowModel #flowmodeltypeid").val();
							if (flowmodeltype == null || flowmodeltypeid == "") {
								parent.layer.alert("请先选择模板类型");
								return;
							}
							if (srmRowsType == null) {
								parent.layer.alert('请选你要修改的数据！');
								return;
							}
							//把内容放到更新的列表
							$("#window_flowModel #id").val(srmRowsModel.id);
							$("#window_flowModel #flowmodelname").val(
									srmRowsModel.flowmodelname);
							$("#window_flowModel #empid").val(
									srmRowsModel.emp.empname);
							$("#window_flowModel #planday").val(
									srmRowsModel.planday);
							$("#window_flowModel #seq").val(srmRowsModel.seq);
							$(
									"#window_flowModel input[type=radio][name=invalid][value="
											+ srmRowsModel.invalid + "]").attr(
									"checked", 'checked');
							$("#window_flowModel #invalid").attr("disabled",
									false);
							$("#window_flowModel #remark").val(
									srmRowsModel.remark);
							//调用静态窗口
							$('#window_flowModel').modal('show');
							$("#btn_model_save").attr("onclick",
									"updateModel()");

						});
		$("#btn_flowModel_delete").click(function() {
			if (srmRowsModel == null) {
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			parent.layer.confirm('您确定删除该信息吗？', {
				btn : [ '是的', '取消' ], //按钮
				shade : false
			//不显示遮罩
			}, function() {
				deleteFlowModel();
			}, function() {

			});

		});

		//表格数据
		$('#tb_flowModel')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/flow/flowModelSel/flowModelJson', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							toolbar : '#toolbar_flowModel', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							queryParams : queryParams2,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 5, //每页的记录行数（*）
							pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
							clickToSelect : true, //是否启用点击选中行
							showRefresh : true, //是否显示刷新按钮
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							showToggle : true, //是否显示详细视图和列表视图的切换按钮
							singleSelect : true, //设置为单选
							onCheck : function(row, $element) {
								//$element是当前tr的jquery对象
								if (srmRowsModel != null) {
									srmRowsModel = null;
								}
								srmRowsModel = row;
							},//单击row事件
							onUncheck : function(row) {
								srmRowsModel = null;
							},
							columns : [ {
								checkbox : true,
							}, {
								field : 'flowmodeltypeid',
								title : '流程类型',
								formatter : function(value, row, index) {
									var modelType = row.modelType;
									if (modelType == null) {
										return "-";
									} else {
										return modelType.flowmodeltype;
									}
								}
							}, {
								field : 'flowmodelname',
								title : '审批节点名称',
							}, {
								field : 'planday',
								title : '计划完成时间',
							}, {
								field : 'empid',
								title : '审批人',
								formatter : function(value, row, index) {
									var emp = row.emp;
									if (emp == null) {
										return "-";
									} else {
										return emp.empname;
									}
								}
							}, {
								field : 'invalid',
								title : '状态',
								formatter : function(value, row, index) {
									var invalid = row.invalid;
									if (invalid == 0) {
										return "无效";
									} else {
										return "有效";
									}
								}
							}, {
								field : 'seq',
								title : '节点步骤',
							}, ]
						});

		//-----------------------------审批节点-----------------------
		$("#btn_flowModel2_add")
				.click(
						function() {
							//清空新增div中的数据
							var flowmodeltype = $(
									"#window_flowModel2 #flowmodeltype").val();
							var flowmodeltypeid = $(
									"#window_flowModel2 #flowmodeltypeid")
									.val();
							if (flowmodeltype == null || flowmodeltypeid == "") {
								parent.layer.alert("请先选择模板类型");
							} else {
								$("#window_flowModel2 #flowmodelname").val('');
								$("#window_flowModel2 #roleid").val('');
								$("#window_flowModel2 #depid").val('');
								$("#window_flowModel2 #remark").val('');
								$("#window_flowModel2 #planday").val('');
								$("#window_flowModel2 #seq").val('');
								$("#window_flowModel2 #invalid").attr(
										"disabled", true);
								$("#window_flowModel2 #roleid").attr(
										"disabled", false);
								$("#window_flowModel2 #depid").attr("disabled",
										false);
								$('#window_flowModel2').modal('show');
								$("#btn_model2_save").attr("onclick",
										"addModel2()");
							}
						});
		//修改按钮事件
		$("#btn_flowModel2_update").click(
				function() {
					var flowmodeltype = $("#window_flowModel2 #flowmodeltype")
							.val();
					var flowmodeltypeid = $(
							"#window_flowModel2 #flowmodeltypeid").val();
					if (flowmodeltype == null || flowmodeltypeid == "") {
						parent.layer.alert("请先选择模板类型");
						return;
					}
					if (srmRowsType == null) {
						parent.layer.alert('请选你要修改的数据！');
						return;
					}
					//把内容放到更新的列表
					$("#window_flowModel2 #id").val(srmRowsModel.id);
					$("#window_flowModel2 #flowmodelname").val(
							srmRowsModel.flowmodelname);
					$("#window_flowModel2 #roleid").val(srmRowsModel.roleid);
					$("#window_flowModel2 #depid").val(srmRowsModel.roleid);
					$("#window_flowModel2 #planday").val(srmRowsModel.planday);
					$("#window_flowModel2 #seq").val(srmRowsModel.seq);
					$(
							"#window_flowModel2 input[type=radio][name=invalid][value="
									+ srmRowsModel.invalid + "]").attr(
							"checked", 'checked');
					$("#window_flowModel2 #invalid").attr("disabled", false);
					$("#window_flowModel2 #remark").val(srmRowsModel.remark);
					//调用静态窗口
					$('#window_flowModel2').modal('show');
					$("#btn_model2_save").attr("onclick", "updateModel2()");

				});
		$("#btn_flowModel2_delete").click(function() {
			if (srmRowsModel == null) {
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			parent.layer.confirm('您确定删除该信息吗？', {
				btn : [ '是的', '取消' ], //按钮
				shade : false
			//不显示遮罩
			}, function() {
				deleteFlowModel2();
			}, function() {

			});

		});

		//表格数据
		$('#tb_flowModel2')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/flow/flowModelSel/flowModelJson2', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							toolbar : '#toolbar_flowModel2', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							queryParams : queryParams3,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 5, //每页的记录行数（*）
							pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
							clickToSelect : true, //是否启用点击选中行
							showRefresh : true, //是否显示刷新按钮
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							showToggle : true, //是否显示详细视图和列表视图的切换按钮
							singleSelect : true, //设置为单选
							onCheck : function(row, $element) {
								//$element是当前tr的jquery对象
								if (srmRowsModel != null) {
									srmRowsModel = null;
								}
								srmRowsModel = row;
							},//单击row事件
							onUncheck : function(row) {
								srmRowsModel = null;
							},
							columns : [ {
								checkbox : true,
							}, {
								field : 'flowmodeltypeid',
								title : '流程类型',
								formatter : function(value, row, index) {
									var modelType = row.modelType;
									if (modelType == null) {
										return "-";
									} else {
										return modelType.flowmodeltype;
									}
								}
							}, {
								field : 'flowmodelname',
								title : '审批节点名称',
							}, {
								field : 'planday',
								title : '计划完成时间',
							}, {
								field : 'roleid',
								title : '审批人',
								formatter : function(value, row, index) {
									var roleid = row.roleid;
									var role = row.dep;
									if (roleid == null) {
										return "-";
									} else if (roleid == 'rkls') {
										return "任课老师";
									} else if (roleid == 'bzr') {
										return "班主任";
									} else {
										return role.depname;
									}
								}
							}, {
								field : 'invalid',
								title : '状态',
								formatter : function(value, row, index) {
									var invalid = row.invalid;
									if (invalid == 0) {
										return "无效";
									} else {
										return "有效";
									}
								}
							}, {
								field : 'seq',
								title : '节点步骤',
							}, ]
						});

		//-----------------------流程审批角色-------------------------
		/* $("#btn_flowRole_add").click(function() {
			//清空新增div中的数据
			$("#window_flowRole #id").val('');
			$("#window_flowRole #rolename").val('');
			$("#window_flowRole #roleselname").val('');
			$("#window_flowRole #remark").val('');
			$("#window_flowRole #roleselname").attr(
					"readonly", false);
			$("#window_flowRole #invalid").attr("disabled", true);
			$("#btn_role_save").attr("onclick", "addModelRole()");
		}); */
		//修改按钮事件
		/* $("#btn_flowRole_update").click(
			function() {
				if (srmRole == null) {
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_flowRole #id").val(srmRole.id);
				$("#window_flowRole #rolename").val(srmRole.rolename);
				$("#window_flowRole #roleselname").val(srmRole.roleselname);
				$("#window_flowRole #remark").val(srmRole.remark);
				$("#window_flowRole input[type=radio][name=invalid][value="
						+ srmRole.invalid + "]").attr(
						"checked", 'checked');
				$("#window_flowRole #invalid").attr("disabled",false);
				$("#window_flowRole #roleselname").attr("readonly", true);
				$('#window_flowRole').modal('show');
				$("#btn_role_save").attr("onclick","updateModelRole()");
			}); */

		//表格数据
		$('#tb_flowRole')
				.bootstrapTable(
						{
							url : '${pageContext.request.contextPath}/flow/flowModelSel/flowRoleJsonList', //请求后台的URL（*）
							method : 'post', //请求方式（*）
							contentType : "application/x-www-form-urlencoded",
							toolbar : '#toolbar_flowRole', //工具按钮用哪个容器
							cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, //是否显示分页（*）
							queryParams : queryParams,//传递参数（*）
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 5, //每页的记录行数（*）
							pageList : [ 10, 15, 20, 25 ], //可供选择的每页的行数（*）
							clickToSelect : true, //是否启用点击选中行
							showRefresh : true, //是否显示刷新按钮
							uniqueId : "id", //每一行的唯一标识，一般为主键列
							showToggle : true, //是否显示详细视图和列表视图的切换按钮
							singleSelect : true, //设置为单选
							onCheck : function(row, $element) {
								//$element是当前tr的jquery对象
								if (srmRole != null) {
									srmRole = null;
								}
								srmRole = row;
							},//单击row事件
							onUncheck : function(row) {
								srmRole = null;
							},
							columns : [ {
								checkbox : true,
							}, {
								field : 'rolename',
								title : '审批人名称',
							}, {
								field : 'roleselname',
								title : '编码'
							}, {
								field : 'createTime',
								title : '创建时间'
							}, {
								field : 'updateTime',
								title : '修改时间'
							}, {
								field : 'invalid',
								title : '状态',
								formatter : function(value, row, index) {
									var invalid = row.invalid;
									if (invalid == 0) {
										return "无效";
									} else {
										return "有效";
									}
								}
							}, ]

						});
	});

	function addModelRole() {

		//用来判断表单是否验证通过
		if (!modelRoleForm($("#flowRoleForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/flowRoleAdd";
		$.post(url, {
			rolename : $("#window_flowRole #rolename").val(),
			roleselname : $("#window_flowRole #roleselname").val(),
			invalid : $('#window_flowRole input[name=invalid]:checked').val(),
			remark : $("#window_flowRole #remark").val()
		}, addSrmFlowRole, "text");
		//用来关闭新增窗口***********
		$("#window_flowRole").modal('hide')
	}

	function addSrmFlowRole(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('添加成功！');
		} else {
			parent.layer.alert('添加失败！');
		}
		//刷新数据
		$('#tb_flowRole').bootstrapTable('refresh');
	}

	function updateModelRole() {
		var id = srmRole.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/updateFlowRole/"
				+ id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#window_flowRole #id").val(),
			rolename : $("#window_flowRole #rolename").val(),
			roleselname : $("#window_flowRole #roleselname").val(),
			invalid : $('#window_flowRole input[name=invalid]:checked').val(),
			remark : $("#window_flowRole #remark").val()
		}, updateSrmdModelRole, "text");
		//用来关闭新增窗口***********
		$("#window_flowRole").modal('hide');
	}

	function updateSrmdModelRole(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('修改成功！');
		} else {
			parent.layer.alert('修改失败！');
		}
		//刷新表格
		$('#tb_flowRole').bootstrapTable('refresh');
	}

	function addModel2() {

		//用来判断表单是否验证通过
		if (!modelForm2($("#flowModelForm2"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/addFlowModel";
		var role = "";
		var roleid = $("#window_flowModel2 #roleid").val();
		var depid = $("#window_flowModel2 #depid").val();
		if (roleid == "" && depid != "") {
			role = depid;
		} else if (depid == "" && roleid != "") {
			role = roleid;
		} else {
			alert("审批人和审批部门必须选择一个");
			return;
		}
		$.post(url,
				{
					flowmodeltypeid : $("#window_flowModel2 #flowmodeltypeid")
							.val(),
					flowmodelname : $("#window_flowModel2 #flowmodelname")
							.val(),
					planday : $("#window_flowModel2 #planday").val(),
					roleid : role,
					seq : $("#window_flowModel2 #seq").val(),
					invalid : $(
							'#window_flowModel2 input[name=invalid]:checked')
							.val(),
					remark : $("#window_flowModel2 #remark").val()
				}, addSrmHandle2, "text");
		//用来关闭新增窗口***********
		$("#window_flowModel2").modal('hide')
	}

	function addSrmHandle2(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('添加成功！');
		} else {
			parent.layer.alert('添加失败！');
		}
		//刷新数据
		$('#tb_flowModel2').bootstrapTable('refresh');
	}

	function addModel() {

		//用来判断表单是否验证通过
		if (!modelForm($("#flowModelForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/addFlowModel";
		$.post(url, {
			flowmodeltypeid : $("#window_flowModel #flowmodeltypeid").val(),
			flowmodelname : $("#window_flowModel #flowmodelname").val(),
			planday : $("#window_flowModel #planday").val(),
			empid : $("#window_flowModel #empid").val(),
			seq : $("#window_flowModel #seq").val(),
			invalid : $('#window_flowModel input[name=invalid]:checked').val(),
			remark : $("#window_flowModel #remark").val()
		}, addSrmHandle, "text");
		//用来关闭新增窗口***********
		$("#window_flowModel").modal('hide')
	}

	function addSrmHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('添加成功！');
		} else {
			parent.layer.alert('添加失败！');
		}
		//刷新数据
		$('#tb_flowModel').bootstrapTable('refresh');
	}

	function addModelType() {

		//用来判断表单是否验证通过
		if (!modelTypeForm($("#flowModelTypeForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/addFlowModelType";
		$.post(url, {
			flowmodeltype : $("#window_flowModelType #flowmodeltype").val(),
			modelselid : $("#window_flowModelType #modelselid").val(),
			flowType : $("#window_flowModel #flowType").val(),
			invalid : $('#window_flowModelType input[name=invalid]:checked')
					.val(),
			remark : $("#window_flowModelType #remark").val()
		}, addSrmHandleType, "text");
		//用来关闭新增窗口***********
		$("#window_flowModelType").modal('hide')
	}

	function addSrmHandleType(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('添加成功！');
		} else {
			parent.layer.alert('添加失败！');
		}
		//刷新数据
		$('#tb_flowModelType').bootstrapTable('refresh');
	}

	function deleteFlowModel() {
		var id = srmRowsModel.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/deleteModel/"
				+ id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : id
		//从选中的rows中获取id
		}, deleteModelHandle, "text");
	}

	function deleteFlowModel2() {
		var id = srmRowsModel.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/deleteModel/"
				+ id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : id
		//从选中的rows中获取id
		}, deleteModelHandle2, "text");
	}

	function deleteModelHandle2(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			parent.layer.alert('删除成功！');
		} else {
			parent.layer.alert('删除失败！');
		}
		//刷新表格
		$('#tb_flowModel2').bootstrapTable('refresh');
	}

	function deleteModelHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			parent.layer.alert('删除成功！');
		} else {
			parent.layer.alert('删除失败！');
		}
		//刷新表格
		$('#tb_flowModel').bootstrapTable('refresh');
	}

	function deleteModelType() {
		var id = srmRowsType.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/deleteModelType/"
				+ id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : id
		//从选中的rows中获取id
		}, deleteTypeHandle, "text");
	}

	function deleteTypeHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			parent.layer.alert('删除成功！');
		} else {
			parent.layer.alert('删除失败！');
		}
		//刷新表格
		$('#tb_flowModelType').bootstrapTable('refresh');
		$('#tb_flowModel').bootstrapTable('refresh');
	}

	function updateModel2() {
		var id = srmRowsModel.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/updateModel/"
				+ id;
		var role = "";
		var roleid = $("#window_flowModel2 #roleid").val();
		var depid = $("#window_flowModel2 #depid").val();
		if (roleid == "" && depid != "") {
			role = depid;
		} else if (depid == "" && roleid != "") {
			role = roleid;
		} else {
			alert("审批人和审批部门必须选择一个");
			return;
		}
		$.post(url,
				{
					_method : 'PUT',//改成PUT提交
					id : $("#window_flowModel2 #id").val(),
					flowmodeltypeid : $("#window_flowModel2 #flowmodeltypeid")
							.val(),
					flowmodelname : $("#window_flowModel2 #flowmodelname")
							.val(),
					planday : $("#window_flowModel2 #planday").val(),
					roleid : role,
					seq : $("#window_flowModel2 #seq").val(),
					invalid : $(
							'#window_flowModel2 input[name=invalid]:checked')
							.val(),
					remark : $("#window_flowModel2 #remark").val()
				}, updateSrmdModel2, "text");
		//用来关闭新增窗口***********
		$("#window_flowModel2").modal('hide');
	}

	function updateSrmdModel2(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('修改成功！');
		} else {
			parent.layer.alert('修改失败！');
		}
		//刷新表格
		$('#tb_flowModel2').bootstrapTable('refresh');
	}

	function updateModel() {
		var id = srmRowsModel.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/updateModel/"
				+ id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#window_flowModel #id").val(),
			flowmodeltypeid : $("#window_flowModel #flowmodeltypeid").val(),
			flowmodelname : $("#window_flowModel #flowmodelname").val(),
			planday : $("#window_flowModel #planday").val(),
			empid : $("#window_flowModel #empid").val(),
			seq : $("#window_flowModel #seq").val(),
			invalid : $('#window_flowModel input[name=invalid]:checked').val(),
			remark : $("#window_flowModel #remark").val()
		}, updateSrmdModel, "text");
		//用来关闭新增窗口***********
		$("#window_flowModel").modal('hide');
	}

	function updateSrmdModel(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('修改成功！');
		} else {
			parent.layer.alert('修改失败！');
		}
		//刷新表格
		$('#tb_flowModel').bootstrapTable('refresh');
	}

	function updateModelType() {
		var id = srmRowsType.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/updateModelType/"
				+ id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#window_flowModelType #id").val(),
			flowmodeltype : $("#window_flowModelType #flowmodeltype").val(),
			invalid : $('#window_flowModelType input[name=invalid]:checked')
					.val(),
			remark : $("#window_flowModelType #remark").val(),
		}, updateSrmdModelType, "text");
		//用来关闭新增窗口***********
		$("#window_flowModelType").modal('hide');
	}

	function updateSrmdModelType(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('修改成功！');
		} else {
			parent.layer.alert('修改失败！');
		}
		//刷新表格
		$('#tb_flowModelType').bootstrapTable('refresh');
	}

	//新增学生状态，ajax提交
	function addModelSel() {
		//用来判断表单是否验证通过
		if (!modelSelForm($("#flowModelSelForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/flowModelselAdd";
		$.post(url, {
			modelname : $("#window_flowModelSel #modelname").val(),
			modelselname : $("#window_flowModelSel #modelselname").val(),
			flowType : $('#window_flowModelSel input[name=flowType]:checked')
					.val(),
			remark : $("#window_flowModelSel #remark").val()
		}, addSrmHandleSel, "text");
		//用来关闭新增窗口***********
		$("#window_flowModelSel").modal('hide')
	}
	//新增学生状态，ajax提交
	function updateModelSel() {
		var id = srmRows.id;
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#window_flowModelSel #id").val(),
			modelname : $("#window_flowModelSel #modelname").val(),
			modelselname : $("#window_flowModelSel #modelselname").val(),
			invalid : $('#window_flowModelSel input[name=invalid]:checked')
					.val(),
			flowType : $('#window_flowModelSel input[name=flowType]:checked')
					.val(),
			remark : $("#window_flowModelSel #remark").val()
		}, updateSrmHandle, "text");
		//用来关闭新增窗口***********
		$("#window_flowModelSel").modal('hide')
	}
	function updateSrmHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('修改成功！');
		} else {
			parent.layer.alert('修改失败！');
		}
		//刷新表格
		$('#tb_flowModelSel').bootstrapTable('refresh');
	}

	function addSrmHandleSel(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			parent.layer.alert('添加成功！');
		} else {
			parent.layer.alert('添加失败！');
		}
		//刷新数据
		$('#tb_flowModelSel').bootstrapTable('refresh');
	}

	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
		//传查询条件的值
		};
		return temp;
	}

	function queryParams1(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			//传查询条件的值
			modelselid : $("#window_flowModelType #modelselid").val()

		};
		return temp;
	}

	function queryParams2(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			//传查询条件的值
			flowmodeltypeid : $("#window_flowModel #flowmodeltypeid").val()
		};
		return temp;
	}

	function queryParams3(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			//传查询条件的值
			flowmodeltypeid : $("#window_flowModel2 #flowmodeltypeid").val()
		};
		return temp;
	}

	function selectemp() {
		var url = "${pageContext.request.contextPath }/flow/flowModelSel/findEmpIdByDepId";
		$.post(url, {
			id : $("#window_flowModel #depid").val(),
		}, inemp, "json");
	}

	function inemp(data) {
		var opt = "";
		opt += '<option value="'+data.rows.id+'" hassubinfo="true">'
				+ data.rows.empname + '</option>';
		$("#window_flowModel #empid").html(opt);
	}

	function depidDisabled() {
		var roleid = $("#window_flowModel2 #roleid").val();
		if (roleid == "") {
			$("#window_flowModel2 #depid").attr("disabled", false);
		} else {
			$("#window_flowModel2 #depid").attr("disabled", true);
		}
	}

	function roleidDisabled() {
		var depid = $("#window_flowModel2 #depid").val();
		if (depid == "") {
			$("#window_flowModel2 #roleid").attr("disabled", false);
		} else {
			$("#window_flowModel2 #roleid").attr("disabled", true);
		}
	}
</script>
</head>
<body class="gray-bg">

	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-5">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>流程字典</h5>
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

						<div id="toolbar_flowModelSel" class="btn-group">
							<button id="btn_flowModelSel_add" type="button"
								class="btn btn-primary" data-toggle="modal" data-toggle="modal"
								data-target="#window_flowModelSel">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_flowModelSel_update" type="button"
								class="btn btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
						</div>

						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_flowModelSel"></table>
					</div>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>流程节点审批人字典</h5>
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

						<div id="toolbar_flowRole" class="btn-group">
							<button id="btn_flowRole_add" type="button"
								class="btn btn-primary" data-toggle="modal" data-toggle="modal">
								<!-- data-target="#window_flowRole" -->
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_flowRole_update" type="button"
								class="btn btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
						</div>

						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_flowRole"></table>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-5">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>流程模板</h5>
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
						<div id="toolbar_flowModelType" class="btn-group">
							<button id="btn_flowModelType_add" type="button"
								class="btn btn-primary">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_flowModelType_update" type="button"
								class="btn btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_flowModelType_delete" type="button"
								class="btn btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_flowModelType"></table>
					</div>

				</div>
			</div>
			<div class="col-sm-7" id="flowModeldiv" style="display: block">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>模板审批节点</h5>
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
						<div id="toolbar_flowModel" class="btn-group">
							<button id="btn_flowModel_add" type="button"
								class="btn btn-primary">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_flowModel_update" type="button"
								class="btn btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_flowModel_delete" type="button"
								class="btn btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_flowModel"></table>
					</div>

				</div>
			</div>
			<div class="col-sm-7" id="flowModeldiv2" style="display: none">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>模板审批节点</h5>
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
						<div id="toolbar_flowModel2" class="btn-group">
							<button id="btn_flowModel2_add" type="button"
								class="btn btn-primary">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_flowModel2_update" type="button"
								class="btn btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_flowModel2_delete" type="button"
								class="btn btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_flowModel2"></table>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- 流程审批角色字典修改弹窗  -->
	<div class="modal inmodal" id="window_flowRole" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">流程审批角色(角色编码唯一)</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowRoleForm"
						novalidate="novalidate">
						<input id="id" name="id" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">角色名称:</label>
							<div class="col-sm-8">
								<input id="rolename" name="rolename" type="text"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">角色编码:</label>
							<div class="col-sm-8">
								<input id="roleselname" name="roleselname" type="text"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">状态:</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									checked="checked" id="invalid" name="invalid" value="1">&nbsp;&nbsp;&nbsp;有效</label>
								<label class="control-label" style="padding-left: 20px"><input
									type="radio" id="invalid" name="invalid" value="0">&nbsp;&nbsp;无效</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">备注:</label>
							<div class="col-sm-8">
								<textarea id="remark" name="remark" rows="4"
									class="form-control"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" id="btn_role_save" class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 模板字典修改弹窗  -->
	<div class="modal inmodal" id="window_flowModelSel" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">流程模板字典(模板编码唯一)</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowModelSelForm"
						novalidate="novalidate">
						<input id="id" name="id" value="${id }" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">模板类型:</label>
							<div class="col-sm-8">
								<input id="modelname" name="modelname" type="text"
									value="${modelname }" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">模板类型编码:</label>
							<div class="col-sm-8">
								<input id="modelselname" name="modelselname" type="text"
									value="${modelselname }" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">审批模式:</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									checked="checked" id="flowType" name="flowType" value="1">&nbsp;&nbsp;&nbsp;选人模式</label>
								<label class="control-label" style="padding-left: 20px"><input
									type="radio" id="flowType" name="flowType" value="2">&nbsp;&nbsp;角色模式</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">状态:</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									checked="checked" id="invalid" name="invalid" value="1">&nbsp;&nbsp;&nbsp;有效</label>
								<label class="control-label" style="padding-left: 20px"><input
									type="radio" id="invalid" name="invalid" value="0">&nbsp;&nbsp;无效</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">备注:</label>
							<div class="col-sm-8">
								<textarea id="remark" name="remark" rows="4"
									class="form-control">${remark }</textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" id="btn_modelSel_save" onclick=""
								class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 流程模板类型修改弹框  -->
	<div class="modal inmodal" id="window_flowModelType" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<input id="modelselid" value="${modelselid }" type="hidden">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">流程模板类型</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowModelTypeForm"
						novalidate="novalidate">
						<input id="id" name="id" value="${id }" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">模板类型名称:</label>
							<div class="col-sm-8">
								<input id="flowmodeltype" name="flowmodeltype" type="text"
									value="${flowmodeltype }" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">模板类型编码:</label>
							<div class="col-sm-8">
								<input readonly="readonly" type="text" id="modelselname"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">状态:</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									checked="checked" id="invalid" name="invalid" value="1">&nbsp;&nbsp;&nbsp;有效</label>
								<label class="control-label" style="padding-left: 20px"><input
									type="radio" id="invalid" name="invalid" value="0">&nbsp;&nbsp;无效</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">备注:</label>
							<div class="col-sm-8">
								<textarea id="remark" name="remark" rows="4"
									class="form-control">${remark }</textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" id="btn_modelType_save"
								class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 审批节点修改弹框  -->
	<div class="modal inmodal" id="window_flowModel" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<input id="flowmodeltypeid" type="hidden"> <input
					id="flowType" type="hidden">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">流程审批节点</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowModelForm"
						novalidate="novalidate">
						<input id="id" name="id" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">模板类型名称:</label>
							<div class="col-sm-8">
								<input id="flowmodeltype" type="text" readonly="readonly"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">审批节点名称:</label>
							<div class="col-sm-8">
								<input type="text" id="flowmodelname" name="flowmodelname"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">部门:</label>
							<div class="col-sm-3">
								<select id="depid" class="form-control" onchange="selectemp();">
									<option value="">---------</option>
									<c:forEach items="${deplist}" var="dep">
										<option value="${dep.id}">${dep.depname}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">负责人:</label>
							<div class="col-sm-3">
								<select id="empid" name="empid" class="form-control">

								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">计划完成时间:</label>
							<div class="col-sm-3">
								<input type="text" id="planday" name="planday"
									class="form-control">
							</div>
							<label class="col-sm-2 control-label">节点步骤:</label>
							<div class="col-sm-3">
								<input type="text" id="seq" name="seq" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">状态:</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									checked="checked" id="invalid" name="invalid" value="1">&nbsp;&nbsp;&nbsp;有效</label>
								<label class="control-label" style="padding-left: 20px"><input
									type="radio" id="invalid" name="invalid" value="0">&nbsp;&nbsp;无效</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">备注:</label>
							<div class="col-sm-8">
								<textarea id="remark" name="remark" rows="3"
									class="form-control">${remark }</textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" id="btn_model_save" class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 审批节点修改弹框  -->
	<div class="modal inmodal" id="window_flowModel2" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<input id="flowmodeltypeid" type="hidden">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">流程审批节点</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowModelForm2"
						novalidate="novalidate">
						<input id="id" name="id" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">模板类型名称:</label>
							<div class="col-sm-8">
								<input id="flowmodeltype" type="text" readonly="readonly"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">审批节点名称:</label>
							<div class="col-sm-8">
								<input type="text" id="flowmodelname" name="flowmodelname"
									class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">审批人:</label>
							<div class="col-sm-3">
								<select id="roleid" class="form-control"
									onchange="depidDisabled();">
									<option value="">---------</option>
									<c:forEach items="${flowRoles}" var="role">
										<option value="${role.roleselname}">${role.rolename}</option>
									</c:forEach>
								</select>
							</div>

							<label class="col-sm-2 control-label">审批部门:</label>
							<div class="col-sm-3">
								<select id="depid" class="form-control"
									onchange="roleidDisabled();">
									<option value="">---------</option>
									<c:forEach items="${deps}" var="dep">
										<option value="${dep.id}">${dep.depname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">计划完成时间:</label>
							<div class="col-sm-3">
								<input type="text" id="planday" name="planday"
									class="form-control">
							</div>
							<label class="col-sm-2 control-label">节点步骤:</label>
							<div class="col-sm-3">
								<input type="text" id="seq" name="seq" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">状态:</label>
							<div class="col-sm-8">
								<label class="control-label"><input type="radio"
									checked="checked" id="invalid" name="invalid" value="1">&nbsp;&nbsp;&nbsp;有效</label>
								<label class="control-label" style="padding-left: 20px"><input
									type="radio" id="invalid" name="invalid" value="0">&nbsp;&nbsp;无效</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">备注:</label>
							<div class="col-sm-8">
								<textarea id="remark" name="remark" rows="3"
									class="form-control">${remark }</textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" id="btn_model2_save"
								class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script type="text/javascript">
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
		function modelSelForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					modelname : "required",
					modelselname : "required",
				},
				messages : {
					modelname : icon + "请输入流程字典名称",
					modelselname : icon + "请输入流程字典编码",
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}

		function modelRoleForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					rolename : "required",
					roleselname : "required",
				},
				messages : {
					rolename : icon + "请输入角色名称",
					roleselname : icon + "请输入角色编码",
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}

		function modelTypeForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					flowmodeltype : "required",
				},
				messages : {
					flowmodeltype : icon + "请输入流程模板名称",
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}

		function modelForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					flowmodelname : "required",
					empid : "required",
					planday : {
						required : true,
						digits : true
					},
					seq : {
						required : true,
						digits : true
					}
				},
				messages : {
					flowmodelname : icon + "请输入审批节点名称",
					empid : icon + "请选择员工",
					planday : {
						required : icon + "输入计划完成天数",
						digits : icon + "天数必须是整数"
					},
					seq : {
						required : icon + "请输入节点步骤",
						digits : icon + "步骤必须是数字"
					}
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}

		function modelForm2(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					flowmodelname : "required",
					roleid : "required",
					planday : {
						required : true,
						digits : true
					},
					seq : {
						required : true,
						digits : true
					}
				},
				messages : {
					flowmodelname : icon + "请输入审批节点名称",
					roleid : icon + "请选择角色",
					planday : {
						required : icon + "输入计划完成天数",
						digits : icon + "天数必须是整数"
					},
					seq : {
						required : icon + "请输入节点步骤",
						digits : icon + "步骤必须是数字"
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