<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
<script type="text/javascript">
	//全局变量，读取当前行的数据
	var rows = null;
	var studHTML = "";
	
	$(function() {
		
		getData();
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		var oButtonInit = new ButtonInit();
		oButtonInit.Init();
		//2.初始化Button的点击事件
		
		$("#btn_query").click(function(){
			getData();
			//刷新页面，
			$('#tb_tuitions').bootstrapTable('refresh');
			//同时发送请求，
			
        });
		
		$("#btn_clear").click(function() {
			fallid : $("#fallid").val(0);
			classid : $("#classid").val(0);
			majorid : $("#majorid").val(0);
			termName : $("#termName").val('');
		});
	});
	function getData(){
		var url = "${pageContext.request.contextPath }/finance/financefee/getData";
		$.post(
			url,
			{
				_method:'POST',//改成PUT提交
				fallid : $("#fallid").val(),
				classid : $("#classid").val(),
				majorid : $("#majorid").val(),
				termname : $("#termName").val()
			},
			initData,
			"json"
		);
	}
	
	function initData(data){
		document.getElementById("sumPay").innerHTML = data.sumPay;
		document.getElementById("sumNonpay").innerHTML = data.sumNonpay;
		document.getElementById("countPay").innerHTML = data.countPay;
		document.getElementById("countNonpay").innerHTML = data.countNonpay;
		
	}
	
	//确认缴费统计
	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_tuitions').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/financefee/paymentList', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
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
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : true, //是否显示父子表
				singleSelect : false, //设置为单选
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
					field : 'level',
					align : 'center',
					title : '届别'
				},{
					field : 'majorName',
					align : 'center',
					title : '专业'
				},{
					field : 'termname',
					align : 'center',
					title : '学期'
				},{
					field : 'classname',
					align : 'center',
					title : '班级'
				},{
					field : 'stuname',
					align : 'center',
					title : '学生名字'
				},{
					field : 'money',
					align : 'center',
					title : '应缴金额'
				},{
					field : 'payment',
					align : 'center',
					title : '已缴金额',
					formatter : function(value, row, index) {
						if(row.payment==null){
							return 0;
						}else{
							return row.payment;
						}
					}
				},{
					field : 'nopay',
					align : 'center',
					title : '未缴金额',
					formatter : function(value, row, index) {
						return row.money-row.payment;
					}
				},],
		       //注册加载子表的事件。注意下这里的三个参数！
                onExpandRow: function (index, row, $detail) {
                	oTableInit.InitSubTable(index, row, $detail);
                }
			});
		};
		
		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				fallid : $("#fallid").val(),
				classid : $("#classid").val(),
				majorid : $("#majorid").val(),
				termname : $("#termName").val(),
			};
			return temp;
		};
		
		//初始化子表格(无线循环)
	    oTableInit.InitSubTable = function InitSubTable(index, row, $detail) {
	        var termid = row.termid;//学期
	        var cur_table = $detail.html('<table></table>').find('table');
	        $(cur_table).bootstrapTable({
	        	classes:'table-no-bordered',
	            url : '${pageContext.request.contextPath}/finance/financefee/feeList/'+termid, //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 10, //每页的记录行数（*）
				//pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
	            columns : [ {
	            	 field: 'state',
	                 checkbox: true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'tuitionname',
					title : '收费项目'
				}, {
					field : 'money',
					title : '金额'
				},]
	        });
	    };
	    
		return oTableInit;
	}
	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
		};
		return temp;
	}
	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	}
	//联动查询专业
	function findfall(data){
		//$('#tb_tuitions').bootstrapTable('refresh');
		var id=$('#'+data).val();
		var url = "${pageContext.request.contextPath }/finance/financefee/findMajor/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
			},
			InitMajor,
			"text"
		);
	}
	function InitMajor(data){
		var opt='<option value="0">选择专业</option>';
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			for (var i = 0; i<datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].major_id+'">'
						+ datas.rows[i].majorName + '</option>';
			}
		}
		$('#majorid').html(opt);
	}
	//联动查询班级
	function findmajor(data){
		//$('#tb_tuitions').bootstrapTable('refresh');
		var id=$('#'+data).val();
		var url = "${pageContext.request.contextPath }/finance/financefee/findClass/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
			},
			InitClass,
			"text"
		);
	}
	function InitClass(data){
		var opt='<option value="0">选择班级</option>';
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			for (var i = 0; i<datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'">'
						+ datas.rows[i].classname + '</option>';
			}
		}
		$('#classid').html(opt);
	}
	</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<!-- 顶部统计框 -->
		<div class="row">
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title label-primary">
						<span class="label label-success pull-right">元</span>
						<h5>缴费总金额</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">
							<label id="sumPay" ></label>
						</h1>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title label-success">
						<span class="label label-info pull-right">元</span>
						<h5>未缴总金额</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">
							<label id="sumNonpay" ></label>
						</h1>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title label-info">
						<span class="label label-primary pull-right">人</span>
						<h5>已缴清的人数</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">
							<label id="countNonpay" ></label>
						</h1>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="ibox float-e-margins">
					<div class="ibox-title label-warning">
						<span class="label label-danger pull-right">人</span>
						<h5>未缴清的人数</h5>
					</div>
					<div class="ibox-content">
						<h1 class="no-margins">
							<label id="countPay" ></label>
						</h1>
					</div>
				</div>
			</div>
		</div>

		<!-- 搜索输入狂 -->
		<div class="panel panel-default">
			<div class="panel-heading">查询条件</div>
			<div class="panel-body">
				<form id="formSearch" class="form-horizontal">
					<div class="form-group">
						<div class="col-sm-2">
							<select class="form-control" name="fallid" id="fallid"
								onchange="findfall(this.id)">
								<option value="0">选择届别</option>
								<c:forEach items="${fallList}" var="f">
									<option value="${f.id }">${f.level}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="majorid" id="majorid"
								onchange="findmajor(this.id)">
								<option value="0">选择专业</option>
								<c:forEach items="${majorlist }" var="e">
									<option value="${e.id }">${e.majorName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="classid" id="classid"
								onchange="findclass(this.id)">
								<option value="0">选择班级</option>
								<c:forEach items="${classlist }" var="el">
									<option value="${el.id }">${el.classname}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<select class="form-control" name="termName" id="termName"
								onchange="findterm(this.id)">
								<option value="">选择学期</option>
								<option value=第一学期>第一学期</option>
								<option value=第二学期>第二学期</option>
								<option value=第三学期>第三学期</option>
								<option value=第四学期>第四学期</option>
								<option value=第五学期>第五学期</option>
								<option value=第六学期>第六学期</option>
							</select>
						</div>
						<div class="col-sm-1">
							<button type="button" class="btn btn-primary" id="btn_query">
								<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询
							</button>
						</div>
						<div class="col-sm-1">
							<button type="button" class="btn btn-success" id="btn_clear">
								<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>

		<!-- table代码就这些，用js构建表格 -->
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>缴费情况统计</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a>
				</div>
				<div class="ibox-content">
					<table id="tb_tuitions"></table>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</html>