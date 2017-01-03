<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>意向学生跟踪</title>
	
	<jsp:include page="../styleInclude.jsp"></jsp:include>
	
	<script type="text/javascript">
		//全局变量，用来保存选中行的数据
	    var rows = null;
	   
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableInit = new TableInit();
			tableInit.Init();
			//初始化button按钮事件
			var buttonInit = new ButtonInit();
			buttonInit.Init();
			
			//查询
			$("#btn_query").click(function(){
				$("#tb_intentionStudent").bootstrapTable('refresh');
				rows = null;
			});
			$("#btn_clean").click(function(){
				name : $("#txt_search_name").val('');
				sex : $("#txt_search_sex").val('');
				phone : $("#txt_search_phone").val('');
				$("#tb_intentionStudent").bootstrapTable('refresh');
				rows = null;
			});
		});
		
		var TableInit = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_intentionStudent').bootstrapTable({
					url : '${pageContext.request.contextPath}/market/follow/followStudentListJson', //请求后台的URL（*）
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
						field : 'name',
						title : '姓名'
					}, {
						field : 'sex',
						title : '性别'
					}, {
						field : 'age',
						title : '年龄'
					}, {
						field : 'phone',
						title : '电话'
					},{
						field : 'school',
						title : '毕业学校'
					},{
						field : 'followCount',
						title : '记录次数',
						formatter : function(value, row, index) {
							var followCount = row.followCount;
							var text = "<span class='badge badge-danger'>"+followCount+"</span>";
							return text;
						}
					},{
						field : 'msStatus',
						title : '状态',
						formatter : function(value, row, index) {
							var msStatus = row.msStatus;
							var text = "";
							if(msStatus == 0){
								text = "<span class='label'>意向</span>";
								return text;
							}else if(msStatus == 1){
								text = "<span class='label label-warning'>预定报名</span>";
								return text;
							}else if(msStatus == 2){
								text = "<span class='label label-success'>正式报名</span>";
								return text;
							}else if(msStatus == 3){
								text = "<span class='label label-primary'>已分班</span>";
								return text;
							}else if(msStatus == 4){
								text = "<span class='label label-danger'>已离校</span>";
								return text;
							}
						}
					},{
						field : '跟踪记录',
						title : '跟踪记录',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a  href='${pageContext.request.contextPath}/market/followrecord/page/"+id+"'><i class='fa fa-file-text-o'>跟踪记录</i></a>";
						}
					}]
				});
			};
			//得到查询的参数
			table.queryParams = function(params){
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					id : $("input[placeholder=搜索]").val(),
					name : $("#txt_search_name").val(),
					sex : $("#txt_search_sex").val(),
					phone : $("#txt_search_phone").val()
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
		
		function lookHistory(){
			window.location.href='${pageContext.request.contextPath}/market/follow/history'; 
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
									<div class="row">
										<label class="control-label col-sm-1" for="txt_search_name">姓名</label>       
										<div class="col-sm-2">
											<input type="text" class="form-control" id="txt_search_name"> 
										</div>
										<label class="control-label col-sm-1" for="txt_search_sex">性别</label>       
										<div class="col-sm-1">
											<select class="form-control" id="txt_search_sex" class="form-control">
												<option value=""></option>
												<option value="男">男</option>
												<option value="女">女</option>
											</select>
										</div>
									<!-- </div>
									<div class="row"> -->
										<label class="control-label col-sm-1" for="txt_search_phone">电话</label>
										<div class="col-sm-2">
											<input type="text" class="form-control" id="txt_search_phone"> 
										</div>
										<div class="col-sm-3">
											<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
											<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
											<button type="button" style="margin-left: 50px" id="btn_clean" class="btn btn-primary">
											<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清空</button>
										</div>
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
	                        <h5>意向学生跟踪</h5>
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
								<button id="btn_money" type="button" class="btn btn-success btn-outline" onclick="lookHistory()">
									<span class="glyphicon glyphicon-send" aria-hidden="true"></span>查看历史记录
								</button>
							</div>
							<!-- table代码就这些，用js构建表格 -->
							<table id="tb_intentionStudent"></table>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
	</div>
	
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>