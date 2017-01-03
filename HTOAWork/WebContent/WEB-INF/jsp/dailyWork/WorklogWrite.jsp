<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/plugins/markdown/bootstrap-markdown.min.css" />
<title>办公用品列表</title>

<script type="text/javascript">
	$(function() {
		$("#window_add #empid").val($("#window_add #id").val());
		if ($("#window_add #classid").val() == null
				|| $("#window_add #classid").val() == "") {
			$("#window_add #thisclass").css("display", "none");
			$("#window_add #thiscourse").css("display", "none");
			$("#window_add #thisoutline").css("display", "none");
			$("#window_add #thishour").css("display", "none");
		} else {
			$("#window_add #thisclass").css("display", "block");
			$("#window_add #thiscourse").css("display", "block");
			$("#window_add #thisoutline").css("display", "block");
			$("#window_add #thishour").css("display", "block");
			getCourse();
		}
	});
	var getCourse = function() {
		var url = "${pageContext.request.contextPath}/dailyWork/Worklog/getCourseByClassId";
		$.post(url, {
			classId : $("#window_add #classid").val()
		}, resetCourse, "json");
		function resetCourse(datas) {
			if (datas.rows.length > 0) {
				$("#window_add #thiscourse").css("display", "block");
				$("#window_add #thisoutline").css("display", "block");
				$("#window_add #thishour").css("display", "block");
				var str = "";
				var i;
				for (i = 0; i < datas.rows.length; i++) {
					str += '<option value="'+datas.rows[i].id+'">'
							+ datas.rows[i].name + '</option>';
				}
				str += '<option value="-1">未讲课</option>';
				$("#window_add #courseid").html(str);
				getOutLine();
			} else {
				$("#window_add #thiscourse").css("display", "none");
				$("#window_add #thisoutline").css("display", "none");
				$("#window_add #thishour").css("display", "none");
				$("#window_add #courseid").html(
						'<option value="0">无课程信息</option>');
			}
		}
	}
	var getOutLine = function() {
		var id = $("#window_add #courseid").val();
		if (id == "") {
			$('#window_add #process').html("<option value='0'>暂无大纲信息</option>");
			return;
		}
		if (id == -1) {
			$('#window_add #process').html("<option value='0'>暂无大纲信息</option>");
			$('#window_add #hour').html("<option value='0'>暂无课时信息</option>");
			$("#window_add #thisoutline").css("display", "none");
			$("#window_add #thishour").css("display", "none");
			return;
		}
		$("#window_add #thisoutline").css("display", "block");
		$("#window_add #thishour").css("display", "block");
		var url = "${pageContext.request.contextPath}/education/syllabus/getOutlineByCourseId2";
		$.post(url, {
			_method : 'PUT',
			courseId : $("#window_add #courseid").val()
		}, resetOutLine, "text");
		function resetOutLine(data) {
			//alert(data);
			var datas = JSON.parse(data);
			if (datas.rows.length > 0) {
				var str = "";
				for (var i = 0; i < datas.rows.length; i++) {
					str += '<option value="'+datas.rows[i].outline.id+'">'
							+ datas.rows[i].outline.outlineName + '</option>';
				}
				$('#window_add #process').html(str);
				getHourFromOutLine();
			} else {
				$('#window_add #process').html(
						"<option value='0'>暂无大纲信息</option>");
				$('#window_add #hour')
						.html("<option value='0'>暂无课时信息</option>");
			}
		}
	}
	var getHourFromOutLine = function() {
		var id = $("#window_add #courseid").val();
		if (id == "") {
			$('#window_add #hour').html("<option value='0'>暂无课时安排</option>");
			return;
		}
		var outline = $('#window_add #process').val();
		var url = "${pageContext.request.contextPath}/education/syllabus/getHourFromOutLine";
		$.post(url, {
			_method : 'PUT',
			id : outline
		}, setHour, "json");
		function setHour(datas) {
			//alert(datas);
			if (datas.rows.length > 0) {
				if (datas.rows[0].hour > 0) {
					var str = "";
					for (var i = 1; i <= datas.rows[0].hour; i++) {
						str += '<option value="'+i+'">第' + i + '课时</option>';
						$('#window_add #hour').html(str);
					}
				} else {
					$('#window_add #hour').html(
							"<option value='0'>暂无课时安排</option>");
				}
			}
		}
	}

	//新增学生，ajax提交
	function addStudent() {
		if ($("#window_add #contents").val().trim() == null
				|| $("#window_add #contents").val().trim() == "") {
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
		$("#window_add #contents").val('');
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
	function isHavaPlan() {
		getCourse();
	}
	function isHavaOutLine() {
		getOutLine();
	}
	function isHavaHour() {
		getHourFromOutLine();
	}
</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12" id="window_add">
				<div class="ibox float-e-margins">
					<form class="form-horizontal m-t" id="addForm"
						novalidate="novalidate">
						<div class="form-group">
							<!-- <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button> -->
							<div class="col-sm-2 col-sm-offset-2">
								<button type="button" onclick="addStudent()"
									class="btn btn-lg btn-block btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>保存日志</button>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-9 col-sm-offset-2">
								<!-- <input id="contents" name="contents" type="text" required class="form-control"> -->
								<textarea name="content" data-provide="markdown" rows="4"
									id="contents" name="contents" placeholder="工作日报内容..."></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工：</label>
							<div class="col-sm-8">
								<input id="id" type="hidden" value="${userid}"> <select
									id="empid" name="empid" required class="form-control"
									disabled="disabled">
									<c:forEach items="${emp}" var="emp">
										<option value="${emp.id}">${emp.empname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group" style="display: block;" id="thisclass">
							<label class="col-sm-3 control-label">班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</label>
							<div class="col-sm-8">
								<select id="classid" name="classid" required
									class="form-control" onchange="isHavaPlan();">
									<c:forEach items="${educlass}" var="cl">
										<option value="${cl.id}">${cl.classname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group" style="display: block;" id="thiscourse">
							<label class="col-sm-3 control-label">课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：</label>
							<div class="col-sm-8">
								<select id="courseid" name="courseid" required
									class="form-control" onchange="isHavaOutLine();">
								</select>
							</div>
						</div>
						<div class="form-group" style="display: block;" id="thisoutline">
							<label class="col-sm-3 control-label">大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纲：</label>
							<div class="col-sm-8">
								<select id="process" name="process" class="form-control"
									onchange="isHavaHour();">
									<!-- <option value="0">-</option> -->
								</select>
							</div>
						</div>
						<div class="form-group" style="display: block;" id="thishour">
							<label class="col-sm-3 control-label">课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时：</label>
							<div class="col-sm-8">
								<select id="hour" name="hour" class="form-control">
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script type="text/javascript">
		$.validator.setDefaults({
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success')
						.addClass('has-error');
			},
			success : function(element) {
				element.closest('.form-group').removeClass('has-error')
						.addClass('has-success');
			},
			errorElement : "span",
			errorPlacement : function(error, element) {
				if (element.is(":radio") || element.is(":checkbox")) {
					error.appendTo(element.parent().parent().parent());
				} else {
					error.appendTo(element.parent());
				}
			},
			errorClass : "help-block m-b-none",
			validClass : "help-block m-b-none"

		});
		function validateForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					empid : {
						required : true
					},
					workday : {
						required : true
					},
					contents : {
						required : true
					},
					classid : {
						required : true
					},
					courseid : {
						required : true
					}

				},
				messages : {
					empid : {
						required : icon + "请选择员工"
					},
					workday : {
						required : icon + "请选择工作日期"
					},
					contents : {
						required : icon + "请填写工作内容"
					},
					classid : {
						required : icon + "请选择班级"
					},
					courseid : {
						required : icon + "请选择课程"
					}
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
	<!-- simditor -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/plugins/markdown/markdown.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/plugins/markdown/to-markdown.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/plugins/markdown/bootstrap-markdown.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/plugins/markdown/bootstrap-markdown.zh.js"></script>
</body>
</html>