<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请调班</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#window_flowHoliday').modal('show');
		findteacher();
	});

	function clear() {
		//清空新增div中的数据
		$("#window_flowHoliday #changeman").val('');
		$("#window_flowHoliday #remark").val('');
	}
	function findteacher(){
		var weekid=$("#window_flowHoliday #time").val();
		
		//alert(weekid);
		var url = "${pageContext.request.contextPath }/flow/ApplyChangeDuty/findteacher";
		$.post(url, {
			weekends : weekid,
		}, initemp, "text");
		
	}
	function initemp(data){
		$("#window_flowHoliday #changeman").empty();
		
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
						+ datas.rows[i].empName + '</option>';
			}
			$('#window_flowHoliday #changeman').html(opt);
			$('#window_flowHoliday #changeman').trigger("chosen:updated");
		}
		
		
	}

	function saveHoliday() {
		//用来判断表单是否验证通过
		if (!modelHolidayFrom($("#flowHolidayStudFrom"))) {
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/ApplyChangeDuty/saveChangeDuty";
		$.post(url, {
			flowmodeltypeid : $("#window_flowHoliday #flowModelTypeid").val(),
			changeTwo : $("#window_flowHoliday #changeman").val(),
			remark : $("#window_flowHoliday #remark").val()
		}, addSrmHoliday, "text");
	}

	function addSrmHoliday(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal(
					{
						title : "成功",
						text : "你已经完成添加操作",
						type : "success"
					},
					function() {
						window.location.href = "${pageContext.request.contextPath }/flow/myFlowList";
					});
		} else if (date == -1) {
			parent.layer.alert('session过期,请重新登录');
		} else if (date == -2) {
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
		if (stratTime == null || stratTime == "") {
			parent.layer.alert('请填写开始请假时间');
			return;
		}
		var url = "${pageContext.request.contextPath }/flow/ApplyChangeDuty/addChangeDuty";
		$.post(url, {
			flowmodeltypeid : $("#window_flowHoliday #flowModelTypeid").val(),
			changeTwo : $("#window_flowHoliday #changeman").val(),
			remark : $("#window_flowHoliday #remark").val()
		}, addSrmHoliday, "text");
		clear();
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
					<h4 class="modal-title">调班申请单</h4>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowHolidayStudFrom"
						novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">模板类型:</label>
							<div class="col-sm-10">
								<select id="flowModelTypeid" disabled="disabled"
									class="form-control">
									<c:forEach items="${flowmodeltypelist}" var="type">
										<option value="${type.id}">${type.flowmodeltype}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">换班时间:</label>
							<div class="col-sm-4">
								<select id="time" class="form-control" onchange="findteacher();" >
									<%
									String week = "";
									List<String> weksList = (List<String>)request.getAttribute("weeklist");
									for(int a=0;a<weksList.size();a++){
											week = weksList.get(a).toString();
									%>
										<option value="<%=week %>"><%=week %></option>
									<%
									}
									%>
								</select>
							</div>
							<label class="col-sm-2 control-label">和谁换班:</label>
							<div class="col-sm-4">
								<select id="changeman" class="form-control" >
									<option value="">---</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">原因:</label>
							<div class="col-sm-10">
								<textarea id="remark" name="remark" rows="5"
									class="form-control" style="resize: none"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" id="btn_holiday_save"
								onclick="saveHoliday();" class="btn btn-primary">保存草稿</button>
							<button type="button" id="btn_holiday_add"
								onclick="addHoliday();" class="btn btn-primary">提交</button>
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
		function modelHolidayFrom(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					flowModelTypeid : {
						required : true
					},
					stratTime : {
						required : true
					},
					endTime : {
						required : true
					},
					remark : {
						required : true
					}
				},
				messages : {
					flowModelTypeid : {
						required : icon + "请选择模板类别"
					},
					stratTime : {
						required : icon + "必填"
					},
					endTime : {
						required : icon + "必填"
					},
					remark : {
						required : icon + "请填写请假理由"
					}
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
			min : laydate.now(-3),//-1代表昨天，-2代表前天，以此类推
			max : laydate.now(), //+1代表明天，+2代表后天，以此类推
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