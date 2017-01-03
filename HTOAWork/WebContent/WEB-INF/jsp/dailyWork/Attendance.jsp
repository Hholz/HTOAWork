<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>考勤记录数据表</title>
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
		$("#btn_edit").click(

		function() {

			if (rows == null || rows.id == null) {
				swal("警告！", "请选择你要修改的ID", "error");
				//把内容放到更新的列表
				$('#window_update').modal();
			}
			//把内容放到更新的列表
			$('#window_update #id ').val(rows.id);
			$('#window_update #fingerprint ').val(rows.fingerprint);
			$('#window_update #workdayupdate ').val(rows.workday);

			$('#window_update #remark ').val(rows.remark);
		});
		//条件查询click事件注册
		$("#query").click(function() {
			$("#kaoqin_Tab").bootstrapTable('refresh');
		});
		//清空条件
		$("#clean").click(function() {
			$("#fingerprint").val("");
			$("#workday").val("");
			$("#kaoqin_Tab").bootstrapTable('refresh');
		});
	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#kaoqin_Tab').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/kaoqinszs', //请求后台的URL（*）sreward/stureward、stuheart/sayheart
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
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
				singleSelect : true, //设置为单选
				onCheck : function(row) {
					//$element是当前tr的jquery对象
					if (rows != null) {
						rows = null;
					}
					rows = row;
				},
				onUncheck : function(row) {
					if (rows != null) {
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
					field : 'fingerprint',
					title : '员工指纹编号',
				}, {
					field : 'empname',
					title : '员工姓名'
				}, {
					field : 'dep.depname',
					title : '部门',
				}, {
					field : 'workday',
					title : '打卡时间',
				}, {
					field : 'status',
					title : '考勤状态',
					formatter : function(value, row, index) {
						var stat = row.status;
						if (stat == 0) {
							return "周末";
						} else if (stat == 1) {
							return "未打卡";
						} else if (stat == 2) {
							return "迟到";
						} else if (stat == 3) {
							return "无效";
						} else if (stat == 4) {
							return "签到";
						} else {
							return "-";
						}
					}
				}, {
					field : 'late',
					title : '迟到时间/分',
				}, {
					field : 'type',
					title : '打卡类型',
					formatter : function(value, row, index) {
						var type = row.type;
						var a = 'a';
						var p = 'p';
						if (type == a) {
							return "上班";
						} else if (type == p) {
							return "下班";
						} else {
							return "-";
						}
					}
				} ]
			});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				fingerprint : $('#fingerprint').val(),
				workday : $('#workday').val()
			};
			return temp;
		};
		return oTableInit;
	}

	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	}

	//修改学生，ajax提交
	function updateStart() {

		var id = $("#window_update #id").val();
		var url = "${pageContext.request.contextPath }/dailyWork/kaoqin/" + id;
		$.post(url, {

			_method : 'PUT',//改成PUT提交
			fingerprint : $('#window_update #fingerprint').val(),
			attentime : $('#window_update #attentime').val(),
			workday : $('#window_update #workdayupdate').val(),
			remark : $('#window_update #remark').val(),
		}, updateStartHandle, "text");
		$("#window_update").modal('hide')

	}
	function updateStartHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成修改操作",
				type : "success"
			});
		} else {
			swal("修改失败", "请检查你输入的数据！", "error");
		}
		//刷新表格
		$('#kaoqin_Tab').bootstrapTable('refresh');
		//把保存选中行的rows变量清零，很重要，防止缓存
		rows = null;
	}

	//考勤模板表数据生成
	function attenadd(){
		$("#spineradd").css("display","block");
		$("#ts").text("正在重新生成数据");
		var url ="${pageContext.request.contextPath}/dailyWork/deteleAttendances";
		$.post(url,{statsus:1},attenstat, "text");
	}
	function attenstat(data){
		if (data > 0) {
			$("#ts").text("已成功生成！");
			$("#spineradd").css("display","none");
		} else {
			$("#spineradd").css("display","none");
			swal("失败", "请检查你的网络！", "error");
			
		}
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
								<label class="col-sm-2 control-label">员工姓名：</label>
								<div class="col-sm-3">
									<select class="chosen-select"
										style="width: 180px; height: 30px;" tabindex="2"
										name="fingerprint" id="fingerprint">
										<option value="0">-请选择---</option>
										<c:forEach items="${emplist}" var="e">
											<option value="${e.fingerprint}">${e.empname}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label">时间：</label>
								<div class="col-sm-3">
									<input placeholder="选择日期" class="form-control layer-date"
										id="workday" name="workday">
								</div>
								<button type="button" class="btn btn-primary" id="query">查询</button>
								<button type="button" id="clean" class="btn btn-primary">清空</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_upload" data-toggle="modal"
				data-target="#window_upload" type="button"
				class="btn btn-w-m btn-success">
				<span class="fa fa-upload" aria-hidden="true"></span>&nbsp;&nbsp;导入
			</button>
			<button id="btn_mb" type="button" data-toggle="modal"
				class="btn btn-w-m btn-primary" data-target="#window_loading">
				重新生成模板数据</button>
		</div>

		<div class="modal inmodal" id="window_upload" tabindex="-1"
			data-keyboard="false" role="dialog" aria-hidden="true" role="dialog" 
			data-backdrop="static" data-show="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<h4 class="modal-title" id="statustitle">考勤打卡数据上传</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="studrewardForm"
							novalidate="novalidate" enctype="multipart/form-data">
							<div style="display: block" id="formTable">
								<div class="form-group">
									<label class="col-sm-3 control-label">请选择上传文件：</label>
									<div class="col-sm-8">
										<input type="file" id="txt_file" name="txt_file"
											class="file-loading" value="---文件上传---">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">导入信息提示：</label>
									<div class="col-sm-8">
										<div id="failText" style="color: #F00"></div>
										<div id="succeText" style="color: #00F"></div>
									</div>
								</div>
								<span style="color: #F00">请上传EXCLexcel 97-2003
									工作簿*.xls格式文件</span>
								<div class="modal-footer">
									<button type="button" class="btn btn-white"
										data-dismiss="modal">关闭</button>
									<button type="button" onclick="StudrewardImport();"
										class="btn btn-primary">提交</button>
								</div>
							</div>
							<div class="spiner-example" id="spiner" style="display: none">
								<div class="sk-spinner sk-spinner-fading-circle">
									<div class="sk-circle1 sk-circle"></div>
									<div class="sk-circle2 sk-circle"></div>
									<div class="sk-circle3 sk-circle"></div>
									<div class="sk-circle4 sk-circle"></div>
									<div class="sk-circle5 sk-circle"></div>
									<div class="sk-circle6 sk-circle"></div>
									<div class="sk-circle7 sk-circle"></div>
									<div class="sk-circle8 sk-circle"></div>
									<div class="sk-circle9 sk-circle"></div>
									<div class="sk-circle10 sk-circle"></div>
									<div class="sk-circle11 sk-circle"></div>
									<div class="sk-circle12 sk-circle"></div>
									<div class="sk-circle13 sk-circle"></div>
									<div class="sk-circle14 sk-circle"></div>
									<div class="sk-circle15 sk-circle"></div>
									<div class="sk-circle16 sk-circle"></div>
									<div class="sk-circle17 sk-circle"></div>
									<div class="sk-circle18 sk-circle"></div>
									<div class="sk-circle19 sk-circle"></div>
									<div class="sk-circle20 sk-circle"></div>
								</div>
								<div>
									<font size="9"></font>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	<div class="modal inmodal" id="window_loading" tabindex="-1"
			data-keyboard="false" role="dialog" aria-hidden="true" role="dialog" 
			data-backdrop="static" data-show="true">
	<div class="modal-dialog">
		<div class="modal-content animated bounceInRight">
			<div class="ibox-content">
				<form class="form-horizontal m-t" id="studrewardForm"
					novalidate="novalidate" enctype="multipart/form-data">
					<div class="modal-header">
						<h4 class="modal-title" id="ts">
							<font color="#4876FF">是否重新生成数据？</font>
						</h4>
					</div>
					<div class="spiner-example" id="spineradd" style="display: none">
							<div class="sk-spinner sk-spinner-fading-circle">
								<div class="sk-circle1 sk-circle"></div>
								<div class="sk-circle2 sk-circle"></div>
								<div class="sk-circle3 sk-circle"></div>
								<div class="sk-circle4 sk-circle"></div>
								<div class="sk-circle5 sk-circle"></div>
								<div class="sk-circle6 sk-circle"></div>
								<div class="sk-circle7 sk-circle"></div>
								<div class="sk-circle8 sk-circle"></div>
								<div class="sk-circle9 sk-circle"></div>
								<div class="sk-circle10 sk-circle"></div>
								<div class="sk-circle11 sk-circle"></div>
								<div class="sk-circle12 sk-circle"></div>
								<div class="sk-circle13 sk-circle"></div>
								<div class="sk-circle14 sk-circle"></div>
								<div class="sk-circle15 sk-circle"></div>
								<div class="sk-circle16 sk-circle"></div>
								<div class="sk-circle17 sk-circle"></div>
								<div class="sk-circle18 sk-circle"></div>
								<div class="sk-circle19 sk-circle"></div>
								<div class="sk-circle20 sk-circle"></div>
							</div>
							<div>
								<font size="11"></font>
							</div>
						</div>
					<div class="form-group">
						<div class="col-sm-5" style="margin-left: 200px">
							<button type="button" class="btn btn-success"
								data-dismiss="modal">关闭</button>
							<button type="button" onclick="attenadd()"
								class="btn btn-primary">&nbsp;是&nbsp;</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="kaoqin_Tab"></table>
	</div>
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>

	<script type="text/javascript">
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata) {
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
			var validator = formdata.validate({
				rules : {
					fingerprint : {
						required : true
					},
					attentime : {
						required : true
					},
					remark : {
						required : true
					}
				},
				messages : {
					fingerprint : {
						required : icon + "请选择员工指纹编号"
					},
					attentime : {
						required : icon + "请选择时期"
					},
					remark : {
						required : icon + "请输入描述"
					}
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>

	<script type="text/javascript">
		//----导入Excel表
		function StudrewardImport() {
			//使用FormData，在构造这个对象的时候，把表单的对象，作为一个参数放进去，就可以了，
			//然后FormData，就会得到这个表单对象里面的所有的参数，甚至我们在表单中，
			//都不需要声明enctype ="multipart/form-data" ，就可以直接提交
			$("#statustitle").text("数据正在导入中...");
			$("#spiner").css("display", "block");
			$("#formTable").css("display", "none");
			var form = new FormData(document.getElementById("studrewardForm"));
			$.ajax({
						url : "${pageContext.request.contextPath}/dailyWork/importStudReward",
						type : "post",
						data : form,
						processData : false,
						contentType : false,
						success : function(data) {
							if (data.ok) {
								var coCurrentList = data.data.importResults;
								var succeText = '';
								var failText = '';
								if (coCurrentList != null) {
									jQuery.each(coCurrentList,
											function(i, item) {
												if (item.ok == 'true') {
													succeText = '导入成功'
												} else {
													failText = '导入失败'
												}
											});
									$("#succeText").html(succeText);
									$("#failText").html(failText);
									$("#statustitle").text("导入完成!");
									$("#spiner").css("display", "none");
									$("#formTable").css("display", "block");
								}
							} else {
								alert("导入异常！");
								$("#statustitle").text("导入异常");
								$("#spiner").css("display", "none");
								$("#formTable").css("display", "block");
							}
						},
						error : function(e) {
							alert("错误！！");
							window.clearInterval(timer);
							$("#spiner").css("display", "none");
							$("#formTable").css("display", "block");
						}
					});
		}
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#workday',
			format : 'YYYY/MM/DD',
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
			elem : '#workdayupdate',
			format : 'YYYY/MM/DD',
			min : laydate.now(), //设定最小日期为当前日期
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
</body>
</html>