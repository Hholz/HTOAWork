<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>宿舍巡查</title>

	<jsp:include page="../styleInclude.jsp"></jsp:include>
	
	<script type="text/javascript">
		var rows = null;	
	
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			init();
			
			//初始化table
			var tableInit = new TableInit();
			tableInit.Init();
			//初始化按钮事件
			var buttonInit = new ButtonInit();
			buttonInit.Init();
			
			$("#btn_add").click(function(){
				//清空新增div中的数据
				$("#addPatrolHouse #mustarrive").val('');
				$("#addPatrolHouse #truearrive").val('');
				$("#addPatrolHouse #clean").val('');
				$("#addPatrolHouse #patroltime").val('');
				$("#save").attr("onclick","addPatrolHouse()");
			});
			
			$("#btn_edit").click(function(){
				if(rows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				$("#addPatrolHouse #id").val(rows.id);
				$("#addPatrolHouse #houseid").val(rows.houseid);
				$("#addPatrolHouse #mustarrive").val(rows.mustarrive);
				$("#addPatrolHouse #truearrive").val(rows.truearrive);
				$("#addPatrolHouse #clean").val(rows.clean);
				$("#addPatrolHouse #patroltime").val(rows.patroltime);
				$("#save").attr("onclick","updatePatrolHouse()");
				$('#addPatrolHouse').modal('show');
			});
			
			$("#btn_delete").click(function(){
				if(rows == null){
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
	                	deletePatrolHouse();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
			//查询
			$("#btn_query").click(function(){
				$('#tb_patrolHouse').bootstrapTable('refresh');
				rows = null;
			});
		});
		
		var TableInit = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_patrolHouse').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/Patrol/patrolHouseListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
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
					search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
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
						field : 'houseid',
						title : '宿舍',
						formatter : function(value, row, index) {
							var house = row.house;
							if(house == null){
								return "-";
							}else{
								return house.hoursename;
							}
						}
					}, {
						field : 'mustarrive',
						title : '应到人数'
					}, {
						field : 'truearrive',
						title : '实到人数'
					},{
						field : 'clean',
						title : '卫生情况'
					},{
						field : 'patrolteach',
						title : '巡查老师',
						formatter : function(value, row, index) {
							var emp = row.emp;
							if(emp == null){
								return "-";
							}else{
								return emp.empname;
							}
						}
					},{
						field : 'patroltime',
						title : '巡查时间',
						formatter : function(value, row, index) {
							return value.substring(0, 10);
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
					houseid : $("#txt_search_houseid").val(),
					patrolteach : $("#txt_search_patrolteach").val()
				};
				return temp;
			};
			return table;
		};
		
		var ButtonInit = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增
		function addPatrolHouse(){
			//用来判断表单是否验证通过
			if(!validateForm($("#patrolHouseForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/dailyWork/Patrol/addPatrolHouse";
			$.post(
				url,
				{
					houseid:$("#addPatrolHouse #houseid").val(),
					mustarrive:$("#addPatrolHouse #mustarrive").val(),
					truearrive:$("#addPatrolHouse #truearrive").val(),
					clean:$("#addPatrolHouse #clean").val(),
					patroltime:$("#addPatrolHouse #patroltime").val()
				},
				addPatrolHouseHandle,
				"text"
			);
			//关闭新增窗口
			$("#addPatrolHouse").modal('hide');
			//刷新数据
			$("#tb_patrolHouse").bootstrapTable('refresh');
		}
		function addPatrolHouseHandle(data){
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
		
		//修改
		function updatePatrolHouse(){
			var id = $("#addPatrolHouse #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/Patrol/patrolHouse/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					houseid:$("#addPatrolHouse #houseid").val(),
					mustarrive:$("#addPatrolHouse #mustarrive").val(),
					truearrive:$("#addPatrolHouse #truearrive").val(),
					clean:$("#addPatrolHouse #clean").val(),
					patroltime:$("#addPatrolHouse #patroltime").val()
				},
				
				updatePatrolHouseHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addPatrolHouse").modal('hide');
		}
		function updatePatrolHouseHandle(data){
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
			$('#tb_patrolHouse').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		//删除
		function deletePatrolHouse(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/Patrol/patrolHouse/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deletePatrolHouseHandle,
				"text"
			);
		}
		function deletePatrolHouseHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_patrolHouse').bootstrapTable('refresh');
		}
		
		//-----------------------二级联动-----------------------
		//宿舍
		function init(){
			var url ="${pageContext.request.contextPath}/dailyWork/Patrol/allFloor";
			$.post(
				url,
				{
					id:1
				},
				getFloor,
				"json"
			);
		}
		function getFloor(data){
			var td = "<option value=''>---</option>";
			$.each(data,function(i){
				td += "<option value='"+data[i].id+"'>";
				td += data[i].floorname;
				td += "</option>";
			});
			$("#txt_search_floor").html(td);//显示在div中
			houseChange();
		}
		function houseChange(){
			var id = $("#txt_search_floor").val();
			var url = "${pageContext.request.contextPath}/dailyWork/Patrol/allLayer/"+id;
			$.post(
				url,
				{
					id:id
				},
				getLayer,
				"json"
			);
		}
		function getLayer(data){
			var td = "<option value=''>---</option>";
			$.each(data,function(i){
				td += "<option value='"+data[i].id+"'>";
				td += data[i].layername;
				td += "</option>";
			});
			$("#txt_search_houseid").html(td);
		}
	</script>
</head>
<body>
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
									<label class="control-label col-sm-1">宿舍</label>
									<div class="col-sm-2">
										<select name="txt_search_floor" id="txt_search_floor" onchange="houseChange()" class="form-control">
		                                     <option>----</option>
		                                </select>
									</div>
									<div class="col-sm-2">
										<select name="txt_search_houseid" id="txt_search_houseid" class="form-control">
										
		                                </select>    
									</div>
									<label class="control-label col-sm-1" for="txt_search_patrolteach">巡查老师</label>
									<div class="col-sm-2">
										<select id="txt_search_patrolteach" class="form-control">
											<option value=""></option>
	                                        <c:forEach items="${emp}" var="emp">
	                                        	<option value="${emp.id }">${emp.empname }</option>
	                                        </c:forEach>
	                                    </select>       
									</div>
									<div class="col-sm-3">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
										<a href="${pageContext.request.contextPath}/dailyWork/Patrol/patrolHouseList">
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
	                        <h5>宿舍巡查</h5>
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
	                        <div id="toolbar" class="btn-group">
								<button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addPatrolHouse">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
								</button>
								<button id="btn_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
								</button>
								<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
								</button>
							</div>
							
							<div class="modal inmodal" id="addPatrolHouse" tabindex="-1" role="dialog" aria-hidden="true">
					            <div class="modal-dialog">
					                <div class="modal-content animated bounceInRight">
					                    <div class="modal-header">
					                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					                        </button>
					                        <h4 class="modal-title">添加宿舍巡查信息</h4>
					                        <small class="font-bold"></small>
					                     </div>
					                       <div class="modal-body">
					                       	<form class="form-horizontal m-t" id="patrolHouseForm" novalidate="novalidate">
												<input id="id" name="id" type="hidden">
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">宿舍：</label>
					                                <div class="col-sm-8">
					                                    <select id="houseid" name="houseid" class="form-control">
					                                        <c:forEach items="${house}" var="house">
					                                        	<option value="${house.id }">${house.hoursename }</option>
					                                        </c:forEach>
					                                    </select>
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">应到人数：</label>
					                                <div class="col-sm-8">
					                                    <input id="mustarrive" name="mustarrive" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">实到人数：</label>
					                                <div class="col-sm-8">
					                                    <input id="truearrive" name="truearrive" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">卫生情况：</label>
					                                <div class="col-sm-8">
					                                    <input id="clean" name="clean" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">巡查时间：</label>
					                                <div class="col-sm-8">
					                                	<input id="patroltime" name="patroltime" class="form-control layer-date">
					                                </div>
					                            </div>
					                            
											</form>
					                       </div>
					                       <div class="modal-footer">
					                           <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
											<button type="button" id="save" onclick="" class="btn btn-primary">保存</button>
					                       </div>
					                    </div>
					                </div>
					            </div>
							
							<!-- table代码就这些，用js构建表格 -->
							<table id="tb_patrolHouse"></table>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
	</div>
	
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	
	<script>
		//日期范围限制
		var patroltime = {
			elem : '#patroltime',
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
		laydate(patroltime);
	</script>
	
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
		            	mustarrive: {
		                	required:true,
		                	digits:true
	                    },
	                    truearrive: {
		                	required:true,
		                	digits:true
	                    },
		            	patroltime: {
		                	required:true
	                    }
		            	
		            },
		            messages: {
		            	mustarrive :{
	                    	required:icon + "请输入应到人数",
	                    	digits: icon + "请输入数字（整数）"
	                    },
	                    truearrive :{
	                    	required:icon + "请输入实际人数",
	                    	digits: icon + "请输入数字（整数）"
	                    },
		            	patroltime :{
	                    	required:icon + "巡查时间不能为空"
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