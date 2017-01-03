<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>学期管理</title>
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
			
			$("#btn_add_term").click(function(){
                $('#myModel').modal();
            });
			
			$("#btn_query").click(function(){
				$('#tb_departments').bootstrapTable('refresh');
            });
			//修改按钮事件
			$("#btn_edit").click(function(){
                if(rows == null && rows.id==null){
                	swal("错误", "请算着需要修改的系！", "error");
                }else{
                	$('#updateModal').modal();
                	$("#updateModal #termName").val(rows.termName);
                	$("#updateModal #id").val(rows.id);
                	$("#updateModal #fall_id").val(rows.fall.fall_id);
                	$("#updateModal #major_id").val(rows.major.major_id);
	                $("#updateModal #updateRemark").val(rows.termRemark);
                }
            });
			//删除按钮事件
			$("#btn_delete").click(function(){
                if(rows == null || rows.id==null){
                	swal("删除失败！", "请选择需要删除的学期资料", "error");
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
	                		deleteTerm();
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
					url : '${pageContext.request.contextPath}/education/term/termList', //请求后台的URL（*）
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
					//search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "ID", //每一行的唯一标识，一般为主键列
					//showToggle : true, //是否显示详细视图和列表视图的切换按钮
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
						field : 'fall.level',
						title : '届别'
					}, {
						field : 'fall.fall_id',
						title : '届别ID',
						visible:false
					},{
						field : 'major.majorName',
						title : '班级'
					}, {
						field : 'major.major_id',
						title : '专业ID',
						visible:false
					}, {
						field : 'termName',
						title : '学期'
					}, {
						field : 'termRemark',
						title : '说明',
						
					},]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					termName : $("#search_term").val(),
					fall_id : $("#search_fall").val(),
					major_id : $("#search_major").val()
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
		function addTerm(){
			var url = "${pageContext.request.contextPath }/education/term/addTerm";
			$.post(
				url,
				{
					termName:$("#myModel #termName").val(),
					fall_id:$("#myModel #fall_id").val(),
					major_id:$("#myModel #major_id").val(),
					termRemark:$("#myModel #Remark").val(),
				},
				addTermHandle,
				"text"
			);	
			
			//清空新增div中的数据
			Remark:$("#myModel #Remark").val('');
		}
		function addTermHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data==1){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
				$('#tb_departments').bootstrapTable('refresh');
			}else if(data==2){
				swal("添加失败", "已存在该条学期资料！", "error");
			}else if(data==0){
				swal("添加失败", "请检查你输入的数据！", "error");
			}
			$('#tb_departments').bootstrapTable('refresh');
		}
		
		//update department ，ajax提交
		function updateTerm(){
			var id =rows.id;
			var url = "${pageContext.request.contextPath }/education/term/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#updateModal #id").val(),
					termName:$("#updateModal #termName").val(),
					fall_id:$("#updateModal #fall_id").val(),
					major_id:$("#updateModal #major_id").val(),
					termRemark:$("#updateModal #Remark").val(),
				},
				updateTermHandle,
				"text"
			);	
			
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
			//清空新增div中的数据
			termRemark:$("#updateModal #updateRemark").val('');
		}
		function updateTermHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data==1){
				swal({
                    title: "成功",
                    text: "你已经完成修改操作",
                    type: "success"
                });
				$('#tb_departments').bootstrapTable('refresh');
			}else if(data==2){
				swal("修改失败", "已存在该条学期资料！", "error");
			}else if(data==0){
				swal("修改失败", "请检查你输入的数据！", "error");
			}
			$('#tb_departments').bootstrapTable('refresh');
		}
		
		//delect Dept 
		//删除学生，ajax提交
		function deleteTerm(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/education/term/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteTermHandle, 
				"text"
			);	
		}
		
		function deleteTermHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_departments').bootstrapTable('refresh');
		}
		
	</script>
</head>
<body class="gray-bg">
<div class="panel-body" style="padding-bottom: 0px;">
	<div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>查询条件</h5>
            <div class="ibox-tools">
                <a class="collapse-link">
                    <i class="fa fa-chevron-up"></i>
                </a>
            </div>
            <div class="ibox-content">
            <form id="formSearch" class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-1" for="search_fall">届别</label>       
					<div class="col-sm-2">
						<select class="form-control"id="search_fall"> 
						<option value="">--不选择--</option>
						<c:forEach items="${fallList}" var="list">
				            	<option value="${list.id}">${list.level}</option>
				         </c:forEach>
				         </select>    
					</div>
					<label class="control-label col-sm-1" for="search_major">班级</label>
					<div class="col-sm-2">
						<select class="form-control" id="search_major">
							<option value="">--不选择--</option> 
	         				<c:forEach items="${majorList}" var="l">
			            	<option value="${l.id}">${l.majorName}</option>
			            	</c:forEach>
						</select>      
					</div>
					<label class="control-label col-sm-1" for="search_term">学期</label>
					<div class="col-sm-2">
						<select class="form-control" id="search_term">
							<option value="">--不选择--</option>
							<option value="第一学期">第一学期</option>
	          				<option value="第二学期">第二学期</option>
	          				<option value="第三学期">第三学期</option>
	          				<option value="第四学期">第四学期</option>
	          				<option value="第五学期">第五学期</option>
	          				<option value="第六学期">第六学期</option>
						</select>       
					</div>
					<div class="col-sm-1">
						<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">查询</button>
					</div>
				</div>
			</form>
		</div>
	   </div>
    </div>
   	
    <div class="ibox float-e-margins ">
	      <div class="ibox-title">
			<h5>学期资料</h5>
            <div class="ibox-tools">
                 <a class="collapse-link">
                     <i class="fa fa-chevron-up"></i>
                 </a>
             </div>
             <div class="ibox-content">
             	<div id="toolbar" class="btn-group">
             	<button id="btn_add_term" type="button" class="btn btn-w-m btn-info">
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
		</div>
	</div>
</div>

<div class="modal fade" id="myModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">修改学期资料</h4>
        </div>
        <div class="modal-body">
        <!-- 新增系别 -->
          <input type="hidden" id="id" name="id">
            <form class="form-inline">
          	<div class="form-group">
          		<label for="termName">学期</label>
          			<select name="termName" id="termName" class="form-control">
          				<option value="第一学期" >第一学期</option>
          				<option value="第二学期">第二学期</option>
          				<option value="第三学期">第三学期</option>
          				<option value="第四学期">第四学期</option>
          				<option value="第五学期">第五学期</option>
          				<option value="第六学期">第六学期</option>
          		</select>
          	</div>
          	<div class="form-group">
          		<label for="fall_id">届别</label>
          			<select name="fall_id" id="fall_id"  class="form-control">
          					<c:forEach items="${fallList}" var="list">
			            	<option value="${list.id}">${list.level}</option>
			            	</c:forEach>
          			</select>
          	</div>
          	<div class="form-group">
          		<label for="major_id">班级类型</label>
          			<select name="major_id" id="major_id"  class="form-control">
          					<c:forEach items="${majorList}" var="l">
			            	<option value="${l.id}">${l.majorName}</option>
			            	</c:forEach>
          			</select>
          	</div>
          </form>
          	<div class="form-group">
	            <label for="Remark">说明</label>
	            <textarea name="Remark" rows="3" class="form-control" id="Remark">
	            </textarea>
          	</div>
          	<div class="modal-footer">
		         <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
          		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addTerm()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
	        </div>
		</div>
      </div>
    </div>
</div>

<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">修改学期资料</h4>
        </div>
        <div class="modal-body">
        <!-- 新增系别 -->
          <input type="hidden" id="id" name="id">
            <form class="form-inline">
          	<div class="form-group">
          		<label for="termName">学期</label>
          			<select name="termName" id="termName" class="form-control">
          				<option value="第一学期" <c:if test="${object.termName==value}">selected</c:if>>第一学期</option>
          				<option value="第二学期" <c:if test="${object.termName==value}">selected</c:if>>第二学期</option>
          				<option value="第三学期" <c:if test="${object.termName==value}">selected</c:if>>第三学期</option>
          				<option value="第四学期" <c:if test="${object.termName==value}">selected</c:if>>第四学期</option>
          				<option value="第五学期" <c:if test="${object.termName==value}">selected</c:if>>第五学期</option>
          				<option value="第六学期" <c:if test="${object.termName==value}">selected</c:if>>第六学期</option>
          			</select>
          	</div>
          	<div class="form-group">
          		<label for="fall_id">届别</label>
          			<select name="fall_id" id="fall_id" class="form-control">
          					<c:forEach items="${fallList}" var="list">
			            	<option value="${list.id}" <c:if test="${object.fall_id== value}">selected</c:if>>${list.level}</option>
			            	</c:forEach>
          			</select>
          	</div>
          	<div class="form-group">
          		<label for="major_id">班级类型</label>
          			<select name="major_id" id="major_id" class="form-control">
          					<c:forEach items="${majorList}" var="l">
			            	<option value="${l.id}" <c:if test="${object.major_id== value}">selected</c:if>>${l.majorName}</option>
			            	</c:forEach>
          			</select>
          	</div>
          </form>
          	<div class="form-group">
	            <label for="Remark">说明</label>
	            <textarea name="Remark" rows="3" class="form-control" id="Remark">
	            </textarea>
          	</div>
          	<div class="modal-footer">
		         <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
          		<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateTerm()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
	        </div>
		</div>
      </div>
    </div>
</div>
<!-- 自定义js -->               
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>