<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
			$("#btn_query").click(function(){
				$('#tb_departments').bootstrapTable('refresh');
            });
			$("#btn_clear").click(function() {
				typename : $("#txt_search_departmentname").val("");
				identifying : $("#txt_search_statu").val("");
			});
			//修改按钮事件
			$("#btn_edit").click(function(){
                if(rows == null || rows.id==null){
                	swal("错误", "请算着需要修改的系！", "error");
                }else{
                	$('#updateModal').modal();
                	id:$("#updateModal #id").val(rows.id);
                	typename:$("#updateModal #typename").val(rows.typename);
                	identifying:$("#updateModal #identifying").val(rows.identifying);
                	payRemark:$("#updateModal #payRemark").val(rows.payRemark);
	                createTime:$("#updateModal #createTime").val(rows.createTime);
                }
            });
			//删除按钮事件
			$("#btn_delete").click(function(){
                if(rows == null || rows.id==null){
                	swal("删除失败！", "请选择需要删除的系", "error");
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
	                		deleteFinanceType();
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
					url : '${pageContext.request.contextPath}/finance/financeType/paytypeList', //请求后台的URL（*）
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
						title : '编号'
					}, {
						field : 'typename',
						title : '收支名称'
					},{
						field : 'identifying',
						title : '收支类别',
						formatter : function(value, row, index){
							if(value=="0"){
								return "收入";
							}else if(value=="1"){
								return "支出";
							}
						}
					},{
						field : 'createTime',
						title : '创建时间',
						formatter : function(value, row, index){
							if(value==null){
								return "未曾更改";
							}else{
								return value.substring(0, 10);
							}
						}
					}, {
						field : 'updateTime',
						title : '修改时间',
						formatter : function(value, row, index){
							if(value==null){
								return "未曾更改";
							}else{
								return value.substring(0, 10);
							}
						}
					},{
						field : 'payRemark',
						title : '说明'
					}]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					identifying : $("#txt_search_statu").val(),
					typename : $("#txt_search_departmentname").val(),
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
		function addFinanceType(){
			var url = "${pageContext.request.contextPath }/finance/financeType/addPayType";
			$.post(
				url,
				{
					typename:$("#myModal #typename").val(),
					identifying:$("#myModal #identifying").val(),
	                payRemark:$("#myModal #payRemark").val(),
				},
				addFinanceTypeHandle,
				"text"
			);	
			
			//清空新增div中的数据
			deptName:$("#myModal #typename").val('');
            status:$("#myModal #identifying").val('');
            Remark:$("#myModal #payRemark").val('');
		}
		function addFinanceTypeHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
				$('#tb_departments').bootstrapTable('refresh');
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
			$('#tb_departments').bootstrapTable('refresh');
		}
		
		//update department ，ajax提交
		function updateFinanceType(){
			var id =rows.id;
			var url = "${pageContext.request.contextPath }/finance/financeType/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#updateModal #id").val(),
					typename:$("#updateModal #typename").val(),
					identifying:$("#updateModal #identifying").val(),
	                payRemark:$("#updateModal #payRemark").val(),
	                createTime:$("#updateModal #createTime").val()
				},
				addUpdateHandle,
				"text"
			);	
			
			//清空新增div中的数据
			typename:$("#updateModal #typename").val('');
			identifying:$("#updateModal #identifying").val('');
            payRemark:$("#updateModal #payRemark").val('');
            createTime:$("#updateModal #createTime").val('');
		}
		function addUpdateHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "Update is seccussed！",
                    type: "success"
                });
				$('#tb_departments').bootstrapTable('refresh');
			}else{
				swal("修改错误！", "请检查你输入的数据！", "error");
			}
			$('#tb_departments').bootstrapTable('refresh');
		}
		
		//delect Dept 
		//删除学生，ajax提交
		function deleteFinanceType(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/finance/financeType/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteFinanceTypeHandle, 
				"text"
			);	
		}
		
		function deleteFinanceTypeHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else {
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
						<label class="control-label col-sm-1" for="txt_search_departmentname">收支名称</label>       
						<div class="col-sm-3">
							<input type="text" class="form-control"id="txt_search_departmentname">       
						</div>
						<label class="control-label col-sm-1" for="txt_search_statu">收支类别</label>
						<div class="col-sm-3">
							 <select id="txt_search_statu" class="form-control">
							 	<option value="">--不选择--</option>
            					<option value="0">收入</option>
            					<option value="1">支出</option>
           					</select>      
						</div>
						<div class="col-sm-3">
							<button type="button" id="btn_query" class="btn btn-primary">查询</button>
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
          <h4 class="modal-title" id="myModalLabel">新增</h4>
        </div>
        <div class="modal-body">
        <!-- 新增系别 -->
          <div class="form-group">
            <label for="typename">收支名称</label>
            <input type="text" name="typename" class="form-control" id="typename" >
          </div>
          <div class="form-group">
            <label for="identifying">收支类型</label>
            <select name="identifying" id="identifying" class="form-control">
            	<option value="0">收入</option>
            	<option value="1">支出</option>
            </select>
          </div>
          <div class="form-group">
            <label for="payRemark">说明</label>
            <textarea name="payRemark" rows="3" class="form-control" id="payRemark">
            </textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addFinanceType()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
        </div>
      </div>
    </div>
  </div>
	
	<!-- update dialog -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">修改</h4>
        </div>
        <div class="modal-body">
         <!-- Hidden -->
        <input type="hidden" name="id" id="id">
        <input type="hidden" name="createTime" id="createTime">
        <!-- 新增系别 -->
          <div class="form-group">
            <label for="typename">收支名称</label>
            <input type="text" name="typename" class="form-control" id="typename" >
          </div>
          <div class="form-group">
            <label for="identifying">收支类型</label>
            <select name="identifying" id="identifying" class="form-control">
            	<option value="0">收入</option>
            	<option value="1">支出</option>
            </select>
          </div>
          <div class="form-group">
            <label for="payRemark">说明</label>
            <textarea name="payRemark" rows="3" class="form-control" id="payRemark">
            </textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateFinanceType()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
        </div>
      </div>
    </div>
  </div>
  
</body>
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</html>