<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>调班申请</title>

	<jsp:include page="../styleInclude.jsp"></jsp:include>
	<script type="text/javascript">
		var swapdutyRows = null;
		
		//--------------------------------调班申请----------------------------
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			init();
			init2();
			
			//初始化table
			var tableSwapduty = new TableSwapduty();
			tableSwapduty.Init();
			//初始化button按钮事件
			var buttonSwapduty = new ButtonSwapduty();
			buttonSwapduty.Init();
			
			$("#btn_swapduty_add").click(function(){
				//清空新增div中的数据
				$("#addSwapduty #weeks1").val('');
				$("#addSwapduty #weekends1").val('');
				$("#addSwapduty #weeks2").val('');
				$("#addSwapduty #weekends2").val('');
				$("#addSwapduty #empid").val('');
				$("#addSwapduty #applyreason").val('');
				$("#btn_swapduty_save").attr("onclick","addSwapduty()");
			});
			
			$("#btn_swapduty_update").click(function(){
				if(swapdutyRows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				var applystatus = swapdutyRows.applystatus;
				if(applystatus == 1){
					alert("此申请已提交!  不能修改");
					return;
				}
				
				$("#addSwapduty #id").val(swapdutyRows.id);
				$("#addSwapduty #weeks1").val(swapdutyRows.weeks1);
				$("#addSwapduty #weekends1").val(swapdutyRows.weekends1);
				$("#addSwapduty #weeks2").val(swapdutyRows.weeks2);
				$("#addSwapduty #weekends2").val(swapdutyRows.weekends2);
				$("#addSwapduty #empid").val(swapdutyRows.empid);
				$("#addSwapduty #applyreason").val(swapdutyRows.applyreason);
				$('#addSwapduty').modal('show');
				$("#btn_swapduty_save").attr("onclick","updateSwapduty()");
			});
			
			$("#btn_swapduty_delete").click(function(){
				if(swapdutyRows == null){
					parent.layer.alert('请选择你要删除的数据！');
					return;
				}
				var applystatus = swapdutyRows.applystatus;
				if(applystatus == 1){
					alert("此申请已提交!  不能删除");
					return;
				}
				
				swal({
					title: "您确定要删除这条信息吗",
                    text: "删除后将无法恢复，请谨慎操作！",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "是的，我要删除！",
                    cancelButtonText: "让我再考虑一下…",
                    closeOnConfirm: false,
                    closeOnCancel: false
	            },
	            function (isConfirm) {
	                if (isConfirm) {
	                	//调用删除函数
	                	deleteSwapduty();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
			
			//查询
			$("#btn_query").click(function(){
				$("#tb_swapduty").bootstrapTable('refresh');
				swapdutyRows = null;
			});
		});
		
		var TableSwapduty = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_swapduty').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/Duty/swapdutyListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar_swapduty', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : table.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					//search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					searchOnEnterKey : true, //按回车搜索
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onCheck: function(row) {
		                if(swapdutyRows != null){
		                	swapdutyRows = null;
		                }
		                swapdutyRows = row;
		            },//单击事件
		            onUncheck: function(row) {
		                if(swapdutyRows != null){
		                	swapdutyRows = null;
		                }
		            },
					columns : [ {
						checkbox : true
					}, {
						field : 'dutyid',
						title : '申请人',
						formatter : function(value, row, index) {
							var emp = row.emp;
							if(emp == null){
								return "-";
							}else{
								return emp.empname;
							}
						}
					}, {
						field : 'weeks1',
						title : '原第几周'
					}, {
						field : 'weekends1',
						title : '原星期几'
					}, {
						field : 'weeks2',
						title : '调第几周'
					}, {
						field : 'weekends2',
						title : '调星期几'
					}, {
						field : 'empid',
						title : '与谁调换',
						formatter : function(value, row, index) {
							var emp1 = row.emp1;
							if(emp1 == null){
								return "-";
							}else{
								return emp1.empname;
							}
						}
					}, {
						field : 'applyreason',
						title : '申请理由'
					}, {
						field : 'applytime',
						title : '申请日期',
						formatter : function(value, row, index) {
							var applytime = row.applytime;
							if(applytime == null){
								return "-";
							}else{
								return value.substring(0, 10);
							}
						}
					}, {
						field : 'applystatus',
						title : '申请状态',
						formatter : function(value, row, index) {
							var applystatus = row.applystatus;
							var name = "";
							if(applystatus == 0){
								name = "未提交";
							}else if(applystatus == 1){
								name = "已提交";
							}
							return name;
						}
					}, {
						field : 'ApprovalStatus',
						title : '审批状态',
						formatter : function(value, row, index) {
							var approvalstatus = row.approvalstatus;
							var name = "";
							if(approvalstatus == 1){
								name = "正在等待领导审批";
							}else if(approvalstatus == 2){
								name = "领导未批准你的申请";
							}else if(approvalstatus == 3){
								name = "领导批准了你的申请";
							}
							return name;
						}
					},]
				});
			};
			//得到查询的参数
			table.queryParams = function(params){
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					id : $("input[placeholder=搜索]").val(),
					applystatus : $("#txt_search_applystatus").val()
				};
				return temp;
			};
			return table;
		};
		
		var ButtonSwapduty = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增申请
		function addSwapduty(){
			//用来判断表单是否验证通过
			if(!validateForm($("#swapdutyForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/dailyWork/Duty/addSwapduty";
			$.post(
				url,
				
				{
					weeks1:$("#addSwapduty #weeks1").val(),
					weekends1:$("#addSwapduty #weekends1").val(),
					weeks2:$("#addSwapduty #weeks2").val(),
					weekends2:$("#addSwapduty #weekends2").val(),
					empid:$("#addSwapduty #empid").val(),
					applyreason:$("#addSwapduty #applyreason").val()
				},
				addSwapdutyHandle,
				"text"
			);
			//关闭新增窗口
			$("#addSwapduty").modal('hide');
			//刷新数据
			$("#tb_swapduty").bootstrapTable('refresh');			
		}
		function addSwapdutyHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				swal({
					title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
		}
		//修改申请
		function updateSwapduty(){
			var id = $("#addSwapduty #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/Duty/swapduty/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					weeks1:$("#addSwapduty #weeks1").val(),
					weekends1:$("#addSwapduty #weekends1").val(),
					weeks2:$("#addSwapduty #weeks2").val(),
					weekends2:$("#addSwapduty #weekends2").val(),
					empid:$("#addSwapduty #empid").val(),
					applyreason:$("#addSwapduty #applyreason").val()
				},
				updateSwapdutyHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addSwapduty").modal('hide');
		}
		function updateSwapdutyHandle(data){
			//从后台返回出来的int类型数据，用来判断是否修改成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成修改操作",
                    type: "success"
                });
			}else{
				swal("修改失败", "请检查你输入的数据！", "error");
			}
			//刷新表格
			$('#tb_swapduty').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			swapdutyRows = null;
		}
		//删除申请
		function deleteSwapduty(){
			var id = swapdutyRows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/Duty/swapduty/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:swapdutyRows.id  //从选中的rows中获取id
				},
				deleteSwapdutyHandle,
				"text"
			);
		}
		function deleteSwapdutyHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_swapduty').bootstrapTable('refresh');
		}
		//提交申请
		function commit(){
			if(swapdutyRows == null){
				parent.layer.alert('请选择你要提交的数据！');
				return;
			}
			var applystatus = swapdutyRows.applystatus;
			if(applystatus == 1){
				alert("此申请已提交");
				return;
			}
			
			var id = swapdutyRows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/Duty/commitduty";
			$.post(
				url,
				{
					id : swapdutyRows.id
				}, 
				commitHandle, 
				"text"
			);
		}
		function commitHandle(data) {
			if (data > 0) {
				swal("提交成功！", "正在等待上级部门的审批。", "success");
			} else {
				swal("提交失败", "服务器繁忙！", "error");
			}
			rows = null;
			//刷新表格
			$('#tb_swapduty').bootstrapTable('refresh');
		}
		
		//---------------------二级联动----------------------
		function init(){
			var url = "${pageContext.request.contextPath}/dailyWork/Duty/allWeeks";
			$.post(
				url,
				{
					weeks1:''
				},
				getWeeks,
				"json"
			);
		}
		function getWeeks(data){
			var td = "<option value=''>---请选择---</option>";
			$.each(data,function(i){
				td += "<option value='"+data[i]+"'>";
				td += data[i];
				td += "</option>";
			});
			$("#weeks1").html(td);//显示在div
			weeksChange();
			
		}
		function weeksChange(){
			var weeks1 = $("#weeks1").val();
			var url = "${pageContext.request.contextPath}/dailyWork/Duty/allWeekends";
			$.post(
				url,
				{
					weeks1:weeks1
				},
				getWeekends,
				"json"
			);
		}
		function getWeekends(data){
			var td = "<option value=''>---</option>"
			$.each(data,function(i){
				td += "<option value='"+data[i]+"'>";
				td += data[i];
				td += "</option>";
			});
			$("#weekends1").html(td);//显示在div中
		}
		//--------------------------------三级联动-----------------------------
		function init2(){
			var url = "${pageContext.request.contextPath}/dailyWork/Duty/allWeeks2";
			$.post(
				url,
				{
					weeks2:''
				},
				getWeeks2,
				"json"
			);
		}
		function getWeeks2(data){
			var td = "<option value=''>---请选择---</option>";
			$.each(data,function(i){
				td += "<option value='"+data[i]+"'>";
				td += data[i];
				td += "</option>";
			});
			$("#weeks2").html(td);//显示在div
			weeksChange2();
		}
		function weeksChange2(){
			var weeks2 = $("#weeks2").val();
			var url = "${pageContext.request.contextPath}/dailyWork/Duty/allWeekends2";
			$.post(
				url,
				{
					weeks2:weeks2
				},
				getWeekends2,
				"json"
			);
		}
		function getWeekends2(data){
			var td = "<option value=''>---</option>"
			$.each(data,function(i){
				td += "<option value='"+data[i]+"'>";
				td += data[i];
				td += "</option>";
			});
			$("#weekends2").html(td);//显示在div中
			dutyidChange();
		}
		function dutyidChange(){
			var weeks2 = $("#weeks2").val();
			var weekends2 = $("#weekends2").val();
			var url = "${pageContext.request.contextPath}/dailyWork/Duty/dutyid";
			$.post(
				url,
				{
					weeks2:weeks2,
					weekends2:weekends2
				},
				getDutyid,
				"json"
			);
		}
		function getDutyid(data){
			var td = "<option value=''>---</option>"
			$.each(data,function(i){
				td += "<option value='"+data[i].id+"'>";
				td += data[i].empname;
				td += "</option>";
			});
			$("#empid").html(td);//显示在div中
		}
	</script>
	
</head>
<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
		<div class="panel panel-default">
			<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins" style="margin-bottom: 0px">
	                    <div class="ibox-title">
	                        <h5>查询条件</h5>
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content">
	                        <form id="formSearch" class="form-horizontal">
								<div class="form-group" style="margin-top: 15px">
									<label class="control-label col-sm-1" for="txt_search_applystatus">申请状态</label>       
									<div class="col-sm-2">
										<select id="txt_search_applystatus" class="form-control">
											<option value="">---</option>
	                                        <option value="1">已提交</option>
	                                        <option value="0">未提交</option>
	                                    </select>      
									</div>
									<div class="col-sm-3">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
										<a href="${pageContext.request.contextPath}/dailyWork/Duty/swapdutyList">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清空</button></a>
									</div>
								</div>
							</form>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
		<div class="panel panel-default">
			<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>值班信息</h5>
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content">
	                        <div id="toolbar_swapduty" class="btn-group">
								<button id="btn_swapduty_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addSwapduty">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
								</button>
								<button id="btn_swapduty_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
								</button>
								<button id="btn_swapduty_delete" type="button" class="btn btn-w-m btn-danger">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
								</button>
								<button id="btn_money" type="button" class="btn btn-success btn-outline" onclick="commit()">
									<span class="glyphicon glyphicon-send" aria-hidden="true"></span>提    交
								</button>
							</div>
							
							<div class="modal inmodal" id="addSwapduty" tabindex="-1" role="dialog" aria-hidden="true">
					            <div class="modal-dialog">
					                <div class="modal-content animated bounceInRight">
					                    <div class="modal-header">
					                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					                        </button>
					                        <h4 class="modal-title">添加申请</h4>
					                        <small class="font-bold"></small>
					                     </div>
					                       <div class="modal-body">
					                       	<form class="form-horizontal m-t" id="swapdutyForm" novalidate="novalidate">
												<input id="id" name="id" type="hidden">
					                            <div class="form-group">
					                               <label class="col-sm-3 control-label">原第几周：</label>
													<div class="col-sm-8">
														<select name="weeks1" id="weeks1" onchange="weeksChange()" class="form-control">
						                                     <option>----</option>
						                                </select>
													</div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">原星期几：</label>
					                                <div class="col-sm-8">
														<select name="weekends1" id="weekends1" class="form-control">
														
						                                </select>    
													</div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">调第几周：</label>
					                                <div class="col-sm-8">
						                                <select name="weeks2" id="weeks2" onchange="weeksChange2()" class="form-control">
						                                     <option>----</option>
						                                </select>
					                                 </div>  
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">调星期几：</label>
					                                <div class="col-sm-8">
					                                    <select name="weekends2" id="weekends2" onchange="dutyidChange()" class="form-control">
														
						                                </select> 
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">与谁调换：</label>
					                                <div class="col-sm-8">
														<select name="empid" id="empid" class="form-control">
														
						                                </select>
													</div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">申请理由：</label>
					                                <div class="col-sm-8">
					                                    <input id="applyreason" name="applyreason" type="text" class="form-control">
					                                </div>
					                            </div>
											</form>
					                       </div>
					                       <div class="modal-footer">
					                           <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
											<button type="button" id="btn_swapduty_save" onclick="" class="btn btn-primary">保存</button>
					                       </div>
					                    </div>
					                </div>
					            </div>
							
							<!-- table代码就这些，用js构建表格 -->
							<table id="tb_swapduty"></table>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
	</div>
	
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	
	<script type="text/javascript">
		//已经把文档下到本地，访问地址：http://localhost:8080/HTOAWork/Demo/validateDemo/jQueryValidate.html
		//详情参考：http://www.runoob.com/jquery/jquery-plugin-validate.html
		//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
	    $.validator.setDefaults({
	        highlight: function (element) {
	            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	        },
	        success: function (element) {
	            element.closest('.form-group').removeClass('has-error').addClass('has-success');
	        },
	        errorElement: "span",
	        errorPlacement: function (error, element) {
	            if (element.is(":radio") || element.is(":checkbox")) {
	                error.appendTo(element.parent().parent().parent());
	            } else {
	                error.appendTo(element.parent());
	            }
	        },
	        errorClass: "help-block m-b-none",
	        validClass: "help-block m-b-none"
	    });
	
	  	//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	weeks1: "required",
	            	weekends1: "required",
	            	weeks2: "required",
	            	weekends2: "required",
	            	empid: "required",
	            	applyreason: "required"
	            },
	            messages: {
	            	weeks1: icon + "请输入原第几周",
	            	weekends1: icon + "请选择原星期几",
	            	weeks2: icon + "请输入调第几周",
	            	weekends2: icon + "请选择调星期几",
	            	empid: icon + "请选择与谁调换",
	            	applyreason: icon + "请输入申请理由"
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