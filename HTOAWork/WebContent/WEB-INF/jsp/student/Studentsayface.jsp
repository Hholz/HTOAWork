<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起反馈</title>
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
		$("#btn_edit").click(
				
				function() {
					
					if(rows==null || rows.id==null){
						swal("警告！", "请选择你要修改的ID", "error");
						//把内容放到更新的列表
						$('#window_update').modal();
					}
					//把内容放到更新的列表
					$('#window_update #id ').val(rows.id);
					$('#window_update #sayfaceid ').val(rows.sayfaceid);
					$('#window_update #teacherid ').val(rows.teacherid);
					$('#window_update #sayback ').val(rows.sayback);
					$('#window_update #sayscon ').val(rows.sayscon);
					$('#window_update #sayproblem ').val(
							rows.sayproblem);
					$('#window_update #createTime ').val(
							rows.startTime.substring(0, 19));
					$('#window_update #saytime ').val(
							rows.endTime.substring(0, 19));
					
					
				});
		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		//清空条件
		$("#clean").click(function() {
			$("#startEmpid").val("");
			$("#sayfaceid").val("");
			$("#empid").val("");
			$("#start").val("");
			$("#end").val("");
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		//删除按钮事件
		$('#btn_delete').click(function() {
			if(rows==null || rows.id==null){
				swal("警告！", "请选择你要删除的ID", "error");
				
			}else{
			
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
		  }
		});

	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#feedbackstart_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/stuheart/sayheart', //请求后台的URL（*）
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
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(rows != null){
	                	rows = null;
	                }
	                rows = row;
	            },
	            onUncheck: function(row) {
	                if(rows != null){
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
					field : 'sayfaceid',
					title : '谈心学生',
					formatter : function(value, row, index) {
						var student = row.student;
						if(student==null){
							return "-";
						}else{
							return student.stuname;
						}
					}
				}, {
					field : 'teacherid',
					title : '谈心老师',
					formatter : function(value, row, index) {
						var emp = row.emp;
						if(emp==null){
							return "-";
						}else{
							return emp.empname;
						}
					}
				}, {
					field : 'sayscon',
					title : '谈心内容'
				}, {
					field : 'sayproblem',
					title : '谈心问题'
				}, {
					field : 'sayback',
					title : '学生反馈'
				}, {
					field : 'createTime',
					title : '开始时间',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 19);
						}
					}
				}, {
					field : 'saytime',
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
				sayfaceid : $('#formSearch #sayfaceid').val() ,
				teacherid : $('#formSearch #empid').val() ,
				createTime : $('#formSearch #start').val(),
				saytime : $('#formSearch #end').val()
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
		if(!validateForm($("#addsayface"))){
			return;
		}
		
		var url = "${pageContext.request.contextPath }/stuheart/heart/add";
		$.post(url, {
			sayfaceid : $('#window_add #sayfaceid').val(),
			teacherid : $('#window_add #teacherid').val(),
			sayscon : $('#window_add #sayscon').val(),
			sayproblem : $('#window_add #sayproblem').val(),
			saytime : $('#window_add #saytimeadd').val(),
			sayback : $('#window_add #sayback').val(),
			createTime : $('#window_add #createTimeadd').val()
		}, addStartHandle, "text");
		$("#window_add").modal('hide')
		$('#feedbackstart_Tab').bootstrapTable('refresh');
		$("#window_add #sayfaceid").val('');
		$("#window_add #teacherid").val('');
		$("#window_add #sayscon").val('');
		$("#window_add #sayproblem").val('');
		$("#window_add #saytime").val('');
		$("#window_add #sayback").val('');
		$("#window_add #createTime").val('');
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
		
		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/stuheart/heart/"+ id;
		$.post(url, {
			
			_method : 'PUT',//改成PUT提交
			sayfaceid : $('#window_update #sayfaceid').val(),
			teacherid : $('#window_update #teacherid').val(),
			sayscon : $('#window_update #sayscon').val(),
			sayproblem : $('#window_update #sayproblem').val(),
			sayback : $('#window_update #sayback').val(),
			saytime : $('#window_update #saytime').val(),
			createTime : $('#window_update #createTime').val()
		}, updateStartHandle, "text");
		$("#window_update").modal('hide')
		
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
		var url = "${pageContext.request.contextPath }/stuheart/heart/" + id;
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
	
	//班级学生二级联动
	function selectstud() {
		//var classid=$("#window_srmd #classid").val();
		var url = "${pageContext.request.contextPath }/stuheart/findstudent";
		$.post(url, {
			classid : $("#formSearch #stuclassid").val(),
		}, initstud, "text");
	}
	function initstud(data) {
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
						+ datas.rows[i].stuname + '</option>';
			}
			$('#formSearch #sayfaceid').empty();
			$('#formSearch #sayfaceid').html(opt);
			$('#formSearch #sayfaceid').trigger("chosen:updated");
			$('#formSearch #sayfaceid').chosen();
		}else{
			$('#formSearch #sayfaceid').empty();
		}
	}
	
	
	//班级学生二级联动新增
	function selectstuadd(){
		//var classid=$("#window_srmd #classid").val();
		var url = "${pageContext.request.contextPath }/stuheart/findstudent";
		$.post(url, {
			classid : $("#window_add #stuclassid").val(),
		}, initstud, "text");
	}
	function initstud(data) {
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
						+ datas.rows[i].stuname + '</option>';
			}
			$('#window_add #sayfaceid').empty();
			$('#window_add #sayfaceid').html(opt);
			$('#window_add #sayfaceid').trigger("chosen:updated");
			$('#window_add #sayfaceid').chosen();
		}else{
			$('#window_add #sayfaceid').empty();
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
								<label class="col-sm-2 control-label">学生班级：</label>
								<div class="col-sm-3">
									<select class="chosen-select" onchange="selectstud();" style="width:180px;height:30px;" tabindex="2" id="stuclassid" name="stuclassid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${scList}" var="e">
		                                   <option value="${e.id}">${e.classname}</option >
		                                </c:forEach>
		                            </select>
								</div>
							
								<label class="col-sm-2 control-label">谈心学生：</label>
								<div class="col-sm-3">
									<select class="chosen-select" style="width:180px;height:30px;" tabindex="2" id="sayfaceid" name="sayfaceid">
		                              	<option value=""> -请选择---</option >
		                            </select>
								</div>
								
							</div>
							<div class="form-group" style="margin-left: 250px">
								<label class="col-sm-2 control-label">谈心老师：</label>
								<div class="col-sm-3">
									<select class="chosen-select" style="width:180px;height:30px;" tabindex="2" name="teacherid" id="empid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach>
		                            </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">时间：</label>
								<div class="col-sm-3">
									<input placeholder="谈心开始日期" class="form-control layer-date"
										id="start" name="start">
								</div>
								<div class="col-sm-3">
									<input placeholder="谈心结束日期" class="form-control layer-date"
										id="end" name="end">
								</div>
								<div class="col-sm-3">
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

		<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">谈心记录修改表</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dataForm"
							novalidate="novalidate">
							<div class="form-group">
								<input type="hidden" name="id" id="id" />  
								<label class="col-sm-3 control-label">谈心学生：</label>
								<div class="col-sm-8">
									<select class="chosen-select" style="width:362px;height:30px;" tabindex="2" id="sayfaceid" name="sayfaceid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${stulist}" var="e">
		                                   <option value="${e.id}">${e.stuname}</option >
		                                </c:forEach>
		                            </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" >谈心老师：</label>
								<div class="col-sm-8">
									<select class="chosen-select" style="width:362px;height:30px;" tabindex="2" id="teacherid" name="teacherid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" >谈心时间：</label>
								<div class="col-sm-8">
									<input placeholder="谈心开始" class="form-control layer-date"
										id="createTime" name="createTime"> <input
										placeholder="谈心结束" class="form-control layer-date"
										id="saytime" name="saytime">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">谈心内容：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="谈心内容："
										style="resize: none" id="sayscon"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">谈心问题：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="谈心问题："
										style="resize: none" id="sayproblem"></textarea>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">学生反馈：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="谈心问题："
										style="resize: none" id="sayback"></textarea>
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
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
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">谈心记录添加表</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addsayface"
							novalidate="novalidate">
							<div class="form-group">
								<label class="col-sm-3 control-label">学生班级：</label>
								<div class="col-sm-8">
									<select class="chosen-select" onchange="selectstuadd();" style="width:362px;height:30px;" tabindex="2" id="stuclassid" name="stuclassid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${scList}" var="e">
		                                   <option value="${e.id}">${e.classname}</option >
		                                </c:forEach>
		                            </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">谈心学生：</label>
								<div class="col-sm-8">
									<select class="chosen-select" style="width:362px;height:30px;" tabindex="2" id="sayfaceid" name="sayfaceid">
		                              	<option value=""> -请选择---</option >
		                            </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" >谈心老师：</label>
								<div class="col-sm-8">
									<select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="teacherid" id="teacherid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach>
		                            </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label" >谈心时间：</label>
								<div class="col-sm-8">
									<input placeholder="谈心开始" class="form-control layer-date"
										id="createTimeadd" name="createTimeadd"> <input
										placeholder="谈心结束" class="form-control layer-date"
										id="saytimeadd" name="saytimeadd">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">谈心内容：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="谈心内容："
										style="resize: none" id="sayscon" name="sayscon"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">谈心问题：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="谈心问题："
										style="resize: none" id="sayproblem" name="sayproblem"></textarea>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">学生反馈：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="谈心问题："
										style="resize: none" id="sayback" name="sayback"></textarea>
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
			elem : '#createTimeadd',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			festival: true, //显示节日
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#saytimeadd',
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
			elem : '#createTime',
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
			elem : '#saytime',
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
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script type="text/javascript">
	    
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata){
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	sayfaceid :{
                    	required:true
                    },
                    teacherid :{
                    	required:true
                    },
                    sayscon :{
                    	required:true
                    },
                    sayproblem :{
                    	required:true
                    },
                    sayback:{
                    	required:true
                    }
	            },
	            messages: {
			            sayfaceid :{
		                	required:icon + "请选择学生名称"
		                },
		                teacherid :{
		                	required:icon + "请选择老师名称"
		                },
		                sayscon :{
		                	required:icon + "请输入谈心内容"
		                },
		                sayproblem :{
		                	required:icon + "请输入谈心问题"
		                },
		                sayback:{
		                	required:icon + "请输入学生反馈"
		                }
		            },
		            submitHandler:function(form) {
		        		alert("表单验证成功，不提交"+validator.form());
		        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
</script>
</body>
</html>