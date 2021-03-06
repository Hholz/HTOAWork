<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/plugins/markdown/bootstrap-markdown.min.css" />
<title>办公用品列表</title>

<script type="text/javascript">
	//全局变量，用来保存选中行的数据
	var rows = null;
	var processjson="";
	var coursejson="";
	$(function() {
		$("#find_empid").val($("#window_add #id").val());
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();
		//新增按钮事件*************************
		$("#btn_add").click(function() {
			$("#window_add #empid").val($("#window_add #id").val());
			if($("#window_add #classid").val()==null || $("#window_add #classid").val()==""){
				$("#window_add #thisclass").css("display","none");
				$("#window_add #thiscourse").css("display","none");
				$("#window_add #thisoutline").css("display","none");
				$("#window_add #thishour").css("display","none");
			}else{
				$("#window_add #thisclass").css("display","block");
				$("#window_add #thiscourse").css("display","block");
				$("#window_add #thisoutline").css("display","block");
				$("#window_add #thishour").css("display","block");
				getCourse();
			}
			
		});
		//修改按钮事件
		$("#btn_edit").click(function() {
			if(rows==null){
				parent.layer.alert('请选你要修改的数据！');
				return;
			}
			$("#window_update #id").val(rows.id);
			$("#window_update #empid").val(rows.empid);
			$("#window_update #workday").val(rows.workday);
			$("#window_update #courseid").val(rows.courseid);
			$("#window_update #process").val(rows.process);
			$("#window_update #hour").val(rows.hour);
			$("#window_update #contents").val(rows.contents);
			$('#window_update').modal('show');
			updateprocess("courseid");
		});
		//条件查询click事件注册
		$("#query").click(function() {
			$("#tb_departments").bootstrapTable('refresh');
		});
		//删除按钮事件
		//*************************************************************************按钮事件
		$('#btn_delete').click(function() {
			if(rows==null){
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			swal({
				title : "您确定要删除这条信息吗",
				text : "删除后将无法恢复，请谨慎操作！",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "是的，我要删除！",
				cancelButtonText : "让我再考虑一下…",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					//调用删除函数
					deleteStudent();
				} else {
					swal("已取消", "您取消了删除操作！", "error");
				}
			});
		});
	});
	var getCourse = function(){
		var url = "${pageContext.request.contextPath}/dailyWork/Worklog/getCourseByClassId";
		$.post(
			url,{
				classId : $("#window_add #classid").val()
			},
			resetCourse,
			"json"
		);
		function resetCourse(datas){
			if(datas.rows.length>0){
				$("#window_add #thiscourse").css("display","block");
				$("#window_add #thisoutline").css("display","block");
				$("#window_add #thishour").css("display","block");
				var str= "";
				var i;
				for (i=0;i<datas.rows.length;i++) {
					str +='<option value="'+datas.rows[i].id+'">'+datas.rows[i].name+'</option>';
				}
				str +='<option value="-1">未讲课</option>';
				$("#window_add #courseid").html(str);
				getOutLine();
			}else{
				$("#window_add #thiscourse").css("display","none");
				$("#window_add #thisoutline").css("display","none");
				$("#window_add #thishour").css("display","none");
				$("#window_add #courseid").html('<option value="0">无课程信息</option>');
			}
		}
	}
	var getOutLine = function(){
		var id = $("#window_add #courseid").val();
		if(id==""){
			$('#window_add #process').html("<option value='0'>暂无大纲信息</option>");
			return;
		}
		if(id==-1){
			$('#window_add #process').html("<option value='0'>暂无大纲信息</option>");
			$('#window_add #hour').html("<option value='0'>暂无课时信息</option>");
			$("#window_add #thisoutline").css("display","none");
			$("#window_add #thishour").css("display","none");
			return;
		}
		$("#window_add #thisoutline").css("display","block");
		$("#window_add #thishour").css("display","block");
		var url = "${pageContext.request.contextPath}/education/syllabus/getOutlineByCourseId2";
		$.post(
			url,
			{
				_method : 'PUT',
				courseId : $("#window_add #courseid").val()
			},
			resetOutLine,
			"text"
		);
		function resetOutLine(data){
			//alert(data);
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				var str = "";
				for (var i = 0; i<datas.rows.length; i++) {
					str += '<option value="'+datas.rows[i].outline.id+'">'
							+ datas.rows[i].outline.outlineName + '</option>';
				}
				$('#window_add #process').html(str);
				getHourFromOutLine();
			}else{
				$('#window_add #process').html("<option value='0'>暂无大纲信息</option>");
				$('#window_add #hour').html("<option value='0'>暂无课时信息</option>");
			}
		}
	}
	var getHourFromOutLine = function (){
		var id = $("#window_add #courseid").val();
		if(id==""){
			$('#window_add #hour').html("<option value='0'>暂无课时安排</option>");
			return;
		}
		var outline = $('#window_add #process').val();
		var url = "${pageContext.request.contextPath}/education/syllabus/getHourFromOutLine";
		$.post(
			url,
			{
				_method : 'PUT',
				id: outline
			},
			setHour,
			"json"
		);
		function setHour(datas){
			//alert(datas);
			if(datas.rows.length>0){
				if(datas.rows[0].hour>0){
					var str = "";
					for(var i=1;i<=datas.rows[0].hour;i++){
						str += '<option value="'+i+'">第'+i+'课时</option>';
						$('#window_add #hour').html(str);
					}
				}else{
					$('#window_add #hour').html("<option value='0'>暂无课时安排</option>");
				}
			}
		}
	}
	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_departments').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/Worklog/0', //请求后台的URL（*）
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
				pageSize : 10, //每页的记录行数（*）
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
	            },
				columns : [ {
					checkbox : true
				}, {
					field : 'id',
					title : 'ID',
					align : 'center',
					valign : 'middle',
					visible:false
				}, {
					field : 'empname',
					title : '员工',
					align : 'center',
					valign : 'middle'
				}, {
					field : 'workday',
					title : '工作日期',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index){
						if (value) {
							return value.substring(0, 10);
						}
					}
				}, {
					field : 'classname',
					title : '班级',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index){
						if(!value){
							return "无";
						}else{
							return value;
						}
					}
				},  {
					field : 'educoursename',
					title : '课程',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index){
						if(!value){
							return "无";
						}else{
							return value;
						}
					}
				}, {
					field : 'eduoutlinename',
					title : '大纲',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index){
						if(!value){
							return "无";
						}else{
							return value;
						}
					}
				}, {
					field : 'hour',
					title : '完成的课程课时',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index){
						if(!value){
							return "无";
						}else{
							return "第"+value+"课时";
						}
					}
				},{
					field : 'contents',
					title : '工作内容',
					align : 'center',
					valign : 'middle'
				}, ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码  
				empid : $("#find_empid").val(),
				workday : $("#find_workday").val(),
				courseid : $("#find_courseid").val(),
				classid:$("#find_classid").val(),
				process : $("#find_process").val(),
				contents : $("#find_contents").val(),
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
	function addStudent() {
		if($("#window_add #contents").val().trim()==null || $("#window_add #contents").val().trim()==""){
			swal({
				title : "警告",
				text : "请填写工作日报内容！",
				type : "warning"
			});
			return;
		}
		/* if(!validateForm($("#addForm"))){
			return;
		} */
		var url = "${pageContext.request.contextPath }/dailyWork/Worklog/add";
		$.post(url, {
			empid : $("#window_add #id").val(),
			courseid : $("#window_add #courseid").val(),
			classid : $("#window_add #classid").val(),
			process : $("#window_add #process").val(),
			hour : $("#window_add #hour").val(),
			contents : $("#window_add #contents").val(),
		}, addStudentHandle, "text");

		//用来关闭新增窗口***********
		$("#window_add").modal('hide')
		//刷新数据
		$('#tb_departments').bootstrapTable('refresh');
		//清空新增div中的数据
		/* $("#window_add #workday_add").val('');
		$("#window_add #courseid").val('0');
		$("#window_add #process").val('');
		$("#window_add #hour").val('');
		$("#window_add #contents").val('');
		addprocess('courseid'); */
	}
	function addStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			});
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		$('#tb_departments').bootstrapTable('refresh');
	}

	//修改学生，ajax提交
	function updateStudent() {
		if(!validateForm($("#updateForm"))){
			return;
		}
		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/dailyWork/Worklog/" + id;
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			empid : $("#window_update #empid").val(),
			courseid : $("#window_update #courseid").val(),
			classid : $("#window_update #classid").val(),
			process : $("#window_update #process").val(),
			hour : $("#window_update #hour").val(),
			contents : $("#window_update #contents").val()
		}, updateStudentHandle, "text");
		//用来关闭修改窗口***********
		$("#window_update").modal('hide');
	}
	function updateStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成修改操作",
				type : "success"
			});
		}else if(data==-1){
			swal("修改失败", "新增超过12小时的数据不能修改", "error");
		}else {
			swal("修改失败", "请检查你输入的数据！", "error");
		}
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
		//把保存选中行的rows变量清零，很重要，防止缓存
		rows = null;
	}
	//********************************************************删除
	//删除学生，ajax提交
	function deleteStudent() {
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/dailyWork/Worklog/"
				+ id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
			id : rows.id
		//从选中的rows中获取id
		}, deleteStudentHandle, "text");
	}
	function deleteStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else {
			swal("删除失败", "新增超过12小时的数据不能删除", "error");
		}
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
	}
	function getprocess(data){
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			for (var i = 0; i<datas.rows.length; i++) {
				processjson += '<option value="'+datas.rows[i].id+'">'
						+ datas.rows[i].outlineName + '</option>';
			}
		}
	}
	//查询联动
	function findprocess(date){
		var id=$("#"+date.id).val();
		processjson = '<option value="-1">不限</option><option value="0">-</option>';
		var url = "${pageContext.request.contextPath }/dailyWork/Worklog/findprocess/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
			},
			findgetprocess,
			"text"
		);
    }
    function findgetprocess(data){
    	getprocess(data);
		$('#find_process').html(processjson);
    }
    //添加页面联动
	function addprocess(date){
		var id=$("#window_add #"+date).val();
		processjson = '';
		if(id==0){
			var str='<option value="0">-</option>';
			processjson = '<option value="0">-</option>';
			$('#window_add #hour').html(str);
			$('#window_add #process').html(processjson);
			return;
		}
		var url = "${pageContext.request.contextPath }/dailyWork/Worklog/findprocess/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
			},
			addgetprocess,
			"text"
		);
    }
    function addgetprocess(data){
    	getprocess(data);
    	addhour();
		$('#window_add #process').html(processjson);
    }
    function addhour(){
    	var id=$("#window_add #courseid").val();
    	var url = "${pageContext.request.contextPath }/dailyWork/Worklog/findhour/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
				courseid : $("#window_add #courseid").val(),
				empid : $("#window_add #id").val(),
				classid : $("#window_add #classid").val(),
			},
			addgethour,
			"text"
		);
    }
    function addgethour(data){
    	var str='<option value="0">-</option>';
    	str+='<option value="'+data+'">第'+data+'课时</option>';
		$('#window_add #hour').html(str);
    }
    //修改页面联动
    function updateprocess(date){
		var id=$("#window_update #"+date).val();
		processjson = '';
		if(id==0){
			var str='<option value="0">-</option>';
			processjson = '<option value="0">-</option>';
			$('#window_update #hour').html(str);
			$('#window_update #process').html(processjson);
			return;
		}
		var url = "${pageContext.request.contextPath }/dailyWork/Worklog/findprocess/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
			},
			updategetprocess,
			"text"
		);
    }
    function updategetprocess(data){
    	getprocess(data);
    	updatehour();
		$('#window_update #process').html(processjson);
    }
    function updatehour(){
    	var id=$("#window_update #courseid").val();
    	if(rows.hour!=0){
    		updategethour(rows.hour);
    		return;
    	}
    	var url = "${pageContext.request.contextPath }/dailyWork/Worklog/findhour/"+id;
		$.post(
			url,
			{
				_method:'PUT',//改成PUT提交
				courseid : $("#window_update #courseid").val(),
				empid : $("#window_update #empid").val(),
				classid : $("#window_update #classid").val(),
			},
			updategethour,
			"text"
		);
    }
    function updategethour(data){
    	var str='';
    	str+='<option value="0">-</option>';
    	str+='<option value="'+data+'">第'+data+'课时</option>';
		$('#window_update #hour').html(str);
		$("#window_update #hour").val(rows.hour);
    }
    function isHavaPlan(){
    	getCourse();
    }
    function isHavaOutLine(){
    	getOutLine();
    }
    function isHavaHour(){
    	getHourFromOutLine();
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
                                <label class="col-sm-1 control-label">员工：</label>
                                <div class="col-sm-2">
	                                <select id="find_empid" class="form-control">
	                                	<option value=" ">----------</option>
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                                <label class="col-sm-1 control-label">班级：</label>
                                <div class="col-sm-2">
                                	<select id="find_classid" required class="form-control">
	                            		<option value="-1">不限</option>
	                                    <c:forEach items="${classes}" var="cl">
	                                    	<option value="${cl.id}">${cl.classname}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
								<label class="col-sm-1 control-label">课程：</label>
	                            <div class="col-sm-2">
	                            	<select id="find_courseid" required class="form-control" onchange="findprocess(this);">
	                            		<option value="-1">不限</option>
	                                    <c:forEach items="${course}" var="course">
	                                    	<option value="${course.id}">${course.courseName}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                                <label class="col-sm-1 control-label">大纲：</label>
                                <div class="col-sm-2">
                                	<select id="find_process" required class="form-control">
	                            		<option value="-1">不限</option>
	                            		<option value="0">-</option>
	                                    <c:forEach items="${outline}" var="outline">
	                                    	<option value="${outline.id}">${outline.outlineName}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- <label class="col-sm-1 control-label">工作日期:</label>
                                <div class="col-sm-3">
									<input placeholder="工作日期" class="form-control layer-date"
										id="find_workday">
								</div> -->
                                <label class="col-sm-1 control-label">工作内容:</label>
	                            <div class="col-sm-3">
                                    <input id="find_contents" type="text" class="form-control">
                                </div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">查询</button>
								</div>
                            </div>
						</form>
					</div>
				</div>
				<div id="toolbar" class="btn-group">
					<!-- <button id="btn_add" type="button" class="btn btn-w-m btn-primary"
						data-toggle="modal" data-target="#window_add">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
					</button> -->
					<!-- <button id="btn_edit" type="button" class="btn btn-w-m btn-success"
						data-toggle="modal" >
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
					</button> -->
					<button id="btn_delete" type="button"
						class="btn btn-w-m btn-danger">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>
				</div>

				<div class="modal inmodal" id="window_update" tabindex="-1"
					role="dialog" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content animated bounceInRight">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
								</button>
								<h4 class="modal-title">修改员工工作日志</h4>
							</div>
							<form class="form-horizontal m-t" id="updateForm" novalidate="novalidate" name="updateForm">
								<input id="id" type="hidden">
								<div class="form-group">
									<label class="col-sm-3 control-label">员工：</label>
	                                <div class="col-sm-8">
		                                <select id="empid" name="empid" required class="form-control" disabled="disabled">
		                                    <c:forEach items="${emp}" var="emp">
		                                    	<option value="${emp.id}">${emp.empname}</option>
		                                    </c:forEach>
		                                </select>
		                            </div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">*班级：</label>
		                            <div class="col-sm-8">
		                            	<select id="classid" name="classid" required class="form-control" disabled="disabled">
		                                    <c:forEach items="${educlass}" var="cl">
		                                    	<option value="${cl.id}">${cl.classname}</option>
		                                    </c:forEach>
	                                	</select>
		                            </div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">课程：</label>
		                            <div class="col-sm-8">
		                            	<select id="courseid" name="courseid_update" class="form-control" onchange="updateprocess(this.id);">
		                            		<option value="0">-</option>
		                                    <c:forEach items="${educourse}" var="course">
		                                    	<option value="${course.id}">${course.name}</option>
		                                    </c:forEach>
		                                </select>
		                            </div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">大纲：</label>
	                                <div class="col-sm-8">
	                                	<select id="process" name="process" class="form-control">
		                            		<option value="0">-</option>
		                                    <c:forEach items="${outline}" var="outline">
		                                    	<option value="${outline.id}">${outline.outlineName}</option>
		                                    </c:forEach>
	                                	</select>
	                                </div>
								</div>
								<div class="form-group">
	                                <label class="col-sm-3 control-label">课程课时：</label>
	                                <div class="col-sm-8">
										<select id="hour" name="hour" class="form-control">
		                            		<option value="0">-</option>
	                                	</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">*工作内容:</label>
		                            <div class="col-sm-8">
	                                    <input id="contents" name="contents" required type="text" class="form-control">
	                                </div>
                                </div>
								<div class="modal-footer">
									<button type="button" class="btn btn-white"
										data-dismiss="modal">关闭</button>
									<button type="button" onclick="updateStudent()"
										class="btn btn-primary">保存</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

			<div class="modal inmodal" id="window_add" tabindex="-1"
				role="dialog" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content animated bounceInRight">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">添加日志</h4>
						</div>
						<div class="ibox-content">
							<form class="form-horizontal m-t" id="addForm"
								novalidate="novalidate">
								<div class="form-group">
									<!-- <label class="col-sm-2 control-label">*工作内容:</label> -->
		                            <div class="col-sm-10 col-sm-offset-1">
	                                    <!-- <input id="contents" name="contents" type="text" required class="form-control"> -->
	                                    <textarea name="content" data-provide="markdown" rows="3" id="contents" name="contents" placeholder="工作日报内容..."></textarea>
	                                </div>
                                </div>
								<div class="form-group">
									<label class="col-sm-3 control-label">员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工：</label>
	                                <div class="col-sm-8">
	                                	<input id="id" type="hidden" value="${userid}">
		                                <select id="empid" name="empid" required class="form-control" disabled="disabled">
		                                    <c:forEach items="${emp}" var="emp">
		                                    	<option value="${emp.id}">${emp.empname}</option>
		                                    </c:forEach>
		                                </select>
		                            </div>
								</div>
								<div class="form-group" style="display: block;" id="thisclass">
									<label class="col-sm-3 control-label">班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
		                            <div class="col-sm-8">
		                            	<select id="classid" name="classid" required class="form-control" onchange="isHavaPlan();">
		                                    <c:forEach items="${educlass}" var="cl">
		                                    	<option value="${cl.id}">${cl.classname}</option>
		                                    </c:forEach>
	                                	</select>
		                            </div>
								</div>
								<div class="form-group" style="display: block;" id="thiscourse">
									<label class="col-sm-3 control-label">课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：</label>
		                            <div class="col-sm-8">
		                            	<select id="courseid" name="courseid" required class="form-control" onchange="isHavaOutLine();">
		                                    <%-- <c:forEach items="${educourse}" var="c">
		                                    	<option value="${c.id}">${c.name}</option>
		                                    </c:forEach> --%>
		                                </select>
		                            </div>
								</div>
								<div class="form-group" style="display: block;" id="thisoutline">
									<label class="col-sm-3 control-label">大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纲：</label>
	                                <div class="col-sm-8">
	                                	<select id="process" name="process" class="form-control" onchange="isHavaHour();">
		                            		<!-- <option value="0">-</option> -->
	                                	</select>
	                                </div>
								</div>
								<div class="form-group" style="display: block;" id="thishour">
	                                <label class="col-sm-3 control-label">课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时：</label>
	                                <div class="col-sm-8">
										<select id="hour" name="hour" class="form-control">
		                            		<!-- <option value="0">-</option> -->
	                                	</select>
									</div>
								</div>
	                            <div class="modal-footer">
	                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	                                 <button type="button" onclick="addStudent()" class="btn btn-primary">保存</button>
	                            </div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments"></table>
	</div>
		<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
		<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
		<!-- simditor -->
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/markdown/markdown.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/markdown/to-markdown.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/markdown/bootstrap-markdown.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/markdown/bootstrap-markdown.zh.js"></script>
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
		            	empid :{
	                    	required:true
	                    },
	                    workday:{
	                    	required:true
	                    },
	                    contents :{
	                    	required:true
	                    },
	                    classid :{
	                    	required:true
	                    },
	                    courseid :{
	                    	required:true
	                    }
		            	
		            },
		            messages: {
		            	empid :{
	                    	required:icon + "请选择员工"
	                    },
	                    workday:{
	                    	required:icon + "请选择工作日期"
	                    },
	                    contents :{
	                    	required:icon + "请填写工作内容"
	                    },
	                    classid :{
	                    	required:icon + "请选择班级"
	                    },
	                    courseid :{
	                    	required:icon + "请选择课程"
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
		<script>
		//日期范围限制
		var start = {
			elem : '#find_workday',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : laydate.now(), //最大日期
			/* istime : true, */
			istoday : true,
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
			elem : '#workday',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : laydate.now(), //最大日期
			istime : true,
			istoday : true,
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
			elem : '#workday_add',
			format : 'YYYY/MM/DD',
			min : laydate.now(), //设定最小日期为当前日期
			max : laydate.now(), //最大日期
			/* istime : true, */
			istoday : true,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		laydate(start);
	</script>
</body>
</html>