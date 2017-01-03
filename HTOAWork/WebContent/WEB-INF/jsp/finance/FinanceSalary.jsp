<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>员工月份工资生成</title>
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
			
			//新增按钮事件*************************
			$("#btn_add").click(function(){
				getsalary(1);
	        });
			//修改按钮事件
			$("#btn_edit").click(function(){
				//把内容放到更新的列表
				if(rows==null){
					parent.layer.alert('请选你要发放的工资！');
					return;
				}
				if(rows.financeStatus==1){
					parent.layer.alert('该工资已发放！');
					return;
				}
				updateStudent();
	        });
			//工资详细信息
			$("#btn_more").click(function(){
				//把内容放到更新的列表
				if(rows==null){
					parent.layer.alert('请选你要查看的工资！');
					return;
				}
				getmore(rows.empid,rows.time);
	        });
			//删除按钮事件
			//*************************************************************************按钮事件
			$('#btn_delete').click(function () {
				if(rows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				if(rows.financeStatus==1){
					parent.layer.alert('该工资已发放，不能删除！');
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
	                	deleteStudent();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
	        });
			//条件查询click事件注册
			$("#query").click(function() {
				$("#tb_departments").bootstrapTable('refresh');
			});
		});

	    var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_departments').bootstrapTable({
					url : '${pageContext.request.contextPath}/finance/financesalary/salaryList', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType : "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : oTableInit.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 5, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect : true, //设置为单选
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
		            },//单击row事件
					columns : [ {
						checkbox : true
					}, {
						field : 'id',
						title : 'ID',
						visible:false
					},  {
						field : 'empname',
						title : '员工'
					}, {
						field : 'dep.depname',
						title : '部门'
					}, {
						field : 'basic.basicsalary',
						title : '基本工资'
					},{
						field : 'jobsalary',
						title : '岗位工资'
					}, {
						field : 'prizemoney',
						title : '奖金'
					}, {
						field : 'deductmoney',
						title : '扣款总额'
					}, {
						field : 'shouldsalary',
						title : '应发工资'
					}, {
						field : 'factsalary',
						title : '实发工资'
					}, {
						field : 'taxsalary',
						title : '税前工资'
					}, {
						field : 'financeStatus',
						title : '发放情况',
						formatter : function(value, row, index) {
							var id = row.financeStatus;
							var text = "";
							if (id == 0) {
								text = "未发放";
							}else if(id == 1) {
								text = "已发放";
							}
							return text;
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
					time : $("#find_time").val(),
					depid : $("#find_depid").val(),
					empid : $("#find_empid").val(),
					financeStatus : $("#find_financeStatus").val(),
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
		
		//生成该月员工工资
		function addStudent(){
			
			var url = "${pageContext.request.contextPath }/finance/financesalary/add"
			$.post(
				url,
				{	
					_method:'PUT',
				},
				addStudentHandle,
				"text"
			);	
			$("#window_add").modal('hide');
			
		}
		function addStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
			}else if(data==-1){
				swal("有相同数据", "已跳过该数据的新增", "error");
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
			$('#tb_departments').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		//发放，ajax提交,PUT
		function updateStudent(){
			var id =rows.id; 
			var url = "${pageContext.request.contextPath }/finance/financesalary/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					time : rows.time,
					money : rows.money,
				},
				updateStudentHandle,
				"text"
			);	
		}
		function updateStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经发放该工资",
                    type: "success"
                });
			}else{
				swal("权限不足", "您不是员工，不能发放工资", "error");
			}
			//刷新表格
			$('#tb_departments').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		
		//***************************************************************************************删除
		//删除，ajax提交,DELETE
		function deleteStudent(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/finance/financesalary/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteStudentHandle,
				"text"
			);	
		}
		function deleteStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_departments').bootstrapTable('refresh');
		}
		
		//部门与员工联动
		function findemp(data){
			var id=$('#'+data).val();
			var url = "${pageContext.request.contextPath }/finance/financesalary/findemp/"+id;
			$.post(
				url,
				{
					_method:'POST',//改成PUT提交
				},
				initemp,
				"text"
			);	
		}
		function initemp(data){
			var opt='<option value="">--------------</option>';
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				for (var i = 0; i<datas.rows.length; i++) {
					opt += '<option value="'+datas.rows[i].id+'">'
							+ datas.rows[i].empname + '</option>';
				}
			}
			$('#find_empid').html(opt);
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
                                <label class="col-sm-1 control-label">月份:</label>
                                <div class="col-sm-3">
									<input placeholder="时间" class="form-control layer-date"
										id="find_time">
								</div>
                                <label class="col-sm-1 control-label">部门：</label>
                                <div class="col-sm-3">
	                                <select id="find_depid" class="form-control" onchange="findemp(this.id);">
	                                	<option value="0">--------------------</option>
	                                    <c:forEach items="${dep}" var="dep">
	                                    	<option value="${dep.id}">${dep.depname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                                <label class="col-sm-1 control-label">员工：</label>
                                <div class="col-sm-3">
	                                <select id="find_empid" class="form-control">
	                                	<option value="">--------------------</option>
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
							</div>
							<div class="form-group">
                                <label class="col-sm-1 control-label">发放情况:</label>
                                <div class="col-sm-3">
                                    <select id="find_financeStatus" class="form-control">
                                    	<option value="">----</option>
                                    	<option value="0">未发放</option>
                                    	<option value="1">已发放</option>
                                    </select>
                                </div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">查询</button>
								</div>
                            </div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>生成该月员工工资
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal" >
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>发放
			</button>
			<button id="btn_more" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" >
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>详细信息
			</button>
		</div>
		<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">生成员工工资</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addForm" novalidate="novalidate">
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">工资计算方式：</label>
	                            <div class="col-sm-8">
                                	<select id="typeid" name="baoxiaotypeid" class="form-control" onchange="getsalary(this.value);">
	                                    <c:forEach items="${list}" var="list">
	                                    	<option value="${list.id}">第${list.id}种</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                        	</div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">迟到、早退：</label>
	                            <div class="col-sm-8">
                                    <input id="latesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">加班：</label>
	                            <div class="col-sm-8">
                                    <input id="overtimesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">事假：</label>
	                            <div class="col-sm-8">
                                    <input id="leavesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">旷工：</label>
	                            <div class="col-sm-8">
                                    <input id="withoutleavesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">值班：</label>
	                            <div class="col-sm-8">
                                    <input id="dutysalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">病假：</label>
	                            <div class="col-sm-8">
                                    <input id="sickleavesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="addStudent()" class="btn btn-primary">生成</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<div class="modal inmodal" id="window_more" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addForm" novalidate="novalidate">
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">初始总工资：</label>
	                            <div class="col-sm-8">
                                    <input id="sumsalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">迟到、早退：</label>
	                            <div class="col-sm-8">
                                    <input id="latesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">加班：</label>
	                            <div class="col-sm-8">
                                    <input id="overtimesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">事假：</label>
	                            <div class="col-sm-8">
                                    <input id="leavesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">旷工：</label>
	                            <div class="col-sm-8">
                                    <input id="withoutleavesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">值班：</label>
	                            <div class="col-sm-8">
                                    <input id="dutysalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                        	<div class="form-group">
                            	<label class="col-sm-3 control-label">病假：</label>
	                            <div class="col-sm-8">
                                    <input id="sickleavesalary" type="text" class="form-control" disabled="disabled">
                            	</div>
                        	</div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments"></table>
	</div>
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script>
	//日期范围限制
	var start = {
		elem : '#find_time',
		format : 'YYYY/MM',
		max : '2099-06-16 23:59:59', //最大日期
		istime : true,
		istoday : false,
	};
	laydate(start);
</script>
</body>
</html>