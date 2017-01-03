<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>学生信息</title>

<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
    
    
    <script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var rows = null;
	    $(function () {
	    	init();
	 	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//新增按钮事件*************************
			$("#btn_add").click(function(){
				//清空新增div中的数据
				$("#addStudent #id").val('');
				$("#addStudent #stuno").val('');
				$("#addStudent #stuname").val('');
	            $("#addStudent #sex").val('');
	            $("#addStudent #age").val('');
	            $("#addStudent #classid").val('');
	            $("#addStudent #middleschool").val('');
	            $("#addStudent #birthday").val('');
	            $("#addStudent #phone").val('');
	            $("#addStudent #addr").val('');
	            $("#addStudent #stuStatus").val('');
	           	$("#addStudent #entertime").val('');
	            $("#addStudent #nation").val('');
	            $("#addStudent #naplace").val('');
	            $("#addStudent #residence").val('');
	            $("#addStudent #idcard").val('');
	            $("#addStudent #professional").val('');
	            $("#addStudent #prolevel").val('');
	            
	            //修改按钮的点击事件
	            $("#save").attr("onclick","addStudent()");
	        });
			//修改按钮事件
			$("#btn_edit").click(function(){
				if(rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#addStudent #id").val(rows.id);
	            $("#addStudent #stuno").val(rows.stuno);
                $("#addStudent #stuname").val(rows.stuname);
                $("#addStudent input[type=radio][name=sex][value="+rows.sex+"]").attr("checked",'checked');
                $("#addStudent #age").val(rows.age);
                $("#addStudent #classid").val(rows.classid);
                $("#addStudent #middleschool").val(rows.middleschool);
                $("#addStudent #birthday").val(rows.birthday);
                $("#addStudent #phone").val(rows.phone);
                $("#addStudent #addr").val(rows.addr);
                $("#addStudent #stuStatus").val(rows.stuStatus);
                $("#addStudent #entertime").val(rows.entertime);
                $("#addStudent #nation").val(rows.nation);
                $("#addStudent #naplace").val(rows.naplace);
                $("#addStudent #residence").val(rows.residence);
                $("#addStudent #idcard").val(rows.idcard);
                $("#addStudent #professional").val(rows.professional);
                $("#addStudent input[name=prolevel]:checked").val(rows.prolevel);
                $('#addStudent').modal('show');
                $("#save").attr("onclick","updateStudent()");
	        });
			//删除按钮事件
			//*************************************************************************按钮事件
			$('#btn_delete').click(function () {
				if(rows==null){
					parent.layer.alert('请选你要删除的数据！');
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
			//查询按钮
			$('#btn_query').click(function () {
				//刷新数据
				$('#tb_student').bootstrapTable('refresh');
	        });
		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_student').bootstrapTable({
					url : '${pageContext.request.contextPath}/student/stuListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					showExport: true, //是否显示导出
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
					strictSearch : false,
					searchOnEnterKey :true, //按回车搜索
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
						checkbox : true,
					}, {
						field : 'stuno',
						title : '学籍号'
					}, {
						field : 'stuname',
						title : '姓名'
					}, {
						field : 'sex',
						title : '性别'
					}, {
						field : 'phone',
						title : '电话'
					}, {
						field : 'classname',
						title : '班级',
						formatter : function(value, row, index) {
							var classInfo = row.classInfo;
							if(classInfo==null){
								return "-";
							}else{
								return classInfo.classname;
							}
						}
					}, {
						field : 'hoursename',
						title : '宿舍',
						formatter : function(value, row, index) {
							var hourse = row.hourse;
							if(hourse==null){
								return "-";
							}else{
								return hourse.hoursename;
							}
						}
					}, {
						field : 'addr',
						title : '家庭地址'
					},{
						title : '教育与家庭',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a  href='${pageContext.request.contextPath}/student/edufam/page/"+id+"'><i class='fa fa-file-text-o'>教育与家庭</i></a>";
						}
					},{
						title : '详细信息',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a  href='${pageContext.request.contextPath}/student/info/"+id+"'><i class='fa fa-file-text-o'>详情信息</i></a>";
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
					stuname : $("input[placeholder=搜索]").val(),
					phone :  $("#txt_search_phone").val(),
					stuno :  $("#txt_search_stuno").val(),
					classid: $("#txt_search_classid").val(),
					huorid: $("#txt_search_huorid").val(),
					phone: $("#txt_search_phone").val(),
					addr: $("#txt_search_addr").val(),
					middleschool: $("#txt_search_middleschool").val(),
					introduretech :$("#txt_search_introduretech").val(),
					residence :$("#txt_search_res").val()
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
		
		//新增学生，ajax提交
		function addStudent(){
			//用来判断表单是否验证通过
			if(!validateForm($("#stuForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/student/addStudent";
			$.post(
				url,
				{
					stuno:$("#addStudent #stuno").val(),
	                stuname:$("#addStudent #stuname").val(),
	                sex:$('#addStudent input[name=sex]:checked').val(),
	                age:$("#addStudent #age").val(),
	                classid:$("#addStudent #classid").val(),
	                middleschool:$("#addStudent #middleschool").val(),
	                birthday:$("#addStudent #birthday").val(),
	                phone:$("#addStudent #phone").val(),
	                addr:$("#addStudent #addr").val(),
	                stuStatus:$("#addStudent #stuStatus").val(),
	                birthday:$("#addStudent #entertime").val(),
	                nation:$("#addStudent #nation").val(),
	                naplace:$("#addStudent #naplace").val(),
	                residence:$("#addStudent #residence").val(),
	                idcard:$("#addStudent #idcard").val(),
	                professional:$("#addStudent #professional").val(),
	                prolevel:$("#addStudent input[name=prolevel]:checked").val()
				},
				addStudentHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#addStudent").modal('hide');
		}
		function addStudentHandle(data){
			//alert(data);
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
			//刷新数据
			$('#tb_student').bootstrapTable('refresh');
		}
		
		//修改学生，ajax提交
		function updateStudent(){
			var id =$("#addStudent #id").val(); 
			var url = "${pageContext.request.contextPath }/student/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					stuno:$("#addStudent #stuno").val(),
	                stuname:$("#addStudent #stuname").val(),
	                sex:$('#addStudent input[name=sex]:checked').val(),
	                age:$("#addStudent #age").val(),
	                classid:$("#addStudent #classid").val(),
	                middleschool:$("#addStudent #middleschool").val(),
	                birthday:$("#addStudent #birthday").val(),
	                phone:$("#addStudent #phone").val(),
	                addr:$("#addStudent #addr").val(),
	                stuStatus:$("#addStudent #stuStatus").val(),
	                entertime:$("#addStudent #entertime").val(),
	                nation:$("#addStudent #nation").val(),
	                naplace:$("#addStudent #naplace").val(),
	                residence:$("#addStudent #residence").val(),
	                idcard:$("#addStudent #idcard").val(),
	                professional:$("#addStudent #professional").val(),
	                prolevel:$("#addStudent input[name=prolevel]:checked").val()
				},
				updateStudentHandle,
				"text"
			);	
			//用来关闭修改窗口***********
			$("#addStudent").modal('hide');
		}
		function updateStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成修改操作",
                    type: "success"
                });
			}else{
				swal("修改失败", "请检查你输入的数据！", "error");
			}
			//刷新表格
			$('#tb_student').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		
		//***************************************************************************************删除
		//删除学生，ajax提交
		function deleteStudent(){
			//alert('删除');
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/student/"+id;
			//alert(url);
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
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_student').bootstrapTable('refresh');
		}
		
		function init(){
			//alert(555);
  			var url="${pageContext.request.contextPath}/conn/AllFall";	
			$.post(
				url,
				{
					id:1
				},
				getFall,
				"json"
			);	
			var url2="${pageContext.request.contextPath}/conn/AllFloor";	
			$.post(
				url2,
				{
					id:1
				},
				getFloor,
				"json"
			);	
			var url3="${pageContext.request.contextPath}/conn/AllRes";	
			$.post(
				url3,
				{
					id:1
				},
				getResidence,
				"json"
			);	
  		}
		//户口类型
  		function getResidence(data){
  			var td="<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].residence;
				td+="</option>";
			});
			$("#txt_search_res").html(td);//显示在div中
  		}
		//界别班级
  		function getFall(data){
  			var td="<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].level;
				td+="</option>";
			});
			$("#txt_search_fall").html(td);//显示在div中
			clsChange();
  		}
		function clsChange(){
			var id = $("#txt_search_fall").val();
			var url="${pageContext.request.contextPath}/conn/AllCls/"+id;	
			$.post(
				url,
				{
					id:id
				},
				getCls,
				"json"
			);	
		}
		function getCls(data){
			var td = "<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].classname;
				td+="</option>";
			});
			$("#txt_search_classid").html(td);//显示在div中
		}
		
		//楼栋楼层
		function getFloor(data){
			var td = "<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].floorname;
				td+="</option>";
			});
			$("#txt_search_floor").html(td);
			floorChange();
  		}
		function floorChange(){
			var id = $("#txt_search_floor").val();
			var url="${pageContext.request.contextPath}/conn/AllLayer/"+id;	
			$.post(
				url,
				{
					id:id
				},
				getLayer,
				"json"
			);	
		}
		function getLayer(data){
			var td = "<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].layername;
				td+="</option>";
			});
			$("#txt_search_huorid").html(td);
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
								<div class="row">
									<label class="control-label col-sm-1">班级</label>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_fall" id="txt_search_fall" onchange="clsChange()" class="form-control">
		                                     <option>----</option>
		                                </select>
									</div>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_classid" id="txt_search_classid" class="form-control">
										
		                                </select>    
									</div>
									<label class="control-label col-sm-1">宿舍</label>       
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_floor" id="txt_search_floor" onchange="floorChange()" class="form-control">
		                                     <option>----</option>   
		                                </select>    
									</div>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_huorid" id="txt_search_huorid" class="form-control">
		                                     
		                                </select>  
									</div>
								</div>
								<div class="row">
									<label class="control-label col-sm-1">学号</label>
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_stuno">       
									</div>
									<label class="control-label col-sm-1">电话号码</label>       
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_phone">       
									</div>
									<label class="control-label col-sm-1">户口类型</label>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_res" id="txt_search_res" onchange="clsChange()" class="form-control">
										
		                                </select>     
									</div>
								</div>
								<div class="row">
									<label class="control-label col-sm-1" for="txt_search_departmentname">介绍老师</label>       
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_introduretech">       
									</div>
									<label class="control-label col-sm-1" for="txt_search_statu">毕业学校</label>
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_middleschool">       
									</div>
									<label class="control-label col-sm-1" for="txt_search_statu">家庭住址</label>
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_addr">       
									</div>
									<div class="col-sm-1">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">查询</button>
									</div>
									<div class="col-sm-1">
										<a href="${pageContext.request.contextPath}/student/stuList"><button type="button" style="margin-left: 20px" id="btn_query" class="btn btn-primary">清空</button></a>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>学生信息</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<!-- <button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addStudent">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button> -->
							<button id="btn_edit" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_student"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
		
		<div class="modal inmodal fade in" id="addStudent" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<form id="stuForm" action="javascript:void(0)">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">学生信息</h4>
						<small class="font-bold"> </small>
					</div>
					<div class="col-sm-6 b-r" style="background: rgb(248, 250, 251);">
						<div class="form-horizontal m-t">
							<input id="id" type="hidden">
							<div class="form-group">
                                <label class="col-sm-3 control-label">学号：</label>
                                <div class="col-sm-8">
                                    <input id="stuno" name="stuno" disabled="disabled" type="text"  required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-8">
                                    <input id="stuname" name="stuname" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">年龄：</label>
                                <div class="col-sm-8">
                                    <input id="age" name="age" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">性别：</label>
                                <div class="col-sm-8">
                                	<label class="control-label"><input type="radio"  name="sex"  value="男" >&nbsp;&nbsp;&nbsp;男</label>
                                	<label class="control-label" style="padding-left: 20px"><input type="radio"  name="sex"  value="女" >&nbsp;&nbsp;女</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">毕业学校：</label>
                                <div class="col-sm-8">
                                    <input id="middleschool" name="middleschool" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学生电话：</label>
                                <div class="col-sm-8">
                                	<input id="phone" name="phone" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">家庭地址：</label>
                                <div class="col-sm-8">
                                	<input id="addr" name="addr" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">民族：</label>
                                <div class="col-sm-8">
                                	<input id="nation" name="nation" type="text"  class="form-control">
                                </div>
                            </div>
						</div>
					</div>
					<div class="col-sm-6" style="background: rgb(248, 250, 251);">
						<div class="form-horizontal m-t">
							<div class="form-group">
                                <label class="col-sm-3 control-label">出生日期：</label>
                                <div class="col-sm-8">
									<input placeholder="出生日期" class="form-control layer-date"
										id="birthday" name="birthday">
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">入学时间：</label>
                                <div class="col-sm-8">
									<input placeholder="入学时间" class="form-control  layer-date"
										id="entertime" name="entertime">
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">籍贯：</label>
                                <div class="col-sm-8">
                                	<input id="naplace" name="naplace" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">户口性质：</label>
                                <div class="col-sm-8">
									<select class="form-control" name="residence" id="residence" class="form-control">
	                                	<c:forEach items="${resList}" var="res">
	                                        <option value="${res.id}">${res.residence}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">身份证号：</label>
                                <div class="col-sm-8">
                                	<input id="idcard" name="idcard" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">就读院系：</label>
                                <div class="col-sm-8">
                                	<input id="professional" name="professional" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">专业类别：</label>
                                <div class="col-sm-8">
                                	<select class="form-control" disabled="disabled" id="prolevel" name="prolevel" class="form-control">
                                        <c:forEach items="${majorList}" var="major">
	                                        <option value="${major.id}">${major.majorName}</option>
	                                    </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学生状态：</label>
                                <div class="col-sm-8">
                                	<select class="form-control" name="stuStatus" id="stuStatus" class="form-control">
	                                	<c:forEach items="${stustaList}" var="sta">
	                                        <option value="${sta.id}">${sta.stu}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<button type="button" id="save" onclick="" class="btn btn-primary">保存</button>
					</div>
				</div>
				</form>
				<small class="font-bold"> </small>
			</div>
			<small class="font-bold"> </small>
		</div>
		 
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#entertime',
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
		laydate(start);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#birthday',
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
		laydate(start);
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
	                stuno: "required",
	                stuname: "required",
	                age: {
	                	digits:true
                    },
                    phone: {
                    	required:true,
	                	digits:true
                    },
                    addr :{
                    	required:true
                    },
                    entertime:{
                    	required:true
                    }
	            	
	            },
	            messages: {
	                stuno: icon + "请输入学生编号",
	                stuname: icon + "请输入学生姓名",
	                age: {
	                	digits: icon + "年龄必须是整数"
                    },
                    phone: {
                    	required:icon + "必须输入，如果没有，填写0000",
                    	digits: icon + "电话号码必须是数字"
                    },
                    addr :{
                    	required:icon + "请填写学生家庭地址"
                    },
                    entertime:{
                    	required:icon + "请填写学生入学时间"
                    }
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
	</script>
</body>
</html>