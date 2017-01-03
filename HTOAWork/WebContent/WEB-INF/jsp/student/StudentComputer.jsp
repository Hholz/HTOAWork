<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>奖惩记录表</title>
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
		//修改按钮事件
		$("#btn_edit").click(function() {
			$("#save").attr("onclick", "updateStudent()");
			if (rows == null) {
				parent.layer.alert('请选你要修改的数据！');
				return;
			}
			var upset = rows.stat;
			if (upset != 0) {
				parent.layer.alert('此申请已提交！');
				return;
			}
			init();
		});
		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		$("#btn_add").click(function() {
			$("#save").attr("onclick", "addStart()");
			$("#window_update").modal('show');
			$("#window_update #studentid").val('');
			$("#window_update #depid").val('');
			$("#window_update #empid").val('');
		});
		//清空条件
		$("#clean").click(function() {
			$("#remark").val("");
			$("#studentid").val("");
			$("#applyperson").val("");
			$("#flowid").val("");
			$("#stat").val("");
			$("#applytime").val("");
			$("#managetype").val("");
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		//删除按钮事件
		$('#btn_delete').click(function() {
			
			if (rows == null) {
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			var upset = rows.stat;
			if (upset != 0) {
				parent.layer.alert('此申请已提交！');
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
			$('#feedbackstart_Tab')
					.bootstrapTable(
							{
								url : '${pageContext.request.contextPath}/student/scomputer/scomputerlist', //请求后台的URL（*）sreward/stureward、stuheart/sayheart
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
									field : 'student.stuname',
									title : '申请人'
								}, {
									field : 'applyno',
									title : '申请单号',
									formatter : function(value, row, index) {
										var id = row.applyno;
										var name = "";
										if (id == "") {
											name = "未提交";
										} else {
											name = row.applyno;
										}
										return name;
									}
								}, {
									field : 'applytime',
									title : '申请时间'
								}, {
									field : 'managetype',
									title : '类别',
									formatter : function(value, row, index) {
										var managetype = row.managetype;
										if (managetype == "") {
											return "-";
										} else {
											return row.computerManage.managetype;
										}
										return name;
									}
								}, {
									field : 'stat',
									title : '审批进度',
									formatter : function(value, row, index) {
										var id = row.stat;
										var name = "";
										if (id == 0) {
											name = "未提交";
										} else if (id == 1) {
											name = "已提交,正在等待后勤部确认";
										} else if (id == 2) {
											name = "后勤部已确认,已申请备用电脑";
										} else if (id == 3) {
											name = "申请通过,已领用备用电脑";
										} else if (id == 4) {
											name = "备用电脑已归还";
										} else if (id == 5) {
											name = "新生领取新电脑";
										} else if (id == 6) {
											name = "新电脑已发放";
										}
										return name;
									}
								}, {
									field : 'remark',
									title : '简述'
								} ]
							});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				remark : $('#formSearch #remark').val(),
				studentid : $('#formSearch #studentid').val(),
				applyno : $('#formSearch #applyno').val(),
				applyperson : $('#formSearch #applyperson').val(),
				applytime : $('#formSearch #applytime').val(),
				stat : $('#formSearch #stat').val(),
				managetype : $('#formSearch #managetype').val(),

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
		if(!validateForm($("#dataForm"))){
			return;
		}
		var url = "${pageContext.request.contextPath }/student/scomputer/add";
		$.post(url, {
			studentid : $('#window_update #empid').val(),
			remark : $('#window_update #remark').val()
		}, addStartHandle, "text");
		$("#window_update").modal('hide')
		$('#feedbackstart_Tab').bootstrapTable('refresh');
		$("#window_update #empid").val('');
		$("#window_update #remark").val('');
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
	}

	//修改学生，ajax提交
	function updateStart() {
		var upset = rows.stat;
		if (upset != 0) {
			alert("此申请已提交!");
			return;
		}
		if(!validateForm($("#dataForm"))){
			return;
		}
		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/student/scomputer/" + id;
		$.post(url, {

			_method : 'PUT',//改成PUT提交
			id : $('#window_update #id').val(),
			studentid : $('#window_update #studentid').val(),
			remark : $('#window_update #remark').val()
		}, updateStartHandle, "text");
		$("#window_update").modal('hide')

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
		var url = "${pageContext.request.contextPath }/student/scomputer/upset";
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
		
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/student/scomputer/" + id;
		//alert(url);
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : rows.id,
			status : 0
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
	function selectemp() {
		var url = "${pageContext.request.contextPath }/student/scomputer/findstudent";
		$.post(url, {
			id : $("#window_update #depid").val(),
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
						+ datas.rows[i].stuname + '</option>';
			}
			$('#window_add #empid').empty();
			$('#window_update #empid').html(opt);
		}
		$("#window_update #empid").val(rows.studentid);
	}
	function init() {
		var url = "${pageContext.request.contextPath }/student/scomputer/findstudent";
		$.post(url, {
			id : rows.student.classid,
		}, initemp, "text");
		$("#window_update #id").val(rows.id);
		$("#window_update #depid").val(rows.student.classid);
		$("#window_update #remark").val(rows.remark);
		$('#window_update').modal('show');
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
								<label class="col-sm-3 control-label">班级：</label>
								<div class="col-sm-3">
									<select id="depid" required name="depid" class="form-control"
										onchange="selectemp();">
										<option value="">------</option>
										<c:forEach items="${classlist}" var="dep">
											<option value="${dep.id}">${dep.classname}</option>
										</c:forEach>
									</select>
								</div>
									<label class="col-sm-2 control-label">申请状态：</label>
								<div class="col-sm-3">
									<select id="stat" required class="form-control">
										<option value="">--请选择--</option>
										<option value="1">正在申请</option>
										<option value="2">申请通过</option>
										<option value="3">申请失败</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">类别：</label>
								<div class="col-sm-3">
									<select id="managetype" required class="form-control">
										<option value="">--请选择--</option>
										<c:forEach items="${comlist}" var="c">
											<option value="${c.id}">${c.managetype}</option>
										</c:forEach>
									</select>
								</div>

								<div class="col-sm-4">
									<button type="button" class="btn btn-primary" id="query">查询</button>
									<button type="button" id="clean" class="btn btn-primary">清空</button>
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
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
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

		<div class="modal inmodal" id="window_update" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">电脑维修申请</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dataForm"
							novalidate="novalidate">
							<input type="hidden" id="id" value="" />
							<div class="form-group">
								<label class="col-sm-3 control-label">班级：</label>
								<div class="col-sm-8">
									<select id="depid" required name="depid" class="form-control"
										onchange="selectemp();">
										<option value="">------</option>
										<c:forEach items="${classlist}" var="dep">
											<option value="${dep.id}">${dep.classname}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">申请人：</label>
								<div class="col-sm-8">
									<select id="empid" required name="empid" class="form-control">
										<option value="">------</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">描述说明：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="描述说明："
										style="resize: none" name="remark" id="remark"></textarea>
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
		<table id="feedbackstart_Tab"></table>
		 
	</div>
	<script type="text/javascript">
			//已经把文档下到本地，访问地址：http://localhost:8080/HTOAWork/Demo/validateDemo/jQueryValidate.html
			//详情参考：http://www.runoob.com/jquery/jquery-plugin-validate.html
			//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
			$.validator.setDefaults({
				highlight : function(element) {
					$(element).closest('.form-group')
							.removeClass('has-success').addClass('has-error');
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
							required : icon + "必选",
						},
						empid : {
							required : icon + "必选",
						},
						
					},
				});
				//返回表单验证是否通过，true false
				return validator.form();
			}
		</script>
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
			elem : '#applytimeadd',
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
		laydate(start);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#applytime',
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
		laydate(start);
	</script>
</body>
</html>