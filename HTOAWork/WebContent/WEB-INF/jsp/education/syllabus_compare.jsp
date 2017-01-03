<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起反馈</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script>
	function seleEmp(){
		var empid = $("#empid").val();
		if(empid==""){
			return;
		}
		var url = "${pageContext.request.contextPath }/education/syllabuscompare/getClassByEmpId";
		//var url2 = "${pageContext.request.contextPath }/education/syllabuscompare/getCourseByEmpId";
		$.post(
			url,{
				_method : 'PUT',
				id : empid
			},
			resetSele,
			"text"
		);
		/* $.post(
				url2,{
					_method : 'PUT',
					id : empid
				},
				resetSele2,
				"text"
		); */
	}
	function resetSele(data){
		var datas = JSON.parse(data);
		if (datas.rows.length > 0) {
			var str = "<option value=>----不选择----</option>";
			for (var i = 0; i<datas.rows.length; i++) {
				str += '<option value="'+datas.rows[i].id+'">'
						+ datas.rows[i].classname + '</option>';
			}
			$('#classid').html(str);
		}else{
			$('#classid').html("<option value=>暂无班级信息</option>");
		}
	}
	function resetSele2(data){
		var datas = JSON.parse(data);
		if (datas.rows.length> 0) {
			var str = "<option value=>----不选择----</option>";
			for (var i = 0; i<datas.rows.length; i++) {
				str += '<option value="'+datas.rows[i].course.id+'">'
						+ datas.rows[i].course.courseName + '</option>';
			}
			$('#courseid').html(str);
		}else{
			$('#courseid').html("<option value=>暂无课程信息</option>");
		}
	}
</script>
<script type="text/javascript">
	//全局变量，读取当前行的数据
	var rows = null;
	$(function() {
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();

		//条件查询click事件注册
		$("#query").click(function() {
			/* var classid = $("#classid").val();
			var empid = $("#empid").val();
			var courseid = $("#courseid").val();
			if(classid=="" || empid=="" || courseid==""){
				swal("操作警告", "当前条件无可查询的课程进度！", "warning");
				return;
			} */
			$("#progressTab").bootstrapTable('refresh');
		});
		$("#clear").click(function() {
			startEmpid : $('#formSearch #courseid').val('');
			empId : $('#formSearch #empid').val('');
			classId : $('#formSearch #classid').val('');
			$("#progressTab").bootstrapTable('refresh');
		});
	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#progressTab').bootstrapTable({
				url : '${pageContext.request.contextPath}/education/syllabuscompare/getSyllabus', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
				//showExport: true, //是否显示导出
				//exportDataType: "selected", //basic', 'all', 'selected'.好像默认basic不写也罢
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : oTableInit.queryParams,//传递参数（*）
				queryParamsType: "limit",
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, //初始化加载第一页，默认第一页
				pageSize : 10, //每页的记录行数（*）
				pageList : [ 10, 25, 50, 100, 'ALL'], //可供选择的每页的行数（*
				search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
				strictSearch : false,
				showColumns : true, //是否显示所有的列
				showRefresh : true, //是否显示刷新按钮
				minimumCountColumns : 2, //最少允许的列数
				clickToSelect : true, //是否启用点击选中行
				height : 400, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
				singleSelect : true, //设置为单选
				onClickRow : function(row, $element) {//selected
					//$element是当前tr的jquery对象
					if (rows != null) {
						rows = null;
					}
					rows = row;
				},//单击row事件
				onUncheck: function(row) {
		             if(rows != null){
		             	rows = null;
		             }
		         },
				columns : [{
					align:"center",
					field : 'empTeacher',
					title : '教员',
					formatter : function(value, row, index) {
						var empTeacher = row.empTeacher;
						if(empTeacher==null){
							return "-";
						}else{
							return empTeacher.empname;
						}
					} 
				}, {
					align:"center",
					field : 'classname',
					title : '班级',
					formatter : function(value, row, index) {
						var stuClass = row.stuClass;
						if(stuClass==null){
							return "-";
						}else{
							return stuClass.classname;
						}
					}
				} , {
					align:"center",
					field : 'course',
					title : '课程',
					formatter : function(value, row, index) {
						var course = row.course;
						if(course==null){
							return "-";
						}else{
							return course.courseName;
						}
					} 
				},{
					align :"center",
					field : "finish",
					title : "实际进度(蓝：已完成/红：未完成)",
					formatter : function(value, row, index){
						var allhour = row.allhour;
						var finishhour = row.finishhour;
						var s="";
						var h="";
						var e="";
						if(allhour==null || finishhour==null){
							return "-";
						}else{
							e = (finishhour/allhour)*100+"";
							if(finishhour/allhour<1){
								s = e.substr(0,2);//已完成
								h = 100-(e.substr(0,3));
							}else{
								s=100;
								h=0;
							}
							//未完成
							var f = '<div class="progress progress-striped active"><div style="width: '+s+'%" class="progress-bar progress-bar-success"><span id="finished">'+s+'%</span></div><div style="width: '+h+'%" class="progress-bar progress-bar-danger"><span id="unfinish">'+h+'%</span></div></div>';
							return f;
						}
					}
				},{
					align :"center",
					field : "plan",
					title : "计划进度(蓝色：应完成/黄：未完成)",
					formatter : function(value, row, index){
						var plan = row.plan;
						var today = row.today;
						var s="";
						var h="";
						var e="";
						if(plan==null || today==null){
							return "-";
						}else{
							e = (today/plan)*100+"";
							if(today<=0){
								s=0;
								h=100;
								var f = '<div class="progress progress-striped active"><div style="width: '+s+'%" class="progress-bar progress-bar-info"><span id="finished">'+s+'%</span></div><div style="width: '+h+'%" class="progress-bar progress-bar-warning"><span id="unfinish">'+h+'%</span></div></div>';
								return f;
							}else if(today/plan<1){
								//s = e.substr(0,3);//已完成
								//h = 100-(e.substr(0,3));
								var arr = e.split(".");
								s = arr[0];
								h=100-s;
								var f = '<div class="progress progress-striped active"><div style="width: '+s+'%" class="progress-bar progress-bar-info"><span id="finished">'+s+'%</span></div><div style="width: '+h+'%" class="progress-bar progress-bar-warning"><span id="unfinish">'+h+'%</span></div></div>';
								return f;
							}else if(today/plan>=1){
								s=100;
								h=0;
								var f = '<div class="progress progress-striped active"><div style="width: '+s+'%" class="progress-bar progress-bar-info"><span id="finished">'+s+'%</span></div><div style="width: '+h+'%" class="progress-bar progress-bar-warning"><span id="unfinish">'+h+'%</span></div></div>';
								return f;
							}
							//未完成
							//var f = '<div class="progress progress-striped active"><div style="width: '+s+'%" class="progress-bar progress-bar-info"><span id="finished">'+s+'%</span></div><div style="width: '+h+'%" class="progress-bar progress-bar-warning"><span id="unfinish">'+h+'%</span></div></div>';
							//return f;
						}
					}
				}]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				empId : $('#formSearch #empid').val() ,
				classId : $('#formSearch #classid').val(),
				courseId : $('#formSearch #courseid').val()
			};
			return temp;
		};
		return oTableInit;
	}

	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
		};
		return oInit;
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
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">教员：</label>
								<div class="col-sm-2">
									<!-- <input class="form-control" type="text" id="empid"> -->
									<select class="form-control" name="empid" id="empid"
										onchange="seleEmp();">
										<option value=>----不选择----</option>
										<c:forEach items="${allEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
										<c:forEach items="${oneEmp }" var="e">
											<option value="${e.id }">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">班级：</label>
								<div class="col-sm-2">
									<!-- <input class="form-control" type="text" id="classid"> -->
									<select class="form-control" name="classid" id="classid">
										<option value=>----不选择----</option>
										<c:forEach items="${allClass }" var="c">
											<option value="${c.id }">${c.classname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">课程：</label>
								<div class="col-sm-2">
									<select class="form-control" name="courseid" id="courseid">
										<option value=>----不选择----</option>
										<c:forEach items="${allCourse }" var="e">
											<option value="${e.id }">${e.courseName}</option>
										</c:forEach>
										<c:forEach items="${teacherCourse }" var="e">
											<option value="${e.course.id}">${e.course.courseName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询
									</button>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-success" id="clear">
										<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>课程进度</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<table id="progressTab"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</body>
</html>