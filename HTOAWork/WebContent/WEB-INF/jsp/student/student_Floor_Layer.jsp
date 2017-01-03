<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
  <script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var rows = null;
    	var rowsLayer=null;
    	function test(id){
	    	$("#stuRMId").val(id);
	    	//让模板明细表刷新
			$('#tb_Layer').bootstrapTable('refresh');
	    }
	    $(function () {
	 		
			//1.初始化Table
			var oTable = new TableInit();
			//2.初始化楼层的Table
			var oTableLayer = new TableLayerInit();
			oTable.Init();
			oTableLayer.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			var oButtonLayerInit = new ButtonLayerInit();
			oButtonLayerInit.Init();
			
			//新增按钮事件*************************
				//新增事件
			$("#btn_add").click(function(){
				//清空新增div中的数据
				$("#window_add #id").val('');
		        $("#window_add #floorname").val('');
		        $("#window_add #floorAdmin").val('');
		        $("#window_add #phone").val('');
		        $("#window_add #address").val('');
		        $("#window_add #layerNum").val('');
		        $('#window_add').modal('show');
		        
		    });
			
			//修改按钮事件
			$("#btn_edit").click(function(){
				//把内容放到更新的列表
				if(rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_update #id").val(rows.id);
	            $("#window_update #floorname").val(rows.floorname);
	            $("#window_update #floorAdmin").val(rows.floorAdmin);
	            $("#window_update #phone").val(rows.phone);
	            $("#window_update #address").val(rows.address);
	            $('#window_update').modal('show');
                $("#FloorSave").attr("onclick","updateStudentFloor()");
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
	                	deleteStudentFloor();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
			
			//删除楼层按钮事件
			$('#btn_deleteLayer').click(function () {
				if(rowsLayer==null){
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
	                	deleteStudentLayer();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
	    });
			
		//这是楼栋维护的datatable=========================================================================
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_Floor').bootstrapTable({
					url : '${pageContext.request.contextPath}/student/FloorLayer/listFloorJson', //请求后台的URL（*）
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
					pageSize : 9, //每页的记录行数（*）
					pageList : [ 9, 15], //可供选择的每页的行数（*）
					//search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
					//showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					//minimumCountColumns : 3, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onClickRow: function(row, $element) {
		                 //$element是当前tr的jquery对象
		                 if(rows!=null){
		                	 rows = null;
		                 }
		                 rows = row;
		            },//单击row事件
		            onUncheck: function(row) {
			            if(rows != null){
			            	rows = null;
			            }
			        },
					columns : [ {
						checkbox : true
					},{
						field : 'number',
						title : '序号',
						formatter: function (value, row, index) { 
							return index+1; 
						} 

					}, {
						field : 'id',
						title : '编号'
					}, {
						field : 'floorname',
						title : '楼栋',
						align : 'center'
					}, {
						field : 'floorAdmin',
						title : '宿舍管理员',
						align : 'center'
					},{
						field : 'phone',
						title : '电话',
						align : 'center'
					},{
						field : 'address',
						title : '地址',
						align : 'center'
					},{
						field : 'layernum',
						title : '层数',
						align : 'center'
					},{
						field : 'Id',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a onclick='test("+id+")'><i class='fa fa-file-text-o'>楼层</i></a>";
						}
					}, ]
				});
			};
		
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					departmentname : $("input[placeholder=搜索]").val(),
					name : $("input[placeholder=搜索]").val(),
					statu : $("#txt_search_statu").val()
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
		function addFloor(){
			//用来判断表单是否验证通过
			if(!validateForm($("#FloorForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/student/FloorLayer/addFloor";
			$.post(
				url,
				{
					id:$("#window_add #id").val(),
	                floorname:$("#window_add #floorname").val(),
	                floorAdmin:$("#window_add #floorAdmin").val(),
	                phone:$("#window_add #phone").val(),
	                address:$("#window_add #address").val(),
	                layerNum:$("#window_add #layerNum").val()
				},
				addFloorHandle,
				"text"
			);	
			
			//用来关闭新增窗口***********
			$("#window_add").modal('hide')
		}
		
		
		function addFloorHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
				$('#tb_Floor').bootstrapTable('refresh');
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
		}
	
		//修改学生，ajax提交
		function updateStudentFloor(){
			var id =$("#window_update #id").val(); 
			var url = "${pageContext.request.contextPath }/student/FloorLayer/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_update #id").val(),
	                floorname:$("#window_update #floorname").val(),
	                floorAdmin:$("#window_update #floorAdmin").val(),
	                phone:$("#window_update #phone").val(),
	                address:$("#window_update #address").val()
				},
				updateStudentFloorHandle,
				"text"
			);	
			//用来关闭修改窗口***********
			$("#window_update").modal('hide')
		}
		function updateStudentFloorHandle(data){
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
			$('#tb_Floor').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
	
		
		//***************************************************************************************删除
		//删除学生楼栋，ajax提交
		function deleteStudentFloor(){
			//alert('删除');
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/student/FloorLayer/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteStudentFloorHandle,
				"text"
			);	
			$('#tb_Floor').bootstrapTable('refresh');
		}
		function deleteStudentFloorHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
				$('#tb_Floor').bootstrapTable('refresh');
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			
		}
		
		<!--这里写楼层的datatable-->
		
		var TableLayerInit = function() {
			var oTableLayerInit = new Object();
			//初始化Table
			oTableLayerInit.Init = function() {
				$('#tb_Layer').bootstrapTable({
					url : '${pageContext.request.contextPath}/student/FloorLayer/listLayerJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbarLayer', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : oTableLayerInit.queryParam,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 9, //每页的记录行数（*）
					pageList : [ 9, 15], //可供选择的每页的行数（*）
					//search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
					//showColumns : true, //是否显示所有的列
					//showRefresh : true, //是否显示刷新按钮
					//minimumCountColumns : 3, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onClickRow: function(row, $element) {
		                 //$element是当前tr的jquery对象
		                 if(rowsLayer!=null){
		                	 rowsLayer = null;
		                 }
		                 rowsLayer = row;
		            },//单击row事件
					columns : [ {
						checkbox : true
					},{
						field : 'number',
						title : '序号',
						formatter: function (value, row, index) { 
							return index+1; 
						} 

					}, {
						field : 'id',
						title : '编号'
					}, {
						field : 'floorname',
						title : '楼栋',
						formatter : function(value, row, index) {
							var studentFloor = row.studentFloor;
							if(studentFloor==null){
								return "-";
							}else{
								return studentFloor.floorname;
							}
						}
					}, {
						field : 'layername',
						title : '楼层',
						formatter : function(value, row, index) {
							var Name = row.layername;
							return '第'+Name+'层';
						}
					}, ]
				});
			};
		
			//得到查询的参数
			oTableLayerInit.queryParam = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					/* departmentname : $("input[placeholder=搜索]").val(),
					name : $("input[placeholder=搜索]").val(),
					statu : $("#txt_search_statu").val() */
					floorid:$("#stuRMId").val()
				};
				return temp;
			};
			return oTableLayerInit;
		};
		var ButtonLayerInit = function() {
			var oInitLayer = new Object();
			var postdata = {};

			oInitLayer.Init = function() {
				//初始化页面上面的按钮事件
			};

			return oInitLayer;
		};
		//新增学生楼层，ajax提交
		function addLayer(){
			//用来判断表单是否验证通过
			if(!validateForm($("#layerForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/student/FloorLayer/addLayer";
			$.post(
				url,
				{
					//id:$("#window_addLayer #id").val(),
					layername:$("#window_addLayer #box").val(),
					floorid:$("#window_addLayer #stuRMId").val()
				},
				addLayerHandle,
				"text"
			);	
			
			//用来关闭新增窗口***********
			$("#window_addLayer").modal('hide')
		}
		function addLayerHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
				$('#tb_Layer').bootstrapTable('refresh');
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
		}
		//删除学生楼层，ajax提交
		function deleteStudentLayer(){
			
			//alert('删除');
			var id = rowsLayer.id;
			//alert(id);
			var url = "${pageContext.request.contextPath }/student/Layer/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rowsLayer.id  //从选中的rows中获取id
				},
				deleteStudentLayerHandle,
				"text"
			);	
			$('#tb_Layer').bootstrapTable('refresh');
		}
		function deleteStudentLayerHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
				$('#tb_Layer').bootstrapTable('refresh');
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			
		}
		
		function selectValue(data){
			
			$("#window_addLayer #box").val(data);
		}
	</script>
</head>
<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
     	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>宿舍管理</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                               
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <p>这里是所有学生宿舍的楼栋楼层信息</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>楼栋维护</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
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
						<!--修改的弹窗  -->
						<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content animated bounceInRight">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
										</button>
										<h4 class="modal-title">修改楼栋</h4>
									</div>
									
				                    <div class="ibox-content">
				                        <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate" >
				                        	<input id="id" type="hidden">
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼栋名称：</label>
				                                <div class="col-sm-8">
				                                    <input id="floorname" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼栋管理员：</label>
				                                <div class="col-sm-8">
				                                    <input id="floorAdmin" name="floorAdmin" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">电话：</label>
				                                <div class="col-sm-8">
				                                    <input id="phone" name="phone" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">地址：</label>
				                                <div class="col-sm-8">
				                                    <input id="address" name="address" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="modal-footer">
				                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				                                 <button type="button" id="FloorSave" onclick="updateStudentFloor()" class="btn btn-primary">保存</button>
				                            </div>
				                        </form>
				                    </div>
								</div>
							</div>
						</div>
						<!-- 新增的弹窗 -->
						<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content animated bounceIn">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
										</button>
										<h4 class="modal-title">新增楼栋</h4>
									</div>
									
				                    <div class="ibox-content">
				                        <form class="form-horizontal m-t" id="FloorForm" novalidate="novalidate">
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼栋名称：</label>
				                                <div class="col-sm-8">
				                                    <input id="floorname" name="floorname" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">宿舍管理员：</label>
				                                <div class="col-sm-8">
				                                    <input id="floorAdmin" name="floorAdmin" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">电话：</label>
				                                <div class="col-sm-8">
				                                    <input id="phone" name="phone" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">地址：</label>
				                                <div class="col-sm-8">
				                                    <input id="address" name="address" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼层数量：</label>
				                                <div class="col-sm-8">
				                                    <input id="layerNum" name="layerNum" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="modal-footer">
				                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				                                 <button type="button" onclick="addFloor()" class="btn btn-primary">保存</button>
				                            </div>
				                        </form>
				                    </div>
								</div>
							</div>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_Floor"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>楼层维护</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div id="toolbarLayer" class="btn-group">
							<button id="btn_addLayer" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_addLayer">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<!-- <button id="btn_editLayer" type="button" class="btn btn-w-m btn-success" data-toggle="modal" data-target="#window_updateLayer">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button> -->
							<button id="btn_deleteLayer" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!--修改的弹窗  -->
						<div class="modal inmodal" id="window_updateLayer" tabindex="-1" role="dialog" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content animated flip">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
										</button>
										<h4 class="modal-title">修改楼层</h4>
									</div>
									
				                    <div class="ibox-content">
				                        <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate" >
				                        	<input id="id" type="hidden">
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼层：</label>
				                                <div class="col-sm-8">
				                                    <input id="layername" type="text" required class="form-control">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼栋：</label>
				                                <div class="col-sm-8">
				                                    <!--input id="floorAdmin" type="text" required class="form-control"-->
				                                     <select id="floorid" data-placeholder="请选择楼栋..." class="chosen-select" style="width:362px;height:34px" tabindex="2">
					                                   <c:forEach items="${studentFloorList}" var="s">
					                                    <option value="${s.id }">${s.floorname }</option>
					                                    </c:forEach>
					                                 </select>
				                                </div>
				                            </div>
				                            <div class="modal-footer">
				                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				                                 <button type="button" onclick="updateStudentFloor()" class="btn btn-primary">保存</button>
				                            </div>
				                        </form>
				                    </div>
								</div>
							</div>
						</div>
						<!-- 新增的弹窗 -->
						<div class="modal inmodal fade" id="window_addLayer" tabindex="-1" role="dialog" aria-hidden="true">
							<div class="modal-dialog modal-sm">
								<div class="modal-content">
									<input type="hidden" id="stuRMId"> 
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
										</button>
										<h4 class="modal-title">新增楼层</h4>
									</div>
									
				                    <div class="ibox-content">
				                        <form class="form-horizontal m-t" id="layerForm" novalidate="novalidate">
				                            <div class="form-group">
				                                <label class="col-sm-3 control-label">楼层：</label>
				                                <label class="col-sm-1 control-label">第</label>
				                                <div class="col-sm-4"> 
				                                	<div style="position:relative;">
														<span style="margin-left:100px;width:20px;overflow:hidden;">
														<select style="width:80px;height:30px;margin-left:-100px" onchange="selectValue(this.value)">
															<option value="1">1</option>
															<option value="2">2</option>
															<option value="3">3</option>
															<option value="3">4</option>
														</select>
														</span>
														<input name="box" id="box" placeholder="数字" style="width:60px;height:30px;position:absolute;left:0px;">
													</div>
					                            </div>
					                            <label class="col-sm-1 control-label">层</label>
				                            </div>
				                            <div class="modal-footer">
				                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				                                 <button type="button" onclick="addLayer()" class="btn btn-primary">保存</button>
				                            </div>
				                        </form>
				                    </div>
								</div>
							</div>
						</div>
                        <table id="tb_Layer"></table>
                    </div>
                </div>
            </div>
        </div>
        
     </div>
     
     <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
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
	            	floorname: "required",
	                floorAdmin: "required",
	                layername:"required"
	            },
	            messages: {
	            	floorname: icon + "请输入楼栋",
	            	floorAdmin: icon + "请输入管理员姓名",
	                layername: icon + "请输入楼层"
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		$('#layerForm #layername').editableSelect({ 
		    effects: 'slide' 
		}); 
	</script>
	<script type="text/javascript">
        $(function () {
        	$('#tb_Floor').bootstrapTable('hideColumn', 'id');
            $('#tb_Layer').bootstrapTable('hideColumn', 'id');
        }); 
    </script>
</body>
</html>