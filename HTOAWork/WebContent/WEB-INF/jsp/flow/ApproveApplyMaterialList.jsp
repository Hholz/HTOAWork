<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<meta http-equiv="refresh"
	content="60; url='${pageContext.request.contextPath }/flow/approveapplymaterial/ApplyMaterialList'">
<meta charset="utf-8">
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link
	href="${pageContext.request.contextPath}/css/bootstrap.min14ed.css?v=3.3.6"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/css/font-awesome.min93e3.css?v=4.4.0"
	rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/animate.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/css/style.min862f.css?v=4.1.0"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<title>每日任务</title>
<style>
.divcss5-x5 {
	padding-bottom: 1px;
	border-bottom: 1px solid #000
}
</style>

<!-- 员工类事件 -->
<script type="text/javascript">
	function yes(id) {
		var url = "${pageContext.request.contextPath }/flow/approveapplymaterial/over"
		$.post(url, {
			id : id,
			flowmodelid : 1,
		}, updateStudentHandle, "text");
	}
	function no(id) {
		var url = "${pageContext.request.contextPath }/flow/approveapplymaterial/over"
		$.post(url, {
			id : id,
			flowmodelid : 0,
		}, updateStudentHandle, "text");
	}
	function updateStudentHandle() {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/flow/approveapplymaterial/ApplyMaterialList";
	}

	function approvematerialYes(id,flowApplyMaterialid,flowid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/approveapplymaterial/over"
			$.post(url, {
				id : id,
				flowmodelid : 1,
				materialid : flowApplyMaterialid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	function EmpHolidayYes(id,empholidayid,flowid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/flowHolidayEmp/flowEmpHolidayYes"
			$.post(url, {
				id : id,
				holidayid : empholidayid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function SupplementYes(id,sumentid,flowid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/ApplySupplement/flowSupplementYes"
			$.post(url, {
				id : id,
				sumentid : sumentid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function ReceiveMaterialYes(id,sumentid,flowid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/flowRematerialYes"
			$.post(url, {
				id : id,
				receivematerid : sumentid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function ReturnMaterialYes(id,sumentid,flowid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/ReturnMaterial/flowReturnmaterialYes"
			$.post(url, {
				id : id,
				returnmaterid : sumentid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	function ReimburseYes(id,sumentid,flowid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/Reimburse/flowbaoxiaoYes"
			$.post(url, {
				id : id,
				baoxiaoid : sumentid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
</script>

<!-- 学生类事件 -->
<script type="text/javascript">
	function toFrom(){
		
		var flowid = $("#window_flowModel #flowid").val();
		var id = $("#window_flowModel #id").val();
		var holidayid = $("#window_flowModel #holidayid").val();
		var remark = $("#window_flowModel #remark").val();
		
		if(flowid == 1){
			var url = "${pageContext.request.contextPath }/flow/approveapplymaterial/over";
			$.post(url, {
				id : id,
				flowmodelid : 0,
				remark : remark
			}, noholiday, "text");
		}else if(flowid == 2){
			var url = "${pageContext.request.contextPath }/flow/flowHolidayEmp/flowEmpHolidayNo";
			$.post(url, {
				id : id,
				holidayid : holidayid,
				remark : remark
			}, noholiday, "text");
		}else if(flowid == 3){
			var url = "${pageContext.request.contextPath }/flow/ApplySupplement/flowSupplementNo"
				$.post(url, {
					id : id,
					sumentid : holidayid,
					suremark : remark,
				}, noholiday, "text");
		}else if(flowid == 4){
			var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/flowRematerialNo"
				$.post(url, {
					id : id,
					receivematerid : holidayid,
					remark : remark,
				}, noholiday, "text");
		}else if(flowid == 5){
			var url = "${pageContext.request.contextPath }/flow/ReturnMaterial/flowReturnmaterialNo"
				$.post(url, {
					id : id,
					returnmaterid : holidayid,
					remark : remark,
				}, noholiday, "text");
		}else if(flowid == 6){
			var url = "${pageContext.request.contextPath }/flow/Reimburse/flowbaoxiaoNo"
				$.post(url, {
					id : id,
					returnmaterid : holidayid,
					remark : remark,
				}, noholiday, "text");
		}else if(flowid == 7){
			
		}else if(flowid == 8){
			
		}else if(flowid == 9){
			
		}else if(flowid == 10){
			var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/flowHolidayNo"
			$.post(url, {
				id : id,
				holidayid : holidayid,
				remark : remark,
			}, noholiday, "text");
		}else if(flowid == 11){
			var url = "${pageContext.request.contextPath }/flow/flowStudApply/flowStudentApplyNo"
			$.post(url, {
				id : id,
				studapplyid : holidayid,
				remark : remark,
			}, noholiday, "text");
		}else if(flowid == 12){
			var url = "${pageContext.request.contextPath }/flow/flowFeedBack/flowFeedBackNo"
			$.post(url, {
				id : id,
				feedbackid : holidayid,
				remark : remark,
			}, noholiday, "text");
		}else if(flowid == 13){
			
		}else if(flowid == 14){
			
		}else if(flowid == 15){
			
		}else if(flowid == 16){
			
		}else if(flowid == 17){
			
		}else if(flowid == 18){
			
		}
	}
	
	function holidayNo(id,flowid,holidayid){
		$("#window_flowModel #id").val(id);
		$("#window_flowModel #flowid").val(flowid);
		$("#window_flowModel #holidayid").val(holidayid);
		$('#window_flowModel').modal('show');
	}
	
	function feedBackDetialNo(id,flowid,studapplyid){
		$("#window_flowModel #id").val(id);
		$("#window_flowModel #flowid").val(flowid);
		$("#window_flowModel #holidayid").val(studapplyid);
		$('#window_flowModel').modal('show');
	}
	
	function computerApplyYes(id,computerapplyid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/flowComputerApply/computerApplyYes"
			$.post(url, {
				id : id,
				computerapplyid : computerapplyid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function studentApplyYes(id,studapplyid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/flowStudApply/studentApplyYes"
			$.post(url, {
				id : id,
				studapplyid : studapplyid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function feedBackDetialYes(id,feedbackid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/flowFeedBack/flowFeedBackYes"
			$.post(url, {
				id : id,
				feedbackid : feedbackid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function holidayYes(id,holidayid){
		parent.layer.confirm('确定同意申请吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/flowHolidayYes"
			$.post(url, {
				id : id,
				holidayid : holidayid,
			}, yesholiday, "text");
		},function(){//取消
			
		});
	}
	
	function yesholiday() {
		parent.layer.alert('已同意');
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/flow/approveapplymaterial/ApplyMaterialList";
	}
	
	function noholiday(){
		parent.layer.alert('已驳回申请');
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/flow/approveapplymaterial/ApplyMaterialList";
	}
	
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox">
					<div class="ibox-content">
						<h3>员工类申请审批任务</h3>
						<ul class="sortable-list connectList agile-list">
							<c:forEach items="${feedBackDetails }" var="feed">
								<li class="warning-element"><font size="3"><B> 班主任 <span
											class="divcss5-x5">${feed.feedBack.applyMan}</span> 替 <span
											class="divcss5-x5">${feed.feedBack.student.classInfo.classname }</span>班的<span
											class="divcss5-x5">${feed.feedBack.student.stuname}</span>同学发起
											退费申请,退费金额为:<span class="divcss5-x5">${feed.feedBack.money}</span>,
											退费理由为:<span class="divcss5-x5">${feed.feedBack.remark}</span>
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="feedBackDetialYes(${feed.id },${feed.feedbackid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="feedBackDetialNo(${feed.id },12,${feed.feedbackid });" id=""
											style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o">${feed.feedBack.createTime }</i></font>

									</div></li>
							</c:forEach>
							<c:forEach items="${famdlist }" var="data">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${data.flowApplyMaterial.applyman}</span>申请购买<span
											class="divcss5-x5">${data.material.counts}${data.material.unit }${data.material.materialname}</span>,
											总金额为<span class="divcss5-x5">${data.material.counts*data.material.price}元,</span>
											理由是:${data.flowApplyMaterial.applymaterialRemark}.
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="approvematerialYes(${data.id },${data.materialid },1 );"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${data.id },1,${data.materialid });" id=""
											style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o"></i></font>

									</div></li>
							</c:forEach>
							<c:forEach items="${EmpHolidayList }" var="empholiday">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${empholiday.flowHoliday.applyman }
										</span>以 <span class="divcss5-x5">${empholiday.flowHoliday.remark }</span>
											为理由请假, 开始时间:<span class="divcss5-x5">${empholiday.flowHoliday.strattime }</span>
											结束时间:<span class="divcss5-x5">${empholiday.flowHoliday.endtime }</span>
											望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="EmpHolidayYes(${empholiday.id },${empholiday.holidayid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${empholiday.id },2,${empholiday.holidayid });"
											id="" style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${SupplementdeatilList }" var="smdl">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${smdl.flowSupplement.applyman } </span>申请为
											<span class="divcss5-x5">${smdl.flowSupplement.sutime }</span>
											补签, 理由为:<span class="divcss5-x5">${smdl.flowSupplement.suremark }</span>
											望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="SupplementYes(${smdl.id },${smdl.sumentid });" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${smdl.id },3,${smdl.sumentid });" id=""
											style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${receivemateriallist }" var="rml">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${rml.flowReceivematerial.applyman }</span>
											申领了<span class="divcss5-x5">${rml.flowReceivematerial.counts }</span>${rml.material.unit }
											<span class="divcss5-x5">${rml.material.materialname }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="ReceiveMaterialYes(${rml.id },${rml.receivematerid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${rml.id },4,${rml.receivematerid });"
											id="" style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${returnmateriallist }" var="ret">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${ret.flowreturnmaterial.applyman }</span>
											归还了<span class="divcss5-x5">${ret.flowreturnmaterial.counts }</span>${ret.material.unit }
											<span class="divcss5-x5">${ret.material.materialname }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="ReturnMaterialYes(${ret.id },${ret.returnmaterid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${ret.id },5,${ret.returnmaterid });"
											id="" style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${Reimburselist }" var="Reimburse">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${Reimburse.flowBaoxiao.applyman }</span>
											以<span class="divcss5-x5">${Reimburse.flowBaoxiao.applyremark }</span>为理由
											申请报销,金额为<span class="divcss5-x5">${Reimburse.flowBaoxiao.price }</span>元
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="ReimburseYes(${Reimburse.id },${Reimburse.baoxiaoid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${Reimburse.id },6,${Reimburse.baoxiaoid });"
											id="" style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox">
					<div class="ibox-content">
						<h3>学生类申请审批任务</h3>
						<ul class="sortable-list connectList agile-list">
							<c:forEach items="${holidaydetails }" var="holiday">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${holiday.flowHoliday.student.stuname }
										</span>同学以 <span class="divcss5-x5">${holiday.flowHoliday.remark }</span>
											为理由请假, 离校时间:<span class="divcss5-x5">${holiday.flowHoliday.strattime }</span>
											返校时间:<span class="divcss5-x5">${holiday.flowHoliday.endtime }</span>
											望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="holidayYes(${holiday.id },${holiday.holidayid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${holiday.id },10,${holiday.holidayid });"
											id="" style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o">${holiday.flowHoliday.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${applyDetails }" var="app">
								<li class="warning-element"><font size="3"><B>
											姓名:<span class="divcss5-x5">${app.studentApply.student.stuname }
										</span> 现在所在班级:<span class="divcss5-x5">${app.studentApply.studentClass.classname }</span>
											入学时间:<span class="divcss5-x5">${app.studentApply.stratTime }</span>
											现在所在班级所学课程阶段:<span class="divcss5-x5">${app.studentApply.studwork }</span>
											转班原因: <span class="divcss5-x5">${app.studentApply.remark }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#"
											onclick="studentApplyYes(${app.id },${app.studapplyid });"
											id="" style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">同意</a> <a href="#"
											onclick="holidayNo(${app.id },11,${app.studapplyid });" id=""
											style="background-color: white; color: black;"
											class="pull-right btn btn-ms btn-white">不同意</a> <font
											size="4"><i class="fa fa-clock-o">${app.studentApply.createTime }</i></font>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 不同意申请理由  -->
	<div class="modal inmodal" id="window_flowModel" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">不同意理由</h4>
				</div>

				<div class="ibox-content">
					<form class="form-horizontal m-t" id="flowFrom"
						novalidate="novalidate">
						<input id="id" name="id" type="hidden" /> <input id="flowid"
							type="hidden" /> <input id="holidayid" type="hidden" />
						<div class="form-group">
							<label class="col-sm-2 control-label">理由:</label>
							<div class="col-sm-9">
								<textarea id="remark" name="remark" rows="4"
									class="form-control"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
							<button type="button" onclick="toFrom();" class="btn btn-primary">提交</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>