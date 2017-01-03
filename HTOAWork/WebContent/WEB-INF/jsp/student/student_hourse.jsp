<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>宿舍 分配表</title>
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
			//修改按钮事件
			$("#btn_edit").click(function(){
				//if(rows==null || rows.id==null){
					//swal("！", "请选择你要修改的ID", "error");
					//把内容放到更新的列表
					//$('#window_update').modal();
					//parent.layer.alert('请选你要修改的数据！');
					//return;
				//}
				if(rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_update #id").val(rows.id);
 				$("#window_update #floorid").val(rows.floorid);
 				selectupdate(rows.floorid);
 				$("#window_update #count").val(rows.count);
 				//$("#window_update #layerid").val(rows.layerid);
 				$("#window_update #layerid").find("option:selected").text();
 				//alert(rows.layerid);
 				$("#window_update #addr").val(rows.addr);
 				$("#window_update #hoursename").val(rows.hoursename);
 				$("#window_update input[type=radio][name=hsex][value="+rows.hsex+"]").attr("checked",'checked');
 				$("#window_update #remark").val(rows.remark);
 				$('#window_update').modal('show');
                $("#updateButton").attr("onclick","updateStudent()");
	       });
			
			$("#btn_query").click(function(){
				//刷新表格
				$('#tb_departments').bootstrapTable('refresh');
				
	          
	        });
			$("#btn_clean").click(function(){
				//刷新表格
				$("#txt_search_hoursename").val("");
				$("#txt_search_hsex").val("");
				$('#tb_departments').bootstrapTable('refresh');
	          
	        });
			
			//新增事件
			$("#btn_add").click(function(){
				//清空新增div中的数据
				$("#window_add #id").val('');
				//$("#window_add #floorid").val('');
				//$("#window_add #layerid").val('');
		        $("#window_add #hoursename").val('');
		        $("#window_add #count").val('');
		        //$("#window_add #addr").val('');
		        $("#window_add #remark").val('');
		        //$("#window_add #birthday").val('');
		        $('#window_add').modal('show');
		        
		    });
			//删除按钮事件
			//*************************************************************************按钮事件
			$('#btn_delete').click(function () {
			   if(rows == null || rows.id==null){
	               	swal("删除失败！", "请选择删除项", "error");
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
		               	deleteStudent();
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
					url : '${pageContext.request.contextPath}/student/studentHourse/hourseJson', //请求后台的URL（*）
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
					search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : true,
					//showColumns : true, //是否显示所有的列
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
						checkbox : true
					},{
						field : 'number',
						title : '序号',
						formatter: function (value, row, index) { 
							return index+1; 
						} 

					}, {
						field : 'id',
						title : 'ID'
					}, {
						field : 'hoursename',
						title : '宿舍名称'
					}, {
						field : 'hsex',
						title : '宿舍性别'
					},{
						field : 'count',
						title : '宿舍限定人数'
					},{
						field : 'currentCount',
						title : '现住人数',
						formatter : function(value, row, index) {
							var num = row.currentCount;
							if(num==row.count){
								return "<span class='label label-danger'>已满</span>";
							}else{
								return num;
							}
						}
					},{
						field : 'createTime',
						title : '创建时间',
						formatter : function(value, row, index) {
							var times = row.createTime;
							if(times==null){
								return "空";
							}else{
								return times.substring(0, 10);
							}
						}
					},{
						field : 'remark',
						title : '说明'
					},]
				});
			};
			
			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					//hoursename : $("#tb_departments_search input[placeholder=搜索]").val(),
					hoursename:$("#formSearch #txt_search_hoursename").val(),
					hsex:$("#formSearch #txt_search_hsex").val()
					
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
		if(!validateForm($("#addForm"))){
			return;
		}
 		var url = "${pageContext.request.contextPath }/student/studentHourse/addhourse";
 		$.post(
 			url,
 			{
 				id:$("#window_add #id").val(),
 				floorid:$("#window_add #floorid").val(),
 				layerid:$("#window_add #layerid").val(),
 				hoursename:$("#window_add #hoursename").val(),
 				hsex:$('#window_add input[name=hsex]:checked').val(),
 				count:$("#window_add #count").val(),
 				addr:$("#window_add #addr").val(),
 				remark:$("#window_add #remark").val()
 			},
 			addStudentHandle,
 			"text"
 		);	
 		
 		//用来关闭新增窗口***********
 		$("#window_add").modal('hide')
 	}
 	function addStudentHandle(data){
 		//从后台返回出来的int类型数据，用来判断是否新增成功
 		if(data>0){
 			swal({
                 title: "成功",
                 text: "你已经完成添加操作",
                 type: "success"
             });
 			//刷新表格
 			$('#tb_departments').bootstrapTable('refresh');
 		}else{
 			swal("添加失败", "请检查你输入的数据！", "error");
 		}
 		
 	}
 	
 	//修改学生，ajax提交
 	function updateStudent(){
 		var id =$("#window_update #id").val(); 
 		var url = "${pageContext.request.contextPath }/student/studentHourse/"+id;
 		$.post(
 			url,
 			{
 				_method:'PUT',//改成PUT提交
 				id:$("#window_update #id").val(),
 				floorid:$("#window_update #floorid").val(),
 				count:$("#window_update #count").val(),
 				layerid:$("#window_update #layerid").val(),
 				hoursename:$("#window_update #hoursename").val(),
 				hsex:$('#window_update input[name=hsex]:checked').val(),
 				remark:$("#window_update #remark").val(),
 				addr:$("#window_update #addr").val()
 			},
 			updateStudentHandle,
 			"text"
 		);	
 		//用来关闭修改窗口***********
 		$("#window_update").modal('hide')
 		rows = null;
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
 		$('#tb_departments').bootstrapTable('refresh');
 		//把保存选中行的rows变量清零，很重要，防止缓存
 		rows = null;
 	}
 	
 	
 	//***************************************************************************************删除
 	//删除学生，ajax提交
 	function deleteStudent(){
 		//alert('删除');
 		var id = rows.id;
 		var url = "${pageContext.request.contextPath }/student/studentHourse/"+id;
 		//alert(url);
 		$.post(
 			url,
 			{
 				_method:'DELETE',//改成PUT提交
 				id:rows.id,  //从选中的rows中获取id
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
 		$('#tb_departments').bootstrapTable('refresh');
 	}
    //修改二级联动
    function selectupdate(floorid){
    	var url = "${pageContext.request.contextPath }/student/studentHourse/findLayer";
		$.post(url, {
			floorid : floorid,
		}, initupdate, "text");
    }
    function initupdate(data) {
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
						+'第'+ datas.rows[i].layername +'层'+ '</option>';
			}
			$('#window_update #layerid').empty();
			$('#window_update #layerid').html(opt);
			$('#window_update #layerid').trigger("chosen:updated");
			$('#window_update #layerid').chosen();
		}else{
			$('#window_update #layerid').empty();
		}
	}
 	
 	//二级联动
	function selectemp() {
 		
		var url = "${pageContext.request.contextPath }/student/studentHourse/findLayer";
		$.post(url, {
			floorid : $("#window_add #floorid").val(),
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
						+ '第'+datas.rows[i].layername +'层'+'</option>';
			}
			$('#window_add #layerid').empty();
			$('#window_add #layerid').html(opt);
			$('#window_add #layerid').trigger("chosen:updated");
			$('#window_add #layerid').chosen();
		}else{
			$('#window_add #layerid').empty();
		}
	}
	function selectLayerNameUpdate(){
		var floorName = $("#window_update #floorid").find("option:selected").text();
		
 		//$("#window_add #hoursename").val(floorName);
		//var Floor = $("#window_add #hoursename").val();
		var Layer = $("#window_update #layerid").find("option:selected").text();
		var value = Layer.replace(/[^0-9]/ig,""); 
		//alert(value);
		$("#window_update #hoursename").val(floorName+value+"0");
	}
	function selectLayerName(){
		var floorName = $("#window_add #floorid").find("option:selected").text();
		
 		//$("#window_add #hoursename").val(floorName);
		//var Floor = $("#window_add #hoursename").val();
		var Layer = $("#window_add #layerid").find("option:selected").text();
		var value = Layer.replace(/[^0-9]/ig,""); 
		//alert(value);
		$("#window_add #hoursename").val(floorName+value+"0");
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
							<div class="form-group" style="margin-top: 15px">
								<label class="control-label col-sm-1" for="txt_search_departmentname">宿舍名称</label>       
								<div class="col-sm-2">
									<input id="txt_search_hoursename" type="text" placeholder="可模糊查询" class="form-control">       
								</div>
								<label class="control-label col-sm-1" for="txt_search_floorid">宿舍性别</label>
								<div class="col-sm-2">
									<!-- input type="text" id="" class="form-control" id="txt_search_statu" -->
									<select  id="txt_search_hsex" class="form-control" style="width:120px;height:30px;" tabindex="2">
		                              	<option value=""> -选择性别-</option >
		                                <option value="男">男</option >
		                                <option value="女">女</option >
		                            </select>      
								</div>
								<div class="col-sm-3">
									<button type="button" style="margin-left: 30px" id="btn_query" class="btn btn-primary">查询</button>
									<button type="button" style="margin-left: 30px" id="btn_clean" class="btn btn-primary">清空</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success"
				>
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
			<!-- <div data-toggle="modal" data-target="#window_allot" class="btn btn-success btn-outline">
				<i  class='glyphicon glyphicon-send'>宿舍分配</i>
			</div> -->
		</div>

		<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">宿舍修改</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="dataForm"
							novalidate="novalidate">
							<input id="id" type="hidden">
                        	<div class="form-group">
                                <label class="col-sm-3 control-label">楼栋：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" onchange="selectupdate(this.value)" tabindex="2" name="floorid" id="floorid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${Floorlist}" var="e">
		                                   <option value="${e.id}">${e.floorname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">楼层：</label>
                                <div class="col-sm-8">
                                    <select class="chosen-select" style="width:362px;height:30px;" onchange="selectLayerNameUpdate();" tabindex="2" name="layerid" id="layerid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${Layerlist}" var="e">
		                                   <option value="${e.id}">第${e.layername}层</option >
		                                </c:forEach>
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">宿舍名称：</label>
                                <div class="col-sm-8">
                                    <input id="hoursename" name="hoursename" placeholder="例如：1栋101" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">宿舍性别：</label>
                                <div class="col-sm-8">
                                	<label class="control-label"><input type="radio"  name="hsex"  value="男" >&nbsp;&nbsp;&nbsp;男</label>
                                	<label class="control-label" style="padding-left: 20px"><input type="radio"  name="hsex"  value="女" >&nbsp;&nbsp;女</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">宿舍限定人数：</label>
                                <div class="col-sm-8">
                                    <input id="count" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                    <input id="remark" type="text" required class="form-control">
                                </div>
                            </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" id="updateButton" onclick="updateStudent()"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">宿舍添加</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<div class="form-group">
                                <label class="col-sm-3 control-label">楼栋：</label>
                                <div class="col-sm-8">
                                   <select class="chosen-select" style="width:362px;height:30px;" onchange="selectemp();" tabindex="2" name="floorid" id="floorid">
		                              	<option value=""> -请选择---</option >
		                              	<c:forEach items="${Floorlist}" var="e">
		                                   <option value="${e.id}">${e.floorname}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">楼层：</label>
                                <div class="col-sm-8">
                                	<select class="chosen-select" style="width:362px;height:30px;" tabindex="2" name="layerid" id="layerid" onchange="selectLayerName();">
		                              	<option value=""> -请选择---</option >
		                              	
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">宿舍名称：</label>
                                <div class="col-sm-8">
                                    <input id="hoursename" type="text" placeholder="例如：1栋101" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">宿舍性别：</label>
                                <div class="col-sm-8">
                                	<label class="control-label"><input type="radio"  name="hsex"  value="男" >&nbsp;&nbsp;&nbsp;男</label>
                                	<label class="control-label" style="padding-left: 20px"><input type="radio"  name="hsex"  value="女" >&nbsp;&nbsp;女</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">限定人数：</label>
                                <div class="col-sm-8">
                                     <input id="count" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                    <input id="remark" type="text" required class="form-control">
                                </div>
                            </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" id="btn_cancel" data-dismiss="modal">关闭</button>
								<button type="button" onclick="addStudent()" class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 选择宿舍 -->
         <div class="modal inmodal" id="window_allot" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated fadeIn">
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox-content">
								<div id="toolbar_stu">
									<select class="chosen-select" style="width:130px;height:30px;" tabindex="2" id="huorid">
		                              	<option value=""> -分配宿舍---</option >
		                              	<c:forEach items="${Hourselist}" var="c">
		                                   <option value="${c.id}">${c.hoursename}</option >
		                                 </c:forEach> 
		                            </select>     
								</div>
								<!-- table代码就这些，用js构建表格 -->
								<div id="tb_search">
									<table id="tb_student"></table>
								</div>
								<div class="modal-footer">
	                                 <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
	                                 <button type="button" id="btn_setStudent" onclick="setStudent()" class="btn btn-primary" data-dismiss="modal">确认</button>
	                            </div>
							</div>
						</div>
				  </div>
			</div>
		</div>
     </div>
				

		<!-- table代码就这些，用js构建表格 -->
		<div id="tb_departments_search">
		<table id="tb_departments"></table>
		</div>
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
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
	            	floorid: "required",
	            	layerid: "required",
	            	hoursename: "required",
	            	addr: "required",
	            	count:"required"
	            },
	            messages: {
	            	floorid: icon + "请选择楼栋",
	            	layerid: icon + "请选择楼层",
	            	hoursename: icon + "请输入宿舍名称",
	            	addr: icon + "请输入地址",
	            	count: icon + "请输入人数"
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		//***input控件要设置name属性***
		function validateForm2(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	 srmName: {
	                    	required:true,
		                	digits:true
	                 }
	            },
	            messages: {
	            	srmName: {
                    	required:icon + "请输入分数",
                    	digits: icon + "分数必须是整数"
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
	
	<script type="text/javascript">
        $(function () {
        	// $('#tb_student').bootstrapTable('showColumn', 'id');
            $('#tb_departments').bootstrapTable('hideColumn', 'id');
        }); 
    </script>
</body>
</html>