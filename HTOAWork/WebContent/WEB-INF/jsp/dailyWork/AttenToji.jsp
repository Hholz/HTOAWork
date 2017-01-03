<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起反馈</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
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

		//新增按钮事件*************************
		//修改按钮事件
		//$("#btn_edit").click(
				
				
				//);
		//条件查询click事件注册
		$("#query").click(function() {
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		//清空条件
		$("#clean").click(function() {
			$("#start").val("");
			$("#empname").val("");
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		
		
		
	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#feedbackstart_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/AttenTojiList', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
				striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : oTableInit.queryParams,//传递参数（*）
				//queryParamsType: "limit",
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
					visible : false
				}, {
					field : 'finger',
					title : '员工编号',
					align : 'center'
				}, {
					field : 'empname',
					title : '员工姓名',
					align : 'center'
					
				}, {
					field : 'depid',
					title : '部门',
					align : 'center',
					formatter : function(value, row, index) {
						var dep = row.dep;
						if(dep==null){
							return "-";
						}else {
							return dep.depname;
						}
					}
				}, {
					field : 'workday',
					title : '时期',
					align : 'center',
					formatter : function(value, row, index) {
						var time = row.workday;
						if(time==null){
							return  "-";
						}else {
							return  time.substring(0,7);
						}
					}
				}, {
					field : 'late',
					title : '迟到次数',
					align : 'center'
				}, {
					field : 'absent',
					title : '旷工次数',
					align : 'center',
				}, {
					field : 'leave',
					title : '请假次数',
					align : 'center',
				}, {
					field : 'fclockin',
					title : '忘打卡次数',
					align : 'center',
				}, {
					field : '',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var finger = row.finger;
						return "<a href='${pageContext.request.contextPath}/dailyWork/AttenShenhe?finger="+finger+"'><i  class='glyphicon glyphicon-send'>审核</i></a>";
					}
				} ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				empname :$('#formSearch #empname').val(),
				workday : $('#formSearch #start').val()
			};
			return temp;
		};
		return oTableInit;
	}

	function queryParams2(params) {
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
		};
		return temp;
	};
	
	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	}
	
	function btnaddpd(){
    	var si =  $("#panduan #sizes").val();
		if(si==1){
			$("#window_loading").modal();
    	}else {
    		btnadd();
    	}
    	
    }
    
	function del_add(){
    	
    	var url = "${pageContext.request.contextPath }/dailyWork/deletelist";
    	$("#ts").text("生成中，请稍等");
    	seljdt2();
    	$("#gbqr").css("display", "none");
    	$.post(
			url,
			{
				workday : $("#panduan #ati").val()
			},
			function(data){
				if(data>=1){
					$("#feedbackstart_Tab").bootstrapTable('refresh');
					//$("#ts").text("数据已成功生成！");
				}else{
					swal("添加失败", "请检查你的数据..！", "error");
				}
			},
			"text"
		);
    	$("#feedbackstart_Tab").bootstrapTable('refresh');
    }
    
	function btnadd(){
    	$("#window_addloading").modal();
    	seljdt();
    	var url = "${pageContext.request.contextPath }/dailyWork/addAttenstatis";//addAttenstatis-AttenstatisList/add
		$.post(
			url,
			{
				status : 1
			},
			function(data){
				if(data>=1){
					$("#feedbackstart_Tab").bootstrapTable('refresh');
				}else{
					swal("添加失败", "数据已生成，请不要重复添加", "error");
				}
			},
			"text"
		);	
		
    }
	
	//部门与员工联动
	function selectdep(){
		var url = "${pageContext.request.contextPath }/dailyWork/attenfindemp";
		$.post(url, {
			id : $("#depid").val(),
		}, 
		initemp, "text");
	}
	function initemp(data) {
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].empname+'" hassubinfo="true">'
						+ datas.rows[i].empname + '</option>';
			}
			$('#empname').html(opt);
		}
		
	}
	
	var j =5;
	
	function run2(){
		
		j +=10;//进度条速度
		if(j>=80){
			$("#ts").text("数据即将生成！");
		}
		if(j>=100){
			$("#ts").text("数据已生成！");
		}
		$("#studrewardForm #jdt2").css("width", j + "%"); 
	}
	
	function seljdt2(){
		run2();
		if(j >= 100){
			$('#window_loading ').modal('hide');
			return ;
		}
		setTimeout("seljdt2()", 1000);
	}

	/* function addsel(){
		var url = "${pageContext.request.contextPath }/dailyWork/addjdt";
		$.post(url, {
			status : 1,
		}, function(data){
			var das = data;
			var jd = das+'%';
			$("#jdbfb").html(jd);
			$('#panduan #dtjdt').val(das);
		}, "text");
	} */
	var p =5;
	
	function run(){
		var jd = p +'%';
		$("#jdbfb").html(jd);
		p +=8;//璋冩暣杩涘害閫熷害
		if(p>=90){
			$("#jdts").text("数据即将生成！");
		}
		$("#studrewardForm2 #jdt").css("width", p + "%"); 
	}
	
	function seljdt(){
		run();
		if(p >= 100){
			$('#window_addloading ').modal('hide');
			return ;
		}
		setTimeout("seljdt()", 1000);
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
								<label class="control-label col-sm-1" style="margin-left: 30px">部门：</label>
								<div class="col-sm-2">
									<select class="chosen-select" style="width:120px;height:33px;" tabindex="2" name="depid" id="depid" onchange="selectdep();" >
										<option value=""> -请选择---</option >
										<c:forEach items="${deplist}" var="dep">
											<option value="${dep.id}">${dep.depname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="control-label col-sm-1">员工：</label>
								<div class="col-sm-2">
									<select class="chosen-select" style="width:120px;height:33px;" tabindex="2" name="empname" id="empname">
										<option value=""> -请选择---</option >
		                              	
									</select>
								</div>
								<label class="control-label col-sm-1">时期：</label>
								<div class="col-sm-2">
									<input placeholder="当前月份"  class="form-control layer-date"
										id="start" name="start"/>
								</div>
								<div class="col-sm-2">
									<button type="button" class="btn btn-primary" id="query">查询</button>
									<button type="button" id="clean" class="btn btn-primary">清空</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary"
				data-toggle="modal" onclick="btnaddpd()"><!-- onclick="addStart()" -->
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>生成上月考勤统计
			</button>
			
			<div id="addts"></div>
		</div>

		<div id="panduan">
			<div>
				<input type="hidden" name="sizes" id="sizes" value="${sizes}"/>
				<input type="hidden" name="ati" id="ati" value="${ati}"/>
				<input type="hidden" name="dtjdt" id="dtjdt" value=""/>
			</div>
		</div>
	
		<div class="modal inmodal" id="window_loading" tabindex="-1" role="dialog"
		   	aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="studrewardForm" novalidate="novalidate" enctype="multipart/form-data">
						<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<div class="modal-header">
							<h4 class="modal-title" id="ts"><font color="#4876FF">该表已有全部人员上月考勤统计,确定覆盖？</font></h4>
						</div>
						
							<div class="progress progress-striped active">
								<div id="jdt2" class="progress-bar progress-bar-info" role="progressbar"
									 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
								 	 style="width: 0.5%;">
									<span class="sr-only"></span>
								</div>
							</div>
							
                        	<div class="form-group" id="gbqr">
                        		<div class="col-sm-5" style="margin-left: 200px">
                        			<button type="button" class="btn btn-success" data-dismiss="modal">关闭</button>
									<button type="button" onclick="del_add()"
									class="btn btn-primary">确定</button>
                        		</div>
                        	</div>
                        	
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal inmodal" id="window_addloading" tabindex="-1" role="dialog"
			aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="studrewardForm2" novalidate="novalidate" enctype="multipart/form-data">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
							</button>
							<h3 class="modal-title" id="jdts"><font color="#4876FF">正在生成统计表数据，请耐心等待。</font></h3>
						</div>
							
						<div class="progress progress-striped active">
							<div id="jdt" class="progress-bar progress-bar-info" role="progressbar"
								 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
							 	 style="width: 0%;">
								<span class="sr-only"></span>
							</div>
						</div>
						
						<div class="form-group" >
	                        <label class="control-label col-sm-2">进程：</label>
	                        <div class="control-label col-sm-2">
	                        <div id="jdbfb" style="color: #00f"></div>
	                        </div>
                        </div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
	
		<!-- table代码就这些，用js构建表格 -->
		<table id="feedbackstart_Tab"></table>
		 
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#start',
			format : 'YYYY/MM',
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
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script type="text/javascript">
	    
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata){
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	holidaystat:{
                    	required:true
                    }
	            },
	            messages: {
	            	holidaystat:{
		                	required:icon + "请选择好状态"
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