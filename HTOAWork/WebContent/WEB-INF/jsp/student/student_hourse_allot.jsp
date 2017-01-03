<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>宿舍 分配</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
	<script type="text/javascript">
	
    //全局变量，用来保存选中行的数据
	    $(function () {
	    	
	    	
	    	$("#btn_cancel").click(function(){
				//刷新表格
				$("#classid").val("");
				$("#stuname").val("");
				$('#tb_student').bootstrapTable('refresh');
	          
	        });
	    	$('#selectByClassid').click(function (){
				$('#tb_student').bootstrapTable('refresh');
			})
	    	$("#btn_clean_selectByClassid").click(function(){
				//刷新表格
				$("#classid").val("");
				$("#stuname").val("");
				$('#tb_student').bootstrapTable('refresh');
	          
	        });
	    	$("#random").click(function(){
				//刷新表格;
				$('#tb_student').bootstrapTable('refresh');
	          
	        });
	    	 $("#allot_add").click(function(){
	    		 if(rows==null){
		 				parent.layer.alert('请选择你要分配的宿舍！');
		 				return;
		 			}
		 			//var number = rows.count - rows.currentCount;
		 			if(rows.count == rows.currentCount){
		 				parent.layer.alert('此宿舍已满，请选择其他的宿舍！');
		 				return;
		 			}
		 			selectHourseSex();
		 			var num=0;
		 			$("#number").val(num);
		 			$('#tb_student').bootstrapTable('refresh');
		 			$('#window_allot_ok').modal('show');
		 			document.getElementById('random').style.display = "none";
		 			document.getElementById('StudentSearch').style.display = ""; 
	            //$("#btn_setStudent").attr("onclick","allot_save()");
	         });
	    	 
	    	 $('#allot_delete').click(function(){
	    		 if(Roomrows==null){
		 				parent.layer.alert('请选择你要删除的学生！');
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
	                	deleteStudentallot();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
	    	 });
	    	 //学生明细表格数据
			$('#tb_studentDetail').bootstrapTable({
				url : '${pageContext.request.contextPath}/student/studentHourse/studentRoomJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuRoom', //工具按钮用哪个容器
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 7, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				showRefresh : true, //是否显示刷新按钮
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				//searchOnEnterKey :true, //按回车搜索
				minimumCountColumns : 2, //最少允许的列数
				singleSelect: true,  //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(Roomrows != null){
	                	Roomrows = null;
	                }
	                Roomrows = row;
	            },//单击row事件
	            onUncheck: function(row) {
	            	Roomrows = null;
	            },
	            columns : [ {
					checkbox : true,
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
					field : 'hourseid',
					title : '宿舍',
					formatter : function(value, row, index) {
						var hourse = row.hourse;
						if(hourse==null){
							return "-";
						}else{
							return hourse.hoursename;
						}
					}
				},{
					field : 'studentid',
					title : '学生'
					
				},{
					field : 'classname',
					title : '班级'
					
				},]
				
			});
	    	
	    	
			$('#tb_student').bootstrapTable({
				url : '${pageContext.request.contextPath}/student/studentHourse/studentJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stu', //工具按钮用哪个容器
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 6, //每页的记录行数（*）
				pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
				search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
				strictSearch : false,
				searchOnEnterKey :true, //按回车搜索
				//minimumCountColumns : 2, //最少允许的列数
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				columns : [ {
					checkbox : true,
				},{
					field : 'number',
					title : '序号',
					formatter: function (value, row, index) { 
						return index+1; 
					}
				}, {
					field : 'stuname',
					title : '姓名'
				}, {
					field : 'sex',
					title : '性别'
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
					field : 'huorsename',
					title : '宿舍',
					formatter : function(value, row, index) {
						var hourse = row.hourse;
						if(hourse==null){
							return "空";
						}else{
							return hourse.hoursename;
						}
					}
				}, ]
			});
		});
		//得到查询的参数
		function queryParams(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				stuname : $("#stuname").val(),
				hourseid:$("#hourseid").val(),
				sex:$("#student_sex").val(),
				classid:$("#classid").val(),
				Num:$("#number").val()
			};
			return temp;
		};
		
		//删除已分配的学生
		function deleteStudentallot(){
			//alert('删除');
			var id = Roomrows.id;
			var url = "${pageContext.request.contextPath }/student/studentRoom/"+id;
			//alert(url);
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:Roomrows.id,  //从选中的rows中获取id
					studentid:Roomrows.studentid
				},
				deleteStudentallotHandle,
				"text"
			);	
			$('#tb_studentDetail').bootstrapTable('refresh');
		}
		function deleteStudentallotHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
				$('#tb_studentDetail').bootstrapTable('refresh');
				$('#tb_departments').bootstrapTable('refresh');
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			
		}
		
			
		//选择学生分配
		function setStudent(){
			//这里是所有的选中行数据，和后台传进来的一样，怎么取看下面,student是list
			var stuList = $('#tb_student').bootstrapTable('getSelections');
			var huorid = rows.id;
			//alert("===="+huorid);
			var ids = "";
			for(var i =0 ;i<stuList.length;i++){
				if(i!=stuList.length-1){
					ids = ids +stuList[i].id+",";
				}else{
					ids = ids +stuList[i].id;
				}
			}
			//alert(ids);
			var url = "${pageContext.request.contextPath}/student/studentHourse/studentHourseLot";
	 		$.post(
	 			url,
	 			{
	 				huorid:huorid,
	 				ids:ids
	 			},
	 			updatestudentallot,
	 			"text"
	 		);	
		}
		
		function updatestudentallot(data){
	 		//从后台返回出来的int类型数据，用来判断是否新增成功
	 		if(data>0){
	 			swal({
	                 title: "成功",
	                 text: "你已经完成宿舍分配",
	                 type: "success"
	             });
	 			//刷新表格
	 			$('#tb_student').bootstrapTable('refresh');
	 			$('#tb_departments').bootstrapTable('refresh');
	 			$('#tb_studentDetail').bootstrapTable('refresh');
	 			$("#window_allot #classid").val('');
	 		}else{
	 			swal("分班失败", "请检查你选择的数据！", "error");
	 		}
	 		
	 	}
		function selectClassid(){
			
		}
		function selClass(classid){ 
			//刷新表格
			$('#tb_student').bootstrapTable('refresh');
		}
	</script>

	<script type="text/javascript">
	  //全局变量，用来保存选中行的数据
	    var rows = null;
	    var Roomrows=null;
	  	
	  	function test(id){
		    $("#hourseid").val(id);
		    //让模板明细表刷新
			$('#tb_studentDetail').bootstrapTable('refresh');
		}
	  	function selectHourseSex(){
	  		var sex = rows.hsex;
	  		//alert(sex);
	  		$("#student_sex").val(sex);
	  	}
	    $(function () {	
	    	//精选分配
	    	 $("#allot_ok").click(function(){
	 			if(rows==null){
	 				parent.layer.alert('请选择你要分配的宿舍！');
	 				return;
	 			}
	 			var number = rows.count - rows.currentCount;
	 			if(rows.count == rows.currentCount){
	 				parent.layer.alert('此宿舍已满，请选择其他的宿舍！');
	 				return;
	 			}
	 			selectHourseSex();
	 			var num=0;
	 			$("#number").val(num);
	 			$('#tb_student').bootstrapTable('refresh');
	 			$('#window_allot_ok').modal('show');
	 			document.getElementById('random').style.display = "none";
	 			document.getElementById('StudentSearch').style.display = ""; 
	            //$("#btn_setStudent").attr("onclick","allot_save()");
	         });
	    	//随机分配
	    	 $("#allot_random").click(function(){
		 			if(rows==null){
		 				parent.layer.alert('请选择你要分配的宿舍！');
		 				return;
		 			}
		 			selectHourseSex();
		 			var num=rows.count - rows.currentCount;
		 			if(num<=0){
		 				parent.layer.alert('宿舍已满，请选择其他的宿舍！');
		 				return;
		 			}
		 			$("#number").val(num);
		 			$('#tb_student').bootstrapTable('refresh');
		 			$('#window_allot_ok').modal('show');
		 			document.getElementById('random').style.display = "";
		 			document.getElementById('StudentSearch').style.display = "none";
		            //$("#btn_setStudent").attr("onclick","allot_save()");
		         });
	    	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			$("[data-toggle='tooltip']").tooltip();
			
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
					pageSize : 6, //每页的记录行数（*）
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
						field : 'Id',
						title : '操作',
						align : 'center',
						formatter : function(value, row, index) {
							var id = row.id;
							return "<a onclick='test("+id+")'><i class='fa fa-file-text-o'>查看学生</i></a>";
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
		<div class="row">
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>宿舍信息</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
						<div id="toolbar" class="btn-group">
                        	<button id="allot_ok" class="btn btn-primary dim" type="button"><i class="glyphicon glyphicon-ok"></i>&nbsp;&nbsp;精选分配</button>
							<button id="allot_random" class="btn btn-success  dim" type="button"><i class="glyphicon glyphicon-random"></i>&nbsp;&nbsp;随机分配</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<div id="tb_departments_search">
							
							<table id="tb_departments"></table>
						</div>
					</div>
                </div>
            </div>
			<div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学生明细</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div id="toolbar_stuRoom" class="btn-group">
							<button id="allot_add" class="btn btn-info  dim" type="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>
							<button id="allot_delete" class="btn btn-warning  dim" type="button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删 除</button>
						</div>
						<!--  table代码就这些，用js构建表格 -->
						<div id="tb_studentDetail_search">
							<input id="hourseid" type="hidden">
							<table id="tb_studentDetail"></table>
						</div>
                    </div>
               </div>
         </div>
    </div> 
    </div>  
	<!-- 精选分配宿舍 -->
    <div class="modal inmodal" id="window_allot_ok" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated fadeIn">
				<div class="row">
					<div class="col-sm-12">
						<div class="ibox-content">
							<form id="StudentSearch" role="form" class="form-inline">
	                           	<div>
	                                <select class="form-control" id="classid">
		                              	<option value="">选择班级</option >
		                              	<c:forEach items="${ClassList}" var="c">
		                                   <option value="${c.id}">${c.classname}</option >
		                                </c:forEach> 
			                        </select>
			                        <input type="text" id="stuname" placeholder="输入姓名" class="form-control"/> 
		                            <button id="selectByClassid" class="btn btn-outline btn-primary" type="button" style="margin-left: 80px">查询</button>
		                            <button id="btn_clean_selectByClassid" class="btn btn-outline btn-default" type="button">清空</button>
	                        	</div>
	                        </form>
							<!-- table代码就这些，用js构建表格 -->
							<div id="tb_search">
								<input type="hidden" id="student_sex">
								<input type="hidden" id="student_classid">
								<input type="hidden" id="number" value='0'>
								<table id="tb_student"></table>
							</div>
							<div class="modal-footer">
								 <button type="button" class="btn btn-success" id="random"><i class="glyphicon glyphicon-repeat"></i>&nbsp;换一批</button>
                                 <button type="button" class="btn btn-white" id="btn_cancel" data-dismiss="modal">取消</button>
                                 <button type="button" id="setStudent" onclick="setStudent()" class="btn btn-primary" data-dismiss="modal">确认</button>
                            </div>
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
        $(function () {
        	$('#tb_studentDetail').bootstrapTable('hideColumn', 'id');
            $('#tb_departments').bootstrapTable('hideColumn', 'id');
        }); 
    </script>
</body>
</html>