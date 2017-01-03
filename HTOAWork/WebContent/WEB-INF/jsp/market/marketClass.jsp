<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>班级管理页面</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
	<script type="text/javascript">
	//全局变量，用来保存选中行的数据
    var rows = null;
	var All_classid = "19";
	var num = 0;//用来判断一个班还可以新增几个人
    //全局变量，用来保存选中行的数据
	    $(function () {
	    	$('#btn_changeStudent').click(function () {
				//刷新数据
				$('#tb_student').bootstrapTable('refresh');
	        });
	    	$(function () {
		    	$('#btn_changeStudent').click(function () {
					//刷新数据
					$('#tb_student').bootstrapTable('refresh');
		        });
		    	
				$('#tb_cls_student').bootstrapTable({
					url : '${pageContext.request.contextPath}/market/marketClass/marketStudentByclsIdListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					//toolbar : '#toolbar_cls_stu', //工具按钮用哪个容器
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					sortOrder : "asc", //排序方式
					sortable : false, //是否启用排序
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					queryParams : cls_student_Params,//传递参数（*）
					columns : [ {
						field : 'number',
						title : '序号',
						formatter: function (value, row, index) { 
							return index+1; 
						} 
					}, {
						field : 'name',
						title : '姓名'
					}, {
						field : 'school',
						title : '学校',
						formatter : function(value, row, index) {
							if(row.clsName!=""){
								return row.school+"("+row.clsName+")"
							}else{
								return row.school;
							}
						}
					}, {
						field : 'msStatus',
						title : '状态',
						align : 'center',
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
							}else if(msStatus == 5){
								text = "<span class='label label-info'>试学</span>";
								return text;
							}
						}
					},]
				});
			});
			$('#tb_student').bootstrapTable({
				url : '${pageContext.request.contextPath}/conn/testStuListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stu', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				clickToSelect : true, //是否启用点击选中行
				queryParams : queryParams2,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 10, //每页的记录行数（*）
				pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
				minimumCountColumns : 2, //最少允许的列数
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				columns : [ {
					checkbox : true
				},{
					field : 'number',
					title : '序号',
					formatter: function (value, row, index) { 
						return index+1; 
					} 
				}, {
					field : 'name',
					title : '姓名'
				}, {
					field : 'school',
					title : '学校',
					formatter : function(value, row, index) {
						if(row.clsName!=""){
							return row.school+"("+row.clsName+")"
						}else{
							return row.school;
						}
					}
				}, {
					field : 'msStatus',
					title : '状态',
					align : 'center',
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
						}else if(msStatus == 5){
							text = "<span class='label label-info'>试学</span>";
							return text;
						}
					}
				},]
			});
		});
		//得到查询的参数
		function queryParams2(params) {
			
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				offset : params.offset, //页码
				stuname : $("#div_stu input[placeholder=搜索]").val(),
				limit : function(){
					if(rows!=null){
						return rows.count-rows.countstu;
					}else{
						return 1;
					}
				}
			};
			return temp;
		};
		function cls_student_Params(params) {
			
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				classid : All_classid,
			};
			return temp;
		};
		function allotClass(){
			$('#tb_student').bootstrapTable('refresh');
			if(rows==null){
				parent.layer.alert('请选中操作的班级！');
				return;
			}
			num = rows.count-rows.countstu;
			if(num<=0){
				parent.layer.alert('班级人数已满，请选择别的班级！');
				return;
			}
            $('#window_allot').modal('show');
		}
		//选择学生
		function setStudent(){
			//这里是所有的选中行数据，和后台传进来的一样，怎么取看下面,student是list
			var stuList = $('#tb_student').bootstrapTable('getSelections');
			//var stuList = $('#tb_student').bootstrapTable('getData');
			var classid = rows.id;
			var ids = "";
			if(stuList.length>num){
				parent.layer.alert('当前班级无法新增怎么多人');
				return;
			}
			for(var i =0 ;i<stuList.length;i++){
				if(i!=stuList.length-1){
					ids = ids +stuList[i].id+",";
				}else{
					ids = ids +stuList[i].id;
				}
			}
			var url = "${pageContext.request.contextPath}/market/marketClass/stuclassallot";
	 		$.post(
	 			url,
	 			{
	 				classid:classid,
	 				ids:ids
	 			},
	 			allotHandler,
	 			"text"
	 		);	
		}
		function allotHandler(data){
	 		//从后台返回出来的int类型数据，用来判断是否新增成功
	 		if(data>0){
	 			swal({
	                 title: "成功",
	                 text: "该班成功添加"+data+"名学生",
	                 type: "success"
	             });
	 		}else if(data==-1){
	 			swal("分班失败", "班级人数满了，请选择别的班！", "error");
	 		}else if(data==-2){
	 			swal("分班失败", "还未设置该届该班第一学期学费！", "error");
	 		}else if(data==-3){
	 			swal("分班失败", "还未设置该届该班第一学期的数据！", "error");
	 		}
	 		$('#tb_student').bootstrapTable('refresh');
	 		$('#tb_departments').bootstrapTable('refresh');
	 	}
	</script>
	<script type="text/javascript">
	    $(function () {	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			$("#btn_query").click(function(){
				//刷新表格
				$('#tb_departments').bootstrapTable('refresh');
	          
	        });
			$("#btn_clean").click(function(){
				//刷新表格
				$("#txt_search_statu").val("");
				$("#txt_search_departmentname").val("");
				$('#tb_departments').bootstrapTable('refresh');
	          
	        });
		});
	    
	    var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_departments').bootstrapTable({
					url : '${pageContext.request.contextPath}/market/marketClass/testClassListJson', //请求后台的URL（*）
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
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
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
			        },
					columns : [ {
						checkbox : true
					}, {
						field : 'classno',
						title : '班级编号'
					}, {
						field : 'classname',
						title : '班级名称'
					},{
						field : 'count',
						title : '班级人数'
					},{
						field : 'stunum',
						title : '现有人数',
						formatter : function(value, row, index) {
							if(row.clsStatus==-1){
								return row.counttest;
							}else{
								return row.countstu;
							}
						}
					},{
						field : 'leaderId',
						title : '校领导',
						formatter : function(value, row, index) {
							var empl = row.empl;
							if(empl==null){
								return "-";
							}else{
								return empl.empname;
							}
						}
					},{
						field : 'clteacherId',
						title : '班主任',
						formatter : function(value, row, index) {
							var empc = row.empc;
							if(empc==null){
								return "-";
							}else{
								return empc.empname;
							}
						}
					},{
						field : 'teacherId',
						title : '任课老师',
						formatter : function(value, row, index) {
							var emp = row.emp;
							if(emp==null){
								return "-";
							}else{
								return emp.empname;
							}
						}
					},{
						field : 'majorName',
						title : '专业',
						formatter : function(value, row, index) {
							var major = row.eduMajor;
							if(major==null){
								return "-";
							}else{
								return major.majorName;
							}
						}
					},{
						field : 'clsStatus',
						title : '班级状态',
						formatter : function(value, row, index) {
							var clsStatus = row.clsStatus;
							var text = "";
							if(clsStatus == 0){
								text = "<span class='label label-success'>新  班</span>";
								return text;
							}else if(clsStatus == 1){
								text = "<span class='label label-primary'>正  常</span>";
								return text;
							}else if(clsStatus == 2){
								text = "<span class='label label-danger'>毕  业</span>";
								return text;
							}else if(clsStatus == -1){
								text = "<span class='label label-warning'>试学</span>";
								return text;
							}
						}
					},{
						field : 'level',
						title : '届别',
						formatter : function(value, row, index) {
							var studentfall = row.studentfall;
							if(studentfall==null){
								return "-";
							}else{
								return studentfall.level;
							}
						}
					},{
						title : '操作',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a onclick='allStuWindow("+id+")'>所有学生</a>";
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
					levelId : $("#levelId").val(),
					classname : $("#txt_search_statu").val()
					
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
 	function allStuWindow(classid){
 		All_classid = classid;
 		$('#tb_cls_student').bootstrapTable('refresh');
 		$('#window_cls_student').modal('show');
 	}
	</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
	<div class="panel panel-default">
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
								<label class="control-label col-sm-1" for="txt_search_departmentname">届别</label>       
								<div class="col-sm-3">
									<select class="chosen-select" style="width:180px;height:33px;" tabindex="2" name="levelId" id="levelId">
									<option value="">--请选择--</option>
									<c:forEach items="${sList}" var="s">
		                               <option value="${s.id}">${s.level}</option >
		                            </c:forEach>  
		                            </select>      
								</div>
								<label class="control-label col-sm-1" for="txt_search_statu">班名</label>
								<div class="col-sm-3">
									<input type="text" class="form-control" id="txt_search_statu">       
								</div>
								<div class="col-sm-3">
									<button type="button" style="margin-left: 80px" id="btn_query" class="btn btn-primary">查询</button>
									<button type="button" id="btn_clean" class="btn btn-primary">清空</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="panel-body">
	<div class="panel panel-default">
		<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>试学班级管理</h5>
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
							<div data-toggle="modal" class="btn btn-success btn-outline">
								<i  class='glyphicon' onclick="allotClass()">分配学生</i>
							</div>
						</div>
						<table id="tb_departments"></table>
                    </div>
                </div>
            </div>
        </div>
	</div>
	</div>
	<!-- 选择学生 -->
         <div class="modal inmodal" id="window_allot" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated fadeIn">
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox-content">
								<!-- table代码就这些，用js构建表格 -->
								<div id="div_stu">
								<table id="tb_student"></table>
								</div>
								<div class="modal-footer">
									 <button type="button"  id="btn_changeStudent" class="btn btn-success">换一批</button>
	                                 <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
	                                 <button type="button" id="btn_setStudent" onclick="setStudent()" class="btn btn-primary" data-dismiss="modal">确认</button>
	                            </div>
							</div>
						</div>
				   </div>
				</div>
			</div>
     	</div>
     <!-- 该班所有学生 -->
     	<div class="modal fade" id="window_cls_student" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated fadeIn">
					<div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title">所有学生</h4>
				    </div>
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox-content">
								<!-- table代码就这些，用js构建表格 -->
								<div id="div_stu">
								<table id="tb_cls_student"></table>
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
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>