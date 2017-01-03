<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>意向学生</title>
	
	<jsp:include page="../styleInclude.jsp"></jsp:include>
	
	<script type="text/javascript">
		//全局变量，用来保存选中行的数据
	    var rows = null;
	   
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableInit = new TableInit();
			tableInit.Init();
			//初始化button按钮事件
			var buttonInit = new ButtonInit();
			buttonInit.Init();
			
			$("#btn_add").click(function(){
				//清空新增div中的数据
				$("#addIntentionStudent #name").val('');
				$("#addIntentionStudent #sex").val('');
				$("#addIntentionStudent #age").val('');
				$("#addIntentionStudent #phone").val('');
				$("#addIntentionStudent #addr").val('');
				$("#addIntentionStudent #school").val('');
				$("#addIntentionStudent #qqCode").val('');
				$("#addIntentionStudent #idCard").val('');
				$("#addIntentionStudent #clsName").val('');
				$("#addIntentionStudent #iGrade").val('中');
				$("#save").attr("onclick","addIntentionStudent()");
			});
			
			$("#btn_edit").click(function(){
				var stuList = $('#tb_intentionStudent').bootstrapTable('getSelections');
				if(stuList.length<1){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				if(stuList.length>1){
					parent.layer.alert('一次只能修改一个学生的数据！');
					return;
				}
				if(stuList.length==1){
					rows = stuList[0];
				}
				$("#addIntentionStudent #id").val(rows.id);
				$("#addIntentionStudent #name").val(rows.name);
				$("#addIntentionStudent input[value="+rows.sex+"]").attr('checked','true');
				$("#addIntentionStudent #age").val(rows.age);
				$("#addIntentionStudent #phone").val(rows.phone);
				$("#addIntentionStudent #addr").val(rows.addr);
				$("#addIntentionStudent #school").val(rows.school);
				$("#addIntentionStudent #qqCode").val(rows.qqCode);
				$("#addIntentionStudent #idCard").val(rows.idCard);
				$("#addIntentionStudent #clsName").val(rows.clsName);
				$("#addIntentionStudent #iGrade").val(rows.iGrade);
				$('#addIntentionStudent').modal('show');
				$("#save").attr("onclick","updateIntentionStudent()");
				
			});
			
			$("#btn_delete").click(function(){
				if(rows == null){
					parent.layer.alert('请选择你要删除的数据！');
					return;
				}
				
				swal({
					title: "您确定要删除这些信息吗",
                    text: "删除后将无法恢复，请谨慎操作",
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
	                	deleteIntentionStudent();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
				
			});
			//查询
			$("#btn_query").click(function(){
				$("#tb_intentionStudent").bootstrapTable('refresh');
				rows = null;
			});
			$("#btn_clean").click(function(){
				name : $("#txt_search_name").val('');
				sex : $("#txt_search_sex").val('');
				phone : $("#txt_search_phone").val('');
				$("#tb_intentionStudent").bootstrapTable('refresh');
				rows = null;
			});
		});
		
		var TableInit = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_intentionStudent').bootstrapTable({
					url : '${pageContext.request.contextPath}/market/intention/intentionStudentListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : table.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					//search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					searchOnEnterKey : true, //按回车搜索
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					//singleSelect: true,  //设置为单选
					onCheck: function(row) {
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
						field : 'name',
						title : '姓名'
					}, {
						field : 'sex',
						title : '性别'
					}, {
						field : 'age',
						title : '年龄'
					}, {
						field : 'phone',
						title : '电话'
					},{
						field : 'qqCode',
						title : 'QQ号'
					},{
						field : 'applyCost',
						title : '是否交报名费'
					},{
						field : 'iGrade',
						title : '意向等级'
					},{
						field : 'school',
						title : '毕业学校',
						formatter : function(value, row, index) {
							if(row.clsName!=""){
								return row.school+"("+row.clsName+")"
							}else{
								return row.school;
							}
						}
					},{
						field : 'msStatus',
						title : '状态',
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
					},{
						field : 'followCount',
						title : '次数',
						align : 'center',
						formatter : function(value, row, index) {
							var followCount = row.followCount;
							var text = followCount;
							if(followCount>0){
								text = "<span class='badge badge-warning'>"+followCount+"</span>";
							}
							if(row.isTest=='是'){
								text = "<span class='badge badge-primary'>"+followCount+"</span>";
							}
							return text;
						}
					},{
						field : '跟踪记录',
						title : '跟踪记录',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a  href='${pageContext.request.contextPath}/market/followrecord/page/"+id+"'>跟踪记录</a>";
						}
					}]
				});
			};
			//得到查询的参数
			table.queryParams = function(params){
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					name : $("#txt_search_name").val(),
					sex : $("#txt_search_sex").val(),
					phone : $("#txt_search_phone").val(),
					msStatus:$("#txt_search_msStatus").val()
				};
				return temp;
			};
			return table;
		};
		
		var ButtonInit = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增意向学生
		function addIntentionStudent(){
			//用来判断表单是否验证通过
			if(!validateForm($("#intenstuForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/market/intention/addIntentionStudent";
			$.post(
				url,
				
				{
					name:$("#addIntentionStudent #name").val(),
					sex:$('#addIntentionStudent input[name=sex]:checked').val(),
					age:$("#addIntentionStudent #age").val(),
					phone:$("#addIntentionStudent #phone").val(),
					qqCode:$("#addIntentionStudent #qqCode").val(),
					idCard:$("#addIntentionStudent #idCard").val(),
					iGrade:$("#addIntentionStudent #iGrade").val(),
					addr:$("#addIntentionStudent #addr").val(),
					school:$("#addIntentionStudent #school").val(),
					clsName:$("#addIntentionStudent #clsName").val()
				},
				addIntentionStudentHandle,
				"text"
			);
			//关闭新增窗口
			$("#addIntentionStudent").modal('hide');
		}
		function addIntentionStudentHandle(data){
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
			$("#tb_intentionStudent").bootstrapTable('refresh');
		}
		//修改意向学生
		function updateIntentionStudent(){
			var id = $("#addIntentionStudent #id").val();
			var url = "${pageContext.request.contextPath }/market/intention/intentionStudent/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					name:$("#addIntentionStudent #name").val(),
					sex:$('#addIntentionStudent input[name=sex]:checked').val(),
					age:$("#addIntentionStudent #age").val(),
					phone:$("#addIntentionStudent #phone").val(),
					qqCode:$("#addIntentionStudent #qqCode").val(),
					idCard:$("#addIntentionStudent #idCard").val(),
					iGrade:$("#addIntentionStudent #iGrade").val(),
					addr:$("#addIntentionStudent #addr").val(),
					school:$("#addIntentionStudent #school").val(),
					clsName:$("#addIntentionStudent #clsName").val()
				},
				
				updateIntentionStudentHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addIntentionStudent").modal('hide');
		}
		function updateIntentionStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否修改成功
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
			$('#tb_intentionStudent').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		//删除意向学生
		function deleteIntentionStudent(){
			//这里是所有的选中行数据，和后台传进来的一样，怎么取看下面,student是list
			var stuList = $('#tb_intentionStudent').bootstrapTable('getSelections');
			var ids = "";
			for(var i =0 ;i<stuList.length;i++){
				if(i!=stuList.length-1){
					ids = ids +stuList[i].id+",";
				}else{
					ids = ids +stuList[i].id;
				}
			}
			var url = "${pageContext.request.contextPath }/market/intention/delStudents";
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					ids:ids  //所有选中的id
				},
				deleteIntentionStudentHandle,
				"text"
			);
			
			//var id = rows.id;
			//var url = "${pageContext.request.contextPath }/market/intention/intentionStudent/"+id;
			//$.post(
				//url,
				//{
					//_method:'DELETE',//改成PUT提交
					//id:rows.id  //从选中的rows中获取id
				//},
				//deleteIntentionStudentHandle,
				//"text"
			//);
		}
		function deleteIntentionStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您成功的删除了"+data+"条记录", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_intentionStudent').bootstrapTable('refresh');
		}
		function addPrestudent(){
			var list = $('#tb_intentionStudent').bootstrapTable('getSelections');
			if(list == null||list.length<=0){
				parent.layer.alert('请选择要缴费的学生！');
				return;
			}
			parent.layer.confirm('请再次确认？', {
			    btn: ['是的','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				//成功
				addPrestudent2();
			}, function(){
				
			});
		}
		//缴费
		function addPrestudent2(){
			var list = $('#tb_intentionStudent').bootstrapTable('getSelections');
			var ids = "";
			for(var i =0 ;i<list.length;i++){
				if(i!=list.length-1){
					ids = ids +list[i].id+",";
				}else{
					ids = ids +list[i].id;
				}
			}
			var url = "${pageContext.request.contextPath }/market/intention/addPrestudents";
			$.post(
				url,
				{
					ids:ids  //从选中的rows中获取id
				},
				addPrestudentHandle,
				"text"
			);
		}
		function addPrestudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('缴费成功，共有'+data+'名');
			}else{
				parent.layer.alert('缴费失败！');
			}
			//刷新表格
			$('#tb_intentionStudent').bootstrapTable('refresh');
		}
		
		function bePrestudent(){
			var stuList = $('#tb_intentionStudent').bootstrapTable('getSelections');
			var ids = "";
			for(var i =0 ;i<stuList.length;i++){
				if(i!=stuList.length-1){
					ids = ids +stuList[i].id+",";
				}else{
					ids = ids +stuList[i].id;
				}
			}
			var url = "${pageContext.request.contextPath }/market/intention/bePrestudent";
			$.post(
				url,
				{
					ids:ids  //从选中的rows中获取id
				},
				bePrestudentHandle,
				"text"
			);
		}
		function bePrestudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("状态修改成功！", data+"名学生等待分班。", "success");
			}else if(data==-1){
				swal("状态修改失败", "该学生已缴报名费！", "error");
			}else{
				swal("状态修改失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_intentionStudent').bootstrapTable('refresh');
		}
		//离校
		function beLeave(){
			var stuList = $('#tb_intentionStudent').bootstrapTable('getSelections');
			var ids = "";
			for(var i =0 ;i<stuList.length;i++){
				if(i!=stuList.length-1){
					ids = ids +stuList[i].id+",";
				}else{
					ids = ids +stuList[i].id;
				}
			}
			var url = "${pageContext.request.contextPath }/market/intention/beLeave";
			$.post(
				url,
				{
					ids:ids  //从选中的rows中获取id
				},
				beLeaveHandle,
				"text"
			);
		}
		function beLeaveHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("状态修改成功！", data+"名学生已设置为离开", "success");
			}else{
				swal("状态修改失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_intentionStudent').bootstrapTable('refresh');
		}
		//试学
		function beTest(){
			var stuList = $('#tb_intentionStudent').bootstrapTable('getSelections');
			var ids = "";
			for(var i =0 ;i<stuList.length;i++){
				if(i!=stuList.length-1){
					ids = ids +stuList[i].id+",";
				}else{
					ids = ids +stuList[i].id;
				}
			}
			var url = "${pageContext.request.contextPath }/market/intention/beTeststudent";
			$.post(
				url,
				{
					ids:ids  //从选中的rows中获取id
				},
				function(data){
					if(data>0){
						swal("状态修改成功！", data+"名学生已设置为试学", "success");
					}else{
						swal("状态修改失败", "服务器繁忙！", "error");
					}
					//刷新表格
					$('#tb_intentionStudent').bootstrapTable('refresh');
				},
				"text"
			);
		}
	</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="panel panel-default">
			<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins" style="margin-bottom: 0px">
	                    <div class="ibox-title">
	                        <h5>查询条件</h5>
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
		                    <div class="form-inline" id="formSearch">
						   		<label class="control-label" for="txt_search_name">姓名</label>
						   		<input type="text" class="form-control" id="txt_search_name">   
						   		<label class="control-label" for="txt_search_sex">性别</label>       
								<select class="form-control" id="txt_search_sex" class="form-control">
									<option value=""></option>
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
								<label class="control-label" for="txt_search_phone">电话</label>
								<input type="text" class="form-control" id="txt_search_phone"> 
								<label class="control-label" for="txt_search_sex">状态</label>       
								<select class="form-control" id="txt_search_msStatus" class="form-control">
									<option value="">默认</option>
									<option value="100">全部</option>
									<option value="1">预定报名</option>
									<option value="5">试学</option>
									<option value="2">正式报名</option>
									<option value="3">已分班</option>
									<option value="4">已离校</option>
								</select>
								<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
								<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
								<button type="button" style="margin-left: 50px" id="btn_clean" class="btn btn-primary">
								<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清空</button>
							</div>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
		<div class="panel panel-default">
			<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>意向学生信息</h5>
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
	                        <div id="toolbar" >
		                        <div class="btn-group">
		                        	<button id="btn_add" type="button" class="btn btn-white glyphicon glyphicon-plus" data-toggle="modal" data-target="#addIntentionStudent">
									</button>
									<button id="btn_edit" type="button" class="btn btn-white glyphicon glyphicon-pencil" data-toggle="modal">
									</button>
									<button id="btn_delete" type="button" class="btn btn-white glyphicon glyphicon glyphicon-minus">
									</button>
		                        </div>
		                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                        <div class="btn-group">
		                        	<button id="btn_money" type="button" class="btn btn-warning" onclick="addPrestudent()">
										缴预定报名费
									</button>
									<button id="btn_money" type="button" class="btn btn-info" onclick="beTest()">
										试学
									</button>
									<button id="btn_money" type="button" class="btn btn-success" onclick="bePrestudent()">
										正式报名
									</button>
									<button id="btn_money" type="button" class="btn btn-danger" onclick="beLeave()">
										离校
									</button>
		                        </div>
							</div>
							
							<div class="modal inmodal" id="addIntentionStudent" tabindex="-1" role="dialog" aria-hidden="true">
					            <div class="modal-dialog">
					                <div class="modal-content animated bounceInRight">
					                    <div class="modal-header">
					                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					                        </button>
					                        <h4 class="modal-title">添加意向学生信息</h4>
					                        <small class="font-bold"></small>
					                     </div>
					                       <div class="modal-body">
					                       	<form class="form-horizontal m-t" id="intenstuForm" novalidate="novalidate">
												<input id="id" name="id" type="hidden">
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">姓名：</label>
					                                <div class="col-sm-8">
					                                    <input id="name" name="name" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                               <label class="col-sm-3 control-label">性别：</label>
					                               <div class="col-sm-8">
					                               	<label class="control-label"><input type="radio"  name="sex"  value="男" checked="checked">&nbsp;&nbsp;&nbsp;男</label>
					                               	<label class="control-label" style="padding-left: 20px"><input type="radio"  name="sex"  value="女" >&nbsp;&nbsp;女</label>
					                               </div>
					                           	</div>
					                           	<div class="form-group">
					                                <label class="col-sm-3 control-label">年龄：</label>
					                                <div class="col-sm-8">
					                                    <input id="age" name="age" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">电话：</label>
					                                <div class="col-sm-8">
					                                    <input id="phone" name="phone" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">QQ号：</label>
					                                <div class="col-sm-8">
					                                    <input id="qqCode" name="qqCode" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">身份证：</label>
					                                <div class="col-sm-8">
					                                    <input id="idCard" name="idCard" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">意向等级：</label>
					                                <div class="col-sm-8">
					                                	<select class="form-control" name="iGrade" id="iGrade" class="form-control">
						                                	<option value="低">低</option>
						                                	<option value="中">中</option>
						                                	<option value="高">高</option>
						                                </select>
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">住址：</label>
					                                <div class="col-sm-8">
					                                	<input id="addr" name="addr" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">毕业学校：</label>
					                                <div class="col-sm-8">
					                                	<input id="school" name="school" type="text" class="form-control">
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">毕业班级：</label>
					                                <div class="col-sm-8">
					                                	<input id="clsName" name="clsName" type="text" class="form-control">
					                                </div>
					                            </div>
											</form>
					                       </div>
					                       <div class="modal-footer">
					                           <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
											   <button type="button" id="save" onclick="" class="btn btn-primary">保存</button>
					                       </div>
					                    </div>
					                </div>
					            </div>
							
							<!-- table代码就这些，用js构建表格 -->
							<table id="tb_intentionStudent"></table>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
	</div>
	
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	
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
	            	name: "required",
	            	age: {
	                	required:true,
	                	digits:true
	                },
	                phone: {
	                	required:true,
	                	digits:true
                    },
                    addr: {
	                	required:true
	                },
	                school: {
	                	required:true
	                }
	            },
	            messages: {
	            	name: icon + "请输入姓名",
	            	age :{
                    	required:icon + "请输入年龄",
                    	digits:icon + "请输入数字（整数）"
                    },
                    phone :{
                    	required:icon + "请输入电话号码",
                    	digits:icon + "请输入数字（整数）"
                    },
                    addr :{
                    	required:icon + "请输入住址"
                    },
                    school :{
                    	required:icon + "请输入毕业学校"
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