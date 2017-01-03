<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>BootStrap Table使用</title>

<jsp:include page="../styleInclude.jsp"></jsp:include>
    
    <script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var rows = null;
        var rowList = new Array();
	    $(function () {
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//查询按钮
			$('#btn_query').click(function () {
				alert("查询");
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
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : oTableInit.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 5, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					searchOnEnterKey :true, //按回车搜索
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					columns : [ {
						checkbox : true,
					}, {
						field : 'stuno',
						title : '编号'
					}, {
						field : 'stuname',
						title : '姓名'
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
						field : 'huorid',
						title : '宿舍'
					}, ]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					classid : $("#selClassid").val(),
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
		
		function select(){
			//这里是所有的选中行数据，和后台传进来的一样，怎么取看下面
			var rowList = $('#tb_student').bootstrapTable('getSelections');
			for(var i=0;i<rowList.length;i++){
				//从这里取取到数据
				alert(rowList[i].id+","+rowList[i].stuno+","+rowList[i].stuname);
			}
			return $('#tb_student').bootstrapTable('getSelections');
		}
		function selClass(classid){ 
			//刷新表格
			$('#tb_student').bootstrapTable('refresh');
		}
	</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>选择学生</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar">
							<select class="form-control" name="classid" id="selClassid" onchange="selClass(this[selectedIndex].value)">
								<option value="">选择班级</option>
								<c:forEach var="cls" items="${clsList}">
	                                <option value="${cls.id}">${cls.classname}</option>
								</c:forEach>
                            </select>     
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_student"></table>
						&nbsp;<button class="btn btn-primary pull-right" onclick="select()" type="button"><i class="fa fa-check"></i>&nbsp;提&nbsp;交&nbsp;</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>