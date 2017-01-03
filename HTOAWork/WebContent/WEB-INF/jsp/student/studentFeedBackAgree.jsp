<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>学生退费管理</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
    
 <script type="text/javascript">
    //全局变量，读取当前行的数据
	var rows=null;
	function Agree(id){
		//alert(id);
		var ID=id;
		var url = "${pageContext.request.contextPath }/student/studentFeedBack/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
				id:ID,
				feedStatus:0
			},
			agreeUpdateHandle,
			"text"
		);	
		$('#tb_financefeedback').bootstrapTable('refresh');
	}
	function agreeUpdateHandle(data){
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if(data>0){
			//alert("成功")
			/* swal({
                title: "成功",
                text: "Update is seccussed！",
                type: "success"
            }); */
			$('#tb_financefeedback').bootstrapTable('refresh');
		}else{
			//swal("修改错误！", "请检查你输入的数据！", "error");
			$('#tb_financefeedback').bootstrapTable('refresh');
		}
		$('#tb_financefeedback').bootstrapTable('refresh');
	}
	 function Refused(id){
		 var url = "${pageContext.request.contextPath }/student/studentFeedBack/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:id,  //从选中的rows中获取id
					feedStatus:3
				},
				RefusedDeptHandle, 
				"text"
			);	
			$('#tb_financefeedback').bootstrapTable('refresh');
		}
		
		function RefusedDeptHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//swal("删除成功！", "您已经永久删除了这条信息。", "success");
				$('#tb_financefeedback').bootstrapTable('refresh');
			}else{
				//swal("删除失败", "服务器繁忙！", "error");
				$('#tb_financefeedback').bootstrapTable('refresh');
			}
			//刷新表格
			$('#tb_financefeedback').bootstrapTable('refresh');
		}
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
			//修改按钮事件
			$("#btn_edit").click(function(){
                if(rows == null || rows.id==null){
                	swal("错误", "请选择需要修改的模板！", "error");
                }else{
                	$('#updateModal').modal();
                	id:$("#updateModal #id").val(rows.id);
                	name:$("#updateModal #name").val(rows.name);
                	score:$("#updateModal #score").val(rows.score);
	                status:$("#updateModal #status").val(rows.status);
                	remark:$("#updateModal #remark").val(rows.remark);
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
	                		deleteDept();
	                	} else {
	                    	swal("已取消", "您取消了删除操作！", "error");
	                	}
	            	});
                }
            });
			//查询按钮
			$('#btn_query').click(function () {
				//alert("查询");
				//刷新数据
				$('#tb_financefeedback').bootstrapTable('refresh');
	        });

		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_financefeedback').bootstrapTable({
					url : '${pageContext.request.contextPath}/student/studentFeedBack/FeedBackJson', //请求后台的URL（*）
					method : 'get', //请求方式（*）
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
					pageList : [ 10, 25], //可供选择的每页的行数（*）
					search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onCheck: function(row) {//selected
		                 //$element是当前tr的jquery对象
		                 if(rows!=null){
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
						title : '编号'
					}, {
						field : 'stuname',
						title : '学生',
						formatter : function(value, row, index) {
							var student = row.student;
							if(student==null){
								return "-";
							}else{
								return student.stuname;
							}
						}
					}, {
						field :'classname',
						title : '班级',
						formatter : function(value, row, index) {
							var studentClass = row.studentClass;
							if(studentClass==null){
								return "-";
							}else{
								return studentClass.classname;
							}
						}
					}, {
						field : 'reason',
						title : '退费原因'
					}, {
						field : 'createTime',
						title : '申请时间',
						formatter : function(value, row, index){
							if(value==null){
								return "未填写";
							}else{
								return value.substring(0,20);
							}
						}
					}, {
						field : 'feedStatus',
						title : '操作',
						formatter : function(value,row,index){
							var id = row.id;
							if(row.feedStatus==0){
								return "已同意";
							}else if(row.feedStatus==1){
								return "<a onclick='Agree("+id+")'>同意</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Refused("+id+")'>拒绝</a>";
							}else if(row.feedStatus==3){
								return "已拒绝";
							}
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
					//clssid : $("input[placeholder=搜索]").val(),
					studentid : $("#txt_search_studentid").val(),
					classid : $("#txt_search_classid").val()
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
		function addfinancefeedback(){
			var url = "${pageContext.request.contextPath }/student/studentFeedBack/addfeedback";
			$.post(
				url,
				{
					classid:$("#myModal #classid").val(),
					studentid:$("#myModal #studentid").val(),
	                flowid:$("#myModal #flowid").val(),
	                reason:$("#myModal #reason").val()
				},
				addfinancefeedbackHandle,
				"text"
			);	
			
			//刷新数据
			$('#tb_financefeedback').bootstrapTable('refresh');
			//清空新增div中的数据
			classid:$("#myModal #classid").val('');
			studentid:$("#myModal #studentid").val('');
            flowid:$("#myModal #flowid").val('');
            reason:$("#myModal #reason").val('');
		}
		function addfinancefeedbackHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
				$('#tb_financefeedback').bootstrapTable('refresh');
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
		}
		
		//update department ，ajax提交
		function updatefinancefeedback(){
			var id =rows.id;
			var url = "${pageContext.request.contextPath }/finance/FeedBackSet/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#updateModal #id").val(),
					name:$("#updateModal #name").val(),
					score:$("#updateModal #score").val(),
	                status:$("#updateModal #status").val(),
	                remark:$("#updateModal #remark").val(),
				},
				addUpdateHandle,
				"text"
			);	
			
			//刷新数据
			//清空新增div中的数据
			name:$("#updateModal #name").val('');
            status:$("#updateModal #status").val('');
            Remark:$("#updateModal #remark").val('');
		}
		function addUpdateHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "Update is seccussed！",
                    type: "success"
                });
				$('#tb_financefeedback').bootstrapTable('refresh');
			}else{
				swal("修改错误！", "请检查你输入的数据！", "error");
			}
		}
		
		//delect Dept 
		//删除反馈模板，ajax提交
		function deleteDept(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/finance/FeedBackSet/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteDeptHandle, 
				"text"
			);	
		}
		
		function deleteDeptHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_financefeedback').bootstrapTable('refresh');
		}
		//二级联动
		function selectemp() {
			//var classid=$("#window_srmd #classid").val();
			var url = "${pageContext.request.contextPath }/student/studentFeedBack/findstudent";
			$.post(url, {
				classid : $("#myModal #classid").val(),
			}, initemp, "text");
		}
		function initemp(data) {
			var opt = "";
			var datas = JSON.parse(data);
			var len = datas.rows.length;
			var i;
			if (len > 0) {
				for (i = 0; i < datas.rows.length; i++) {
					opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
							+ datas.rows[i].stuname + '</option>';
				}
				$('#myModal #studentid').empty();
				$('#myModal #studentid').html(opt);
				$('#myModal #studentid').trigger("chosen:updated");
				$('#myModal #studentid').chosen();
			}else{
				$('#myModal #studentid').empty();
			}
		}
		
		//查询条件二级联动
		function selectclass() {
			//var classid=$("#window_srmd #classid").val();
			var url = "${pageContext.request.contextPath }/student/studentFeedBack/findstudent";
			$.post(url, {
				classid : $("#formSearch #txt_search_classid").val(),
			}, initclass, "text");
		}
		function initclass(data) {
			var opt = "";
			var datas = JSON.parse(data);
			var len = datas.rows.length;
			var i;
			if (len > 0) {
				for (i = 0; i < datas.rows.length; i++) {
					opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
							+ datas.rows[i].stuname + '</option>';
				}
				$('#formSearch #txt_search_studentid').empty();
				$('#formSearch #txt_search_studentid').html(opt);
				$('#formSearch #txt_search_studentid').trigger("chosen:updated");
				$('#formSearch #txt_search_studentid').chosen();
			}else{
				$('#formSearch #txt_search_studentid').empty();
			}
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
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                           	<a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="tabs_panels.html#">选项1</a>
                                </li>
                                <li><a href="tabs_panels.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
							<form id="formSearch" class="form-horizontal">
								<div class="form-group" style="margin-top: 15px">
									<label class="control-label col-sm-1" for="txt_search_classid">班级</label>       
									<div class="col-sm-3">
										<!--  input type="text" class="form-control"id="txt_search_departmentname"-->
										<select id="txt_search_classid" name="txt_search_classid" class="form-control"  onchange="selectclass();">
											<option value="">---选择班级---</option>
											<c:forEach items="${studentclasslist}" var="s">
	                           					<option value="${s.id}">${s.classname}</option>
	                       					</c:forEach>
                       					</select>       
									</div>
									<!-- <label class="control-label col-sm-1" for="txt_search_studentid">学生</label>
									<div class="col-sm-3">
										input type="text" class="form-control" id="txt_search_statu"
										 <select id="txt_search_studentid" name="txt_search_studentid" class="form-control">
							                   	<option value="">---选择学生---</option>
						                 </select>      
									</div> -->
									<div class="col-sm-3">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">查询</button>
									</div>
								</div>
							</form>
                    </div>
                </div>
            </div>
        </div>
		
		<div id="toolbar" class="btn-group">
			<!-- <button id="btn_add" type="button" class="btn btn-w-m btn-primary">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>申请退费
			</button> -->
			<!--  
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
			-->
		</div>
		
		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_financefeedback"></table>
		 
	</div>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">填写申请表</h4>
        </div>
        <div class="ibox-content">
			<form class="form-horizontal m-t" id="commentForm" novalidate="novalidate">
	        <!-- 新增系别 -->
	          <div class="form-group">
	            <label for="name" class="col-sm-3 control-label">班级</label>
	            <div class="col-sm-8">
	            	<select id="classid" name="classid" class="form-control"  onchange="selectemp();">
	                   	<option value="">---选择班级---</option>
                   		<c:forEach items="${studentclasslist}" var="s">
                           <option value="${s.id}">${s.classname}</option>
                       </c:forEach>
                   </select>
	          	</div>
	          </div>
	          <div class="form-group">
	            <label for="score" class="col-sm-3 control-label">学生</label>
	             <div class="col-sm-8">
	               <select id="studentid" name="studentid" class="form-control">
	                   	<option value="">---选择学生---</option>
                   </select>
	             </div>
	          </div>
	          <div class="form-group">
	            <label for="status" class="col-sm-3 control-label">流程类别</label>
	             <div class="col-sm-8">
		            <select id="flowid" name="flowid" class="form-control"  onchange="selectemp();">
	                   	<option value="">---选择类别---</option>
                   		<c:forEach items="${flowModeTypelist}" var="s">
                           <option value="${s.id}">${s.flowmodeltype}</option>
                       </c:forEach>
                   </select>
	             </div>
	          </div>
	          <div class="form-group">
	            <label for="reason" class="col-sm-3 control-label">退费原因</label>
	            <div class="col-sm-8">
	            	<textarea name="reason" rows="4" class="form-control" id="reason"></textarea>
	            </div>
	          </div>
	          <div class="modal-footer">
	          	<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	          	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addfinancefeedback()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
	          </div>
         	</form>
	    </div>
      </div>
    </div>
  </div>
	
	<!-- update dialog 
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">修改模板</h4>
        </div>
        <div class="ibox-content">
			<form class="form-horizontal m-t" id="commentForm" novalidate="novalidate">
	         新增系别 
	          <div class="form-group">
	            <label for="name" class="col-sm-3 control-label">反馈名称</label>
	            <div class="col-sm-8">
	            	<input type="text" name="name" class="form-control" id="name">
	          	</div>
	          </div>
	          <div class="form-group">
	            <label for="score" class="col-sm-3 control-label">分数</label>
	             <div class="col-sm-8">
	               <input type="text" name="score" class="form-control" id="score">
	             </div>
	          </div>
	          <div class="form-group">
	            <label for="status" class="col-sm-3 control-label">状态</label>
	             <div class="col-sm-8">
		            <select name="status" id="status" class="form-control">
		            	<option value="0">启用</option>
		            	<option value="1">停用</option>
		            </select>
	             </div>
	          </div>
	          <div class="form-group">
	            <label for="remark" class="col-sm-3 control-label">说明</label>
	            <div class="col-sm-8">
	            	<textarea name="remark" rows="3" class="form-control" id="remark"></textarea>
	            </div>
	          </div>
	          <div class="modal-footer">
	          	<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
	          	<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updatefinancefeedback()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
	          </div>
         	</form>
	     </div>
       </div>
     </div>
   </div>
    -->
</body>
<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</html>