<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<meta charset="utf-8">
<jsp:include page="../styleInclude.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/demo/bs-is-fun.css">
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
<title>我的申请</title>
<style>
.divcss5-x5 {
	padding-bottom: 1px;
	border-bottom: 1px solid #000
}
</style>
<!-- 员工类事件 -->
<script type="text/javascript">

	function deleteapplymaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ApplyMaterial/"+id;
		$.post(url, {
			_method : 'DELETE',
			
		}, del, "text");
	}
	
	function deleteEmpHoliday(id){
		var url = "${pageContext.request.contextPath }/flow/flowHolidayEmp/deleteHolidayById";
		$.post(url, {
			_method : 'DELETE',
			id : id
		}, del, "text");
	}
	
	function deleteSupplement(id){
		var url = "${pageContext.request.contextPath }/flow/ApplySupplement/deleteSupplementById";
		$.post(url, {
			_method : 'DELETE',
			id : id
		}, del, "text");
	}
	function deleteReceiveMaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/"+id;
		$.post(url, {
			_method : 'DELETE',
			id : id
		}, del, "text");
	}
	function deleteReturnMaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ReturnMaterial/"+id;
		$.post(url, {
			_method : 'DELETE',
			id : id
		}, del, "text");
	}
	function deleteReimburse(id){
		var url = "${pageContext.request.contextPath }/flow/Reimburse/"+id;
		$.post(url, {
			_method : 'DELETE',
			id : id
		}, del, "text");
	}
	function deleteSwaparrange(id){
		var url = "${pageContext.request.contextPath }/flow/ApplyChangeDuty/deleteChangeDutyById";
		$.post(url, {
			_method : 'DELETE',
			id : id
		}, del, "text");
	}
	
	
	function upsetapplymaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ApplyMaterial/upset";
		$.post(url, {
			id : id
			
		}, noholiday, "text");
	}
	
	function upsetEmpHoliday(id){
		var url = "${pageContext.request.contextPath }/flow/flowHolidayEmp/updateHolidayUpset";
		$.post(url, {
			_method : 'PUT',
			id : id
			
		}, noholiday, "text");
	}
	
	function upsetSupplement(id){
		var url = "${pageContext.request.contextPath }/flow/ApplySupplement/updateSupplementUpset";
		$.post(url, {
			_method : 'PUT',
			id : id
			
		}, noholiday, "text");
	}
	
	function upsetReceiveMaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/upset";
		$.post(url, {
			_method : 'PUT',
			id : id
			
		}, noholiday, "text");
	}
	
	function upsetReturnMaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ReturnMaterial/upset";
		$.post(url, {
			_method : 'PUT',
			id : id
			
		}, noholiday, "text");
	}
	function upsetReimburse(id){
		var url = "${pageContext.request.contextPath }/flow/Reimburse/upset";
		$.post(url, {
			_method : 'PUT',
			id : id
			
		}, noholiday, "text");
	}
	function upsetSwaparrange(id){
		var url = "${pageContext.request.contextPath }/flow/ApplyChangeDuty/updateChangeDutyUpset";
		$.post(url, {
			_method : 'PUT',
			id : id
			
		}, noholiday, "text");
	}
	
	function lookapplymaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ApplyMaterial/lookapplymaterial";
		$.post(url, {
			id : id
			
		}, looklength, "json");
	}
	
	function looklength(data){
		var opt = "";
		var len = data.rows.length;
		opt += '<ul class="nav nav-pills nav-justified step step-progress">';
		for(var i=0;i<len;i++){
			if(data.rows[i].status == 1){
				opt += '<li class="active"><a>'+data.rows[i].flowmodel.flowmodelname+'<br>&nbsp;'+data.rows[i].emp.empname+'<span class="caret"></span></a></li>';
			}else{
				opt += '<li><a>'+data.rows[i].flowmodel.flowmodelname+'<br>&nbsp;'+data.rows[i].emp.empname+'<span class="caret"></span></a></li>';
			}
		}
		opt += '</ul>';
		$("#window_flowModelSel #detial").html(opt);
		$("#window_flowModelSel").modal('show');
	}
	
	function lookempholidaydetial(id){
		var url = "${pageContext.request.contextPath }/flow/flowHolidayEmp/lookHolidayDetial";
		$.post(url, {
			id : id
			
		}, empholidaylength, "json");
	}
	
	function empholidaylength(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<ul class="nav nav-pills nav-justified step step-progress">';
		opt += '<li class="active"><a>填写申请单<br/>&nbsp;<span class="caret"></span></a></li>'
		for(var i=0;i<len;i++){
			if(data.rows[i].status == 1){
				opt += '<li class="active"><a>'+data.rows[i].flowModel.flowmodelname+'<br>&nbsp;'+data.rows[i].emp.empname+'<span class="caret"></span></a></li>';
			}else{
				opt += '<li><a>'+data.rows[i].flowModel.flowmodelname+'<br><span class="caret"></span>'+data.rows[i].emp.empname+'</a></li>';
			}
		}
		opt += '</ul>';
		$("#window_flowModelSel #detial").html(opt);
		$("#window_flowModelSel").modal('show');
	}
	function lookempholidaylength(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<ul class="nav nav-pills nav-justified step step-progress">';
		for(var i=0;i<len;i++){
			if(data.rows[i].status == 1){
				opt += '<li class="active"><a>'+data.rows[i].flowModel.flowmodelname+'<br>&nbsp;'+data.rows[i].emp.empname+'<span class="caret"></span></a></li>';
			}else{
				opt += '<li><a>'+data.rows[i].flowModel.flowmodelname+'<br><span class="caret"></span>'+data.rows[i].emp.empname+'</a></li>';
			}
		}
		opt += '</ul>';
		$("#window_flowModelSel #detial").html(opt);
		$("#window_flowModelSel").modal('show');
	}
	function lookSupplementdetial(id){
		var url = "${pageContext.request.contextPath }/flow/ApplySupplement/lookSupplementDetial";
		$.post(url, {
			id : id
			
		}, empholidaylength, "json");
	}
	
	function lookReceivceMaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/lookReceviceMaterial";
		$.post(url, {
			id : id
			
		}, lookempholidaylength, "json");
	}
	function lookReturnMaterial(id){
		var url = "${pageContext.request.contextPath }/flow/ReturnMaterial/lookReturnMaterial";
		$.post(url, {
			id : id
			
		}, lookempholidaylength, "json");
	}
	
	function lookReimburse(id){
		var url = "${pageContext.request.contextPath }/flow/Reimburse/lookbaoxiao";
		$.post(url, {
			id : id
			
		}, empholidaylength, "json");
	}
	function lookSwaparrange(id){
		var url = "${pageContext.request.contextPath }/flow/ApplyChangeDuty/lookChangeDutyDetial";
		$.post(url, {
			id : id
			
		}, empholidaylength, "json");
	}


</script>

<!-- 学生类事件 -->
<script type="text/javascript">

	//提交请假草稿
	function formFlowComputerApply(id){
		var url = "${pageContext.request.contextPath }/flow/flowComputerApply/updateComputerApply"
		$.post(url, {
			_method : 'PUT',
			id : id,
		}, noholiday, "text");
	}

	//提交请假草稿
	function formFlowHoliday(id){
		var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/updateHolidayUpset"
		$.post(url, {
			_method : 'PUT',
			id : id,
		}, noholiday, "text");
	}
	
	//提交学生调班申请单
	function formFlowStudentApply(id){
		var url = "${pageContext.request.contextPath }/flow/flowStudApply/updateStudentApplyUpset"
		$.post(url, {
			_method : 'PUT',
			id : id,
		}, noholiday, "text");
	}
	
	function noholiday(){
		parent.layer.alert('已提交申请');
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/flow/myFlowList";
	}

	//删除学生领取电脑申请草稿
	function deleteComputerApply(id){
		var url = "${pageContext.request.contextPath }/flow/flowComputerApply/deleteComputerApply"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, del, "text");
	}
	
	//删除请假单草稿
	function deleteHoliday(id){
		var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/deleteHolidayById"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, del, "text");
	}
	
	//删除学生调班申请单
	function deleteStudentApply(id){
		var url = "${pageContext.request.contextPath }/flow/flowStudApply/deleteStudentApplyById"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, del, "text");
	}
	
	function del(){
		parent.layer.alert('删除成功');
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/flow/myFlowList";
	}
	
	//查看学生申请领取电脑审批详情
	function lookComputerApplyDetial(id){
		var url = "${pageContext.request.contextPath }/flow/flowComputerApply/lookComputerApplyDetial"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, lookStudentApply, "json");
	}
	
	//查看学生请假审批进度
	function lookHolidayDetial(id){
		var url = "${pageContext.request.contextPath }/flow/flowHolidayStud/lookHolidayDetial"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, lookStudentApply, "json");
	}
	
	//查看学生请假审批进度
	function lookStudentApplyDetial(id){
		var url = "${pageContext.request.contextPath }/flow/flowStudApply/lookStudentApplyDetial"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, lookStudentApply, "json");
	}
	
	//查看学生退费申请审批详情
	function lookFeedBackDetial(id){
		var url = "${pageContext.request.contextPath }/flow/flowFeedBack/lookFeedBackDetial"
		$.post(url, {
			_method : 'PUT',//改成PUT提交
			id : id,
		}, lookStudentApply, "json");
	}
	
	function lookStudentApply(data) {
		var opt = "";
		var len = data.rows.length;
		opt += '<ul class="nav nav-pills nav-justified step step-progress">';
		opt += '<li class="active"><a>填写申请单<br/>&nbsp;<span class="caret"></span></a></li>'
		for(var i=0;i<len;i++){
			if(data.rows[i].status == 1){
				opt += '<li class="active"><a>'+data.rows[i].flowModel.flowmodelname+'<br/>'+data.rows[i].emp.empname+'<span class="caret"></span></a></li>';
			}else{
				opt += '<li><a>'+data.rows[i].flowModel.flowmodelname+'<br/>'+data.rows[i].emp.empname+'<span class="caret"></span></a></li>';
			}
		}
		opt += '</ul>';
		$("#window_flowModelSel #detial").html(opt);
		$("#window_flowModelSel").modal('show');
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox">
					<div class="ibox-content">
						<h3>已提交申请</h3>
						<ul class="sortable-list connectList agile-list">
							<c:forEach items="${feedBacks1 }" var="feed">
								<li class="warning-element"><font size="3"><B> 班主任<span
											class="divcss5-x5">${feed.applyMan } </span>替<span
											class="divcss5-x5">${feed.student.classInfo.classname } </span>班的<span
											class="divcss5-x5">${feed.student.stuname } </span>同学发起
											退费申请,退费理由为: <span
											class="divcss5-x5">${feed.remark }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookFeedBackDetial(${feed.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${feed.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${holiday1 }" var="h1">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${h1.student.stuname } </span>同学以 <span
											class="divcss5-x5">${h1.remark }</span> 为理由请假, 离校时间:<span
											class="divcss5-x5">${h1.strattime }</span> 返校时间:<span
											class="divcss5-x5">${h1.endtime }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookHolidayDetial(${h1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${h1.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${studentApplies1 }" var="app1">
								<li class="warning-element"><font size="3"><B><span
											class="divcss5-x5">${app1.studentClass.classname } </span>班的 <span
											class="divcss5-x5">${app1.student.stuname } </span>同学以 <span
											class="divcss5-x5">${app1.remark }</span> 为理由申请调换班级, 转入<span
											class="divcss5-x5">${app1.studentClass1.classname }</span>班,
											现在所在班级的课程进度为:<span class="divcss5-x5">
											${app1.studwork }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookStudentApplyDetial(${app1.id});"
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${app1.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${flist2 }" var="f2">
								<li class="warning-element"><font size="3"><B>
											申购了 <span class="divcss5-x5">${f2.counts }</span>${f2.material.unit }
											<span class="divcss5-x5">${f2.material.materialname },</span>
											总价为:<span class="divcss5-x5">${f2.material.price*f2.counts}</span>元
									</B> </font>
									<div class="agile-detail">
										<a href="#" onclick="lookapplymaterial(${f2.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${f2.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${empholidaylist2 }" var="eh2">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${eh2.emp.empname } </span>以 <span
											class="divcss5-x5">${eh2.remark }</span> 为理由请假, 开始时间:<span
											class="divcss5-x5">${eh2.strattime }</span> 结束时间:<span
											class="divcss5-x5">${eh2.endtime }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookempholidaydetial(${eh2.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${eh2.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${supplementlist2 }" var="sp2">
								<li class="warning-element"><font size="3"><B>申请于 <span
											class="divcss5-x5">${sp2.sutime } </span>补签,理由是: <span
											class="divcss5-x5">${sp2.suremark }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookSupplementdetial(${sp2.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${sp2.createtime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${receivemateriallist2 }" var="rm2">
								<li class="warning-element"><font size="3"><B>申领了<span class="divcss5-x5">${rm2.counts }</span>${rm2.material.unit }
										<span class="divcss5-x5">${rm2.material.materialname }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookReceivceMaterial(${rm2.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${rm2.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${returnmateriallist2 }" var="return2">
								<li class="warning-element"><font size="3"><B>归还了<span class="divcss5-x5">${return2.counts }</span>${return2.material.unit }
										<span class="divcss5-x5">${return2.material.materialname }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookReturnMaterial(${return2.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${return2.createtime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${Reimburselist2 }" var="Reimburse2">
								<li class="warning-element"><font size="3"><B>以<span class="divcss5-x5">${Reimburse2.applyremark }</span>为理由
										申请报销,金额为<span class="divcss5-x5">${Reimburse2.price }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookReimburse(${Reimburse2.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${Reimburse2.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${Swaparrangelist2 }" var="Swaparrange">
								<li class="warning-element"><font size="3"><B>
										申请于<span class="divcss5-x5">${Swaparrange.weekends }</span>
										和<span class="divcss5-x5">${Swaparrange.changeTwoMan }</span>
										换班,理由是:<span class="divcss5-x5">${Swaparrange.remark }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="lookSwaparrange(${Swaparrange.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">查看审批进度</a> <font
											size="4"><i class="fa fa-clock-o">${Swaparrange.createTime }</i></font>
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox">
					<div class="ibox-content">
						<h3>未提交申请</h3>
						<ul class="sortable-list connectList agile-list">
							<c:forEach items="${holiday0 }" var="h0">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${h0.student.stuname } </span>同学以 <span
											class="divcss5-x5">${h0.remark }</span> 为理由请假, 离校时间:<span
											class="divcss5-x5">${h0.strattime }</span> 返校时间:<span
											class="divcss5-x5">${h0.endtime }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="formFlowHoliday(${h0.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a> <a href="#"
											onclick="deleteHoliday(${h0.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a> <font
											size="4"><i class="fa fa-clock-o">${h0.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${studentApplies0 }" var="app0">
								<li class="warning-element"><font size="3"><B><span
											class="divcss5-x5">${app0.studentClass.classname } </span>班的 <span
											class="divcss5-x5">${app0.student.stuname } </span>同学以 <span
											class="divcss5-x5">${app0.remark }</span> 为理由申请调换班级, 转入<span
											class="divcss5-x5">${app0.studentClass1.classname }</span>班,
											现在所在班级的课程进度为:<span class="divcss5-x5">
											${app0.studwork }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="formFlowStudentApply(${app0.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a> <a href="#"
											onclick="deleteStudentApply(${app0.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a> <font
											size="4"><i class="fa fa-clock-o">${app0.createTime }</i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${flist1 }" var="f1">
								<li class="warning-element"><font size="3"><B>
											申购了 <span class="divcss5-x5">${f1.counts }</span>${f1.material.unit }
											<span class="divcss5-x5">${f1.material.materialname },</span>
											总价为:<span class="divcss5-x5">${f1.material.price}</span>元
									</B> </font>
									<div class="agile-detail">
										<a href="#" onclick="deleteapplymaterial(${f1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										<a href="#" onclick="upsetapplymaterial(${f1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${empholidaylist1 }" var="eh1">
								<li class="warning-element"><font size="3"><B> <span
											class="divcss5-x5">${eh1.emp.empname } </span>以 <span
											class="divcss5-x5">${eh1.remark }</span> 为理由请假, 开始时间:<span
											class="divcss5-x5">${eh1.strattime }</span> 结束时间:<span
											class="divcss5-x5">${eh1.endtime }</span> 望批准;
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="upsetEmpHoliday(${eh1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<a href="#" onclick="deleteEmpHoliday(${eh1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${supplementlist1 }" var="sp1">
								<li class="warning-element"><font size="3"><B>申请于 <span
											class="divcss5-x5">${sp1.sutime } </span>补签,理由是: <span
											class="divcss5-x5">${sp1.suremark }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="upsetSupplement(${sp1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<a href="#" onclick="deleteSupplement(${sp1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${receivemateriallist1 }" var="rm1">
								<li class="warning-element"><font size="3"><B>
										申领了<span class="divcss5-x5">${rm1.counts }</span>${rm1.material.unit }
										<span class="divcss5-x5">${rm1.material.materialname }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="upsetReceiveMaterial(${rm1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<a href="#" onclick="deleteReceiveMaterial(${rm1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${returnmateriallist1 }" var="return1">
								<li class="warning-element"><font size="3"><B>
										归还了<span class="divcss5-x5">${return1.counts }</span>${return1.material.unit }
										<span class="divcss5-x5">${return1.material.materialname }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="upsetReturnMaterial(${return1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<a href="#" onclick="deleteReturnMaterial(${return1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${Reimburselist1 }" var="Reimburse1">
								<li class="warning-element"><font size="3"><B>
										以<span class="divcss5-x5">${Reimburse1.applyremark }</span>为理由
										申请报销,金额为<span class="divcss5-x5">${Reimburse1.price }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="upsetReimburse(${Reimburse1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<a href="#" onclick="deleteReimburse(${Reimburse1.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							<c:forEach items="${Swaparrangelist1 }" var="Swaparrangelist">
								<li class="warning-element"><font size="3"><B>
										申请于<span class="divcss5-x5">${Swaparrangelist.weekends }</span>
										和<span class="divcss5-x5">${Swaparrangelist.changeTwoMan }</span>
										换班,理由是:<span class="divcss5-x5">${Swaparrangelist.remark }</span>
									</B></font>
									<div class="agile-detail">
										<a href="#" onclick="upsetSwaparrange(${Swaparrangelist.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">提交申请</a>
										<a href="#" onclick="deleteSwaparrange(${Swaparrangelist.id});" id=""
											style="background-color: #84c1ff; color: black;"
											class="pull-right btn btn-ms btn-white">删除该申请</a>
										
										<font size="4"><i class="fa fa-clock-o"></i></font>
									</div></li>
							</c:forEach>
							
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 模板字典修改弹窗  -->
	<div class="modal inmodal" id="window_flowModelSel" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">申请审批详情</h4>
				</div>

				<div class="ibox-content">
					<div id="detial"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>