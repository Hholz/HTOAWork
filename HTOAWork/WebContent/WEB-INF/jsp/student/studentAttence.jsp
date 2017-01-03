<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生考勤</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<script type="text/javascript">
	$(function() {
		$('#tab_attence').bootstrapTable({
			url : '${pageContext.request.contextPath}/student/attence/attencelistJson', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType : "application/x-www-form-urlencoded",
			toolbar : '#toolbar', //工具按钮用哪个容器
			striped : true, //是否显示行间隔色
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			sortable : false, //是否启用排序
			sortOrder : "asc", //排序方式
			queryParams : queryParams,//传递参数（*）
			//queryParamsType: "limit",
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 30, //每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
			search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
			showColumns : true, //是否显示所有的列
			showRefresh : true, //是否显示刷新按钮
			minimumCountColumns : 2, //最少允许的列数
			//clickToSelect : true, //是否启用点击选中行
			//height : 1500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			showToggle : true, //是否显示详细视图和列表视图的切换按钮
			cardView : false, //是否显示详细视图
			detailView : false, //是否显示父子表
			singleSelect : true, //设置为单选
			onCheck: function(row) {
                 //$element是当前tr的jquery对象
                if(rows != null){
                	
                }
            },
            onUncheck: function(row) {
                if(rows != null){
                	
                }
            },
			columns : [ {
				checkbox : true
			}, {
				field : 'id',
				title : 'ID',
				visible : false
			}, {
				field : 'stuId',
				title : '学生姓名',
				formatter : function(value, row, index) {
					var stu = row.stu;
					if(stu==null){
						return "-";
					}else{
						return stu.stuname;
					}
				}
			}, {
				field : 'clsName',
				title : '班级名称',
				formatter : function(value, row, index) {
					var cls = row.cls;
					if(cls==null){
						return "-";
					}else{
						return cls.classname;
					}
				}
				
			}, {
				field : 'morning',
				title : '早操·状态',
				formatter : function(value, row, index) {
					return colour(row.id,value,'morning');
				}
			}, {
				field : 'forenoon',
				title : '上午·状态',
				formatter : function(value, row, index) {
					return colour(row.id,value,'forenoon');
				}
			}, {
				field : 'afternoon',
				title : '下午·状态',
				formatter : function(value, row, index) {
					return colour(row.id,value,'afternoon');
				}
			}, {
				field : 'night',
				title : '晚上·状态',
				formatter : function(value, row, index) {
					return colour(row.id,value,'night');
				}
			}, {
				field : 'createTime',
				title : '日期时间'
			}, ]
		});
	});
	function queryParams(params) {
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			clsId : $('#findDiv #cls_id').val(),
			createTime : $('#findDiv #createTime2').val(),
		};
		return temp;
	}
	//上色
	var colour = function(id,value,time){
		var text = "";
		if(value=='正常'){
			text += "<span class='label label-primary' id='"+time+"normal"+id+"' onclick='normal("+id+", &#39;"+time+"&#39;)'>正常</span>";
		}else{
			text += "<span class='label' id='"+time+"normal"+id+"' onclick='normal("+id+",&#39;"+time+"&#39;)'>正常</span>";
		}
		if(value=='迟到'){
			text += "<span class='label label-warning' id='"+time+"late"+id+"' onclick='late("+id+",&#39;"+time+"&#39;)'>迟到</span>";
		}else{
			text += "<span class='label' id='"+time+"late"+id+"' onclick='late("+id+",&#39;"+time+"&#39;)'>迟到</span>";
		}
		
		if(value=='旷课'){
			text += "<span class='label label-danger' id='"+time+"truant"+id+"' onclick='truant("+id+",&#39;"+time+"&#39;)'>旷课</span>";
		}else{
			text += "<span class='label' id='"+time+"truant"+id+"' onclick='truant("+id+",&#39;"+time+"&#39;)'>旷课</span>";
		}
		
		if(value=='请假'){
			text += "<span class='label label-success' id='"+time+"leave"+id+"' onclick='leave("+id+",&#39;"+time+"&#39;)'>请假</span>";
		}else{
			text += "<span class='label' id='"+time+"leave"+id+"' onclick='leave("+id+",&#39;"+time+"&#39;)'>请假</span>";
		}
		return text;
	}
	//正常
	function normal(id,time){
		upOnetimeStatus(id,time,"正常");
		$('#'+time+'normal'+id).attr("class","label label-primary");
		$('#'+time+'late'+id).attr("class","label");
		$('#'+time+'truant'+id).attr("class","label");
		$('#'+time+'leave'+id).attr("class","label");
	}
	//迟到
	function late(id,time){
		upOnetimeStatus(id,time,"迟到");
		$('#'+time+'normal'+id).attr("class","label");
		$('#'+time+'late'+id).attr("class","label label-warning");
		$('#'+time+'truant'+id).attr("class","label");
		$('#'+time+'leave'+id).attr("class","label");
	}
	//旷课
	function truant(id,time){
		upOnetimeStatus(id,time,"旷课");
		$('#'+time+'normal'+id).attr("class","label");
		$('#'+time+'late'+id).attr("class","label");
		$('#'+time+'truant'+id).attr("class","label label-danger");
		$('#'+time+'leave'+id).attr("class","label");
	}
	//请假
	function leave(id,time){
		upOnetimeStatus(id,time,"请假");
		$('#'+time+'normal'+id).attr("class","label");
		$('#'+time+'late'+id).attr("class","label");
		$('#'+time+'truant'+id).attr("class","label");
		$('#'+time+'leave'+id).attr("class","label label-success");
	}
	
	//做修改
	function upOnetimeStatus(id,time,state){
		var url = "${pageContext.request.contextPath }/student/attence/upOnetimeStatus";
 		$.post(
 			url,
 			{
 				id:id,
 				time:time,
 				state:state
 			},
 			function(data){
 				if(data>0){
 					toastr.success('修改考勤', '修改成功');
 				}else{
 					toastr.error('修改考勤', '修改失败');
 				}
 			},
 			"text"
 		);	
	}
	//生成某日一个班的考勤
	function attenCreate(){
		var url = "${pageContext.request.contextPath }/student/attence/createTodayAttence";
 		$.post(
 			url,
 			{
 				clsId:$('#window_create #cls_id').val(),
 				createTime:$('#window_create #createTime').val(),
 			},
 			function(data){
 				if(data>0){
 					$('#tab_attence').bootstrapTable('refresh');	
 					parent.layer.alert('生成考勤成功！');
 				}else if(data==-1){
 					parent.layer.alert('该班该日考勤已存在');
 				}else{
 					parent.layer.alert('生成考勤失败！');
 				}
 			},
 			"text"
 		);
 		$("#window_create").modal('hide');
	}
	//通过条件刷新表格
	function refreshTable(){
		$('#tab_attence').bootstrapTable('refresh');	
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
						<div class="form-inline" id="findDiv">
							<label for="cls_id">班级名称</label>
			       			<select name="cls_id" id="cls_id"  class="form-control" onchange="refreshTable()">
			      				<c:forEach items="${clsList}" var="cls">
					           		<option value="${cls.id}">${cls.classname}</option>
					           	</c:forEach>
			       			</select>
			       			<label for="createTime2">考勤时间：</label>
					   		<input placeholder="默认今日" class="form-control  layer-date" id="createTime2" name="createTime2">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>学生考勤信息</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="form-inline">
							<button id="btn_add" type="button" class="btn btn-w-m btn-primary"
								data-toggle="modal" data-target="#window_create">
								生成考勤
							</button>
							<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
		       			</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tab_attence"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
<div class="modal fade" id="window_create" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="myModalLabel">生成今日考勤</h4>
        </div>
        <div class="modal-body">
        <!-- 新增系别 -->
         <input type="hidden" id="id" name="id">
            <form class="form-inline">
          	<div class="form-group">
          		<label for="cls_id">班级名称</label>
       			<select name="cls_id" id="cls_id"  class="form-control">
      				<c:forEach items="${clsList}" var="cls">
		           		<option value="${cls.id}">${cls.classname}</option>
		           	</c:forEach>
       			</select>
       			<label for="createTime">考勤时间：</label>
		   		<input placeholder="默认今日" class="form-control  layer-date" id="createTime" name="createTime">
          	</div>
          	<div class="modal-footer">
		         <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
          		 <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="attenCreate()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>生成</button>
	        </div>
	       </form>
		</div>
      </div>
    </div>
</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
    <!-- Toastr script -->
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script>
		//日期范围限制
		var start = {
			elem : '#createTime',
			format : 'YYYY-MM-DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : false,
			istoday : true,
			choose : function() {
				//选完日期的回调函数，刷新表格
			}
		};
		laydate(start);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#createTime2',
			format : 'YYYY-MM-DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : false,
			istoday : true,
			choose : function() {
				//选完日期的回调函数，刷新表格
				$('#tab_attence').bootstrapTable('refresh');
			}
		};
		laydate(start);
	</script>
</body>
</html>