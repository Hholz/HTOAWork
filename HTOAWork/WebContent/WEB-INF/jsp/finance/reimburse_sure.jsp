<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起反馈</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
<!-- <script type="text/javascript" src="//rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js"></script> -->
<script type="text/javascript">
	//全局变量，读取当前行的数据
	var rows = null;
	$(function() {
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();

		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		$("#clear").click(function() {
			empid : $('#formSearch #empid').val('');
			createTime : $('#formSearch #start').val('');
			updateTime : $('#formSearch #end').val('');
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
	});
	function surepay(){
		swal({
			title : "确定付款？",
			text : "",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "付款",
			cancelButtonText : "取消",
			closeOnConfirm : false,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				//调用删除函数
				reimburse_sure();
			} else {
				swal("已取消", "您取消了支付操作！", "error");
			}
		});
	}
	function reimburse_sure(){
		var url = "${pageContext.request.contextPath}/finance/financebalance/addBalance";
		$.post(
			url,
			{
				typeId:'15',
                money:rows.price,
                reportman:rows.empid,
                orderId : rows.applynum
			},
			isSure,
			"text"
		);
	}
	function isSure(data){
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成支付操作",
				type : "success"
			});
		} else {
			swal("支付失败", "请稍后再确认支付！", "error");
		}
		$("#feedbackstart_Tab").bootstrapTable('refresh');
	}
	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#feedbackstart_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/reimburse/statusfourlist', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				//toolbar : '#toolbar', //工具按钮用哪个容器
				showExport: true, //是否显示导出
				//exportDataType: "selected", //basic', 'all', 'selected'.好像默认basic不写也罢
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : oTableInit.queryParams,//传递参数（*）
				queryParamsType: "limit",
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 10, //每页的记录行数（*）
				pageList : [ 10, 25, 50, 100, 'ALL'], //可供选择的每页的行数（*
				search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
				strictSearch : false,
				showColumns : true, //是否显示所有的列
				showRefresh : true, //是否显示刷新按钮
				minimumCountColumns : 2, //最少允许的列数
				clickToSelect : true, //是否启用点击选中行
				height : 400, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
				singleSelect : true, //设置为单选
				onClickRow : function(row, $element) {//selected
					//$element是当前tr的jquery对象
					if (rows != null) {
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
				}, {
					field : 'id',
					title : 'ID',
					visible:false
				},{
					field : 'empname',
					align : 'center',
					title : '员工'
				},{
					field : 'baoxiaotypename',
					align : 'center',
					title : '报销类别'
				},{
					field : 'price',
					align : 'center',
					title : '金额'
				},{
					field : 'flowstatus',
					align : 'center',
					title : '状态',
					formatter : function(value, row, index) {
						var id = row.flowstatus;
						var text = "";
						if (id == 0) {
							text = "未提交";
						} else if (id == 1) {
							text = "已提交";
						} else if(id<0){
							text = "审批未通过";
						}else if(id==3){
							text = "等待领取";
						}else if(id==4){
							text = "已领取";
						}
						return text;
					}
				},{
					field : 'baoxiaoRemark',
					align : 'center',
					title : '说明',
					formatter : function(value, row, index) {
						if(row.baoxiaoRemark==null || row.baoxiaoRemark==""){
							return "暂无说明";
						}else{
							return row.baoxiaoRemark;
						}
					}
				},{
					field : 'deal',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						if(row.financeid == null || row.financeid==""){
							return '<button type="button" class="btn btn-info" onclick="surepay()"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span>确认付款</button>';
						}else{
							return '<button type="button" class="btn btn-success">已支付<span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>';
						}
						
					}
				}]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				empid : $('#formSearch #empid').val() ,
				createTime : $('#formSearch #start').val(),
				updateTime : $('#formSearch #end').val()
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
								<label class="col-sm-1 control-label">申请人：</label>
								<div class="col-sm-2">
									<select class="form-control" name="empid"
										id="empid">
										<option value=>----不选择----</option>
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">时间：</label>
								<div class="col-sm-3">
									<input placeholder="开始日期" class="form-control layer-date"
										id="start" name="start">
								</div>
								<div class="col-sm-3">
									<input placeholder="结束日期" class="form-control layer-date"
										id="end" name="end">
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-success" id="clear"><span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="feedbackstart_Tab"></table>
		 
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
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
</body>
</html>