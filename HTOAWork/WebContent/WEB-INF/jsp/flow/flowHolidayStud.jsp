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
	
	function saveHoliday() {
		//用来判断表单是否验证通过
		if (!modelHolidayFrom($("#flowHolidayStudFrom"))) {
			return;
		}
		var stratTime = $("#window_flowHoliday #stratTime").val();
		var endTime = $("#window_flowHoliday #endTime").val();
		if(stratTime == null || stratTime == ""){
			parent.layer.alert('请填写开始请假时间');
			return;
		}
		if(endTime == null || endTime == ""){
			parent.layer.alert('请填写返校时间');
			return;
		}
		var dateStrat = stratTime.split(" ");//年月日和时分秒分离
		var dateEnd = endTime.split(" ");
		var dateS1 = dateStrat[0].split("/");//年月日分离
		var dateE1 = dateEnd[0].split("/");
		var dateS2 = dateStrat[1].split(":");//时分秒分离
		var dateE2 = dateEnd[1].split(":");
		var dateS = new Date(dateS1[0]-1,dateS1[1]-1,dateS1[2]+1,dateS2[0],dateS2[1],dateS2[2]);//拼接成date类型
		var dateE = new Date(dateE1[0]-1,dateE1[1]-1,dateE1[2]+1,dateE2[0],dateE2[1],dateE2[2]);//拼接成date类型
		var dateST = dateS.getTime();
		var dateET = dateE.getTime();
		if(dateST-dateET>0){
			parent.layer.alert('开始请假时间不能超过返校时间');
			return;
		}else if(dateST - dateET == 0){
			parent.layer.alert('开始请假时间和返校时间一样');
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/saveFlowHolidayStud";
		$.post(url, {
			flowmodeltypeid : $("#window_flowHoliday #flowModelTypeid").val(),
			strattime : $("#window_flowHoliday #stratTime").val(),
			endtime : $("#window_flowHoliday #endTime").val(),
			remark : $("#window_flowHoliday #remark").val()
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
		} else if(date == -1) {
			parent.layer.alert('session过期,请重新登录');
		} else if(date == -2){
			parent.layer.alert('所选模板类型没有审批人,请重新选择');
		} else {
			parent.layer.alert('添加失败！');
		}
	}
	
	function addHoliday() {
		//用来判断表单是否验证通过
		if (!modelHolidayFrom($("#flowHolidayStudFrom"))) {
			return;
		}
		var stratTime = $("#window_flowHoliday #stratTime").val();
		var endTime = $("#window_flowHoliday #endTime").val();
		if(stratTime == null || stratTime == ""){
			parent.layer.alert('请填写开始请假时间');
			return;
		}
		if(endTime == null || endTime == ""){
			parent.layer.alert('请填写返校时间');
			return;
		}
		var dateStrat = stratTime.split(" ");//年月日和时分秒分离
		var dateEnd = endTime.split(" ");
		var dateS1 = dateStrat[0].split("/");//年月日分离
		var dateE1 = dateEnd[0].split("/");
		var dateS2 = dateStrat[1].split(":");//时分秒分离
		var dateE2 = dateEnd[1].split(":");
		var dateS = new Date(dateS1[0]-1,dateS1[1]-1,dateS1[2]+1,dateS2[0],dateS2[1],dateS2[2]);//拼接成date类型
		var dateE = new Date(dateE1[0]-1,dateE1[1]-1,dateE1[2]+1,dateE2[0],dateE2[1],dateE2[2]);//拼接成date类型
		var dateST = dateS.getTime();
		var dateET = dateE.getTime();
		if(dateST-dateET>0){
			parent.layer.alert('开始请假时间不能超过返校时间');
			return;
		}else if(dateST - dateET == 0){
			parent.layer.alert('开始请假时间和返校时间一样');
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/addFlowHolidayStud";
		$.post(url, {
			flowmodeltypeid : $("#window_flowHoliday #flowModelTypeid").val(),
			strattime : $("#window_flowHoliday #stratTime").val(),
			endtime : $("#window_flowHoliday #endTime").val(),
			remark : $("#window_flowHoliday #remark").val()
		}, addSrmHoliday, "text");
	}
	
</script>
</head>
<body>
	<div class="modal inmodal" id="window_flowHoliday" tabindex="-1" data-keyboard="false"
		role="dialog" aria-hidden="true" data-backdrop="static" data-show="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">填写请假申请单</h4>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowHolidayStudFrom"
						novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">模板类型:</label>
							<div class="col-sm-10">
								<select name="flowModelTypeid" id="flowModelTypeid" class="form-control">
									<c:forEach items="${modeltypes}" var="type">
										<option value="${type.id}">${type.flowmodeltype}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">请假时间:</label>
							<div class="col-sm-4">
								<input placeholder="选择日期" class="form-control layer-date"
										id="stratTime" name="stratTime">
							</div>
							<label class="col-sm-2 control-label">返校时间:</label>
							<div class="col-sm-4">
								<input placeholder="选择日期" class="form-control layer-date"
										id="endTime" name="endTime">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">请假理由:</label>
							<div class="col-sm-10">
								<textarea id="remark" name="remark" rows="5"
									class="form-control" style="resize:none"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" id="btn_holiday_save" onclick="saveHoliday();" class="btn btn-primary">保存草稿</button>
							<button type="button" id="btn_holiday_add" onclick="addHoliday();" class="btn btn-primary">提交</button>
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
					flowModelTypeid : {required:true},
					stratTime : {required:true},
					endTime : {required:true},
					remark : {required:true}
				},
				messages : {
					flowModelTypeid :{required:icon + "请选择模板类型"},
					stratTime : {required:icon + "必填"},
					endTime : {required:icon + "必填"},
					remark : {required:icon + "请填写请假理由"}
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
			format : 'YYYY/MM/DD hh:mm:ss',
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
			elem : '#endTime',
			format : 'YYYY/MM/DD hh:mm:ss',
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
</body>
</html>