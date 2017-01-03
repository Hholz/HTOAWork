<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>BootStrap Table使用</title>

<jsp:include page="../styleInclude.jsp"></jsp:include>


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

		//新增按钮事件*************************
		$("#btn_add").click(function() {
			//清空新增div中的数据
			$("#addStudent #id").val('');
			$("#addStudent #quantity").val('');
			$("#addStudent #materialid").val('');
			$("#addStudent #remark").val('');
			$("#addStudent #materialtype").attr("disabled", false);
			$("#addStudent #materialid").attr("disabled", false);
			//修改按钮的点击事件
			$("#save").attr("onclick", "addStudent()");
		});
		//修改按钮事件
		$("#btn_edit").click(function() {
			if (rows == null) {
				parent.layer.alert('请选你要修改的数据！');
				return;
			}
			//用品名不能修改

			//把内容放到更新的列表
			$("#addStudent #id").val(rows.id);
			$("#addStudent #quantity").val(rows.quantity);
			$("#addStudent #materialid").val(rows.materialid);
			$("#addStudent #materialtype").val(rows.materialtypeid);
			$("#addStudent #materialtype").attr("disabled", true);
			$("#addStudent #materialid").attr("disabled", true);
			$("#addStudent #remark").val(rows.remark);
			$('#addStudent').modal('show');
			$("#save").attr("onclick", "updateStudent()");
			selectmaterial();
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
					deleteStudent();
				} else {
					swal("已取消", "您取消了删除操作！", "error");
				}
			});
		});
		//查询按钮
		$('#btn_query').click(function() {
			//alert("查询");
			//刷新数据
			$('#tb_studentApplyHourse').bootstrapTable('refresh');

		});
	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_studentApplyHourse')
					.bootstrapTable(
							{
								url : '${pageContext.request.contextPath}/sysSet/finaPolicQuantitySet/Json', //请求后台的URL（*）
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
								pageSize : 5, //每页的记录行数（*）
								pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
								search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
								strictSearch : false,
								searchOnEnterKey : true, //按回车搜索
								showColumns : true, //是否显示所有的列
								showRefresh : true, //是否显示刷新按钮
								minimumCountColumns : 2, //最少允许的列数
								clickToSelect : true, //是否启用点击选中行
								height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
								uniqueId : "id", //每一行的唯一标识，一般为主键列
								showToggle : true, //是否显示详细视图和列表视图的切换按钮
								cardView : false, //是否显示详细视图
								detailView : false, //是否显示父子表
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
									checkbox : true,
								}, {
									field : 'id',
									title : '编号'
								}, {
									field : 'materialname',
									title : '用品名称',
									formatter : function(value, row, index) {
										var material = row.materialname;
										if (material == null) {
											return "-";
										} else {
											return material;
										}
									}
								}, {
									field : 'quantity',
									title : '报警数量'
								}, {
									field : 'countstatus',
									title : '状态',
									formatter : function(value, row, index) {
										if (row.countstatus == 0) {
											return "充足";
										} else if (row.countstatus == 1) {
											return "紧缺";
										}
									}
								}, {
									field : 'remark',
									title : '说明'
								}, ]
							});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				materialtypeid : $("#materialtypeid").val()
			};
			return temp;
		};
		return oTableInit;
	};

	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	};

	//新增学生，ajax提交
	function addStudent() {
		if (!validateForm($("#addForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/sysSet/finaPolicQuantitySet/add";
		$.post(url, {
			quantity : $("#addStudent #quantity").val(),
			materialid : $('#addStudent #materialid').val(),
			materialtypeid : $("#addStudent #materialtype").val(),
			remark : $("#addStudent #remark").val(),
		}, addStudentHandle, "text");
		//用来关闭新增窗口***********
		$("#addStudent").modal('hide')
	}
	function addStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			//这是弹窗的代码
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
		} else{
			swal("添加失败", "此数据已添加！", "error");
		}
		//刷新数据
		$('#tb_studentApplyHourse').bootstrapTable('refresh');
	}

	//修改学生，ajax提交
	function updateStudent() {
		var id = $("#addStudent #id").val();
		var url = "${pageContext.request.contextPath }/sysSet/finaPolicQuantitySet/"
				+ id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#addStudent #id").val(),
			quantity : $("#addStudent #quantity").val(),
			materialid : $('#addStudent #materialid').val(),
			remark : $("#addStudent #remark").val(),
		}, updateStudentHandle, "text");
		//用来关闭修改窗口***********
		$("#addStudent").modal('hide');
	}
	function updateStudentHandle(data) {
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
		$('#tb_studentApplyHourse').bootstrapTable('refresh');
		//把保存选中行的rows变量清零，很重要，防止缓存
		rows = null;
	}

	//***************************************************************************************删除
	//删除学生，ajax提交
	function deleteStudent() {
		//alert('删除');
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/sysSet/finaPolicQuantitySet/"
				+ id;
		//alert(url);
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : rows.id
		//从选中的rows中获取id
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
		$('#tb_studentApplyHourse').bootstrapTable('refresh');
	}
	function selectmaterial() {
		var url = "${pageContext.request.contextPath }/sysSet/finaPolicQuantitySet/findmaterial";
		$.post(url, {
			id : $("#addStudent #materialtype").val(),
		}, initemp, "text");
	} 
	function initemp(data) {
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
						+ datas.rows[i].materialname + '</option>';
			}
			$('#addStudent #materialid').empty();
			$('#addStudent #materialid').html(opt);
			$('#addStudent #materialid').trigger("chosen:updated");
		}
		$("#addStudent #materialid").val(rows.materialid);
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
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group" style="margin-top: 15px">
								<label class="control-label col-sm-1"
									for="txt_search_departmentname">类别</label>       
								<div class="col-sm-6">
									<!--input type="text" class="form-control" id="txt_search_phone"-->
									<div class="col-sm-6">
									<select id="materialtypeid" name="materialtypeid" class="form-control">
										<option value="">------</option>
										<c:forEach items="${materialtypelist}" var="m">
											<option value="${m.id}">${m.materialtypename}</option>
										</c:forEach>
									</select>
								</div>
								<!--  
								<label class="control-label col-sm-1" for="txt_search_statu">申请人</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="txt_search_stuno">       
								</div>
								-->
								<div class="col-sm-3">
									<button type="button" style="margin-left: 50px" id="btn_query"
										class="btn btn-primary">查询</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>库存信息</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<button id="btn_add" type="button"
								class="btn btn-w-m btn-primary" data-toggle="modal"
								data-target="#addStudent">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_edit" type="button"
								class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_delete" type="button"
								class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_studentApplyHourse"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal inmodal fade in" id="addStudent" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<form id="addForm" action="javascript:void(0)">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">报警数量</h4>
					</div>
					<div>
						<div class="form-horizontal m-t">
							<input id="id" type="hidden">
							<div class="form-group">
								<label class="col-sm-3 control-label">选择类别：</label>
								<div class="col-sm-6">
									<select id="materialtype" name="materialtype" onchange="selectmaterial();" class="form-control">
										<option value="">------</option>
										<c:forEach items="${materialtypelist}" var="m">
											<option value="${m.id}">${m.materialtypename}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">选择用品：</label>
								<div class="col-sm-6">
									<select id="materialid" name="materialid" class="form-control">
										<option value="">-----</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">报警数量：</label>
								<div class="col-sm-6">
									<input name="quantity" id="quantity" type="number"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">说明：</label>
								<div class="col-sm-6">
									<textarea name="remark" rows="3" class="form-control"
										id="remark"></textarea>
								</div>
							</div>
						</div>
						<div class="modal-footer" align="center">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="submit" id="save" onclick=""
								class="btn btn-primary">确定</button>
						</div>
					</div>
				</div>
			</form>
			<small class="font-bold"> </small>
		</div>
		<small class="font-bold"> </small>
	</div>
	 
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#entertime',
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
		laydate(start);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#birthday',
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
		laydate(start);
	</script>
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
					materialid : "required",
					quantity : "required",
					remark : "required"
				},
				messages : {
					materialid : icon + "请选择用品",
					quantity : icon + "请输入报警数量",
					remark : icon + "请输入说明"
				},
				submitHandler : function(form) {
					//alert("表单验证成功，不提交"+validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
</body>
</html>