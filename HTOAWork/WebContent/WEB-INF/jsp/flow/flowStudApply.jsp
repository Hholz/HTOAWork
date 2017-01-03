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
		$('#window_flowFeedBack').modal('show');
	});
	
	function saveStudentApply() {
		//用来判断表单是否验证通过
		if (!modelHolidayFrom($("#flowStudentApplyFrom"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowStudApply/saveFlowStudentApply";
		$.post(url, {
			flowmodeltypeid : $("#window_flowFeedBack #flowmodeltypeid").val(),
			stratTime : $("#window_flowFeedBack #stratTime").val(),
			classid2 : $("#window_flowFeedBack #classid").val(),
			studwork  : $("#window_flowFeedBack #studwork").val(),
			remark : $("#window_flowFeedBack #remark").val()
		}, addSrmHoliday, "text");
	}
	
	function addHoliday() {
		//用来判断表单是否验证通过
		if (!modelHolidayFrom($("#flowStudentApplyFrom"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowStudApply/addFlowStudentApply";
		$.post(url, {
			flowmodeltypeid : $("#window_flowFeedBack #flowmodeltypeid").val(),
			stratTime : $("#window_flowFeedBack #stratTime").val(),
			classid2 : $("#window_flowFeedBack #classid").val(),
			studwork  : $("#window_flowFeedBack #studwork").val(),
			remark : $("#window_flowFeedBack #remark").val()
		}, addSrmHoliday, "text");
	}
	
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
			parent.layer.alert('保存失败！');
		}
	}
	
	function selectClass(){
		var url = "${pageContext.request.contextPath }/flow/flowStudApply/findClassListByFallid";
		$.post(url, {
			id : $("#fallid").val(),
		}, findclass, "json");
	}
	
	function findclass(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<option value="">--------</option>';
		for(var i=0;i<len;i++){
			opt += '<option value="'+data.rows[i].id+'" hassubinfo="true">'
				+ data.rows[i].classname + '</option>';
		}
		$("#classid").html(opt);
	}
</script>
</head>
<body>
	<div class="modal inmodal" id="window_flowFeedBack" tabindex="-1"
		data-keyboard="false" role="dialog" aria-hidden="true"
		data-backdrop="static" data-show="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<h4 class="modal-title">填写调班申请单</h4>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowStudentApplyFrom"
						novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">模板类型:</label>
							<div class="col-sm-10">
								<select name="flowmodeltypeid" id="flowmodeltypeid"
									class="form-control">
									<c:forEach items="${modeltypes}" var="type">
										<option value="${type.id}">${type.flowmodeltype}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">界别:</label>
	                        <div class="col-sm-4">
								<select class="form-control" name="fallid" id="fallid" onchange="selectClass();">
									<option value="">--------</option>
									<c:forEach items="${falls }" var="f">
										<option value="${f.id }">${f.level }</option>
									</c:forEach>
								</select>
	                        </div>
	                        <label class="control-label col-sm-2">转入班级:</label>
	                        <div class="col-sm-4">
								<select class="form-control" id="classid" name="classid">
								</select>
			                </div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">入学时间:</label>
	                        <div class="col-sm-4">
								<input placeholder="选择日期" class="form-control layer-date"
										id="stratTime" name="stratTime">
	                        </div>
	                        <label class="control-label col-sm-2">课程阶段:</label>
	                        <div class="col-sm-4">
								<input id="studwork" name="studwork" type="text" class="form-control">
			                </div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">调班理由:</label>
							<div class="col-sm-10">
								<textarea id="remark" name="remark" rows="5"
									class="form-control" style="resize: none"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" id="btn_apply_save"
								onclick="saveStudentApply();" class="btn btn-primary">保存草稿</button>
							<button type="button" id="btn_apply_add"
								onclick="addHoliday();" class="btn btn-primary">提交</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script type="text/javascript">
		function modelHolidayFrom(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					flowmodeltypeid : {required:true},
					stratTime : {required:true},
					fallid : {required:true},
					classid : {required:true},
					studwork : {required:true},
					remark : {required:true}
				},
				messages : {
					flowmodeltypeid :{required:icon + "请选择模板类型"},
					stratTime : {required:icon + "必填"},
					fallid : {required:icon + "请选择界别"},
					classid : {required:icon + "请选择要换到的班级"},
					studwork : {required:icon + "请填写学到的课程进度"},
					remark : {required:icon + "请填写调换班级的理由"}
				},
				submitHandler : function(form) {
					alert("表单验证成功，不提交" + validator.form());
				}
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#stratTime',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : laydate.now(), //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
			}
		};
		
		laydate(start);
	</script>
</body>
</html>