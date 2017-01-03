<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<title>员工请假申请</title>

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
		$("#btn_edit").click(function() {
			if (rows == null) {
				parent.layer.alert('请选你要修改的数据！');
				return;
			}
			var upset = rows.upset;
			if (upset == 1) {
				alert("此申请已提交!");
				return;
			}
			//把内容放到更新的列表
			$("#save").attr("onclick", "updateStudent()");
			$("#window_add #id").val(rows.id);
			$("#window_add #startdate_add").val(rows.starttime);
			$("#window_add #enddate_add").val(rows.endtime);
			$("#window_add #reason").val(rows.holidayRemark);
			$("#window_add #holidaytypeid").val(rows.holidaytypeid);
			$('#window_add').modal('show');
		});
		//新增按钮事件*************************
		$("#btn_add").click(function() {
			$("#window_add #id").val('');
			$("#window_add #startdate_add").val('');
			$("#window_add #enddate_add").val('');
			$("#window_add #reason").val('');
			$("#window_add #holidaytypeid").val('');
			$('#window_add').modal('show');
			//修改按钮的点击事件
			$("#save").attr("onclick", "addStudent()");
		});
		//条件查询click事件注册
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
			var upset = rows.upset;
			if (upset == 1) {
				alert("此申请已提交!");
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
	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_departments').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/Holiday/1', //请求后台的URL（*）
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
				search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
				strictSearch : false,
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
					checkbox : true
				}, {
					field : 'id',
					title : 'ID',
					visible : false
				}, {
					field : 'empname',
					title : '申请人'
				}, {
					field : 'holidayday',
					title : '请假天数'
				}, {
					field : 'holidayRemark',
					title : '请假原因'
				}, {
					field : 'starttime',
					title : '开始时间'
				}, {
					field : 'endtime',
					title : '结束时间'
				}, {
					field : 'upset',
					title : '状态',
					formatter : function(value, row, index) {
						var id = row.upset;
						var name = "";
						if (id == 0) {
							name = "未提交";
						} else if (id == 1) {
							name = "已提交";
						}
						return name;
					}
				},{
					field : 'approvalstatus',
					title : '审批进度',
					formatter : function(value, row, index) {
						var id = row.approvalstatus;
						var name = "";
						if (id == 0) {
							name = "未提交";
						} else if (id == 1) {
							name = "已提交,正在等待领导审批";
						}else if (id == 2) {
							name = "领导否定了你的申请";
						}else if (id == 3) {
							name = "领导通过了你的申请,正在等待财务部审批";
						}else if (id == 4) {
							name = "财务部否定了你的申请";
						}else if (id == 5) {
							name = "财务部通过了你的申请";
						}else if (id == 6) {
							name = "后勤部已确认";
						}
						return name;
					}
				}, ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码  
				empid : $("#empid").val(),
				holidayday : $("#mname").val(),
				upset : $("#invalid_id").val(),
				holidaytypeid : $("#typeid").val(),
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
		if (!validateForm($("#commentForm"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/dailyWork/Holiday/add";
		$.post(url, {
			starttime : $("#window_add #startdate_add").val(),
			endtime : $("#window_add #enddate_add").val(),
			holidayRemark : $("#window_add #reason").val(),
			holidaytypeid : $("#window_add #holidaytypeid").val(),
		}, addStudentHandle, "text");

		//用来关闭新增窗口***********
		$("#window_add").modal('hide')
		//刷新数据
		$('#tb_departments').bootstrapTable('refresh');
		//清空新增div中的数据
		$("#window_add #id").val('');
		$("#window_add #startdate_add").val('');
		$("#window_add #enddate_add").val('');
		$("#window_add #reason").val('');
		$("#window_add #holidaytypeid").val('');
	}
	function addStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}

	//修改学生，ajax提交
	function updateStudent() {
		var upset = rows.upset;
		if (upset == 1) {
			alert("此申请已提交!");
			return;
		}
		if (!validateForm($("#commentForm"))) {
			return;
		}
		var id = $("#window_add #id").val();
		var url = "${pageContext.request.contextPath }/dailyWork/Holiday/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : $("#window_add #id").val(),
			starttime : $("#window_add #startdate_add").val(),
			endtime : $("#window_add #enddate_add").val(),
			holidayRemark : $("#window_add #reason").val(),
			holidaytypeid : $("#window_add #holidaytypeid").val(),
		}, updateStudentHandle, "text");
		//用来关闭修改窗口***********
		$("#window_add").modal('hide');
		$("#window_add #id").val('');
		$("#window_add #startdate_add").val('');
		$("#window_add #enddate_add").val('');
		$("#window_add #reason").val('');
		$("#window_add #holidaytypeid").val('');
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
		$('#tb_departments').bootstrapTable('refresh');
		//把保存选中行的rows变量清零，很重要，防止缓存
		rows = null;
	}
	//********************************************************删除
	//删除学生，ajax提交
	function deleteStudent() {
		var upset = rows.upset;
		if (upset == 1) {
			alert("此申请已提交!");
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/dailyWork/Holiday/" + id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
		}, deleteStudentHandle, "text");
	}
	function upset() {
		if (rows == null) {
			parent.layer.alert('请选你要提交的数据！');
			return;
		}
		var upset = rows.upset;
		if (upset == 1) {
			alert("此申请已提交!");
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/dailyWork/Holiday/upset";

		$.post(url, {
			id : rows.id
		//从选中的rows中获取id
		}, setmessage, "text");
	}
	function setmessage(data) {
		if (data > 0) {
			swal("提交成功！", "正在等待上级部门的审批。", "success");
		} else {
			swal("提交失败", "服务器繁忙！", "error");
		}
		rows = null;
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
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
	function selectemp() {
		var url = "${pageContext.request.contextPath }/dailyWork/Holiday/findemp";
		$.post(url, {
			id : $("#dep").val(),
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
						+ datas.rows[i].empname + '</option>';
			}
			//$('#window_add #empid').empty();
			$('#empid').html(opt);
			//$('#window_add #empid').trigger("chosen:updated");
			//$('#window_add #empid').chosen();

		}
		$('#empid').val(rows.empid);
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
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">请假类别：</label>
								<div class="col-sm-2">
									<select id="typeid" class="form-control">
										<option value="">---------</option>
										<c:forEach items="${mlist}" var="mt">
											<option value="${mt.id}">${mt.holidaytypename}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">天数：</label>
								<div class="col-sm-2">
									<input class="form-control" type="number" id="mname">
								</div>
								<label class="col-sm-2 control-label">部门：</label>
								<div class="col-sm-2">
									<select id="dep" class="form-control" onchange="selectemp();">
										<option value="">---------</option>
										<c:forEach items="${deplist}" var="dep">
											<option value="${dep.id}">${dep.depname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">员工：</label>
								<div class="col-sm-2">
									<select id="empid" class="form-control">
									</select>
								</div>
								<label class="col-sm-1 control-label">状态：</label>
								<div class="col-sm-2">
									<select id="invalid_id" class="form-control">
										<option value="">-----</option>
										<option value="0">未提交</option>
										<option value="1">已提交</option>
									</select>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">查询</button>
								</div>
							</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="toolbar" class="btn-group">
		<button id="btn_add" type="button" class="btn btn-w-m btn-primary"
			data-toggle="modal">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>申请
		</button>
		<button id="btn_edit" type="button" class="btn btn-w-m btn-success"
			data-toggle="modal">
			<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
		</button>
		<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
		</button>
		<button id="btn_upset" type="button" onclick="upset();"
			class="btn btn-w-m btn-primary">
			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>提交
		</button>
	</div>
	<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">员工请假</h4>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="commentForm"
						novalidate="novalidate">
						<input type="hidden" id="id" />
						<div class="form-group">
							<label class="col-sm-3 control-label">*请假类别：</label>
							<div class="col-sm-3">
								<select id="holidaytypeid" name="typeid" class="form-control" required>
									<option value="">---------</option>
									<c:forEach items="${mlist}" var="mt">
										<option value="${mt.id}">${mt.holidaytypename}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*开始时间：</label>
							<div class="col-sm-8">
								<input placeholder="开始时间" class="form-control layer-date" required
									id="startdate_add" name="startdate">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*结束时间：</label>
							<div class="col-sm-8">
								<input placeholder="结束时间" class="form-control layer-date" required
									id="enddate_add" name="enddate">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*请假原因：</label>
							<div class="col-sm-8">
								<input id="reason" name='reason' type="text" required
									class="form-control">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" onclick="" id="save"
								class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- table代码就这些，用js构建表格 -->
	<table id="tb_departments"></table>
	 
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#startdate_add',
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
			elem : '#enddate_add',
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
					typeid :{
                    	required:icon + "请填写请假类别"
                    },
					startdate :{
                    	required:icon + "请填写开始时间"
                    },
                    enddate :{
                    	required:icon + "请填写结束时间"
                    },
                    reason :{
                    	required:icon + "请填写请假原因"
                    }
				},
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>

</body>
</html>