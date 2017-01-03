<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>班级设置页面</title>
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
			//修改按钮事件
			$("#btn_add").click(function(){
				$("#window_add #id").val('');
 				$("#window_add #classname").val('');
 				$("#window_add #count").val('');
 				$("#window_add #teacherId").val('');
 				$("#window_add #leaderId").val('');
 				$("#window_add #clteacherId").val('');
 				$("#window_add #addr").val('');
 				$("#window_add #levelId").val('');
	       });
			//新增按钮事件*************************
			//修改按钮事件
			$("#btn_edit").click(function(){
				if(rows==null || rows.id==null){
					swal("警告！", "请选择你要修改的ID", "error");
					//把内容放到更新的列表
					$('#window_update').modal();
				}
				
				$("#window_update #id").val(rows.id);
 				$("#window_update #classname").val(rows.classname);
 				$("#window_update #count").val(rows.count);
 				$("#window_update #teacherId").val(rows.teacherId);
 				$("#window_update #leaderId").val(rows.leaderId);
 				$("#window_update #clteacherId").val(rows.clteacherId);
 				$("#window_update #addr").val(rows.addr);
 				$("#window_update #levelId").val(rows.levelId);
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
			   if(rows == null || rows.id==null){
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
					url : '${pageContext.request.contextPath}/student/class/classlistJson', //请求后台的URL（*）
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
					showColumns : true, //是否显示所有的列
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
						field : 'classno',
						title : '班级编号'
					}, {
						field : 'classname',
						title : '班级名称'
					},{
						field : 'count',
						title : '人数'
					},{
						field : 'stunum',
						title : '现有人数',
						formatter : function(value, row, index) {
							if(row.clsStatus==-1){
								return row.counttest;
							}else{
								return row.countstu;
							}
						}
					},{
						field : 'leaderId',
						title : '校领导',
						formatter : function(value, row, index) {
							var empl = row.empl;
							if(empl==null){
								return "-";
							}else{
								return empl.empname;
							}
						}
					},{
						field : 'clteacherId',
						title : '班主任',
						formatter : function(value, row, index) {
							var empc = row.empc;
							if(empc==null){
								return "-";
							}else{
								return empc.empname;
							}
						}
					},{
						field : 'teacherId',
						title : '任课老师',
						formatter : function(value, row, index) {
							var emp = row.emp;
							if(emp==null){
								return "-";
							}else{
								return emp.empname;
							}
						}
					},{
						field : 'majorName',
						title : '专业',
						formatter : function(value, row, index) {
							var major = row.eduMajor;
							if(major==null){
								return "-";
							}else{
								return major.majorName;
							}
						}
					},{
						field : 'clsStatus',
						title : '班级状态',
						formatter : function(value, row, index) {
							var clsStatus = row.clsStatus;
							var text = "";
							if(clsStatus == 0){
								text = "<span class='label label-success'>新  班</span>";
								return text;
							}else if(clsStatus == 1){
								text = "<span class='label label-primary'>正  常</span>";
								return text;
							}else if(clsStatus == 2){
								text = "<span class='label label-danger'>毕  业</span>";
								return text;
							}else if(clsStatus == -1){
								text = "<span class='label label-warning'>试  学</span>";
								return text;
							}
						}
					},{
						field : 'level',
						title : '届别',
						formatter : function(value, row, index) {
							var studentfall = row.studentfall;
							if(studentfall==null){
								return "-";
							}else{
								return studentfall.level;
							}
						}
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
					levelId : $("#levelId").val(),
					classname : $("#txt_search_statu").val()
					
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
 		if(!validateForm($("#addclassinfo"))){
			return;
		}
 		
 		var url = "${pageContext.request.contextPath }/student/class/add";
 		$.post(
 			url,
 			{
 				id:$("#window_add #id").val(),
 				classname:$("#window_add #classname").val(),
 				testCls:$('#window_add input[name=testCls]:checked').val(),
 				count:$("#window_add #count").val(),
 				teacherId:$("#window_add #teacherId").val(),
 				leaderId:$("#window_add #leaderId").val(),
 				clteacherId:$("#window_add #clteacherId").val(),
 				addr:$("#window_add #addr").val(),
 				levelId:$("#window_add #levelId").val(),
 				majorId:$("#window_add #majorId").val(),
 				no:$("#window_add #no").val()
 			},
 			addStudentHandle,
 			"text"
 		);	
 		
 		//用来关闭新增窗口***********
 		$("#window_add").modal('hide')
 	}
 	function addStudentHandle(data){
 		//从后台返回出来的int类型数据，用来判断是否新增成功
 		if(data>0){
 			swal({
                 title: "成功",
                 text: "你已经完成添加操作",
                 type: "success"
             });
 			//刷新表格
 			$('#tb_departments').bootstrapTable('refresh');
 		}else if(date=-1){
 			swal("添加失败", "该班级名称已经存在！", "error");
 		}else{
 			swal("添加失败", "请检查你输入的数据！", "error");
 		}
 		
 	}
 	
 	//修改学生，ajax提交
 	function updateStudent(){
 		var id =$("#window_update #id").val(); 
 		var url = "${pageContext.request.contextPath }/student/class/"+id;
 		$.post(
 			url,
 			{
 				_method:'PUT',//改成PUT提交
 				id:$("#window_update #id").val(),
 				classname:$("#window_update #classname").val(),
 				testCls:$('#window_update input[name=testCls]:checked').val(),
 				count:$("#window_update #count").val(),
 				teacherId:$("#window_update #teacherId").val(),
 				leaderId:$("#window_update #leaderId").val(),
 				clteacherId:$("#window_update #clteacherId").val(),
 				addr:$("#window_update #addr").val()
 			},
 			updateStudentHandle,
 			"text"
 		);	
 		//用来关闭修改窗口***********
 		$("#window_update").modal('hide');
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
 		}else if(data==-1){
 			swal("修改失败", "班级人数设置的比已有人数少", "error");
 		}else{
 			swal("修改失败", "请检查你输入的数据！", "error");
 		}
 		//刷新表格
 		$('#tb_departments').bootstrapTable('refresh');
 		//把保存选中行的rows变量清零，很重要，防止缓存
 		rows = null;
 	}
 	
 	
 	//***************************************************************************************删除
 	//删除班级，ajax提交
 	function deleteStudent(){
 		//alert('删除');
 		var id = rows.id;
 		var url = "${pageContext.request.contextPath }/student/class/"+id;
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
 		}else if(data==-1){
 			swal("删除失败", "该班已经分配学生，无法进行删除操作", "error");
 		}else{
 			swal("删除失败", "服务器繁忙！", "error");
 		}
 		//刷新表格
 		$('#tb_departments').bootstrapTable('refresh');
 	}
 	//动态设置班级名称
    function setClassName(){
    	var name = $("#window_add #levelId option:selected").text()
    				+$("#window_add #majorId option:selected").text()
    				+$("#window_add #no").val();
    	$("#window_add #classname").val(name);
    }
  	//动态设置班级名称
    function setClassName2(){
    	var name = $("#window_update #levelId option:selected").text()
    				+$("#window_update #majorId option:selected").text()
    				+$("#window_update #no").val();
    	$("#window_update #classname").val(name);
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
							<div class="form-group" style="margin-top: 15px">
								<label class="control-label col-sm-1" for="txt_search_departmentname">届别</label>       
								<div class="col-sm-3">
									<select class="chosen-select" style="width:180px;height:33px;" tabindex="2" name="levelId" id="levelId">
									<option value="">--请选择--</option>
									<c:forEach items="${sList}" var="s">
		                               <option value="${s.id}">${s.level}</option >
		                            </c:forEach>  
		                            </select>      
								</div>
								<label class="control-label col-sm-1" for="txt_search_statu">班名</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="txt_search_statu">       
								</div>
								<div class="col-sm-3">
									<button type="button" style="margin-left: 80px" id="btn_query" class="btn btn-primary">查询</button>
									<button type="button" id="btn_clean" class="btn btn-primary">清空</button>
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
		<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加班级</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t"
							novalidate="novalidate" id="addclassinfo" name="addclassinfo">
							<div class="form-group">
								<div class="form-inline col-sm-12 text-center">
									<label class="control-label">界别：</label>
                                	<select class="form-control" id="levelId" name="levelId" onchange="setClassName()">
		                              	<c:forEach items="${sList}" var="s">
		                                   <option value="${s.id}">${s.level}</option >
		                                 </c:forEach> 
		                            </select>
		                            <label class="control-label">专业：</label>
	                                <select class="form-control" id="majorId" name="majorId" onchange="setClassName()">
		                              	<c:forEach items="${majorList}" var="major">
		                                   <option value="${major.id}">${major.majorName}</option >
		                                 </c:forEach> 
		                            </select>
	                                <label class="control-label">班号：</label>
			                        <select class="form-control" id="no" class="form-control" onchange="setClassName()">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">8</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
									</select>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级名称：</label>
                                <div class="col-sm-8">
                                    <input id="classname" name="classname" type="text" disabled="disabled" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否试学班：</label>
                                <div class="col-sm-8">
                                	<label class="control-label"><input type="radio"  name="testCls"  value="true" >&nbsp;&nbsp;&nbsp;是</label>
                                	<label class="control-label" style="padding-left: 20px"><input type="radio" checked  name="testCls"  value="false" >&nbsp;&nbsp;否</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级人数：</label>
                                <div class="col-sm-8">
                                    <input id="count" name="count" type="number" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班主任：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="clteacherId" id="clteacherId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">系领导：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="leaderId" id="leaderId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任课老师：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="teacherId" id="teacherId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级地址：</label>
                                <div class="col-sm-8">
                                    <input id="addr" type="text"  class="form-control" name="addr">
                                </div>
                            </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="addStudent()"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
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
						<h4 class="modal-title">班级信息修改</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t"
							novalidate="novalidate" id="addclassinfo" name="addclassinfo">
							<div class="form-group">
								<input type="hidden" id="id">
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级名称：</label>
                                <div class="col-sm-8">
                                    <input id="classname" name="classname" type="text" disabled="disabled" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级人数：</label>
                                <div class="col-sm-8">
                                    <input id="count" name="count" type="number" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班主任：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="clteacherId" id="clteacherId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">系领导：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="leaderId" id="leaderId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任课老师：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="teacherId" id="teacherId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.id}">${e.empname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级地址：</label>
                                <div class="col-sm-8">
                                    <input id="addr" type="text" class="form-control" name="addr">
                                </div>
                            </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="updateStudent()"
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
	            	levelId :{
                    	required:true
                    },
                    classname :{
                    	required:true,
                    	remote: {
                    	    url: "${pageContext.request.contextPath}/student/class/check/classname",     //后台处理程序
                    	    type: "post",               //数据发送方式
                    	    dataType: "json",           //接受数据格式   
                    	    cache:false,
                            async:false,
                    	    data: {                     //要传递的数据
                    	        classname: function() {
                    	            return $("#window_add #classname").val();
                    	        }
                    	    }
                    	}
                    },
                    count :{
                    	required:true,
                    	number:true,
                    	digits:true,
                    	min:0
                    },
                    clteacherId :{
                    	required:true 
                    },
                    leaderId :{
                    	required:true 
                    },
                    teacherId :{
                    	required:true 
                    },
                    leaderId :{
                    	required:true 
                    }
	            },
	            messages: {
	            	levelId :{
                    	required:icon + "请选择届别"
                    },
                    classname :{
                    	required:icon + "请输入班名",
                    	remote:icon + "该班级已新增"
                    },
                    count :{
                    	required:icon + "请输入班人数",
                    	number:icon + "请输入合法的数字",
                    	digits:icon + "请输入合理的数字",
                    	min:icon + "逻辑有问题，别介意"
                    },
                    clteacherId :{
                    	required:icon + "请选择班主任" 
                    },
                    leaderId :{
                    	required:icon + "请选择负责该班的领导"
                    },
                    teacherId :{
                    	required:icon + "请选择任课老师"
                    }
	            }
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
</script>
</body>
</html>