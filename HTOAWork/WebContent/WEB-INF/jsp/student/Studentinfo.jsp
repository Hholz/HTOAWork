<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>BootStrap Table使用</title>


	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/style.css?v=4.1.0" rel="stylesheet">

	<!-- 全局js -->
    <script src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>

   

	<!-- 弹出框必需 -->
    <script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    

    <!-- Bootstrap table -->
    <script src="${pageContext.request.contextPath}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript">
    //全局变量，读取当前行的数据
	var rows=null;
    $(function () {
		//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();

			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//新增按钮事件*************************
			$("#btn_add").click(function(){
                $('#myModal').modal();
            });
			//修改按钮事件
			$("#btn_edit").click(function(){
                if(rows == null || rows.id==null){
                	swal("Update is failed！", "Place select update ID", "error");
                }else{
                	$('#updateModal').modal();
                	id:$("#updateModal #id").val(rows.id);
					classname:$("#updateModal #classname").val(rows.classname);
					bzname:$("#updateModal #bzname").val(rows.bzname);
	 				count:$("#updateModal #count").val(rows.count);
	 				addr:$("#updateModal #addr").val(rows.addr);
                }
            });
			//删除按钮事件
			$("#btn_delete").click(function(){
                if(rows == null || rows.id==null){
                	swal("Delete is failed！", "Place select Delete ID", "error");
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
	                		deleteDept();
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
					url : '${pageContext.request.contextPath}/stuinfo/infolistJson', //请求后台的URL（*）
					method : 'get', //请求方式（*）
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
					height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "ID", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onClickRow: function(row, $element) {//selected
		                 //$element是当前tr的jquery对象
		                 if(rows!=null){
		                	 rows = null;
		                 }
		                 rows = row;
		            },//单击row事件
					columns : [ {
						checkbox : true
					}, {
						field : 'id',
						title : '编号'
					}, {
						field : 'classname',
						title : '班名'
					},{
						field : 'count',
						title : '人数'
					},{
						field : 'studentfall.level',
						title : '届别'
					},{
						field : 'teacherId',
						title : '任课老师'
					},{
						field : 'clteacherId',
						title : '班主任'
					},{
						field : 'bzname',
						title : '班长'
					},{
						field : 'addr',
						title : '班地址'
					},]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					departmentname : $("input[placeholder=搜索]").val(),
					statu : $("#txt_search_statu").val()
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
		function addclass(){
			var url = "${pageContext.request.contextPath }/stuinfo/add";
			$.post(
				url,
				{
					id:$("#myModal #id").val(),
					classname:$("#myModal #classname").val(),
					bzname:$("#myModal #bzname").val(),
	 				count:$("#myModal #count").val(),
	 				teacherId:$("#myModal #teacherId").val(),
	 				clteacherId:$("#myModal #clteacherId").val(),
	 				addr:$("#myModal #addr").val(),
	 				levelId:$("#myModal #levelId").val()
				},
				addDeptHandle,
				"text"
			);	
			
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
			//用来关闭新增窗口***********
	 		$("#myModal").modal('hide')
			
		}
		function addDeptHandle(data){
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
		}
		
		//update department ，ajax提交
		function updateDept(){
			var id =rows.id;
			alert(id);
			var url = "${pageContext.request.contextPath }/stuinfo/cinfo/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#updateModal #id").val(),
					classname:$("#updateModal #classname").val(),
					bzname:$("#updateModal #bzname").val(),
	 				count:$("#updateModal #count").val(),
	 				addr:$("#updateModal #addr").val(),
	 				teacherId:$("#updateModal #teacherId").val(),
	 				clteacherId:$("#updateModal #clteacherId").val(),
	 				levelId:$("#updateModal #levelId").val()
	 				
				},
				addUpdateHandle,
				"text"
			);	
			
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
			//用来关闭新增窗口***********
	 		$("#updateModal").modal('hide')
			
		}
		function addUpdateHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "Update is seccussed！",
                    type: "success"
                });
			}else{
				swal("修改失败！", "请检查你输入的数据！", "error");
			}
		}
		
		//delect Dept 
		//删除学生，ajax提交
		function deleteDept(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/stuinfo/cinfo/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id,  //从选中的rows中获取id
					status:0
				},
				deleteDeptHandle,
				"text"
			);	
		}
		
		function deleteDeptHandle(data){
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
		<div class="panel panel-default">
			<div class="panel-heading">查询条件</div>
			<div class="panel-body">
				<form id="formSearch" class="form-horizontal">
					<div class="form-group" style="margin-top: 15px">
						<label class="control-label col-sm-1" for="txt_search_departmentname">部门名称</label>       
						<div class="col-sm-3">
							<input type="text" class="form-control"id="txt_search_departmentname">       
						</div>
						<label class="control-label col-sm-1" for="txt_search_statu">状态</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="txt_search_statu">       
						</div>
						<div class="col-sm-3">
							<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">查询</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>
		
		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments"></table>
		 
	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">班级新增</h4>
        </div>
       <div class="ibox-content">
          <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate">
              <div class="form-group">
                  <label class="col-sm-3 control-label">班名：</label>
                  <div class="col-sm-8">
                      <input id="classname" type="text" required class="form-control">
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">班人数：</label>
                  <div class="col-sm-8">
                      <input id="count" type="text" required class="form-control">
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">班长：</label>
                  <div class="col-sm-8">
                      <input id="bzname" type="text" required class="form-control">
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">班主任：</label>
                  <div class="col-sm-8">
                      <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="clteacherId">
                  	<option value=""> -请选择---</option >
                  	<option value=""> -啦啦啦---</option >
                </select>
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">年级主任：</label>
                  <div class="col-sm-8">
                      <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="leaderId">
                  	<option value=""> -请选择---</option >
                  	<option value=""> -啦啦啦---</option >
                </select>
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">任课老师：</label>
                  <div class="col-sm-8">
                      <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="teacherId">
                  	<option value=""> -请选择---</option >
                  	<option value=""> -啦啦啦---</option >
                </select>
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">班地址：</label>
                  <div class="col-sm-8">
                      <input id="addr" type="text" required class="form-control">
                  </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-3 control-label">届别：</label>
                  <div class="col-sm-8">
                <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" id="levelId">
                  	<option value=""> -请选择---</option >
                  	<c:forEach items="${sList}" var="s">
                       <option value="${s.id}">${s.level}</option >
                     </c:forEach> 
                </select>
                  </div>
              </div>
              <div class="modal-footer">
                   <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                   <button type="button" onclick="addclass()" class="btn btn-primary">保存</button>
              </div>
          </form>
      </div>
      </div>
      
      <div class="modal inmodal" id="updateModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改分班信息</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                        	<div class="form-group">
                                <label class="col-sm-3 control-label">班名：</label>
                                <div class="col-sm-8">
                                    <input id="classname" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班人数：</label>
                                <div class="col-sm-8">
                                    <input id="count" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
				                 <label class="col-sm-3 control-label">班长：</label>
				                 <div class="col-sm-8">
				                      <input id="bzname" type="text" required class="form-control">
				                 </div>
				             </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班主任：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="clteacherId">
		                              	<option value=""> -请选择---</option >
		                              	<option value=""> -啦啦啦---</option >
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">任课老师：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="clteacherId">
		                              	<option value=""> -请选择---</option >
		                              	<option value=""> -啦啦啦---</option >
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班地址：</label>
                                <div class="col-sm-8">
                                    <input id="addr" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">届别：</label>
                                <div class="col-sm-8">
		                            <select class="chosen-select" style="width:362px;height:30px;" tabindex="2" id="levelId">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${sList}" var="s">
		                                   <option value="${s.id}">${s.level}</option >
		                                 </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="updateDept()" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
      
    </div>
  </div>
   <!-- 自定义js -->
   <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</body>
</html>