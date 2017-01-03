<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#window_flowHoliday').modal('show');
	});
	
	function addSrmHoliday(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
                title: "成功",
                text: "你已经完成添加操作",
                type: "success"
            },function(){
            	window.location.href = window.location.href;
            });
		} else {
			parent.layer.alert('添加失败！');
		}
	}
	
	function addHoliday() {
		//用来判断表单是否验证通过
		if (!modelHolidayFrom($("#flowHolidayStudFrom"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowFeedBack/addFlowFeedBack";
		$.post(url, {
			flowmodeltypeid : $("#window_flowHoliday #flowModelTypeid").val(),
			studentid : $("#window_flowHoliday #studId").val(),
			classid : $("#window_flowHoliday #classid").val(),
			money : $("#window_flowHoliday #money").val(),
			remark : $("#window_flowHoliday #remark").val()
		}, addSrmHoliday, "text");
	}
	
	function selectClass() {
		var url = "${pageContext.request.contextPath }/flow/flowFeedBack/findFeedBackClassList";
		$.post(url, {
			id : $("#fallid").val(),
		}, findclass, "json");
	}

	function findclass(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<option value="">--------</option>';
		for (var i = 0; i < len; i++) {
			opt += '<option value="'+data.rows[i].id+'" hassubinfo="true">'
					+ data.rows[i].classname + '</option>';
		}
		$("#classid").html(opt);
	}

	function selectStud() {
		var url = "${pageContext.request.contextPath }/flow/flowFeedBack/findFeedBackStudentList";
		$.post(url, {
			id : $("#classid").val(),
		}, findStud, "json");
	}

	function findStud(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<option value="">--------</option>';
		for (var i = 0; i < len; i++) {
			opt += '<option value="'+data.rows[i].id+'" hassubinfo="true">'
					+ data.rows[i].stuname + '</option>';
		}
		$("#studId").html(opt);
	}
</script>
</head>
<body>
	<div class="modal inmodal" id="window_flowHoliday" tabindex="-1"
		data-keyboard="false" role="dialog" aria-hidden="true"
		data-backdrop="static" data-show="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<h4 class="modal-title">发起学生退费申请</h4>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowHolidayStudFrom"
						novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">模板类型:</label>
							<div class="col-sm-10">
								<select name="flowModelTypeid" id="flowModelTypeid"
									class="form-control">
									<c:forEach items="${modeltypes}" var="type">
										<option value="${type.id}">${type.flowmodeltype}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">届别:</label>
							<div class="col-sm-4">
								<select class="form-control" id="fallid"
									onchange="selectClass();">
									<option value="">--------</option>
									<c:forEach items="${falls }" var="f">
										<option value="${f.id }">${f.level }</option>
									</c:forEach>
								</select>
							</div>
							<label class="control-label col-sm-2">班级名称:</label>
							<div class="col-sm-4">
								<select class="form-control" id="classid"
									onchange="selectStud();">
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">退费学生:</label>
							<div class="col-sm-4">
								<select class="form-control" id="studId" name="studId">
								</select>
							</div>
							<label class="control-label col-sm-2">退费金额:</label>
							<div class="col-sm-4">
								<input type="number" id="money" name="money" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">退费理由:</label>
							<div class="col-sm-10">
								<textarea id="remark" name="remark" rows="5"
									class="form-control" style="resize: none"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="reset" class="btn btn-primary">取消</button>
							<button type="button" id="btn_holiday_add"
								onclick="addHoliday();" class="btn btn-primary">提交</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script type="text/javascript">
		function modelHolidayFrom(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					flowModelTypeid : {required:true},
					studId : {required:true},
					money : {required:true},
					remark : {required:true}
				},
				messages : {
					flowModelTypeid :{required:icon + "请选择模板类型"},
					studId : {required:icon + "请选择要退费的学生"},
					money : {required:icon + "请输入退费金额"},
					remark : {required:icon + "请输入退费理由"}
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
</body>
</html>