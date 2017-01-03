<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>奖惩记录表</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/js/timer.js"></script>

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
					$('#window_update #createTime ').val(rows.time1);
					$('#window_update #createTime2 ').val(rows.time2);
					$('#window_update #createTime3 ').val(rows.time3);
					$('#window_update #remark ').val(rows.remark);
				});
		
		
		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		//清空条件
		$("#clean").click(function() {
			$("#remark").val("");
			$("#start").val("");
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
				url : '${pageContext.request.contextPath}/sysSet/fattensel', //请求后台的URL（*）sreward/stureward、stuheart/sayheart
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
					field : 'time1',
					title : '上午第一时间'
				}, {
					field : 'time2',
					title : '上午第二时间'
				}, {
					field : 'time3',
					title : '下午下班时间'
				},  {
					field : 'status',
					title : '状态',
					align : 'center',
					formatter : function(value, row, index) {
						var stat = row.status;
						if(stat==1){
							return "启用";
						}else{
							return "禁用";
						}
					}
				},  {
					field : 'remark',
					title : '时段',
					align : 'center',
				} , {
					field : '',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<a onclick='play("+id+")'><i class='glyphicon glyphicon-send'>启用</i></a>";
					}
				} ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				remark :$('#formSearch #remark').val() ,
				createTime : $('#formSearch #start').val(),
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
	
	function play(id) {
		//alert(id);
		var url = "${pageContext.request.contextPath }/sysSet/qiyong/" + id;
		//alert(url);
		
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : rows.id,
			status:1
		//从选中的rows中获取id
		}, updatestatus, "text");
	}
	function updatestatus(data) {
		if (data > 0) {
			swal("启用成功！", "该时段已成功启用。", "success");
		} else {
			swal("启用失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#feedbackstart_Tab').bootstrapTable('refresh');
	}
	
	//新增学生，ajax提交
	function addStart() {
		if(!validateForm($("#addattensys"))){
			return;
		}
		
		
		var url = "${pageContext.request.contextPath }/sysSet/fatten/add";
		$.post(url, {
			time1 : $('#window_add #createTimeadd').val(),
			time2 : $('#window_add #createTimeadd2').val(),
			time3 : $('#window_add #createTimeadd3').val(),
			remark : $('#window_add #remark').val(),
			
		}, addStartHandle, "text");
		$("#window_add").modal('hide')
		$('#feedbackstart_Tab').bootstrapTable('refresh');
		$("#window_add #createTimeadd").val('');
		$("#window_add #createTimeadd2").val('');
		$("#window_add #createTimeadd3").val('');
	
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
	}

	//修改学生，ajax提交
	function updateStart() {
		
		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/sysSet/fatten/"+ id;
		$.post(url, {
			
			_method : 'PUT',//改成PUT提交
			time1 : $('#window_update #createTime').val(),
			time2 : $('#window_update #createTime2').val(),
			time3 : $('#window_update #createTime3').val(),
			remark : $('#window_update #remark').val(),
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
		var url = "${pageContext.request.contextPath }/sysSet/fatten/" + id;
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
</script>
<script type="text/javascript">
	
	
	
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
								<label class="col-sm-2 control-label">时段：</label>
								<div class="col-sm-3">
									<input class="form-control" type="text" id="remark">
								</div>
								
								<div class="col-sm-2" style="margin-left: 100px">
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
						<h4 class="modal-title">考勤设置修改表<span class="fa fa-clock-o"></span></h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dataForm"
							novalidate="novalidate">
							<div class="form-group">
							<input type="hidden" name="id" id="id" />
								<label class="col-sm-2 control-label" >上午时间：</label>
								<div class="col-sm-5">
									<input placeholder="上午第一时间" class="form-control layer-date"
										id="createTime" name="createTime"> 
								</div>
								<div class="col-sm-5">
									<input placeholder="上午第二时间" class="form-control layer-date"
										id="createTime2" name="createTime2"> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label" >下午时间：</label>
								<div class="col-sm-5">
			                        <input class="form-control" id="xw" />
			                            <button class="input-group-addon" onclick="showTimer('xw')">
			                                    <span class="fa fa-clock-o"></span>
			                            </button>
                        			</div>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label" >时段：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="描述说明："
										style="resize: none" id="remark"></textarea> 
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="updateStart()"
									class="btn btn-primary">修改</button>
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
						<h4 class="modal-title">考勤设置添加表<span class="fa fa-clock-o"></span></h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addattensys"
							novalidate="novalidate">
							<div class="form-group">
								<label class="col-sm-2 control-label" >上午时间：</label>
								<div class="col-sm-5">
									<input placeholder="上午第一时间" class="form-control layer-date"
										id="createTimeadd" name="createTimeadd"> 
								</div>
							
								<div class="col-sm-5">
									<input placeholder="上午第二时间" class="form-control layer-date"
										id="createTimeadd2" name="createTimeadd2"> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label" >下午时间：</label>
								<div class="col-sm-5">
									<input placeholder="下午下班时间" class="form-control layer-date"
										id="createTimeadd3" name="createTimeadd3"> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label" >时段：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="描述说明："
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
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>	

<script type="text/javascript">
//-----------------------------------
/* 	$('.clockpicker').clockpicker()
	 .find('input').change(function(){
	 	// TODO：时间改变
	     console.log(this.value);  
	 });
 */
</script>
<script type="text/javascript">
	//调用表单验证的方法，在addStudent()里调用，出入form对象
	//***input控件要设置name属性***
	function validateForm(formdata){
		var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
        var validator = formdata.validate({
            rules: {
            	   createTimeadd :{
                   	required:true
                   },
                   createTimeadd2 :{
                   	required:true
                   },
                   createTimeadd3 :{
                   	required:true
                   }
                   
            },
            messages: {
            	   createTimeadd :{
                   	required:icon + "请选择时间"
                   },
                   createTimeadd2 :{
                   	required:icon + "请选择时间"
                   },
                   createTimeadd3 :{
                   	required:icon + "请选择时间"
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
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		laydate(start);
		
		var start2 = {
			elem : '#createTimeadd2',
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
		laydate(start2);
		
		var start3 = {
			elem : '#createTimeadd3',
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
		laydate(start3);
		
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
		laydate(start);
		
	var start2 = {
			elem : '#createTime2',
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
		laydate(start2);
			
	var start3 = {
			elem : '#createTime3',
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
		laydate(start3);
				
	</script>
	
</body>
</html>