<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>员工工作经历</title>
    <jsp:include page="../styleInclude.jsp"></jsp:include>
    
    <script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var rows = null;
	    $(function () {
	 	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//新增按钮事件*************************
			//修改按钮事件
			$("#btn_edit").click(function(){
				//把内容放到更新的列表
				if(rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_update #id").val(rows.id);
	            $("#window_update #empid").val(rows.empid);
	            $("#window_update #companyname").val(rows.companyname);
	            $("#window_update #degree").val(rows.degree);
	            $("#window_update #startdate").val(rows.startdate);
	            $("#window_update #enddate").val(rows.enddate);
	            $("#window_update #reason").val(rows.reason);
	            $("#window_update #worksRemark").val(rows.worksRemark);
	            $('#window_update').modal('show');
	        });
			//删除按钮事件
			//*************************************************************************按钮事件
			$('#btn_delete').click(function () {
				if(rows==null){
					parent.layer.alert('请选你要删除的数据！');
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
	                	deleteStudent();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
	        });
			//条件查询click事件注册
			$("#query").click(function() {
				$("#tb_departments").bootstrapTable('refresh');
			});
		});

	    var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_departments').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/works/0', //请求后台的URL（*）
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
		            },//单击row事件
					columns : [ {
						checkbox : true
					}, {
						field : 'id',
						title : 'ID',
						visible:false
					}, {
						field : 'emp.empname',
						title : '员工'
					}, {
						field : 'companyname',
						title : '公司名称'
					}, {
						field : 'degree',
						title : '岗位'
					}, {
						field : 'startdate',
						title : '入职时间'
					}, {
						field : 'enddate',
						title : '离职时间'
					},{
						field : 'reason',
						title : '离职原因'
					},{
						field : 'worksRemark',
						title : '说明'
					},]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					empid : $("#find_empid").val(),
					companyname : $("#find_companyname").val(),
					degree : $("#find_degree").val(),
					startdate : $("#find_startdate").val(),
					enddate : $("#find_enddate").val()
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
		
		//新增，ajax提交
		function addStudent(){
			if(!validateForm($("#addForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/works/add";
			$.post(
				url,
				{	
					_method:'PUT',
					empid:$("#window_add #empid").val(),
					companyname:$("#window_add #companyname").val(),
					degree:$("#window_add #degree").val(),
					startdate:$("#window_add #startdate_add").val(),
					enddate:$("#window_add #enddate_add").val(),
					reason:$("#window_add #reason").val(),
					worksRemark:$("#window_add #worksRemark").val()
				},
				addStudentHandle,
				"text"
			);	
			
			//用来关闭新增窗口***********
			$("#window_add").modal('hide');
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
			//清空新增div中的数据
            $("#window_add #companyname").val('');
            $("#window_add #degree").val('');
            $("#window_add #startdate_add").val('');
            $("#window_add #enddate_add").val('');
            $("#window_add #reason").val('');
            $("#window_add #worksRemark").val('');
		}
		function addStudentHandle(data){
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
			$('#tb_departments').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		//修改，ajax提交,PUT
		function updateStudent(){
			if(!validateForm($("#updateForm"))){
				return;
			}
			var id =$("#window_update #id").val(); 
			var url = "${pageContext.request.contextPath }/dailyWork/works/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					empid:$("#window_update #empid").val(),
					companyname:$("#window_update #companyname").val(),
					degree:$("#window_update #degree").val(),
					startdate:$("#window_update #startdate").val(),
					enddate:$("#window_update #enddate").val(),
					reason:$("#window_update #reason").val(),
					worksRemark:$("#window_update #worksRemark").val()
				},
				updateStudentHandle,
				"text"
			);	
			//用来关闭修改窗口***********
			$("#window_update").modal('hide')
		}
		function updateStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
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
			$('#tb_departments').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		
		//***************************************************************************************删除
		//删除，ajax提交,DELETE
		function deleteStudent(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/works/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteStudentHandle,
				"text"
			);	
		}
		function deleteStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_departments').bootstrapTable('refresh');
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
                                <label class="col-sm-1 control-label">员工：</label>
                                <div class="col-sm-3">
	                                <select id="find_empid" class="form-control">
	                                	<option value="">-----------------</option>
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                           		<label class="col-sm-1 control-label">公司：</label>
	                            <div class="col-sm-3">
	                                <input id="find_companyname" type="text" required class="form-control">
	                            </div>
                                <label class="col-sm-1 control-label">岗位：</label>
                                <div class="col-sm-3">
                                    <input id="find_degree" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label">入职时间:</label>
                                <div class="col-sm-3">
									<input placeholder="入职时间" class="form-control layer-date"
										id="find_startdate">
								</div>
								<label class="col-sm-1 control-label">离职时间:</label>
								<div class="col-sm-3">
									<input placeholder="离职时间" class="form-control layer-date"
										id="find_enddate">
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">查询</button>
								</div>
                            </div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal" >
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>
		
		<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改员工工作经历</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="updateForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid" name="empid" class="form-control" required disabled="disabled">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">*公司：</label>
	                            <div class="col-sm-8">
	                               <input id="companyname" name="companyname" type="text" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*岗位：</label>
                                <div class="col-sm-8">
                                    <input id="degree" name="degree" type="text" required class="form-control">
                                </div>
                            </div>
								<div class="form-group">
	                                <label class="col-sm-3 control-label">*入职时间：</label>
	                                <div class="col-sm-8">
										<input placeholder="入职时间" class="form-control layer-date"
											id="startdate" name="startdate">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">*离职时间：</label>
									<div class="col-sm-8">
										<input placeholder="离职时间" class="form-control layer-date"
											id="enddate" name="enddate">
									</div>
								</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*离职原因：</label>
                                <div class="col-sm-8">
                                	<input id="reason" name="reason" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                	<input id="worksRemark" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="updateStudent()" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		
		<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加员工工作经历</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addForm" novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid" name="empid" required class="form-control">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">*公司：</label>
	                            <div class="col-sm-8">
	                               <input id="companyname" name="companyname" type="text" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*岗位：</label>
                                <div class="col-sm-8">
                                    <input id="degree" name="degree" type="text" required class="form-control">
                                </div>
                            </div>
								<div class="form-group">
	                                <label class="col-sm-3 control-label">*入职时间：</label>
	                                <div class="col-sm-8">
										<input placeholder="入职时间" class="form-control layer-date"
											id="startdate_add" name="startdate">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">*离职时间：</label>
									<div class="col-sm-8">
										<input placeholder="离职时间" class="form-control layer-date"
											id="enddate_add" name="enddate">
									</div>
								</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*离职原因：</label>
                                <div class="col-sm-8">
                                	<input id="reason" name='reason' type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                	<input id="worksRemark" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="addStudent()" class="btn btn-primary">保存</button>
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
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script>
		//日期范围限制
		var start = {
			elem : '#find_startdate',
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
			elem : '#find_enddate',
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
			elem : '#startdate',
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
			elem : '#enddate',
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
	        highlight: function (element) {
	            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	        },
	        success: function (element) {
	            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
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
	            	empid :{
                    	required:true
                    },
	            	companyname :{
                    	required:true
                    },
                    degree:{
                    	required:true
                    },
                    startdate :{
                    	required:true
                    },
                    enddate :{
                    	required:true
                    },
                    reason :{
                    	required:true
                    }
	            },
	            messages: {
	            	empid :{
                    	required:icon + "请选择员工"
                    },
	            	companyname :{
                    	required:icon + "请填写公司名称"
                    },
                    degree:{
                    	required:icon + "请填写岗位"
                    },
                    startdate :{
                    	required:icon + "请填写入职时间"
                    },
                    enddate :{
                    	required:icon + "请填写离职时间"
                    },
                    reason :{
                    	required:icon + "请填写离职原因"
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