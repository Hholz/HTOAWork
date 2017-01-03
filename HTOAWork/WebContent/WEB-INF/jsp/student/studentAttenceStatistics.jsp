<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>考勤统计</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<script type="text/javascript">
	var wide_stuId = 0;
	var wide_createTime = '';
	$(function() {
		$('#tab_attence').bootstrapTable({
			url : '${pageContext.request.contextPath}/student/attence/stuAttenceCountlistJson', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType : "application/x-www-form-urlencoded",
			toolbar : '#toolbar', //工具按钮用哪个容器
			striped : true, //是否显示行间隔色
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			sortable : false, //是否启用排序
			sortOrder : "asc", //排序方式
			queryParams : queryParams,//传递参数（*）
			//queryParamsType: "limit",
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 30, //每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
			search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
			showColumns : true, //是否显示所有的列
			showRefresh : true, //是否显示刷新按钮
			minimumCountColumns : 2, //最少允许的列数
			//clickToSelect : true, //是否启用点击选中行
			//height : 1500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			showToggle : true, //是否显示详细视图和列表视图的切换按钮
			cardView : false, //是否显示详细视图
			detailView : false, //是否显示父子表
			singleSelect : true, //设置为单选
			onCheck: function(row) {
            },
            onUncheck: function(row) {
            },
			columns : [ {
				field : 'stuId',
				title : '学生姓名',
				formatter : function(value, row, index) {
					var stu = row.stu;
					if(stu==null){
						return "-";
					}else{
						return stu.stuname;
					}
				}
			}, {
				field : 'clsName',
				title : '班级名称',
				formatter : function(value, row, index) {
					var clsName = row.clsName;
					if(clsName==null){
						return "-";
					}else{
						return clsName;
					}
				}
				
			}, {
				field : 'normal',
				title : '<span class="label label-primary">正常</span>',
				align : 'center'
			}, {
				field : 'late',
				title : '<span class="label label-warning">迟到</span>',
				align : 'center'
			}, {
				field : 'truant',
				title : '<span class="label label-danger">旷课</span>',
				align : 'center'
			}, {
				field : 'leave',
				title : '<span class="label label-success">请假</span>',
				align : 'center'
			}, {
				field : 'createTime',
				title : '日期时间'
			},{
				field : 'do',
				title : '操作',
				formatter : function(value, row, index) {
					return '<a onclick="details('+row.stuId+',&#39;'+row.createTime+'&#39;)">本月详情</a>';
				}
			},]
		});
		function queryParams(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				clsId : $('#toolbar #cls_id').val(),
				createTime : $('#toolbar #createTime2').val(),
			};
			return temp;
		}
		$('#tb_stuMonth').bootstrapTable({
			url : '${pageContext.request.contextPath}/student/attence/stuMonthTbJson', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType: "application/x-www-form-urlencoded",
			//toolbar : '#toolbar_stu', //工具按钮用哪个容器
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : false, //是否显示分页（*）
			sortable : false, //是否启用排序
			sortOrder : "asc", //排序方式
			queryParams : queryParams2,//传递参数（*）
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 50, //每页的记录行数（*）
			//pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
			minimumCountColumns : 2, //最少允许的列数
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			columns : [ {
				field : 'id',
				title : 'ID',
				visible : false
			}, {
				field : 'stuId',
				title : '学生姓名',
				formatter : function(value, row, index) {
					var stu = row.stu;
					if(stu==null){
						return "-";
					}else{
						return stu.stuname;
					}
				}
			}, {
				field : 'clsName',
				title : '班级名称',
				formatter : function(value, row, index) {
					var cls = row.cls;
					if(cls==null){
						return "-";
					}else{
						return cls.classname;
					}
				}
				
			}, {
				field : 'morning',
				title : '早操·状态',
				align :'center',
				formatter : function(value, row, index) {
					return colour(value);
				}
			}, {
				field : 'forenoon',
				title : '上午·状态',
				align :'center',
				formatter : function(value, row, index) {
					return colour(value);
				}
			}, {
				field : 'afternoon',
				title : '下午·状态',
				align :'center',
				formatter : function(value, row, index) {
					return colour(value);
				}
			}, {
				field : 'night',
				title : '晚上·状态',
				align :'center',
				formatter : function(value, row, index) {
					return colour(value);
				}
			}, {
				field : 'createTime',
				title : '日期时间'
			}, ]
		});
		function queryParams2(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				stuId : wide_stuId,
				createTime :wide_createTime,
			};
			return temp;
		}
	});
	//弹出该学生一个月的考勤情况
	function details(stuId,createTime){
		//给全局变量赋值
		wide_stuId = stuId;
		wide_createTime = createTime;
		$('#tb_stuMonth').bootstrapTable('refresh');	
		$("#window_month").modal('show');
	}
	//上色
	var colour = function(value){
		var text = "";
		if(value=='正常'){
			text += "<span class='label label-primary'>正常</span>";
		}else if(value=='迟到'){
			text += "<span class='label label-warning'>迟到</span>";
		}else if(value=='旷课'){
			text += "<span class='label label-danger'>旷课</span>";
		}else if(value=='请假'){
			text += "<span class='label label-success'>请假</span>";
		}
		return text;
	}
	//通过条件刷新表格
	function refreshTable(){
		$('#tab_attence').bootstrapTable('refresh');	
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
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div  class="ibox-content">
						
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>学生考勤统计</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="form-inline">
							<label for="cls_id">班级名称</label>
			       			<select name="cls_id" id="cls_id"  class="form-control" onchange="refreshTable()">
			      				<c:forEach items="${clsList}" var="cls">
					           		<option value="${cls.id}">${cls.classname}</option>
					           	</c:forEach>
			       			</select>
			       			<label for="createTime2">考勤时间：</label>
					   		<input placeholder="默认本月" class="form-control  layer-date" id="createTime2" name="createTime2">
		       			</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tab_attence"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 一个学生的本月考勤记录明细表 -->
	<div class="modal fade" id="window_month" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated fadeIn">
					<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title">该月明细</h4>
				    </div>
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox-content">
								<!-- table代码就这些，用js构建表格 -->
								<div id="div_stu">
								<table id="tb_stuMonth"></table>
								</div>
								<div class="modal-footer">
	                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	                            </div>
							</div>
						</div>
				  </div>
			</div>
		</div>
     </div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
    <!-- Toastr script -->
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#createTime2',
			format : 'YYYY-MM',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : false,
			istoday : true,
			choose : function() {
				//选完日期的回调函数，刷新表格
				$('#tab_attence').bootstrapTable('refresh');
			}
		};
		laydate(start);
	</script>
</body>
</html>