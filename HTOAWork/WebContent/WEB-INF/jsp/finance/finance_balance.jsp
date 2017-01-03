<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
    //全局变量，读取当前行的数据
	var rows=null;
	var lows=null;
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
			$("#formSearch #btn_query").click(function(){
				$('#tb_departments').bootstrapTable('refresh');
            });
			$("#formSearch #btn_clear").click(function() {
				$("#formSearch #search_identifying").val("");
				$("#formSearch #search_typeId").val("");
				$("#formSearch #search_startTime").val('');
				$("#formSearch #search_endTime").val('');
				$("#formSearch #search_financeman").val("");
				$("#formSearch #search_status").val(1);
				$('#tb_departments').bootstrapTable('refresh');
			});
			//修改按钮事件
			/* $("#btn_add").click(function(){
                if(rows == null || rows.id==null){
                	swal("错误", "请算着需要修改的数据项！", "error");
                }else{
                	$('#updateModal').modal();
                	id:$("#updateModal #id").val(rows.id);
                	typeName:$("#updateModal #typeName").val(rows.type.typename);
                	money:$("#updateModal #money").val(rows.money);
                	occurTime:$("#updateModal #occurTime").val(rows.occurtime);
                	reportMan:$("#updateModal #reportMan").val(rows.reportEmp.empname);
	                financeMan:$("#updateModal #financeMan").val(rows.financeEmp.empname);
                	systime:$("#updateModal #financeMan").val(rows.systime);
                	financeRemark:$("#updateModal #financeRemark").val(rows.financeRemark);
                }
            }); */
			//删除按钮事件
			$("#btn_delete").click(function(){
                if(rows == null || rows.id==null){
                	swal("删除失败！", "请选择需要删除的数据项", "error");
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
	                		deleteFinanceBalance();
	                	} else {
	                    	swal("已取消", "您取消了删除操作！", "error");
	                	}
	            	});
                }
            });
		});
		
    	function getMeney(){
    		swal({
    			title : "操作后不可修改，确定操作？",
    			text : "",
    			type : "info",
    			showCancelButton : true,
    			confirmButtonColor : "#DD6B55",
    			confirmButtonText : "朕准了",
    			cancelButtonText : "朕就不",
    			closeOnConfirm : false,
    			closeOnCancel : false
    		}, function(isConfirm) {
    			if (isConfirm) {
    				//调用删除函数
    				reimburse_sure();
    			} else {
    				swal("已取消", "您取消了当前操作！", "error");
    			}
    		}); 
    	}
    	function deleteM(){
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
               		deleteFinanceBalance();
               	} else {
                   	swal("已取消", "您取消了删除操作！", "error");
               	}
           	});
    	}
    	function deleteFinanceBalance(){
    		var url = "${pageContext.request.contextPath}/finance/financebalance/"+rows.id;
    		$.post(
    			url,
    			{
    				_method : 'DELETE'
    			},
    			isSure,
    			"text"
    		);
    	}
    	function reimburse_sure(){
    		//alert(rows.id);
    		var url = "${pageContext.request.contextPath}/finance/financebalance/"+rows.id;
    		$.post(
    			url,
    			{
    				_method : 'GET',
    				status : rows.status
    			},
    			isSure,
    			"text"
    		);
    	}
    	function isSure(data){
    		if (data > 0) {
    			swal({
    				title : "成功",
    				text : "你已经完成当前操作",
    				type : "success"
    			});
    		} else {
    			swal("操作失败", "请稍后再进行操作！", "error");
    		}
    		$('#tb_departments').bootstrapTable('refresh');
    	}
    	function getInfo(){
    		
    	}
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_departments').bootstrapTable({
					url : '${pageContext.request.contextPath}/finance/financebalance/balanceInfo', //请求后台的URL（*）
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
					pageSize : 7, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					//search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : 470, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "ID", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,
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
						title : '编号',
						visible : false
					}, {
						align:"center",
						field : 'type.typename',
						title : '收支名称'
					},{
						align:"center",
						field : 'occurtime',
						title : '发生时间',
						formatter : function(value, row, index){
							return value.substring(0, 19);
						}
					},{
						align:"center",
						field : 'money',
						title : '发生金额(元)',
						formatter : function(value, row, index){
							if(row.status==0){
								return '<button type="button" class="btn btn-xs btn-primary">'+row.money+'元</button>';
							}else if(row.status==2){
								return '<button type="button" class="btn btn-xs btn-warning">'+row.money+'元</button>';
							}else if(row.status==3 || row.status==5){
								return '<button type="button" class="btn btn-xs btn-danger">'+row.money+'元</button>';
							}else if(row.status==4 || row.status==6){
								return '<button type="button" class="btn btn-xs btn-info">'+row.money+'元</button>';
							}
						}
					}, {
						align:"center",
						field : 'reportemp',
						title : '申报人',
						formatter : function(value, row, index){
							if(row.reportEmp==null){
								return "无";
							}else{
								return row.reportEmp.empname;
							}
						}
					}, {
						align:"center",
						field : 'financeemp',
						title : '确认操作人',
						formatter : function(value, row, index){
							if(row.financeEmp==null){
								return "无";
							}else{
								return row.financeEmp.empname;
							}
						}
					},  {
						align:"center",
						field : 'status',
						title : '状态',
						formatter : function(value, row, index){
							//0、已收款，不能修改删除
							//1、已删除，已经删除的的财务数据，不做显示也不做统计
							//2、已付款，不能修改删除
							//3、未付款，不能修改删除
							//4、未收款，不能修改删除
							//5、新增的财务数据，可修改删除，为待付款状态，付款后状态为2，不能删除
							//6、新增的财务数据，可修改删除，为待收款状态，收款后状态为0，不能删除
							if(row.status == 0){
								return "已收款";
							}else if(row.status == 2){
								return "已付款";
							}else if(row.status == 4){
								return "待收款";
							}else if(row.status == 3){
								return "待付款";
							}else if(row.status == 5){
								return "待付款";
							}else if(row.status == 6){
								return "待收款";
							}
							
						}
					},  {
						field : 'financeRemark',
						title : '备注'
					},{
						field : 'deal',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							if(row.status==4){
								var b = '<button id="btn_add" type="button" class="btn btn-xs btn-danger" onclick="getMeney();"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>收款</button>';
								return b;
							}else if(row.status==0){
								var b = '<button type="button" class="btn btn-xs btn-primary">无可操作项</button>';
								return b;
							}else if(row.status==2){
								var b = '<button type="button" class="btn btn-xs btn-warning"">无可操作项</button>';
								return b;
							}else if(row.status==3){
								var b = '<button id="btn_add" type="button" class="btn btn-xs btn-danger" onclick="getMeney();"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>付款</button>';
								return b;
							}else if(row.status==5){
								var b = '<button id="btn_add" type="button" class="btn btn-xs btn-danger" onclick="getMeney();"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>付款</button>&nbsp;&nbsp;';
								var d = '<button id="btn_add" type="button" class="btn btn-xs btn-warning" onclick="deleteM();"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>';
								return b+d;
							}else if(row.status==6){
								var b = '<button id="btn_add" type="button" class="btn btn-xs btn-info" onclick="getMeney();"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>收款</button>&nbsp;&nbsp;';
								var d = '<button id="btn_add" type="button" class="btn btn-xs btn-warning" onclick="deleteM();"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>';
								return b+d;
							}
						}
					}]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					identifying : $("#formSearch #search_identifying").val(),
					typeId : $("#formSearch #search_typeId").val(),
					startTime : $("#formSearch #search_startTime").val(),
					endTime : $("#formSearch #search_endTime").val(),
					financeman : $("#formSearch #search_financeman").val() ,
					status : $("#formSearch #search_status").val()
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
		function addFinanceBalance(){
			if(!validateForm($("#addForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/finance/financebalance/addBalance2";
			$.post(
				url,
				{
					typeId:$("#myModal #typeName").val(),
					occurtime:$("#myModal #occurTime").val(),
	                money:$("#myModal #money").val(),
	                reportman:$("#myModal #reportMan").val(),
	                financeman:$("#myModal #financeMan").val(),
	                financeRemark:$("#myModal #financeRemark").val()
				},
				addFinanceBalanceHandle,
				"text"
			);	
			//清空新增div中的数据
			typeName:$("#myModal #typeName").val('');
			ouccerTime:$("#myModal #ouccerTime").val('');
            money:$("#myModal #money").val('');
            reportMan:$("#myModal #reportMan").val('');
            financeRemark:$("#myModal #financeRemark").val('');
			$("#myModal").modal('hide');
		}
		function addFinanceBalanceHandle(data){
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
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
		}
		
		//update department ，ajax提交
		function updateFinanceBalance(){
			var url = "${pageContext.request.contextPath }/finance/financebalance/"+rows.id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#updateModal #id").val(),
					typeId:$("#updateModal #typeName").val(),
					occurtime:$("#updateModal #occurTime").val(),
	                financeRemark:$("#updateModal #financeRemark").val(),
	                reportman:$("#updateModal #reportMan").val(),
	                financeman:$("#updateModal #financeMan").val(),
	                money:$("#updateModal #money").val(),
	                createTime:$("#updateModal #createTime").val()
				},
				addUpdateHandle,
				"text"
			);	
			
			//清空新增div中的数据
			//清空新增div中的数据
			typeName:$("#myModal #typeName").val('');
			ouccerTime:$("#myModal #ouccerTime").val('');
            money:$("#myModal #money").val('');
            reportMan:$("#myModal #reportMan").val('');
            financeRemark:$("#myModal #financeRemark").val('');
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
				swal("修改错误！", "请检查你输入的数据！", "error");
			}
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
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
			            </div>
		            </div>
		            <div class="ibox-content">
		            <form id="formSearch" class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-1" for="search_identifying">收支</label>
							<div class="col-sm-2">
								 <select id="search_identifying" class="form-control">
								 	<option value="">--不选择--</option>
		           					<option value="0">收入</option>
		           					<option value="1">支出</option>
		          					</select>      
							</div>
							<label class="control-label col-sm-1" for="search_startTime">开始时间</label>       
							<div class="col-sm-3">
								<input placeholder="请选择开始时间" class="form-control layer-date" id="search_startTime"name="search_startTime"></input>         
							</div>
							<label class="control-label col-sm-1" for="search_endTime">结束时间</label>
							<div class="col-sm-3">
								<input placeholder="请选择结束时间" class="form-control layer-date" id="search_endTime"name="search_endTime"></input>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-1" for="search_typeId">财务类别</label>       
							<div class="col-sm-2">
								<select class="form-control" id="search_typeId"
									name="search_typeId">
									<option value="">--不选择--</option>
									<c:forEach items="${typeList}" var="t">
										<option value="${t.id }">${t.typename}</option>
									</c:forEach>
								</select>         
							</div>
							<label class="control-label col-sm-1" for="search_financeman">经办人</label>
							<div class="col-sm-2">
								<select class="form-control" name="search_financeman" id="search_financeman">
									<option value="">--不选择--</option>
									<c:forEach items="${empList}" var="e">
										<option value="${e.id }">${e.empname}</option>
									</c:forEach>
								</select>
							</div>
							<label class="control-label col-sm-1" for="search_status">状态</label>
							<div class="col-sm-2">
								 <select id="search_status" class="form-control">
								 	<option value="1">--不选择--</option>
		           					<option value="0">已收款</option>
		           					<option value="2">已付款</option>
		           					<option value="3">待付款</option>
		           					<option value="4">待收款</option>
		          				</select>      
							</div>
							<div class="col-sm-2">
								
								<button type="button" id="btn_query" class="btn btn-primary ">查询</button>
								<button type="button" class="btn btn-success" id="btn_clear">清除</button>
							</div>
						</div>
						</form>
					</div>       
				</div>
				<div id="toolbar" class="btn-group">
					<button id="btn_add" type="button" class="btn btn-w-m btn-primary">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					</button>
					<!-- <button id="btn_edit" type="button" class="btn btn-w-m btn-success">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
					</button>
					<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button> -->
				</div>
				<!-- table代码就这些，用js构建表格 -->
				<div class="ibox float-e-margins">
		        	<div class="ibox-title">
		            	<h5>财务结算表</h5>
			            <div class="ibox-tools">
			                <a class="collapse-link">
			                    <i class="fa fa-chevron-up"></i>
			                </a>
			            </div>
		            <div class="ibox-content">
						<table id="tb_departments"></table>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">新增财务记录</h4>
        </div>
        <div class="ibox-content">
			<form class="form-horizontal m-t" id="addForm" novalidate="novalidate">
				<div class="form-group">
					<label for="typeName" class="col-sm-3 control-label">收支名称</label>
					<div class="col-sm-8">
						<select class="form-control" name="typeName" id="typeName">
							<c:forEach items="${typeList}" var="t">
								<option value="${t.id }">${t.typename}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="occurTime" class="control-label col-sm-3">发生时间</label>
					<div class="col-sm-8">
						<input placeholder="发生时间" class="form-control layer-date"
						id="occurTime" name="occurTime">
					</div>
				</div>
				<div class="form-group">
					<label for="money" class="control-label col-sm-3">发生金额</label>
					<div class="col-sm-8">
						<input type="number" class="form-control" id="money" name="money">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">申&nbsp;&nbsp;报&nbsp;&nbsp;人</label>
					<div class="col-sm-8">
						<select class="form-control" name="reportMan" id="reportMan">
							<option value=>---无---</option>
							<c:forEach items="${empList}" var="e">
								<option value="${e.id }">${e.empname}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="financeMan" class="col-sm-3 control-label">操&nbsp;&nbsp;作&nbsp;&nbsp;人</label>
					<div class="col-sm-8">
						<input type="hidden"  id="financeMan" name="financeMan" value="${emp.id}">
						<input type="text" class="form-control col-sm-8" value="${emp.empname}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="financeRemark" class="col-sm-3 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</label>
					<div class="col-sm-8">
						<input type="text" class="form-control col-sm-8" id="financeRemark" name="financeRemark">
					</div>
				</div>
			<div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	          <button type="button" class="btn btn-primary" onclick="addFinanceBalance()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
	        </div>
	        </form>
        </div>
      </div>
    </div>
  </div>
	
	<!-- update dialog -->
	<%-- <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">修改</h4>
        </div>
        <div>
			<div class="form-horizontal m-t">
				<div class="form-group">
					<label for="typeName" class="col-sm-3 control-label">收支名称</label>
					<div class="col-sm-8">
						<select class="form-control" name="typeName" id="typeName">
							<c:forEach items="${typeList}" var="t">
								<option value="${t.id }">${t.typename}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="occurTime" class="control-label col-sm-3">发生时间</label>
					<div class="col-sm-8">
						<input placeholder="发生时间" class="form-control layer-date"
						id="occurTime" name="occurTime">
					</div>
				</div>
				<div class="form-group">
					<label for="money" class="control-label col-sm-3">发生金额</label>
					<div class="col-sm-8">
						<input type="number" class="form-control" id="money" name="money">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">申&nbsp;&nbsp;报&nbsp;&nbsp;人</label>
					<div class="col-sm-8">
						<select class="form-control" name="reportMan" id="reportMan">
							<c:forEach items="${empList}" var="e">
								<option value="${e.id }">${e.empname}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="financeMan" class="col-sm-3 control-label">操&nbsp;&nbsp;作&nbsp;&nbsp;人</label>
					<div class="col-sm-8">
						<input type="hidden"  id="financeMan" name="financeMan" value="${emp.id}">
						<input type="text" class="form-control col-sm-8" value="${emp.empname}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="financeRemark" class="col-sm-3 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</label>
					<div class="col-sm-8">
						<input type="text" class="form-control col-sm-8" id="financeRemark" name="financeRemark">
					</div>
				</div>
			</div>
		<div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateFinanceBalance()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
        </div>
        </div>
        
      </div> 
    </div>
  </div>--%>
  
</body>
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script type="text/javascript">
		//日期范围限制
		var occurTime = {
			elem : '#occurTime',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			istoday : true,
			max : laydate.now(), //最大日期
			istime : true,
		};
		laydate(occurTime);
		
		//日期范围限制
		var start = {
			elem : '#search_startTime',
			format : 'YYYY/MM/DD hh:mm:ss',
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#search_endTime',
			format : 'YYYY/MM/DD hh:mm:ss',
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
	            	occurTime: "required",
	                money: "required"            	
	            },
	            messages: {
	            	occurTime: icon + "请选择发生时间",
	            	money: icon + "请填写金额"
	            }
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
	</script>
</html>