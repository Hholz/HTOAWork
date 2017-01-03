<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生届别</title>
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
			if(rows==null || rows.id==null){
				swal("警告！", "请选择你要修改的ID", "error");
				//把内容放到更新的列表
				$('#window_update').modal();
			}
			//把内容放到更新的列表
			$("#window_update #id").val(rows.id);
            $("#window_update #level").val(rows.level);
            $("#window_update #remark").val(rows.remark);
        });
		$("#btn_query").click(function(){
			//刷新表格
			$('#tb_departments').bootstrapTable('refresh');
          
        });
		$("#btn_clean").click(function(){
			//刷新表格
			$("#txt_search_statu").val("");
			$("#txt_search_departmentname").val("");
			$('#tb_departments').bootstrapTable('refresh');
          
        });
		//删除按钮事件
		//*************************************************************************按钮事件
		$('#btn_delete').click(function () {
			if(rows==null || rows.id==null){
				swal("警告！", "请选择你要删除的ID", "error");
				
			}else{
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
		  }
        });
	});
   	
     var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_departments').bootstrapTable({
					url : '${pageContext.request.contextPath}/student/fall/fallListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : oTableInit.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
				//	showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
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
						title : '编号'
					}, {
						field : 'level',
						title : '届别名称'
					}, {
						field : 'remark',
						title : '描述',
						
					},]
				});
			};
			
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					level : $("input[placeholder=搜索]").val(),
					remark : $("#txt_search_statu").val(),
					level : $("#txt_search_departmentname").val()
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
	function addStudent(){
		if(!validateForm($("#addfall"))){
			return;
		}
		
		var url = "${pageContext.request.contextPath }/student/fall/add";
		$.post(
			url,
			{
				id:$("#window_add #id").val(),
				level:$("#window_add #level").val(),
                remark:$("#window_add #remark").val()
			},
			addStudentHandle,
			"text"
		);	
		
		//用来关闭新增窗口***********
		$("#window_add").modal('hide');
		$("#window_add #level").val('');
		$("#window_add #remark").val('');
		
	}
	function addStudentHandle(data){
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if(data>0){
			swal({
                title: "成功",
                text: "你已经完成添加操作",
                type: "success"
            });
		}else{
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}
	
	//修改学生，ajax提交
	function updateStudent(){
		var id =$("#window_update #id").val(); 
		var url = "${pageContext.request.contextPath }/student/fall/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
				id:$("#window_update #id").val(),
                level:$("#window_update #level").val(),
                remark:$("#window_update #remark").val()
			},
			updateStudentHandle,
			"text"
		);	
		//用来关闭修改窗口***********
		$("#window_update").modal('hide')
		rows = null;
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
	//删除学生，ajax提交
	function deleteStudent(){
		//alert('删除');
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/student/fall/"+id;
		//alert(url);
		$.post(
			url,
			{
				_method:'DELETE',//改成PUT提交
				id:rows.id,  //从选中的rows中获取id
				status:0,
			
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

		<div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>查询向导</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="form_wizard.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="panel panel-default">
						<div class="panel-body">
							<form id="formSearch" class="form-horizontal">
								<div class="form-group" style="margin-top: 15px">
									<label class="control-label col-sm-1" for="txt_search_departmentname">届别</label>       
									<div class="col-sm-3">
										<input type="text" class="form-control"id="txt_search_departmentname">       
									</div>
									<label class="control-label col-sm-1" for="txt_search_statu">描述</label>
									<div class="col-sm-3">
										<input type="text" class="form-control" id="txt_search_statu">       
									</div>
									<div class="col-sm-3">
										<button type="button" style="margin-left: 60px" id="btn_query" class="btn btn-primary">查询</button>
										<button type="button" id="btn_clean" class="btn btn-primary">清空</button>
									</div>
								</div>
							</form>
						</div>
					</div>
                </div>
            </div>
        </div>
        <div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal" data-target="#window_update">
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
						<h4 class="modal-title">修改届别信息</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addfall2" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                        	<div class="form-group">
                                <label class="col-sm-3 control-label">届别：</label>
                                <div class="col-sm-8">
                                    <input id="level" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">描述：</label>
                                <div class="col-sm-8">
                                    <input id="remark" type="text" required class="form-control">
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
						<h4 class="modal-title">添加届别信息</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addfall" novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">届别：</label>
                                <div class="col-sm-8">
                                    <input id="level" name="level" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">描述：</label>
                                <div class="col-sm-8">
                                    <input id="remark" type="text" class="form-control">
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
		
		
		
		<!-- table代码，用js构建表格 -->
		
			<div class="row">
				<div class="col-sm-12">
					<table id="tb_departments"></table>
				</div>
			</div>
		</div>
    </div>
   
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
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	level :{
                    	required:true,
                    	rangelength:[2,10]
                    }
                    
	            },
	            messages: {
	            	level :{
                    	required:icon + "请输入届别名称",
                    	rangelength:icon + "请输入届别名称必须在2-10字符之间"
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