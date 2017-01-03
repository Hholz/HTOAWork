<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>值班模板</title>

	<jsp:include page="../styleInclude.jsp"></jsp:include>
	
	<script type="text/javascript">
		var dutymodelRows = null;
		var modeldetailRows = null;
		
		function test(id){
	    	$("#modeldetailId").val(id);
	    	//让模板明细表刷新
			$('#tb_modeldetail').bootstrapTable('refresh');
	    }
		//--------------------------------------值班模板--------------------------------------------
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableDutymodel = new TableDutymodel();
			tableDutymodel.Init();
			//初始化button按钮事件
			var buttonDutymodel = new ButtonDutymodel();
			buttonDutymodel.Init();
			
			$("#btn_dutymodel_add").click(function(){
				//清空新增div中的数据
				$("#addDutymodel #modelname").val('');
				$("#addDutymodel #remark").val('');
				$("#btn_dutymodel_save").attr("onclick","addDutymodel()");
			});
			
			$("#btn_dutymodel_update").click(function(){
				if(dutymodelRows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				
				$("#addDutymodel #id").val(dutymodelRows.id);
				$("#addDutymodel #modelname").val(dutymodelRows.modelname);
				$("#addDutymodel #remark").val(dutymodelRows.remark);
				$('#addDutymodel').modal('show');
				$("#btn_dutymodel_save").attr("onclick","updateDutymodel()");
				
			});
			
			$("#btn_dutymodel_delete").click(function(){
				if(dutymodelRows == null){
					parent.layer.alert('请选择你要删除的数据！');
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
	                	deleteDutymodel();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
		});
		
		var TableDutymodel = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_dutymodel').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/DutyModel/dutymodelListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar_dutymodel', //工具按钮用哪个容器
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
					//showColumns : true, //是否显示所有的列
					//showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					//showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onCheck: function(row) {
		                if(dutymodelRows != null){
		                	dutymodelRows = null;
		                }
		                dutymodelRows = row;
		            },//单击事件
		            onUncheck: function(row) {
		                if(dutymodelRows != null){
		                	dutymodelRows = null;
		                }
		            },
					columns : [ {
						checkbox : true
					}, {
						field : 'modelname',
						title : '模板名称'
					}, {
						field : 'remark',
						title : '描述'
					},{
						field : 'status',
						title : '状态',
						align : 'center',
						formatter : function(value,row,index){
							var status = row.status;
							if(status == 1){
								return "<span class='label label-primary'>启用</span>";
							}else if(status == 2){
								return "<a onclick='using("+row.id+")'><span class='label label-danger'>停用</span></a>";
							}
						}
						
					}, {
						field : 'id',
						title : '详情',
						align : 'center',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a onclick='test("+id+")'><i class='fa fa-file-text-o'>详情</i></a>";
						}
					},]
				});
			};
			//得到查询的参数
			table.queryParams = function(params){
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset //页码
				};
				return temp;
			};
			return table;
		};
		
		var ButtonDutymodel = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		function using(id){
			parent.layer.confirm('确认启用这条值班模板？', {
			    btn: ['是的','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				//成功
				using2(id);
			}, function(){
				
			});
		}
		function using2(id){
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/using/"+id;
			$.post(
				url,
				{},
				function(data){
					if(data>0){
						$("#tb_dutymodel").bootstrapTable('refresh');
						parent.layer.alert("启用成功");
					}
				},
				"text"
			);
		}
		function addDutymodel(){
			//用来判断表单是否验证通过
			if(!validateForm($("#modelForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/addDutymodel";
			$.post(
				url,
				
				{
					modelname:$("#addDutymodel #modelname").val(),
					remark:$("#addDutymodel #remark").val()
				},
				addDutymodelHandle,
				"text"
			);
			//关闭新增窗口
			$("#addDutymodel").modal('hide');
		}
		function addDutymodelHandle(data){
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
			$("#tb_dutymodel").bootstrapTable('refresh');
		}
		//修改值班模板
		function updateDutymodel(){
			var id = $("#addDutymodel #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/dutymodel/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					modelname:$("#addDutymodel #modelname").val(),
					remark:$("#addDutymodel #remark").val()
				},
				
				updateDutymodelHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addDutymodel").modal('hide');
		}
		function updateDutymodelHandle(data){
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
			$('#tb_dutymodel').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			dutymodelRows = null;
		}
		//删除值班模板
		function deleteDutymodel(){
			var id = dutymodelRows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/dutymodel/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:dutymodelRows.id  //从选中的rows中获取id
				},
				deleteDutymodelHandle,
				"text"
			);
		}
		function deleteDutymodelHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_dutymodel').bootstrapTable('refresh');
		}
		
		//-----------------------------------------值班模板明细--------------------------------------
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableModeldetail = new TableModeldetail();
			tableModeldetail.Init();
			//初始化button按钮事件
			var buttonModeldetail = new ButtonModeldetail();
			buttonModeldetail.Init();
			
			$("#btn_modeldetail_add").click(function(){
				//清空新增div中的数据
				$("#addModeldetail #weekends").val('');
				$("#addModeldetail #empName").val('');
				$("#addModeldetail #empScope").val('');
				$("#addModeldetail #empType").val('');
				$("#btn_modeldetail_save").attr("onclick","addModeldetail()");
			});
			
			$("#btn_modeldetail_update").click(function(){
				if(modeldetailRows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				$("#addModeldetail #id").val(modeldetailRows.id);
				$("#addModeldetail #modelid").val(modeldetailRows.modelid);
				$("#addModeldetail #weekends").val(modeldetailRows.weekends);
				$("#addModeldetail #empType").val(modeldetailRows.empType);
				$("#addModeldetail #empName").val(modeldetailRows.empName);
				$("#addModeldetail #empScope").val(modeldetailRows.empScope);
				$('#addModeldetail').modal('show');
				$("#btn_modeldetail_save").attr("onclick","updateModeldetail()");
				
			});
			
			$("#btn_modeldetail_delete").click(function(){
				if(modeldetailRows == null){
					parent.layer.alert('请选择你要删除的数据！');
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
	                	deleteModeldetail();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
			//查询
			$("#btn_query").click(function(){
				$('#tb_notice').bootstrapTable('refresh');
				modeldetailRows = null;
			});
			$("#btn_clean").click(function(){
				noticename : $("#txt_search_noticename").val('');
				title : $("#txt_search_title").val('');
				$('#tb_notice').bootstrapTable('refresh');
				modeldetailRows = null;
			});
		});
		
		var TableModeldetail = function(){
			var table2 = new Object();
			//初始化table
			table2.Init = function(){
				$('#tb_modeldetail').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/DutyModel/modeldetailListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar_modeldetail', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : table2.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 7, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					//search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					searchOnEnterKey : true, //按回车搜索
					//showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					//showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onCheck: function(row) {
		                if(modeldetailRows != null){
		                	modeldetailRows = null;
		                }
		                modeldetailRows = row;
		            },//单击事件
		            onUncheck: function(row) {
		                if(modeldetailRows != null){
		                	modeldetailRows = null;
		                }
		            },
					columns : [ {
						checkbox : true
					}, {
						field : 'modelid',
						title : '值班模板',
						formatter : function(value, row, index) {
							var dutymodel = row.dutymodel;
							if(dutymodel == null){
								return "-";
							}else{
								return dutymodel.modelname;
							}
						}
					}, {
						field : 'weekends',
						title : '星期几'
					}, {
						field : 'empType',
						title : '类别',
						formatter : function(value, row, index){
							var type = row.empType;
							if(type == 1){
								return "值班老师";
							}else if(type == 2){
								return "值班班主任";
							}else if(type == 3){
								return "总值班";
							}
						}
					},{
						field : 'empName',
						title : '值班人',
					},{
						field : 'empScope',
						title : '值班范围',
					}, ]
				});
			};
			//得到查询的参数
			table2.queryParams = function(params){
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					modelid : $("#modeldetailId").val()
				};
				return temp;
			};
			return table2;
		};
		
		var ButtonModeldetail = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增值班模板明细
		function addModeldetail(){
			//用来判断表单是否验证通过
			if(!validateForm($("#modeldetailForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/addModeldetail";
			$.post(
				url,
				
				{
					modelid:$("#addModeldetail #modelid").val(),
					weekends:$("#addModeldetail #weekends").val(),
					empName:$("#addModeldetail #empName").val(),
					empPhone:$("#addModeldetail #empName").find("option:selected").attr("phone"),
					empType:$("#addModeldetail #empType").val(),
					empScope:$("#addModeldetail #empScope").val()
				},
				addModeldetailHandle,
				"text"
			);
			//关闭新增窗口
			$("#addModeldetail").modal('hide');
			//刷新数据
			$("#tb_modeldetail").bootstrapTable('refresh');
		}
		function addModeldetailHandle(data){
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
		//修改值班模板明细
		function updateModeldetail(){
			var id = $("#addModeldetail #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/modeldetail/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					modelid:$("#addModeldetail #modelid").val(),
					weekends:$("#addModeldetail #weekends").val(),
					empName:$("#addModeldetail #empName").val(),
					empPhone:$("#addModeldetail #empName").find("option:selected").attr("phone"),
					empType:$("#addModeldetail #empType").val(),
					empScope:$("#addModeldetail #empScope").val()
				},
				
				updateModeldetailHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addModeldetail").modal('hide');
		}
		function updateModeldetailHandle(data){
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
			$('#tb_modeldetail').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			modeldetailRows = null;
		}
		//删除值班模板明细
		function deleteModeldetail(){
			var id = modeldetailRows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/DutyModel/modeldetail/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:modeldetailRows.id  //从选中的rows中获取id
				},
				deleteModeldetailHandle,
				"text"
			);
		}
		function deleteModeldetailHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_modeldetail').bootstrapTable('refresh');
		}
	</script>
</head>
<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
     	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><span class="glyphicon glyphicon-question-sign"></span>  帮助</h5>
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
                        <p>点击模板表详情,详细表显示所有打分项</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>值班模板表</h5>
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
                    	<div id="toolbar_dutymodel" class="btn-group">
							<button id="btn_dutymodel_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-toggle="modal" data-target="#addDutymodel">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_dutymodel_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_dutymodel_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_dutymodel"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>值班模板明细表</h5>
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
                    	<div id="toolbar_modeldetail" class="btn-group">
							<button id="btn_modeldetail_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addModeldetail">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_modeldetail_update" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_modeldetail_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_modeldetail"></table>
                    </div>
                </div>
            </div>
        </div>
     </div>
     <!--值班模板的弹窗  -->
		<div class="modal inmodal" id="addDutymodel" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">值班模板</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="modelForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模板名称：</label>
                                <div class="col-sm-8">
                                    <input id="modelname" name="modelname" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模板描述：</label>
                                <div class="col-sm-8">
                                    <input id="remark" name="remark" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_dutymodel_save" onclick="" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!--值班模板明细的弹窗  -->
		<div class="modal inmodal" id="addModeldetail" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<input type="hidden" id="modeldetailId"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">值班模板明细</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="modeldetailForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模板名称：</label>
                                <div class="col-sm-8">
                                    <select name="modelid" id="modelid" class="form-control">
	                                	<c:forEach items="${model}" var="model">
	                                        <option value="${model.id}">${model.modelname}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">星期几：</label>
                                <div class="col-sm-8">
	                                <select id="weekends" name="weekends" class="form-control">
	                                   	<option value="">---未选择---</option>
	                                   	<option value="周1">周一</option>
	                                   	<option value="周2">周二</option>
	                                   	<option value="周3">周三</option>
	                                   	<option value="周4">周四</option>
	                                   	<option value="周5">周五</option>
	                                   	<option value="周6">周六</option>
	                                   	<option value="周7">周日</option>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">类型：</label>
                                <div class="col-sm-8">
	                                <select id="empType" name="empType" class="form-control">
	                                   <option value="">---未选择---</option>
	                                   <option value="1">值班老师</option>
	                                   <option value="2">值班班主任</option>
	                                   <option value="3">总值班</option>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">值班人：</label>
                                <div class="col-sm-8">
	                                <select id="empName" name="empName" class="form-control">
	                                   <option value="">---未选择---</option>
	                                   <option value="无(不用值班)">无(不用值班)</option>
	                                   <c:forEach items="${emp}" var="emp">
	                                   	<option value="${emp.empname }" phone="${emp.phone }">${emp.empname }</option>
	                                   </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">值班范围：</label>
                                <div class="col-sm-8">
	                                <select id="empScope" name="empScope" class="form-control">
	                                   <option value="">---未选择---</option>
	                                   <option value="全部班级">全部班级</option>
		                               <c:forEach items="${fallList}" var="fall">
		                                   <option value="${fall.level}班级">${fall.level}</option >
		                               </c:forEach> 
	                                </select>
	                            </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_modeldetail_save" onclick="" class="btn btn-primary">保存</button>
                            </div>
                        </form>
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
	            	modelname: "required",
	            	weekends: "required",
	            	dutyid: "required"
	            },
	            messages: {
	            	modelname: icon + "请输入模板名称",
	            	weekends: icon + "请选择星期几",
	            	dutyid: icon + "请选择值班人"
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