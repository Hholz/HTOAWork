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
	var arr = new Array();
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
			$('#formSearch #majorid').val('0');
			$('#formSearch #classid').val('0');
			$('#formSearch #termid').val('');
			$('#formSearch #fallid').val('0');
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
	});
	function getInfo(obj){
		//alert(obj);
		$('#window_update #studentid').val(rows.stuid);
		$('#window_update #chargeid').val(rows.id);
		$('#window_update #info').val(rows.classname+"班"+rows.stuname+rows.termname+"学费");
		$('#window_update #should').val(rows.money);
		$('#window_update #pay').val(rows.payment);
		$('#window_update #money').val(rows.money-rows.payment);
		$('#window_update').modal();
	}
	function surepay(){
		swal({
			title : "确定收费？",
			text : "",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "收费",
			cancelButtonText : "取消",
			closeOnConfirm : false,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				//调用删除函数
				reimburse_sure();
			} else {
				swal("已取消", "您取消了收费操作！", "error");
			}
		}); 
	}
	function reimburse_sure(){
		var url = "${pageContext.request.contextPath}/finance/financebalance/addBalance";
		$.post(
			url,
			{
				typeId:'11',
				chargeid:$('#window_update #chargeid').val(),
                money:$('#window_update #money').val(),
                studId:$('#window_update #studentid').val()
			},
			isSure,
			"text"
		);
		$('#window_update').modal('hide');
	}
	function isSure(data){
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成收费操作",
				type : "success"
			});
		} else {
			swal("支付失败", "请稍后再确认操作！", "error");
		}
		$("#feedbackstart_Tab").bootstrapTable('refresh');
	}
	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#feedbackstart_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/schoolFeePaySure/startlist', //请求后台的URL（*）
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
				height : 400, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
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
					title : '应收金额'
				},{
					field : 'payment',
					align : 'center',
					title : '已收金额',
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
					title : '未收金额',
					formatter : function(value, row, index) {
						return row.money-row.payment;
					}
				},{
					field : 'status',
					title : '状态',
					align : 'center',
					formatter : function(value, row, index) {
						if(row.ispay==0){
							var b = '未收费';
							return b;
						}else if(row.ispay==1){
							var b = '已收清';
							return b;
						}
						else if(row.ispay==3){
							var b = '未缴清';
							return b;
						}
					}
				},{
					field : 'deal',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						if(row.ispay==0){
							var b = '<button id="btn_add" type="button" class="btn btn-sm btn-danger" onclick="getInfo();"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>双击收费</button>';
							return b;
						}else if(row.ispay==1){
							arr.push(row.id);
							var b = '<button id="btn_add" type="button" class="btn btn-sm btn-primary" data-toggle="modal"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>已收费</button>';
							return b;
						}
						else if(row.ispay==3){
							arr.push(row.id);
							var b = "<button type='button' class='btn btn-sm btn-danger' onclick='getInfo();'><span class='glyphicon glyphicon-plus' aria-hidden='true'></span>双击收费</button>";
							return b;
						}
					}
				}]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset , //页码
				fallid : $('#formSearch #fallid').val() ,
				termname : $('#formSearch #termid').val(),
				classid : $('#formSearch #classid').val(),
				majorid : $('#formSearch #majorid').val() 
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
	function surepay2(){
		swal({
			title : "确定收费？",
			text : "",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "收费",
			cancelButtonText : "取消",
			closeOnConfirm : false,
			closeOnCancel : false
		}, function(isConfirm) {
			if (isConfirm) {
				//调用删除函数
				batchPay();
			} else {
				swal("已取消", "您取消了确认收费操作！", "error");
			}
		});
	}
	function batchPay(){
		$('#feedbackstart_Tab').bootstrapTable('uncheckBy', {field:'id', values:arr});
        var ids = "";
		var moneys = "";
		var stuids = "";
		var termnames = "";
		var stuList = $('#feedbackstart_Tab').bootstrapTable("getSelections");
		for(var i =0 ;i<stuList.length;i++){
			if(i!=stuList.length-1){
				ids = ids +stuList[i].id+",";
				stuids = stuids +stuList[i].stuid+",";
				moneys = moneys +stuList[i].money+",";
				termnames = termnames +stuList[i].termname+",";
			}else{
				ids = ids +stuList[i].id;
				stuids = stuids +stuList[i].stuid;
				moneys = moneys +stuList[i].money;
				termnames = termnames +stuList[i].termname;
			}
		}
		if(ids==""){
			swal("收费失败", "请先选择未收费的项！", "warning");
			return;
		}
		var url = "${pageContext.request.contextPath}/finance/financebalance/batchPay";
		$.post(
			url,
			{
				ids : ids,
				moneys : moneys,
				stuids : stuids,
				termnames : termnames,
				typeid : '11'
			},
			resetEmp,
			"text"
		); 
	}
	function resetEmp(data){
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成批量确认收费操作！",
				type : "success"
			});
		} else {
			swal("确认收费失败", "请检查你选择的数据！", "error");
		}
		//刷新表格
		$('#feedbackstart_Tab').bootstrapTable('refresh');
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
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<!-- <label class="col-sm-1 control-label">届别：</label> -->
								<div class="col-sm-2">
									<select class="form-control" name="fallid" id="fallid">
										<option value="0">----选择届别----</option>
										<c:forEach items="${falllist}" var="f">
											<option value="${f.id }">${f.level}</option>
										</c:forEach>
									</select>
								</div>
								<!-- <label class="col-sm-1 control-label">专业：</label> -->
								<div class="col-sm-2">
									<select class="form-control" name="majorid" id="majorid">
										<option value="0">----选择专业----</option>
										<c:forEach items="${majorlist }" var="e">
											<option value="${e.id }">${e.majorName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-2">
									<select class="form-control" name="classid" id="classid">
										<option value="0">----选择班级----</option>
										<c:forEach items="${classlist }" var="e">
											<option value="${e.id }">${e.classname}班</option>
										</c:forEach>
									</select>
								</div>
								<!-- <label class="col-sm-1 control-label">学期：</label> -->
								<div class="col-sm-2">
									<select class="form-control" name="termid" id="termid">
										<option value="">---选择学期---</option>
										<option value=第一学期>第一学期</option>
				          				<option value=第二学期>第二学期</option>
				          				<option value=第三学期>第三学期</option>
				          				<option value=第四学期>第四学期</option>
				          				<option value=第五学期>第五学期</option>
				          				<option value=第六学期>第六学期</option>
									</select>
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
		<div id="toolbar" class="btn-group">
			<!-- <button id="btn_add" type="button" class="btn btn-w-m btn-primary"
				data-toggle="modal" data-target="#window_add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success"
				data-toggle="modal" data-target="#window_update">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button> -->
			<button id="btn_pay" type="button" class="btn btn-w-m btn-warning" onclick="surepay2()">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>批量收费
			</button><font color="red">&nbsp;&nbsp;注意：批量收费只适用于一次性全部缴清的学生</font>
		</div>
		<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated lightSpeedIn">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">收费详情(单位:人民币/元)</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<input type="hidden" id="studentid">
							<input type="hidden" id="chargeid">
							<div class="form-group">
								<label class="col-sm-3 control-label">缴费学生信息：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="info" readonly="readonly"> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">应收费总金额：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="should" readonly="readonly"> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">已收费总金额：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="pay" readonly="readonly"> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">本次收费金额：</label>
								<div class="col-sm-8">
									<input type="number" class="form-control" id="money"> 
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="surepay();"
									class="btn btn-primary">保存</button>
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