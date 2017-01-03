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
		//$("#btn_edit").click(
				
				
				//);
		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		//清空条件
		$("#clean").click(function() {
			$("#holidaystat").val("");
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
				url : '${pageContext.request.contextPath}/sysSet/holidaylist', //请求后台的URL（*）
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
				pageSize : 10, //每页的记录行数（*）
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
					field : 'holidaytime',
					title : '日期',
					align : 'center',
					formatter : function(value, row, index) {
						var holidaytime = row.holidaytime;
							return holidaytime.substring(0,10);
					}
					
				}, {
					field : 'holidaystat',
					title : '假期状态',
					align : 'center',
					formatter : function(value, row, index) {
						var stat = row.holidaystat;
						if(stat==1){
							return "<span class='btn btn-success btn-xs dropdown-toggle'>上班</span>";
						}else{
							return "<span class='btn btn-primary btn-xs dropdown-toggle'>放假</span>";
						}
					}
				}, {
					field : 'updateholiday',
					title : '修改时间',
					align : 'center',
					formatter : function(value, row, index) {
						var updateholiday = row.updateholiday;
						    return updateholiday.substring(0,19);
					}
				}, {
					field : 'remark',
					title : '备注',
					align : 'center',
				}, {
					field : '',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<button onclick='updatedit()' class='btn btn-success btn-outline' data-toggle='modal' data-target='#window_update'>修改</button>";
					}
				} ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				holidaystat : $('#formSearch #holidaystat').val() ,
				holidaytime : $('#formSearch #start').val(),
				endtime : $('#formSearch #end').val()
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
	
	//新增判断
	function btnadd() {
		var day =  $("#panduan #day").val();
		//alert("==="+day);
		if(day==1){
			swal("添加失败", "已经有了上个月的数据无需添加！", "error");
		}else if(day==2){
			updatelist();
		}else{
			addStart();
		}
	};
	
	//新增学生，ajax提交
	function addStart() {		
		
		var url = "${pageContext.request.contextPath }/sysSet/addtime";
		$.post(url, {
			status : 1
		}, addStartHandle, "text");
	}

	function addStartHandle(data) {
		if (data > 0) {
			attenadd();
			$('#feedbackstart_Tab').bootstrapTable('refresh');
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
	}
	
	//新增学生，ajax提交2
	function updatelist() {				
		var url = "${pageContext.request.contextPath }/sysSet/updatelist";
		$.post(url, {
			status : 1
		}, addStartHandle2, "text");
	}

	function addStartHandle2(data) {
		if (data > 0) {
			attenadd();
			$('#feedbackstart_Tab').bootstrapTable('refresh');
		} else {
			swal("添加失败", "请检查你输入的数据..！", "error");
		}
	}
	
	
	//考勤模板表数据生成
	function attenadd(){
		
		var url ="${pageContext.request.contextPath}/dailyWork/kaoqinadd";
		$.post(url,{status:1},attenstat, "text");
	}
	function attenstat(data){
		//alert(data);
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成考勤模板生成！",
				type : "success"
			});
		} else {
			swal("失败", "请检查你的数据！", "error");
		}
	}
	

	//修改学生，ajax提交
	function updateStart() {
		
		if(!validateForm($("#updateholidays"))){
			return;
		}
		
		var id = $("#window_update #id").val();
		
		var url = "${pageContext.request.contextPath }/sysSet/update/"+ id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			holidaystat : $('#window_update #holidaystats').val(),
			remark : $('#window_update #remark').val()
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
		var url = "${pageContext.request.contextPath }/sysSet/delete/" + id;
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
	
	function updatedit() {
		
		if(rows==null || rows.id==null){
			swal("警告！", "请选择你要修改的ID", "error");
			//把内容放到更新的列表
			$('#window_update').modal();
		}
		
		//把内容放到更新的列表
		$('#window_update #id ').val(rows.id);
		$('#window_update #holidaystats ').val(rows.holidaystat);
		$('#window_update #remark ').val(rows.remark);
		$('#window_update #createTime ').val(
				rows.holidaytime.substring(0, 19)).attr("disabled", true);
	};
	
	
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
								<label class="control-label col-sm-1" style="margin-left: 20px">时间：</label>
								<div class="col-sm-2">
									<input placeholder="开始日期" class="form-control layer-date"
										id="start" name="start">
								</div>
								<div class="col-sm-2">
									<input placeholder="结束日期" class="form-control layer-date"
										id="end" name="end">
								</div>
								<label class="control-label col-sm-1" >状态：</label>
								<div class="col-sm-1">
									<select class="chosen-select" style="width:120px;height:33px;" tabindex="2" name="holidaystat" id="holidaystat">
		                              	<option value=""> --请选择--</option >
		                              	<option value="1">上班</option >
		                              	<option value="0">放假</option >
		                            </select>
								</div>
								<div class="col-sm-3" style="margin-left: 100px">
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
				data-toggle="modal" onclick="btnadd()" ><!-- onclick="addStart()" -->
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>生成上月日历模板
			</button>
		</div>
		 
		<div id="panduan">
			<div>
				<input type="hidden" name="day" id="day" value="${day}"/>
			</div>
		</div>

		<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">节假日时间修改表</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="updateholidays" name="updateholidays"
							novalidate="novalidate">
							<input type="hidden" name="id" id="id" />  
							
							<div class="form-group">
								<input type="hidden" name="id" id="id" />  
								<label class="col-sm-3 control-label">状态：</label>
								<div class="col-sm-8">
									<select class="chosen-select" style="width:240px;height:35px;" tabindex="2" name="holidaystats" id="holidaystats">
		                              	<option value=""> -请选择---</option >
		                              	<option value="1">上班</option >
		                              	<option value="0">放假</option >
		                            </select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-8">
									<textarea class="form-control" placeholder="备注："
										style="resize: none" id="remark"></textarea>
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
			elem : '#createTime',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		
		laydate(start);
		
		
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
	            	holidaystat:{
                    	required:true
                    }
	            },
	            messages: {
	            	holidaystat:{
		                	required:icon + "请选择好状态"
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