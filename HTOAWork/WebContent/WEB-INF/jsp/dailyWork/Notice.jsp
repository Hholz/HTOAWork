<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统公告</title>

	<jsp:include page="../styleInclude.jsp"></jsp:include>
	
	<script type="text/javascript">
		var noticeTypeRows = null;
		var noticeRows = null;
		
		//--------------------------------------公告类别--------------------------------------------
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableNoticeType = new TableNoticeType();
			tableNoticeType.Init();
			//初始化button按钮事件
			var buttonNoticeType = new ButtonNoticeType();
			buttonNoticeType.Init();
			
			$("#btn_noticeType_add").click(function(){
				//清空新增div中的数据
				$("#addNoticeType #noticetypename").val('');
				$("#addNoticeType #remark").val('');
				$("#btn_noticeType_save").attr("onclick","addNoticeType()");
			});
			
			$("#btn_noticeType_update").click(function(){
				if(noticeTypeRows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				
				$("#addNoticeType #id").val(noticeTypeRows.id);
				$("#addNoticeType #noticetypename").val(noticeTypeRows.noticetypename);
				$("#addNoticeType #remark").val(noticeTypeRows.remark);
				$('#addNoticeType').modal('show');
				$("#btn_noticeType_save").attr("onclick","updateNoticeType()");
			});
			
			$("#btn_noticeType_delete").click(function(){
				if(noticeTypeRows == null){
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
	                	deleteNoticeType();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
		});
		
		var TableNoticeType = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_noticeType').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/Notice/noticeTypeListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar_noticeType', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					//sortable : true, //是否启用排序
					//sortOrder : "desc", //排序方式
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
		                if(noticeTypeRows != null){
		                	noticeTypeRows = null;
		                }
		                noticeTypeRows = row;
		            },//单击事件
		            onUncheck: function(row) {
		                if(noticeTypeRows != null){
		                	noticeTypeRows = null;
		                }
		            },
					columns : [ {
						checkbox : true
					}, {
						field : 'noticetypename',
						title : '类别名称'
					}, {
						field : 'remark',
						title : '说明'
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
		
		var ButtonNoticeType = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增公告类别
		function addNoticeType(){
			if(!validateForm($("#noticeTypeForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/dailyWork/Notice/addNoticeType";
			$.post(
				url,
				
				{
					noticetypename:$("#addNoticeType #noticetypename").val(),
					remark:$("#addNoticeType #remark").val()
				},
				addNoticeTypeHandle,
				"text"
			);
			//关闭新增窗口
			$("#addNoticeType").modal('hide');
			//刷新数据
			$("#tb_noticeType").bootstrapTable('refresh');
		}
		function addNoticeTypeHandle(data){
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
		//修改公告类别
		function updateNoticeType(){
			var id = $("#addNoticeType #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/Notice/noticeType/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					noticetypename:$("#addNoticeType #noticetypename").val(),
					remark:$("#addNoticeType #remark").val()
				},
				
				updateNoticeTypeHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addNoticeType").modal('hide');
		}
		function updateNoticeTypeHandle(data){
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
			$('#tb_noticeType').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			noticeTypeRows = null;
		}
		//删除公告类别
		function deleteNoticeType(){
			var id = noticeTypeRows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/Notice/noticeType/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:noticeTypeRows.id  //从选中的rows中获取id
				},
				deleteNoticeTypeHandle,
				"text"
			);
		}
		function deleteNoticeTypeHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_noticeType').bootstrapTable('refresh');
		}
	
		//--------------------------------------公告信息--------------------------------------------
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableNotice = new TableNotice();
			tableNotice.Init();
			//初始化button按钮事件
			var buttonNotice = new ButtonNotice();
			buttonNotice.Init();
			
			$("#btn_notice_add").click(function(){
				//清空新增div中的数据
				$("#addNotice #title").val('');
				$("#addNotice #content").val('');
				$("#btn_notice_save").attr("onclick","addNotice()");
			});
			
			$("#btn_notice_update").click(function(){
				if(noticeRows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				$("#addNotice #id").val(noticeRows.id);
				$("#addNotice #noticename").val(noticeRows.noticename);
				$("#addNotice #title").val(noticeRows.title);
				$("#addNotice #content").val(noticeRows.content);
				$('#addNotice').modal('show');
				$("#btn_notice_save").attr("onclick","updateNotice()");
				
			});
			
			$("#btn_notice_delete").click(function(){
				if(noticeRows == null){
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
	                	deleteNotice();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
			});
			//查询
			$("#btn_query").click(function(){
				$('#tb_notice').bootstrapTable('refresh');
				noticeRows = null;
			});
			$("#btn_clean").click(function(){
				noticename : $("#txt_search_noticename").val('');
				title : $("#txt_search_title").val('');
				$('#tb_notice').bootstrapTable('refresh');
				noticeRows = null;
			});
		});
		
		var TableNotice = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_notice').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/Notice/noticeListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar_notice', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : table.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 3, //每页的记录行数（*）
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
		                if(noticeRows != null){
		                	noticeRows = null;
		                }
		                noticeRows = row;
		            },//单击事件
		            onUncheck: function(row) {
		                if(noticeRows != null){
		                	noticeRows = null;
		                }
		            },
					columns : [ {
						checkbox : true
					}, {
						field : 'noticename',
						title : '公告类别',
						formatter : function(value, row, index) {
							var noticeType = row.noticeType;
							if(noticeType == null){
								return "-";
							}else{
								return noticeType.noticetypename;
							}
						}
					}, {
						field : 'title',
						title : '标题'
					}, {
						field : 'content',
						title : '内容'
					}, {
						field : 'empid',
						title : '发布人',
						formatter : function(value, row, index) {
							var emp = row.emp;
							if(emp == null){
								return "-";
							}else{
								return emp.empname;
							}
						}
					},{
						field : 'noticeTime',
						title : '发布时间',
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
					noticename : $("#txt_search_noticename").val(),
					title : $("#txt_search_title").val()
				};
				return temp;
			};
			return table;
		};
		
		var ButtonNotice = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增公告信息
		function addNotice(){
			//用来判断表单是否验证通过
			if(!validateForm($("#noticeForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/dailyWork/Notice/addNotice";
			$.post(
				url,
				
				{
					noticename:$("#addNotice #noticename").val(),
					title:$("#addNotice #title").val(),
					content:$("#addNotice #content").val()
				},
				addNoticeHandle,
				"text"
			);
			//关闭新增窗口
			$("#addNotice").modal('hide');
			//刷新数据
			$("#tb_notice").bootstrapTable('refresh');
		}
		function addNoticeHandle(data){
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
		
		//修改公告信息
		function updateNotice(){
			var id = $("#addNotice #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/Notice/notice/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					noticename:$("#addNotice #noticename").val(),
					title:$("#addNotice #title").val(),
					content:$("#addNotice #content").val()
				},
				
				updateNoticeHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addNotice").modal('hide');
		}
		function updateNoticeHandle(data){
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
			$('#tb_notice').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			noticeRows = null;
		}
		
		//删除公告信息
		function deleteNotice(){
			var id = noticeRows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/Notice/notice/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:noticeRows.id  //从选中的rows中获取id
				},
				deleteNoticeHandle,
				"text"
			);
		}
		function deleteNoticeHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_notice').bootstrapTable('refresh');
		}
	</script>
</head>
<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
     	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
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
								<label class="control-label col-sm-1" for="txt_search_noticename">公告类别</label>       
								<div class="col-sm-2">
									<select id="txt_search_noticename" class="form-control">
									   <option value=""></option>
                                       <c:forEach items="${noticeType}" var="notice">
                                       		<option value="${notice.id }">${notice.noticetypename }</option>
                                       </c:forEach>
                                    </select>      
								</div>
								<label class="control-label col-sm-1" for="txt_search_title">标题</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" id="txt_search_title">      
								</div>
								<div class="col-sm-3">
									<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
									<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
									<button type="button" style="margin-left: 50px" id="btn_clean" class="btn btn-primary">
									<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清空</button>
								</div>
							</div>
						</form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row"  style="padding-bottom: 0px;" style="height: 287px;">
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>公告类别</h5>
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
                    	<div id="toolbar_noticeType" class="btn-group">
							<button id="btn_noticeType_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addNoticeType">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_noticeType_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_noticeType_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_noticeType"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>公告信息</h5>
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
                    	<div id="toolbar_notice" class="btn-group">
							<button id="btn_notice_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addNotice">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_notice_update" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_notice_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_notice" ></table>
                    </div>
                </div>
            </div>
        </div>
     </div>
     <!--公告类别的弹窗  -->
	 <div class="modal inmodal" id="addNoticeType" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">公告类别</h4>
				</div>
				
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="noticeTypeForm" novalidate="novalidate" >
                    	<input id="id" type="hidden">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">类别名称：</label>
                            <div class="col-sm-8">
                                <input id="noticetypename" name="noticetypename" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">说明：</label>
                            <div class="col-sm-8">
								<input id="remark" name="remark" type="text" class="form-control">
							</div>
                        </div>
                        <div class="modal-footer">
                             <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                             <button type="button" id="btn_noticeType_save" onclick="" class="btn btn-primary">保存</button>
                        </div>
                    </form>
                </div>
			</div>
		</div>
	 </div>
	<!--公告信息的弹窗  -->
	<div class="modal inmodal" id="addNotice" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">添加公告信息</h4>
				</div>
				
                   <div class="ibox-content">
                       <form class="form-horizontal m-t" id="noticeForm" novalidate="novalidate" >
                       	<input id="id" name="id" type="hidden">
                           <div class="form-group">
                               <label class="col-sm-3 control-label">类别名称：</label>
                               <div class="col-sm-8">
                                   <select id="noticename" name="noticename" class="form-control">
                                       <c:forEach items="${noticeType}" var="notice">
                                       		<option value="${notice.id }">${notice.noticetypename }</option>
                                       </c:forEach>
                                    </select>
                               </div>
                           </div>
                           <div class="form-group">
                               <label class="col-sm-3 control-label">标题：</label>
                               <div class="col-sm-8">
                                   <input id="title" name="title" type="text" class="form-control">
                               </div>
                           </div>
                           <div class="form-group">
                               <label class="col-sm-3 control-label">内容：</label>
                               <div class="col-sm-8">
                                   <textarea id="content" name="content" rows="5" class="form-control"></textarea>
                               </div>
                           </div>
                           <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                <button type="button" id="btn_notice_save" onclick="" class="btn btn-primary">保存</button>
                           </div>
                       </form>
                   </div>
			</div>
		</div>
	</div>
	
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	
	<script>
		//日期范围限制
		var noticeTime = {
			elem : '#noticeTime',
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
		laydate(noticeTime);
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
	            	noticetypename: "required",
	            	title: "required",
	            	content: "required",
	            	noticeTime: {
	                	required:true
                    }
	            	
	            },
	            messages: {
	            	noticetypename: icon + "请输入类别名称",
	            	title: icon + "请输入标题",
	            	content: icon + "请输入内容",
	            	noticeTime :{
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
</body>
</html>